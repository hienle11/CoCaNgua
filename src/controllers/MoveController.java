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
import models.Chess;
import models.Player;

import java.util.HashMap;

public class MoveController
{
    private static HashMap<Cell, Integer[]> possibleMoves = new HashMap<>();

    public static HashMap<Cell, Integer[]> getPossibleMoves()
    {
        return possibleMoves;
    }

    public static boolean isMovable()
    {
        if (TurnController.getDiceWasUsed() == 2)
            return false;
        possibleMoves.clear();
        calculatePossibleMoves();
        if (possibleMoves.size() == 0)
            return false;
        else
            showPossibleCells();
        return true;
    }

    private static void calculatePossibleMoves()
    {
        Cell currentCell;
        Chess checkedChess;
        for (int i = 0 ; i < 4; i ++)
        {
            if(i == TurnController.getChessNumberHasMoved())
                continue;
            checkedChess = TurnController.getCurrentPlayer().getChess(i);
            currentCell = CellController.getCell(checkedChess.getCellId());
            int currentCellIndex = CellController.getCellList().indexOf(currentCell);
            if (currentCell.getId().contains("Nest"))
                getSpawnMoves(i);
            else if (currentCell.getId().contains(TurnController.getPlayerTurn().toString().toLowerCase() + "Home"))
            {
                if(currentCellIndex < 48)
                {
                    currentCellIndex = CellController.getHomeCellIndex();
                }
                getHomeArrivalMoves(currentCellIndex, i);
            }
            else
                getNormalMoves(currentCellIndex, i);
        }
    }

    private static void getNormalMoves(int currentCellIndex, int chessNumber)
    {
        boolean checkingResult = true;
        int finalCellIndex;
        for (int i = 0; i < 3; i ++)
        {
            if(TurnController.getDiceValue()[i] < 0)
                continue;
            if (TurnController.getCurrentPlayer().getChess(chessNumber).getHomeDistance() - TurnController.getDiceValue()[i] < 0)
            {
                continue;
            }
            if((finalCellIndex = checkFinalCell(currentCellIndex, i, false)) < 0)
            {
                continue;
            }
            for (int j = 1; j < TurnController.getDiceValue()[i]; j++)
            {
                checkingResult = checkOtherCells(currentCellIndex, j, false);
                if (checkingResult == false)
                {
                    break;
                }
            }
            if (checkingResult == true)
            {
                Integer [] chessAndDice = new Integer[2];
                chessAndDice[0] = chessNumber;
                chessAndDice[1] = i;
                possibleMoves.put(CellController.getCellList().get(finalCellIndex), chessAndDice);
            }
        }
    }

    private static int checkFinalCell(int currentCellIndex, int diceValueIndex, boolean homeArrival)
    {
        Cell finalCell;
        int checkedCellIndex;
        checkedCellIndex = currentCellIndex + TurnController.getDiceValue()[diceValueIndex];
        if (checkedCellIndex >= 48 && (!homeArrival))
            checkedCellIndex -= 48;
        finalCell = CellController.getCellList().get(checkedCellIndex);
        if (finalCell.getChess() != null)
        {
            if (finalCell.getChess().getColor() == TurnController.getPlayerTurn())
                return -1;
        }
        return checkedCellIndex;
    }

    private static boolean checkOtherCells(int currentCellIndex, int steps, boolean homeArrival)
    {
        Cell checkedCell;
        int checkedCellIndex;
        checkedCellIndex = currentCellIndex + steps;
        if (checkedCellIndex >= 48 && (!homeArrival))
        {
            checkedCellIndex -= 48;

        }
        checkedCell = CellController.getCellList().get(checkedCellIndex);
        if (checkedCell.getChess() != null)
        {
            return false;
        }
        return true;
    }

    private static void getSpawnMoves(int chessNumber)
    {
        Cell possibleCell = null;
        if (TurnController.doDicesContainSix())
        {
            Player.Color playerTurn = TurnController.getPlayerTurn();
            switch (playerTurn)
            {
                case BLUE:
                    possibleCell = CellController.getCell("blueSpawn");
                    break;
                case RED:
                    possibleCell = CellController.getCell("redSpawn");
                    break;
                case GREEN:
                    possibleCell = CellController.getCell("greenSpawn");
                    break;
                case YELLOW:
                    possibleCell = CellController.getCell("yellowSpawn");
                    break;
            }
            Chess chess = possibleCell.getChess();
            if (chess == null || playerTurn != chess.getColor())
            {
                Integer [] chessAndDice = new Integer[2];
                chessAndDice[0] = chessNumber;
                if(TurnController.getDiceValue()[0] == 6)
                    chessAndDice[1] = 0;
                else
                    chessAndDice[1] = 1;
                possibleMoves.put(possibleCell, chessAndDice);
            }
        }
    }

    private static void getHomeArrivalMoves(int currentCellIndex, int chessNumber)
    {
        Cell currentCell = CellController.getCellList().get(currentCellIndex);
        boolean checkingResult = false;
        int[] diceValue = TurnController.getDiceValue();
        for (int i = 0; i < 3; i ++)
        {
            if(diceValue[i] < 0)
                continue;
            if(diceValue[i] > 6)
                break;
            if(currentCell.getId().contains("0"))
            {
                if ((checkFinalCell(currentCellIndex, i, true)) < 0)
                    continue;
                for (int j = 1; j < diceValue[i]; j++)
                {
                    if (checkingResult = checkOtherCells(currentCellIndex, j, true))
                        break;
                }
            } else if (currentCell.getId().contains("" + (diceValue[i]-1)))
            {
                if (CellController.getCellList().get(currentCellIndex + 1).getChess() != null)
                    continue;
                else
                    checkingResult = true;
            }
            if (checkingResult == true)
            {
                Integer [] chessAndDice = new Integer[2];
                chessAndDice[0] = chessNumber;
                chessAndDice[1] = i;
                if(currentCell.getId().contains("0"))
                {
                    possibleMoves.put(CellController.getCellList().get(currentCellIndex + diceValue[i]), chessAndDice);
                }else
                {
                    possibleMoves.put(CellController.getCellList().get(currentCellIndex + 1), chessAndDice);
                }
                checkingResult = false;
            }
        }
    }

    private static void showPossibleCells()
    {
        possibleMoves.forEach((cell, chessAndDice) ->
        {
            CellController.getCellView(cell).showCellSelection();
        });
    }

    public static void hidePossibleCells()
    {
        possibleMoves.forEach((cell, chessAndDice) ->
        {
            CellController.getCellView(cell).hideCellSelection();
        });
    }

}
