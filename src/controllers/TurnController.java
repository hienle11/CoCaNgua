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

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.Cell;
import models.Chess;
import models.Player;

import java.util.ResourceBundle;

public class TurnController
{
    private static Text winnerName, winnerScore;
    private static Pane gameOverPane;
    private static Label turnLabel, history;
    private static ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle");

    private static boolean[] chosenPlayer = new boolean[4];
    public static boolean opponentTurn = false;
    private static int[] diceValue = new int[3];
    private static Player.Color playerTurn = Player.Color.BLUE;
    private static int diceWasUsed = -1, currentTurn = 0;
    private static Chess chessHasMoved = null;
    private static Player currentPlayer = null;
    private static boolean currentPlayerIsComputer, diceIsRolled = false;
    private static boolean[] comPlayer;
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
        TurnController.chosenPlayer = PlayerController.getChosenPlayer();
        TurnController.comPlayer = comPlayer;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      INITIALIZE METHOD                                        //
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initialize(Label turn, Pane gameOverPane, Text winnerName, Text winnerScore, Label history)
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
        TurnController.history = history;
        currentPlayer = PlayerController.getPlayer(playerTurn);
        opponentTurn = GamePlayController.onlinePlayer[currentTurn];
        turnLabel = turn;
        turnLabel.setText(bundle.getString("playerturn") + "\n" +  currentPlayer.getName());
        TurnController.gameOverPane = gameOverPane;
        TurnController.winnerName = winnerName;
        TurnController.winnerScore = winnerScore;
        initialRollDice();
    }

    private static void initialRollDice()
    {
        while(initialDiceValue[currentTurn] == -1)
            switchTurn();
        if (comPlayer[currentTurn] || (GamePlayController.playOnline && opponentTurn))
            ButtonController.rollDiceBtHandler();
        else
            ButtonController.enableRollDiceBt();
    }

    public static void getInitialDiceValue ()
    {
        initialDiceValue[currentTurn] = diceValue[2];
        int firstTurn = determineFirstTurn();
        if ((firstTurn != -1))
        {
            currentTurn = firstTurn == 0 ? 3 : firstTurn - 1;
            initialRollDice = false;
            endTurn();
            history.setText(currentPlayer.getName() + " " + bundle.getString("gofirst"));
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
            pauseTransition.setOnFinished(t -> history.setText(""));
            pauseTransition.play();
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
        for (int i = 1; i < 4; i++)
        {
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
            endGame();
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

    private static void endGame()
    {
        ButtonController.disableRollDiceBt();
        MediaController.playWinSound();
        gameOverPane.setVisible(true);
        winnerName.setText(currentPlayer.getName() + " " + bundle.getString("win"));
        winnerScore.setText(bundle.getString("winnerscore") + currentPlayer.getScore());
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
        opponentTurn = GamePlayController.onlinePlayer[currentTurn];
        currentPlayerIsComputer = comPlayer[currentTurn];
        turnLabel.setText(bundle.getString("playerturn") + "\n" +  currentPlayer.getName());
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

    private static boolean checkHome(Cell selectedCell2) {
        String temp = selectedCell2.getId();
        return temp.contains("Home") && temp.charAt(temp.length() - 1) != '0' && temp.charAt(0) - String.valueOf(currentPlayer.getColor()).charAt(0) == 32;
    }

    public static void updateConditions(Cell selectedCell2)
    {
        chessHasMoved = PlayerController.getSelectedChess();
        diceWasUsed = MoveController.getPossibleMoves().get(selectedCell2)[1];
        diceValue[diceWasUsed] = (-diceValue[diceWasUsed]);
        String horseColor = bundle.getString(String.valueOf(currentPlayer.getColor()));
        if (checkHome(selectedCell2)) history.setText(horseColor + bundle.getString("gohome") + selectedCell2.getId().charAt(selectedCell2.getId().length() - 1));
        else if (!PlayerController.isASpawnMove()) history.setText(horseColor + bundle.getString("move") + (-diceValue[diceWasUsed]) + " " + bundle.getString("space"));
        else history.setText(horseColor + bundle.getString("spawn"));
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
