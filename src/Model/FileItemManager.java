package Model;



import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
 


/**
 * Writes items in FileItemManager to hard drive
 * 
 * @author MK Babst
 *
 */
public class FileItemManager extends ItemManager {
	
	
	/**
	 * @throws IOException in the event app can't connect to internet or malformed url
	 */
	public FileItemManager() throws IOException {
		super();
	}

	JSONArray itemArray = new JSONArray();
	
	
	/* (non-Javadoc)
	 * @see Model.ItemManager#start()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void start() throws IOException {
		restore();
		
		if(itemArray.size() <= 0) {
			Item item = new Item("(Sample) UTEP Shot glass", "https://www.bkstr.com/ProductDisplay?urlRequestType=Base&catalogId=10001&categoryId=10051&productId=78807287&errorViewName=ProductDisplayErrorView&langId=-1&top_category=10003&parent_category_rn=10003&storeId=15728","20 Apr 2019");
			itemArray.add(item.toJSON());
		}
	}
	
	/**
	 * Loads items from items.json file and stores them in a simple json array
	 */
	public void restore() {
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("items.json")){
			Object obj = jsonParser.parse(reader);
			
			itemArray = (JSONArray) obj;		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see Model.ItemManager#saveAll()
	 */
	@Override
	public void saveAll() {
		
		try (FileWriter file = new FileWriter("items.json")){
			file.write(itemArray.toString());
			file.flush();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see Model.ItemManager#addItem(Model.Item)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addItem(Item item) {
		JSONObject itemToFile = item.toJSON();
		itemArray.add(itemToFile);
		saveAll();
	}
	
	/* (non-Javadoc)
	 * @see Model.ItemManager#removeItem(Model.Item)
	 */
	@SuppressWarnings("unchecked")
	@Override 
	public void removeItem(Item oldItem) {
		JSONArray newArray = new JSONArray();
		
		ArrayList<Item> allItems = new ArrayList<>();
		for(Object item: itemArray) {
			allItems.add(Item.fromJson((JSONObject) item));
		}
		
		int indexToRemove = -1;
		for(int j=0; j<allItems.size();j++) {
			if(allItems.get(j).equals(oldItem)) {
				indexToRemove = j;
			}
		}

		for(int i=0; i< itemArray.size(); i++) {
			if(i!=indexToRemove) {
				newArray.add(itemArray.get(i));
			}
		}
		itemArray = newArray;
		saveAll();
	}
	
	/* (non-Javadoc)
	 * @see Model.ItemManager#changeItem(Model.Item, Model.Item)
	 */
	@Override
	public void changeItem(Item Olditem, Item newItem) {
		removeItem(Olditem);
		addItem(newItem);
	}
	
	/* (non-Javadoc)
	 * @see Model.ItemManager#getItemList()
	 */
	@Override
	public ArrayList<Item> getItemList(){
		ArrayList<Item> items = new ArrayList<>();
		for(Object item: itemArray) {
			items.add((Item.fromJson((JSONObject)item)));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see Model.ItemManager#getItemList(java.lang.String)
	 */
	@Override
	public ArrayList<Item> getItemList(String source) {
		ArrayList<Item> filtered = new ArrayList<>();
		ArrayList<Item> allItems = new ArrayList<>();
		
		for(Object item: itemArray) {
			allItems.add((Item.fromJson((JSONObject)item)));
		}
		
		if(source == "all") {
			filtered = allItems;
		}
		else if(source == "thinkgeek") {
			for(Item item : allItems) {
				if(item.getSource().equalsIgnoreCase(source)) {
					filtered.add(item);
				}
			}
		}
		else if(source == "UTEP Bookstore") {
			for(Item item : allItems) {
				if(item.getSource().equalsIgnoreCase(source)) {
					filtered.add(item);
				}
			}
		}
		else if(source == "eBay") {
			for(Item item : allItems) {
				if(item.getSource().equalsIgnoreCase(source)) {
					filtered.add(item);
				}
			}
		}
		else if(source == "CS3331") {
			for(Item item : allItems) {
				if(item.getSource().equalsIgnoreCase(source)) {
					filtered.add(item);
				}
			}
		}
		
		return filtered;
	}
	
}
