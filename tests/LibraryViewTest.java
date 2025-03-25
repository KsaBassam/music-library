package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Album;
import model.Song;
import model.LibraryModel;
import model.UserManager;
import store.MusicStore;
import view.LibraryView;

class LibraryViewTest {
    private LibraryView libraryView;
    private MusicStore store;
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        store = new MusicStore();
        userManager = new UserManager(store);
        libraryView = new LibraryView(userManager, store);
    }

    @Test
    void testSearchSongByTitleExists() {
        store.addAlbum(new Album("Sons", "The Heavy", "Rock", 2020));
        store.getAlbums().get(0).addSong(new Song("Fire", "The Heavy", "Sons"));


        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("1\n1\nFire\n".getBytes()));
        libraryView.start(); // Simulating user navigation
        System.setIn(stdin);
    }

    @Test
    void testSearchSongByTitleNotExists() {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("1\n1\nNonexistent Song\n".getBytes()));
        libraryView.start(); // Simulating user navigation
        System.setIn(stdin);
    }

    @Test
    void testAddSongMenuSuccess() {
        store.addAlbum(new Album("Sons", "The Heavy", "Rock", 2020));
        store.getAlbums().get(0).addSong(new Song("Fire", "The Heavy", "Sons"));

        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("1\n2\nFire\n".getBytes()));
        libraryView.start(); // Simulating user navigation
        System.setIn(stdin);
        
        LibraryModel model = userManager.getUser("default").getLibrary();
        assertFalse(model.searchSongsByTitle("Fire").isEmpty());
    }

    @Test
    void testAddAlbumMenuSuccess() {
        store.addAlbum(new Album("Sons", "The Heavy", "Rock", 2020));
        
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("1\n3\nSons\n".getBytes()));
        libraryView.start(); // Simulating user navigation
        System.setIn(stdin);
        
        LibraryModel model = userManager.getUser("default").getLibrary();
        assertFalse(model.searchAlbumsByTitle("Sons").isEmpty());
    }
}