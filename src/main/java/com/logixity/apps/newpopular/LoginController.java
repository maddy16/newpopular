package com.logixity.apps.newpopular;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.logixity.apps.newpopular.db.DatabaseHandler;
import com.logixity.apps.newpopular.models.User;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
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
        final String username = uname.getText();
        final String password = pass.getText();

        loginBtn.setDisable(true);
        uname.setDisable(true);
        pass.setDisable(true);
        final Task task = new Task() {
            @Override
            protected Object call() {
                try {
                    return db.isValidUser(username, password);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return null;
                }

            }
        };
        task.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event event) {
                Object response = task.getValue();
                if (response != null) {
                    User user = (User) response;

                    showStatus("Welcome Mr. " + user.getFullName(), false);
                    MainController.user = user;

                    Task<Void> sleeper = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                            return null;
                        }
                    };
                    sleeper.setOnSucceeded(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            try {
                                showNextScene("Scene");
                            } catch (IOException ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                    });
                    new Thread(sleeper).start();

                } else {
                    loginBtn.setDisable(false);
                    uname.setDisable(false);
                    pass.setDisable(false);
                    showStatus("Invalid Username / Pass", false);

                }

            }
        }
        );
        new Thread(task)
                .start();

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
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.setMaximized(true);
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
