/**
 * @file:    SongTest.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: JUnit test class for Song.java
 *           Ensures maximum branch and path coverage.
 */

package tests;

import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    private Song song;

    /**
     * Setup method to initialize test objects before each test.
     */
    @BeforeEach
    void setUp() {
        song = new Song("Fire", "The Heavy", "Sons");
    }

    /**
     * Tests getTitle() method.
     */
    @Test
    void testGetTitle() {
        assertEquals("Fire", song.getTitle());
    }

    /**
     * Tests getArtist() method.
     */
    @Test
    void testGetArtist() {
        assertEquals("The Heavy", song.getArtist());
    }

    /**
     * Tests getAlbumTitle() method.
     */
    @Test
    void testGetAlbumTitle() {
        assertEquals("Sons", song.getAlbumTitle());
    }

    /**
     * Tests getRating() method initially returning 0.
     */
    @Test
    void testGetRatingInitiallyZero() {
        assertEquals(0, song.getRating());
    }

    /**
     * Tests isFavorite() initially returning false.
     */
    @Test
    void testIsFavoriteInitiallyFalse() {
        assertFalse(song.isFavorite());
    }

    /**
     * Tests setRating() with valid values.
     */
    @Test
    void testSetRatingValid() {
        song.setRating(3);
        assertEquals(3, song.getRating());
        assertFalse(song.isFavorite());
    }

    /**
     * Tests setRating() with a rating of 5, making song a favorite.
     */
    @Test
    void testSetRatingFavorite() {
        song.setRating(5);
        assertEquals(5, song.getRating());
        assertTrue(song.isFavorite());
    }

    /**
     * Tests setRating() with values outside valid range.
     */
    @Test
    void testSetRatingInvalid() {
        song.setRating(6);
        assertEquals(0, song.getRating());
        assertFalse(song.isFavorite());

        song.setRating(0);
        assertEquals(0, song.getRating());
        assertFalse(song.isFavorite());
    }
}
