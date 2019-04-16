package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.Main;

/**
 * This class is used to load configuration data into Main JavaFX window,
 * and small sticker format into CSVProcessor
 * @author Stefan Boskovic
 */
public class ConfigFilesHandeler { 
	private static final int configFileNoLines = 3;
	private Main app;
	
	public ConfigFilesHandeler(Main main) {
		this.app = main;
	}
	
	public void loadConfigData() {
        try {
        	Recorder r = new Recorder("config.txt");
        	ArrayList<ArrayList<String>> settings = r.readConfigFile();
        	if (settings.size() < configFileNoLines)
        		return;
        	app.setSourceFolder(new File(settings.get(0).get(0)));
        	app.getSmallStickerPrinterChoice().setValue(settings.get(1).get(0));
        	app.getA5PrinterChoice().setValue(settings.get(2).get(0));
        } catch (IOException e) {
        	app.getMessage().setText("Configuration file not found!");
        }
    }
    
    public void loadSmallStickerFormat() {
    	try {
        	Recorder r = new Recorder("smallStickerFormat.txt");
        	ArrayList<ArrayList<String>> format = r.readConfigFile();
			CSVProcessor.setSmallStickerFormat(format);
		} catch (IOException e) {
			app.getMessage().setStyle("-fx-text-fill: #ff0000;");
			app.getMessage().setText("Error! Small sticker format not found.");
		}
    }
}
