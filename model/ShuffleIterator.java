/**
 * @author Bassam
 * @author Joshua
 * 
 * Part of the Model for LA#2. Implements the Iterator pattern for shuffled playlists.
 * - Uses Collections.shuffle() to randomize song order.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException; 

public class ShuffleIterator implements Iterator<Song> {
    private List<Song> shuffledList;
    private int index = 0;

    public ShuffleIterator(List<Song> songs) {
        this.shuffledList = new ArrayList<>(songs);
        Collections.shuffle(shuffledList);
    }

    @Override
    public boolean hasNext() {
        return index < shuffledList.size();
    }

    @Override
    public Song next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the playlist."); 
        }
        return shuffledList.get(index++);
    }
}
