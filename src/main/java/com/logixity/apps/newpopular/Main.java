package com.logixity.apps.newpopular;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
