/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 10/01/2020
  By: Phuoc (s3695516)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Phuoc (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Player;

// this class is created to handle actions of buttons
public class ButtonController
{
    // store the path of fxml files in to Strings
    private static final String gamePlayWindow = "../views/GamePlay.fxml";
    private static final String mainMenuWindow = "../views/MainMenu.fxml";

    private static Button rollDiceBt = null;

    public static Button getRollDiceBt()
    {
        return rollDiceBt;
    }

    public static void initialize(Button newRollDiceButton)
    {
        rollDiceBt = newRollDiceButton;
    }

    // this method is created to handle the Quit Game Button
    public static void quitGameBtHandler()
    {
        Platform.exit();
    }

    //this method is created to handle the Exit Game Button
    public static void exitGameBtHandler(ActionEvent event)
    {
        System.exit(0);
    }

//    // this method is created to handle the Sound Button
//    public static void soundBtHandler(ActionEvent event)
//    {
//        String btImage;
//        if(MediaController.isPlaying())             //check if the media is playing
//        {
//            MediaController.pause();                //if it is, pause it
//            btImage = "File:src/img/soundOff.png";
//        }
//        else
//        {
//            MediaController.play();
//            btImage = "File:src/img/soundOn.png";
//        }
//        //set the new ImageView for the Sound Buttonâ
//        Button button = (Button)event.getSource();
////        button.setGraphic((imageView));
//    }

    //this method is created to handle the Start Game Button
    public static void playAgBtHandler(ActionEvent event)
    {
        // get the current stage and load a new scene;
        WindowController window = new WindowController((Stage) ((Node)event.getSource()).getScene().getWindow());
        window.loadWindow(gamePlayWindow);
    }


    public static void rollDiceBtHandler()
    {
        rollDiceBt.setOnAction(null);
        if (TurnController.opponentTurn)
        {
            PlayerController.waitForOpponentRollDice();
        }
        else
        {
            System.out.println("I roll");
            TurnController.setDiceValue(Player.rollDice());
            int[] diceValue = TurnController.getDiceValue();
            if (GamePlayController.playOnline)
                SocketController.sendMessage("" + diceValue[0] + diceValue[1]);
        }
        TurnController.setDiceIsRolled(true);
        AnimationController.animateDiceRolling();
    }

    public static void enableRollDiceBt()
    {
        rollDiceBt.setOnAction(e-> rollDiceBtHandler());
    }

    public static void disableRollDiceBt()
    {
        rollDiceBt.setOnAction(null);
    }
}