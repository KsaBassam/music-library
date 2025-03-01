/**
 * @file: 	 PlaylistTest.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: Test class for Playlist.java.
 */

package tests;

import model.Playlist;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class PlaylistTest {
    private Playlist playlist;
    private Song song1;
    private Song song2;

    /**
     * Setup method to initialize test objects before each test.
     */
    @BeforeEach
    void setUp() {
        playlist = new Playlist("My Playlist");
        song1 = new Song("Song 1", "Artist 1", null);
        song2 = new Song("Song 2", "Artist 2", null);
    }

    /**
     * Tests getName() method.
     */
    @Test
    void testGetName() {
        assertEquals("My Playlist", playlist.getName());
    }

    /**
     * Tests getTrackList() initially returns an empty list.
     */
    @Test
    void testGetTrackListInitiallyEmpty() {
        assertTrue(playlist.getTrackList().isEmpty());
    }

    /**
     * Tests addSong() and getTrackList().
     */
    @Test
    void testAddSong() {
        playlist.addSong(song1);
        List<Song> trackList = playlist.getTrackList();
        assertEquals(1, trackList.size());
        assertTrue(trackList.contains(song1));
    }

    /**
     * Tests removeSong() when the song is in the playlist.
     */
    @Test
    void testRemoveSongExists() {
        playlist.addSong(song1);
        playlist.removeSong(song1);
        assertFalse(playlist.contains(song1));
    }

    /**
     * Tests removeSong() when the song is not in the playlist.
     */
    @Test
    void testRemoveSongNotExists() {
        playlist.removeSong(song1); // Should not throw an error
        assertFalse(playlist.contains(song1));
    }

    /**
     * Tests contains() when the song is in the playlist.
     */
    @Test
    void testContainsSongExists() {
        playlist.addSong(song1);
        assertTrue(playlist.contains(song1));
    }

    /**
     * Tests contains() when the song is not in the playlist.
     */
    @Test
    void testContainsSongNotExists() {
        assertFalse(playlist.contains(song1));
    }

    /**
     * Tests adding multiple songs and verifies the playlist.
     */
    @Test
    void testAddMultipleSongs() {
        playlist.addSong(song1);
        playlist.addSong(song2);
        List<Song> trackList = playlist.getTrackList();
        assertEquals(2, trackList.size());
        assertTrue(trackList.contains(song1));
        assertTrue(trackList.contains(song2));
    }

    /**
     * Tests removeSong() does not affect other songs.
     */
    @Test
    void testRemoveSongKeepsOtherSongs() {
        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.removeSong(song1);
        assertFalse(playlist.contains(song1));
        assertTrue(playlist.contains(song2));
    }
}
