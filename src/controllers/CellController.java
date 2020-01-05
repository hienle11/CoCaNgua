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
import javafx.scene.layout.StackPane;
import models.Cell;
import views.ChessView;

public class CellController
{
    private static final String blueChessImage = "File:src/resources/images/BLUE.jpg";
    private static final String redChessImage = "File:src/resources/images/RED.jpg";
    private static final String yellowChessImage = "File:src/resources/images/YELLOW.jpg";
    private static final String greenChessImage = "File:src/resources/images/GREEN.jpg";

    private static ObservableList<StackPane> cellViewList = FXCollections.observableArrayList();
    private static Cell[] cell = null;

    public static ObservableList<StackPane> getCellViewList()
    {
        return cellViewList;
    }

    public static Cell getCell(int number)
    {
        return cell[number];
    }

    public static Cell getCell(String id)
    {
        for (int i = 0; i < cellViewList.size(); i++)
        {
            if (cell[i].getId().equals(id))
                return cell[i];
        }
        return null;
    }

    public static void initialize()
    {
        cell = new Cell[cellViewList.size()];
        for(int i = 0; i < cellViewList.size(); i++)
        {
            cell[i] = new Cell(cellViewList.get(i).getId());
        }
        for (int i = 72; i < 88; i++)
        {
            if (i < 76)
            {
                cellViewList.get(i).getChildren().add(new ChessView(blueChessImage));
                cell[i].setChess(PlayerController.getPlayer(0).getChess(i-72));
                System.out.println("here = " + cell[i].getChess().getColor());
            }
            else if (i < 80)
            {
                cellViewList.get(i).getChildren().add(new ChessView(redChessImage));
                cell[i].setChess(PlayerController.getPlayer(1).getChess(i-76));
            }
            else if (i < 84)
            {
                cellViewList.get(i).getChildren().add(new ChessView(greenChessImage));
                cell[i].setChess(PlayerController.getPlayer(2).getChess(i-80));
            }
            else
            {
                cellViewList.get(i).getChildren().add(new ChessView(yellowChessImage));
                cell[i].setChess(PlayerController.getPlayer(3).getChess(i-84));
            }
        }
    }


}
