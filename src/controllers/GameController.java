/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 23/12/2019
  By: Le Quang Hien (s3695516)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Chess;
import models.Player;
import views.ChessView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable
{
    @FXML
    private ImageView dice0, dice1;
    @FXML
    private StackPane blueSpawn, blueNest, blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10,
            redSpawn, redNest, red1, red2, red3, red4, red5, red6, red7, red8, red9, red10,
            yellowSpawn, yellowNest, yellow1, yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, yellow8, yellow9, yellow10,
            greenSpawn, greenNest, green1, green2, green3, green4, green5, green6, green7, green8, green9, green10,
            greenHome0, greenHome1, greenHome2, greenHome3, greenHome4, greenHome5, greenHome6,
            redHome0, redHome1, redHome2, redHome3, redHome4, redHome5, redHome6,
            yellowHome0, yellowHome1, yellowHome2, yellowHome3, yellowHome4, yellowHome5, yellowHome6,
            blueHome0, blueHome1, blueHome2, blueHome3, blueHome4, blueHome5, blueHome6;

    private static final String blueChessImage = "File:src/resources/images/BLUE.jpg";
    private static final String redChessImage = "File:src/resources/images/RED.jpg";
    private static final String yellowChessImage = "File:src/resources/images/YELLOW.jpg";
    private static final String greenChessImage = "File:src/resources/images/GREEN.jpg";

    private static int[] dice = new int[2];
    private static Player.Color playerTurn = Player.Color.BLUE;
    private static ChessView selectedChess = null;
    private static ArrayList<Chess> blueChess = null;
    private static ArrayList<Chess> redChess = null;
    private static ArrayList<Chess> yellowChess = null;
    private static ArrayList<Chess> greenChess = null;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //initializeChess();
        blueSpawn.getChildren().add(new ChessView(blueChessImage));
        redSpawn.getChildren().add(new ChessView(redChessImage));
        yellowSpawn.getChildren().add(new ChessView(yellowChessImage));
        greenSpawn.getChildren().add(new ChessView(greenChessImage));
    }

    public void initializeChess()
    {
        for(int i = 0; i < 4; i++)
        {
            blueChess.add(new Chess(Chess.Color.BLUE, Chess.Location.NEST, blueChessImage));
            redChess.add(new Chess(Chess.Color.RED, Chess.Location.NEST, redChessImage));
            yellowChess.add(new Chess(Chess.Color.YELLOW, Chess.Location.NEST, yellowChessImage));
            greenChess.add(new Chess(Chess.Color.GREEN, Chess.Location.NEST, greenChessImage));
        }
    }

    public void testBt()
    {
        System.out.println("hello");
    }

    public void cellOnMouseClicked(MouseEvent event)
    {
        StackPane selectedPane = (StackPane) event.getSource();
        if(selectedChess == null)
        {
            if (selectedPane.getChildren().size() == 2)
            {
                selectedChess = (ChessView) selectedPane.getChildren().get(1);
                System.out.println("here 1 = " + blue5.getId());
                System.out.println("here 2 = " + playerTurn.toString());
                if (!selectedChess.getImage().toString().contains(playerTurn.toString()))
                    selectedChess = null;
            }
        }
        else
        {
            selectedPane.getChildren().add(selectedChess);
            switchTurn();
            selectedChess = null;
        }
    }

    public void switchTurn()
    {
        switch (playerTurn)
        {
            case BLUE:
                playerTurn = Player.Color.RED;
            case RED:
                playerTurn = Player.Color.GREEN;
            case GREEN:
                playerTurn = Player.Color.YELLOW;
            case YELLOW:
                playerTurn = Player.Color.BLUE;
        }
    }

    public void rollDiceBt()
    {
        dice = Player.rollDice();
        dice0.setImage(new Image("File:src/resources/images/" + dice[0] + ".jpg"));
        dice1.setImage(new Image("File:src/resources/images/" + dice[1] + ".jpg"));
        //spawnChess(playerTurn);
    }

    public void spawnChess(Player.Color playerColor)
    {
        blueSpawn.getChildren().add(new ChessView(playerColor.toString()));
    }
}

