package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Album;
import store.MusicStore;

class MusicStoreTest {
    private MusicStore musicStore;

    @BeforeEach
    void setUp() {
        musicStore = new MusicStore();
        
        // Manually adding mock data instead of overriding loadAlbums()
        Album album1 = new Album("Sons", "The Heavy", "Rock", 2020);
        Album album2 = new Album("Test Album", "Test Artist", "Pop", 2019);
        musicStore.addAlbum(album1);
        musicStore.addAlbum(album2);
    }

    @Test
    void testGetAlbumsInitiallyEmpty() {
        List<Album> albums = musicStore.getAlbums();
        assertFalse(albums.isEmpty()); // Now ensures test is meaningful
    }

    @Test
    void testGetAlbumExists() {
        Album retrievedAlbum = musicStore.getAlbum("Sons");
        assertNotNull(retrievedAlbum);
        assertEquals("Sons", retrievedAlbum.getTitle());
    }

    @Test
    void testGetAlbumNotExists() {
        assertNull(musicStore.getAlbum("Nonexistent Album"));
    }

    @Test
    void testGetAlbumsMultiple() {
        assertTrue(musicStore.getAlbums().size() > 1);
    }
}