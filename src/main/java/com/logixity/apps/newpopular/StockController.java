/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logixity.apps.newpopular;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class StockController implements Initializable {

    @FXML
    private AnchorPane contentPane;
    
    @FXML
    private VBox sideBar;
    ResourceBundle bundle;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bundle = rb;
    }

    @FXML
    void newStockBtnClicked(ActionEvent event) throws IOException {
        toggleButton(event);
        Main.loadScene("/fxml/newStockScene.fxml", this, contentPane, bundle);

    }
    
    void toggleButton(ActionEvent event) {
        resetSideButtons();
        JFXButton btn = (JFXButton) event.getSource();
        btn.setStyle("-fx-background-color: #4CAF50;");
    }

    void resetSideButtons() {
        sideBar.getChildren().forEach((child) -> {
            child.setStyle("-fx-background-color:  #5C6BC0;");
        });
    }

}
