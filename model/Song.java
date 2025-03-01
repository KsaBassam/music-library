/**
 * @file:    Song.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: The Song class represents a music track, containing details such as
 *           title, artist, album title, rating, and favorite status. It provides 
 *           methods to access and modify song details.
 */

package model;

public class Song {
    // Title of song
    private String title;
    
    // Artist who performed song
    private String artist;
    
    // Title of album song belongs to
    private String albumTitle;
    
    // Rating of song, from 1 to 5
    private int rating;
    
    // Flag indicating if song is a favorite
    private boolean favorite;

    /**
     * Constructor to initialize Song object with specified details.
     * 
     * @param title:      Title of song.
     * @param artist:     Artist who performed song.
     * @param albumTitle: Title of album song belongs to.
     */
    public Song(String title, String artist, String albumTitle) {
        this.title = title;
        this.artist = artist;
        this.albumTitle = albumTitle;
    }

    // Getters for Song class's fields

    /**
     * Gets title of song.
     * 
     * @return Title of song.
     */
    public String getTitle() { 
        return title; 
    }

    /**
     * Gets artist of song.
     * 
     * @return Artist who performed song.
     */
    public String getArtist() { 
        return artist; 
    }

    /**
     * Gets title of album song belongs to.
     * 
     * @return Title of album.
     */
    public String getAlbumTitle() { 
        return albumTitle; 
    }

    /**
     * Gets rating of song.
     * 
     * @return Rating of song from 1 to 5.
     */
    public int getRating() { 
        return rating; 
    }

    /**
     * Checks if song is marked as favorite.
     * 
     * @return true if song is a favorite, false otherwise.
     */
    public boolean isFavorite() { 
        return favorite; 
    }

    /**
     * Sets rating for song.
     * If rating is set to 5, song is automatically marked as favorite.
     * 
     * @param rating: Rating value between 1 and 5.
     */
    public void setRating(int rating) {
        if (rating < 1 || rating > 5) return;
        this.rating = rating;
        this.favorite = (rating == 5);
    }
}
