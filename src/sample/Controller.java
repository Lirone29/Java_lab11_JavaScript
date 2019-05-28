package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller {
    @FXML
    private ScrollPane outputPanel;

    @FXML
    private Label outputLabel;

    @FXML
    private ScrollPane InputPanel;

    @FXML
    private ComboBox<String> chooseBox;

    @FXML
    private Label inputLabel;

    @FXML
    private Button formatButton;

    @FXML
    private TextArea inputTextArea;

    //@FXML
   // private TextArea outputTextArea;

    @FXML
    private WebView outputWebView;

    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    private List<String> files = null;

    ObservableList<String> options;
    FileChooser fs;


    public void initialize() {
        files = getJSFiles();
        options = FXCollections.observableArrayList(files);
        chooseBox.setItems(options);
        //outputTextArea.se
        //outputTextArea.setContentType("text/html");

        try {
            engine.eval(new FileReader("moralizator.js"));
        } catch (ScriptException | FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private List<String> getJSFiles(){
        File dir = new File(".");
        File [] files = dir.listFiles((File dir1, String name1) -> name1.endsWith(".js"));
        LinkedList<String> result = new LinkedList<String>();
        assert files != null;
        result.add("None");
        for (File file : files) {
            result.push(file.getName());
        }

        return result;
    }

    public void getChooseBoxAction() {
        String converterName = chooseBox.getValue();

        assert converterName != null;
        if (converterName.equals("None")){
            return;
        }

        try {
            engine.eval(new FileReader(converterName));
        } catch (FileNotFoundException | ScriptException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void formatHandler() {
        if (Objects.requireNonNull(chooseBox.getValue()).equals("None")){
            outputWebView.getEngine().loadContent(inputTextArea.getText());
            //outputTextArea.setText(inputTextArea.getText());
            //outputTextArea.setUserData(inputWebView.getUserData());
            return;
        }

        outputWebView.getEngine().loadContent("");
        //outputTextArea.setText("");
        Invocable invocable = (Invocable) engine;

        Object result = null;
        try {
            result = invocable.invokeFunction("process", inputTextArea.getText());
        } catch (ScriptException | NoSuchMethodException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        assert result != null;
        outputWebView.getEngine().loadContent(result.toString());
        //outputTextArea.setText(result.toString());

    }
}
