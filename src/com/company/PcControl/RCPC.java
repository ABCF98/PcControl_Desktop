package com.company.PcControl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RCPC extends Application {

    //@Override
    /*public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("Main.fxml")
        ); // Setting MainScreen.fxml as the layout screen
        //Parent root = (Parent) fxmlLoader.load();
        Main mainScreenMaster = (Main) fxmlLoader.getController(); // Getting the fxml controller
        mainScreenMaster.setMainScreenController(mainScreenMaster); // Setting the mainScreen Controller

        Scene scene = new Scene(root); // Creating a new scene / pane

        stage.setScene(scene); // Setting the entire scene as loaded from fxml loader
        stage.setTitle("RemoteControlPC"); // Setting the title of the window pane
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        }); // Setting how to close the set window
        stage.show(); // Displaying the entire window on pc
    }*/



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        //loader.setController(new Main());
        Pane mainPane = loader.load();
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene); // Setting the entire scene as loaded from fxml loader
        primaryStage.setTitle("Pc Controller"); // Setting the title of the window pane
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        }); // Setting how to close the set window
        primaryStage.show(); // Displaying the entire window on pc
    }
}
