/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 03/01/2020
  By: Le Quang Hien (s3695516)
  Last modified: 14/01/2020
  By: Le Quang Hien (s3695516) + Le Ngoc Duy (s3757327)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.*;
import views.CellView;

import java.util.ResourceBundle;

public class PlayerController
{
    // properties of PlayerController class
    private static Player[] player = new Player[4];         // list of 4 players
    static Player.Color[] color = new Player.Color[]{Player.Color.BLUE, Player.Color.RED, Player.Color.GREEN, Player.Color.YELLOW};
    private static ObservableList<ImageView> chessViewList = FXCollections.observableArrayList(); // list of all chessViews
    private static ImageView kickedChessView = null;        // ImageView of the chess get kicked
    private static ImageView selectedChessView = null;      // ImageView of the chess selected by the player
    private static CellView selectedCellView1 = null;       // Primary CellView containing the chess selected by the player
    private static CellView selectedCellView2 = null;       // Secondary CellView selected by the player to move the chess
    private static CellView emptyNestCellView = null;       // The CellView of the empty nest for the chess got kicked
    private static Chess selectedChess = null;              // the chess selected by the player
    private static Cell selectedCell1 = null;               // the cell containing the chess selected by the player
    private static Cell selectedCell2 = null;               // the cell which the chess will move to
    private static boolean[] chosenPlayer = new boolean[4];
    private static Label blueScore, redScore, yellowScore, greenScore, history;
    private static ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle");

    //Necessary accessors and mutators for other classes to communicate with PlayerController
    //////////////////////////////////////////////////////////////////////////////////////////////
    //Accessors
    public static ObservableList<ImageView> getChessViewList()
    {
        return chessViewList;
    }

    public static ImageView getSelectedChessView()
    {
        return selectedChessView;
    }

    public static CellView getEmptyNestCellView()
    {
        return emptyNestCellView;
    }

    public static ImageView getKickedChessView()
    {
        return kickedChessView;
    }

    public static Chess getSelectedChess()
    {
        return selectedChess;
    }

    public static Cell getSelectedCell1()
    {
        return selectedCell1;
    }

    public static Player getPlayer(int number)
    {
        return player[number];
    }

    public static Player getPlayer(Player.Color color)
    {
        for (int i = 0; i < 4; i ++)
        {
            if (color == player[i].getColor())
                return player[i];
        }
        return null;
    }

    public static CellView getSelectedCellView1()
    {
        return selectedCellView1;
    }

    public static CellView getSelectedCellView2()
    {
        return selectedCellView2;
    }

    //Mutators
    public static void setSelectedChess(Chess selectedChess)
    {
        PlayerController.selectedChess = selectedChess;
    }

    public static void setKickedChessView(ImageView kickedChessView)
    {
        PlayerController.kickedChessView = kickedChessView;
    }

    public static void setSelectedCellView1(CellView selectedCellView1)
    {
        PlayerController.selectedCellView1 = selectedCellView1;
    }
    ///////////////////////////////////////////////////////////////////////////////////
    // this method is to get the corresponding ChessView of the CellView clicked by the player
    public static ImageView getChessView(CellView cellView)
    {
        for (int i = 0; i < chessViewList.size(); i++)
        {
            if((chessViewList.get(i).getLayoutX() == (cellView.getLayoutX() + 4))
                    && (chessViewList.get(i).getLayoutY() == (cellView.getLayoutY() + 4)))
                return chessViewList.get(i);
        }
        return null;
    }

