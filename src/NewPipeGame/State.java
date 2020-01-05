package NewPipeGame;

import java.util.*;

public class State<T> implements Comparable<State<T>>{
	private Point point;
	private State<T> prevState;
	private Point start;
	private int cost;
	private T[][] board;
	@SuppressWarnings("unchecked")
	//CTOR for initState().
	public State(T[][] matrix,Point start) 
	{
		this.prevState = null;
		this.cost = 0;
		this.point = new Point(start);
		this.start = new Point(start);
		this.board = (T[][])new Character[matrix.length][matrix[0].length];
		for (int i=0; i<matrix.length; i++)
		{
			for (int j=0; j<matrix[0].length; j++)
			{
				this.board[i][j] = matrix[i][j];
			}
		}
	}
	@SuppressWarnings("unchecked")
	//CTOR for new states.
	public State(State<T> prev,Point p,T t)
	{
		this.point = new Point(p);
		this.prevState = prev;
		this.start = new Point(prev.getStartPoint());
		this.cost = prev.getCost();
		//this.cost = Math.abs(p.getRow()-prev.getStartPoint().getRow())+Math.abs(p.getColumn()-prev.getStartPoint().getColumn());
		this.board = (T[][])new Character[prev.getNumOfRows()][prev.getNumOfColumns()];
		for (int i=0;i<prev.getBoard().length;i++)
		{
			for (int j=0;j<prev.getBoard()[0].length;j++)
			{
				this.board[i][j] = prev.getBoard()[i][j];
			}
		}
		this.board[p.getRow()][p.getColumn()] = t;
	}
	//Get the content of a point in the board.
	public T getTOnPoint(Point point)	
	{
		return board[(int)point.getRow()][(int)point.getColumn()];
	}
	//Set the content of a point in the board.
	public void setTOnPoint(Point point,T t) 
	{
		board[point.getRow()][point.getColumn()] = t;
	}
	//Get the board in the state. Shallow copy.
	public T[][] getBoard() 
	{
		return board;
	}
	//Get the number of the rows in the board.
	public int getNumOfRows() 
	{
		return board.length;
	}
	//Get the number of the columns in the board.
	public int getNumOfColumns() 
	{
		return board[0].length;
	}
	//Get the previous state to this state.
	public State<T> getPrevState()
	{
		return prevState;
	}
	//Get the point that rotated this state.
	public Point getPoint() {
		return point;
	}
	//Get the cost for this state.
	public int getCost()
	{
		return this.cost;
	}
	//Set the cost for this state.
	public void setCost(int cost)
	{
		this.cost = cost;
	}
	//Get the start point.
	public Point getStartPoint()
	{
		return this.start;
	}
	//NEED TO BE CHECKED.
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State<?> other = (State<?>) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}
	@Override
	public int compareTo(State<T> state)
	{
	    if(this.cost<state.getCost())
	          return -1;
	    else if(this.cost>state.getCost())
	          return 1;
	    return 0;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		return result;
	}
}
