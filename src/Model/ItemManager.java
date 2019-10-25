package Model;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This is the model of my MVC design pattern.
 * It uses a custom object class Item and stores them in an arraylist.
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
public class ItemManager {
	
	private ArrayList<Item> itemList = new ArrayList<Item>();
	
	/**
	 * the default constructor adds two sample Items for the use to play around with
	 * @throws IOException for when url is inaccessable or malformed
	 */
	public ItemManager() throws IOException {
    //	addItem("(Sample) UTEP Shot glass", "https://www.bkstr.com/ProductDisplay?urlRequestType=Base&catalogId=10001&categoryId=10051&productId=78807287&errorViewName=ProductDisplayErrorView&langId=-1&top_category=10003&parent_category_rn=10003&storeId=15728","20 Apr 2019");
    	addItem("(Sample) HP Laptop", "https://www.ebay.com/itm/HP-Stream-14-Laptop-Celeron-N3060-4GB-RAM-32GB-eMMC-Windows-10/323484448208?hash=item4b512cf5d0%3Am%3AmF_FpQmBb0cRlmEen5Ls5pg&_trkparms=%2526rpp_cid%253D5c86d896ca581d232889a314&var=512535004667","20 Apr 2019");
    //	addItem("(Sample) Picard Facepalm", "https://www.thinkgeek.com/product/kukp/?pfm=HP_Carousel_StarTrekCaptainPicardFacepalmBust_1", "3 May 2018");
	}

	/**
	 * @return size of Item arraylist
	 */
	public int size() {
		return itemList.size();
	}

	/**
	 * method for adding an item to the MODEL 
	 * 
	 * @param name is the name of the item
	 * @param cost is the cost of the item
	 * @param url is the web url of the item
	 * @param dateAdded is the date the item was added
	 * @param price price of item	
	 * @param change price change after item was added
	 * @param originalPrice original price of item when added
	 * @param source website of item when added
	 */
	public void addItem(String name, double price, String url, double change, String dateAdded, double originalPrice, String source) {
		Item item = new Item(name, price, url, change, dateAdded, originalPrice, source);
		itemList.add(item);
	}
	
	public void addItem(String string, String string2, String string3) throws IOException {
		Item item = new Item(string,string2,string3);
		itemList.add(item);
	}
	
	/**
	 * Returns memory address of item at index
	 * 
	 * @param index is the arraylist index 
	 * @return memory address of item in arraylist at the requested index
	 */
	public Item getItemAt(int index) {
		return itemList.get(index);
	}
	
	/**
	 * returns memory address of entire item arraylist
	 * 
	 * @return memory address of entire Item arraylist
	 */
	public ArrayList<Item> getItemList(){
		return itemList;
	}

	/**
	 * @param item adds item to arraylist itemList
	 */
	public void addItem(Item item) {
		itemList.add(item);
	}

	/**
	 * @param item moves item from arraylist itemList
	 */
	public void removeItem(Item item) {
		itemList.remove(item);
	}

	/**
	 * @param Olditem item before change
	 * @param newItem item after change
	 */
	public void changeItem(Item Olditem, Item newItem) {
		itemList.remove(Olditem);
		itemList.add(newItem);
	}

	/**
	 * does nothing in itemManager superclass
	 */
	public void saveAll() {
	}

	/**
	 * @throws IOException does nothing in itemManager superclass
	 */
	public void start() throws IOException {
	}
	
	/**
	 * @param source website where price was pulled from
	 * @return list containing source as website
	 */
	public ArrayList<Item> getItemList(String source) {
		return itemList;
	}

}
