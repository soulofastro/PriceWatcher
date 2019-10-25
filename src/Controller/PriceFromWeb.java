package Controller;


import java.io.IOException;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import Model.Item;

/**
 * This randomly generates a price change until true implementation in HW4
 * 
 * Date: 4/20/2019
 * 
 * This is an application that tracks the price of online items.
 * 
 * @author MK Babst
 * 
 * @version 0.1
 * 
 * CS 3331 
 * 
 * Homework 3
 * 
 */
public class PriceFromWeb extends PriceWatcher {

	/**
	 * This randomly generates a price change until true implementation in HW4
	 * @param item from JListframe in mainMenu
	 * @return updated cost of item
	 * @throws IOException when url is malformed on unable to connect
	 */
	public static double getpriceFromWeb(Item item) throws IOException {
		Document document;
		Double oldPrice = Double.parseDouble(item.getprice());
		Double cost = 0.0;
		
//		try {
			document = Jsoup.connect(item.getURL()).get();
//			String title = document.title(); //Get title
			
			String tempPrice = "";
			
			if(item.getURL().contains("thinkgeek.com")) {// if webpage is thinkgeek
				tempPrice = document.select("div.actions.clearfix h3").text(); //Get price
				tempPrice = tempPrice.replace("$", "");
				item.setSource("thinkgeek");
			}
			else if(item.getURL().contains("bkstr.com")) {// if webpage is UTEP bookstore
				tempPrice = document.select("span#efitemPrice").text();
				tempPrice = tempPrice.replace("$", "");
				item.setSource("UTEP Bookstore");
			}
			else if(item.getURL().contains("ebay.com")) {// if webpage is ebay
				tempPrice = document.select("span#prcIsum.notranslate").text();
				tempPrice = tempPrice.replaceAll("[^0-9][^.][^0-9]","");
				tempPrice = tempPrice.replace("$", "");
				item.setSource("eBay");
			}
			else if(item.getURL().contains("cs.utep")) {
				tempPrice = document.select("p:nth-of-type(1)").text();
				tempPrice = tempPrice.replaceAll("[^0-9][^.][^0-9]","");
				tempPrice = tempPrice.replace("$", "");
				item.setSource("CS3331");
			}
			
			try {
				cost = Double.parseDouble(tempPrice);
			}
			catch(Exception e) {
				cost = oldPrice;
			}
			
			item.setPrice(cost);
			if(Double.parseDouble(item.getoriginalPrice()) == 0.0) {
				item.setOriginalPrice(tempPrice);
			}
			
			double change = ((cost - (Double.parseDouble(item.getoriginalPrice())))/(Double.parseDouble(item.getoriginalPrice()))) *100.0;
			item.setChange(change);
			
			
//		} catch (IOException e) {
////			System.out.println("BAD or INCORRECT URL");
////			e.printStackTrace();
//		}
		
		return cost;
	}
	/*
	 *   For R1, introduce a subclass of the PriceFinder class from HW0, say
   WebPriceFinder, and override the method that, given a URL, finds
   the price of an item. This will minimize the changes needed to the
   rest of the program.
	 * 
	 */
	public static double getPrice(Item item) throws IOException {
		double price = getpriceFromWeb(item);
		return price;
	}
	
	public static double getPriceOnly(String url) throws IOException {
		double price = 0.0;
		Document document;
		
			document = Jsoup.connect(url).get();
			
			String tempPrice = "";
			
			if(url.contains("thinkgeek.com")) {// if webpage is thinkgeek
				tempPrice = document.select("div.actions.clearfix h3").text(); //Get price
				tempPrice = tempPrice.replace("$", "");
			}
			else if(url.contains("bkstr.com")) {// if webpage is UTEP bookstore
				tempPrice = document.select("span#efitemPrice").text();
				tempPrice = tempPrice.replace("$", "");
			}
			else if(url.contains("ebay.com")) {// if webpage is ebay
				tempPrice = document.select("span#prcIsum.notranslate").text();
				tempPrice = tempPrice.replaceAll("[^0-9][^.][^0-9]","");
				tempPrice = tempPrice.replace("US $", "");
			}
			else if(url.contains("cs.utep")) {// test page from CS 3331
				tempPrice = document.select("p:nth-of-type(1)").text();
				tempPrice = tempPrice.replaceAll("[^0-9][^.][^0-9]","");
				tempPrice = tempPrice.replace("$", "");
			}
			
			price = Double.parseDouble(tempPrice);
						
		
		return price;
	}

}
