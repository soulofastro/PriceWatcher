package Controller;
/**
 * Date: 4/20/2019
 * This is an application that tracks the price of online items.
 * @author MK Babst
 * @version 0.1
 * CS 3331 
 * Homework 3
 * 
 */
import java.awt.Dialog.ModalityType;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.Item;
import Model.ItemManager;
import Model.FileItemManager;
import View.MainMenu;
import View.MainMenu.ItemViewGUI;

/**
 * 
 * This is the controller in my MVC design pattern. 
 * This contains all of the methods and classes necessary for the user
 * to interact with the price watcher app.
 * 
 * @author MK Babst
 * 
 */
public class Controller {

	/**
	 * Instance of MainMenu VIEW that this controller uses
	 */
	private MainMenu view;
	/**
	 * Instance of Items and ItemManager this controller uses
	 */
	private ItemManager model;
	private FileItemManager modelOnDisk;
	
	/**
	 * Assign an instance of of MainMenu and ItemMenu to this Controller
	 * 
	 * @param v is the instance of MainMenu that this will update
	 * @param m is the instance of ItemManager that this will use
	 * @throws IOException when url is malformed or unable to connect to web
	 */
	public Controller(MainMenu v, ItemManager m) throws IOException {
		model.start();
		model = m;
		view = v;
	}
	public Controller(MainMenu v, FileItemManager m) {
		modelOnDisk = m;
		view = v;
		model = modelOnDisk;
	}
	private boolean savedAttempt = false;
	private Item oldItem;
	
	
	private Item getOldItem() {
		return oldItem;
	}
	private void setOldItem(Item oldItem) {
		this.oldItem = oldItem;
	}
	private void setSavedAttempt(boolean savedAttempt) {
		this.savedAttempt = savedAttempt;
	}
	private boolean getSavedAttempt() {
		return savedAttempt;
		
	}
	/**
	 * Activates all the buttons and menu items in MainMenu.
	 * Also activates list selection listener for JListframe.
	 */
	public void initController() {
		
		view.getSaveItemButton().addActionListener(new addWindowListener());
		view.getExitItemButton().addActionListener(new addWindowListener());
		view.getGetPriceButton().addActionListener(new addWindowListener());
		
		view.getSaveEditButton().addActionListener(new editWindowListener());
		view.getExitEditButton().addActionListener(new editWindowListener());
		view.getGetPriceEditButton().addActionListener(new editWindowListener());
		
		view.getAddItemButton().addActionListener(new ToolbarEventListener());      
		view.getRemoveButton().addActionListener(new ToolbarEventListener());   
		view.getRefreshButton().addActionListener(new ToolbarEventListener());   
		view.getEditButton().addActionListener(new ToolbarEventListener());
		view.getViewWebPage().addActionListener(new ToolbarEventListener());

		view.getAppMenuAbout().addActionListener(new MenuBarEventListener());
		view.getAppMenuExit().addActionListener(new MenuBarEventListener());
		view.getItemMenuAdd().addActionListener(new MenuBarEventListener());
		view.getItemMenuCheckAll().addActionListener(new MenuBarEventListener());
		
		view.getSortShowAll().addActionListener(new MenuBarEventListener());
		view.getSortThinkgeek().addActionListener(new MenuBarEventListener());
		view.getSortUTEP().addActionListener(new MenuBarEventListener());
		view.getSorteBay().addActionListener(new MenuBarEventListener());
		view.getSortOther().addActionListener(new MenuBarEventListener());

		view.getItemMenuCheckPrice().addActionListener(new MenuBarEventListener());
		view.getItemMenuViewPage().addActionListener(new MenuBarEventListener());
		view.getItemMenuEditItem().addActionListener(new MenuBarEventListener());
		view.getItemMenuRemoveItem().addActionListener(new MenuBarEventListener());
		
		view.getItemList().addListSelectionListener(new ListListener());
	}
	

	/** 
	 * monitors user input for item selection in list 
     * @author MK Babst
     *
     */
    private class ListListener implements ListSelectionListener {
    	
    	public void valueChanged(ListSelectionEvent e) {
    		int itemIndex = view.getItemList().getSelectedIndex();
    		if(itemIndex > -1) {
    			view.setSelectedIndex(itemIndex);
    		}
    		
    	}
    }
    
    /** 
     * monitors user input in add item window
     * @author MK Babst
     *
     */
    private class addWindowListener implements ActionListener {
    	
