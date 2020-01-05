package NewPipeGame;

import java.util.*;
public class BestFirstSearch<T> implements ISearcher<T> {
	//Search method by Best First Search (using Priority Queue).
	//Heuristic function: distance from the start point.
	public Solution search(ISearchable<T> searchable)
	{
		PriorityQueue<State<T>> pQueue = new PriorityQueue<State<T>>();
		HashMap<String,State<T>> checked = new HashMap<String,State<T>>();
		State<T> state = searchable.initState();
		pQueue.add(state);
		checked.put(searchable.getStateAsString(state), state);
		Solution sol = new Solution();
		while(!pQueue.isEmpty())
		{
			state = pQueue.remove();
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
					//Set the cost by the distance from the start point.
					item.setCost(Math.abs(item.getPoint().getRow()-item.getStartPoint().getRow())+Math.abs(item.getPoint().getColumn()-item.getStartPoint().getColumn()));
					pQueue.add(item);
					checked.put(searchable.getStateAsString(item), item);
				}
			}
		
		}
		return sol;
	}
}

/*
// Old Search method by Best First Search (using Queue).
public Solution search(ISearchable<T> searchable)
{
	PriorityQueue<State<T>> pQueue = new PriorityQueue<State<T>>();
	HashMap<String,State<T>> checked = new HashMap<String,State<T>>();
	pQueue.add(searchable.initState());
	State<T> state;
	Solution sol = new Solution();
	while(!pQueue.isEmpty())
	{
		state = pQueue.remove();
		String stateAsString = searchable.getStateAsString(state);
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
					pQueue.add(item);
				}
			}
		}
	}
	return sol;
}*/