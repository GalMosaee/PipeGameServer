package NewPipeGame;

import java.util.*;
public class DFS<T> implements ISearcher<T>
{
	//Search method by DFS (using Stack).
	public Solution search(ISearchable<T> searchable)
	{
		Stack<State<T>> stack = new Stack<State<T>>();
		HashMap<String,State<T>> checked = new HashMap<String,State<T>>();
		State<T> state = searchable.initState();
		stack.add(state);
		checked.put(searchable.getStateAsString(state), state);
		Solution sol = new Solution();
		while(!stack.isEmpty())
		{
			state = stack.pop();
			if(searchable.isGoal(state))
			{
				while(state.getPrevState() != null)
				{
					sol.addStep(state.getPoint());
					state = state.getPrevState();
				}
				sol.normalizeSolution(searchable);
				return sol;
			}
			for(State<T> item : searchable.getAllPossibleStates(state))
			{
				if(!checked.containsKey(searchable.getStateAsString(item)))
				{
					stack.add(item);
					checked.put(searchable.getStateAsString(item), item);
				}
			}
		}
		return sol;
	}
}

/*
//Old Search method by DFS (using Stack).
public Solution search(ISearchable<T> searchable)
{
	Stack<State<T>> stack = new Stack<State<T>>();
	HashMap<String,State<T>> checked = new HashMap<String,State<T>>();
	stack.add(searchable.initState());
	State<T> state;
	String stateAsString;
	Solution sol = new Solution();
	while(!stack.isEmpty())
	{
		state = stack.pop();
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
				sol.normalizeSolution(searchable);
				return sol;
			}
			for(State<T> item : searchable.getAllPossibleStates(state))
			{
				if(!checked.containsKey(searchable.getStateAsString(item)))
				{
					stack.add(item);
				}
			}
		}
	}
	return sol;
}
*/