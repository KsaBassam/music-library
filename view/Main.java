/**
 * @author Bassam
 * @author Joshua
 * 
 * AI-GENERATED CODE (with manual adjustments): 
 * - Entry point for the application.
 * - Initializes the MusicStore and LibraryView.
 */
package view;

import model.UserManager;
import store.MusicStore;

public class Main {
	public static void main(String[] args) {
		MusicStore store = new MusicStore();
		UserManager userManager = new UserManager(store);
		LibraryView view = new LibraryView(userManager, store);
		view.start();
	}
}
