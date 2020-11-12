package de.kleindev.twitchbot.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(FXMLLoader.load(JavaFXMain.class.getClassLoader().getResource("fxml/debug/DebugMain.fxml"))));
        primaryStage.show();
    }
}
