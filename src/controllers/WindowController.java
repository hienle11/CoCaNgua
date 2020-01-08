package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// this class is created to control the window of the game
public class WindowController
{
    private Stage window;

    public WindowController(Stage window)
    {
        this.window = window;
    }

    // this method is created to load a new scene for the window
    public void loadWindow(String fxmlFile)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            window.setScene(new Scene(root));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}