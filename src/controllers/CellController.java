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
import models.Cell;
import views.CellView;
import views.ChessView;

import java.util.ArrayList;

public class CellController
{
    private static final String blueChessImage = "File:src/resources/images/BLUE.jpg";
    private static final String redChessImage = "File:src/resources/images/RED.jpg";
    private static final String yellowChessImage = "File:src/resources/images/YELLOW.jpg";
    private static final String greenChessImage = "File:src/resources/images/GREEN.jpg";

    private static ObservableList<CellView> cellViewList = FXCollections.observableArrayList();
    private static ArrayList<Cell> cellList = new ArrayList<>();

    public static ObservableList<CellView> getCellViewList()
    {
        return cellViewList;
    }


    public static ArrayList<Cell> getCellList()
    {
       return cellList;
    }

    public static Cell getCell(String id)
    {
        for (int i = 0; i < cellList.size(); i ++)
        {
            if (cellList.get(i).getId().equals(id))
                return cellList.get(i);
        }
        return null;
    }
    public static void initialize()
    {
        for(int i = 0; i < cellViewList.size(); i++)
            cellList.add(new Cell(cellViewList.get(i).getId()));

        for (int i = 72; i < 88; i++)
        {
            if (i < 76)
            {
                cellViewList.get(i).getChildren().add(new ChessView(blueChessImage));
                cellList.get(i).setChess(PlayerController.getPlayer(0).getChess(i-72));
            }
            else if (i < 80)
            {
                cellViewList.get(i).getChildren().add(new ChessView(redChessImage));
                cellList.get(i).setChess(PlayerController.getPlayer(1).getChess(i-76));
            }
            else if (i < 84)
            {
                cellViewList.get(i).getChildren().add(new ChessView(greenChessImage));
                cellList.get(i).setChess(PlayerController.getPlayer(2).getChess(i-80));
            }
            else
            {
                cellViewList.get(i).getChildren().add(new ChessView(yellowChessImage));
                cellList.get(i).setChess(PlayerController.getPlayer(3).getChess(i-84));
            }
        }
    }
}
