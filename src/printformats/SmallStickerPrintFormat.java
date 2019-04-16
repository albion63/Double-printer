package printformats;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.print.PageFormat;
import java.util.HashMap;
import java.util.ArrayList;


/**
 * This class is used to print data in a fixed format.
 * 
 * Because this format is not going to be changed, for simplicity, a lot of it was hard coded
 * rather than creating complex mechanisms for customizable format.
 * 
 * @author Stefan Boskovic
 * */
public class SmallStickerPrintFormat {
	private Graphics g;
	private Graphics2D g2d;
	private PageFormat pf;
	
	private int startHeight = 0;
	private Font headerFont;
	private Font textFont;
	private Font lastLineFont;
	ArrayList<ArrayList<String>> printFormat;
	ArrayList<ArrayList<String>> data;
	private static final int avgFontSize = 20;
	private static final int minFontSize = 15;
	private static final int maxFontSize = 25;
	
	public SmallStickerPrintFormat(ArrayList<ArrayList<String>> printFormat2, ArrayList<ArrayList<String>> data, Graphics g, PageFormat pf) {
		this.g = g;
		this.pf = pf;
		g2d = (Graphics2D)g;
	    g2d.translate(pf.getImageableX(), pf.getImageableY());
	    
	    this.printFormat = printFormat2;
	    this.data = data;

	    createFonts();
		initDimensions();
	}

	public void print() {
		printHeader();
		printData();
	}
	
	private void printHeader() {
		g2d.setFont(headerFont);
		String toBePrinted = printFormat.get(0).get(0);
		g.drawString(toBePrinted, getMiddleWidth(true, toBePrinted), startHeight);
	}
	
	private void printData() {
		int i = maxFontSize, count = 0;
		String toBePrinted = "";
		for (ArrayList<String> line : data) {
			g2d.setFont(textFont);
			if (count == data.size() - 1) {
				g2d.setFont(lastLineFont);
				i+= maxFontSize - minFontSize;
			}
			StringBuilder allLineData = new StringBuilder("");
			for (String s : line)
				allLineData.append(s + " ");
			toBePrinted = allLineData.toString();
			g.drawString(toBePrinted, getMiddleWidth(false, toBePrinted), startHeight + i);
			i+= minFontSize;
			count++;
		}
	}
	
	private int getMiddleWidth(boolean first, String s) {
		int actualLen = 0;
		if (first) {
			FontMetrics fm = g.getFontMetrics(headerFont);
			actualLen = fm.stringWidth(s);
		}
		else {
			FontMetrics fm = g.getFontMetrics(textFont);
			actualLen = fm.stringWidth(s);
		}
		return (int) (pf.getImageableWidth() / 2 - actualLen / 2);
	}
	

	private void initDimensions() {
		startHeight = (int) (pf.getImageableHeight() / 2 - (printFormat.size() * avgFontSize) / 2);
	}
	
	private void createFonts() {
		HashMap<TextAttribute, Number> map = new HashMap<TextAttribute, Number>();
	    map.put(TextAttribute.SIZE, 8);
	    map.put(TextAttribute.FAMILY, Font.TRUETYPE_FONT);
	    headerFont = new Font(map);
	    
	    map.put(TextAttribute.SIZE, 12);
	    map.put(TextAttribute.FAMILY, Font.PLAIN);
	    textFont = new Font(map);
	    
	    map.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRABOLD);
	    lastLineFont = new Font(map);
	}
}
