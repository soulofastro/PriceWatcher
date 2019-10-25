package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.FileItemManager;
import Model.Item;
import Model.ItemManager;


/**
 * MainMenu is the superclass View of my MVC design pattern.
 * This generates all information on screen.
 * 
 * Date 4/20/2019
 * 
 * @author MK Babst
 * 
 */
@SuppressWarnings({ "serial", "unused" })
public class MainMenu extends JFrame {

	/*
	 * pull data from ItemManager (the model) during MainMenu initialization
	 */
	private ItemManager model;
	private FileItemManager modelOnDisk;
	private ArrayList<Item> GUIarrayList = new ArrayList<Item>();
	
    /* Default dimensions for the app. */
	private final static Dimension DEFAULT_SIZE = new Dimension(600, 400);
    public final Dimension DIALOG_SIZE = new Dimension(600, 215);
    private final static Dimension BUTTON_SIZE = new Dimension(100,40);
    private final static Dimension MENU_BUTTON_SIZE = new Dimension(30,30);
    private final static Dimension LABEL_SIZE = new Dimension(100,25);
    private final static int TEXTFIELD_SIZE = 30;
      
    /* Frame, toolbar, and panels to display the watched items. **/
    private ItemViewGUI JListframe;
    private JFrame frame;
    private JPanel addItemPanel;
    private JPanel editItem;
    private JPanel editCurrentItem;
    private JPanel addView;
    private JPanel addItem;
    private JToolBar panel;
    private JDialog editItemDialog;
    private JDialog addItemDialog;
    
    /* List of Menu objects **/
    private JMenuBar menuBar;
    private JPopupMenu popupMenu;
    private JPopupMenu itemPopupMenu;
    private JMenu appMenu;
    private JMenuItem appMenuAbout;
    private JMenuItem appMenuExit;
    private JMenu itemMenu;
    private JMenuItem itemMenuAdd;
    private JMenuItem itemMenuCheckAll;
    private JMenuItem itemMenuCheckPrice;
    private JMenuItem itemMenuViewPage;
    private JMenuItem itemMenuEditItem;
    private JMenuItem itemMenuRemoveItem;
    private JMenu sortMenu; 
    private JMenuItem sortShowAll;
    private JMenuItem sortThinkgeek;
    private JMenuItem sortUTEP;
    private JMenuItem sorteBay;
    private JMenuItem sortOther;
    
    /* List items for ItemViewGUI **/
    private JList<Item> itemList;
    private int selectedIndex = -1;
    private DefaultListModel<Item> listModel;
    
    /* List of buttons used in dialogs and toolbar **/
    private JButton addItemButton;
    private JButton removeButton;
    private JButton refreshButton;
    private JButton editButton;
    private JButton viewWebPage;
	private JButton saveItemButton;
    private JButton exitItemButton;
    private JButton saveEditButton;
    private JButton exitEditButton;
    private JButton getPriceButton;
    private JButton getPriceEditButton;
    
    /* Information fields in ADD or EDIT item window **/
    private JTextField name;
    private JTextField url;
    private JTextField price;
    private JTextField dateAdded;
    
    /* User entered text in ADD or EDIT item window **/
    private String nameEntered;
    private String urlEntered;
    private String priceEntered;
    private String dateAddedEntered;
    private String currentDate = new SimpleDateFormat("d MMM yyyy").format(new Date());
    
    /* JLabels **/
	private JLabel nameLabel = new JLabel("Name:", JLabel.TRAILING);
	private JLabel urlLabel = new JLabel("URL:", JLabel.TRAILING);
	private JLabel priceLabel = new JLabel("Price:", JLabel.TRAILING);
	private JLabel dateLabel = new JLabel("DateAdded:", JLabel.TRAILING);
      
    /* Message bar to display various messages. */
    private JLabel msgBar = new JLabel(" ");
    
    /* Icons for buttons **/
    private Icon menuAddIcon;
    private Icon menuExitIcon;
    private Icon infoIcon;
    private Icon checkIcon;    
    private Icon saveIcon;
    private Icon cancelIcon;
    private Icon addIcon;
    private Icon removeIcon;
    private Icon refreshIcon;
    private Icon editIcon;
    private Icon webIcon;
    private Icon getPriceIcon;
    private Icon thinkgeekIcon;
    private Icon eBayIcon;
    private Icon UTEPIcon;
    private Icon worldIcon;
    private Icon otherIcon;
    
    /** 
     * Create a new app using ItemManager as model. 
     * @param itemModel is the M of MVC
     */
    public MainMenu(ItemManager itemModel) {
    	model = itemModel;
    	initializeAll();
    	init();
    }
    
    public MainMenu(FileItemManager itemModel) throws IOException {
    	model = itemModel;
    	model.start();
    	initializeAll();
    	init();
    }
    
