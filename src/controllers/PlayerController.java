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
import javafx.scene.input.MouseEvent;
import models.*;
import views.CellView;

public class PlayerController
{
    private static Player[] player = new Player[4];
    private static ImageView kickedChessView = null;
    private static ImageView selectedChessView = null;
    private static CellView selectedCellView1 = null;
    private static CellView selectedCellView2 = null;
    private static CellView emptyNestCellView = null;

    private static ObservableList<ImageView> chessViewList = FXCollections.observableArrayList();

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

    public static void setKickedChessView(ImageView kickedChessView)
    {
        PlayerController.kickedChessView = kickedChessView;
    }

    public static ImageView getKickedChessView()
    {
        return kickedChessView;
    }

    private static Chess selectedChess = null;
    private static Cell selectedCell1 = null;
    private static Cell selectedCell2 = null;

    public static void initialize()
    {
        player[0] = new ComputerPlayer(Player.Color.BLUE);
        player[0].setName("Player");
        player[1] = new ComputerPlayer(Player.Color.RED);
        player[1].setName("Comp1");
        player[2] = new ComputerPlayer(Player.Color.GREEN);
        player[2].setName("Comp3");
        player[3] = new ComputerPlayer(Player.Color.YELLOW);
        player[3].setName("Comp2");
    }
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

    public static void computerMove()
    {
        Player currentPlayer = TurnController.getCurrentPlayer();
        int[] diceValue = TurnController.getDiceValue();
        selectedCell2 = ((ComputerPlayer) currentPlayer).makeDecision(MoveController.getPossibleMoves(), diceValue);
        TurnController.updateConditions(selectedCell2);
        selectedChess = currentPlayer.getChess(MoveController.getPossibleMoves().get(selectedCell2)[0]);
        selectedCell1 = CellController.getCell(selectedChess.getCellId());
        selectedCellView1 = CellController.getCellView(selectedCell1);
        selectedCellView2 = CellController.getCellView(selectedCell2);
        selectedChessView = getChessView(selectedCellView1);
        if (selectedCell2.getChess() != null)
        {
            Chess anotherChess = selectedCell2.getChess();
            kickChess(anotherChess);
        }
        currentPlayer.moveChess(selectedChess, selectedCell1, selectedCell2, TurnController.getDiceValueWasUsed());
        AnimationController.animateChessMoving();
    }


    public static void selectChess(MouseEvent event)
    {
        Object selectedObject = event.getSource();

        if (selectedObject instanceof ImageView)
        {
            System.out.println("12455");
            selectedChessView = (ImageView) selectedObject;
            selectedCellView1 = CellController.getCellView(selectedChessView);
        } else if (selectedObject instanceof CellView)
        {
            selectedCellView1 = (CellView) selectedObject;
            selectedChessView = PlayerController.getChessView(selectedCellView1);
        }
        selectedCellView1.showPossibleCell();
        selectedCell1 = CellController.getCell(selectedCellView1);

        selectedChess = selectedCell1.getChess();
        if (selectedChess != null)
        {
            if (!selectedChess.getColor().toString().equals(TurnController.getPlayerTurn().toString()))
                selectedChess = null;
        }
    }

    public static boolean moveChess(MouseEvent event)
    {
        System.out.println("123");
        selectedCellView1.hidePossibleCell();
        if (event.getSource() instanceof CellView)
        {
            selectedCellView2 = (CellView) event.getSource();
            selectedCell2 = CellController.getCell(selectedCellView2);
            if (isValidMove())
            {
                TurnController.updateConditions(selectedCell2);
                if (selectedCell2.getChess() != null)
                {
                    Chess anotherChess = selectedCell2.getChess();
                    kickChess(anotherChess);
                }
                TurnController.getCurrentPlayer().moveChess(selectedChess, selectedCell1, selectedCell2, TurnController.getDiceValueWasUsed());
                System.out.println("here selectedChess = " + selectedChess.getCellId());
                System.out.println("here cell1 = " + selectedCell1.getId());
                System.out.println("here cell1 = " + selectedCell2.getId());
                System.out.println("dice value was used =  " + TurnController.getDiceValueWasUsed());
                selectedChess = null;
                return true;
            }
        }
        selectedChess = null;
        return false;
    }

    public static void setSelectedChessView(ImageView selectedChessView)
    {
        PlayerController.selectedChessView = selectedChessView;
    }

    public static CellView getSelectedCellView1()
    {
        return selectedCellView1;
    }

    public static void setSelectedCellView1(CellView selectedCellView1)
    {
        PlayerController.selectedCellView1 = selectedCellView1;
    }

    public static CellView getSelectedCellView2()
    {
        return selectedCellView2;
    }

    public static void setSelectedCellView2(CellView selectedCellView2)
    {
        PlayerController.selectedCellView2 = selectedCellView2;
    }

    public static Chess getSelectedChess()
    {
        return selectedChess;
    }

    public static void setSelectedChess(Chess selectedChess)
    {
        PlayerController.selectedChess = selectedChess;
    }

    public static void kickChess(Chess kickedChess)
    {
        kickedChessView = PlayerController.getChessView(selectedCellView2);
        Cell nestCell = CellController.findEmptyNest(kickedChess);
        emptyNestCellView = CellController.getCellView(nestCell);
        nestCell.setChess(kickedChess);
        kickedChess.getKicked(nestCell);
    }

    public static boolean isValidMove()
    {
        if (!MoveController.getPossibleMoves().containsKey(selectedCell2))
            return false;
        int chessNumber = MoveController.getPossibleMoves().get(selectedCell2)[0];
        if (selectedChess == TurnController.getCurrentPlayer().getChess(chessNumber))
            return true;
        return false;
    }

    public static void HumanMove()
    {
        if(!MoveController.isMovable())
            TurnController.endTurn();
    }

    public static boolean isASpawnMove()
    {
        return selectedCellView2.getId().contains(TurnController.getPlayerTurn().toString().toLowerCase() + "Spawn");
    }

    public static boolean isAHomeArrivalMove()
    {
        return (selectedCellView1.getId().contains(TurnController.getPlayerTurn().toString().toLowerCase() + "Home0"));
    }
}
