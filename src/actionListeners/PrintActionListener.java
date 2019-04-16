package actionListeners;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import exceptions.InvalidCSVFormat;
import javafx.event.ActionEvent;
import messages.MessageThread;
import printers.PDFPrinter;
import services.CSVProcessor;
import services.FileHandler;

/**
 * Action listener that is called on print button press. It prints data onto designated printers
 * and writes appropriate messages for end user.
 * 
 * @author Stefan Boskovic
 */
public class PrintActionListener extends MyActionListener {
	public PrintActionListener(Main allData) {
		super(allData);
	}

	@Override
	public void handle(ActionEvent event) {
		extractData();	// must be called
		print();
	}
	
	private void print() {
		printCSVs();
        printPDFs();
        message.setText("Completed!");
        message.setStyle("-fx-text-fill: #00cc00");
        MessageThread mt = new MessageThread(message, "Awaiting task.", 2500);
        mt.start();
	}
	
	private void printCSVs() {
    	FileHandler cf = new FileHandler();
    	ArrayList<File> csvFiles = cf.getFilesWithExtension(sourceFolder, "csv");
    	CSVProcessor csvProcessor = new CSVProcessor(smallStickerPrinterChoice.getValue(), csvFiles);
    	try {
    		csvProcessor.process();
			deleteFiles(csvFiles);
		} catch (IOException e) {
			message.setStyle("-fx-text-fill: #ff0000;");
            message.setText("Error processing csv: " + e);
			e.printStackTrace();
		} catch (PrinterException e) {
			message.setStyle("-fx-text-fill: #ff0000;");
            message.setText("Error printing csv: " + e);
			e.printStackTrace();
		} catch (InvalidCSVFormat e) {
			message.setStyle("-fx-text-fill: #ff0000;");
            message.setText("Error invalid csv format: " + e);
			e.printStackTrace();
		}
    }
    
    private void printPDFs() {
    	FileHandler cf = new FileHandler();
    	ArrayList<File> pdfFiles = cf.getFilesWithExtension(sourceFolder, "pdf");
        PDFPrinter pdfPrinter = new PDFPrinter(a5PrinterChoice.getValue(), pdfFiles);
    	try {
			pdfPrinter.print();
			deleteFiles(pdfFiles);
		} catch (Exception e) {
			message.setStyle("-fx-text-fill: #ff0000;");
            message.setText("Error printing pdf: " + e);
			e.printStackTrace();
		}
    }

    private void deleteFiles(ArrayList<File> csvFiles) {
        for (File f : csvFiles) {
            f.delete();
        }
    }
}
