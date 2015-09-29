package game;

import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ClientDB {
	LinkedList<String> usernames;
	LinkedList<String> passwords;

	public ClientDB() {

		usernames = new LinkedList<String>();
		passwords = new LinkedList<String>();
		startDb();
	}

	private void startDb() {

		usernames.add("admin");
		passwords.add("admin");

		usernames.add("user");
		passwords.add("user");

		usernames.add("user2");
		passwords.add("123");

		usernames.add("user3");
		passwords.add("123");
	}

	private boolean checkLogin(String user, String password) {
		for (int i = 0; i < usernames.size(); i++) {
			if (user.equals(usernames.get(i)) && password.equals(passwords.get(i)))
				return true;
		}
		return false;
	}

	public boolean tryLogin(String user, String password) {

		if (checkLogin(user, password)) {
			System.out.println("Login successful");
			return true;
		}else {
			JOptionPane.showMessageDialog(null, "login failed");
			return false;
		}
	}

}
