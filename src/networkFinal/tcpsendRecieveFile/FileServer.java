package networkFinal.tcpsendRecieveFile;

import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

	ServerSocket serverSocket;
	public static final int PORT = 12345;

	public FileServer() {
	}

	public void start() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					FileServerProcessing fsp = new FileServerProcessing(socket);
					fsp.start();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		FileServer fileServer = new FileServer();
		fileServer.start();
	}
}
