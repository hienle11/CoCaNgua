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

import javafx.scene.control.Label;
import models.Cell;
import models.Chess;
import models.Player;

public class TurnController
{
    private static Label turn;
    public static boolean opponentTurn = false;
    private static int[] diceValue = new int[3];
    private static Player.Color playerTurn = Player.Color.BLUE;
    private static int diceWasUsed = -1;
    private static Chess chessHasMoved = null;
    private static Player currentPlayer = null;
    private static boolean currentPlayerIsComputer, diceIsRolled = false;
    private static boolean[] comPlayer;

    public static void setComOrHuman(boolean[] comPlayer) {
        TurnController.comPlayer = comPlayer;
        currentPlayerIsComputer = comPlayer[0];
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

    public static void initialize(Label turn)
    {
        playerTurn = Player.Color.BLUE;
        diceWasUsed = -1;
        chessHasMoved = null;
        diceIsRolled = false;
        currentPlayer =  PlayerController.getPlayer(playerTurn);
        turn.setText("Player Turn: " + playerTurn.toString());
        endTurn();
    }

    public static void endTurn()
    {
        if (isGameEnded())
        {
            System.out.println("game is ended");
            System.exit(0);
        }
        switchTurn();
        chessHasMoved = null;
        diceWasUsed = -1;
        diceIsRolled = false;
        currentPlayer = PlayerController.getPlayer(playerTurn);
        if (currentPlayerIsComputer || (GamePlayController.playOnline && opponentTurn))
        {
            ButtonController.rollDiceBtHandler();
        }else
        {
            System.out.println("here = button active");
            ButtonController.getRollDiceBt().setOnAction(event -> ButtonController.rollDiceBtHandler());
        }
    }

    public static void switchTurn()
    {
        if (Math.abs(diceValue[0]) != Math.abs(diceValue[1]))
        {
            opponentTurn = false;
            switch (playerTurn)
            {
                case BLUE:
                    playerTurn = Player.Color.RED;
                    opponentTurn = false;
                    currentPlayerIsComputer = comPlayer[1];
                    break;
                case RED:
                    playerTurn = Player.Color.GREEN;
                    currentPlayerIsComputer = comPlayer[2];
                    break;
                case GREEN:
                    playerTurn = Player.Color.YELLOW;
                    currentPlayerIsComputer = comPlayer[3];
                    break;
                case YELLOW:
                    playerTurn = Player.Color.BLUE;
                    currentPlayerIsComputer = comPlayer[0];
                    break;
            }
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
