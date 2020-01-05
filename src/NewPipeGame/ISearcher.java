package NewPipeGame;

public interface ISearcher<T> {
	public Solution search(ISearchable<T> searchable);
}