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
    public enum Color
    {
        BLUE,
        RED,
        GREEN,
        YELLOW
    }

    private String image;
    private Color color;
    private int position;
    private Location location;
    private int homeDistance;

    Chess(Color color, Location location, String image)
    {
        this.color = color;
        this.location = location;
        this.image = image;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
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

    public void move(int numberOfMoves)
    {
        position += numberOfMoves;
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
