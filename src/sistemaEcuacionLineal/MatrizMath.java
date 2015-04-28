package sistemaEcuacionLineal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class MatrizMath
{

	private double[][] mat;
	private int filas, columnas;

	public MatrizMath(int filas, int columnas) throws DistDimException
	{
		if (filas > 0 && columnas > 0)
		{
			this.filas = filas;
			this.columnas = columnas;
			mat = new double[filas][columnas];

			for (int i = 0; i < filas; i++)
				for (int j = 0; j < columnas; j++)
					mat[i][j] = 0;
		}
		else
			throw new DistDimException("Dimension no valida");
	}

	public MatrizMath(double[][] matriz, int dimension)
	{
		mat = matriz.clone();
		filas = columnas = dimension;
	}

	public MatrizMath(String ruta)
	{
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String[] elementos;
		try
		{
			archivo = new File(ruta);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			// Lectura del fichero
			String linea;
			linea = br.readLine();
			elementos = linea.split(" ");
			filas = Integer.parseInt(elementos[0]);
			columnas = Integer.parseInt(elementos[1]);
			mat = new double[filas][columnas];
			while ((linea = br.readLine()) != null)
			{
				elementos = linea.split(" ");
				mat[Integer.parseInt(elementos[0])][Integer
						.parseInt(elementos[1])] = Double
						.parseDouble(elementos[2]);
			}
			// No corta en los espacios en blanco
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (null != fr)
				{
					fr.close();
				}
			}
			catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
	}

	public MatrizMath cloneMat()
	{
		MatrizMath copiada = new MatrizMath(filas, columnas);
		for (int i = 0; i < filas; i++)
			for (int j = 0; j < columnas; j++)
				copiada.mat[i][j] = mat[i][j];
		return copiada;
	}

	@Override
	public String toString()
	{
		String mat = "";
		for (int i = 0; i < filas; i++)
		{
			for (int j = 0; j < columnas; j++)
				mat += " " + this.mat[i][j];
			mat += '\n';
		}
		return mat;
	}

	public MatrizMath sumarMat(MatrizMath m2) throws DistDimException
	{
		if (filas != m2.filas || columnas != m2.columnas)
			throw new DistDimException(
					" La dimension no coincide, no se puede sumar ");
		MatrizMath resultado = new MatrizMath(filas, columnas);
		for (int i = 0; i < filas; i++)
			for (int j = 0; j < columnas; j++)
				resultado.mat[i][j] = mat[i][j] + m2.mat[i][j];
		return resultado;
	}

	public MatrizMath restarMat(MatrizMath m2) throws DistDimException
	{
		if (filas != m2.filas || columnas != m2.columnas)
			throw new DistDimException(
					" La dimension no coincide, no se puede restar ");
		MatrizMath resultado = new MatrizMath(filas, columnas);
		for (int i = 0; i < filas; i++)
			for (int j = 0; j < columnas; j++)
				resultado.mat[i][j] = mat[i][j] - m2.mat[i][j];
		return resultado;
	}

	public MatrizMath prodMatricial(MatrizMath m2) throws DistDimException
	{
		if (columnas != m2.filas)
			throw new DistDimException(
					" Las dimensiones no coinciden, imposible operar. ");
		MatrizMath resultado = new MatrizMath(filas, m2.columnas);
		for (int i = 0; i < resultado.filas; i++)
			for (int j = 0; j < resultado.columnas; j++)
				for (int ciclo = 0; ciclo < columnas; ciclo++)
					resultado.mat[i][j] += mat[i][ciclo] * m2.mat[ciclo][j];
		return resultado;
	}

	public void prodMatricial(int escalar)
	{
		for (int i = 0; i < filas; i++)
			for (int j = 0; j < columnas; j++)
				mat[i][j] *= escalar;
	}

	public VectorMath prodMatricial(VectorMath v1) throws DistDimException
	{
		if (v1.getDimension() != columnas)
			throw new DistDimException("Dimensiones no validas");
		double vector[] = new double[v1.getDimension()];
		vector = v1.getArray();
		double[] resultado = new double[filas];
		MatrizMath trasp = this.trasponer();
		for (int i = 0; i < resultado.length; i++)
			for (int ciclo = 0; ciclo < trasp.filas; ciclo++)
				resultado[i] += vector[ciclo] * trasp.mat[ciclo][i];
		VectorMath vResultado = new VectorMath(columnas);
		vResultado.setVec(resultado);
		return vResultado;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatrizMath other = (MatrizMath) obj;
		if (columnas != other.columnas)
			return false;
		if (filas != other.filas)
			return false;
		if (!Arrays.deepEquals(mat, other.mat))
			return false;
		return true;
	}

	public double norma1() throws DistDimException
	{
		if (filas != columnas)
			throw new DistDimException("La matriz no es cuadrada ");
		double maximo = 0;
		double acum = 0;
		for (int i = 0; i < columnas; i++)
		{
			for (int j = 0; j < filas; j++)
				acum += Math.abs(mat[j][i]);
			if (acum > maximo)
				maximo = acum;
			acum = 0;
		}
		return maximo;
	}

	public double normaInf() throws DistDimException
	{
		if (filas != columnas)
			throw new DistDimException("La matriz no es cuadrada");
		double maximo = 0;
		double acum = 0;

		for (int i = 0; i < filas; i++)
		{
			for (int j = 0; j < columnas; j++)
				acum += Math.abs(mat[i][j]);
			if (acum > maximo)
				maximo = acum;
			acum = 0;
		}

		return maximo;
	}

	public double normaDos() throws DistDimException
	{
		if (filas != columnas)
			throw new DistDimException("La matriz no es cuadrada");
		double acum = 0;

		for (int i = 0; i < filas; i++)
			for (int j = 0; j < columnas; j++)
				acum += Math.pow(mat[i][j], 2);

		return Math.sqrt(acum);
	}

	public int verificaOperacion(int fila, int columna)
	{
		if (mat[columna][columna] != 0)
			return -1; // no hubo cambio de fila, el signo del determinante no
						// cambia

		if (mat[columna][columna] == 0)
		{
			int i = fila;
			while (i < filas)
			{
				if (mat[i][columna] != 0)
				{
					for (int j = 0; j < filas; j++)
					{
						double aux = mat[i][j];
						mat[i][j] = mat[columna][j];
						mat[columna][j] = aux;
					}
					return 1; // cambio de filas, cambia signo determinante
				}
				i++;
			}
		}
		return 0;
	}

	public boolean verificaOperacion(int fila, int columna, MatrizMath inversa)
	{
		if (mat[columna][columna] != 0)
			return true;

		if (mat[columna][columna] == 0)
		{
			int i = fila;
			while (i < filas)
			{
				if (mat[i][columna] != 0)
				{
					for (int j = 0; j < filas; j++)
					{
						double aux = mat[i][j];
						mat[i][j] = mat[columna][j];
						mat[columna][j] = aux;
						double auxInv = inversa.mat[i][j];
						inversa.mat[i][j] = inversa.mat[columna][j];
						inversa.mat[columna][j] = auxInv;
					}
					return true;
				}
				i++;
			}
		}
		return false;
	}

	public int triangularMatAbajo()
	{
		int cambios = 0;
		for (int columna = 0; columna < columnas; columna++)
			for (int fila = columna + 1; fila < filas; fila++)
			{
				int retorno = this.verificaOperacion(fila, columna);
				if (retorno != 0)
				{
					cambios += retorno == 1 ? 1 : 0;
					if (mat[fila][columna] != 0)
					{
						double factor = mat[fila][columna]
								/ mat[columna][columna];
						for (int indColumna = columna; indColumna < columnas; indColumna++)
							mat[fila][indColumna] -= (mat[columna][indColumna] * factor);

						mat[fila][columna] = 0;
					}
				}
			}
		return cambios;
	}

	public void triangularMatArriba()
	{
		for (int fila = filas - 2; fila >= 0; fila--)
			for (int columna = columnas - 1; columna > fila; columna--)
			{
				if (mat[fila][columna] != 0)
				{
					double factor = mat[fila][columna] / mat[columna][columna];
					for (int indColumna = columna; indColumna >= 0; indColumna--)
						mat[fila][indColumna] -= (mat[columna][indColumna] * factor);

					mat[fila][columna] = 0;
				}
			}
	}

	public void triangulacionMatAbajo(MatrizMath inversa)
	{
		for (int columna = 0; columna < columnas; columna++)
			for (int fila = columna + 1; fila < filas; fila++)
			{
				if (this.verificaOperacion(fila, columna, inversa))
				{
					if (mat[fila][columna] != 0)
					{
						double factor = mat[fila][columna]
								/ mat[columna][columna];

						for (int indColumna = 0; indColumna < columnas; indColumna++)
						/*
						 * tiene que empezar de cero para que haga la operacion
						 * en el elemento que corresponde de la inversa
						 * mat[2][0]
						 */
						{
							mat[fila][indColumna] -= (mat[columna][indColumna] * factor);
							inversa.mat[fila][indColumna] -= (inversa.mat[columna][indColumna] * factor);
						}
						mat[fila][columna] = 0;
					}
				}
			}
	}

	public void triangulacionMatArriba(MatrizMath inversa)
	{
		for (int fila = filas - 2; fila >= 0; fila--)
			for (int columna = columnas - 1; columna > fila; columna--)
			{
				if (mat[fila][columna] != 0)
				{
					double factor = mat[fila][columna] / mat[columna][columna];
					for (int indColumna = columnas - 1; indColumna >= 0; indColumna--)
					/*
					 * tiene que empezar desde el final para que haga la
					 * operacion en el elemento que corresponde de la inversa
					 * mat[0][2]
					 */
					{
						mat[fila][indColumna] -= (mat[columna][indColumna] * factor);
						inversa.mat[fila][indColumna] -= (inversa.mat[columna][indColumna] * factor);
					}
					mat[fila][columna] = 0;
				}
			}
	}

	public double determinante() throws DistDimException
	{
		if (filas != columnas)
			throw new DistDimException("La matriz no es cuadrada");
		MatrizMath aux = this.cloneMat();

		int cambios = aux.triangularMatAbajo();
		double det = (cambios % 2) == 0 ? aux.mat[0][0] : aux.mat[0][0] * -1;
		for (int i = 1; i < filas; i++)
			det *= aux.mat[i][i];

		return det;
	}

	public MatrizMath invertirMatriz() throws DistDimException
	{
		MatrizMath inversa = new MatrizMath(filas, columnas);
		for (int i = 0; i < filas; i++)
			inversa.mat[i][i] = 1; // YA ESTA CREADA LA IDENTIDAD
		MatrizMath aux = this.cloneMat();
		aux.triangulacionMatAbajo(inversa);
		for (int i = 0; i < filas; i++)
		{
			if (aux.mat[i][i] == 0)
				throw new DistDimException(
						"El determinante es 0, la matriz no tiene inversa.");
		}
		aux.triangulacionMatArriba(inversa);
		for (int i = 0; i < filas; i++)
		{
			double factor = aux.mat[i][i];
			for (int j = 0; j < columnas; j++)
				inversa.mat[i][j] /= factor;
		}
		return inversa;
	}

	public void escribirMatrizArchivo(String ruta)
	{
		FileWriter fichero = null;
		PrintWriter pw = null;
		try
		{
			fichero = new FileWriter(ruta);
			pw = new PrintWriter(fichero);
			pw.println(filas + " " + columnas);
			for (int i = 0; i < filas; i++)
				for (int j = 0; j < columnas; j++)
					pw.println(i + " " + j + " " + mat[i][j]);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (null != fichero)
					fichero.close();
			}
			catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
	}

	public boolean verificaInversa()
	{
		MatrizMath identidad = new MatrizMath(filas, columnas);
		for (int i = 0; i < filas; i++)
			identidad.mat[i][i] = 1;

		for (int i = 0; i < filas; i++)
			for (int j = 0; j < columnas; j++)
			{
				if (Math.abs(identidad.mat[i][j] - mat[i][j]) > 1.0E-8)
					return false;
			}
		return true;
	}

	public MatrizMath trasponer()
	{
		MatrizMath traspuesta = new MatrizMath(columnas,filas);
		if (filas == columnas)
		{
			for (int i = 0; i < filas; i++)
				for (int j = i; j < columnas; j++)
				{
					traspuesta.mat[i][j] = mat[j][i];
					traspuesta.mat[j][i] = mat[i][j];
				}
		}
		else
		{
			for (int i = 0; i < filas; i++)
				for(int j = 0; j < columnas; j++)
					traspuesta.mat[j][i] = mat[i][j];
		}
		return traspuesta;
	}
}
