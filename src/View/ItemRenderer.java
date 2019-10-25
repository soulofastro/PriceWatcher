package View;
import java.awt.Color;
import java.awt.Component; 
import javax.swing.ImageIcon; 
import javax.swing.JLabel; 
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import Model.Item;  
/**
 * custom list cell renderer that displays the paintcomponent part of each items associated itemView
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
@SuppressWarnings({ "serial", "unused" })
public class ItemRenderer extends JLabel implements ListCellRenderer<Item>{
	
	public ItemRenderer() {
		setOpaque(false);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Item> list, Item item, int index, boolean isSelected,
			boolean cellHasFocus) {

		
        if (isSelected) { 
            setBackground(list.getSelectionBackground()); 
            setForeground(list.getSelectionForeground()); 
            item.view.setBackground(Color.LIGHT_GRAY);
        } 
        else { 
            setBackground(list.getBackground()); 
            setForeground(list.getForeground()); 
            item.view.setBackground(Color.WHITE);
        } 
        

		return item.view;
	}

}
