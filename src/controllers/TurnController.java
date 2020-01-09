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

import models.Cell;
import models.Player;

public class TurnController
{
    private static int[] diceValue = new int[3];
    private static Player.Color playerTurn = Player.Color.BLUE;
    private static int diceWasUsed = -1;
    private static int chessNumberHasMoved = -1;
    private static Player currentPlayer = null;
    private static boolean currentPlayerIsComputer = false;
    private static boolean diceIsRolled = false;

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

    public static int getChessNumberHasMoved()
    {
        return chessNumberHasMoved;
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

    public static void initialize()
    {
        currentPlayer =  PlayerController.getPlayer(playerTurn);
    }

    public static void endTurn()
    {
        if (isGameEnded())
        {
            System.out.println("game is ended");
            System.exit(0);
        }
        switchTurn();
        chessNumberHasMoved = -1;
        diceWasUsed = -1;
        diceIsRolled = false;
        currentPlayer = PlayerController.getPlayer(playerTurn);
        if (currentPlayerIsComputer)
        {
            AnimationController.rollDiceAnimation();
        }
        else
            AnimationController.getRollDiceBt().setOnAction(event -> ButtonController.rollDiceBtHandler());
    }

    public static void switchTurn()
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
    }


    private static boolean isGameEnded()
    {
        for(int i = 0; i < 4; i++)
        {
            System.out.println("endGame: + " + currentPlayer.getChess(i).getCellId());
            if (!currentPlayer.getChess(i).getCellId().matches("Home+[3-6]"))
                return false;
        }
        return true;
    }

    public static void updateConditions(Cell selectedCell2)
    {
        chessNumberHasMoved = MoveController.getPossibleMoves().get(selectedCell2)[0];
        diceWasUsed = MoveController.getPossibleMoves().get(selectedCell2)[1];
        diceValue[diceWasUsed] = (-diceValue[diceWasUsed]);
        if(diceValue[2] > 0)
            diceValue[2] = (-diceValue[2]);
    }

    public static boolean isDicesContainSix()
    {
        return (diceValue[0] == 6 && diceWasUsed != 0) || (diceValue[1] == 6 && diceWasUsed != 1);
    }

    public static int getDiceValueWasUsed()
    {
        return -diceValue[diceWasUsed];
    }
}
