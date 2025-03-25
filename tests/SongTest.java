package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Song;

class SongTest {
    private Song song;

    @BeforeEach
    void setUp() {
        song = new Song("Fire", "The Heavy", "Sons");
    }

    @Test
    void testGetTitle() {
        assertEquals("Fire", song.getTitle());
    }

    @Test
    void testGetArtist() {
        assertEquals("The Heavy", song.getArtist());
    }

    @Test
    void testGetAlbumTitle() {
        assertEquals("Sons", song.getAlbumTitle());
    }

    @Test
    void testGetRatingInitiallyZero() {
        assertEquals(0, song.getRating());
    }

    @Test
    void testIsFavoriteInitiallyFalse() {
        assertFalse(song.isFavorite());
    }

    @Test
    void testSetRatingValid() {
        song.setRating(3);
        assertEquals(3, song.getRating());
        assertFalse(song.isFavorite());
    }

    @Test
    void testSetRatingFavorite() {
        song.setRating(5);
        assertEquals(5, song.getRating());
        assertTrue(song.isFavorite());
    }

    @Test
    void testSetRatingInvalid() {
        song.setRating(6);
        assertEquals(0, song.getRating());
        assertFalse(song.isFavorite());

        song.setRating(-1);  // Negative value test
        assertEquals(0, song.getRating());
        assertFalse(song.isFavorite());
    }

    @Test
    void testSetRatingBoundaryCases() {
        song.setRating(0);  // Edge boundary for minimum rating
        assertEquals(0, song.getRating());
        assertFalse(song.isFavorite());

        song.setRating(5);  // Edge boundary for maximum rating
        assertEquals(5, song.getRating());
        assertTrue(song.isFavorite());
    }

    @Test
    void testGetPlayCountInitiallyZero() {
        assertEquals(0, song.getPlayCount());
    }

    @Test
    void testPlayIncrementsPlayCount() {
        song.play();
        assertEquals(1, song.getPlayCount());

        song.play();
        assertEquals(2, song.getPlayCount());
    }

    @Test
    void testGetLastPlayedUpdatesOnPlay() {
        long beforePlay = System.currentTimeMillis();
        song.play();
        long afterPlay = System.currentTimeMillis();

        assertTrue(song.getLastPlayed() >= beforePlay);
        assertTrue(song.getLastPlayed() <= afterPlay);
    }

    @Test
    void testMultiplePlayCountAndLastPlayedConsistency() {
        song.play();
        long firstPlayTime = song.getLastPlayed();
        
        song.play();
        assertEquals(2, song.getPlayCount());
        assertTrue(song.getLastPlayed() >= firstPlayTime);
    }
}
