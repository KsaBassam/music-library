/**
 * @file: LibraryModelTest.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: Test class for LibraryModel.java.
 */

package tests;

import model.LibraryModel;
import model.Album;
import model.Song;
import model.Playlist;
import store.MusicStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryModelTest {

    private LibraryModel library;
    private MusicStore store;

    /**
     * Setup before each test.
     */
    @BeforeEach
    public void setUp() {
        store = new MusicStore();
        library = new LibraryModel(store);
    }

    /**
     * Test adding a song from store to library.
     */
    @Test
    public void testAddSongFromStore() {
        assertTrue(library.addSongFromStore("Song 1"));
        assertEquals(1, library.getAllSongs().size());
        assertEquals("Song 1", library.getAllSongs().get(0).getTitle());
    }

    /**
     * Test adding an album from store to library.
     */
    @Test
    public void testAddAlbumFromStore() {
        assertTrue(library.addAlbumFromStore("Test Album"));
        assertFalse(library.getAllAlbums().isEmpty());
    }

    /**
     * Test searching songs by title.
     */
    @Test
    public void testSearchSongsByTitle() {
        library.addAlbumFromStore("Test Album");
        List<Song> results = library.searchSongsByTitle("Song 1");
        assertEquals(1, results.size());
        assertEquals("Song 1", results.get(0).getTitle());
    }

    /**
     * Test searching albums by artist.
     */
    @Test
    public void testSearchAlbumsByArtist() {
        library.addAlbumFromStore("Test Album");
        List<Album> results = library.searchAlbumsByArtist("Test Artist");
        assertFalse(results.isEmpty());
        assertEquals("Test Artist", results.get(0).getArtist());
    }

    /**
     * Test creating a playlist.
     */
    @Test
    public void testCreatePlaylist() {
        assertTrue(library.createPlaylist("Chill Vibes"));
        assertNotNull(library.getPlaylist("Chill Vibes"));
    }

    /**
     * Test adding a song to a playlist.
     */
    @Test
    public void testAddSongToPlaylist() {
        library.createPlaylist("Chill Vibes");
        library.addSongFromStore("Song 1");
        assertTrue(library.addSongToPlaylist("Chill Vibes", library.findSongInLibrary("Song 1")));
        assertEquals(1, library.getPlaylist("Chill Vibes").getSongs().size());
    }

    /**
     * Test rating a song.
     */
    @Test
    public void testRateSong() {
        library.addSongFromStore("Song 1");
        Song song = library.findSongInLibrary("Song 1");
        library.rateSong(song, 5);
        assertEquals(5, song.getRating());
        assertTrue(song.isFavorite());
    }

    /**
     * Test finding a song in the library.
     */
    @Test
    public void testFindSongInLibrary() {
        library.addSongFromStore("Song 1");
        Song foundSong = library.findSongInLibrary("Song 1");
        assertNotNull(foundSong);
        assertEquals("Song 1", foundSong.getTitle());
    }
}