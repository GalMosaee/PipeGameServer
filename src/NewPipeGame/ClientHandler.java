package NewPipeGame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.rowset.CachedRowSet;


public class ClientHandler implements IClientHandler {

	private ICacheManager cache;
	private ISolver solver;
	private BufferedReader out;
	private BufferedReader outConsole;
	private PrintWriter in;
	private PrintWriter inConsole;
	private volatile boolean stop;
	private ArrayList<String> problem;
	private boolean hasProblem;
	private String problemString;
	
	public ClientHandler(ISolver solver, ICacheManager cache) {
		this.stop = false;
		this.solver = solver;
		this.cache = cache;
		this.problem = new ArrayList<String>();
		this.hasProblem = false;
	}
	
	public IClientHandler GetNewInstance()
	{
		return new ClientHandler(this.solver, this.cache);
	}

	public int getProblemRow()
	{
		return this.problem.size();
	}
	
	public int getProblemCol()
	{
		return this.problem.get(0).length();
	}
	
	private void SendSolutionMessage(ArrayList<String> s)
	{
		for (String row : s)
		{
			in.println(row.replace("/r", "").replace("/n", ""));
		}
		in.println("done");
	}
	
	@Override
	public boolean isWorking() {
		return !stop;
	}
	
	public int getProblemSize()
	{
		if (!hasProblem)
			return 0;
		return this.problem.size()*this.problem.get(0).length();
	}
	public boolean handleProblem()
	{
		int row = getProblemRow();
		int col = getProblemCol();
		try {
			ArrayList<String> cacheSolution = cache.load(this.problemString);
			
			if (cacheSolution != null)
			{
				//System.out.println("cache loaded a solution successfully!");
				// print to server console
				//System.out.println(cacheSolution);
				// send the solution to the client
				SendSolutionMessage(cacheSolution);
			}
			else
			{
				// solve the problem
				Solution sol = solver.solve(Normalizer.stringToStringsArray(row, col, this.problemString));
				// print to server console
				//System.out.println(sol);
				// send the solution to the client
				SendSolutionMessage(sol.getArraySolution());
				// save  solution created  into new cache file
				cache.Save(String.format("%s%s%s", this.problemString, System.lineSeparator(), sol.toString()));		
			}
			return true;
		}
		catch(Exception e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		this.stop = false;
		while (!stop) {
			try {
				out = new BufferedReader(new InputStreamReader(inFromClient));
				//outConsole = new BufferedReader(new InputStreamReader(System.in));
				in = new PrintWriter(outToClient, true);
				//printWriterConsole = new PrintWriter(System.out, true);
				
				try {
					String line = out.readLine();
					if (line == null)
					{
						this.stop = true;
						break;
					}
					
					// Start timing
					while (line != "" && !("done").equalsIgnoreCase(line.toLowerCase())) {
						this.problem.add(line);
						line = "";
						line = out.readLine();
					}
					// Check if we got a full problem
					if (line.toLowerCase().contains("done"))
					{
						String problemStr = "";
						for (String str : this.problem)
						{
							problemStr += str;
						}
						// Some check
						if (problemStr.length()/this.problem.size() == this.problem.get(0).length())
						{
							this.problemString = problemStr;
							hasProblem = true;
							//handleProblem(this.problem.size(), this.problem.get(0).length(), problemStr);
							//this.problem = new ArrayList<String>();
							this.stop = true; //TODO: CHECK IF NEEDED
						}
						line = "";
					}
				} catch(SocketException e){
					stop = true;
					//System.out.println("client socket is closed, could not get new data.");
				}
			} catch (IOException e) {
				this.stop = true;
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
}

