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

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import models.Player;
import views.CellView;

public class AnimationController
{
    private static int diceThrow = 0;
    private static ImageView dice0, dice1;

    public static boolean isChessMoving()
    {
        return chessIsMoving;
    }

    private static boolean chessIsMoving = false;

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
        ButtonController.getRollDiceBt().setOnAction(null);
        TurnController.setDiceValue(Player.rollDice());
        int[] diceValue = TurnController.getDiceValue();
        dice0.setImage(new Image("File:src/resources/images/" + diceValue[0] + ".jpg"));
        dice1.setImage(new Image("File:src/resources/images/" + diceValue[1] + ".jpg"));
//        TurnController.setDiceIsRolled(true);
        return rt1;
    }

//    public static void findMax(int[] maxThrow) {
//        int max = 0;
//        for (int k : maxThrow) {
//            if (max < k)
//        }
//    }

//    public static void determineFirstTurn() {
//        int[] maxThrow = new int[4];
//        RotateTransition rt1 = diceRoll();
//        if (TurnController.isCurrentPlayerIsComputer())
//        {
//            rt1.setOnFinished(e ->
//            {
//                maxThrow[diceThrow] = TurnController.getDiceValue()[2];
//                TurnController.endTurn();
//                diceThrow++;
//
//            });
//        }else
//            rt1.setOnFinished(e -> PlayerController.HumanMove());
//        rt1.play();
//
//    }

    public static void initialize(ImageView newDice0, ImageView newDice1)
    {
        dice0 = newDice0;
        dice1 = newDice1;
//        determineFirstTurn();
    }

    public static void animateDiceRolling()
    {
//        dice0.setImage(new Image("File:src/resources/images/6.jpg"));
//        dice1.setImage(new Image("File:src/resources/images/6.jpg"));
//        RotateTransition rt = new RotateTransition(Duration.seconds(0.1),dice0);
//        rt.setFromAngle(0);
//        rt.setToAngle(360);
//        rt.setCycleCount(5);
//        rt.play();
//        RotateTransition rt1 = new RotateTransition(Duration.seconds(0.1),dice1);
//        rt1.setFromAngle(0);
//        rt1.setToAngle(360);
//        rt1.setCycleCount(5);

//        ButtonController.getRollDiceBt().setOnAction(null);
//        TurnController.setDiceValue(Player.rollDice());
//        int[] diceValue = TurnController.getDiceValue();
//        dice0.setImage(new Image("File:src/resources/images/" + diceValue[0] + ".jpg"));
//        dice1.setImage(new Image("File:src/resources/images/" + diceValue[1] + ".jpg"));
//        TurnController.setDiceIsRolled(true);
//        TurnController.setDiceValue(Player.rollDice());

        MediaController.playRollSound();
        TurnController.setDiceIsRolled(true);
        RotateTransition rt1 = diceRoll();
        if (TurnController.isCurrentPlayerIsComputer())
        {
            rt1.setOnFinished(e ->
            {
                if (MoveController.isMovable())
                    PlayerController.computerMove();
                else
                    TurnController.endTurn();
            });
        }else
            rt1.setOnFinished(e -> PlayerController.HumanMove());
        rt1.play();
    }

    public static void animateChessMoving()
    {
        CellView currentCellView;

        currentCellView = PlayerController.getSelectedCellView1();
        System.out.println("indexOfCurrentCellView = " + CellController.getCellViewList().indexOf(currentCellView));
        chessIsMoving = true;
        if (currentCellView != PlayerController.getSelectedCellView2())
        {
            CellView nextCellView;

            if (PlayerController.isASpawnMove())
                nextCellView = PlayerController.getSelectedCellView2();
            else
                nextCellView = CellController.getNextCellView(currentCellView);

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
            startMovingAnimation(transition, nextCellView);
            MediaController.playMoveSound();
        }
        else if (PlayerController.getKickedChessView() != null)
        {
            animateChessGettingKicked();
        }
        else
        {
            PlayerController.getSelectedChessView().setLayoutX(PlayerController.getSelectedCellView2().getLayoutX() + 4);
            PlayerController.getSelectedChessView().setLayoutY(PlayerController.getSelectedCellView2().getLayoutY() + 4);
            PlayerController.getSelectedChessView().setTranslateX(0);
            PlayerController.getSelectedChessView().setTranslateY(0);
            PlayerController.setSelectedChess(null);
            chessIsMoving = false;
            if (!MoveController.isMovable())
                TurnController.endTurn();
            else if (TurnController.isCurrentPlayerIsComputer())
                PlayerController.computerMove();
        }
    }

    private static void animateChessGettingKicked()
    {
        CellView emptyNest = PlayerController.getEmptyNestCellView();
        ImageView kickedChessView = PlayerController.getKickedChessView();
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
        transition.setOnFinished(e->
        {
            PlayerController.getKickedChessView().setLayoutX(PlayerController.getEmptyNestCellView().getLayoutX() + 4);
            PlayerController.getKickedChessView().setLayoutY(PlayerController.getEmptyNestCellView().getLayoutY() + 4);
            PlayerController.getKickedChessView().setTranslateX(0);
            PlayerController.getKickedChessView().setTranslateY(0);
            PlayerController.setKickedChessView(null);
            animateChessMoving();
        });
        transition.play();
    }

    private static void startMovingAnimation(PathTransition transition, CellView destinationCellView)
    {
        transition.setOnFinished(e ->
        {
            PlayerController.getSelectedChessView().setLayoutX(destinationCellView.getLayoutX() + 4);
            PlayerController.getSelectedChessView().setLayoutY(destinationCellView.getLayoutY() + 4);
            PlayerController.getSelectedChessView().setTranslateX(0);
            PlayerController.getSelectedChessView().setTranslateY(0);
            PlayerController.setSelectedCellView1(destinationCellView);
            animateChessMoving();
        });
        transition.play();
    }
}
