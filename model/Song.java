package model;

public class Song {
	private String title;
	private String artist;
	private String albumTitle;
	private int rating;
	private boolean favorite;
	private int playCount;
	private long lastPlayed;

	public Song(String title, String artist, String albumTitle) {
		this.title = title;
		this.artist = artist;
		this.albumTitle = albumTitle;
		this.rating = 0;
		this.favorite = false;
		this.playCount = 0;
		this.lastPlayed = 0;
	}

	// Getters
	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public int getRating() {
		return rating;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public int getPlayCount() {
		return playCount;
	}

	public long getLastPlayed() {
		return lastPlayed;
	}

	// Setter for rating (updates favorite status)
	public void setRating(int rating) {
		if (rating >= 0 && rating <=5) {
		this.rating = rating;
		this.favorite = (rating == 5);
		} else {
			this.rating = 0;
		}
	}

	// Track plays
	public void play() {
		playCount++;
		lastPlayed = System.currentTimeMillis();
	}
}