package printers;

import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;

import javax.print.PrintService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 * This class prints multiple PDF files on designated printer.
 * Side note printing of PDF's is achieved using open-source library apache.pdfbox
 *
 * @author Stefan Boskovic
 */
public class PDFPrinter {
	private ArrayList<File> pdfFiles;
	private String printer;
	
	public PDFPrinter(String printer, ArrayList<File> pdfFiles) {
		this.printer = printer;
		this.pdfFiles = pdfFiles;
	}
	
	public void print() throws Exception  {
		for (File file : pdfFiles) {
			PDDocument document = PDDocument.load(new File(file.getAbsolutePath()));
			PrintService pdfPrinter = SmallStickerPrinter.getPrinter(printer);
			
			PrinterJob job = PrinterJob.getPrinterJob();
	        job.setPageable(new PDFPageable(document));
	        job.setPrintService(pdfPrinter);
	        job.print();
	        document.close();
		}
	}
}