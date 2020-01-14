/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 10/01/2020
  By: Ninh Van Hoang Phuoc (s3777323)
  Last modified: 14/01/2020
  By: Ninh Van Hoang Phuoc (s3777323)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

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