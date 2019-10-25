package Model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import Controller.PriceFromWeb;
import View.ItemView;

/**
 * 
 * Custom object to track individual Item for sale.
 * Used in ItemManager.
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
public class Item {
	/**
	 * Name of the item for sale
	 */
	private String name = " ";
	/**
	 * price of the item for sale
	 */
	private double price = 0.0;
	/**
	 *  http url of item for sale
	 */
	private String url = " ";
	/**
	 *  % change of item price from originalPrice
	 */
	private double change = 0.0;
	/**
	 *  date item was added
	 */
	private String dateAdded = " ";
	/**
	 *  original price of item when added
	 */
	private double originalPrice = 0.0;
	/**
	 * Associated ItemView Component of item. Accessed in ItemRenderer custom cell renderer
	 */
	public ItemView view;
	/**
	 * The name of the online store where item is located.
	 */
	private String source = " ";
	
//	private String ID = "";
	
	/**
	 * default constructor
	 */
	public Item() {
		
	}
	
	/**
	 * Constructor for item (name, price, url, change, dateAdded, originalPrice, source)
	 * 
	 * @param name Item name
	 * @param price Item price. Updated in classes priceWatcher and priceFromWeb
	 * @param url can be any string, but must be a proper http if you want access to webpage
	 * @param change determined after checking price
	 * @param dateAdded date added can be any format. dd mmm yyyy is default
	 * @param originalPrice price when item first created in pricewatcher app
	 * @param source gotten when price pulled from webpage
	 */
	public Item(String name, double price, String url, double change, String dateAdded, double originalPrice, String source) {
		this.name = name;
		this.price = price;
		this.url = url;
		this.change = change;
		this.dateAdded = dateAdded;
		this.originalPrice = originalPrice;
		this.source = source;
		view = new ItemView(this);
	}
	
	/**
	 * @param name Item name
	 * @param url can be any string, but must be a proper http if you want access to webpage
	 * @param dateAdded date added can be any format. dd mmm yyyy is default
	 * @throws IOException caused by malformed url
	 */
	public Item(String name, String url, String dateAdded) throws IOException {
		this.name = name;
		this.url = url;
		this.dateAdded = dateAdded;
		view = new ItemView(this);
		this.price = PriceFromWeb.getpriceFromWeb(this);
//		this.ID = getURL()+getSource();
	}
	
	/**
	 * @param name Item name
	 * @param price Item price. Updated in classes priceWatcher and priceFromWeb
	 * @param url can be any string, but must be a proper http if you want access to webpage
	 * @param dateAdded date added can be any format. dd mmm yyyy is default
	 * @throws IOException caused by malformed url
	 */
	public Item(String name, Double price, String url, String dateAdded) throws IOException {
		this.name = name;
		this.url = url;
		this.dateAdded = dateAdded;
		view = new ItemView(this);
		this.price = PriceFromWeb.getpriceFromWeb(this);
//		this.ID = getURL()+getSource();
	}

	/**
	 * duplicates an item
	 * 
	 * @param copyItem duplicates an item
	 */
	public Item(Item copyItem) {
		this.name = copyItem.getname();
		this.price = copyItem.price;
		this.url = copyItem.getURL();
		this.change = copyItem.change;
		this.dateAdded = copyItem.getDateAdded();
		this.originalPrice = copyItem.originalPrice;
		this.source = copyItem.getSource();
		view = new ItemView(this);
	}

	/* Setters */
	/**
	 * set name of item
	 * @param name set name of item
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * set cost of item
	 * @param cost set cost of item
	 */
	public void setPrice(double cost) {
		this.price = cost;
	}
	/**
	 * set url of item
	 * @param url set url of item
	 */
	public void setURL(String url) {
		this.url = url;
	}
	/**
	 * set price change of item
	 * @param change2 set price change of item
	 */
	public void setChange(double change2) {
		this.change = change2;
	}
	/**
	 * set original price of item
	 * @param originalPrice set original price of item
	 */
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = Double.parseDouble(originalPrice);
	}
	/**
	 * set date item was added
	 * @param dateAdded set date item was added
	 */
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	/* Getters */
	/**
	 * @return name of item
	 */
	public String getname() {
		return name;
	}
	/**
	 * url of item
	 * @return url of item
	 */
	public String getURL() {
		return url;
	}
	/**
	 * current price of item formatted to two decimal places
	 * @return current price of item formatted to two decimal places
	 */
	public String getprice() {
		String shortPrice = String.format("%.2f", this.price);
		return shortPrice;
	}
	/**
	 * % change of item formatted to two decimal places
	 * @return % change of item formatted to two decimal places
	 */
	public String getchange() {
		String shortChange = String.format("%.2f", this.change);
		return shortChange;
	}
	/**
	 * date item was added
	 * @return date item was added
	 */
	public String getDateAdded() {
		return dateAdded;
	}
	/**
	 * original price of item
	 * @return original price of item
	 */
	public String getoriginalPrice() {
		return String.valueOf(originalPrice);
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return source website of price
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source where price was found from URL entered by user
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
    /**
     * @return item converted to JSON object
     */
    public JSONObject toJSON() {
    	Map<String, Object> map = new HashMap<>();
    	map.put("name", name);
    	map.put("price", price);
    	map.put("url", url);
    	map.put("change", change);
    	map.put("dateAdded", dateAdded);
    	map.put("originalPrice", originalPrice);
    	map.put("source", source);
//    	map.put("ID", ID);
    	return new JSONObject(map);
    }
    // item.toJson().toString() will give a JSON string of the form:
    // { "name": "LED Monitor", "currentPrice": 30.99, ... }

    /**
     * @param obj JSON simple object
     * @return item created from JSON simple object
     */
    public static Item fromJson(JSONObject obj) {
    	double price = 0.0;
    	double change = 0.0;
    	double originalPrice = 0.0;
        String name = (String) obj.get("name");
        String url = (String) obj.get("url");
        String dateAdded = (String) obj.get("dateAdded");
        String source = (String) obj.get("source");
//        String ID = (String) obj.get("ID");
        
        if(obj.get("price") instanceof Number) {
        	price = ((Number) obj.get("price")).doubleValue();
        }
        if(obj.get("change") instanceof Number) {
        	change = ((Number) obj.get("change")).doubleValue();
        }
        if(obj.get("originalPrice") instanceof Number ) {
        	originalPrice = ((Number) obj.get("originalPrice")).doubleValue();
        }
        
        Item item = new Item(name, price, url, change, dateAdded, originalPrice, source);
    	
        return item;
    }
    
	@Override
	public boolean equals(Object other) {
		if(this == other) {return true;}
		if(other == null) {return false;}
		if(getClass() != other.getClass()) {return false;}
		if(!this.getname().contentEquals(((Item) other).getname())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		result = 7 * name.length();
		return result;
	}

}
