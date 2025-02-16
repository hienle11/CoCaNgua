/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 03/01/2020
  By: Le Quang Hien (s3695516)
  Last modified: 14/01/2020
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import models.Cell;
import models.Chess;
import views.CellView;

import java.util.ArrayList;

public class CellController
{
    // properties of CellController
    private static ObservableList<CellView> cellViewList = FXCollections.observableArrayList(); // list of CellViews
    private static ArrayList<Cell> cellList = new ArrayList<>();                                // list of Cell

    // accessors for cellView list and cell List
    public static ObservableList<CellView> getCellViewList()
    {
        return cellViewList;
    }           //

    public static ArrayList<Cell> getCellList()
    {
       return cellList;
    }

    // this method is to get the Cell by its id
    public static Cell getCell(String id)
    {
        for (int i = 0; i < cellList.size(); i ++)
        {
            if (cellList.get(i).getId().equals(id))
                return cellList.get(i);
        }
        return null;
    }

    // this method is to get the Cell from the corresponding CellView
    public static Cell getCell(CellView cellView)
    {
        for (int i = 0; i < cellList.size(); i ++)
        {
            if (cellList.get(i).getId().equals(cellView.getId()))
                return cellList.get(i);
        }
        return null;
    }

    // this method is to get the CellView from the ChessView standing on it
    public static CellView getCellView(ImageView chessView)
    {
        for (int i = 0; i < cellViewList.size(); i++)
        {
            if((cellViewList.get(i).getLayoutX() == (chessView.getLayoutX() - 4))
                && (cellViewList.get(i).getLayoutY() == (chessView.getLayoutY() - 4)))
                return cellViewList.get(i);
        }
        return null;
    }

    // this method is to get the cellView from the corresponding cell
    public static CellView getCellView(Cell cell)
    {
        for (int i = 0; i < cellList.size(); i ++)
        {
            if (cellViewList.get(i).getId().equals(cell.getId()))
                return cellViewList.get(i);
        }
        return null;
    }

    // this method is to get the nextCellView of a cellView
    public static CellView getNextCellView(CellView currentCellView)
    {
        int currentCellViewIndex;
        if (PlayerController.isAHomeArrivalMove())
        {
            currentCellViewIndex = getHomeCellIndex();
        }else
        {
            currentCellViewIndex = cellViewList.indexOf(currentCellView);
        }
        return cellViewList.get(currentCellViewIndex + 1);
    }

    // this method is to find an empty nest for the chess got kicked
    public static Cell findEmptyNest(Chess chess)
    {
        Cell cell;
        String cellId = chess.getColor().toString().toLowerCase() + "Nest";
        for(int i = 1; i < 5; i ++)
        {
            cell = getCell(cellId + i);
            if (cell.getChess() == null)
                return cell;
        }
        return null;
    }

    // this method is to initialize the cell List and the cellView List
    public static void initialize()
    {

        for(int i = 0; i < cellViewList.size(); i++)
            cellList.add(new Cell(cellViewList.get(i).getId()));
        for (int i = 76; i < 92; i++)
        {
            if (i < 80)
            {
                cellList.get(i).setChess(PlayerController.getPlayer(0).getChess(i-76));
            }
            else if (i < 84)
            {
                cellList.get(i).setChess(PlayerController.getPlayer(1).getChess(i-80));
            }
            else if (i < 88)
            {
                cellList.get(i).setChess(PlayerController.getPlayer(2).getChess(i-84));
            }
            else
            {
                cellList.get(i).setChess(PlayerController.getPlayer(3).getChess(i-88));
            }
        }
    }

    // this method is to convert the normalCell index into homeCell index when a chess arrives at home0
    public static int getHomeCellIndex()
    {
        switch (TurnController.getPlayerTurn())
        {
            case BLUE:
                return 48;
            case RED:
                return 55;
            case GREEN:
                return 62;
            case YELLOW:
                return 69;
        }
        return -1;
    }
}
