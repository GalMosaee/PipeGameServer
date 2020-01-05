package NewPipeGame;

public abstract class PriorityBySizeRunnable implements Comparable<PriorityBySizeRunnable>, Runnable {
	int priority;
	
	@Override
	public int compareTo(PriorityBySizeRunnable o) {
		return this.priority - o.priority;
	}

	public PriorityBySizeRunnable(int priority){
		this.priority = priority;
	}

}