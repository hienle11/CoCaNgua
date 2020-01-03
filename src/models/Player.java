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

import java.util.ArrayList;

public abstract class Player
{
    public enum Color
    {
        BLUE,
        RED,
        GREEN,
        YELLOW
    }
    private Color color;
    private int score;
    private ArrayList<Chess> chessList = new ArrayList<>();
    private String name;

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public ArrayList<Chess> getChessList()
    {
        return chessList;
    }

    public void setChessList(ArrayList<Chess> chessList)
    {
        this.chessList = chessList;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public static int[] rollDice()
    {
        int[] dice = new int[2];
        dice[0] = (int) (Math.random() * 6 + 1);
        dice[1] = (int) (Math.random() * 6 + 1);
        return dice;
    }

    abstract public Chess moveChess();
}
