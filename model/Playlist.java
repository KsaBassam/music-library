/**
 * @file:    Playlist.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: Playlist class represents a collection of songs.
 * 			 Provides functionality to add, remove, and check for songs in a playlist.
 */ 

package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name; // Name of playlist
    private List<Song> trackList; // List to store Song objects

    /**
     * Constructor to initialize a Playlist with a given name.
     * @param name: Name of playlist.
     */
    public Playlist(String name) {
        this.name = name; // Assign provided name to playlist
        this.trackList = new ArrayList<>(); // Initialize empty list for storing songs
    }

    /**
     * Gets name of playlist.
     * @return Name of playlist.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns list of songs in playlist.
     * A copy is returned to prevent external modifications.
     * @return A copy of the trackList.
     */
    public List<Song> getTrackList() {
        return new ArrayList<>(trackList); // Returns new list to maintain encapsulation
    }

    /**
     * Adds a song to playlist.
     * @param song: Song to be added.
     */
    public void addSong(Song song) {
        trackList.add(song); // Adds song to list
    }

    /**
     * Removes song from playlist if it exists.
     * @param song: Song to be removed.
     */
    public void removeSong(Song song) {
        trackList.remove(song); // Removes song from list if it is present
    }

    /**
     * Checks if song is present in playlist.
     * @param song: Song to check for.
     * @return true if song exists in playlist, false otherwise.
     */
    public boolean contains(Song song) {
        return trackList.contains(song); // Checks if song exists in list
    }
}
