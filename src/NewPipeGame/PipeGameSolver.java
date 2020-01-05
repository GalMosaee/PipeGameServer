package NewPipeGame;

import java.util.*;

public class PipeGameSolver<T> implements ISolver<T> {
	private ISearcher<T> searcher;
	//Default CTOR
	public PipeGameSolver(ISearcher searcher)
	{
		this.searcher = searcher;
	}
	//Get the Searcher.
	public ISearcher<T> getSearcher()
	{
		return this.searcher;
	}
	@Override
	//Solve the problem by the chosen searcher.
	public Solution solve(String[] problem)
	{
		Pipe pipe = new Pipe(problem);
		return this.searcher.search((ISearchable)pipe);
	}
}
