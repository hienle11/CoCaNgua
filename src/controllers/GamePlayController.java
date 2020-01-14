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

package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import views.CellView;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePlayController implements Initializable
{
    @FXML
         public Pane root, gameOverPane;
    @FXML
    private Button rollDiceBt, playAgBt;
    @FXML
    private ImageView dice0, dice1,
            blueChess0, blueChess1, blueChess2, blueChess3,
            redChess0, redChess1, redChess2, redChess3,
            greenChess0, greenChess1, greenChess2, greenChess3,
            yellowChess0, yellowChess1, yellowChess2, yellowChess3;
    @FXML
    private CellView blueSpawn, blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10,
            redSpawn, red1, red2, red3, red4, red5, red6, red7, red8, red9, red10,
            yellowSpawn, yellow1, yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, yellow8, yellow9, yellow10,
            greenSpawn, green1, green2, green3, green4, green5, green6, green7, green8, green9, green10,
            greenHome0, greenHome1, greenHome2, greenHome3, greenHome4, greenHome5, greenHome6,
            redHome0, redHome1, redHome2, redHome3, redHome4, redHome5, redHome6,
            yellowHome0, yellowHome1, yellowHome2, yellowHome3, yellowHome4, yellowHome5, yellowHome6,
            blueHome0, blueHome1, blueHome2, blueHome3, blueHome4, blueHome5, blueHome6,
            blueNest1, blueNest2, blueNest3, blueNest4,
            redNest1, redNest2, redNest3, redNest4,
            yellowNest1, yellowNest2, yellowNest3, yellowNest4,
            greenNest1, greenNest2, greenNest3, greenNest4;
    @FXML
    Label turn, name0, name1, name2, name3, blueScore, redScore, yellowScore, greenScore;

    static CharSequence[] str;
    static boolean[] comPlayer;
    static boolean[] chosenPlayer;
    static boolean playOnline = false;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      INITIALIZE METHOD                                        //
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL location, ResourceBundle resource)
    {
        root.toFront();
        name0.setText(String.valueOf(str[0]));
        name1.setText(String.valueOf(str[1]));
        name2.setText(String.valueOf(str[2]));
        name3.setText(String.valueOf(str[3]));
        PlayerController.initialize(str, comPlayer, chosenPlayer, blueScore, redScore, yellowScore, greenScore);
        PlayerController.getChessViewList().clear();
        PlayerController.getChessViewList().addAll(blueChess0, blueChess1, blueChess2, blueChess3,
                redChess0, redChess1, redChess2, redChess3,
                greenChess0, greenChess1, greenChess2, greenChess3,
                yellowChess0, yellowChess1, yellowChess2, yellowChess3);
        CellController.getCellViewList().clear();
        CellController.getCellList().clear();
        CellController.getCellViewList().addAll(
                blueHome0, blueSpawn, blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10,
                redHome0, redSpawn, red1, red2, red3, red4, red5, red6, red7, red8, red9, red10,
                greenHome0, greenSpawn, green1, green2, green3, green4, green5, green6, green7, green8, green9, green10,
                yellowHome0, yellowSpawn, yellow1, yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, yellow8, yellow9, yellow10,
                blueHome0, blueHome1, blueHome2, blueHome3, blueHome4, blueHome5, blueHome6,
                redHome0, redHome1, redHome2, redHome3, redHome4, redHome5, redHome6,
                greenHome0, greenHome1, greenHome2, greenHome3, greenHome4, greenHome5, greenHome6,
                yellowHome0, yellowHome1, yellowHome2, yellowHome3, yellowHome4, yellowHome5, yellowHome6,
                blueNest1, blueNest2, blueNest3, blueNest4,
                redNest1, redNest2, redNest3, redNest4,
                greenNest1, greenNest2, greenNest3, greenNest4,
                yellowNest1, yellowNest2, yellowNest3, yellowNest4);
        CellController.initialize();

        AnimationController.initialize(dice0, dice1);
        ButtonController.initialize(rollDiceBt);
        //SocketController.initialize();
        TurnController.initialize(turn, gameOverPane, rollDiceBt);
        manualTestCases();
    }

    public void normalCellOnMouseClicked(MouseEvent event)
    {
        if (PlayerController.getSelectedCellView1() !=  event.getSource() && PlayerController.isAllowToClick())
        {
            if (PlayerController.getSelectedCellView1() != null
                    && !MoveController.getPossibleMoves().containsKey(PlayerController.getSelectedCell1()))
            {
                PlayerController.getSelectedCellView1().hideCellSelection();
            }
            if (PlayerController.getSelectedChessView() == null || PlayerController.getSelectedChess() == null)
            {
                PlayerController.selectChess(event);
            } else
            {
                if(PlayerController.moveChess(event))
                {

                    AnimationController.animateChessMoving();
                }
            }
        }
    }

    public void rollDiceBtHandler()
    {
        ButtonController.rollDiceBtHandler();
    }

    public void playAgBtHandler(ActionEvent event){ButtonController.playAgBtHandler(event);}

    public void quitBtHandler(){ButtonController.quitGameBtHandler();}

    public void manualTestCases()
    {
        PlayerController.updateMove("blueNest1", "blueHome6", false);
        PlayerController.updateMove("blueNest2", "blueHome5", false);
        PlayerController.updateMove("blueNest3", "blueHome4", false);
        PlayerController.updateMove("blueNest4", "blueHome2", false);

        rollDiceBt.setOnAction(e->
        {
            TurnController.setDiceValue(new int[]{3,3,6});
            AnimationController.animateDiceRolling();
        });
    }
}

