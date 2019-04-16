package services;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFileChooser;

/**
 * Helper class used to select folder that contains files for printing, 
 * selecting files with given file type, and getting file type for given file. 
 *
 * @author Stefan Boskovic
 */
public class FileHandler {
	JFileChooser chooser;
	String path;

	public FileHandler() {
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		path = "";
	}

	public File chooseDir() throws FileNotFoundException {
		File folder = null;
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			folder = chooser.getSelectedFile();			
			path = folder.toString();
		}
		else {
			path = "No file was selected";
		}
		return folder;
	}
	
	public ArrayList<File> getFilesWithExtension(File folder, String extension) {
    	ArrayList<File> res = new ArrayList<File>();
    	File allFiles[] = folder.listFiles();
    	for (File f : allFiles) {
    		if (getFileExtension(f).equals(extension))
    			res.add(f);
    	}
    	return res;
    }
    
    private String getFileExtension(File file) {
    	StringBuilder res = new StringBuilder();
    	String fileName = file.getName();
    	Stack<Character> stack = new Stack<>();
    	for (int i = fileName.length() - 1; i >= 0; i--) {
    		if (fileName.charAt(i) == '.')
    			break;
    		stack.add(fileName.charAt(i));
    	}
    	while (!stack.empty())
    		res.append(stack.pop());
    	return res.toString();
    }
}