package net.prokhyon.modularfuzzy.shell.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class FxDialogHelper {


    public static void informationDialogHelper(String title, String header, String content1, String content2, String labelStr, Alert.AlertType alertType){

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content1);

        Label label = new Label(labelStr);

        TextArea textArea = new TextArea(content2);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static void informWarningDialog(String title, String header, String content){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void informErrorWithStacktraceDialog(Exception ex, String title, String header, String content){

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        informationDialogHelper(title, header, content, exceptionText, "The exception stacktrace was:", Alert.AlertType.ERROR);
    }

    public static File saveFilesIntoDirectoryDialog(Stage stage) {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        return selectedDirectory;
    }

    public static File saveFileDialog(Stage stage, String initialFileName, String ... extensions) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Model File(s)");
        fileChooser.setInitialFileName(initialFileName);
        ExtensionHelper.applyExtensionsOnFileChooser(fileChooser, extensions);
        //fileChooser.setSelectedExtensionFilter();
        File file = fileChooser.showSaveDialog(stage);
        return file;
    }

}
