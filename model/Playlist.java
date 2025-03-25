/**
 * @author Bassam
 * @author Joshua
 * 
 * Part of the Model for LA#2. Represents a playlist in the music library.
 * - Maintains songs in order and supports shuffling via the Iterator pattern.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Playlist {
	private String name;
	private List<Song> trackList;

	public Playlist(String name) {
		this.name = name;
		this.trackList = new ArrayList<>();
	}

	public void addSong(Song song) {
		trackList.add(song);
	}

	public boolean removeSong(Song song) {
		return trackList.remove(song);
	}

	public String getName() {
		return name;
	}

	public List<Song> getTrackList() {
		return new ArrayList<>(trackList);
	} // Defensive copy

	// Shuffle iterator (Iterator Pattern)
	public Iterator<Song> getShuffledIterator() {
		List<Song> shuffled = new ArrayList<>(trackList);
		Collections.shuffle(shuffled);
		return shuffled.iterator();
	}
}
