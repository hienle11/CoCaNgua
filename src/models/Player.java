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
    private int score = 0;
    private Chess[] chess = new Chess[4];
    private String name;

    public Player(Color color)
    {
        this.color = color;
        this.chess[0] = new Chess(color);
        this.chess[1] = new Chess(color);
        this.chess[2] = new Chess(color);
        this.chess[3] = new Chess(color);
    }

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

    public Chess getChess(int number)
    {
        return chess[number];
    }

    public void setChess(Chess[] chess)
    {
        this.chess = chess;
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
