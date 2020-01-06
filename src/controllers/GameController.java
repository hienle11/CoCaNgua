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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import models.Cell;
import models.Chess;
import models.Player;
import views.ChessView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable
{
    @FXML
    private Button rollDiceBt;
    @FXML
    private ImageView dice0, dice1;
    @FXML
    private StackPane blueSpawn, blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10,
            redSpawn, red1, red2, red3, red4, red5, red6, red7, red8, red9, red10,
            yellowSpawn, yellow1, yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, yellow8, yellow9, yellow10,
            greenSpawn, green1, green2, green3, green4, green5, green6, green7, green8, green9, green10,
            greenHome0, greenHome1, greenHome2, greenHome3, greenHome4, greenHome5, greenHome6,
            redHome0, redHome1, redHome2, redHome3, redHome4, redHome5, redHome6,
            yellowHome0, yellowHome1, yellowHome2, yellowHome3, yellowHome4, yellowHome5, yellowHome6,
            blueHome0, blueHome1, blueHome2, blueHome3, blueHome4, blueHome5, blueHome6,
            blueNest1, blueNest2, blueNest3, blueNest4,
            redNest1, redNest2, redNest3, redNest4,
            yellowNest1, yellowNest2, yellowNest3, yellowNest4,
            greenNest1, greenNest2, greenNest3, greenNest4;



    private static int[] diceValue = new int[3];
    private static Player.Color playerTurn = Player.Color.BLUE;

    private boolean diceIsRolled = false;
    private ChessView selectedChessView = null;
    private StackPane selectedCellView1 = null;
    private StackPane selectedCellView2 = null;
    private Chess selectedChess = null;
    private Cell selectedCell1 = null;
    private Cell selectedCell2 = null;
    private ArrayList<Cell> possibleCellList = new ArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        PlayerController.initialize();
        CellController.getCellViewList().addAll(
                blueHome0, blueSpawn, blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10,
                redHome0, redSpawn, red1, red2, red3, red4, red5, red6, red7, red8, red9, red10,
                greenHome0, greenSpawn, green1, green2, green3, green4, green5, green6, green7, green8, green9, green10,
                yellowHome0, yellowSpawn, yellow1, yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, yellow8, yellow9, yellow10,
                blueHome1, blueHome2, blueHome3, blueHome4, blueHome5, blueHome6,
                redHome1, redHome2, redHome3, redHome4, redHome5, redHome6,
                yellowHome1, yellowHome2, yellowHome3, yellowHome4, yellowHome5, yellowHome6,
                greenHome1, greenHome2, greenHome3, greenHome4, greenHome5, greenHome6,
                blueNest1, blueNest2, blueNest3, blueNest4,
                redNest1, redNest2, redNest3, redNest4,
                greenNest1, greenNest2, greenNest3, greenNest4,
                yellowNest1, yellowNest2, yellowNest3, yellowNest4);
        CellController.initialize();
    }

    public void normalCellOnMouseClicked(MouseEvent event)
    {
        if (selectedCellView1 !=  event.getSource() && diceIsRolled)
        {
            if (selectedChess == null)
            {
                selectedCellView1 = (StackPane) event.getSource();
                selectChessView();
                System.out.println("selectedCell = " + selectedCell1);
            } else
            {
                selectedCellView2 = (StackPane) event.getSource();
                if(moveChessView())
                    switchTurn();
                selectedChess = null;
            }
        }
    }

    public void switchTurn()
    {
        if (diceValue[0] != diceValue[1])
        {
            switch (playerTurn)
            {
                case BLUE:
                    playerTurn = Player.Color.RED;
                    break;
                case RED:
                    playerTurn = Player.Color.GREEN;
                    break;
                case GREEN:
                    playerTurn = Player.Color.YELLOW;
                    break;
                case YELLOW:
                    playerTurn = Player.Color.BLUE;
                    break;
            }
        }
        rollDiceBt.setOnAction(event -> {
            rollDiceBtHandler();

            });
        diceIsRolled = false;
        possibleCellList.clear();
    }

    public void rollDiceBtHandler()
    {
        diceValue = Player.rollDice();
        dice0.setImage(new Image("File:src/resources/images/" + diceValue[0] + ".jpg"));
        dice1.setImage(new Image("File:src/resources/images/" + diceValue[1] + ".jpg"));
        rollDiceBt.setOnAction(null);
        diceIsRolled = true;
        if(!isMovable())
            switchTurn();
    }

    public boolean isMovable()
    {
        getPossibleMoves();
        if (possibleCellList.size() == 0)
            return false;
        possibleCellList.forEach(cell -> System.out.println("possible cell = " + cell.getId()));
        return true;
    }

    public void getPossibleMoves()
    {
        Cell checkedCell;
        Chess checkedChess;
        for (int i = 0 ; i < 4; i ++)
        {
            checkedChess = PlayerController.getPlayer(playerTurn).getChess(i);
            System.out.println(checkedChess.getCellId());
            checkedCell = CellController.getCell(checkedChess.getCellId());
            int currentCellIndex = CellController.getCellList().indexOf(checkedCell);
            if (checkedCell.getId().contains("Nest"))
                getSpawnMoves();
            else
                getNormalMoves(currentCellIndex);
        }
    }

    public void getNormalMoves(int currentCellIndex)
    {
        boolean checkingResult = true;
        Cell checkedCell = null, finalCell = null;
        checkedCell = CellController.getCellList().get(currentCellIndex);
        System.out.println("123checkedCell =  " + checkedCell.getId());
        for (int i = 0; i < 3; i ++)
        {
            finalCell = CellController.getCellList().get(currentCellIndex + diceValue[i]);
            System.out.println("123finalCell =  " + finalCell.getId());
            if (finalCell.getChess() != null)
            {
                if (finalCell.getChess().getColor() == playerTurn)
                    continue;
            }
            for (int j = 1; j < diceValue[i]; j++)
            {
                checkedCell = CellController.getCellList().get(currentCellIndex + j);
                if (checkedCell.getChess() != null)
                    checkingResult = false;
            }
            if (checkingResult == true)
                possibleCellList.add(finalCell);
        }
    }

    public void getSpawnMoves()
    {
        Cell possibleCell = null;
        if (diceValue[0] == 6 || diceValue[1] == 6)
        {
            switch (playerTurn)
            {
                case BLUE:
                    possibleCell = CellController.getCell("blueSpawn");
                    break;
                case RED:
                    possibleCell = CellController.getCell("redSpawn");
                    break;
                case GREEN:
                    possibleCell = CellController.getCell("greenSpawn");
                    break;
                case YELLOW:
                    possibleCell = CellController.getCell("yellowSpawn");
                    break;
            }
            if (possibleCell.getChess() == null)
                possibleCellList.add(possibleCell);
        }
    }

    public boolean moveChessView()
    {
        selectedCell2 = CellController.getCell(selectedCellView2.getId());
        if(possibleCellList.contains(selectedCell2))
        {
            selectedCellView2.getChildren().add(selectedChessView);
            selectedCell1.setChess(null);
            selectedCell2.setChess(selectedChess);
            selectedChess.moveTo(selectedCellView2.getId());
            return true;
        } else
            return false;
    }

    public void selectChessView()
    {
        selectedCell1 = CellController.getCell(selectedCellView1.getId());
        selectedChess = selectedCell1.getChess();
        if (selectedChess != null)
        {
            selectedChessView = (ChessView) selectedCellView1.getChildren().get(1);
            if (!selectedChess.getColor().toString().equals(playerTurn.toString()))
                selectedChess = null;
        }
    }
}

