package NewPipeGame;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public interface IClientHandler {
	void handleClient(InputStream in, OutputStream out);
	boolean handleProblem();
	IClientHandler GetNewInstance();
	int getProblemSize();
	int getProblemRow();
	int getProblemCol();
	boolean isWorking();
}
