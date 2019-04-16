package actionListeners;

import java.io.File;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public abstract class MyActionListener implements EventHandler<ActionEvent> {
	protected Main allData;
	protected File sourceFolder;
	protected ChoiceBox<String> a5PrinterChoice;
	protected ChoiceBox<String> smallStickerPrinterChoice;
	protected TextArea message;
	
	public MyActionListener(Main allData) {
		this.allData = allData;
	}
	
	/**
	 * This function extracts data from JavaFX window, 
	 * which is why it must be called at the beginning of handle method in implementing subclasses.
	 */
	protected void extractData() {
    	this.sourceFolder = allData.getSourceFolder();
    	this.a5PrinterChoice = allData.getA5PrinterChoice();
    	this.smallStickerPrinterChoice = allData.getSmallStickerPrinterChoice();
    	this.message = allData.getMessage();
    }
}
