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

import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import models.Player;

public class AnimationController
{
    private static ImageView dice0, dice1;
    private static Button rollDiceBt;

    public static void initialize(ImageView newDice0, ImageView newDice1, Button newRollDiceBt)
    {
        dice0 = newDice0;
        dice1 = newDice1;
        rollDiceBt = newRollDiceBt;
    }

    public static Button getRollDiceBt()
    {
        return rollDiceBt;
    }

    public static void rollDiceAnimation()
    {
        dice0.setImage(new Image("File:src/resources/images/6.jpg"));
        dice1.setImage(new Image("File:src/resources/images/6.jpg"));
        RotateTransition rt = new RotateTransition(Duration.seconds(0.1),dice0);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.setCycleCount(5);
        rt.play();
        RotateTransition rt1 = new RotateTransition(Duration.seconds(0.1),dice1);
        rt1.setFromAngle(0);
        rt1.setToAngle(360);
        rt1.setCycleCount(5);

        rollDiceBt.setOnAction(null);
        TurnController.setDiceValue(Player.rollDice());
        int[] diceValue = TurnController.getDiceValue();
        dice0.setImage(new Image("File:src/resources/images/" + diceValue[0] + ".jpg"));
        dice1.setImage(new Image("File:src/resources/images/" + diceValue[1] + ".jpg"));
        TurnController.setDiceIsRolled(true);
        if (TurnController.isCurrentPlayerIsComputer())
        {
            rt1.setOnFinished(e -> PlayerController.computerMove());
        } else
            rt1.setOnFinished(e -> PlayerController.humanMove());
        rt1.play();
    }
}
