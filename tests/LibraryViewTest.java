/**
 * @file:    LibraryViewTest.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: JUnit test class for LibraryView.java
 *           Ensures maximum branch and path coverage.
 */

package tests;

import model.LibraryModel;
import model.Song;
import model.Album;
import store.MusicStore;
import view.LibraryView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * JUnit test class for LibraryView.java
 * Ensures maximum branch and path coverage.
 */
class LibraryViewTest {
    private LibraryView libraryView;
    private LibraryModel model;
    private MusicStore store;

    /**
     * Setup method to initialize test objects before each test.
     */
    @BeforeEach
    void setUp() {
        store = new MusicStore();
        model = new LibraryModel(store);
        libraryView = new LibraryView(model);
    }

    /**
     * Tests searchSongByTitle() with an existing song.
     */
    @Test
    void testSearchSongByTitleExists() {
        store.getAlbums().add(new Album("Test Album", "Test Artist", "Rock", 2020));
        model.addSongFromStore("Test Song");
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("Test Song\n".getBytes()));
        libraryView.searchSongByTitle();
        System.setIn(stdin);
    }

    /**
     * Tests searchSongByTitle() with a non-existing song.
     */
    @Test
    void testSearchSongByTitleNotExists() {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("Nonexistent Song\n".getBytes()));
        libraryView.searchSongByTitle();
        System.setIn(stdin);
    }

    /**
     * Tests addSongMenu() when adding a valid song.
     */
    @Test
    void testAddSongMenuSuccess() {
        store.getAlbums().add(new Album("Test Album", "Test Artist", "Rock", 2020));
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("Test Song\n".getBytes()));
        libraryView.addSongMenu();
        assertFalse(model.searchSongsByTitle("Test Song").isEmpty());
        System.setIn(stdin);
    }

    /**
     * Tests addAlbumMenu() when adding a valid album.
     */
    @Test
    void testAddAlbumMenuSuccess() {
        store.getAlbums().add(new Album("Test Album", "Test Artist", "Rock", 2020));
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("Test Album\n".getBytes()));
        libraryView.addAlbumMenu();
        assertFalse(model.searchAlbumsByTitle("Test Album").isEmpty());
        System.setIn(stdin);
    }
}
