package NewPipeGame;
import java.util.*;
public class Normalizer
{
	//Normalize from String(by format as mentioned) to Character.
	//Format: 8 chars header, 3 char rows, 3 char columns, rows*columns chars. Ex: matrixxx003003s7-J|g LJ.
	public static Character[][] stringToCharacterMatrix(String str)
	{
		int row = ((str.charAt(8)-'0')*100) + ((str.charAt(9)-'0')*10) + (str.charAt(10)-'0');
		int col = ((str.charAt(11)-'0')*100) + ((str.charAt(12)-'0')*10) + (str.charAt(13)-'0');
		if(str.length() != 14 + (row*col))
		{
			throw new ArithmeticException("String is not at the right format.");
		}
		Character[][] out = new Character[row][col];
		for (int i=0;i<row;i++)
		{
			for (int j=0;j<col;j++)
			{
				out[i][j] = str.charAt(14+(i*col) + j);
			}
		}
		return out;
	}
	//Normalize from String(by format as mentioned) to String[].
	//Format: 8 chars header, 3 char rows, 3 char columns, rows*columns chars. Ex: matrixxx003003s7-J|g LJ.
	public static String[] stringToStringsArray(String str)
	{
		int row = ((str.charAt(8)-'0')*100) + ((str.charAt(9)-'0')*10) + (str.charAt(10)-'0');
		int col = ((str.charAt(11)-'0')*100) + ((str.charAt(12)-'0')*10) + (str.charAt(13)-'0');
		if(str.length() != 14 + (row*col))
		{
			throw new ArithmeticException("String is not at the right format.");
		}
		String[] out = new String[row];
		for (int i=0;i<row;i++)
		{
			out[i] = "";
			for (int j=0;j<col;j++)
			{
				out[i] += str.charAt(14+(i*col) + j);
			}
		}
		return out;
	}
	
	//Normalize from String(by new format as requierd by patam course) to String[].
	//Format: int for number of rows, int for number of column and the string (Ex: s7-J|g LJ).
	public static String[] stringToStringsArray(int row, int col, String str)
	{
		String[] out = new String[row];
		for (int i=0;i<row;i++)
		{
			out[i] = "";
			for (int j=0;j<col;j++)
			{
				out[i] += str.charAt((i*col) + j);
			}
		}
		return out;
	}
	//Example for using.
	/*public static void main(String[] args)
	{
		String str = "matrixxx004005s-|FJ----  gL7F   -|";
		System.out.println(str);
		try
		{
			Character[][] matrix = Normalizer.stringToCharacterMatrix(str);
			for (int i=0;i<matrix.length;i++)
			{
				for(int j=0;j<matrix[0].length;j++)
				{
					System.out.print(matrix[i][j]);
				}
				System.out.println("");
			}
			String[] array = Normalizer.stringToStringsArray(str);
			for (int i=0;i<array.length;i++)
			{
				for(int j=0;j<array[i].length();j++)
				{
					System.out.print(array[i].charAt(j));
				}
				System.out.println("");
			}
		}
		catch (ArithmeticException e)
		{
			System.out.println(e);
		}
	}*/
}
