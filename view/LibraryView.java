package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Album;
import model.LibraryModel;
import model.Song;
import model.User;
import model.UserManager;
import store.MusicStore;

public class LibraryView {
	private UserManager userManager;
	private MusicStore store;
	private Scanner scanner;
	private User currentUser;

	public LibraryView(UserManager userManager, MusicStore store) {
		this.userManager = userManager;
		this.store = store;
		this.scanner = new Scanner(System.in);
	}

	public void start() {
		System.out.println("1. Login\n2. Create Account");
		int choice = scanner.nextInt();
		scanner.nextLine();

		if (choice == 1) {
			login();
		} else if (choice == 2) {
			createAccount();
		}

		if (currentUser != null) {
			runMainMenu();
		}
	}

	private void login() {
		System.out.print("Username: ");
		String username = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();

		if (userManager.authenticate(username, password)) {
			currentUser = userManager.getUser(username);
			System.out.println("Login successful!");
		} else {
			System.out.println("Invalid credentials.");
		}
	}

	private void createAccount() {
		System.out.print("New username: ");
		String username = scanner.nextLine();
		System.out.print("New password: ");
		String password = scanner.nextLine();
		userManager.createUser(username, password, store);
		currentUser = userManager.getUser(username);
		System.out.println("Account created!");
	}

	private void runMainMenu() {
		LibraryModel model = currentUser.getLibrary();
		while (true) {
			System.out.println(
					"\n1. Search\n2. Add Song\n3. Add Album\n4. Play Song\n5. Remove Item\n6. View Playlists\n7. Logout");
			int choice = scanner.nextInt();
			scanner.nextLine();

			try {
				switch (choice) {
				case 1 -> searchMenu(model);
				case 2 -> addSongMenu(model);
				case 3 -> addAlbumMenu(model);
				case 4 -> playSongMenu(model);
				case 5 -> removeItemMenu(model);
				case 6 -> playlistMenu(model);
				case 7 -> {
					userManager.saveUsers();
					System.out.println("Logged out.");
					start(); // Return to login menu after logout
					return;
				}
				default -> System.out.println("Invalid option.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Try again.");
				scanner.nextLine();
			}
		}
	}

	// 1. Search Menu
	private void searchMenu(LibraryModel model) {
		System.out.println("Search for:");
		System.out.println("1. Song by Title\n2. Song by Artist\n3. Album by Title\n4. Album by Artist");
		int choice = scanner.nextInt();
		scanner.nextLine();

		System.out.print("Enter search term: ");
		String term = scanner.nextLine();

		switch (choice) {
		case 1 -> {
			List<Song> results = model.searchSongsByTitle(term);
			displaySongs(results);
		}
		case 2 -> {
			List<Song> results = model.searchSongsByArtist(term);
			displaySongs(results);
		}
		case 3 -> {
			List<Album> results = model.searchAlbumsByTitle(term);
			displayAlbums(results);
		}
		case 4 -> {
			List<Album> results = model.searchAlbumsByArtist(term);
			displayAlbums(results);
		}
		default -> System.out.println("Invalid option.");
		}
	}

	// 2. Add Song Menu
	private void addSongMenu(LibraryModel model) {
		System.out.print("Enter song title to add: ");
		String title = scanner.nextLine();
		if (model.addSongFromStore(title)) {
			System.out.println("Song added!");
		} else {
			System.out.println("Song not found in store.");
		}
	}

	// 3. Add Album Menu
	private void addAlbumMenu(LibraryModel model) {
		System.out.print("Enter album title to add: ");
		String title = scanner.nextLine();
		if (model.addAlbumFromStore(title)) {
			System.out.println("Album added!");
		} else {
			System.out.println("Album not found in store.");
		}
	}

	// 4. Play Song Menu
	private void playSongMenu(LibraryModel model) {
		System.out.print("Enter song title to play: ");
		String title = scanner.nextLine();
		List<Song> results = model.searchSongsByTitle(title);
		if (!results.isEmpty()) {
			model.playSong(results.get(0));
			System.out.println("Now playing: " + results.get(0).getTitle());
		} else {
			System.out.println("Song not found.");
		}
	}

	// 5. Remove Item Menu
	private void removeItemMenu(LibraryModel model) {
		System.out.println("1. Remove Song\n2. Remove Album");
		int choice = scanner.nextInt();
		scanner.nextLine();

		System.out.print("Enter title to remove: ");
		String title = scanner.nextLine();

		switch (choice) {
		case 1 -> {
			Song song = findSongByTitle(model, title);
			if (song != null && model.removeSong(song)) {
				System.out.println("Song removed.");
			} else {
				System.out.println("Song not found.");
			}
		}
		case 2 -> {
			Album album = findAlbumByTitle(model, title);
			if (album != null && model.removeAlbum(album)) {
				System.out.println("Album removed.");
			} else {
				System.out.println("Album not found.");
			}
		}
		default -> System.out.println("Invalid option.");
		}
	}

	// 6. Playlist Menu
	private void playlistMenu(LibraryModel model) {
		System.out.println("1. Recent Plays\n2. Frequent Plays\n3. Genre Playlists");
		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1 -> displaySongs(model.getRecentPlays(), "Recent Plays");
		case 2 -> displaySongs(model.getFrequentPlays(), "Frequent Plays");
		case 3 -> {
			Map<String, List<Song>> genrePlaylists = model.getGenrePlaylists();
			genrePlaylists.forEach((genre, songs) -> {
				System.out.println("Genre: " + genre);
				displaySongs(songs);
			});
		}
		default -> System.out.println("Invalid option.");
		}
	}

	// Helper Methods
	private Song findSongByTitle(LibraryModel model, String title) {
		List<Song> results = model.searchSongsByTitle(title);
		return results.isEmpty() ? null : results.get(0);
	}

	private Album findAlbumByTitle(LibraryModel model, String title) {
		List<Album> results = model.searchAlbumsByTitle(title);
		return results.isEmpty() ? null : results.get(0);
	}

	private void displaySongs(List<Song> songs) {
		if (songs.isEmpty()) {
			System.out.println("No songs found.");
			return;
		}
		for (Song song : songs) {
			System.out.println("Title: " + song.getTitle() + "\nArtist: " + song.getArtist() + "\nAlbum: "
					+ song.getAlbumTitle() + "\nRating: " + song.getRating() + "\n--------------------");
		}
	}

	private void displaySongs(List<Song> songs, String playlistName) {
		System.out.println("Playlist: " + playlistName);
		displaySongs(songs);
	}

	private void displayAlbums(List<Album> albums) {
		if (albums.isEmpty()) {
			System.out.println("No albums found.");
			return;
		}
		for (Album album : albums) {
			System.out.println("Album: " + album.getTitle() + "\nArtist: " + album.getArtist() + "\nGenre: "
					+ album.getGenre() + "\nYear: " + album.getYear() + "\nSongs:\n--------------------");
			for (Song song : album.getSongs()) {
				System.out.println("- " + song.getTitle());
			}
			System.out.println("--------------------");
		}
	}
}