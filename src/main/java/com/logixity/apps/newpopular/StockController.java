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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class StockController implements Initializable {

    Stage dialogStage;
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
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StockController.class.getResource("/fxml/newStockScene.fxml"));
            loader.setController(this);
            loader.setResources(bundle);
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            dialogStage = new Stage();
            dialogStage.setTitle("New Stock Entry");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(Main.getMainStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.resizableProperty().setValue(Boolean.FALSE);
//            searchByCombo.getItems().add("Service Number");
//            searchByCombo.getItems().add("CPR Number");
//            searchByCombo.getSelectionModel().select(0);

//            controller.setPerson(person);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

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
