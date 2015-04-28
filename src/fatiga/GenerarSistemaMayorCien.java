package fatiga;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GenerarSistemaMayorCien {

	public static void main(String[] args) {
		FileWriter fichero = null;
		PrintWriter pw = null;

		try {
			fichero = new FileWriter("entrada.in");
			pw = new PrintWriter(fichero);
			Random num = new Random();
			pw.println(1000);
			for (int i = 0; i < 1000; i++)
				for (int j = 0; j < 1000; j++)
					pw.println(i + " " + j + " " + (num.nextInt() % 100) + num.nextDouble());
			for (int i = 0; i < 1000; i++)
				pw.println(num.nextInt() % 100 + num.nextDouble());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
