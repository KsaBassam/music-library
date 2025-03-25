package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ShuffleIterator;
import model.Song;

class ShuffleIteratorTest {
    private List<Song> songs;
    private Song song1;
    private Song song2;
    private Song song3;

    @BeforeEach
    void setUp() {
        songs = new ArrayList<>();
        song1 = new Song("Song 1", "Artist 1", null);
        song2 = new Song("Song 2", "Artist 2", null);
        song3 = new Song("Song 3", "Artist 3", null);

        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
    }

    @Test
    void testIteratorWithMultipleSongs() {
        ShuffleIterator iterator = new ShuffleIterator(songs);
        List<Song> shuffledList = new ArrayList<>();

        while (iterator.hasNext()) {
            shuffledList.add(iterator.next());
        }

        assertEquals(3, shuffledList.size());
        assertTrue(shuffledList.containsAll(songs));
    }

    @Test
    void testIteratorWithEmptyList() {
        ShuffleIterator iterator = new ShuffleIterator(new ArrayList<>());
        assertFalse(iterator.hasNext()); // Empty list should never enter loop
    }

    @Test
    void testIteratorWithSingleElement() {
        List<Song> singleSongList = Collections.singletonList(song1);
        ShuffleIterator iterator = new ShuffleIterator(singleSongList);

        assertTrue(iterator.hasNext());
        assertEquals(song1, iterator.next());
        assertFalse(iterator.hasNext());  // Only one element — iterator should stop here
    }

    @Test
    void testIteratorRandomization() {
        ShuffleIterator iterator1 = new ShuffleIterator(songs);
        ShuffleIterator iterator2 = new ShuffleIterator(songs);

        List<Song> order1 = new ArrayList<>();
        List<Song> order2 = new ArrayList<>();

        iterator1.forEachRemaining(order1::add);
        iterator2.forEachRemaining(order2::add);

        // Ensures the lists contain the same elements
        assertEquals(3, order1.size());
        assertEquals(3, order2.size());
        assertTrue(order1.containsAll(songs));
        assertTrue(order2.containsAll(songs));

        // Ensures the orders are different (may occasionally match by chance)
        assertNotEquals(order1, order2, "Shuffle results should differ in order.");
    }

    @Test
    void testIteratorHasNextEdgeCase() {
        ShuffleIterator iterator = new ShuffleIterator(songs);

        // Iterate until end
        iterator.next();
        iterator.next();
        iterator.next();

        assertFalse(iterator.hasNext()); // Iterator should correctly report no more elements
    }

    @Test
    void testNextWithoutHasNextThrowsException() {
        ShuffleIterator iterator = new ShuffleIterator(songs);

        iterator.next();
        iterator.next();
        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
