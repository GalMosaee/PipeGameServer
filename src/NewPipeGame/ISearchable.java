package NewPipeGame;

import java.util.*;

public interface ISearchable<T> {
	public State<T> initState();
	public boolean isGoal(State<T> state);
	public LinkedList<State<T>> getAllPossibleStates(State<T> state);
	public String getStateAsString(State<T> state);
	public int maxNumOfOptions(Point p);
}
