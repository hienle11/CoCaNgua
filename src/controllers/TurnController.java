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
    private static Label turnLabel;
    private static boolean[] chosenPlayer = new boolean[4];
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
    static boolean initialRollDice = true;
    static int[] initialDiceValue = new int[4];

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

    public static void setComOrHuman(boolean[] comPlayer)
    {
        TurnController.chosenPlayer = PlayerController.getChoosenPlayer();
        TurnController.comPlayer = comPlayer;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      INITIALIZE METHOD                                        //
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initialize(Label turn, Pane gameOverPane, Button rollDiceBt)
    {
        int i = 0;
        for (i = 0; i < 4; i++)
        {
            if(chosenPlayer[i])
            {
                playerTurn = PlayerController.color[i];
                break;
            }
        }
        currentPlayer = PlayerController.getPlayer(playerTurn);
        turnLabel = turn;
        turnLabel.setText("Player Turn: " + playerTurn.toString());
        TurnController.gameOverPane = gameOverPane;
        initialRollDice();
    }

    private static void initialRollDice()
    {
        while(initialDiceValue[currentTurn] == -1)
            switchTurn();
        if (comPlayer[currentTurn])
            ButtonController.rollDiceBtHandler();
        else
            ButtonController.enableRollDiceBt();
    }

    public static void getInitialDiceValue ()
    {
        initialDiceValue[currentTurn] = diceValue[2];
        System.out.println("initialDiceValue[" + currentTurn + "] = " +diceValue[2]);
        int firstTurn = determineFirstTurn();
        System.out.println("first turn = " + firstTurn);
        if ((firstTurn != -1))
        {
            System.out.println("here");
            currentTurn = firstTurn == 0 ? 3 : firstTurn - 1;
            initialRollDice = false;
            endTurn();
        } else
        {
            switchTurn();
            initialRollDice();
        }
    }

    public static int determineFirstTurn()
    {
        int tempIndex = 0;
        int playerHasHighestDiceValue = 0;
        System.out.println("start Compare");
        System.out.println("initialDiceValue[" + 0 + "] = " + initialDiceValue[0]);
        for (int i = 1; i < 4; i++)
        {
            System.out.println("initialDiceValue[" + i + "] = " + initialDiceValue[i]);
            if (!chosenPlayer[i])
                continue;
            else if (initialDiceValue[i] == 0)
            {
                playerHasHighestDiceValue = -1;
                break;
            }
            else if (initialDiceValue[tempIndex]  == initialDiceValue[i])
            {
                initialDiceValue[i] = 0;
                playerHasHighestDiceValue = -1;
            }
            else if (initialDiceValue[tempIndex] < initialDiceValue[i])
            {
                initialDiceValue[tempIndex] = -1;
                tempIndex = i;
                playerHasHighestDiceValue = i;
            } else if (initialDiceValue[tempIndex] > initialDiceValue[i])
            {
                initialDiceValue[i] = -1;
            }
        }
        return playerHasHighestDiceValue;
    }

    public static void endTurn()
    {
        if (isGameEnded())
        {
            ButtonController.disableRollDiceBt();
            MediaController.playWinSound();
            gameOverPane.setVisible(true);
        } else
        {
            if (Math.abs(diceValue[0]) != Math.abs(diceValue[1]))
            {
                switchTurn();
            }
            chessHasMoved = null;
            diceWasUsed = -1;
            diceIsRolled = false;
            currentPlayer = PlayerController.getPlayer(playerTurn);
            if (currentPlayerIsComputer || (GamePlayController.playOnline && opponentTurn))
            {
                ButtonController.rollDiceBtHandler();
            } else
            {
                ButtonController.enableRollDiceBt();
            }
        }
    }

    public static void switchTurn()
    {
        currentTurn++;
        if (currentTurn > 3) currentTurn = 0;
        while(!chosenPlayer[currentTurn])
        {
            currentTurn++;
            if (currentTurn > 3) currentTurn = 0;
        }
        playerTurn = PlayerController.color[currentTurn];
        currentPlayerIsComputer = comPlayer[currentTurn];
        turnLabel.setText("Player Turn: " + playerTurn.toString());
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
