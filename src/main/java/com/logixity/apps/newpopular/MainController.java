/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logixity.apps.newpopular;

import com.logixity.apps.newpopular.models.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ahmed
 */
public class MainController implements Initializable {
 
    static MainController instance;
    static User user;
    @FXML
    private Label unameLabel;

    @FXML
    private Label error;
    @FXML
    private AnchorPane contentPane;
    ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        instance = this;
        bundle = rb;
        hideError();
        unameLabel.setText(user.getFullName());
    }
    
    @FXML
    void ordersClicked(ActionEvent event) throws IOException {
        Main.loadScene("/fxml/ordersScene.fxml", null, contentPane, bundle);
        showSuccess("Success Message");
    }


    public void showError(String msg) {
        error.getStyleClass().remove("success");
        error.getStyleClass().add("error");
        error.setText(msg);
        error.setVisible(true);
    }

    public void showSuccess(String msg) {
        error.getStyleClass().add("success");
        error.getStyleClass().remove("error");
        error.setText(msg);
        error.setVisible(true);
        final Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Thread.sleep(1000);
                return null;
            }

        };
        task.setOnSucceeded((event) -> {
            error.setVisible(false);

        });
        new Thread(task).start();
    }

    public void hideError() {
        error.setText("");
        error.setVisible(false);
    }
}
