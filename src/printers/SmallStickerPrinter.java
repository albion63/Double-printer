package printers;
import java.awt.Graphics;
import java.awt.print.*;
import java.util.ArrayList;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import printformats.SmallStickerPrintFormat;

/**
 * This class prints data extracted form CSV file onto a printer in following format:
 * 
 * 	Company Name
 *  
 *  Data
 *  
 *  <b> Last line of data<b>
 *  
 *  Note that data format is stored in SmallStickerFormat.txt and
 *  data format consists of which columns will be placed in which row  
 *  
 *  @author Stefan Boskovic
 */
public class SmallStickerPrinter implements Printable {
	private PrintService printer;
	private ArrayList<ArrayList<String>> data;
	public ArrayList<ArrayList<String>> printFormat;
	
	public SmallStickerPrinter(String pr, ArrayList<ArrayList<String>> smallStickerFormat, ArrayList<ArrayList<String>> page) { 
		printer = getPrinter(pr);
		this.data = page;
		this.printFormat = smallStickerFormat;
	}

	public void print() throws PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		Book book = new Book();// java.awt.print.Book
		PageFormat pf = job.defaultPage();
		book.append(this, pf);
		job.setPageable(book);

		// setDimensions
		pf.setOrientation(PageFormat.LANDSCAPE);
		Paper p = pf.getPaper();
		p.setSize(4.05 * 72, 6.2 * 72);
		p.setImageableArea(0, 0, 4.05 * 72, 6.2 * 72);
		pf.setPaper(p);

		job.setPrintService(printer);
		job.print();
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		if (page > 0) {
			return NO_SUCH_PAGE;
		}
		// Fixed format class for printing data
		SmallStickerPrintFormat printForm = new SmallStickerPrintFormat(printFormat, data, g, pf);
		printForm.print();

		return PAGE_EXISTS;
	}

	static public PrintService getPrinter(String printer) {
		String junk[] = printer.split(": ");
		if (junk.length > 1)
			printer = junk[1];
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		for (PrintService p : printServices) {
			if (p.getName().equals(printer))
				return p;
		}
		return null;
	}
}
