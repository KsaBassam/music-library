package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import store.MusicStore;

public class LibraryModel {
	private MusicStore store;
	private List<Song> songs;
	private List<Album> albums;
	private List<Playlist> playlists;
	private List<Song> recentPlays;
	private List<Song> frequentPlays;

	public LibraryModel(MusicStore store) {
		this.store = store;
		this.songs = new ArrayList<>();
		this.albums = new ArrayList<>();
		this.playlists = new ArrayList<>();
		this.recentPlays = new ArrayList<>();
		this.frequentPlays = new ArrayList<>();
	}

	public void setStore(MusicStore store) {
		this.store = store;
	}

	// Add song from store
	public boolean addSongFromStore(String title) {
		for (Album album : store.getAlbums()) {
			for (Song song : album.getSongs()) {
				if (song.getTitle().equalsIgnoreCase(title) && !songs.contains(song)) {
					songs.add(song);
					return true;
				}
			}
		}
		return false;
	}

	// Add album from store
	public boolean addAlbumFromStore(String title) {
		Album album = store.getAlbum(title);
		if (album != null) {
			songs.addAll(album.getSongs());
			albums.add(album);
			return true;
		}
		return false;
	}

	// Play a song and update play counts
	public void playSong(Song song) {
		song.play();
		updateRecentPlays(song);
		updateFrequentPlays();
	}

	private void updateRecentPlays(Song song) {
		recentPlays.remove(song); // Move to front if already exists
		recentPlays.add(0, song);
		if (recentPlays.size() > 10) {
			recentPlays.remove(10);
		}
	}

	private void updateFrequentPlays() {
		frequentPlays = new ArrayList<>(songs);
		frequentPlays.sort((s1, s2) -> Integer.compare(s2.getPlayCount(), s1.getPlayCount()));
		if (frequentPlays.size() > 10) {
			frequentPlays = frequentPlays.subList(0, 10);
		}
	}

	// Getters for playlists
	public List<Song> getRecentPlays() {
		return new ArrayList<>(recentPlays);
	}

	public List<Song> getFrequentPlays() {
		return new ArrayList<>(frequentPlays);
	}

	// Sorting methods
	public List<Song> getSongsSortedByTitle() {
		return songs.stream().sorted(Comparator.comparing(Song::getTitle)).collect(Collectors.toList());
	}

	public List<Song> getSongsSortedByArtist() {
		return songs.stream().sorted(Comparator.comparing(Song::getArtist)).collect(Collectors.toList());
	}

	public List<Song> getSongsSortedByRating() {
		return songs.stream().sorted(Comparator.comparingInt(Song::getRating).reversed()).collect(Collectors.toList());
	}

	// Search methods (from LA#1)
	public List<Song> searchSongsByTitle(String title) {
		return songs.stream().filter(s -> s.getTitle().equalsIgnoreCase(title)).collect(Collectors.toList());
	}

	// 1. Search songs in the user's library by artist
	public List<Song> searchSongsByArtist(String artist) {
		List<Song> results = new ArrayList<>();
		for (Song song : songs) {
			if (song.getArtist().equalsIgnoreCase(artist)) {
				results.add(song);
			}
		}
		return results;
	}

	// 2. Search albums in the user's library by title
	public List<Album> searchAlbumsByTitle(String title) {
		List<Album> results = new ArrayList<>();
		for (Album album : albums) {
			if (album.getTitle().equalsIgnoreCase(title)) {
				results.add(album);
			}
		}
		return results;
	}

	// 3. Search albums in the user's library by artist
	public List<Album> searchAlbumsByArtist(String artist) {
		List<Album> results = new ArrayList<>();
		for (Album album : albums) {
			if (album.getArtist().equalsIgnoreCase(artist)) {
				results.add(album);
			}
		}
		return results;
	}

	// Remove functionality
	public boolean removeSong(Song song) {
		boolean removed = songs.remove(song);
		if (removed) {
			playlists.forEach(p -> p.removeSong(song));
			updateRecentPlays(song);
			updateFrequentPlays();
		}
		return removed;
	}

	public boolean removeAlbum(Album album) {
		boolean removed = albums.remove(album);
		if (removed) {
			songs.removeIf(s -> s.getAlbumTitle().equalsIgnoreCase(album.getTitle()));
		}
		return removed;
	}

	// Automatic playlists (LA#2)
	public List<Song> getFavorites() {
		return songs.stream().filter(Song::isFavorite).collect(Collectors.toList());
	}

	public Map<String, List<Song>> getGenrePlaylists() {
		Map<String, List<Song>> genreMap = new HashMap<>();
		for (Song song : songs) {
			Album album = store.getAlbum(song.getAlbumTitle());
			genreMap.computeIfAbsent(album.getGenre(), k -> new ArrayList<>()).add(song);
		}
		return genreMap.entrySet().stream().filter(e -> e.getValue().size() >= 10)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public List<Song> getTopRated() {
		return songs.stream().filter(s -> s.getRating() >= 4)
				.sorted(Comparator.comparingInt(Song::getRating).reversed()).collect(Collectors.toList());
	}
}