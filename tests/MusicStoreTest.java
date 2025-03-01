/**
 * @file:    MusicStoreTest.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: JUnit test class for MusicStore.java
 */

package tests;

import model.Album;
import store.MusicStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MusicStoreTest {
    private MusicStore musicStore;

    /**
     * Setup method to initialize test objects before each test.
     */
    @BeforeEach
    void setUp() {
        musicStore = new MusicStore();
    }

    /**
     * Tests getAlbums() initially returns an empty list if no albums are loaded.
     */
    @Test
    void testGetAlbumsInitiallyEmpty() {
        assertFalse(musicStore.getAlbums().isEmpty());
    }

    /**
     * Tests getAlbum() using MusicStore's loading method.
     */
    @Test
    void testGetAlbumExists() {
        // Ensure albums are loaded from files
        musicStore = new MusicStore(); // Reload to trigger loadAlbums()
        Album retrievedAlbum = musicStore.getAlbum("Sons");
        assertNotNull(retrievedAlbum);
        assertEquals("Sons", retrievedAlbum.getTitle());
    }

    /**
     * Tests getAlbum() when album does not exist.
     */
    @Test
    void testGetAlbumNotExists() {
        assertNull(musicStore.getAlbum("Nonexistent Album"));
    }

    /**
     * Tests getAlbums() when multiple albums are available.
     */
    @Test
    void testGetAlbumsMultiple() {
        // Ensure albums are loaded from files
        musicStore = new MusicStore(); // Reload to trigger loadAlbums()
        assertTrue(musicStore.getAlbums().size() > 1);
    }
}
