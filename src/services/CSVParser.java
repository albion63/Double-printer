package services;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.InvalidCSVFormat;

/**
 * This class parses single CSV file, selecting requested columns from CSV file
 * and returning it as list of data formated into a rows (lists), as specified in SmallStickerFormat.txt
 *
 * @author Stefan Boskovic
 */
public class CSVParser {
    private ArrayList<ArrayList<String>> smallStickerFormat;

    public CSVParser(ArrayList<ArrayList<String>> requestedColumns) {
        this.smallStickerFormat = requestedColumns;
    }

    /**
     * Note that single CSV can contain data (rows) for multiple customers.
     * @param printerFolder
     * @return {ArrayList<ArrayList<ArrayList<String>>>}
     * Each row in CSV file is stored as list of lists:
     * Column1Row1 -> Column2Row1 ... ColumnXRow1
     * Column1Row2 -> Column2Row2 ... ColumnXRow2
     * ...
     * Column1RowN -> Column2RowN ... ColumnXRowN
     * 
     * And all rows in CSV file are linked into a list
     * @throws IOException
     * @throws InvalidCSVFormat
     */
	@SuppressWarnings("resource")
	public ArrayList<ArrayList<ArrayList<String>>> getRequestedData(File printerFolder)
            throws IOException, InvalidCSVFormat {


		ArrayList<ArrayList<ArrayList<String>>> result = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(printerFolder), "ISO_8859_1"));

        String line = br.readLine();
        if (line == null) {
			throw new InvalidCSVFormat("");
		}
        
        HashMap<String, Integer> indexes = getIndexes(line);

        while ((line = br.readLine()) != null) {
            String[] cells = getCells(line);
            ArrayList<ArrayList<String>> rowData = new ArrayList<>();
            for (ArrayList<String> currLine : smallStickerFormat) {
            	ArrayList<String> formatRowData = new ArrayList<String>();
            	for (String requestCell : currLine) {
            		int index = indexes.get(requestCell);
            		formatRowData.add(cells[index]);
            	}
            	rowData.add(formatRowData);
            }
            result.add(rowData);
        }
        br.close();

        return result;
    }

    private HashMap<String, Integer> getIndexes(String line) {
        HashMap<String, Integer> res = new HashMap<>();
        String cells[] = getCells(line);
        int i = 0;
        for (String cell : cells) {
            if (checkCellExists(cell))
                res.put(cell, i);
            i++;
        }

        return res;
    }
    
    private String[] getCells(String line) {
        String cells[] = line.split("\";\"");
        if (cells.length == 1)
            cells = line.split(";");
        return cells;
    }

    private boolean checkCellExists(String cell) {
        for (ArrayList<String> line : smallStickerFormat) {
        	for (String req : line)
	            if (cell.equals(req))
	                return true;
        }
        return false;
    }
}
