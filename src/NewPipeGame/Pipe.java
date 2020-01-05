package NewPipeGame;

import java.util.*;

public class Pipe implements ISearchable<Character>  {
	private int row;
	private int col;
	private char[][] matrix;
	//private HashMap<String,State<Character>> map;
	//CTOR for Pipe board.
	public Pipe()
	{
		this.matrix = null;
	}
	//CTOR by char[][]. Using setMatrix by char[][].
	public Pipe(char[][] str)
	{
		this.row = str.length;
		this.col = str[0].length;
		this.SetMatrix(str);
	}
	//Set the matrix by char[][].
	public void SetMatrix(char[][] str)
	{
		matrix = new char[this.row][this.col];
		for (int i=0;i<this.row;i++)
		{
			for (int j=0;j<this.col;j++)
			{
				this.matrix[i][j] = str[i][j];
			}
		}
	}
	//CTOR by char[][]. Using setMatrix by char[][].
	public Pipe(Character[][] str)
	{
		this.row = str.length;
		this.col = str[0].length;
		this.SetMatrix(str);
	}
	//Set the matrix by char[][].
	public void SetMatrix(Character[][] str)
	{
		matrix = new char[this.row][this.col];
		for (int i=0;i<this.row;i++)
		{
			for (int j=0;j<this.col;j++)
			{
				this.matrix[i][j] = str[i][j];
			}
		}
	}
	//CTOR by String[]. Using setMatrix by String[].
	public Pipe(String[] str)
	{
		this.row = str.length;
		this.col = str[0].length();
		this.SetMatrix(str);
	}
	//Set the matrix by String[].
	public void SetMatrix(String[] str)
	{
		matrix = new char[this.row][this.col];
		for (int i=0;i<this.row;i++)
		{
			for (int j=0;j<this.col;j++)
			{
				this.matrix[i][j] = str[i].charAt(j);
			}
		}
	}
	//Get matrix. Return as char[][]. Deep copy.
	public char[][] getMatrixAsCharMatrix()
	{
		char[][] out = new char[this.row][this.col];
		for(int i = 0; i<this.row;i++)
		{
			for(int j = 0; j<this.col;j++)
			{
				out[i][j] = this.matrix[i][j];
			}
		}
		return out;
	}
	//Get matrix. Return as Character[][]. Deep copy.
	public Character[][] getMatrixAsCharacterMatrix()
	{
		Character[][] out = new Character[this.row][this.col];
		for(int i = 0; i<this.row;i++)
		{
			for(int j = 0; j<this.col;j++)
			{
				out[i][j] = this.matrix[i][j];
			}
		}
		return out;
	}
	//Get matrix. Return as String[]. Deep copy.
	public String[] getMatrixAsStringArray()
	{
		String[] out = new String[this.row];
		for(int i = 0; i<this.row;i++)
		{
			out[i] = new String();
			for(int j = 0; j<this.col;j++)
			{
				out[i] += this.matrix[i][j];
			}
		}
		return out;
	}
	//Get matrix. Return as String. Deep copy.
	public String getMatrixAsString()
	{
		String out = "";
		for(int i = 0; i<this.row;i++)
		{
			for(int j = 0; j<this.col;j++)
			{
				out += this.matrix[i][j];
			}
		}
		return out;
	}
	//Get start point.
	public Point getStartPoint()
	{
		for (int i=0;i<this.row;i++)
		{
			for(int j=0;j<this.col;j++)
			{
				if (this.matrix[i][j] == 's')
				{
					return new Point(i,j);
				}
			}
		}
		return new Point();
	}
	//Get goal point.
	public Point getGoalPoint()
	{
		for (int i=0;i<this.row;i++)
		{
			for(int j=0;j<this.col;j++)
			{
				if (this.matrix[i][j] == 'g')
				{
					return new Point(i,j);
				}
			}
		}
		return new Point();
	}
	//Get number of columns.
	public int getColumns()
	{
		return this.col;
	}
	//Get number of rows.
	public int getRows()
	{
		return this.row;
	}
	//Get a pipe at index.
	public char getPipeAtIndex(int row,int col)
	{
		return this.matrix[row][col];
	}
	//Get the initialState.
	public State<Character> initState()
	{
		State<Character> out = new State<Character>(this.getMatrixAsCharacterMatrix(),this.getStartPoint());
		//this.map = new HashMap();
		//map.put(this.getMatrixAsString(), out);
		return out;
	}
	//Get State board as string (Deep Copy).
	public String getStateAsString(State<Character> state)
	{
		Character[][] temp = state.getBoard();
		String out = "";
		for(int i=0;i<state.getNumOfRows();i++)
		{
			for(int j=0;j<state.getNumOfColumns();j++)
			{
				out += temp[i][j];
			}
		}
		return out;
	}
	//Check if a state is Goal state (Possible path from s to g). Using recIsGoal().
	public boolean isGoal(State<Character> state)
	{
		Point start = new Point(this.getStartPoint());
		boolean[][] visited = new boolean[state.getNumOfRows()][state.getNumOfColumns()];
		//By Default boolean (primitive) is false.
		/*for (int i = 0;i<state.getNumOfRows();i++)
		{
			for (int j = 0;j<state.getNumOfColumns();j++)
			{
				 visited[i][j] = false;
			}
		}*/
		return recIsGoal(state,visited,start);
	}
	//Recurse check for isGoal() (Dynamic Algorithm).
	private boolean recIsGoal(State<Character> state,boolean[][] visited,Point p)
	{
		if(state.getTOnPoint(p) == 'g')
		{
			return true;
		}
		Point next = new Point(p);
		visited[next.getRow()][next.getColumn()] = true;
		boolean flag = false;
		if(state.getTOnPoint(p) == '|')
		{
			next.setRow(p.getRow()+1);
			if ((next.getRow() < state.getNumOfRows())&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'L')||(state.getTOnPoint(next) == 'J')||(state.getTOnPoint(next) == '|')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setRow(p.getRow()-1);
			if ((next.getRow() >= 0)&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == '7')||(state.getTOnPoint(next) == 'F')||(state.getTOnPoint(next) == '|')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setRow(p.getRow());
		}
		if(state.getTOnPoint(p) == '-')
		{
			next.setColumn(p.getColumn()+1);
			if ((next.getColumn() < state.getNumOfColumns())&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'J')||(state.getTOnPoint(next) == '7')||(state.getTOnPoint(next) == '-')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setColumn(p.getColumn()-1);
			if ((next.getColumn() >= 0)&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'L')||(state.getTOnPoint(next) == 'F')||(state.getTOnPoint(next) == '-')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setColumn(p.getColumn());
		}
		if(state.getTOnPoint(p) == 'L')
		{
			next.setRow(p.getRow()-1);
			if ((next.getRow() >= 0)&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == '7')||(state.getTOnPoint(next) == 'F')||(state.getTOnPoint(next) == '|')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setRow(p.getRow());
			next.setColumn(p.getColumn()+1);
			if ((next.getColumn() < state.getNumOfColumns())&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'J')||(state.getTOnPoint(next) == '7')||(state.getTOnPoint(next) == '-')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setColumn(p.getColumn());
		}
		next.setColumn(p.getColumn());
		if(state.getTOnPoint(p) == 'F')
		{
			next.setRow(p.getRow()+1);
			if ((next.getRow() < state.getNumOfRows())&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'L')||(state.getTOnPoint(next) == 'J')||(state.getTOnPoint(next) == '|')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setRow(p.getRow());
			next.setColumn(p.getColumn()+1);
			if ((next.getColumn() < state.getNumOfColumns())&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'J')||(state.getTOnPoint(next) == '7')||(state.getTOnPoint(next) == '-')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setColumn(p.getColumn());
		}
		if(state.getTOnPoint(p) == '7')
		{
			next.setRow(p.getRow()+1);
			if ((next.getRow() < state.getNumOfRows())&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'L')||(state.getTOnPoint(next) == 'J')||(state.getTOnPoint(next) == '|')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setRow(p.getRow());
			next.setColumn(p.getColumn()-1);
			if ((next.getColumn() >= 0)&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'L')||(state.getTOnPoint(next) == 'F')||(state.getTOnPoint(next) == '-')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setColumn(p.getColumn());
		}
		if(state.getTOnPoint(p) == 'J')
		{
			next.setRow(p.getRow()-1);
			if ((next.getRow() >= 0)&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == '7')||(state.getTOnPoint(next) == 'F')||(state.getTOnPoint(next) == '|')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setRow(p.getRow());
			next.setColumn(p.getColumn()-1);
			if ((next.getColumn() >= 0)&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'L')||(state.getTOnPoint(next) == 'F')||(state.getTOnPoint(next) == '-')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setColumn(p.getColumn());
		}
		if(state.getTOnPoint(p) == 's')
		{
			next.setRow(p.getRow()-1);
			if ((next.getRow() >= 0)&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == '7')||(state.getTOnPoint(next) == 'F')||(state.getTOnPoint(next) == '|')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setRow(p.getRow()+1);
			if ((next.getRow() < state.getNumOfRows())&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'L')||(state.getTOnPoint(next) == 'J')||(state.getTOnPoint(next) == '|')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setRow(p.getRow());
			next.setColumn(p.getColumn()-1);
			if ((next.getColumn() >= 0)&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'L')||(state.getTOnPoint(next) == 'F')||(state.getTOnPoint(next) == '-')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setColumn(p.getColumn()+1);
			if ((next.getColumn() < state.getNumOfColumns())&&(!visited[next.getRow()][next.getColumn()])&&((state.getTOnPoint(next) == 'J')||(state.getTOnPoint(next) == '7')||(state.getTOnPoint(next) == '-')||(state.getTOnPoint(next) == 'g')))
			{
				if(recIsGoal(state,visited,next))
				{
					flag = true;
				}
			}
			next.setColumn(p.getColumn());
		}
		return flag;
	}
	//Return a Queue of all Possible states from a current state (Rotate every cell that contain a pipe).
	public LinkedList<State<Character>> getAllPossibleStates(State<Character> state)
	{
		Point location = new Point();
		LinkedList<State<Character>> lStates = new LinkedList<State<Character>>();
		for(int i = 0;i<state.getNumOfRows();i++)
		{
			location.setRow(i);
			for(int j = 0;j<state.getNumOfColumns();j++)
			{
				location.setColumn(j);
				if(state.getTOnPoint(location) == '|')
				{	
					//checkToAddNewState(state, qStates, i, j,'-');
					lStates.add(new State<Character>(state,location,'-'));
				}
				else if (state.getTOnPoint(location) == '-')
				{
					//checkToAddNewState(state, qStates, i, j,'|');
					lStates.add(new State<Character>(state,location,'|'));
				}
				else if (state.getTOnPoint(location) == 'L')
				{
					//checkToAddNewState(state, qStates, i, j,'F');
					lStates.add(new State<Character>(state,location,'F'));
				}
				else if (state.getTOnPoint(location) == 'F')
				{
					//checkToAddNewState(state, qStates, i, j,'7');
					lStates.add(new State<Character>(state,location,'7'));
				}
				else if (state.getTOnPoint(location) == '7')
				{
					//checkToAddNewState(state, qStates, i, j,'J');
					lStates.add(new State<Character>(state,location,'J'));
				}
				else if (state.getTOnPoint(location) == 'J')
				{
					//checkToAddNewState(state, qStates, i, j,'L');
					lStates.add(new State<Character>(state,location,'L'));
				}
			}
		}
		return lStates;
	}
	// Check if hash map contain the state already, if not add it
	/*private void checkToAddNewState(State<Character> state, Queue<State<Character>> qStates, int i, int j,char c)
	{
		State<Character> tempState = new State<Character>(state,new Point(i,j),c);
		if(!map.containsKey(this.getStateAsString(tempState)))
		{
			qStates.add(tempState);
		}
	}*/	

