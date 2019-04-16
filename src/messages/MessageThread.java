package messages;

import javafx.scene.control.TextArea;

/**
 * This class is used to set given message to designated messageArea, after certain amount of time. 
 * Goal is to make front-end look responsive.
 * 
 * @author Stefan Boskovic
 */
public class MessageThread extends Thread {
	private TextArea messageArea;
	private String message;
	private int sleep;
	private String textColor;
	
	public MessageThread(TextArea msgArea, String message, int sleep) {
		this.messageArea = msgArea;
		this.message = message;
		this.sleep = sleep;
		textColor = "#000000;";
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(sleep);
			messageArea.setText(message);
			messageArea.setStyle("-fx-text-fill: " + textColor);
		} catch (InterruptedException e) {
			messageArea.setStyle("-fx-text-fill: #ff0000;");
			messageArea.setText("Greska u niti!");
		}
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
}