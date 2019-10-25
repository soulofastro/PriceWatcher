package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Model.Item;
/**
 * 2D graphics GUI partial implementation. 
 * A special panel to display the detail of an item.
 * 
 * Date: 4/20/2019
 * 
 * This is an application that tracks the price of online items.
 * 
 * @author MK Babst
 * 
 * @version 0.1

 * CS 3331 
 * 
 * Homework 3
 * 
 */
@SuppressWarnings({ "serial", "unused" })
public class ItemView extends JPanel {
    
	private Font boldFont = new java.awt.Font("Sans serif", Font.BOLD, 12); 
    private Font regularFont = new java.awt.Font("Sans serif", Font.PLAIN, 12); 
	
//	/** Interface to notify a click on the view page icon. */
//	public interface ClickListener {
//		
//		/** Callback to be invoked when the view page icon is clicked. */
//		void clicked();
//	}
//	
//	/** Directory for image files: src/image in Eclipse. */
//	@SuppressWarnings("unused")
//	private final static String IMAGE_DIR = "/image/";
//        
//	/** View-page clicking listener. */
//    private ClickListener listener;
    
    private Item itemInstance;
    
    /** 
     * Create a new instance of ItemView. 
     * @param item for each item, it's itemview is stored for use in MainMenu-ItemViewGUI-ItemRenderer
     */
    public ItemView(Item item) {
    	itemInstance = item;
    	setPreferredSize(new Dimension(350, 125));
        setBackground(Color.WHITE);
//        addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//            	if (isViewPageClicked(e.getX(), e.getY()) && listener != null) {
//            		listener.clicked();
//            	}
//            }
//        });
    }
    /**
     * not used in this app, but kept for posterity.
     */
    public ItemView() {
    	setPreferredSize(new Dimension(350, 125));
        setBackground(Color.WHITE);
//        addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//            	if (isViewPageClicked(e.getX(), e.getY()) && listener != null) {
//            		listener.clicked();
//            	}
//            }
//        });
    }
        
//    /** Set the view-page click listener. */
//    public void setClickListener(ClickListener listener) {
//    	this.listener = listener;
//    }
    
    /** 
     * Overridden here to display the details of the item. */
    @Override
	public void paintComponent(Graphics g) {
    	Item item;
    	item = itemInstance;
        super.paintComponent(g); 
        //Dimension dim = getSize();
        //--aided in determining where to click to open webpage
        //g.setColor(Color.green);
        //g.fillRect(10, 10, 40, 25);
        g.setColor(Color.black);
        int x = 16, y = 10, z = 100;
        if(item.getSource().contentEquals("thinkgeek")) {
        	g.drawImage(getImage(getCodeBase(),"x_images/thinkgeek.jpg"), x-10, y, this);
        }
        else if(item.getSource().contentEquals("UTEP Bookstore")) {
        	g.drawImage(getImage(getCodeBase(),"x_images/utep.gif"), x-10, y, this);
        }
        else if(item.getSource().contentEquals("eBay")) {
        	g.drawImage(getImage(getCodeBase(),"x_images/ebay.png"), x-10, y, this);
        }
        else {
        	g.drawImage(getImage(getCodeBase(),"x_icons/image.png"), x-10, y, this);
        }
        x += 30;
        y += 10;
        g.drawString("Name:   ", x, y);
        g.setFont(boldFont);
        g.drawString(item.getname(), z, y);
        g.setFont(regularFont);
        y += 20;
        g.drawString("URL:   ", x, y);
        g.drawString(item.getURL(), z, y);
        y += 20;
        g.drawString("Price:   ", x, y);
        g.setColor(Color.blue);
        g.drawString("$" + item.getprice(), z, y);
        y += 20;
        g.setColor(Color.black);
        g.drawString("Change:   ", x, y);
        if(Double.parseDouble(item.getchange())>0) {
        	g.setColor(Color.red);
        }
        else if (Double.parseDouble(item.getchange())<0){
        	g.setColor(Color.green);
        }
        g.drawString(item.getchange()+"%", z, y);
        g.setColor(Color.black);
        y += 20;
        g.drawString("Added:   ", x, y);
        g.drawString(item.getDateAdded() + " ($"+ item.getoriginalPrice()+")", z, y);
        y += 20;
        //--
        
    }

//	/** Return true if the given screen coordinate is inside the viewPage icon. */
//    private boolean isViewPageClicked(int x, int y) {
//    	//--
//    	//-- WRITE YOUR CODE HERE
//    	if(x>=10 && x<=40 && y>=10 && y<=25 ) {
//    		return true;
//    	}
//    	//--
//    	else
//    		return new Rectangle(20, 20, 30, 20).contains(x,  y);
//    }
        
    /*
     *  Return the image stored in the given file. 
	 *  Stuff taken from NoApplet so I can grab pictures from image package **/
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
}