	//Return for every cell in matrix the maximum number of rotations (Using for normalizing). 
	public int maxNumOfOptions(Point p)
	{
		if ((this.matrix[p.getRow()][p.getColumn()] == '7')||(this.matrix[p.getRow()][p.getColumn()] == 'J')||(this.matrix[p.getRow()][p.getColumn()] == 'L')||(this.matrix[p.getRow()][p.getColumn()] == 'F'))
		{
			return 4;
		}
		else if ((this.matrix[p.getRow()][p.getColumn()] == '|')||(this.matrix[p.getRow()][p.getColumn()] == '-'))
		{
			return 2;
		}
		else return 0;
	}
	public static void main(String[] args)
	{
		String[] str = new String[4];
		str[0] = "s   L";
		str[1] = "7|||L";
		str[2] = " --FL";
		str[3] = "g-|F|";
		PipeGameSolver pipe1 = new PipeGameSolver(new BFS());
		System.out.println(pipe1.solve(str));
		PipeGameSolver pipe2 = new PipeGameSolver(new DFS());
		System.out.println(pipe2.solve(str));
		PipeGameSolver pipe3 = new PipeGameSolver(new BestFirstSearch());
		System.out.println(pipe3.solve(str));
		/*System.out.println(str[0]);
		System.out.println(str[1]);
		System.out.println(str[2]);
		System.out.println(str[3]);
		Pipe pipe = new Pipe(str);
		BFS bfs = new BFS();
		System.out.println(bfs.search(pipe));
		System.out.println("end!");
		DFS dfs = new DFS();
		System.out.println(dfs.search(pipe));
		System.out.println("end!");*/
	}
}