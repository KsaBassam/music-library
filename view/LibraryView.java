/**
 * @file:    LibraryView.java
 * @authors: Bassam Faiz H Alqaidi, Joshua Puhala
 * @purpose: The LibraryView class handles user interaction by providing a text-based
 *           menu interface for searching and adding songs and albums from user's library.
 *           It interacts with the LibraryModel to perform operations such as searching by
 *           title, artist, and adding songs or albums from store.
 */

package view;

import model.LibraryModel;
import model.Song;
import model.Album;
import java.util.List;
import java.util.Scanner;

public class LibraryView {
    // Model representing user's music library
    private LibraryModel model;
    
    // Scanner for reading user input from console
    private Scanner scanner;

    /**
     * Constructor to initialize LibraryView with specified LibraryModel.
     * 
     * @param model: LibraryModel representing user's music library.
     */
    public LibraryView(LibraryModel model) {
        this.model = model;
        scanner = new Scanner(System.in);
    }

    /*
     * Starts the main menu loop, allowing user to interact with library.
     */
    public void start() {
        while (true) {
            System.out.println("\n1. Search\n2. Add Song\n3. Add Album\n4. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchMenu();
                    break;
                case 2:
                    addSongMenu();
                    break;
                case 3:
                    addAlbumMenu();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    /*
     * Displays search menu for user to search songs or albums.
     */
    private void searchMenu() {
        System.out.println("\nSearch for:");
        System.out.println("1. Song by Title\n2. Song by Artist\n3. Album by Title\n4. Album by Artist");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                searchSongByTitle();
                break;
            case 2:
                searchSongByArtist();
                break;
            case 3:
                searchAlbumByTitle();
                break;
            case 4:
                searchAlbumByArtist();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    /*
     * Prompts user to enter song title and searches for songs by title.
     */
    private void searchSongByTitle() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        List<Song> results = model.searchSongsByTitle(title);
        displaySongResults(results);
    }

    /*
     * Prompts user to enter artist name and searches for songs by artist.
     */
    private void searchSongByArtist() {
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        List<Song> results = model.searchSongsByArtist(artist);
        displaySongResults(results);
    }

    /**
     * Displays list of song results.
     * 
     * @param results: List of songs to display.
     */
    private void displaySongResults(List<Song> results) {
        if (results.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }
        for (Song song : results) {
            System.out.println("Title: " + song.getTitle() + 
                             "\nArtist: " + song.getArtist() + 
                             "\nAlbum: " + song.getAlbumTitle());
        }
    }

    /*
     * Prompts user to enter album title and searches for albums by title.
     */
    private void searchAlbumByTitle() {
        System.out.print("Enter album title: ");
        String title = scanner.nextLine();
        List<Album> results = model.searchAlbumsByTitle(title);
        displayAlbumResults(results);
    }

    /*
     * Prompts user to enter artist name and searches for albums by artist.
     */
    private void searchAlbumByArtist() {
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        List<Album> results = model.searchAlbumsByArtist(artist);
        displayAlbumResults(results);
    }

    /**
     * Displays list of album results and associated songs.
     * 
     * @param results: List of albums to display.
     */
    private void displayAlbumResults(List<Album> results) {
        if (results.isEmpty()) {
            System.out.println("No albums found.");
            return;
        }
        for (Album album : results) {
            System.out.println("Album: " + album.getTitle() +
                             "\nArtist: " + album.getArtist() +
                             "\nGenre: " + album.getGenre() +
                             "\nYear: " + album.getYear());
            System.out.println("Songs:");
            for (Song song : album.getSongs()) {
                System.out.println("  - " + song.getTitle());
            }
        }
    }

    /*
     * Prompts user to enter song title and attempts to add song from store to library.
     */
    private void addSongMenu() {
        System.out.print("Enter song title to add: ");
        String title = scanner.nextLine();
        if (model.addSongFromStore(title)) {
            System.out.println("Song added successfully!");
        } else {
            System.out.println("Song not found in store.");
        }
    }

    /*
     * Prompts user to enter album title and attempts to add album from store to library.
     */
    private void addAlbumMenu() {
        System.out.print("Enter album title to add: ");
        String title = scanner.nextLine();
        if (model.addAlbumFromStore(title)) {
            System.out.println("Album added successfully!");
        } else {
            System.out.println("Album not found in store.");
        }
    }
}
