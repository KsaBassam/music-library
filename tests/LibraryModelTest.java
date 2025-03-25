package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Album;
import model.LibraryModel;
import model.Song;
import store.MusicStore;

public class LibraryModelTest {

    private LibraryModel library;
    private MusicStore store;

    @BeforeEach
    public void setUp() {
        store = new MusicStore();
        
        // Mocking MusicStore data
        Album album = new Album("Sons", "The Heavy", "Rock", 2024);
        Song song1 = new Song("Fire", "The Heavy", "Sons");
        Song song2 = new Song("The Thief", "The Heavy", "Sons");
        album.addSong(song1);
        album.addSong(song2);
        store.addAlbum(album);

        
        library = new LibraryModel(store);
    }

    @Test
    public void testAddSongFromStore() {
        assertTrue(library.addSongFromStore("Fire"));
        assertEquals(1, library.getSongsSortedByTitle().size());
        assertEquals("Fire", library.getSongsSortedByTitle().get(0).getTitle());
    }

    @Test
    public void testAddAlbumFromStore() {
        assertTrue(library.addAlbumFromStore("Sons"));
        assertEquals(1, library.searchAlbumsByTitle("Sons").size());
        assertEquals("Sons", library.searchAlbumsByTitle("Sons").get(0).getTitle());
    }

    @Test
    public void testSearchSongsByTitle() {
        library.addAlbumFromStore("Sons");
        List<Song> results = library.searchSongsByTitle("Fire");
        assertEquals(1, results.size());
        assertEquals("Fire", results.get(0).getTitle());
    }

    @Test
    public void testSearchAlbumsByArtist() {
        library.addAlbumFromStore("Sons");
        List<Album> results = library.searchAlbumsByArtist("The Heavy");
        assertFalse(results.isEmpty());
        assertEquals("The Heavy", results.get(0).getArtist());
    }

    @Test
    public void testGetRecentPlays() {
        library.addSongFromStore("Fire");
        Song song = library.getSongsSortedByTitle().get(0);
        library.playSong(song);
        List<Song> recentPlays = library.getRecentPlays();
        assertEquals(1, recentPlays.size());
        assertEquals(song, recentPlays.get(0));
    }

    @Test
    public void testGetFrequentPlays() {
        library.addSongFromStore("Fire");
        library.addSongFromStore("The Thief");
        
        Song song1 = library.getSongsSortedByTitle().get(0);
        Song song2 = library.getSongsSortedByTitle().get(1);
        
        library.playSong(song1);
        library.playSong(song1);
        library.playSong(song2);
        
        List<Song> frequentPlays = library.getFrequentPlays();
        assertEquals(2, frequentPlays.size());
        assertEquals(song1, frequentPlays.get(0));
    }

    @Test
    public void testRemoveSong() {
        library.addSongFromStore("Fire");
        Song song = library.getSongsSortedByTitle().get(0);
        assertTrue(library.removeSong(song));
        assertTrue(library.getSongsSortedByTitle().isEmpty());
    }

    @Test
    public void testRemoveAlbum() {
        library.addAlbumFromStore("Sons");
        Album album = library.searchAlbumsByTitle("Sons").get(0);
        assertTrue(library.removeAlbum(album));
        assertTrue(library.searchAlbumsByTitle("Sons").isEmpty());
    }
}
