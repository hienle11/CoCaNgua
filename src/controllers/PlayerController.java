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

import models.Cell;
import models.ComputerPlayer;
import models.HumanPlayer;
import models.Player;
import views.CellView;
import views.ChessView;

public class PlayerController
{
    private static Player[] player = new Player[4];


    public static void initialize()
    {
        player[0] = new HumanPlayer(Player.Color.BLUE);
        player[0].setName("Player");
        player[1] = new HumanPlayer(Player.Color.RED);
        player[1].setName("Comp1");
        player[2] = new HumanPlayer(Player.Color.GREEN);
        player[2].setName("Comp3");
        player[3] = new HumanPlayer(Player.Color.YELLOW);
        player[3].setName("Comp2");
    }

    public static Player getPlayer(int number)
    {
        return player[number];
    }

    public static Player getPlayer(Player.Color color)
    {
        for (int i = 0; i < 4; i ++)
        {
            if (color == player[i].getColor())
                return player[i];
        }
        return null;
    }

}
