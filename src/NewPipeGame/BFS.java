package NewPipeGame;

import java.util.*;

public class BFS<T> implements ISearcher<T>
{
	//Search method by BFS (using Queue).
	public Solution search(ISearchable<T> searchable)
	{
		Queue<State<T>> queue = new LinkedList<State<T>>();
		HashMap<String,State<T>> checked = new HashMap<String,State<T>>();
		State<T> state = searchable.initState();
		queue.add(state);
		checked.put(searchable.getStateAsString(state), state);
		Solution sol = new Solution();
		while(!queue.isEmpty())
		{
			state = queue.remove();
			if(searchable.isGoal(state))
			{
				while(state.getPrevState() != null)
				{
					sol.addStep(state.getPoint());
					state = state.getPrevState();
				}
				return sol;
			}
			for(State<T> item : searchable.getAllPossibleStates(state))
			{					
				if(!checked.containsKey(searchable.getStateAsString(item)))
				{
					queue.add(item);
					checked.put(searchable.getStateAsString(item), item);
				}
			}
		}
		return sol;
	}
}


/*
//Old Search method by BFS (using Queue).
	public Solution search(ISearchable<T> searchable)
	{
		Queue<State<T>> queue = new LinkedList<State<T>>();
		HashMap<String,State<T>> checked = new HashMap<String,State<T>>();
		queue.add(searchable.initState());
		State<T> state;
		String stateAsString;
		Solution sol = new Solution();
		while(!queue.isEmpty())
		{
			state = queue.remove();
			stateAsString = searchable.getStateAsString(state);
			if(!checked.containsKey(stateAsString))
			{
				checked.put(stateAsString, state);
				if(searchable.isGoal(state))
				{
					while(state.getPrevState() != null)
					{
						sol.addStep(state.getPoint());
						state = state.getPrevState();
					}
					return sol;
				}
				for(State<T> item : searchable.getAllPossibleStates(state))
				{
					if(!checked.containsKey(searchable.getStateAsString(item)))
					{
						queue.add(item);
					}
				}
			}
		}
		return sol;
	}
*/