package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;

import org.junit.jupiter.api.*;

import model.User;
import store.MusicStore;

class UserTest {
    private MusicStore dummyStore;
    private User user;

    @BeforeEach
    void setUp() {
        dummyStore = new MusicStore(); // Creating a dummy MusicStore instance for testing
    }

    @Test
    void testCreateUser() {
        // Create a new user with username and password
        user = new User("john_doe", "securePassword", dummyStore);

        // Verify the user is created with correct username
        assertEquals("john_doe", user.getUsername());

        // Verify library is initialized
        assertNotNull(user.getLibrary());

        // Verify hashed password is not null or empty
        assertNotNull(user.getHashedPassword());
        assertFalse(user.getHashedPassword().isEmpty());
    }

    @Test
    void testGenerateSalt() {
        user = new User("john_doe", "securePassword", dummyStore);

        // Check if salt is generated correctly (it's a byte array of size 16)
        assertNotNull(user.getSalt());
        assertEquals(16, user.getSalt().length);
    }

    @Test
    void testPasswordHashing() {
        user = new User("john_doe", "securePassword", dummyStore);

        // Ensure that the hashed password is a Base64 encoded string
        String hashedPassword = user.getHashedPassword();
        assertNotNull(hashedPassword);

        // Check if it's a valid Base64 encoded string
        Base64.Decoder decoder = Base64.getDecoder();
        assertDoesNotThrow(() -> decoder.decode(hashedPassword));
    }

    @Test
    void testDifferentPasswordsHaveDifferentHashes() {
        user = new User("john_doe", "securePassword1", dummyStore);
        User anotherUser = new User("john_doe", "securePassword2", dummyStore);

        // Ensure different passwords result in different hashes
        assertNotEquals(user.getHashedPassword(), anotherUser.getHashedPassword());
    }

    @Test
    void testSaltIsUnique() {
        user = new User("john_doe", "securePassword", dummyStore);
        User anotherUser = new User("jane_doe", "anotherPassword", dummyStore);

        // Ensure that the salt is unique for each user
        assertNotEquals(user.getSalt(), anotherUser.getSalt());
    }

    @Test
    void testLibraryModelInitialization() {
        user = new User("john_doe", "securePassword", dummyStore);

        // Ensure that the library is correctly initialized
        assertNotNull(user.getLibrary());
        // Optionally, verify the MusicStore is properly set (if LibraryModel exposes any relevant methods)
        // assertEquals(dummyStore, user.getLibrary().getStore()); // Uncomment if LibraryModel provides a way to get the store
    }

    @Test
    void testUsernameNotNull() {
        user = new User("john_doe", "securePassword", dummyStore);

        // Ensure the username is not null and is correctly set
        assertNotNull(user.getUsername());
        assertEquals("john_doe", user.getUsername());
    }

    @Test
    void testHashedPasswordLength() {
        user = new User("john_doe", "securePassword", dummyStore);

        // Ensure that the hashed password has the expected length (SHA-256 should be 64 characters in Base64)
        assertEquals(44, user.getHashedPassword().length());  // Base64 encoding of SHA-256 hash is always 44 chars
    }

    @Test
    void testSaltIsNotEqualToHashedPassword() {
        user = new User("john_doe", "securePassword", dummyStore);

        // Ensure that the salt is not equal to the hashed password
        assertNotEquals(user.getSalt(), user.getHashedPassword().getBytes());
    }

}
