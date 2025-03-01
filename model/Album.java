/**
 * @file: 	 Album.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: The Album class represents a music album, which contains data fields such as
 * 			 title, artist, genre, year of release, and a list of songs. The class provides
 * 			 functionality to manage an album's information and add songs to it.
 * */

package model;

import java.util.ArrayList;
import java.util.List;

public class Album {
    // Title of album
    private String title;
    
    // Artist or band who created album
    private String artist;
    
    // Genre of album
    private String genre;
    
    // Year of album release
    private int year;
    
    // List of songs on album, stored as Song objects
    private List<Song> songs;

    /**
     * Constructor to initialize Album object with specified details.
     * 
     * @param title:  Title of album.
     * @param artist: Artist or band who created album.
     * @param genre:  Genre of album.
     * @param year:   Year of album release.
     */
    public Album(String title, String artist, String genre, int year) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
        
        // Initialize songs list as an empty ArrayList
        this.songs = new ArrayList<>();
    }

    /**
     * Adds a song to album's list of songs.
     * 
     * @param song: Song object to be added to album.
     * @throws IllegalArgumentException if song title is null.
     */
    public void addSong(Song song) {
        if (song == null || song.getTitle() == null) {
            throw new IllegalArgumentException("Song or song title cannot be null.");
        }
        songs.add(song);
    }

    // Getters for Album class's fields

    /**
     * Gets title of album.
     * 
     * @return Album's title.
     */
    public String getTitle() { 
        return title; 
    }

    /**
     * Gets artist of album.
     * 
     * @return Artist or band who created album.
     */
    public String getArtist() { 
        return artist; 
    }

    /**
     * Gets genre of album.
     * 
     * @return Genre of album.
     */
    public String getGenre() { 
        return genre; 
    }

    /**
     * Gets year of album release.
     * 
     * @return Release year of album.
     */
    public int getYear() { 
        return year; 
    }

    /**
     * Gets a copy of list of songs in album. 
     * 
     * List is returned as a new ArrayList to prevent external modification 
     * of internal songs list.
     * 
     * @return New list containing songs in album.
     */
    public List<Song> getSongs() { 
        return new ArrayList<>(songs); 
    }
}
