package services;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.InvalidCSVFormat;
import printers.SmallStickerPrinter;

/**
 * This class processes multiple CSV files at the time.
 * For each file process is as follows:
 * 1. All required data for current file is selected form file using CSVParser class
 * 2. Data is printed on designated printer using SmallStickerPrinter class
 * 
 * @author Stefan Boskovic
 */
public class CSVProcessor {
	private String printer;
    private ArrayList<File> files;
    private static ArrayList<ArrayList<String>> smallStickerFormat;	// First row contains owner's company name, and is not contained in CSV's 
    private static ArrayList<ArrayList<String>> requestedColumns;		// This list contains all columns that need to be found in CSV's
    
	public CSVProcessor(String printer, ArrayList<File> csvFiles) {
        this.printer = printer;
        this.files = csvFiles;
    }
	
	/**
	 * Extracts data from CSV files, than prints it onto designated printer.
	 * @throws IOException
	 * @throws PrinterException
	 * @throws InvalidCSVFormat
	 */
	public void process() throws IOException, PrinterException, InvalidCSVFormat {
        for (File file : files) {
        	CSVParser parser = new CSVParser(requestedColumns);
        	ArrayList<ArrayList<ArrayList<String>>> r = parser.getRequestedData(file);
            for (ArrayList<ArrayList<String>> page : r) {
                SmallStickerPrinter dpd = new SmallStickerPrinter(printer, smallStickerFormat, page);
                dpd.print();
            }
        }
    }
	
	public static void setSmallStickerFormat(ArrayList<ArrayList<String>> format) {
		smallStickerFormat = format;
		requestedColumns = new ArrayList<ArrayList<String>>(format);
		requestedColumns.remove(0);
	}
}
