package NewPipeGame;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server implements IServer{
	
	private IClientHandler base_ch;
	private int port;
	private volatile boolean stop;

	private ServerSocket server_socket;
	private Socket client_socket;
	private InputStream in_stream;
	private OutputStream out_stream;
	private BlockingQueue<Runnable> pbq_clientProblemHandler;
	
	private Thread newClientsListener; // Listen and accept new clients, transfer them to handleAccepetedClient
	private ThreadPoolExecutor threadPool;
	private final int MAX_T = 2;
	
	public Server(int port, IClientHandler ch) {
		this.base_ch = ch;
		this.port = port;
		this.stop = false;
		this.pbq_clientProblemHandler = new PriorityBlockingQueue<Runnable>();
		this.threadPool = new ThreadPoolExecutor(MAX_T, MAX_T, 1, TimeUnit.MINUTES, pbq_clientProblemHandler);
	}
	
	@Override
	public void OpenListener(ServerSocket server_socket, Socket client_socket, InputStream in_stream,
			OutputStream out_stream) {
		try {
			server_socket = new ServerSocket(this.port);
			server_socket.setSoTimeout(800);
			while (!stop)
			{
				try {
				
				client_socket = server_socket.accept(); // blocking call
				
				IClientHandler new_ch = base_ch.GetNewInstance();
				handleAccpetedClient(new_ch, client_socket); // open new thread
				
				} catch (SocketTimeoutException e) {}
			}

			CloseThreads();
			client_socket.close();
			server_socket.close();
		}
		catch (IOException e) {}
		
	}
	
	public void handleAccpetedClient(IClientHandler new_ch, Socket clientSock)
	{
		Thread t = new Thread() {
			@Override
				public void run() {
					boolean stopHandleSpesificClient = false;
	            	while (!stopHandleSpesificClient)
					{
						try {
							new_ch.handleClient(clientSock.getInputStream(), clientSock.getOutputStream());
							int size = new_ch.getProblemSize();
							threadPool.execute(new PriorityBySizeRunnable(size)
									{
										@Override
										public void run() {
											new_ch.handleProblem();
										}
									});
							
							if (!new_ch.isWorking())
							{
								stopHandleSpesificClient = true;
							}
						} catch (SocketException  e) {
						} catch (IOException e) {
						}
					}
	            	stopHandleSpesificClient = false;
				}
		};
		t.start();
	}
	
	public void start(){
		newClientsListener = new Thread()
        {
            @Override
            public void run()
            {
            	OpenListener(server_socket, client_socket, in_stream, out_stream);
            }
        };
        newClientsListener.start();
	}
	
	public void stop() {
		this.stop = true;

		System.out.println("Server was shutdown and could not accept new client problems.");
	}
	
	private void CloseThreads()
	{
		try {
			// Close the thread pool while executing the remaining task problems.
			threadPool.shutdown();
			threadPool.awaitTermination(5, TimeUnit.MINUTES);
		}
		catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Server was shutdown and could not accept new client problems.");
		}
	}
	
}
