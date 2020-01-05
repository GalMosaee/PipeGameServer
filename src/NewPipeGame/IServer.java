package NewPipeGame;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public interface IServer {
	void OpenListener(ServerSocket server_socket, Socket client_socket, InputStream in_stream, 
			OutputStream out_stream) throws Exception;
	
}
