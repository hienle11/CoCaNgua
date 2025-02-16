/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 24/12/2019
  By: Le Quang Hien (s3695516)
  Last modified: 14/01/2020
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package models;

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
        for (int i = 0; i < 4; i ++)
        {
            String cellId = color.toString().toLowerCase() + "Nest" + (i+1);
            this.chess[i] = new Chess(color, cellId);
        }
    }

    public void updateKickedScore(int score)
    {
        this.score += score;
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

    public Chess getChess(int number)
    {
        return chess[number];
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
        int[] diceValue = new int[3];
        diceValue[0] = (int) (Math.random() * 6 + 1);
        diceValue[1] = (int) (Math.random() * 6 + 1);
        diceValue[2] = diceValue[0] + diceValue[1];
        return diceValue;
    }

    public void moveChess(Chess chess, Cell cell1, Cell cell2, int diceValue)
    {
        chess.moveTo(cell2, diceValue);
        cell1.setChess(null);
        cell2.setChess(chess);
    }
}
