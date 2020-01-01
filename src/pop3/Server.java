package pop3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(123456);
		while (true) {
			Socket socket = ss.accept();
			ServerPop3 serverProc = new ServerPop3(socket);
			serverProc.start();
		}
	}
}
