package com.logixity.apps.newpopular;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.sun.glass.ui.Application;
import com.logixity.apps.newpopular.db.DatabaseHandler;
import com.logixity.apps.newpopular.Main;
import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    private DatabaseHandler db;
    @FXML
    private JFXTextField uname;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXButton exitBtn;
    @FXML
    private Label statusMsg;

    @FXML
    private JFXSpinner statusSpinner;

    @FXML
    public void performLogin(ActionEvent event) throws IOException {
        showStatus("Authenticating... Please wait.", true);
        final String user = uname.getText();
        final String password = pass.getText();

        loginBtn.setDisable(true);
        uname.setDisable(true);
        pass.setDisable(true);
        final Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return db.isValidUser(user, password);
            }
        };
        task.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event event) {
                boolean success = (boolean) task.getValue();
                if (success) {
                    try {
                        showStatus("Welcome " + user, false);
                        showNextScene("Scene");
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                } else {
                    loginBtn.setDisable(false);
                    uname.setDisable(false);
                    pass.setDisable(false);
                    showStatus("Invalid Username / Pass", false);

                }
            }
        });
        new Thread(task).start();

    }

    private void showNextScene(String sceneName) throws IOException {
        Stage stage = Main.getMainStage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + sceneName + ".fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Home - New Popular Military Store");
        stage.setScene(scene);
        stage.show();
        stage.resizableProperty().setValue(Boolean.TRUE);
        stage.setMinWidth(765);
        stage.setMinHeight(520);
        stage.setWidth(765);
        stage.setHeight(520);
    }

    void showStatus(String msg, boolean loading) {
        statusSpinner.setVisible(loading);
        statusMsg.setText(msg);
        statusMsg.setVisible(true);
    }

    void hideStatus() {
        statusSpinner.setVisible(false);
        statusMsg.setVisible(false);
    }

    @FXML
    public void exitApp(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hideStatus();
        db = DatabaseHandler.getDatabaseHandler();
    }
}
