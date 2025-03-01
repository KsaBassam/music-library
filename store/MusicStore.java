/**
 * @file:    MusicStore.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: The MusicStore class handles loading of albums and their associated songs
 *           from text files and provides methods to access them. It allows retrieval
 *           of individual albums or entire list of albums.
 */

package store;

import model.Album;
import model.Song;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicStore {
    // List of albums available in music store
    private List<Album> albums;

    /**
     * Constructor to initialize MusicStore and load albums from text files.
     */
    public MusicStore() {
        albums = new ArrayList<>();
        loadAlbums();
    }

    /**
     * Loads album data from "albums.txt" file, which contains album titles and artist names.
     * 
     * Each album is then loaded from its respective text file.
     */
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
            e.printStackTrace();
        }
    }

    /**
     * Loads an album's songs from a file named "<albumTitle>_<artist>.txt".
     * 
     * First line contains album metadata (title, artist, genre, year),
     * and remaining lines contain song titles.
     * 
     * @param albumTitle: Title of album.
     * @param artist:     Artist who created album.
     */
    private void loadAlbumFile(String albumTitle, String artist) {
        String filename = albumTitle + "_" + artist + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String header = br.readLine();
            String[] headerParts = header.split(",");

            String genre = headerParts[2].trim();
            int year = Integer.parseInt(headerParts[3].trim());

            Album album = new Album(albumTitle, artist, genre, year);

            String songTitle;
            while ((songTitle = br.readLine()) != null) {
                Song song = new Song(songTitle.trim(), artist, albumTitle);
                album.addSong(song);
            }

            albums.add(album);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an album by title.
     * 
     * @param title: Title of album to retrieve.
     * @return Album object if found, null otherwise.
     */
    public Album getAlbum(String title) {
        for (Album album : albums) {
            if (album.getTitle().equalsIgnoreCase(title)) {
                return album;
            }
        }
        return null;
    }

    /**
     * Retrieves list of all albums in music store.
     * 
     * @return List of albums.
     */
    public List<Album> getAlbums() {
        return new ArrayList<>(albums);
    }
}
