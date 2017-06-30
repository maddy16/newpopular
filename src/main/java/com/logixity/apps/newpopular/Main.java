package com.logixity.apps.newpopular;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
    static Stage mainStage;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginScene.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.resizableProperty().setValue(Boolean.FALSE);
//        stage.resizableProperty().setValue(Boolean.TRUE);
        stage.setWidth(540);
        stage.setHeight(433);
//        stage.setMinWidth(765);
//        stage.setMinHeight(520);
        stage.setTitle("Login - New Popular Military Store");
        stage.setScene(scene);
        mainStage = stage;
        stage.show();
    }
    public static Stage getMainStage(){
        return mainStage;
    }
    public static void loadScene(String sceneName, Object controller, AnchorPane contentPane, ResourceBundle bundle) throws IOException {
        resetContentArea(contentPane);
        FXMLLoader loader = null;
        if (bundle == null) {
            loader = new FXMLLoader(MainController.instance.getClass().getResource(sceneName));
        } else {
            loader = new FXMLLoader(MainController.instance.getClass().getResource(sceneName), bundle);
        }
        if (controller != null) {
            loader.setController(controller);
        }
        Parent root = loader.load();
        fitParentAnchor(root);
        contentPane.getChildren().add(root);
    }

    public static void resetContentArea(AnchorPane contentPane) {
        contentPane.getChildren().clear();
    }

    public static void fitParentAnchor(Parent root) {
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    

}
