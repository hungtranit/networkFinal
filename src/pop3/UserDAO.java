package pop3;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	List<User> listUser;

	public UserDAO() {
		listUser = new ArrayList<>();
	}

	public void createData() {
		listUser.add(new User("admin", "@123"));
		listUser.add(new User("minh", "123123"));
		listUser.add(new User("thien", "123123"));
		listUser.add(new User("bong", "123123"));
	}

	public boolean checkUserName(String userName) {
		for (User user : listUser) {
			if (userName.equalsIgnoreCase(user.getUserName()))
				return true;
		}
		return false;
	}

	public boolean login(String userName, String password) {
		for (User user : listUser) {
			if (userName.equalsIgnoreCase(user.getUserName()) && password.equalsIgnoreCase(user.getPassword()))
				return true;
		}
		return false;
	}

}
