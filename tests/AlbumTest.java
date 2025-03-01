/**
 * @file: 	 AlbumTest.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: Test class for Album.java.
 */

package tests;
import model.Album;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AlbumTest {

    private Album album;
    private Song song1;
    private Song song2;

    /**
     * Setup before each test.
     */
    @BeforeEach
    public void setUp() {
        album = new Album("Test Title", "Test Artist", "Test Genre", 2024);
        song1 = new Song("Song 1", "Test Artist", "Test Title");
        song2 = new Song("Song 2", "Test Artist", "Test Title");
    }

    /**
     * Test album creation and field initialization.
     */
    @Test
    public void testAlbumInitialization() {
        assertEquals("Test Title", album.getTitle());
        assertEquals("Test Artist", album.getArtist());
        assertEquals("Test Genre", album.getGenre());
        assertEquals(2024, album.getYear());
        assertTrue(album.getSongs().isEmpty());
    }

    /**
     * Test adding a song to the album.
     */
    @Test
    public void testAddSong() {
        album.addSong(song1);
        List<Song> songs = album.getSongs();
        assertEquals(1, songs.size());
        assertEquals(song1, songs.get(0));
    }

    /**
     * Test adding multiple songs to the album.
     */
    @Test
    public void testAddMultipleSongs() {
        album.addSong(song1);
        album.addSong(song2);
        List<Song> songs = album.getSongs();
        assertEquals(2, songs.size());
        assertEquals(song1, songs.get(0));
        assertEquals(song2, songs.get(1));
    }

    /**
     * Test song rating and favorite status.
     */
    @Test
    public void testSongRatingAndFavorite() {
        song1.setRating(3);
        assertEquals(3, song1.getRating());
        assertFalse(song1.isFavorite());

        song1.setRating(5);
        assertEquals(5, song1.getRating());
        assertTrue(song1.isFavorite());
    }

    /**
     * Test setting invalid song ratings.
     */
    @Test
    public void testInvalidSongRating() {
        song1.setRating(0);
        assertEquals(0, song1.getRating());

        song1.setRating(6);
        assertEquals(0, song1.getRating());
    }

    /**
     * Test immutability of the returned song list.
     */
    @Test
    public void testGetSongsImmutability() {
        album.addSong(song1);
        List<Song> songs = album.getSongs();
        songs.add(song2);
        assertEquals(1, album.getSongs().size());
    }

    /**
     * Test album with an empty song list.
     */
    @Test
    public void testEmptySongList() {
        List<Song> songs = album.getSongs();
        assertTrue(songs.isEmpty());
    }

    /**
     * Test adding a null song.
     */
    @Test
    public void testAddNullSong() {
        assertThrows(IllegalArgumentException.class, () -> album.addSong(null));
    }

    /**
     * Test album with edge case year values.
     */
    @Test
    public void testEdgeCaseYear() {
        Album oldAlbum = new Album("Old Title", "Old Artist", "Old Genre", 0);
        assertEquals(0, oldAlbum.getYear());
        
        Album futureAlbum = new Album("Future Title", "Future Artist", "Future Genre", 3000);
        assertEquals(3000, futureAlbum.getYear());
    }
}