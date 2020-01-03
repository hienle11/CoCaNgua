/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 03/01/2020
  By: Le Quang Hien (s3695516)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import models.Chess;
import views.ChessView;

import java.util.ArrayList;

public class ChessController
{
    private static final String blueChessImage = "File:src/resources/images/BLUE.jpg";
    private static final String redChessImage = "File:src/resources/images/RED.jpg";
    private static final String yellowChessImage = "File:src/resources/images/YELLOW.jpg";
    private static final String greenChessImage = "File:src/resources/images/GREEN.jpg";
    private Chess[] blueChess = new Chess[4];
    private Chess[] redChess = new Chess[4];
    private Chess[] greenChess = new Chess[4];
    private Chess[] yellowChess = new Chess[4];


    public void initialize()
    {
        for(int i = 0; i < 4; i++)
        {
            blueChess[i] = new Chess(Chess.Color.BLUE, Chess.Location.NEST);
            redChess[i] = new Chess(Chess.Color.RED, Chess.Location.NEST);
            yellowChess[i] = new Chess(Chess.Color.YELLOW, Chess.Location.NEST);
            greenChess[i] = new Chess(Chess.Color.GREEN, Chess.Location.NEST);
        }
    }

    public Chess getBlueChess(int number)
    {
        return blueChess[number];
    }
    public Chess getRedChess(int number)
    {
        return redChess[number];
    }
    public Chess getGreenChess(int number)
    {
        return greenChess[number];
    }
    public Chess getYellowChess(int number)
    {
        return yellowChess[number];
    }
}
