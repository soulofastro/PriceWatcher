package Controller;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import Model.Item;

/**
 * This class acts with the Controller class to pull item info and use it to display
 * its webpage and obtain its current price.
 * 
 * @author MK Babst
 * 
 * CS 3331 
 * 
 * Homework 3
 * 
 */
public class PriceWatcher {
	static Desktop d = Desktop.getDesktop();
	
	/**
	 * @param item from JListframe in MainMenu
	 * 
	 * updated item price from subclass priceFromWeb
	 */
	public static void getPrice() {
	}

	/**
	 * Opens this items webpage in users default browser
	 * @param item from JListframe in MainMenu
	 * @throws IOException throw when receiving bad input
	 * @throws URISyntaxException throw when improper url entered
	 */
	public static void openURL(Item item) throws IOException, URISyntaxException {
		d.browse(new URI(item.getURL()));
		
	}

}
