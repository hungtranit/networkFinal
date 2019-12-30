package networkFinal.tcpsendRecieveFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

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

	private void analysis(String line) throws IOException {
		StringTokenizer st = new StringTokenizer(line);
		String command = st.nextToken();
		String sf, df;
		switch (command) {
		case "SET_SERVER_DIR":
			netOut.writeUTF(line);
			break;
		case "SET_CLIENT_DIR":
			try {
				clientDir = st.nextToken();
			} catch (Exception e) {
				throw new NoSuchElementException("Bạn đã nhập thiếu đường dẫn!");
			}
			break;
		case "SEND":
			try {
				sf = st.nextToken();
				df = st.nextToken();
			} catch (Exception e) {
				throw new NoSuchElementException("Nhập thiếu kìa bạn gì gì ơi!");
			}
			netOut.writeUTF(command + " " + df);
			sendFile(sf);
			break;
		case "GET":
			try {
				sf = st.nextToken();
				df = st.nextToken();
			} catch (Exception e) {
				throw new NoSuchElementException("Nhập thiếu kìa bạn gì gì ơi!");
			}
			netOut.writeUTF(command + " " + sf);
			netOut.flush();
			recivieFile(df);
			break;
		case "QUIT":
			netOut.writeUTF(line);
			break;

		default:
			break;
		}
		netOut.flush();
	}

	private void recivieFile(String df) throws IOException {
		File file = new File(clientDir + File.separator + df);
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		long size = netIn.readLong();
		int byteRead, byteMustRead;
		long remain = size;

		byte[] buff = new byte[10 * 1024];

		while (remain > 0) {
			byteMustRead = buff.length > remain ? (int) remain : buff.length;
			byteRead = netIn.read(buff, 0, byteMustRead);
			out.write(buff);
			remain -= byteRead;
		}
		out.close();
	}

	private void sendFile(String sf) throws IOException {
		File file = new File(sf);
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		netOut.writeLong(file.length());
		byte[] buff = new byte[10 * 1024];
		int data;
		while ((data = in.read(buff)) != -1) {
			netOut.write(buff, 0, data);
		}
		netOut.flush();
		in.close();
	}

	public static void main(String[] args) {
		FileClient fc = new FileClient();
		fc.request();
	}
}
