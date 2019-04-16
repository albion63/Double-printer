package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.ConfigFilesHandeler;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import actionListeners.PrintActionListener;
import actionListeners.SaveSettingActionListener;
import actionListeners.SetSourceFolderActionListener;

import java.io.*;

/**
 * Main JavaFX application window, plus some small helper functions.
 * 
 * @author Stefan Boskovic
 */
public class Main extends Application {
    private double width = 500;
    private double rightWidth = 335;
    private double leftWidth = 135;
    private double height = 300;
    Stage window;

    private File sourceFolder;
    private TextArea message;
    private ChoiceBox<String> smallStickerPrinterChoice;
    private ChoiceBox<String> a5PrinterChoice;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setMinHeight(height);
        window.setMinWidth(width);
        window.setTitle("Double printer");

        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // First row - Path to print folder
        Label folderLabel = new Label("Printer folder:");
        GridPane.setConstraints(folderLabel, 0, 0);

        Button folderButton = new Button("Choose");
        //folderButton.setOnAction(e -> setSourceFolder());
        GridPane.setConstraints(folderButton, 1, 0);

        // Second row - Small sticker printer
        Label smallStickerLabel = new Label("Sticker printer:");
        smallStickerLabel.setMinWidth(leftWidth);
        GridPane.setConstraints(smallStickerLabel, 0, 1);

        smallStickerPrinterChoice = new ChoiceBox<>();
        smallStickerPrinterChoice.setMinWidth(rightWidth);
        addPrinters(smallStickerPrinterChoice);
        GridPane.setConstraints(smallStickerPrinterChoice, 1, 1);

        // Third row - A5 (PDF) printer
        Label a5PrinterLabel = new Label("A5 (PDF) printer:");
        GridPane.setConstraints(a5PrinterLabel, 0, 2);

        a5PrinterChoice = new ChoiceBox<>();
        a5PrinterChoice.setMinWidth(rightWidth);
        addPrinters(a5PrinterChoice);
        GridPane.setConstraints(a5PrinterChoice, 1, 2);

        // Fourth row - save settings
        Button saveSettingsButton = new Button("Save settings");
        GridPane.setConstraints(saveSettingsButton, 0, 4);

        // Fifth row - message
        Label messageLabel = new Label("Message: ");
        GridPane.setConstraints(messageLabel, 0, 5);

        message = new TextArea("Ready.");
        message.setMinWidth(rightWidth);
        message.setWrapText(true);
        GridPane.setConstraints(message, 1, 5);
        GridPane.setRowSpan(message, 2);

        // Sixth row - print button
        Button printButton = new Button("Print");
        printButton.setMinSize(leftWidth + rightWidth + 10, 35);
        GridPane.setConstraints(printButton, 0, 7);
        GridPane.setFillWidth(printButton, true);
        GridPane.setColumnSpan(printButton, 2);
        GridPane.setValignment(printButton, VPos.CENTER);

        //Add everything to grid
        grid.getChildren().add(printButton);
        grid.getChildren().addAll(folderLabel, folderButton, smallStickerLabel, smallStickerPrinterChoice);
        grid.getChildren().addAll(a5PrinterLabel, a5PrinterChoice);
        grid.getChildren().add(saveSettingsButton);
        grid.getChildren().add(messageLabel);
        grid.getChildren().addAll(message);

        Scene scene = new Scene(grid, width, height);
        
        // Load configuration data
        ConfigFilesHandeler cf = new ConfigFilesHandeler(this);
        cf.loadConfigData();
        // Load print format from a file
        cf.loadSmallStickerFormat();
        
        // Add action listeners
        folderButton.setOnAction(new SetSourceFolderActionListener(this));
        saveSettingsButton.setOnAction(new SaveSettingActionListener(this));
        printButton.setOnAction(new PrintActionListener(this));
        
        window.setScene(scene);
        window.show();
        window.toFront();
    }

    private void addPrinters(ChoiceBox<String> box) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        boolean first = true;
        for (PrintService printer : printServices) {
            box.getItems().add(printer.toString());
            if (first) {
                box.setValue(printer.toString());
                first = false;
            }
        }
    }

	public File getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(File sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public TextArea getMessage() {
		return message;
	}

	public void setMessage(TextArea message) {
		this.message = message;
	}

	public ChoiceBox<String> getSmallStickerPrinterChoice() {
		return smallStickerPrinterChoice;
	}

	public void setSmallStickerPrinterChoice(ChoiceBox<String> smallStickerPrinterChoice) {
		this.smallStickerPrinterChoice = smallStickerPrinterChoice;
	}

	public ChoiceBox<String> getA5PrinterChoice() {
		return a5PrinterChoice;
	}

	public void setA5PrinterChoice(ChoiceBox<String> a5PrinterChoice) {
		this.a5PrinterChoice = a5PrinterChoice;
	}
}