package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Chess;
import views.ChessView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/GamePlay.fxml"));
        primaryStage.setTitle("Co Ca Ngua");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

       /* StackPane stackpane = new StackPane();
        ChessView blueChess = new ChessView("blue");
        stackpane.getChildren().add(blueChess);
        Scene scene = new Scene(stackpane, 300, 300);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();*/

    }


    public static void main(String[] args) {
        launch(args);
    }
}
