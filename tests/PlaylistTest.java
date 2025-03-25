package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Playlist;
import model.Song;

class PlaylistTest {
    private Playlist playlist;
    private Song song1;
    private Song song2;
    private Song duplicateSong;

    @BeforeEach
    void setUp() {
        playlist = new Playlist("My Playlist");
        song1 = new Song("Song 1", "Artist 1", null);
        song2 = new Song("Song 2", "Artist 2", null);
        duplicateSong = new Song("Song 1", "Artist 1", null); // Same details as `song1`
    }

    @Test
    void testGetName() {
        assertEquals("My Playlist", playlist.getName());
    }

    @Test
    void testGetTrackListInitiallyEmpty() {
        assertTrue(playlist.getTrackList().isEmpty());
    }

    @Test
    void testAddSong() {
        playlist.addSong(song1);
        List<Song> trackList = playlist.getTrackList();
        assertEquals(1, trackList.size());
        assertTrue(trackList.contains(song1));
    }

    @Test
    void testRemoveSongExists() {
        playlist.addSong(song1);
        assertTrue(playlist.removeSong(song1));  // Return value check
        assertFalse(playlist.getTrackList().contains(song1));
    }

    @Test
    void testRemoveSongNotExists() {
        assertFalse(playlist.removeSong(song1)); // Ensures method returns `false`
        assertTrue(playlist.getTrackList().isEmpty());
    }

    @Test
    void testAddDuplicateSongs() {
        playlist.addSong(song1);
        playlist.addSong(duplicateSong); // Same details as `song1`
        List<Song> trackList = playlist.getTrackList();
        assertEquals(2, trackList.size());
        assertTrue(trackList.contains(song1));
        assertTrue(trackList.contains(duplicateSong));
    }

    @Test
    void testRemoveDuplicateSongInstance() {
        playlist.addSong(song1);
        playlist.addSong(duplicateSong);
        playlist.removeSong(song1); // Should only remove one instance
        assertEquals(1, playlist.getTrackList().size());
        assertTrue(playlist.getTrackList().contains(duplicateSong));
    }

    @Test
    void testGetTrackListDefensiveCopy() {
        playlist.addSong(song1);
        List<Song> trackList = playlist.getTrackList();
        trackList.clear(); // Attempt to modify defensive copy
        assertEquals(1, playlist.getTrackList().size()); // Original list should remain unchanged
    }

    @Test
    void testRemoveFromEmptyPlaylist() {
        assertFalse(playlist.removeSong(song1)); // Ensures no exception is thrown
        assertTrue(playlist.getTrackList().isEmpty());
    }

    @Test
    void testMultipleAddAndRemoveOperations() {
        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.removeSong(song1);
        playlist.addSong(song1);

        List<Song> trackList = playlist.getTrackList();
        assertEquals(2, trackList.size());
        assertTrue(trackList.contains(song1));
        assertTrue(trackList.contains(song2));
    }
}
