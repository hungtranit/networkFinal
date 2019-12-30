package networkFinal.tcpsendRecieveFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class FileServerProcessing extends Thread {

	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	String serverDir;

	public FileServerProcessing(Socket socket) {
		try {
			this.socket = socket;
			netIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			netOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void run() {
		try {
			String line;
			while (true) {
				line = netIn.readUTF();
				if (line.equalsIgnoreCase("QUIT"))
					break;
				analysis(line);
			}
			netIn.close();
			netOut.flush();
			netOut.close();
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void analysis(String line) throws IOException {
		StringTokenizer st = new StringTokenizer(line);
		String sf, df;
		String command = st.nextToken();
		switch (command) {
		case "SET_SERVER_DIR":
			serverDir = st.nextToken();
			break;
		case "SEND":
			df = st.nextToken();
			recivieFile(df);
			break;
		case "GET":
			sf = st.nextToken();
			sendFile(sf);
			break;

		default:
			break;
		}
	}

	private void recivieFile(String df) throws IOException {
		File file = new File(serverDir + File.separator + df);
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

}
