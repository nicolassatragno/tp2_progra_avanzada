package fatiga;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import sistemaEcuacionLineal.MatrizMath;
import sistemaEcuacionLineal.SEL;
import sistemaEcuacionLineal.VectorMath;

public class GraficoFatiga {

	public static void main(String[] args) {
		Random num = new Random();
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter("./grafico.out");
			pw = new PrintWriter(fw);
			for (int caso = 1; caso < 1000; caso++) {
				double[][] mat = new double[caso][caso];
				double[] vec = new double[caso];
				for (int i = 0; i < caso; i++)
					for (int j = 0; j < caso; j++)
						mat[i][j] = num.nextDouble() % 1000;
				for (int i = 0; i < caso; i++)
					vec[i] = num.nextDouble() % 1000;
				SEL sel = new SEL(new MatrizMath(mat, caso), new VectorMath(vec, caso), caso);
				sel.resolverSistema();
				pw.println(sel.getTiempo());
				if (caso % 100 == 0) {
					System.out.println("Progreso: " + caso / 10 + "%");
					pw.flush();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.close();
		
	}
}
