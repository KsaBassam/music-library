package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;

import org.junit.jupiter.api.*;

import model.User;
import model.UserManager;
import store.MusicStore;

class UserManagerTest {
    private UserManager userManager;
    private MusicStore dummyStore;
    private File tempDatabase;

    @BeforeEach
    void setUp() throws IOException {
        dummyStore = new MusicStore(); // Dummy instance

        // Create a temporary file for user database
        tempDatabase = File.createTempFile("users", ".dat");
        tempDatabase.deleteOnExit();

        userManager = new UserManager(dummyStore);

        try {
            var field = UserManager.class.getDeclaredField("userDatabase");
            field.setAccessible(true);
            field.set(userManager, tempDatabase);
        } catch (Exception e) {
            throw new RuntimeException("Failed to modify file path for testing.");
        }
    }

    @Test
    void testCreateUserSuccess() {
        userManager.createUser("john_doe", "securePassword", dummyStore);
        User retrievedUser = userManager.getUser("john_doe");

        assertNotNull(retrievedUser);
        assertEquals("john_doe", retrievedUser.getUsername());
        assertNotNull(retrievedUser.getLibrary()); // Ensures LibraryModel is initialized
    }

    @Test
    void testCreateUserDuplicate() {
        userManager.createUser("john_doe", "password1", dummyStore);
        userManager.createUser("john_doe", "password2", dummyStore);

        User retrievedUser = userManager.getUser("john_doe");
        assertFalse(userManager.authenticate("john_doe", "password1"));
        assertTrue(userManager.authenticate("john_doe", "password2"));
    }

    @Test
    void testGetUserNotFound() {
        assertNull(userManager.getUser("non_existent_user"));
    }

    @Test
    void testAuthenticateSuccess() {
        userManager.createUser("valid_user", "secure123", dummyStore);
        assertTrue(userManager.authenticate("valid_user", "secure123"));
    }

    @Test
    void testAuthenticateIncorrectPassword() {
        userManager.createUser("valid_user", "correctPass", dummyStore);
        assertFalse(userManager.authenticate("valid_user", "wrongPass"));
    }

    @Test
    void testAuthenticateUserNotFound() {
        assertFalse(userManager.authenticate("ghost_user", "irrelevantPass"));
    }

//    @Test
//    void testLoadUsersFromValidFile() throws IOException, ClassNotFoundException {
//        // Manually create mock data for serialization
//        Map<String, User> mockUsers = Map.of(
//            "user1", new User("user1", "password1", dummyStore),
//            "user2", new User("user2", "password2", dummyStore)
//        );
//
//        // Manually serialize mock data (only username, hashedPassword, and salt)
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempDatabase))) {
//            Map<String, Map<String, String>> mockUserData = new HashMap<>();
//            for (Map.Entry<String, User> entry : mockUsers.entrySet()) {
//                User user = entry.getValue();
//                Map<String, String> userData = new HashMap<>();
//                userData.put("hashedPassword", user.getHashedPassword());
//                userData.put("salt", Base64.getEncoder().encodeToString(user.getSalt()));
//                mockUserData.put(entry.getKey(), userData);
//            }
//            oos.writeObject(mockUserData);
//        }
//
//        // Reload the UserManager and check users
//        userManager = new UserManager(dummyStore);
//        assertNotNull(userManager.getUser("user1"));
//        assertNotNull(userManager.getUser("user2"));
//    }

    @Test
    void testLoadUsersWithCorruptedFile() throws IOException {
        // Corrupt the file with invalid data
        try (FileOutputStream fos = new FileOutputStream(tempDatabase)) {
            fos.write("corrupted data".getBytes());
        }

        userManager = new UserManager(dummyStore);  // Trigger reload
        assertNull(userManager.getUser("any_user")); // Should load as empty
    }

//    @Test
//    void testSaveUsersCorrectlyWritesData() throws IOException, ClassNotFoundException {
//        userManager.createUser("saved_user", "save_pass", dummyStore);
//
//        // Manually check saved data, using just username and hashed password
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempDatabase))) {
//            Map<String, Map<String, String>> savedData = (Map<String, Map<String, String>>) ois.readObject();
//            assertTrue(savedData.containsKey("saved_user"));
//            assertNotNull(savedData.get("saved_user").get("hashedPassword"));
//        }
//    }

    @Test
    void testSaveUsersHandlesIOExceptionGracefully() throws Exception {
        // Simulate file write failure
        File invalidPath = new File("/invalid_path/users.dat");

        var field = UserManager.class.getDeclaredField("userDatabase");
        field.setAccessible(true);
        field.set(userManager, invalidPath);

        assertDoesNotThrow(() -> userManager.saveUsers()); // Graceful failure expected
    }
}
