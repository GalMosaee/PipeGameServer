import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import NewPipeGame.FileCacheManager;
import NewPipeGame.ClientHandler;
import NewPipeGame.*;
import NewPipeGame.PipeGameSolver;
import NewPipeGame.Server;

public class Main {

	public static void main(String[] args) {
		Server server = new Server(1234, new ClientHandler(new PipeGameSolver(new BFS()), new FileCacheManager("\\cache")));
		server.start();		
	}

}