    public static boolean[] getChosenPlayer() {
        return chosenPlayer;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      INITIALIZE METHOD                                        //
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public static void initialize(CharSequence[] str, boolean[] comPlayer, boolean[] chosenPlayer, Label blueScore, Label redScore, Label yellowScore, Label greenScore, Label history)
    {
        PlayerController.chosenPlayer = chosenPlayer;
        for (int i = 0; i < 4; i++) {
            player[i] = (comPlayer[i]) ? new ComputerPlayer(color[i]) : new HumanPlayer(color[i]);
            player[i].setName(String.valueOf(str[i]));
        }
        TurnController.setComOrHuman(comPlayer);
        PlayerController.blueScore = blueScore;
        PlayerController.redScore = redScore;
        PlayerController.yellowScore = yellowScore;
        PlayerController.greenScore = greenScore;
        PlayerController.history = history;
    }

    // this method is to perform the move of the computer player
    public static void computerMove()
    {
        Player currentPlayer = TurnController.getCurrentPlayer();                                                       //get the current player
        int[] diceValue = TurnController.getDiceValue();                                                                //get dice value
        selectedCell2 = ((ComputerPlayer) currentPlayer).makeDecision(MoveController.getPossibleMoves(), diceValue);    //make the move decision
        selectedChess = currentPlayer.getChess(MoveController.getPossibleMoves().get(selectedCell2)[0]);
        TurnController.updateConditions(selectedCell2);         //update conditions in TurnController(which move has been chosen)//
        selectedCell1 = CellController.getCell(selectedChess.getCellId());
        updateClients(selectedCell1, selectedCell2);
        selectedCellView1 = CellController.getCellView(selectedCell1);
        selectedCellView2 = CellController.getCellView(selectedCell2);
        selectedChessView = getChessView(selectedCellView1);
        if (selectedCell2.getChess() != null)                   // if the chosen cell contains a chess
        {
            Chess anotherChess = selectedCell2.getChess();
            kickChess(anotherChess);                            // kick the chess
        }
        currentPlayer.moveChess(selectedChess, selectedCell1, selectedCell2, TurnController.getDiceValueWasUsed());     // update the move to models
        AnimationController.animateChessMoving();               // perform the animation on GUI
    }

    // this method is to let the player select the chess they want
    public static void selectChess(MouseEvent event)
    {
        Object selectedObject = event.getSource();

        // get the data from the click of the player
        if (selectedObject instanceof ImageView)                                // if player clicks on the chess,
        {
            selectedChessView = (ImageView) selectedObject;                     // get the ChessView
            selectedCellView1 = CellController.getCellView(selectedChessView);  // get the CellView from the ChessView
        } else if (selectedObject instanceof CellView)                          // if player clicks on the cell
        {
            selectedCellView1 = (CellView) selectedObject;                      // get the CellView
            selectedChessView = PlayerController.getChessView(selectedCellView1); // get the ChessView from the CellView
        }
        selectedCellView1.showCellSelection();                                   // show the cell has been selected
        selectedCell1 = CellController.getCell(selectedCellView1);              // get the cell from the selected CellView

        // check if it is a valid selection
        selectedChess = selectedCell1.getChess();                               //
        if (selectedChess != null)                                              // if a chess has been chosen,
        {                                                                       // check if it is the chess in turn
            if (!selectedChess.getColor().toString().equals(TurnController.getPlayerTurn().toString()))
                selectedChess = null;                                           // if not reset the value of the selected Chess
        }
    }

    // this method is to let the player move the chess to the position they want
    public static boolean moveChess(MouseEvent event)
    {
        selectedCellView1.hideCellSelection();                                   // hide the cell selection
        if (event.getSource() instanceof ImageView)                              // if the player click on the chessView
        {
            selectedCellView2 = CellController.getCellView((ImageView) event.getSource()); //get the cell from the chessView
        }else if (event.getSource() instanceof CellView)                         // if the player clicks on the cellView
        {
            selectedCellView2 = (CellView) event.getSource();                    // get the cellView
        }
        selectedCell2 = CellController.getCell(selectedCellView2);               // get the cell from the cellView
        if (isValidMove())                                                       // check if it is a valid move
        {
            TurnController.updateConditions(selectedCell2);                      // update conditions in turn controller
            if (selectedCell2.getChess() != null)                                // if the selected cell containing a chess
            {
                Chess anotherChess = selectedCell2.getChess();                   // perform the kick
                kickChess(anotherChess);
            }
            //update the move to models
            TurnController.getCurrentPlayer().moveChess(selectedChess, selectedCell1, selectedCell2, TurnController.getDiceValueWasUsed());
            updateClients(selectedCell1, selectedCell2);
            selectedChess = null;
            return true;
        }
        selectedChess = null;
        return false;
    }

    public static void displayScore() {
        ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle");
        blueScore.setText(bundle.getString("score") + " " + getPlayer(Player.Color.BLUE).getScore());
        redScore.setText(bundle.getString("score") + " " + getPlayer(Player.Color.RED).getScore());
        yellowScore.setText(bundle.getString("score") + " " +  getPlayer(Player.Color.YELLOW).getScore());
        greenScore.setText(bundle.getString("score") + " " + getPlayer(Player.Color.GREEN).getScore());
    }

    // this method is created to handle the kick
    public static void kickChess(Chess kickedChess)
    {
        kickedChessView = PlayerController.getChessView(selectedCellView2);     //get the chessView of the chess got kicked
        Cell nestCell = CellController.findEmptyNest(kickedChess);              //find an empty nest for it
        emptyNestCellView = CellController.getCellView(nestCell);               //get the view of the empty nest
        nestCell.setChess(kickedChess);                                         //set the kickedChess to the nest
        kickedChess.getKicked(nestCell);
        TurnController.getCurrentPlayer().updateKickedScore(2);                 //+2 points for kicker
        getPlayer(kickedChess.getColor()).updateKickedScore(-2);                //-2 points to kicked
        history.setText(bundle.getString(String.valueOf(TurnController.getCurrentPlayer().getColor())) + bundle.getString("kick") + bundle.getString(String.valueOf(kickedChess.getColor())));
    }

    // this method is to get the Score when the chess advances home position
    public static void getHomeScore()
    {
        String cellId1 = selectedCell1.getId();
        String cellId2 = selectedCell2.getId();
        if (cellId2.contains("Home") && (cellId2.length() == cellId1.length()))
        {
            TurnController.getCurrentPlayer().updateKickedScore(cellId2.compareTo(cellId1));
        }
        displayScore();
    }

    // this method is to created to handle the move selection by the player
    public static boolean isValidMove()
    {
        if (!MoveController.getPossibleMoves().containsKey(selectedCell2))      //if the pickedCell does not exist in the possible move list
            return false;                                                       //it is an invalid move
        else                                                                    //if it exist in the possible move list
        {
            int chessNumber = MoveController.getPossibleMoves().get(selectedCell2)[0];  // get the corresponding chessNumber in the move
            if (selectedChess == TurnController.getCurrentPlayer().getChess(chessNumber))   //if player choose the correct chess,
            {
                return true;                                                                //it is the valid move
            }
            // if the chess chosen is not exist in the possible list, check if it is spawning
            else if (selectedCell1.getId().contains("Nest") && selectedCell2.getId().contains("Spawn")) //if yes, it is a valid move
            {
                return true;
            }
        }
        return false;
    }

    // this method is created to allow the human player to play
    public static void HumanMove()
    {
        if(!MoveController.isMovable())
        {
            TurnController.endTurn();
        }
    }

    //this method is to check if player has chosen a spawn move
    public static boolean isASpawnMove()
    {
        return selectedCell2.getId().contains(TurnController.getPlayerTurn().toString().toLowerCase() + "Spawn");
    }

    //this method is to check if player has chosen a home arrival move
    public static boolean isAHomeArrivalMove()
    {
        return (selectedCellView1.getId().contains(TurnController.getPlayerTurn().toString().toLowerCase() + "Home0"));
    }

    //this method is to check if player is allow to click on the GUI
    public static boolean isAllowToClick()
    {
        return (TurnController.isDiceIsRolled() && !TurnController.isCurrentPlayerIsComputer()
                && !AnimationController.isChessMoving() && !TurnController.opponentTurn);
    }

    //this method is to wait for the opponent to roll dice
    public static void waitForOpponentRollDice()
    {
        String diceValueInString = SocketController.getMessage(); // get the dice value in String
        // convert dice value in string to integer and store to Turn Controller
        int[] diceValueInInteger = new int[3];
        diceValueInInteger[0] = Integer.parseInt(diceValueInString) / 10;
        diceValueInInteger[1] = Integer.parseInt(diceValueInString) % 10;
        diceValueInInteger[2] = diceValueInInteger[0] + diceValueInInteger[1];
        TurnController.setDiceValue(diceValueInInteger);
    }

    //this method is to wait for opponent move
    public static void waitForOpponentMove()
    {
        String cellId1 = SocketController.getMessage();
        String cellId2 = SocketController.getMessage();
        PlayerController.updateMove(cellId1, cellId2, true);
    }

    //this method is to send data to clients
    public static void updateClients(Cell currentCell, Cell destinationCell)
    {
        if (GamePlayController.playOnline)
        {
            SocketController.sendMessage(currentCell.getId());
            SocketController.sendMessage(destinationCell.getId());
        }
    }

    // this method is to update the move of the opponent
    public static void updateMove(String cell1Id, String cell2Id, boolean animation)
    {
        Cell cell2 = CellController.getCell(cell2Id);
        Cell cell1 = CellController.getCell(cell1Id);
        Chess chess = cell1.getChess();
        CellView cellView1 = CellController.getCellView(cell1);
        CellView cellView2 = CellController.getCellView(cell2);
        ImageView chessView = PlayerController.getChessView(cellView1);
        selectedChessView = chessView;
        selectedCellView1 = cellView1;
        selectedCellView2 = cellView2;
        selectedCell1 = cell1;
        selectedCell2 = cell2;
        Chess kickedChess = cell2.getChess();
        if (kickedChess != null)
        {
            kickChess(kickedChess);                                //get the view of the empty nest
        }
        chess.setCellId(cell2.getId());
        cell1.setChess(null);
        cell2.setChess(chess);

        if (animation == true)
        {
            AnimationController.animateChessMoving();
        } else
        {
            chessView.setLayoutX(cellView2.getLayoutX() + 4);
            chessView.setLayoutY(cellView2.getLayoutY() + 4);
        }
    }
}
