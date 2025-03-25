package model;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import store.MusicStore;

public class User {
	private String username;
	private byte[] salt;
	private String hashedPassword;
	private LibraryModel library;

	public User(String username, String password, MusicStore store) {
		this.username = username;
		this.salt = generateSalt();
		this.hashedPassword = hashPassword(password, salt);
		this.library = new LibraryModel(store); // Pass MusicStore to LibraryModel
	}

	private byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}

	public String hashPassword(String password, byte[] salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt);
			byte[] hashedBytes = md.digest(password.getBytes());
			return Base64.getEncoder().encodeToString(hashedBytes);
		} catch (Exception e) {
			throw new RuntimeException("Error hashing password", e);
		}
	}

	// Getters
	public String getUsername() {
		return username;
	}

	public byte[] getSalt() {
		return salt;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public LibraryModel getLibrary() {
		return library;
	}
}