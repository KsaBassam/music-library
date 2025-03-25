package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.UserManager;
import store.MusicStore;
import view.LibraryView;

class MainTest {
    private MusicStore store;
    private UserManager userManager;
    private LibraryView view;

    @BeforeEach
    void setUp() {
        store = new MusicStore();
        userManager = new UserManager(store);
        view = new LibraryView(userManager, store);
    }

    @Test
    void testApplicationStartWithLogin() {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("1\nusername\npassword\n".getBytes()));
        
        assertDoesNotThrow(() -> view.start());
        
        System.setIn(stdin);
    }

    @Test
    void testApplicationStartWithCreateAccount() {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("2\nnewUser\nnewPass\n".getBytes()));
        
        assertDoesNotThrow(() -> view.start());
        assertNotNull(userManager.getUser("newUser"));
        
        System.setIn(stdin);
    }

    @Test
    void testInvalidLogin() {
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("1\nwrongUser\nwrongPass\n".getBytes()));
        
        assertDoesNotThrow(() -> view.start());
        assertNull(userManager.getUser("wrongUser"));
        
        System.setIn(stdin);
    }
}