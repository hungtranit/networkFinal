package networkFinal.tcpsendRecieveFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.NoSuchElementException;

public class FileClient {

	public static final String HOST = "127.0.0.1";
	public static final int PORT = 12345;

	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	String clientDir;
	BufferedReader userIn;

	public FileClient() {
		// duong dan den file
		clientDir = "";
	}

	public void request() {
		try {
			this.socket = new Socket(HOST, PORT);
			netIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			netOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			userIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Xin chào!");
			System.out.println("Vui lòng nhập yêu cầu của bạn!");
			String line;
			// vòng lặp vô tận để nhận request của client
			while (true) {
				try {
					// đọc lệnh từ người dùng
					line = userIn.readLine();
					// hàm này để phân tích lệnh
					analysis(line);
					if ("QUIT".equalsIgnoreCase(line)) {
						System.out.println("See you again!");
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NoSuchElementException e) {
					System.out.println(e.getMessage());
				}
			}
			netIn.close();
			netOut.flush();
			netOut.close();
			userIn.close();
			socket.close();
		} catch (Exception e) {

		}
	}
}
