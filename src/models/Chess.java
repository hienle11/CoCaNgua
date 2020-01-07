/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 23/12/2019
  By: Le Quang Hien (s3695516)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package models;

public class Chess
{
    private Player.Color color;
    private String cellId;
    private int homeDistance = -1;

    public Chess(Player.Color color, String cellId)
    {
        this.color = color;
        this.cellId = cellId;
    }

    public Player.Color getColor()
    {
        return color;
    }

    public String getCellId()
    {
        return cellId;
    }


    public int getHomeDistance()
    {
        return homeDistance;
    }

    public void setHomeDistance(int numberOfMoves)
    {
        homeDistance -= numberOfMoves;
    }

    public void getKicked(Cell cell)
    {
        this.cellId = cell.getId();
        homeDistance = -1;
    }

    public void moveTo(Cell cell, int steps)
    {
        if (homeDistance == -1)
            homeDistance = 47;
        else
            homeDistance -= steps;
        this.cellId = cell.getId();
    }

}
