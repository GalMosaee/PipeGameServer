package NewPipeGame;

import java.util.*;

public class Solution {
	private LinkedList<String> solutionList;
	//Default CTOR.
	public Solution()
	{
		this.solutionList = new LinkedList<String>();
	}
	//Add a step to the solution.
	//If a point existing in the solution, there is an addition to the rotation at this point.
	public void addStep(Point p)
	{ 
		for(String item : this.solutionList)
		{
			if (item.contains(p.getRow()+","+p.getColumn()+","))
			{
				this.solutionList.remove(item);
				int rotation = Integer.parseInt(item.substring(item.lastIndexOf(',')+1));
				this.solutionList.add(p.getRow()+","+p.getColumn()+","+(rotation+1));
				return;
			}
		}
		this.solutionList.add(p.getRow()+","+p.getColumn()+","+1);
	}
	//Return solution list as a LinkedList<String> (Shallow copy).
	public LinkedList<String> getSolution()
	{
		return this.solutionList;
	}
	public ArrayList<String> getArraySolution()
	{
		ArrayList<String>  solution = new ArrayList<String>();
		for (String s : solutionList)
		{
			solution.add(s);
		}
		return solution;
	}
	//Normalize the solution (Using for normalize number of options).
	public void normalizeSolution(ISearchable searchable)
	{
		Point p = new Point();
		int rotation;
		LinkedList<String> temp = new LinkedList<String>();
		for(String item : this.solutionList)
		{
			p.setRow(Integer.parseInt(item.substring(0, item.indexOf(','))));
			p.setColumn(Integer.parseInt(item.substring(item.indexOf(',')+1, item.lastIndexOf(','))));
			rotation = Integer.parseInt(item.substring(item.lastIndexOf(',')+1));
			rotation = rotation % searchable.maxNumOfOptions(p);
			if(rotation != 0)
			{
				temp.add(p.getRow()+","+p.getColumn()+","+rotation); 
			}
		}
		this.solutionList = temp;
	}
	@Override
	public String toString()
	{
		String out = "";
		for(String item : this.solutionList)
		{
			out += item + System.lineSeparator();
		}
		return out;
	}
	/*//Test main for check Solution class.
	public static void main(String[] args)
	{
		Solution sol = new Solution();
		sol.addStep(new Point(1,1));
		sol.addStep(new Point(1,2));
		sol.addStep(new Point(1,3));
		sol.addStep(new Point(1,2));
		sol.addStep(new Point(1,2));
		sol.addStep(new Point(0,1));
		sol.addStep(new Point(0,2));
		sol.addStep(new Point(1,3));
		sol.addStep(new Point(2,2));
		sol.addStep(new Point(1,2));
		LinkedList<String> list = sol.getSolution();
		System.out.println(sol.toString());
	}*/
}