    /** 
     * clears all text entry data **/
	public void clearAllfields() {
		setNameEntered(new String());
	    setUrlEntered(new String());
	    setPriceEntered(new String());
	    setDateAddedEntered(new String());
	    setName(new JTextField(TEXTFIELD_SIZE));
		setUrl(new JTextField(TEXTFIELD_SIZE));
        setPrice(new JTextField(TEXTFIELD_SIZE));
        setDateAdded(new JTextField(TEXTFIELD_SIZE));
	}
	
	/** Initializes all buttons, textfields, labels, and menus **/
	private void initializeAll() {
		/* Icons **/
	    menuAddIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/add.png"));
	    menuExitIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/door_out.png"));
	    infoIcon = new ImageIcon(getImage(getCodeBase(), "x_icons/information.png"));
	    checkIcon = new ImageIcon(getImage(getCodeBase(), "x_icons/image_link.png"));
	    
	    UTEPIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/utep.gif"));
	    thinkgeekIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/thinkgeek.jpg"));
	    eBayIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/ebay.png"));
	    worldIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/world.png"));
	    otherIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/image.png"));
	    
	    saveIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/accept.png"));
	    cancelIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/cancel.png"));
	    getPriceIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/zoom.png"));
	    
	    addIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/image_add.png"));
	    removeIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/image_delete.png"));
	    refreshIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/images.png"));
	    editIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/image_edit.png"));
	    webIcon = new ImageIcon(getImage(getCodeBase(),"x_icons/world_link.png"));
		
	    /*
	     * TextFields **/
		setName(new JTextField(TEXTFIELD_SIZE));
		setUrl(new JTextField(TEXTFIELD_SIZE));
        setPrice(new JTextField(TEXTFIELD_SIZE));
        setDateAdded(new JTextField(TEXTFIELD_SIZE));
		
		/* 
		 * Labels **/
		nameLabel.setPreferredSize(LABEL_SIZE);
		nameLabel.setOpaque(true);
		nameLabel.setLabelFor(getFieldName());
		
		urlLabel.setPreferredSize(LABEL_SIZE);
		urlLabel.setOpaque(true);
		urlLabel.setLabelFor(getUrl());
		
		priceLabel.setPreferredSize(LABEL_SIZE);
		priceLabel.setOpaque(true);
		priceLabel.setLabelFor(getPrice());
		
		dateLabel.setPreferredSize(LABEL_SIZE);
		dateLabel.setOpaque(true);
		dateLabel.setLabelFor(getDateAdded());
		
		/*
		 * Add and edit item buttons **/
		setSaveItemButton(new JButton("Save",saveIcon));
		getSaveItemButton().setIconTextGap(5);
		getSaveItemButton().setPreferredSize(BUTTON_SIZE);
		getSaveItemButton().setToolTipText("Save item and exit");
		
		setExitItemButton(new JButton("Cancel",cancelIcon));
		getExitItemButton().setIconTextGap(5);
		getExitItemButton().setPreferredSize(BUTTON_SIZE);
		getExitItemButton().setToolTipText("Exit without adding this item");
		
		setSaveEditButton(new JButton("Save",saveIcon));
		getSaveEditButton().setIconTextGap(5);
		getSaveEditButton().setPreferredSize(BUTTON_SIZE);
		getSaveEditButton().setToolTipText("Save changes and exit");
		
		setExitEditButton(new JButton("Cancel",cancelIcon));
		getExitEditButton().setIconTextGap(5);
		getExitEditButton().setPreferredSize(BUTTON_SIZE);
		getExitEditButton().setToolTipText("Discard changes and exit");
		
		setGetPriceButton(new JButton(getPriceIcon));
		getGetPriceButton().setPreferredSize(new Dimension(40,24));
		getGetPriceButton().setToolTipText("Get Price from entered url");
		
		setGetPriceEditButton(new JButton(getPriceIcon));
		getGetPriceEditButton().setPreferredSize(new Dimension(40,24));
		getGetPriceEditButton().setToolTipText("Get Price from entered url");
		
		/*
		 * Toolbar Control Panel Buttons */
    	setAddItemButton(new JButton(addIcon));
    	getAddItemButton().setFocusPainted(false);
    	getAddItemButton().setPreferredSize(MENU_BUTTON_SIZE);
        getAddItemButton().setToolTipText("Opens a window to add a new item");
        
        setRemoveButton(new JButton(removeIcon));
        getRemoveButton().setFocusPainted(false);
        getRemoveButton().setPreferredSize(MENU_BUTTON_SIZE);
        getRemoveButton().setToolTipText("Removes the currently selected item");
    	
    	setRefreshButton(new JButton(refreshIcon));
    	getRefreshButton().setFocusPainted(false);
    	getRefreshButton().setPreferredSize(MENU_BUTTON_SIZE);
        getRefreshButton().setToolTipText("Updates all prices with most the recent price");
        
        setEditButton(new JButton(editIcon));
        getEditButton().setFocusPainted(false);
        getEditButton().setPreferredSize(MENU_BUTTON_SIZE);
        getEditButton().setToolTipText("Opens a window to edit selected item.");
        
        setViewWebPage(new JButton(webIcon));
        getViewWebPage().setFocusPainted(false);
        getViewWebPage().setPreferredSize(MENU_BUTTON_SIZE);
        getViewWebPage().setToolTipText("Opens selected items webpage in default web browser");
        
        
        /* Initialize Menu Items **/
        appMenu = new JMenu("App");
			setAppMenuAbout(new JMenuItem("About",infoIcon));
			getAppMenuAbout().setMnemonic(KeyEvent.VK_B);
			getAppMenuAbout().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
			appMenu.add(getAppMenuAbout());
			
			setAppMenuExit(new JMenuItem("Exit", menuExitIcon));
			getAppMenuExit().setMnemonic(KeyEvent.VK_X);
			getAppMenuExit().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
			appMenu.add(getAppMenuExit());
			
	    itemMenu = new JMenu("Item");
        	setItemMenuAdd(new JMenuItem("Add Item",menuAddIcon));
        	getItemMenuAdd().setMnemonic(KeyEvent.VK_A);
        	getItemMenuAdd().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        	itemMenu.add(getItemMenuAdd());
        	
        	setItemMenuCheckAll(new JMenuItem("Check All", refreshIcon));
        	getItemMenuCheckAll().setMnemonic(KeyEvent.VK_C);
        	getItemMenuCheckAll().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        	itemMenu.add(getItemMenuCheckAll());
 
        sortMenu = new JMenu("Filter");
        	setSortShowAll(new JMenuItem("Show All", worldIcon));
        	getSortShowAll().setMnemonic(KeyEvent.VK_L);
        	getSortShowAll().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        	sortMenu.add(getSortShowAll());
        	
        	setSortThinkgeek(new JMenuItem("thinkgeek", thinkgeekIcon));
        	getSortThinkgeek().setMnemonic(KeyEvent.VK_K);
        	getSortThinkgeek().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.ALT_MASK));
        	sortMenu.add(getSortThinkgeek());
        	
        	setSortUTEP(new JMenuItem("UTEP Store", UTEPIcon));
        	getSortUTEP().setMnemonic(KeyEvent.VK_U);
        	getSortUTEP().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
        	sortMenu.add(getSortUTEP());
        	
        	setSorteBay(new JMenuItem("eBay", eBayIcon));
        	getSorteBay().setMnemonic(KeyEvent.VK_E);
        	getSorteBay().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        	sortMenu.add(getSorteBay());
        	
        	setSortOther(new JMenuItem("Other", otherIcon));
        	getSortOther().setMnemonic(KeyEvent.VK_O);
        	getSortOther().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        	sortMenu.add(getSortOther());
        	
        /* Pop up menu objects */
    	setItemMenuCheckPrice(new JMenuItem("Check Price",checkIcon));
    	getItemMenuCheckPrice().setMnemonic(KeyEvent.VK_P);
    	getItemMenuCheckPrice().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
    	
        setItemMenuViewPage(new JMenuItem("View Page",webIcon));
        getItemMenuViewPage().setMnemonic(KeyEvent.VK_V);
        getItemMenuViewPage().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
        
        setItemMenuEditItem(new JMenuItem("Edit Item",editIcon));
        getItemMenuEditItem().setMnemonic(KeyEvent.VK_E);
        getItemMenuEditItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        
        setItemMenuRemoveItem(new JMenuItem("Remove Item",removeIcon));
        getItemMenuRemoveItem().setMnemonic(KeyEvent.VK_R);
        getItemMenuRemoveItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        
        itemPopupMenu = makePopupMenu();
	}

	/**
	 *  creates main app frame and adds menus, toolbars, lists, and msg bar **/
    private void init() {
    	setFrame(new JFrame());
    	getFrame().setTitle("Price Watcher");
    	
    	/* add Menu to frame */
    	JMenuBar mainMenuBar = makeMenuBar();
    	getFrame().setJMenuBar(mainMenuBar);

        /* add toolbar to top */
        JToolBar control = makeToolbar();
        getFrame().add(control, BorderLayout.NORTH);
    	
    	/* Add list of items in center */
    	JListframe = new ItemViewGUI("all"); 
    	JListframe.setBackground(Color.WHITE);

    	getFrame().add(add(new JScrollPane(JListframe)), BorderLayout.CENTER);
    	
    	/* add message bar to bottom */
		msgBar.setBorder(BorderFactory.createEmptyBorder(10,16,10,0));
        getFrame().add(msgBar, BorderLayout.SOUTH);
        
        /* Finalize main frame */
        getFrame().setDefaultCloseOperation(EXIT_ON_CLOSE);
        getFrame().pack();
        getFrame().setVisible(true);
        
        showMessage("Welcome!");
    }	
    
    public void init(String filter, Point p) {
    	setFrame(new JFrame());
    	getFrame().setTitle("Price Watcher");
    	getFrame().setLocation(p);
    	
    	/* add Menu to frame */
    	JMenuBar mainMenuBar = makeMenuBar();
    	getFrame().setJMenuBar(mainMenuBar);

        /* add toolbar to top */
        JToolBar control = makeToolbar();
        getFrame().add(control, BorderLayout.NORTH);
    	
    	/* Add list of items in center */
    	JListframe = new ItemViewGUI(filter); 
    	JListframe.setBackground(Color.WHITE);

    	getFrame().add(add(new JScrollPane(JListframe)), BorderLayout.CENTER);
    	
    	/* add message bar to bottom */
		msgBar.setBorder(BorderFactory.createEmptyBorder(10,16,10,0));
        getFrame().add(msgBar, BorderLayout.SOUTH);
        
        /* Finalize main frame */
        getFrame().setDefaultCloseOperation(EXIT_ON_CLOSE);
        getFrame().pack();
        getFrame().setVisible(true);
        
        showMessage("Welcome!");
    }	
    
    /** 
     * Creates Pop-up Menu for item list
     * @return a popup menu composed of check price, view web page, edit item, and remove item
     */
    private JPopupMenu makePopupMenu() {
    	popupMenu = new JPopupMenu();
    	
    	popupMenu.add(getItemMenuCheckPrice());
    	popupMenu.add(getItemMenuViewPage());
    	popupMenu.add(getItemMenuEditItem());
    	popupMenu.add(getItemMenuRemoveItem());
    	
    	return popupMenu;
    }
    
    /** 
     * Creates the Menu bar 
     * @return a menu bar composed of the application and item submenus
     */
    private JMenuBar makeMenuBar() {
    	menuBar = new JMenuBar(); 
    	
    	menuBar.add(appMenu);
    	menuBar.add(itemMenu);
    	menuBar.add(sortMenu);
    	return menuBar;
    }
      
    /** 
     * Creates a Toolbar consisting of buttons. 
     * @return a floatable, rollover toolbar with add,edit,remove,view webpage, and refresh all buttons
     */
    private JToolBar makeToolbar() {
    	panel = new JToolBar();
        
        panel.add(getAddItemButton());
        panel.add(getEditButton()); 
        panel.add(getRemoveButton());
        panel.add(getViewWebPage());
        panel.add(getRefreshButton());
        panel.setFloatable(true);
        panel.setRollover(true);
        panel.addSeparator();
        
        
        return panel;
    }

    /** 
     * Show briefly the given string msg in the message bar. 
     * @param msg is a string that displays at the bottom of the main Frame
     */
    public void showMessage(String msg) {
        msgBar.setText(msg);
        new Thread(() -> {
        	try {
				Thread.sleep(3 * 1000); // 3 seconds
			} catch (InterruptedException e) {
			}
        	if (msg.equals(msgBar.getText())) {
        		SwingUtilities.invokeLater(() -> msgBar.setText(" "));
        	}
        }).start();
    }
    
    /**
     *  Creates a scrollable list of items
     * @author MK Babst
     *
     */
    public class ItemViewGUI extends JPanel {
    	
    	public ItemViewGUI(String source) {
    		
    		setListModel(new DefaultListModel<>());
    		setItemListForItemViewGUI(source);
    		
    		for(Item item: GUIarrayList) {
    			getListModel().addElement(item);
    		}
    		setItemList(new JList<>(getListModel()));
    		getItemList().setCellRenderer(new ItemRenderer());
    		getItemList().setComponentPopupMenu(itemPopupMenu);
    		getItemList().addMouseListener( new MouseAdapter(){ // enables right-click to select
    		    public void mousePressed(MouseEvent e)
    		    {
    		        if ( SwingUtilities.isRightMouseButton(e) )
    		        {
    		            @SuppressWarnings("rawtypes")
						JList list = (JList)e.getSource();
    		            int row = list.locationToIndex(e.getPoint());
    		            list.setSelectedIndex(row);
    		        }
    		    }

    		});
    		
            add(getItemList());
    	}
    }
    
    /** 
     * creates window to add an item
     * @author MK Babst
     *
     */
    private class addItemWindow extends JPanel{
    	
    	public addItemWindow() {
    		clearAllfields();
    		getPrice().setEditable(false);
	        getDateAdded().setText(currentDate);
	        
    		addItem = new JPanel();
    		
    		addItem.setPreferredSize(new Dimension(575,120));
    		addItem.setLayout(new SpringLayout());
    		addItem.setBorder(new EtchedBorder());

    		addItem.add(nameLabel);
    		addItem.add(name);
    		
    		addItem.add(urlLabel);
    		addItem.add(url);
    		
    		JPanel pricePanel = new JPanel();
    		pricePanel.add(new JLabel("     $"));
    		pricePanel.add(price);
    		price.setPreferredSize(new Dimension(30,24));
    		price.setBackground(Color.WHITE);
    		pricePanel.add(getPriceButton);
    		pricePanel.setLayout(new FlowLayout());
    		addItem.add(priceLabel);
    		addItem.add(pricePanel);
    		 
    		
    		
    		addItem.add(dateLabel);
    		addItem.add(dateAdded);
    		
    		SpringUtilities.makeCompactGrid(addItem, //parent
					4, 2, //rows,cols
					2, 2, //initX,initY
					2, 2);//xPad,yPad
    		setOpaque(true);
    		add(addItem);
    		
    		JPanel addButtons = new JPanel();
    		addButtons.add(getSaveItemButton());
    		addButtons.add(getExitItemButton());
//    		addButtons.add(getGetPriceButton());
    		addButtons.setBorder(new EtchedBorder());
    		
    		add(addButtons, BorderLayout.SOUTH);
    	}
    	
    }
    
    /** 
     * creates window to edit selected item in list 
	 * @author MK Babst
	 *
	 */
	private class editItemWindow extends JPanel{
	    	
    	public editItemWindow() {
    		editItem = new JPanel();
    		
    		name.setText(getListModel().getElementAt(getSelectedIndex()).getname());
    		url.setText(getListModel().getElementAt(getSelectedIndex()).getURL());
            price.setText(getListModel().getElementAt(getSelectedIndex()).getprice());
            price.setEditable(false);
	        dateAdded.setText(getListModel().getElementAt(getSelectedIndex()).getDateAdded());
    		
	        editItem.setPreferredSize(new Dimension(575,120));
    		editItem.setLayout(new SpringLayout());
    		editItem.setBorder(new EtchedBorder());

    		editItem.add(nameLabel);
    		editItem.add(name);
    		
    		editItem.add(urlLabel);
    		editItem.add(url);
    		
    		JPanel pricePanel = new JPanel();
    		pricePanel.add(new JLabel("     $"));
    		pricePanel.add(price);
    		price.setPreferredSize(new Dimension(30,24));
    		price.setBackground(Color.WHITE);
    		pricePanel.add(getPriceEditButton);
    		pricePanel.setLayout(new FlowLayout());
    		editItem.add(priceLabel);
    		editItem.add(pricePanel);
    		
    		editItem.add(dateLabel);
    		editItem.add(dateAdded);
    		
    		SpringUtilities.makeCompactGrid(editItem, //parent
					4, 2, //rows,cols
					2, 2, //initX,initY
					2, 2);//xPad,yPad
    		setOpaque(true);
    		add(editItem);
    		
    		JPanel addButtons = new JPanel();
    		addButtons.add(getSaveEditButton());
    		addButtons.add(getExitEditButton());
    		addButtons.setBorder(new EtchedBorder());
    		
    		add(addButtons, BorderLayout.SOUTH);
	    }
    	
    }
    
	/* 
	 * Stuff taken from NoApplet so I can grab pictures from image package **/
	
    private URL getCodeBase() {
        return getClass().getResource("/");
    }    
    private Image getImage(URL url) {
    	try {
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Image getImage(URL url, String name) {
    	try {
            url = new URL(url, name);
            return getImage(url);				
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* Setters and Getters for MainMenu variables **/
    
	/**
	 * @return save button in Add item window
	 */
	public JButton getSaveItemButton() {
		return saveItemButton;
	}

	/**
	 * set save button in Add item window
	 * @param saveItemButton set save button in Add item window
	 */
	public void setSaveItemButton(JButton saveItemButton) {
		this.saveItemButton = saveItemButton;
	}

	/**
	 * exit button in add item window
	 * @return exit button in add item window
	 */
	public JButton getExitItemButton() {
		return exitItemButton;
	}

	/**
	 * set exit button in add item window
	 * @param exitItemButton set exit button in add item window
	 */
	public void setExitItemButton(JButton exitItemButton) {
		this.exitItemButton = exitItemButton;
	}

	/**
	 * save button in edit item window
	 * @return save button in edit item window
	 */
	public JButton getSaveEditButton() {
		return saveEditButton;
	}

	/**
	 * set save button in edit item window
	 * @param saveEditButton set save button in edit item window
	 */
	public void setSaveEditButton(JButton saveEditButton) {
		this.saveEditButton = saveEditButton;
	}

	/**
	 * exit button in edit item window
	 * @return exit button in edit item window
	 */
	public JButton getExitEditButton() {
		return exitEditButton;
	}

	/**
	 * set exit button in edit item window
	 * @param exitEditButton set exit button in edit item window
	 */
	public void setExitEditButton(JButton exitEditButton) {
		this.exitEditButton = exitEditButton;
	}

	/**
	 * button to display add item window
	 * @return button to display add item window
	 */
	public JButton getAddItemButton() {
		return addItemButton;
	}

	/**
	 * set button to display add item window
	 * @param addItemButton set button to display add item window
	 */
	public void setAddItemButton(JButton addItemButton) {
		this.addItemButton = addItemButton;
	}

	/**
	 * button to remove item from JList component
	 * @return button to remove item from JList component
	 */
	public JButton getRemoveButton() {
		return removeButton;
	}

	/**
	 * set button to remove item from JList component
	 * @param removeButton set button to remove item from JList component
	 */
	public void setRemoveButton(JButton removeButton) {
		this.removeButton = removeButton;
	}

	/**
	 * refresh button to update items in ItemManager and JList
	 * @return refresh button to update items in ItemManager and JList
	 */
	public JButton getRefreshButton() {
		return refreshButton;
	}

	/**
	 * refreshButton set refresh button
	 * @param refreshButton set refresh button
	 */
	public void setRefreshButton(JButton refreshButton) {
		this.refreshButton = refreshButton;
	}

	/**
	 * edit button 
	 * @return edit button 
	 */
	public JButton getEditButton() {
		return editButton;
	}

	/**
	 * set edit button
	 * @param editButton set edit button
	 */
	public void setEditButton(JButton editButton) {
		this.editButton = editButton;
	}

	public JButton getGetPriceButton() {
		return getPriceButton;
	}

	public void setGetPriceButton(JButton getPriceButton) {
		this.getPriceButton = getPriceButton;
	}

	public JButton getGetPriceEditButton() {
		return getPriceEditButton;
	}

	public void setGetPriceEditButton(JButton getPriceEditButton) {
		this.getPriceEditButton = getPriceEditButton;
	}

	/**
	 * button to open web page
	 * @return button to open web page
	 */
	public JButton getViewWebPage() {
		return viewWebPage;
	}

	/**
	 * set button to open web page
	 * @param viewWebPage set button to open web page
	 */
	public void setViewWebPage(JButton viewWebPage) {
		this.viewWebPage = viewWebPage;
	}

	/**
	 * "about" main menu item
	 * @return "about" main menu item
	 */
	public JMenuItem getAppMenuAbout() {
		return appMenuAbout;
	}

	/**
	 * set "about" main menu item
	 * @param appMenuAbout set "about" main menu item
	 */
	public void setAppMenuAbout(JMenuItem appMenuAbout) {
		this.appMenuAbout = appMenuAbout;
	}

	/**
	 * "exit" main menu item
	 * @return "exit" main menu item
	 */
	public JMenuItem getAppMenuExit() {
		return appMenuExit;
	}

	/**
	 * set "exit" main menu item
	 * @param appMenuExit set "exit" main menu item
	 */
	public void setAppMenuExit(JMenuItem appMenuExit) {
		this.appMenuExit = appMenuExit;
	}

	/**
	 * "add item" main menu item
	 * @return "add item" main menu item
	 */
	public JMenuItem getItemMenuAdd() {
		return itemMenuAdd;
	}

	/**
	 * set "add item" main menu item
	 * @param itemMenuAdd set "add item" main menu item
	 */
	public void setItemMenuAdd(JMenuItem itemMenuAdd) {
		this.itemMenuAdd = itemMenuAdd;
	}

	/**
	 * "check all item prices" main menu item
	 * @return "check all item prices" main menu item
	 */
	public JMenuItem getItemMenuCheckAll() {
		return itemMenuCheckAll;
	}

	/**
	 * set "check all item prices" main menu item
	 * @param itemMenuCheckAll set "check all item prices" main menu item
	 */
	public void setItemMenuCheckAll(JMenuItem itemMenuCheckAll) {
		this.itemMenuCheckAll = itemMenuCheckAll;
	}

	/**
	 * "check price of selected item" in popup menu
	 * @return "check price of selected item" in popup menu
	 */
	public JMenuItem getItemMenuCheckPrice() {
		return itemMenuCheckPrice;
	}

	/**
	 * set "check price of selected item" in popup menu
	 * @param itemMenuCheckPrice set "check price of selected item" in popup menu
	 */
	public void setItemMenuCheckPrice(JMenuItem itemMenuCheckPrice) {
		this.itemMenuCheckPrice = itemMenuCheckPrice;
	}

	/**
	 * "open web page of item"  in popup menu
	 * @return "open web page of item"  in popup menu
	 */
	public JMenuItem getItemMenuViewPage() {
		return itemMenuViewPage;
	}

	/**
	 * set "open web page of item" in popup menu
	 * @param itemMenuViewPage set "open web page of item" in popup menu
	 */
	public void setItemMenuViewPage(JMenuItem itemMenuViewPage) {
		this.itemMenuViewPage = itemMenuViewPage;
	}

	/**
	 * "edit item" in popup menu
	 * @return "edit item" in popup menu
	 */
	public JMenuItem getItemMenuEditItem() {
		return itemMenuEditItem;
	}

	/**
	 * set "edit item" in popup menu
	 * @param itemMenuEditItem set "edit item" in popup menu
	 */
	public void setItemMenuEditItem(JMenuItem itemMenuEditItem) {
		this.itemMenuEditItem = itemMenuEditItem;
	}

	/**
	 * "remove item" in popup menu
	 * @return "remove item" in popup menu
	 */
	public JMenuItem getItemMenuRemoveItem() {
		return itemMenuRemoveItem;
	}

	/**
	 * set "remove item" in popup menu
	 * @param itemMenuRemoveItem set "remove item" in popup menu
	 */
	public void setItemMenuRemoveItem(JMenuItem itemMenuRemoveItem) {
		this.itemMenuRemoveItem = itemMenuRemoveItem;
	}

	/**
	 * @return the sortMenu
	 */
	public JMenu getSortMenu() {
		return sortMenu;
	}

	/**
	 * @param sortMenu the sortMenu to set
	 */
	public void setSortMenu(JMenu sortMenu) {
		this.sortMenu = sortMenu;
	}

	/**
	 * @return the sortShowAll
	 */
	public JMenuItem getSortShowAll() {
		return sortShowAll;
	}

	/**
	 * @param sortShowAll the sortShowAll to set
	 */
	public void setSortShowAll(JMenuItem sortShowAll) {
		this.sortShowAll = sortShowAll;
	}

	/**
	 * @return the sortThinkgeek
	 */
	public JMenuItem getSortThinkgeek() {
		return sortThinkgeek;
	}

	/**
	 * @param sortThinkgeek the sortThinkgeek to set
	 */
	public void setSortThinkgeek(JMenuItem sortThinkgeek) {
		this.sortThinkgeek = sortThinkgeek;
	}

	/**
	 * @return the sortUTEP
	 */
	public JMenuItem getSortUTEP() {
		return sortUTEP;
	}

	/**
	 * @param sortUTEP the sortUTEP to set
	 */
	public void setSortUTEP(JMenuItem sortUTEP) {
		this.sortUTEP = sortUTEP;
	}

	/**
	 * @return the sorteBay
	 */
	public JMenuItem getSorteBay() {
		return sorteBay;
	}

	/**
	 * @param sorteBay the sorteBay to set
	 */
	public void setSorteBay(JMenuItem sorteBay) {
		this.sorteBay = sorteBay;
	}

	/**
	 * @return the sortOther
	 */
	public JMenuItem getSortOther() {
		return sortOther;
	}

	/**
	 * @param sortOther the sortOther to set
	 */
	public void setSortOther(JMenuItem sortOther) {
		this.sortOther = sortOther;
	}

	/**
	 * temporary list of items for MainMenu VIEW to manipulate and render with
	 * @return temporary list of items for MainMenu VIEW to manipulate and render with
	 */
	public JList<Item> getItemList() {
		return itemList;
	}

	/**
	 * set temporary JLIST for use with ItemViewGUI subclass
	 * @param itemList set temporary JLIST for use with ItemViewGUI subclass
	 */
	public void setItemList(JList<Item> itemList) {
		this.itemList = itemList;
	}

	/**
	 * primitive integer of item index of item selected in main menu
	 * @return primitive integer of item index of item selected in main menu
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * updates controller and view, lets them paint and access the user selected item
	 * @param selectedIndex updates controller and view, lets them paint and access the user selected item
	 */
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	/**
	 * name entered in text field
	 * @return name entered in text field
	 */
	public String getNameEntered() {
		return nameEntered;
	}

	/**
	 * set name entered in text field
	 * @param nameEntered set name entered in text field
	 */
	public void setNameEntered(String nameEntered) {
		this.nameEntered = nameEntered;
	}

	/**
	 * url entered in text field
	 * @return url entered in text field
	 */
	public String getUrlEntered() {
		return urlEntered;
	}

	/**
	 * set url entered in text field
	 * @param urlEntered set url entered in text field
	 */
	public void setUrlEntered(String urlEntered) {
		this.urlEntered = urlEntered;
	}

	/**
	 * price entered in text field
	 * @return price entered in text field
	 */
	public String getPriceEntered() {
		return priceEntered;
	}

	/**
	 * set price entered in text field
	 * @param priceEntered set price entered in text field
	 */
	public void setPriceEntered(String priceEntered) {
		this.priceEntered = priceEntered;
	}

	/**
	 * date added in text field
	 * @return date added in text field
	 */
	public String getDateAddedEntered() {
		return dateAddedEntered;
	}

	/**
	 * set date added in text field. Will auto populate with todays date if empty.
	 * @param dateAddedEntered set date added in text field. Will auto populate with todays date if empty.
	 */
	public void setDateAddedEntered(String dateAddedEntered) {
		this.dateAddedEntered = dateAddedEntered;
	}

	/**
	 * field for name text entry
	 * @return field for name text entry
	 */
	public JTextField getFieldName() {
		return name;
	}

	/**
	 * set field for name text entry
	 * @param name set field for name text entry
	 */
	public void setName(JTextField name) {
		this.name = name;
	}

	/**
	 * field for url entry
	 * @return field for url entry
	 */
	public JTextField getUrl() {
		return url;
	}

	/**
	 * set field for url entry
	 * @param url set field for url entry
	 */
	public void setUrl(JTextField url) {
		this.url = url;
	}

	/**
	 * field for price entry
	 * @return field for price entry
	 */
	public JTextField getPrice() {
		return price;
	}

	/**
	 * price set field for price entry
	 * @param price set field for price entry
	 */
	public void setPrice(JTextField price) {
		this.price = price;
	}

	/**
	 * field for date added entry
	 * @return field for date added entry
	 */
	public JTextField getDateAdded() {
		return dateAdded;
	}

	/**
	 *  set field for date added entry
	 * @param dateAdded set field for date added entry
	 */
	public void setDateAdded(JTextField dateAdded) {
		this.dateAdded = dateAdded;
	}

	/**
	 *  Add item dialog panel component
	 * @return Add item dialog panel component
	 */
	public JPanel getAddView() {
		return addView;
	}

	/**
	 * set add item dialog panel component
	 * @param addView set add item dialog panel component
	 */
	public void setAddView(JPanel addView) {
		this.addView = addView;
	}

	/**
	 *  current custom default list model of type Item
	 * @return current custom default list model of type Item
	 */
	public DefaultListModel<Item> getListModel() {
		return listModel;
	}

	/**
	 * set current default list model of type item
	 * @param listModel set current default list model of type item
	 */
	public void setListModel(DefaultListModel<Item> listModel) {
		this.listModel = listModel;
	}

	/**
	 * add item window
	 * @return add item window
	 */
	public JDialog getAddItemDialog() {
		return addItemDialog;
	}

	/**
	 * set add item window
	 * @param addItemDialog set add item window
	 */
	public void setAddItemDialog(JDialog addItemDialog) {
		this.addItemDialog = addItemDialog;
	}

	/**
	 * main app window frame
	 * @return main app window frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * set main app window frame
	 * @param frame set main app window frame
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * edit item window
	 * @return edit item window
	 */
	public JDialog getEditItemDialog() {
		return editItemDialog;
	}

	/**
	 * set edit item window
	 * @param editItemDialog set edit item window
	 */
	public void setEditItemDialog(JDialog editItemDialog) {
		this.editItemDialog = editItemDialog;
	}
	
	/**
	 * JPanel that add item window will use.
	 * @return JPanel that add item window will use.
	 */
	public JPanel getAddItemPanel() {
		return addItemPanel;
	}

	/**
	 * set JPanel that add item window will use
	 * @param addItemPanel set JPanel that add item window will use
	 */
	public void setAddItemPanel(JPanel addItemPanel) {
		this.addItemPanel = addItemPanel;
	}
	/**
	 * create new instance of add item window
	 */
	public void newAddItemPanel() {
		this.addItemPanel = new addItemWindow();
	}

	/**
	 * JPanel that edit item window will use
	 * @return JPanel that edit item window will use
	 */
	public JPanel getEditCurrentItem() {
		return editCurrentItem;
	}

	/**
	 * set JPanel that edit item window will use
	 * @param editCurrentItem set JPanel that edit item window will use
	 */
	public void setEditCurrentItem(JPanel editCurrentItem) {
		this.editCurrentItem = editCurrentItem;
	}
	/**
	 * create new instance of edit item window
	 */
	public void newEditCurrentItem() {
		this.editCurrentItem = new editItemWindow();
	}
	
	/**
	 * @param source locations containing source element object
	 */
	public void setItemListForItemViewGUI(String source) {
		GUIarrayList = model.getItemList(source);
	}

	/**
	 * @return arrayList for use in rebuilding GUI
	 */
	public Object getItemListForItemViewGUI() {
		return GUIarrayList ;
	}

	/**
	 * @return the jListframe
	 */
	public ItemViewGUI getJListframe() {
		return JListframe;
	}

	/**
	 * @param jListframe the jListframe to set
	 */
	public void setJListframe(ItemViewGUI jListframe) {
		JListframe = jListframe;
	}	
	
	public void newJListframe(String filter) {
		this.JListframe = new ItemViewGUI(filter);
	}


}

