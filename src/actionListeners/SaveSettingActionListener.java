package actionListeners;

import java.io.FileNotFoundException;

import application.Main;
import javafx.event.ActionEvent;
import services.Recorder;
/**
 * Action listener that is called on Save settings button press.
 * It's job is to save data about printer folder and default printers.
 * 
 * @author Stefan Boskovic
 */
public class SaveSettingActionListener extends MyActionListener {
	public SaveSettingActionListener(Main allData) {
		super(allData);
	}
	
	@Override
	public void handle(ActionEvent event) {
		extractData();	// must be called
		saveSettings();
	}

	private void saveSettings() {
		StringBuilder settings = new StringBuilder(sourceFolder.getAbsolutePath());
    	settings.append("~" + smallStickerPrinterChoice.getValue());
    	settings.append("~" + a5PrinterChoice.getValue());
    	Recorder r = new Recorder("config.txt");
    	try {
			r.writeToConfigFile(settings.toString().split("~"));
		} catch (FileNotFoundException e) {
			message.setText("Configuration file not found!");
		}
    	message.setText("Settings saved");
	}
}
