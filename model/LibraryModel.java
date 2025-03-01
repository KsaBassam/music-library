/**
 * @file:    LibraryModel.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: The LibraryModel class is responsible for managing the user's music library, 
 *           including songs, albums, playlists, and integration with the music store. 
 *           It provides functionality to search, manage playlists, rate songs, and track favorites.
 */

package model;

import store.MusicStore;
import java.util.ArrayList;
import java.util.List;

public class LibraryModel {
    // List of songs in user's library
    private List<Song> songs;
    
    // List of albums in user's library
    private List<Album> albums;
    
    // List of playlists created by user
    private List<Playlist> playlists;
    
    // Reference to music store for fetching songs and albums
    private MusicStore store;

    /**
     * Constructor to initialize LibraryModel with reference to music store.
     * 
     * @param store: Music store providing access to available albums and songs.
     */
    public LibraryModel(MusicStore store) {
        this.store = store;
        songs = new ArrayList<>();
        albums = new ArrayList<>();
        playlists = new ArrayList<>();
    }

    // Music Store Integration

    /**
     * Adds a song from store to user's library by song title.
     * 
     * @param title: Title of song to be added.
     * @return true if song was added successfully, false otherwise.
     */
    public boolean addSongFromStore(String title) {
        for (Album album : store.getAlbums()) {
            for (Song song : album.getSongs()) {
                if (song.getTitle().equalsIgnoreCase(title)) {
                    songs.add(song);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds an album from store to user's library by album title.
     * 
     * @param title: Title of album to be added.
     * @return true if album was added successfully, false otherwise.
     */
    public boolean addAlbumFromStore(String title) {
        Album album = store.getAlbum(title);
        if (album != null) {
            this.albums.add(album);
            songs.addAll(album.getSongs());
            return true;
        }
        return false;
    }

    // Search Functionality

    /**
     * Searches user's library for songs by title.
     * 
     * @param title: Title of songs to search for.
     * @return List of songs that match the title.
     */
    public List<Song> searchSongsByTitle(String title) {
        List<Song> results = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                results.add(song);
            }
        }
        return results;
    }

    /**
     * Searches user's library for songs by artist name.
     * 
     * @param artist: Name of artist to search for.
     * @return List of songs by the artist.
     */
    public List<Song> searchSongsByArtist(String artist) {
        List<Song> results = new ArrayList<>();
        for (Song song : songs) {
            if (song.getArtist().equalsIgnoreCase(artist)) {
                results.add(song);
            }
        }
        return results;
    }

    /**
     * Searches user's library for albums by title.
     * 
     * @param title: Title of albums to search for.
     * @return List of albums that match the title.
     */
    public List<Album> searchAlbumsByTitle(String title) {
        List<Album> results = new ArrayList<>();
        for (Album album : albums) {
            if (album.getTitle().equalsIgnoreCase(title)) {
                results.add(album);
            }
        }
        return results;
    }

    /**
     * Searches user's library for albums by artist name.
     * 
     * @param artist: Name of artist to search for.
     * @return List of albums by the artist.
     */
    public List<Album> searchAlbumsByArtist(String artist) {
        List<Album> results = new ArrayList<>();
        for (Album album : albums) {
            if (album.getArtist().equalsIgnoreCase(artist)) {
                results.add(album);
            }
        }
        return results;
    }

    // Playlist Management

    /**
     * Creates a new playlist in user's library.
     * 
     * @param name: Name of new playlist.
     * @return true if playlist was created successfully, false otherwise.
     */
    public boolean createPlaylist(String name) {
        if (getPlaylist(name) != null) return false;
        playlists.add(new Playlist(name));
        return true;
    }

    /**
     * Adds a song to a playlist in user's library.
     * 
     * @param playlistName: Name of playlist.
     * @param song: Song to add to playlist.
     * @return true if song was added successfully, false otherwise.
     */
    public boolean addSongToPlaylist(String playlistName, Song song) {
        Playlist playlist = getPlaylist(playlistName);
        if (playlist == null) return false;
        playlist.addSong(song);
        return true;
    }

    /**
     * Removes a song from a playlist in user's library.
     * 
     * @param playlistName: Name of playlist.
     * @param song: Song to remove from playlist.
     * @return true if song was removed successfully, false otherwise.
     */
    public boolean removeSongFromPlaylist(String playlistName, Song song) {
        Playlist playlist = getPlaylist(playlistName);
        if (playlist == null) return false;
        playlist.removeSong(song);
        return true;
    }

    /**
     * Retrieves a playlist by name from user's library.
     * 
     * @param name: Name of playlist.
     * @return Playlist object if found, null otherwise.
     */
    public Playlist getPlaylist(String name) {
        for (Playlist p : playlists) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    // Data Access Methods

    /**
     * Gets list of all songs in user's library.
     * 
     * @return List of all songs.
     */
    public List<Song> getAllSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets list of all albums in user's library.
     * 
     * @return List of all albums.
     */
    public List<Album> getAllAlbums() {
        return new ArrayList<>(albums);
    }

    /**
     * Gets list of all playlists in user's library.
     * 
     * @return List of all playlists.
     */
    public List<Playlist> getAllPlaylists() {
        return new ArrayList<>(playlists);
    }

    // Rating System

    /**
     * Sets a rating for a song.
     * 
     * @param song: Song to rate.
     * @param rating: Rating value between 1 and 5.
     */
    public void rateSong(Song song, int rating) {
        if (rating < 1 || rating > 5) return;
        song.setRating(rating);
    }

    // Favorites Management

    /**
     * Gets list of favorite songs in user's library.
     * 
     * @return List of favorite songs.
     */
    public List<Song> getFavorites() {
        List<Song> favorites = new ArrayList<>();
        for (Song song : songs) {
            if (song.isFavorite()) {
                favorites.add(song);
            }
        }
        return favorites;
    }

    // Helper methods

    /**
     * Finds a song in user's library by title.
     * 
     * @param title: Title of song to find.
     * @return Song object if found, null otherwise.
     */
    public Song findSongInLibrary(String title) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                return song;
            }
        }
        return null;
    }
}
