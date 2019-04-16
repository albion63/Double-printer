package actionListeners;

import java.io.FileNotFoundException;

import application.Main;
import javafx.event.ActionEvent;
import services.FileHandler;

/**
 * Action listener that is called on Choose button press.
 * 
 * @author Stefan
 */
public class SetSourceFolderActionListener extends MyActionListener {

	public SetSourceFolderActionListener(Main allData) {
		super(allData);
	}

	@Override
	public void handle(ActionEvent event) {
		extractData();
		setSourceFolder();
	}

	private void setSourceFolder() {
		FileHandler cf = new FileHandler();
        try {
            sourceFolder = cf.chooseDir();
            allData.setSourceFolder(sourceFolder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
}
