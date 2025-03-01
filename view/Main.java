/**
 * @file:    Main.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: Initializes MusicStore, LibraryModel, and LibraryView, 
 * 			 and starts the user interface.
 */

package view;

import model.LibraryModel;
import store.MusicStore;
import view.LibraryView;

public class Main {

    /*
     * Main method that serves as entry point for the app.
     * 
     * Initializes MusicStore, LibraryModel, and LibraryView, then starts UI.
     */
    public static void main(String[] args) {
        MusicStore store = new MusicStore();
        LibraryModel model = new LibraryModel(store);
        LibraryView view = new LibraryView(model);
        view.start();
    }
}
