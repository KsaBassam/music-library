package model;

import java.util.ArrayList;
import java.util.List;

public class Album {
	private String title;
	private String artist;
	private String genre;
	private int year;
	private List<Song> songs;

	public Album(String title, String artist, String genre, int year) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		this.songs = new ArrayList<>();
	}

	public void addSong(Song song) {
		songs.add(song);
	}

	// Getters
	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getGenre() {
		return genre;
	}

	public int getYear() {
		return year;
	}

	public List<Song> getSongs() {
		return new ArrayList<>(songs);
	} // Defensive copy
}