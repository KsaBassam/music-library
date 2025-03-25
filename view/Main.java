/**
 * @author Bassam
 * @author Joshua
Main
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
