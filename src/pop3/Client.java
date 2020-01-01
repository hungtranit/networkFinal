package pop3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 123456);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			PrintWriter out = new PrintWriter(System.out, true);
			String command;

			while (true) {
				command = in.readLine();
				pw.println(command);
				if (command.equalsIgnoreCase("exist"))
					break;
				command = br.readLine();
				int count = Integer.parseInt(command);
				for (int i = 0; i < count; i++) {
					command = br.readLine();
				}
			}
			in.close();
			out.close();
			pw.close();
			br.close();
			socket.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
