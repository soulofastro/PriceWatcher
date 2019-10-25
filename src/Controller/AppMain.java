package Controller;

import java.io.IOException;

import Model.FileItemManager;
import Model.ItemManager;
import View.MainMenu;

/**
 * The is the main class that brings the Model, View, and Controller together.
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
 * Homework 4
 * 
 * 
 */
public class AppMain {
    /**
     * This main method generates the price watcher frame and implements MVC format
     * 
     * @param args is null and unused.
     * @throws IOException when provided urls are malformed or unable to connect to web
     */
    public static void main(String[] args) throws IOException {
    	
    	/** Load Model-View-Control structure **/
    	FileItemManager m = new FileItemManager();
    	MainMenu v = new MainMenu(m);
    	Controller c = new Controller(v,m);
    	c.initController();
    }
}
