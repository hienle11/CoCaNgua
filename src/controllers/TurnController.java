/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 10/01/2020
  By: Le Quang Hien (s3695516)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import models.Cell;
import models.Chess;
import models.Player;

public class TurnController
{
    private static Label turn;
    private static boolean[] choosenPlayer = new boolean[4];
    public static boolean opponentTurn = false;
    private static int[] diceValue = new int[3];
    private static Player.Color playerTurn = Player.Color.BLUE;
    private static int diceWasUsed = -1, currentTurn = 0;
    private static Chess chessHasMoved = null;
    private static Player currentPlayer = null;
    private static boolean currentPlayerIsComputer, diceIsRolled = false;
    private static boolean[] comPlayer;
    private static Pane gameOverPane;
    private static Button rollDiceBt;

    public static void setComOrHuman(boolean[] comPlayer) {
        TurnController.choosenPlayer = PlayerController.getChoosenPlayer();
        TurnController.comPlayer = comPlayer;
        for (int i = 0; i < 4; i++) {
//            if (choosenPlayer[i]) {
            currentPlayerIsComputer = comPlayer[i];
//            }
        }
    }


    public static void setDiceIsRolled(boolean diceIsRolled)
    {
        TurnController.diceIsRolled = diceIsRolled;
    }

    public static void setDiceValue(int[] diceValue)
    {
        TurnController.diceValue = diceValue;
    }

    public static int[] getDiceValue()
    {
        return diceValue;
    }

    public static Player.Color getPlayerTurn()
    {
        return playerTurn;
    }

    public static int getDiceWasUsed()
    {
        return diceWasUsed;
    }

    public static Chess getChessHasMoved()
    {
        return chessHasMoved;
    }

    public static Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public static boolean isCurrentPlayerIsComputer()
    {
        return currentPlayerIsComputer;
    }

    public static boolean isDiceIsRolled()
    {
        return diceIsRolled;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      INITIALIZE METHOD                                        //
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initialize(Label turn, Pane gameOverPane, Button rollDiceBt)
    {
        int i = 0;
        //        InitialThrowController.determineFirstThrow();
        for (i = 0; i < 4; i++) {
            if(choosenPlayer[i]) {
                playerTurn = PlayerController.color[i];
                break;
            }
        }
//        playerTurn = PlayerController.color[InitialThrowController.determineFirstThrow()];
        diceWasUsed = -1;
        chessHasMoved = null;
        diceIsRolled = false;
        currentPlayer =  PlayerController.getPlayer(playerTurn);
        turn.setText("Player Turn: " + playerTurn.toString());
        if (comPlayer[i]) endTurn();
        TurnController.gameOverPane = gameOverPane;
        TurnController.rollDiceBt = rollDiceBt;
    }

    public static void endTurn()
    {
        if (isGameEnded())
        {
            System.out.println("game is ended");
            gameOverPane.setVisible(true);
            rollDiceBt.setDisable(true);
            MediaController.playWinSound();
            //System.exit(0);
            System.out.println("game is ended");
        }   else {
            switchTurn();
            chessHasMoved = null;
            diceWasUsed = -1;
            diceIsRolled = false;
            currentPlayer = PlayerController.getPlayer(playerTurn);
            if (currentPlayerIsComputer || (GamePlayController.playOnline && opponentTurn)) {
                ButtonController.rollDiceBtHandler();
            } else {
                System.out.println("here = button active");
                ButtonController.getRollDiceBt().setOnAction(event -> ButtonController.rollDiceBtHandler());
            }
        }
    }

    public static void switchTurn()
    {
        if (Math.abs(diceValue[0]) != Math.abs(diceValue[1]))
        {
//            switch (playerTurn)
//            {
//                case BLUE:
//                    playerTurn = Player.Color.RED;
//                    currentPlayerIsComputer = comPlayer[1];
//                    break;
//                case RED:
//                    playerTurn = Player.Color.GREEN;
//                    currentPlayerIsComputer = comPlayer[2];
//                    break;
//                case GREEN:
//                    playerTurn = Player.Color.YELLOW;
//                    currentPlayerIsComputer = comPlayer[3];
//                    break;
//                case YELLOW:
//                    playerTurn = Player.Color.BLUE;
//                    currentPlayerIsComputer = comPlayer[0];
//                    break;
//            }
//            for (boolean i : choosenPlayer) System.out.println(i);
//            while (true) {
//                if (playerTurn == PlayerController.color[currentTurn]) {
//                    if (currentTurn >= 3) currentTurn = 0;
//                    currentTurn++;
//                    if (choosenPlayer[currentTurn]) {
//                        playerTurn = PlayerController.color[currentTurn];
//                        currentPlayerIsComputer = comPlayer[currentTurn];
//                        break;
//                    }
//                }
//            }
            currentTurn++;
            if (currentTurn > 3) currentTurn = 0;
            while(!choosenPlayer[currentTurn]) {
                currentTurn++;
                if (currentTurn > 3) currentTurn = 0;
            }
            playerTurn = PlayerController.color[currentTurn];
            currentPlayerIsComputer = comPlayer[currentTurn];
            System.out.println("Turn = " + playerTurn.toString());
        }
    }


    private static boolean isGameEnded()
    {
        boolean flag = true;
        for(int i = 0; i < 4; i++)
        {
            if (!currentPlayer.getChess(i).getCellId().contains("Home6")
                    && !currentPlayer.getChess(i).getCellId().contains("Home5")
                    && !currentPlayer.getChess(i).getCellId().contains("Home4")
                    && !currentPlayer.getChess(i).getCellId().contains("Home3")) {
                flag =  false;
            }
        }
        return flag;
    }

    public static void updateConditions(Cell selectedCell2)
    {
        chessHasMoved = PlayerController.getSelectedChess();
        diceWasUsed = MoveController.getPossibleMoves().get(selectedCell2)[1];
        System.out.println("dice was used = " + diceWasUsed);
        diceValue[diceWasUsed] = (-diceValue[diceWasUsed]);
        System.out.println("dice was used value  = " + diceValue[diceWasUsed]);
        if(diceValue[2] > 0)
            diceValue[2] = (-diceValue[2]);
    }

    public static boolean doDicesContainSix()
    {
        return (diceValue[0] == 6 && diceWasUsed != 0) || (diceValue[1] == 6 && diceWasUsed != 1);
    }

    public static int getDiceValueWasUsed()
    {
        return -diceValue[diceWasUsed];
    }
}
