package sistemaEcuacionLineal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SEL {

	private static final double ERROR_ACEPTADO = 1E-6;
	private MatrizMath coeficientes;
	private VectorMath resultados;
	private VectorMath incognitas;
	private long tiempo;

	public SEL(String ruta) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String[] elementos;
		int dimension;
		int contador = 0;
		try {
			archivo = new File(ruta);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			// Lectura del fichero
			String linea;
			linea = br.readLine();
			dimension = Integer.parseInt(linea);
			double[][] mat = new double[dimension][dimension];
			while (contador < Math.pow(dimension, 2)) {
				linea = br.readLine();
				elementos = linea.split(" ");
				mat[Integer.parseInt(elementos[0])][Integer
						.parseInt(elementos[1])] = Double
						.parseDouble(elementos[2]);
				contador++;
			}
			contador = 0;
			coeficientes = new MatrizMath(mat, dimension);
			// Hasta ac�, ley� y carg� la matriz. Sigo con el vector resultado.
			double[] resultados = new double[dimension];
			while (contador < dimension) {
				linea = br.readLine();
				resultados[contador] = Double.parseDouble(linea);
				contador++;
			}
			this.resultados = new VectorMath(resultados, dimension);
			// Ya tengo la matriz de coeficientes cargada, y los resultados
			// cargados en el vector.
			// No corta en los espacios en blanco
			this.incognitas = new VectorMath(dimension);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public SEL(MatrizMath coeficientes, VectorMath resultados, int dimension) {
		this.resultados = resultados;
		this.incognitas = new VectorMath(dimension);
		this.coeficientes = coeficientes;
	}

	public void resolverSistema() {
		long tiempoInicial = System.currentTimeMillis();
		MatrizMath inversa = coeficientes.invertirMatriz();
		incognitas = inversa.prodMatricial(resultados);
		tiempo = System.currentTimeMillis() - tiempoInicial;
	}

	public boolean resultadoCorrecto() {
		return getError() < ERROR_ACEPTADO;
	}

	public double getError() {
		return Math.abs(
				coeficientes.prodMatricial(incognitas).norma2() - resultados.norma2());
	}

	public void escribirSolucionArchivo(String ruta) {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(ruta);
			pw = new PrintWriter(fw);

			try {
				resolverSistema();
				pw.print(incognitas.getDimension() + "\n");
				for (int i = 0; i < incognitas.getDimension(); i++)
					pw.println(incognitas.getArray()[i]);
				pw.println("\n" + getError());
			} catch (DistDimException e) {
				pw.println("El Sistema no tiene Solución.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.close();
	}

	public static void main(String[] args) {
		SEL s1 = new SEL("./entrada.in");
		s1.escribirSolucionArchivo("./salida.out");
	}

	public double getTiempo() {
		return tiempo;
	}
}
