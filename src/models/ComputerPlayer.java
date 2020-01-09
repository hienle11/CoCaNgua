/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 24/12/2019
  By: Le Quang Hien (s3695516)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package models;

import java.util.HashMap;
import java.util.Set;

public class ComputerPlayer extends Player
{
    public ComputerPlayer(Color color)
    {
        super(color);
    }

    public Chess selectChess()
    {
        return null;
    }

    public Cell makeDecision(HashMap<Cell, Integer[]> possibleMoves, int[] diceValue)
    {
        Set<Cell> possibleCellSet = possibleMoves.keySet();
        boolean itIsABetterMove = false;
        Cell pickedCell = null;
        int pickedStep = 0;
        int pickedChessNumber = 0;
        int movePriority = 0;
        int diceIndex;
        int chessNumber;
        for(Cell cell: possibleCellSet)
        {
            chessNumber = possibleMoves.get(cell)[0];
            diceIndex = possibleMoves.get(cell)[1];
            if(pickedCell == null)
                itIsABetterMove = true;
            else
            {
                System.out.println("pickedCell =" + pickedCell.getId());
                System.out.println("cell = " + cell.getId());
                System.out.println("------ ' " +this.getColor().toString().toLowerCase() + "Spawn");
                if (cell.getId().contains(this.getColor().toString().toLowerCase() + "Home") && movePriority <= 4)       // check if the Chess is arriving Home
                {
                    if (!pickedCell.getId().contains(this.getColor().toString().toLowerCase() + "Home"))       // if the pickedCell is not a HomeArrivalMove
                        itIsABetterMove = true;
                    else if (pickedStep < diceValue[diceIndex])     // if it is, compare which move leads the chess to higher position
                        itIsABetterMove = true;
                }
                else if (cell.getId().contains(this.getColor().toString().toLowerCase() + "Spawn") && movePriority <=3)  // check if the Chess is spawning
                {
                    itIsABetterMove = true;
                }
                else if(cell.getChess() != null && movePriority <=2) // check if the Chess is kicking
                {
                    if (pickedCell.getChess() == null)              // if it is, check the pickedCell
                        itIsABetterMove = true;                     // if it is not kicking than this is a better move
                    // if the pickedCell is also a kick
                    else if (this.getChess(chessNumber).getHomeDistance() < 24) // check if the chess move significantly near home
                    {   // if it is, check which one moves the nearer Chess.
                        if (isMovingNearerChess(pickedChessNumber, chessNumber))
                            itIsABetterMove = true;
                    }   // if it does not moves the chess significantly near home
                    else if (pickedCell.getChess().getHomeDistance() > cell.getChess().getHomeDistance())
                        // determines the better move by which kickedChess is nearer to its home
                        itIsABetterMove = true;
                } else if (movePriority <= 1)// if the chess is moving normally
                {
                    if (cell.getId().contains("Spawn"))
                        continue;
                    else if (isMovingNearerChess(pickedChessNumber, chessNumber))
                    {
                        System.out.println("here 1");
                        itIsABetterMove = true;
                    }
                    else if (pickedStep < diceValue[diceIndex])
                    {
                        System.out.println("here 2");
                        itIsABetterMove = true;
                    }
                }
            }
            if (itIsABetterMove)
            {
                pickedStep = diceValue[diceIndex];
                pickedCell = cell;
                pickedChessNumber = chessNumber;
                itIsABetterMove = false;
                movePriority = getMovePriority(pickedCell);
            }
        }
        System.out.println("finalPickedCell = " + pickedCell.getId());
        return pickedCell;
    }

    private int getMovePriority (Cell pickedCell)
    {
        if(pickedCell.getId().contains("Home"))
            return 4;
        else if(pickedCell.getId().contains("Spawn"))
            return 3;
        else if(pickedCell.getChess() != null)
            return 2;
        else
            return 1;
    }

    private boolean isMovingNearerChess(int pickedChessNumber, int chessNumber)
    {
        System.out.println("homeDistance1 = " + this.getChess(pickedChessNumber).getHomeDistance());
        System.out.println("homeDistance2 = " + this.getChess(chessNumber).getHomeDistance());

        return this.getChess(pickedChessNumber).getHomeDistance() > this.getChess(chessNumber).getHomeDistance();
    }
}
