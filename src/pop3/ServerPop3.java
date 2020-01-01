package pop3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerPop3 extends Thread {
	Socket socket;
	BufferedReader netIn;
	PrintWriter netOut;
	StudentDAO stDao;
	UserDAO uDao;
	String userName;

	public ServerPop3(Socket socket) throws IOException {
		this.socket = socket;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(socket.getOutputStream());
		this.stDao = new StudentDAO();
		this.uDao = new UserDAO();
		stDao.createStudentList();
		uDao.createData();
		netOut.println("[SERVER] Welcome!");
	}

	@Override
	public void run() {
		String command = null;
		try {
			while (true) {
				command = netIn.readLine();
				if (command.equalsIgnoreCase("quit")) {
					break;
				} else {
					if (command.indexOf("user: ") == 0) {
						String tempUserName = command.substring(6, command.length());
						boolean exists = checkUserName(tempUserName);
						if (exists) {
							netOut.println("[SERVER] '" + tempUserName
									+ "' ton tai. Co the nhap lai username hoac tiep tuc nhap mat khau.");
							userName = tempUserName;
						} else {
							netOut.println("[SERVER] Ten dang nhap '" + tempUserName + "' khong ton tai");
							userName = null;
						}
					} else {
						if (command.indexOf("pass: ") == 0) {
							String pass = command.substring(6, command.length());
							boolean lg = login(pass);
							if (lg) {
								netOut.println("[SERVER] Dang nhap duoi ten '" + userName + "' thanh cong!");
							} else {
								netOut.println("[SERVER] Dang nhap that bai!");
							}
						} else {
							netOut.println("[SERVER] Unknow your command!");
						}
					}
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean checkUserName(String username) {
		return uDao.checkUserName(username);
	}

	private boolean login(String pass) {
		if (userName == null) {
			return false;
		} else {
			return uDao.login(userName, pass);
		}
	}
}
