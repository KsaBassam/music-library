/**
 * @file: AlbumTest.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: Enhanced test class for Album.java.
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Album;
import model.Song;

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
	 * Test adding the same song multiple times.
	 */
	@Test
	public void testAddDuplicateSongs() {
		album.addSong(song1);
		album.addSong(song1); // Adding the same song again
		List<Song> songs = album.getSongs();
		assertEquals(2, songs.size());
		assertEquals(song1, songs.get(0));
		assertEquals(song1, songs.get(1)); // Check if it's added twice
	}

	/**
	 * Test setting invalid song ratings.
	 */
	@Test
	public void testInvalidSongRating() {
		song1.setRating(0);
		assertEquals(0, song1.getRating());

		song1.setRating(6);
		assertEquals(0, song1.getRating()); // Invalid rating should be reset to default
	}

	/**
	 * Test immutability of the returned song list.
	 */
	@Test
	public void testGetSongsImmutability() {
		album.addSong(song1);
		List<Song> songs = album.getSongs();
		songs.add(song2); // Try modifying the returned list
		assertEquals(1, album.getSongs().size()); // Album's song list should remain unchanged
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
	    album.addSong(null);
	    // Check if the song list contains the null value
	    List<Song> songs = album.getSongs();
	    assertEquals(1, songs.size()); // Make sure a song (null) is added
	    assertNull(songs.get(0));      // Ensure the song is actually null
	}

	/**
	 * Test album constructor with null fields.
	 */
	@Test
	public void testConstructorWithNullFields() {
	    Album nullTitleAlbum = new Album(null, "Test Artist", "Test Genre", 2024);
	    assertNull(nullTitleAlbum.getTitle()); // Check that the title is set to null

	    Album nullArtistAlbum = new Album("Test Title", null, "Test Genre", 2024);
	    assertNull(nullArtistAlbum.getArtist()); // Check that the artist is set to null

	    Album nullGenreAlbum = new Album("Test Title", "Test Artist", null, 2024);
	    assertNull(nullGenreAlbum.getGenre()); // Check that the genre is set to null
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

	/**
	 * Test empty strings in album fields.
	 */
	@Test
	public void testConstructorWithEmptyFields() {
		Album emptyTitleAlbum = new Album("", "Test Artist", "Test Genre", 2024);
		assertEquals("", emptyTitleAlbum.getTitle());

		Album emptyArtistAlbum = new Album("Test Title", "", "Test Genre", 2024);
		assertEquals("", emptyArtistAlbum.getArtist());

		Album emptyGenreAlbum = new Album("Test Title", "Test Artist", "", 2024);
		assertEquals("", emptyGenreAlbum.getGenre());
	}
}
