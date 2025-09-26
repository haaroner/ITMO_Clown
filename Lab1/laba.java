import java.util.Random;

public class laba 
{
	static int[] I = new int[6];
	static double[][] l  = new double[6][20];
	static float[] x = new float[20];
	static double xj;
	public static double calc(int i, int j, double _xj)
	{
		if(I[i] == 9)
			l[i][j] = Math.tan(Math.sin(_xj*(_xj+1)));
		else if(I[i] >= 11)
			l[i][j] = Math.tan(Math.tan(Math.cos(_xj)));	
		else
			l[i][j] = Math.pow(((Math.pow(Math.atan(1D/Math.pow(Math.E, Math.abs(_xj) )), 2D*2D*Math.pow(4D/_xj, 3D)))/Math.cbrt(Math.tan(_xj)/(2D-Math.pow(Math.E, _xj)))+1D), 2D);
		return l[i][j];
	}

	public static void printout()
	{
		for(int i = 0; i < 6; ++i)
		{
			for(int j = 0; j < 20; ++j)
			{
				xj = x[j];
				System.out.printf("%.5f", (float)calc(i, j, xj));
				System.out.print("  ");
			}
			System.out.println(" ");
		}
	}
	public static void main(String[] args)
	{
		Random random = new Random();
		
		for(int i = 17; i >= 7; i--) 
			if(i%2 == 1)
				I[(17-i)/2] = i;

		for(int i = 0; i < 20; i++)
		{
			x[i] = (float)(random.nextInt(210)/10.0)-11;
		}
		printout();	
	}
}
