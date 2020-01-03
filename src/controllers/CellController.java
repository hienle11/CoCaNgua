package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import models.Cell;
import views.ChessView;

import java.util.ArrayList;

public class CellController
{
    private static final String blueChessImage = "File:src/resources/images/BLUE.jpg";
    private static final String redChessImage = "File:src/resources/images/RED.jpg";
    private static final String yellowChessImage = "File:src/resources/images/YELLOW.jpg";
    private static final String greenChessImage = "File:src/resources/images/GREEN.jpg";

    private ObservableList<StackPane> cellViewList = FXCollections.observableArrayList();
    private Cell[] cell = null;
    private ChessController chessController = null;

    public ObservableList<StackPane> getCellViewList()
    {
        return cellViewList;
    }

    public Cell getCell(int number)
    {
        return cell[number];
    }

    public Cell getCell(String id)
    {
        for (int i = 0; i < cellViewList.size(); i++)
        {
            if (cell[i].getId().equals(id))
                return cell[i];
        }
        return null;
    }

    public void initialize()
    {
        cell = new Cell[cellViewList.size()];
        for(int i = 0; i < cellViewList.size(); i++)
        {
            cell[i] = new Cell(cellViewList.get(i).getId());
        }
        chessController = new ChessController();
        chessController.initialize();
        for (int i = 72; i < 88; i++)
        {
            if (i < 76)
            {
                cellViewList.get(i).getChildren().add(new ChessView(blueChessImage));
                cell[i].setChess(chessController.getBlueChess(i-72));
            }
            else if (i < 80)
            {
                cellViewList.get(i).getChildren().add(new ChessView(redChessImage));
                cell[i].setChess(chessController.getRedChess(i-76));
            }
            else if (i < 84)
            {
                cellViewList.get(i).getChildren().add(new ChessView(greenChessImage));
                cell[i].setChess(chessController.getGreenChess(i-80));
            }
            else
            {
                cellViewList.get(i).getChildren().add(new ChessView(yellowChessImage));
                cell[i].setChess(chessController.getYellowChess(i-84));
            }
        }

    }
}
