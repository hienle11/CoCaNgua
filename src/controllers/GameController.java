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

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Cell;
import models.Chess;
import models.ComputerPlayer;
import models.Player;
import views.CellView;
import views.ChessView;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameController implements Initializable
{
    @FXML
    private Button rollDiceBt;
    @FXML
    private ImageView dice0, dice1;
    @FXML
    private CellView blueSpawn, blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10,
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
    private int diceWasUsed = -1;
    private int chessNumberHasMoved = -1;

    boolean currentPlayerIsComputer = false;
    private boolean diceIsRolled = false;
    private ChessView selectedChessView = null;
    private CellView selectedCellView1 = null;
    private CellView selectedCellView2 = null;
    private Chess selectedChess = null;
    private Cell selectedCell1 = null;
    private Cell selectedCell2 = null;
    private Player currentPlayer = null;
    private HashMap<Cell, Integer[]> possibleMoves = new HashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        PlayerController.initialize();
        CellController.getCellViewList().addAll(
                blueHome0, blueSpawn, blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10,
                redHome0, redSpawn, red1, red2, red3, red4, red5, red6, red7, red8, red9, red10,
                greenHome0, greenSpawn, green1, green2, green3, green4, green5, green6, green7, green8, green9, green10,
                yellowHome0, yellowSpawn, yellow1, yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, yellow8, yellow9, yellow10,
                blueHome0, blueHome1, blueHome2, blueHome3, blueHome4, blueHome5, blueHome6,
                redHome0, redHome1, redHome2, redHome3, redHome4, redHome5, redHome6,
                greenHome0, greenHome1, greenHome2, greenHome3, greenHome4, greenHome5, greenHome6,
                yellowHome0, yellowHome1, yellowHome2, yellowHome3, yellowHome4, yellowHome5, yellowHome6,
                blueNest1, blueNest2, blueNest3, blueNest4,
                redNest1, redNest2, redNest3, redNest4,
                greenNest1, greenNest2, greenNest3, greenNest4,
                yellowNest1, yellowNest2, yellowNest3, yellowNest4);
        CellController.initialize();
        currentPlayer =  PlayerController.getPlayer(playerTurn);
    }

    public void normalCellOnMouseClicked(MouseEvent event)
    {
        if (selectedCellView1 !=  event.getSource() && diceIsRolled)
        {
            if (selectedChess == null)
            {
                selectedCellView1 = (CellView) event.getSource();
                //System.out.println("selectedCell1 = " + selectedCellView1.getId());
                selectChess();
            } else
            {
                selectedCellView2 = (CellView) event.getSource();
                //System.out.println("selectedCell2 = " + selectedCellView2.getId());
                if(moveChess())
                {
                    hidePossibleCells();
                    if(!isMovable())
                        switchTurn();
                }
                selectedChess = null;
            }
        }
    }

    public void switchTurn()
    {
        do
        {
            if (Math.abs(diceValue[0]) != Math.abs(diceValue[1]))
            {
                switch (playerTurn)
                {
                    case BLUE:
                        playerTurn = Player.Color.RED;
                        currentPlayerIsComputer = true;
                        break;
                    case RED:
                        playerTurn = Player.Color.GREEN;
                        currentPlayerIsComputer = false;
                        break;
                    case GREEN:
                        playerTurn = Player.Color.YELLOW;
                        currentPlayerIsComputer = true;
                        break;
                    case YELLOW:
                        playerTurn = Player.Color.BLUE;
                        currentPlayerIsComputer = false;
                        break;
                }
            }
            System.out.println("playerTurn = " + playerTurn.toString());
            rollDiceBt.setOnAction(event ->
            {
                rollDiceBtHandler();

            });
            chessNumberHasMoved = -1;
            diceWasUsed = -1;
            diceIsRolled = false;
            currentPlayer = PlayerController.getPlayer(playerTurn);
            System.out.println("current Player is " + currentPlayer.getName() + "computer play = " + currentPlayerIsComputer);
            System.out.println("asfasf = " + currentPlayerIsComputer);
            if (currentPlayerIsComputer)
            {
                System.out.println("computer roll dice = " + diceValue[0] + diceValue[1] + diceValue[2]);
                System.out.println("here");
                computerMove();
            }
        } while(currentPlayerIsComputer);
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
        if (diceWasUsed == 2)
            return false;
        possibleMoves.clear();
        getPossibleMoves();
        if (possibleMoves.size() == 0)
            return false;
        showPossibleCells();
        return true;
    }

    public void getPossibleMoves()
    {
        Cell currentCell;
        Chess checkedChess;
        for (int i = 0 ; i < 4; i ++)
        {
            if(i == chessNumberHasMoved)
                continue;
            checkedChess = currentPlayer.getChess(i);
            currentCell = CellController.getCell(checkedChess.getCellId());
            int currentCellIndex = CellController.getCellList().indexOf(currentCell);
            if (currentCell.getId().contains("Nest"))
                getSpawnMoves(i);
            else if (currentCell.getId().contains(playerTurn.toString().toLowerCase() + "Home"))
            {
                if(currentCellIndex < 48)
                {
                    switch (playerTurn)
                    {
                        case BLUE:
                            currentCellIndex = 48;
                            break;
                        case RED:
                            currentCellIndex = 55;
                            break;
                        case GREEN:
                            currentCellIndex = 62;
                            break;
                        case YELLOW:
                            currentCellIndex = 69;
                            break;
                    }
                }
                getHomeArrivalMoves(currentCellIndex, i);
            }
            else
                getNormalMoves(currentCellIndex, i);
        }
    }

    public void getNormalMoves(int currentCellIndex, int chessNumber)
    {
        boolean checkingResult = true;
        int finalCellIndex;
        for (int i = 0; i < 3; i ++)
        {
            if(diceValue[i] < 0)
                continue;
            //System.out.println("homeDistance " + currentPlayer.getChess(chessNumber).getHomeDistance());
            if (currentPlayer.getChess(chessNumber).getHomeDistance() - diceValue[i] < 0)
            {
                continue;
            }
            if((finalCellIndex = checkFinalCell(currentCellIndex, i, false)) < 0)
            {
                continue;
            }
            for (int j = 1; j < diceValue[i]; j++)
            {
                checkingResult = checkOtherCells(currentCellIndex, j, false);
                if (checkingResult == false)
                {
                    break;
                }
            }
            if (checkingResult == true)
            {
                Integer [] chessAndDice = new Integer[2];
                chessAndDice[0] = chessNumber;
                chessAndDice[1] = i;
                possibleMoves.put(CellController.getCellList().get(finalCellIndex), chessAndDice);
            }
        }
    }

    public int checkFinalCell(int currentCellIndex, int diceValueIndex, boolean homeArrival)
    {
        Cell finalCell;
        int checkedCellIndex;
        checkedCellIndex = currentCellIndex + diceValue[diceValueIndex];
        if (checkedCellIndex >= 48 && (!homeArrival))
            checkedCellIndex -= 48;
        finalCell = CellController.getCellList().get(checkedCellIndex);
        //System.out.println("finalCell = " + finalCell.getId());
        if (finalCell.getChess() != null)
        {
            if (finalCell.getChess().getColor() == playerTurn)
                return -1;
        }
        return checkedCellIndex;
    }

    public boolean checkOtherCells(int currentCellIndex, int steps, boolean homeArrival)
    {
        Cell checkedCell;
        int checkedCellIndex;
        checkedCellIndex = currentCellIndex + steps;
        if (checkedCellIndex >= 48 && (!homeArrival))
        {
            checkedCellIndex -= 48;

        }
        checkedCell = CellController.getCellList().get(checkedCellIndex);
        //System.out.println("Cell = " + checkedCell.getId());
        if (checkedCell.getChess() != null)
        {
            //System.out.println("here =-=" +checkedCell.getChess().getColor().toString());
            return false;
        }
        return true;
    }

    public void getSpawnMoves(int chessNumber)
    {
        Cell possibleCell = null;
        if ((diceValue[0] == 6 && diceWasUsed != 0) || (diceValue[1] == 6 && diceWasUsed != 1))
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
            Chess chess = possibleCell.getChess();
            if (chess == null || playerTurn != chess.getColor())
            {
                Integer [] chessAndDice = new Integer[2];
                chessAndDice[0] = chessNumber;
                if(diceValue[0] == 6)
                    chessAndDice[1] = 0;
                else
                    chessAndDice[1] = 1;
                possibleMoves.put(possibleCell, chessAndDice);
            }
        }
    }

    public void getHomeArrivalMoves(int currentCellIndex, int chessNumber)
    {
        Cell currentCell = CellController.getCellList().get(currentCellIndex);
        boolean checkingResult = false;
        for (int i = 0; i < 3; i ++)
        {
            if(diceValue[i] < 0)
                continue;
            if(diceValue[i] > 6)
                break;
            if(currentCell.getId().contains("0"))
            {
                if ((checkFinalCell(currentCellIndex, i, true)) < 0)
                    continue;
                for (int j = 1; j < diceValue[i]; j++)
                {
                    if (checkingResult = checkOtherCells(currentCellIndex, j, true))
                        break;
                }
            } else if (currentCell.getId().contains("" + (diceValue[i]-1)))
            {
                if (CellController.getCellList().get(currentCellIndex + 1).getChess() != null)
                    continue;
                else
                    checkingResult = true;
            }
            if (checkingResult == true)
            {
                Integer [] chessAndDice = new Integer[2];
                chessAndDice[0] = chessNumber;
                chessAndDice[1] = i;
                if(currentCell.getId().contains("0"))
                {
                    possibleMoves.put(CellController.getCellList().get(currentCellIndex + diceValue[i]), chessAndDice);
                }else
                {
                    possibleMoves.put(CellController.getCellList().get(currentCellIndex + 1), chessAndDice);
                }
            }
        }
    }

    public void selectChess()
    {
        selectedCell1 = CellController.getCell(selectedCellView1);
        selectedChess = selectedCell1.getChess();
        if (selectedChess != null)
        {
            //System.out.println("selectedChess HomeDistance: = " + selectedChess.getHomeDistance());
            if (selectedCellView1.getChildren().size() == 3)
                selectedChessView = (ChessView) selectedCellView1.getChildren().get(2);
            else
                selectedChessView = (ChessView) selectedCellView1.getChildren().get(1);
            if (!selectedChess.getColor().toString().equals(playerTurn.toString()))
                selectedChess = null;

        }
    }

    public boolean moveChess()
    {
        selectedCell2 = CellController.getCell(selectedCellView2);
        if(isValidMove())
        {
            updateConditions();
            //System.out.println("dice was used = " + diceWasUsed);
            //System.out.println("diceValue was used = " + diceValue[diceWasUsed]);
            //System.out.println("diceValue[2] = " + diceValue[2]);
            if (selectedCell2.getChess() != null)
            {
                Chess anotherChess = selectedCell2.getChess();
                kickChess(anotherChess);
            }
            selectedChessView.moveTo(selectedCellView2);
            currentPlayer.moveChess(selectedChess, selectedCell1, selectedCell2, -diceValue[diceWasUsed]);
            return true;
        } else
            return false;
    }

    public void updateConditions()
    {
        chessNumberHasMoved = possibleMoves.get(selectedCell2)[0];
        diceWasUsed = possibleMoves.get(selectedCell2)[1];
        diceValue[diceWasUsed] = (-diceValue[diceWasUsed]);
        if(diceValue[2] > 0)
            diceValue[2] = (-diceValue[2]);
    }

    public void computerMove()
    {
        diceValue = Player.rollDice();
        dice0.setImage(new Image("File:src/resources/images/" + diceValue[0] + ".jpg"));
        dice1.setImage(new Image("File:src/resources/images/" + diceValue[1] + ".jpg"));
        rollDiceBt.setOnAction(null);
        diceIsRolled = true;
        while (isMovable())
        {
            System.out.println("Playername =" + currentPlayer.getName());
            selectedCell2 = ((ComputerPlayer) currentPlayer).makeDecision(possibleMoves, diceValue);
            updateConditions();
            selectedChess = currentPlayer.getChess(possibleMoves.get(selectedCell2)[0]);
            selectedCell1 = CellController.getCell(selectedChess.getCellId());
            selectedCellView1 = CellController.getCellView(selectedCell1);
            selectedCellView2 = CellController.getCellView(selectedCell2);
            if (selectedCellView1.getChildren().size() == 3)
                selectedChessView = (ChessView) selectedCellView1.getChildren().get(2);
            else
                selectedChessView = (ChessView) selectedCellView1.getChildren().get(1);
            if (selectedCell2.getChess() != null)
            {
                Chess anotherChess = selectedCell2.getChess();
                kickChess(anotherChess);
            }
            currentPlayer.moveChess(selectedChess, selectedCell1, selectedCell2, -diceValue[diceWasUsed]);
            selectedChessView.moveTo(selectedCellView2);

            hidePossibleCells();
        }

    }

    public void kickChess(Chess kickedChess)
    {
        ChessView kickedChessView = (ChessView) selectedCellView2.getChildren().get(1);
        Cell nestCell = CellController.findEmptyNest(kickedChess);
        CellView nestCellView = CellController.getCellView(nestCell);
        nestCell.setChess(kickedChess);
        kickedChess.getKicked(nestCell);
        kickedChessView.moveTo(nestCellView);
    }

    public boolean isValidMove()
    {
        if (!possibleMoves.containsKey(selectedCell2))
            return false;
        int chessNumber = possibleMoves.get(selectedCell2)[0];
        if (selectedChess == currentPlayer.getChess(chessNumber))
            return true;
        return false;
    }

    public void showPossibleCells()
    {
        possibleMoves.forEach((cell, chessAndDice) ->
        {
            CellController.getCellView(cell).showPossibleCells();
        });
    }

    public void hidePossibleCells()
    {
        possibleMoves.forEach((cell, chessAndDice) ->
        {
            CellController.getCellView(cell).hidePossibleCells();
        });
    }


}

