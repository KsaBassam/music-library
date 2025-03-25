package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import store.MusicStore;

public class UserManager {
	private Map<String, User> users;
	private File userDatabase;

	public UserManager(MusicStore store) {
		this.userDatabase = new File("users.dat");
		this.users = new HashMap<>();
		loadUsers(store); // Load users with MusicStore dependency
	}

	private void loadUsers(MusicStore store) {
		if (!userDatabase.exists()) {
			return;
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userDatabase))) {
			users = (Map<String, User>) ois.readObject();
			// Reinitialize libraries with the MusicStore
			for (User user : users.values()) {
				user.getLibrary().setStore(store); // Requires a setter in LibraryModel
			}
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error loading user data. Starting fresh.");
			users = new HashMap<>();
		}
	}

	public void saveUsers() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userDatabase))) {
			oos.writeObject(users);
		} catch (IOException e) {
			System.err.println("Error saving user data.");
		}
	}

	public boolean authenticate(String username, String password) {
		User user = users.get(username);
		if (user == null) {
			return false;
		}
		String inputHash = user.hashPassword(password, user.getSalt());
		return inputHash.equals(user.getHashedPassword());
	}

	public void createUser(String username, String password, MusicStore store) {
		User newUser = new User(username, password, store);
		users.put(username, newUser);
		saveUsers();
	}

	public User getUser(String username) {
		return users.get(username);
	}
}