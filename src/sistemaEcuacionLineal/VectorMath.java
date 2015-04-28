package sistemaEcuacionLineal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class VectorMath
{
	private double[] vec; // new
	private int dimension;

	public VectorMath(int dimension) throws DistDimException
	{
		if (dimension > 0)
		{
			this.dimension = dimension;
			vec = new double[dimension];
			for (int i = 0; i < dimension; i++)
				vec[i] = 0;
		}
		else
			throw new DistDimException(" Dimension no valida");
	}

	public VectorMath(String ruta) throws DistDimException
	{
		int i = 0;
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			archivo = new File(ruta);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			String linea = br.readLine();
			if (Integer.parseInt(linea) < 0)
				throw new DistDimException(" Distinta Dimension ");
			dimension = Integer.parseInt(linea);
			vec = new double[dimension];
			while ((linea = br.readLine()) != null)
				vec[i++] = Double.parseDouble(linea);
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

	public VectorMath(double[] resultados, int dimension)
	{
		vec = resultados.clone();
		this.dimension = dimension;
	}

	public int getDimension()
	{
		return dimension;
	}

	public double[] getArray()
	{
		return vec;
	}

	public void setVec(double[] vec)
	{
		this.vec = vec.clone();
	}

	public VectorMath suma(VectorMath v2) throws DistDimException
	{
		if (this.dimension != v2.dimension)
			throw new DistDimException(" Distinta Dimension ");
		VectorMath suma = new VectorMath(dimension);
		for (int i = 0; i < dimension; i++)
			suma.vec[i] = vec[i] + v2.vec[i];
		return suma;
	}

	@Override
	public String toString()
	{
		return "VectorMath [vec=" + Arrays.toString(vec) + ", dimension="
				+ dimension + "]";
	}

	public VectorMath resta(VectorMath v2) throws DistDimException
	{
		if (this.dimension != v2.dimension)
			throw new DistDimException(" Distinta Dimension ");
		VectorMath resta = new VectorMath(dimension);
		for (int i = 0; i < dimension; i++)
			resta.vec[i] = vec[i] - v2.vec[i];
		return resta;
	}

	public VectorMath producto(double escalar)
	{
		VectorMath resultado = new VectorMath(dimension);
		for (int i = 0; i < dimension; i++)
			resultado.vec[i] = vec[i] * escalar;
		return resultado;
	}

	public double producto(VectorMath v2) throws DistDimException
	{
		if (dimension != v2.dimension)
			throw new DistDimException(" Distinta Dimension ");
		double result = 0;
		for (int i = 0; i < dimension; i++)
			result += vec[i] * v2.vec[i];
		return result;
	}

	public double norma1()
	{
		double resultado = 0;
		for (int i = 0; i < dimension; i++)
			resultado += Math.abs(vec[i]);
		return resultado;
	}

	public double norma2()
	{
		double resultado = 0;

		for (int i = 0; i < dimension; i++)
			resultado += Math.pow(vec[i], 2);

		return Math.sqrt(resultado);
	}

	public double normaInf()
	{
		double resultado = 0;
		for (int i = 0; i < dimension; i++)
		{
			if (Math.abs(vec[i]) > resultado)
				resultado = Math.abs(vec[i]);
		}
		return resultado;
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
		VectorMath other = (VectorMath) obj;
		if (dimension != other.dimension)
			return false;
		if (!Arrays.equals(vec, other.vec))
			return false;
		return true;
	}

	public VectorMath clonar()
	{
		VectorMath v1 = new VectorMath(this.dimension);
		v1.vec = vec.clone();
		return v1;
	}
}
