package NewPipeGame;

public class Point {
	private int row;
	private int col;
	//Default CTOR.
	public Point()
	{
		this.row = -1;
		this.col = -1;
	}
	// CTOR by index.
	public Point(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	// CTOR by index.
	public Point(Point p)
	{
		this.row = p.getRow();
		this.col = p.getColumn();
	}
	//Get row.
	public int getRow()
	{
		return this.row;
	}
	//Get Column.
	public int getColumn()
	{
		return this.col;
	}
	//Set row.
	public void setRow(int row)
	{
		this.row = row;
	}
	//Set column.
	public void setColumn(int col)
	{
		this.col = col;
	}
	@Override
	public String toString()
	{
		return this.row+""+this.col;
	}
}