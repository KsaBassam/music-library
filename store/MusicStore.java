/**
 * @author Bassam
 * @author Joshua
 * 
 * Simulates the music store database for LA#1/LA#2.
 * - Loads albums from text files.
 * - Provides search functionality for songs/albums.
 */
package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Album;
import model.Song;

public class MusicStore {
	private List<Album> albums;

	public MusicStore() {
		albums = new ArrayList<>();
		loadAlbums();
	}

	private void loadAlbums() {
		try (BufferedReader br = new BufferedReader(new FileReader("albums.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				String albumTitle = parts[0].trim();
				String artist = parts[1].trim();
				loadAlbumFile(albumTitle, artist);
			}
		} catch (IOException e) {
			System.err.println("Error loading albums.txt");
		}
	}

	private void loadAlbumFile(String albumTitle, String artist) {
		String filename = albumTitle + "_" + artist + ".txt";
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String header = br.readLine();
			if (header == null || header.split(",").length < 4) {
				System.err.println("Invalid header in file: " + filename);
				return;
			}

			String[] headerParts = header.split(",");
			String genre = headerParts[2].trim();
			int year = Integer.parseInt(headerParts[3].trim());

			Album album = new Album(headerParts[0].trim(), headerParts[1].trim(), genre, year);
			String songTitle;
			while ((songTitle = br.readLine()) != null) {
				if (!songTitle.trim().isEmpty()) {
					Song song = new Song(songTitle.trim(), artist, albumTitle);
					album.addSong(song);
				}
			}
			albums.add(album);
		} catch (IOException | NumberFormatException e) {
			System.err.println("Error loading album file: " + filename);
		}
	}

	public void addAlbum(Album album) {
	    this.albums.add(album);
	}
	
	public Album getAlbum(String title) {
		for (Album album : albums) {
			if (album.getTitle().equalsIgnoreCase(title)) {
				return album;
			}
		}
		return null;
	}

	public List<Album> getAlbums() {
		return new ArrayList<>(albums); // Defensive copy
	}
}
