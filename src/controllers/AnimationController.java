/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 10/01/2020
  By: Le Quang Hien (s3695516)
  Last modified: 14/01/2020
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package controllers;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import views.CellView;

public class AnimationController
{


    private static ImageView dice0, dice1;
    public static boolean isChessMoving()
    {
        return chessIsMoving;
    }
    private static boolean chessIsMoving = false;

    // initialize by getting the ImageView of 2 dices
    public static void initialize(ImageView newDice0, ImageView newDice1)
    {
        dice0 = newDice0;
        dice1 = newDice1;
    }

    //this function animates the rolling dices
    public static RotateTransition diceRoll() {
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

        return rt1;
    }

    //this function sets the images of the dices and check for computer or opponent's moves after the animation of the dices is finished
    public static void animateDiceRolling()
    {
        MediaController.playRollSound();
        TurnController.setDiceIsRolled(true);
        RotateTransition rt1 = diceRoll();

        rt1.setOnFinished(e ->
        {
            // when rolling animation finished, det the ImageView of 2 dices to the corresponding values
            dice0.setImage(new Image("File:src/resources/images/" + TurnController.getDiceValue()[0] + ".jpg"));
            dice1.setImage(new Image("File:src/resources/images/" + TurnController.getDiceValue()[1] + ".jpg"));
            if (TurnController.initialRollDice)         // if it is a the initial rolld ice turn
            {
                TurnController.getInitialDiceValue();   // get the initialDiceValue
            }
            else if (TurnController.opponentTurn)       // if it is an opponentTurn (online mode)
            {
                if (SocketController.getMessage().equals("movable"))    // wait for message, if it equals to "movable"
                    PlayerController.waitForOpponentMove();             // wait for oppornent to move
                else
                    TurnController.endTurn();                           // if it is not "movable", end the turn
            }
            else if (TurnController.isCurrentPlayerIsComputer())     // if it is computer turn
            {
                if (MoveController.isMovable())                      // check if it is movable
                    PlayerController.computerMove();                 // if so, execute computer move
                else
                    TurnController.endTurn();                        // if it is not movable, end the turn
            } else                                                   // if it is our turn
            {
                PlayerController.HumanMove();                        // play our turn
            }
        });

        rt1.play();
    }

    //this function animates the moving chess
    public static void animateChessMoving()
    {
        CellView currentCellView;
        currentCellView = PlayerController.getSelectedCellView1();
        chessIsMoving = true;

        if (currentCellView != PlayerController.getSelectedCellView2()) // check if the chess has reached the destination
        {
            animateChessNormalMoving(currentCellView);                  // if not continue to perform the moving animation
        }
        else if (PlayerController.getKickedChessView() != null)         // if the chess has reached the destination,
        {                                                               // check if it is kicking another chess
            animateChessGettingKicked();                                // perform the animation of the chess gets kicked
        } else
        {
            ///set the position of the chessView after finishing transition path
            PlayerController.getSelectedChessView().setLayoutX(PlayerController.getSelectedCellView2().getLayoutX() + 4);
            PlayerController.getSelectedChessView().setLayoutY(PlayerController.getSelectedCellView2().getLayoutY() + 4);
            PlayerController.getSelectedChessView().setTranslateX(0);
            PlayerController.getSelectedChessView().setTranslateY(0);
            PlayerController.setSelectedChess(null);

            chessIsMoving = false;
            PlayerController.getHomeScore();                            // update score
            if (TurnController.opponentTurn)                            // if it is the opponentTurn(multiplayer mode)
            {
                if (SocketController.getMessage().equals("movable"))    // wait for message "movable"
                    PlayerController.waitForOpponentMove();             // wait for opponentTurn(multiplayer mode)
                else
                    TurnController.endTurn();                           // end turn
            } else                                                      // if it is not opponent turn
            {
                if (!MoveController.isMovable())                        // check if it is movable
                    TurnController.endTurn();                           // if not end turn
                else if (TurnController.isCurrentPlayerIsComputer())    // if it is movable and player is compputer
                    PlayerController.computerMove();                    // execute computer move
            }
        }
    }

    //this function animates the chess when kicked
    private static void animateChessGettingKicked()
    {
        // get the cellview of empty nest and the chessview of the chess gets kicked
        CellView emptyNest = PlayerController.getEmptyNestCellView();
        ImageView kickedChessView = PlayerController.getKickedChessView();

        // create the path for transition
        Line line = new Line();
        line.setStartX(14);
        line.setStartY(14);
        line.setEndX(emptyNest.getLayoutX() - kickedChessView.getLayoutX() + 10);
        line.setEndY(emptyNest.getLayoutY() - kickedChessView.getLayoutY() + 10);

        PathTransition transition = new PathTransition();
        transition.setNode(PlayerController.getKickedChessView());
        transition.setDuration(Duration.seconds(0.5));
        transition.setPath(line);
        transition.setCycleCount(1);

        MoveController.hidePossibleCells();

        //once the path finish, update the position of the ChessView and return to the animateChessMoving() method
        transition.setOnFinished(e->
        {
            PlayerController.getKickedChessView().setLayoutX(PlayerController.getEmptyNestCellView().getLayoutX() + 4);
            PlayerController.getKickedChessView().setLayoutY(PlayerController.getEmptyNestCellView().getLayoutY() + 4);
            PlayerController.getKickedChessView().setTranslateX(0);
            PlayerController.getKickedChessView().setTranslateY(0);
            PlayerController.setKickedChessView(null);
            animateChessMoving();
        });
        transition.play();                  //start the transition
        MediaController.playKickSound();    // play the kick sound
    }

    //this function animates the chess when moving normally
    private static void animateChessNormalMoving(CellView currentCellView)
    {
        CellView nextCellView;

        if (PlayerController.isASpawnMove())
            nextCellView = PlayerController.getSelectedCellView2();
        else
            nextCellView = CellController.getNextCellView(currentCellView);

        //set the transition path
        Line line = new Line();
        line.setStartX(14);
        line.setStartY(14);
        line.setEndX(nextCellView.getLayoutX() - currentCellView.getLayoutX() + 14);
        line.setEndY(nextCellView.getLayoutY() - currentCellView.getLayoutY() + 14);

        PathTransition transition = new PathTransition();
        transition.setNode(PlayerController.getSelectedChessView());
        transition.setDuration(Duration.seconds(0.2));
        transition.setPath(line);
        transition.setCycleCount(1);

        MoveController.hidePossibleCells();
        if (PlayerController.isASpawnMove())            //if it is spawn move
        {
            MediaController.playWhinnySound();          //play whinny sound
        }
        else MediaController.playMoveSound();           //otherwise play move sound

        // update new position of chessView and goes back to animateChessMovingMethod();
        transition.setOnFinished(e ->
        {
            PlayerController.getSelectedChessView().setLayoutX(nextCellView.getLayoutX() + 4);
            PlayerController.getSelectedChessView().setLayoutY(nextCellView.getLayoutY() + 4);
            PlayerController.getSelectedChessView().setTranslateX(0);
            PlayerController.getSelectedChessView().setTranslateY(0);
            PlayerController.setSelectedCellView1(nextCellView);
            animateChessMoving();
        });

        transition.play();
    }
}