		@Override
		public void actionPerformed(ActionEvent e) { 
			JButton source = (JButton)(e.getSource());
			
			if(source.equals(view.getGetPriceButton())) { 
				try {
					String url = view.getUrl().getText();
					double price = PriceFromWeb.getPriceOnly(url);
					view.getPrice().setText(String.valueOf(price));
	    			view.getAddItemDialog().repaint();
				}
				catch(IOException e1){
					JOptionPane.showMessageDialog(view.getAddView(), "Invalid Web Address. Cannot update price!");
				}
			}
			if(source.equals(view.getSaveItemButton())) {
				view.setNameEntered(view.getFieldName().getText());
				view.setPriceEntered(view.getPrice().getText());
				view.setDateAddedEntered(view.getDateAdded().getText());
		        Double userPrice;
		        try{
		        	view.setUrlEntered(view.getUrl().getText());
		        	userPrice = Double.parseDouble(view.getPriceEntered());
		        	}
		        catch(Exception e1){
		        	JOptionPane.showMessageDialog(view.getAddView(), "Entered URL does not contain this item's price!");
		        	view.setUrlEntered("");
		        	userPrice = -1.0;
		        	};
		        
				if(!view.getNameEntered().isEmpty() && userPrice > 0 && !view.getUrlEntered().isEmpty() && !view.getDateAddedEntered().isEmpty()) {
					
					try {
						Item item = new Item(view.getNameEntered(),userPrice,view.getUrlEntered(),view.getDateAddedEntered());
						model.addItem(item);
						view.getListModel().addElement(item);
						
						view.getAddItemDialog().dispose();
						view.clearAllfields();
						view.getFrame().repaint();
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(view.getAddView(), "Entered URL does not contain this item's price!");
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(view.getAddView(), "Empty fields and bad URLs are not allowed.");
					}
							
					
				}
				else {
					if(userPrice <= 0.0) {
						JOptionPane.showMessageDialog(view.getAddView(), "Entered URL does not contain this item's price!");
					}
					else{
						JOptionPane.showMessageDialog(view.getAddView(), "Bad fields encountered");
				
					}
				}
				model.saveAll();
			}
			else if(source.equals(view.getExitItemButton())) {
				view.getAddItemDialog().dispose();
				view.clearAllfields();
			}
			
			
		}
    	
    }
    
    /**
     *  monitors user input in edit item window 
     * @author MK Babst
     *
     */
    private class editWindowListener implements ActionListener{
    	
		@Override
		public void actionPerformed(ActionEvent e) { 
			JButton source = (JButton)(e.getSource());
			Item copyItem = view.getListModel().getElementAt(view.getSelectedIndex());
			setOldItem(copyItem);
			
			
			if(source.equals(view.getGetPriceButton())) { 
				try {
					String url = view.getUrl().getText();
					double price = PriceFromWeb.getPriceOnly(url);
					view.getPrice().setText(String.valueOf(price));
	    			view.getEditItemDialog().repaint();
				}
				catch(IOException e1){
					JOptionPane.showMessageDialog(view.getAddView(), "Invalid Web Address. Cannot update price!");
				}
			}
			if(source.equals(view.getSaveEditButton())) {
				setSavedAttempt(true);
				model.removeItem(copyItem);
				
				view.setNameEntered(view.getFieldName().getText());
				view.setUrlEntered(view.getUrl().getText());
				view.setDateAddedEntered(view.getDateAdded().getText());
		        Double userPrice;
		        try{
		        	view.setPriceEntered(view.getPrice().getText());
		        	userPrice = Double.parseDouble(view.getPriceEntered());
		        	}
		        catch(Exception e1){
		        	JOptionPane.showMessageDialog(view.getAddView(), "Entered URL does not contain this item's price!");
		        	view.setUrlEntered("");
		        	userPrice = -1.0;
		        	};
		        	
		        if(!view.getNameEntered().isEmpty() && userPrice > 0 && !view.getUrlEntered().isEmpty() && !view.getDateAddedEntered().isEmpty()) {
		        	try {
						Item item = new Item(view.getNameEntered(),userPrice,view.getUrlEntered(),view.getDateAddedEntered());
						view.getListModel().remove(view.getSelectedIndex());
						model.addItem(item);
						view.getListModel().addElement(item);
						
						view.getEditItemDialog().dispose();
						view.clearAllfields();
						view.getFrame().repaint();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(view.getAddView(), "Bad URLs are not allowed.");
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(view.getAddView(), "Empty fields and bad URLs are not allowed..");
					}
				}
				else {
					if(userPrice <= 0.0) {
						JOptionPane.showMessageDialog(view.getAddView(), "Price must be greater than 0.");
					}
					else{
						JOptionPane.showMessageDialog(view.getAddView(), "Bad or empty fields encountered.");
				
					}
				}
				
			}
			else if(source.equals(view.getExitEditButton())) {
				if(getSavedAttempt()) {model.addItem(getOldItem());}
				view.getEditItemDialog().dispose();
				view.clearAllfields();
			}
			
			
		}
    	
    }
    
