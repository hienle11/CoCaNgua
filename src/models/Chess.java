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
    public enum Location
    {
        NEST,
        NORMAL,
        HOME
    }

    private Player.Color color;
    private String cellId;
    private Location location = Location.NEST;
    private int homeDistance;

    public Chess(Player.Color color, String cellId)
    {
        this.color = color;
        this.cellId = cellId;
    }

    public Player.Color getColor()
    {
        return color;
    }

    public void setColor(Player.Color color)
    {
        this.color = color;
    }

    public String getCellId()
    {
        return cellId;
    }

    public void setCellId(String cellId)
    {
        this.cellId = cellId;
    }

    public Location getLocation()
    {
        return location;
    }

    public int getHomeDistance()
    {
        return homeDistance;
    }

    public void setLocation(Location newLocation)
    {
        location = newLocation;
    }

    public void setHomeDistance(int numberOfMoves)
    {
        homeDistance -= numberOfMoves;
    }

    public void moveTo(Cell cell)
    {
        this.cellId = cell.getId();
    }

    private void kick(Chess anotherChess)
    {
        anotherChess.setLocation(Location.NEST);
    }

    public void spawn()
    {
        location = Location.HOME;
    }
}