    /**
     *  monitors user input for toolbar buttons 
     * @author MK Babst
     *
     */
    private class ToolbarEventListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		JButton source = (JButton)(e.getSource());
    		
    		if (source.equals(view.getRefreshButton())) {
    			for(int i=0; i< view.getListModel().size();i++) {
    				Item Olditem = view.getListModel().getElementAt(i);
    	    		try {
						PriceFromWeb.getPrice(view.getListModel().getElementAt(i));
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(view.getFrame(), "Item "+(i+1)+" contains an invalid Web Url!");
					}
    	    		Item newItem = view.getListModel().getElementAt(i);
    	    		model.changeItem(Olditem, newItem);
    	    	}
    			model.saveAll();
    			view.getFrame().repaint();
    			view.showMessage("Refresh clicked!");
    		}
    		else if (source.equals(view.getAddItemButton())) {
    			/* update view with new add item window */
    			view.showMessage("Add Item clicked!");
    			view.newAddItemPanel();
    			view.setAddItemDialog(new JDialog(view.getFrame(), "Add Item"));
    			view.getAddItemDialog().add(view.getAddItemPanel());
    			view.getAddItemDialog().setModalityType(ModalityType.APPLICATION_MODAL);
    			view.getAddItemDialog().setLocation(500, 300);
    			view.getAddItemDialog().setPreferredSize(view.DIALOG_SIZE);
    			view.getAddItemDialog().pack();
    			view.getAddItemDialog().setVisible(true);
    		}
    		else if(view.getSelectedIndex() == -1 || view.getSelectedIndex() >= view.getListModel().size()) {
    			view.showMessage("Please select an item first!");
    		}
    		else {
	    		if (source.equals(view.getRemoveButton())) {
	    			try {
		    			view.getListModel().remove(view.getSelectedIndex());
		    			model.removeItem(view.getListModel().getElementAt(view.getSelectedIndex()));
	    			}catch(Exception e0) {
	    				
	    			}
	    			
	    			view.getFrame().repaint();
	    			view.showMessage("Remove Item clicked!");
	    			view.setSelectedIndex(-1);
	    		}
	    		else if (source.equals(view.getEditButton())) {
	    			/* update view with new edit item window */
	    			view.showMessage("Edit Item clicked!");
	    			view.newEditCurrentItem();
	    			view.setEditItemDialog(new JDialog(view.getFrame(), "Edit Item"));
	    			view.getEditItemDialog().add(view.getEditCurrentItem());
	    			view.getEditItemDialog().setModalityType(ModalityType.APPLICATION_MODAL);
	    			view.getEditItemDialog().setLocation(500, 300);
	    			view.getEditItemDialog().setPreferredSize(view.DIALOG_SIZE);
	    			view.getEditItemDialog().pack();
	    			view.getEditItemDialog().setVisible(true);
	    		}
	    		else if (source.equals(view.getViewWebPage())) {
	    			view.showMessage("Opening this items webpage!");
	    			try {
						PriceWatcher.openURL((Item)view.getListModel().getElementAt(view.getSelectedIndex()));
					} catch (IOException | URISyntaxException e1) {
						view.showMessage("Could not open this items webpage!");
						//e1.printStackTrace();
					}
	    		}
    		}
    	}
    } 
    
    /** 
     * monitors user input for menu selections
     * @author MK Babst
     *
     */
    private class MenuBarEventListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		JMenuItem source = (JMenuItem)(e.getSource());
    		
      		if (source.equals(view.getItemMenuCheckAll())) {
    			for(int i=0; i< view.getListModel().size();i++) {
    				Item Olditem = view.getListModel().getElementAt(i);
    				try {
    					PriceFromWeb.getPrice(view.getListModel().getElementAt(i));
    				}
    				catch (Exception e1) {
    					JOptionPane.showMessageDialog(view.getFrame(), "Item "+(i+1)+" contains an invalid Web Url!");
    				}
    	    		Item newItem = view.getListModel().getElementAt(i);
    	    		model.changeItem(Olditem, newItem);
    	    	}
    			model.saveAll();
    			view.getFrame().repaint();
    			view.showMessage("Refresh in menu selected!");
    		}
    		else if (source.equals(view.getItemMenuAdd())) {
    			/* update view with new add item window*/
    			view.showMessage("Add Item in menu selected!");
    			view.newAddItemPanel();
    			view.setAddItemDialog(new JDialog(view.getFrame(), "Add Item"));
    			view.getAddItemDialog().add(view.getAddItemPanel());
    			view.getAddItemDialog().setModalityType(ModalityType.APPLICATION_MODAL);
    			view.getAddItemDialog().setLocation(500, 300);
    			view.getAddItemDialog().setPreferredSize(view.DIALOG_SIZE);
    			view.getAddItemDialog().pack();
    			view.getAddItemDialog().setVisible(true);
    		}
    		else if (source.equals(view.getAppMenuAbout())) {
    			JOptionPane.showMessageDialog(view.getFrame(), "   Price Watcher\n   Version 0.1\n   By MK Babst\n\n   Supported WebSites:\n   thinkgeek\n   UTEP Bookstore\n   eBay");
    		}
    		else if (source.equals(view.getAppMenuExit())) {
    			model.saveAll();
    			System.exit(0);
    		}
      		else if(source.equals(view.getItemMenuCheckPrice())) {
    	    	try {
					PriceFromWeb.getPrice(view.getListModel().getElementAt(view.getSelectedIndex()));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(view.getFrame(), "Item contains an invalid Web Url!");
				}
    	    	view.getFrame().repaint();
    	    	view.showMessage("Checking for price change!");
      		}
      		else if(source.equals(view.getItemMenuViewPage())) {
      			view.showMessage("Opening this items webpage!");
    			try {
					PriceWatcher.openURL((Item)view.getListModel().getElementAt(view.getSelectedIndex()));
				} catch (IOException | URISyntaxException e1) {
					view.showMessage("Could not open this items webpage!");
					//e1.printStackTrace();
				}
      		}
      		else if(source.equals(view.getItemMenuEditItem())) {
      			/* update view with new edit item window */
      			view.showMessage("Edit Item selected!");
      			view.newEditCurrentItem();
      			view.setEditItemDialog(new JDialog(view.getFrame(), "Edit Item"));
      			view.getEditItemDialog().add(view.getEditCurrentItem());
      			view.getEditItemDialog().setModalityType(ModalityType.APPLICATION_MODAL);
      			view.getEditItemDialog().setLocation(500, 300);
      			view.getEditItemDialog().setPreferredSize(view.DIALOG_SIZE);
      			view.getEditItemDialog().pack();
      			view.getEditItemDialog().setVisible(true);
      		}
      		else if(source.equals(view.getItemMenuRemoveItem())) {
      			model.removeItem(view.getListModel().get(view.getSelectedIndex()));
      			view.getListModel().remove(view.getSelectedIndex());
      			
      			view.getFrame().repaint();
      			view.showMessage("Removed Item!");
      			view.setSelectedIndex(-1);
      		}
      		else if(source.equals(view.getSortShowAll())) {
      			String filter = "all";
      			updateGuiWithFilter(filter);
      			
      		}
      		else if(source.equals(view.getSortThinkgeek())) {
      			String filter = "thinkgeek";
      			updateGuiWithFilter(filter);
      		}
      		else if(source.equals(view.getSortUTEP())) {
      			String filter = "UTEP Bookstore";
      			updateGuiWithFilter(filter);
      		}
      		else if(source.equals(view.getSorteBay())) {
      			String filter = "eBay";
      			updateGuiWithFilter(filter);
      		}
      		else if(source.equals(view.getSortOther())) {
      			String filter = "CS3331";
      			updateGuiWithFilter(filter);
      			
      		}
    	}
    	
    	/* I know this is like hammering a nail with a sledgehammer
    	 * but at this point I really don't care.
    	 */
		private void updateGuiWithFilter(String filter) {
			Point current = view.getFrame().getLocationOnScreen();
			view.getFrame().dispose();
			view.init("all", current);
			view.getFrame().dispose();
			view.init(filter, current);
			view.getItemList().addListSelectionListener(new ListListener());
			
		}
    }
    
	
}
