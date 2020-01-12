/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 1/01/2020
  By: Doan Luong Hoang (sxxxxxxx)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Doan Luong Hoang (sxxxxxxx)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainMenuController
{
    boolean multi = false;
    int current_lang = 0, current_color = 0, num = 0;
    String[][] lang = new String[][]{{"vi", "en"}, {"VN", "US"}};
    TextField[] plName;
    Pane[] panes;
    Rectangle[] rectangles;
    @FXML
    private TextField hostID;

    @FXML
    private CheckBox multiplayer;

    @FXML
    private Pane pane1, pane2, pane3, pane4;

    @FXML
    private Rectangle color1, color2, color3, color4;

    @FXML
    private ImageView menu;

    @FXML
    private Button start, rules, language, back, done, exit;

    @FXML
    private ComboBox<String> choice;

    @FXML
    private TextField plName1,plName2,plName3,plName4;

    @FXML
    void initialize() {
        choice.getItems().addAll("2","3","4");
        plName = new TextField[]{plName1,plName2,plName3,plName4};
        panes = new Pane[]{pane1, pane2, pane3, pane4};
        rectangles = new Rectangle[]{color1, color2, color3, color4};
    }

    @FXML
    void start() {
        start.setVisible(false);
        rules.setVisible(false);
        language.setVisible(false);
        exit.setVisible(false);
        multiplayer.setDisable(true);
        multiplayer.setLayoutX(100);
        choice.setVisible(true);
        back.setVisible(true);
        done.setVisible(true);
        multi = multiplayer.isSelected();
    }

    @FXML
    void displayRules() {

    }

    @FXML
    void changeLanguage() {
        Locale.setDefault(new Locale(lang[0][current_lang], lang[1][current_lang]));
        ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle");
        start.setText(bundle.getString("start"));
        rules.setText(bundle.getString("rules"));
        language.setText(bundle.getString("language"));
        exit.setText(bundle.getString("exit"));
        choice.setPromptText(bundle.getString("choose"));
        menu.setImage(new Image(bundle.getString("menu")));
        done.setText(bundle.getString("done"));
        back.setText(bundle.getString("back"));
        multiplayer.setText(bundle.getString("multi"));
        for (int i = 0; i < 4; i++) plName[i].setText(bundle.getString("player") + (i + 1));
        current_lang = 1 - current_lang;
    }

    @FXML
    void exit() {
        Platform.exit();
    }

    @FXML
    void getPlayerNum() {
        done.setDisable(false);
        boolean visible = true;
        num = (choice.getValue()).charAt(0) - '0';
        for (int i = 0; i < 4; i++) {
            if (i == num) visible = false;
            panes[i].setVisible(visible);
        }
    }

    @FXML
    void back(javafx.scene.input.MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/MainMenu.fxml"))));
    }

    void setNameInGamePlay(Node node, CharSequence[] str, boolean[] compPlayer) throws IOException {
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/GamePlay.fxml"));
        Scene scene = new Scene(loader.load());
        GamePlayController controller = loader.getController();
        controller.setData(str, compPlayer);
        stage.setScene(scene);
    }

    @FXML
    void getName(MouseEvent event) throws IOException {
        ArrayList<Color> color = new ArrayList<>(List.of(Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW));
        boolean[] compPlayer = new boolean[4];
        CharSequence[] str = new CharSequence[4];
        for (int i = 0; i < 4; i++) {
            int k = color.indexOf(rectangles[i].getFill());
            str[k] = plName[i].getCharacters();
            compPlayer[k] = (i > num - 1);
        }
//        for (CharSequence i : str) System.out.println(i);
//        for (boolean i : compPlayer) System.out.println(i);
//        if (multi) {
//            for (int i = 0; i < 4; i++) {
//                panes[i].setVisible(false);
//            }
//            choice.setDisable(true);
//            hostID.setVisible(true);
//            done.setVisible(false);
//        }
        setNameInGamePlay((Node)event.getSource(), str, compPlayer);
    }

    @FXML
    void changeColor(javafx.scene.input.MouseEvent event) {
        Rectangle temp = (Rectangle)event.getSource();
        current_color++;
        if (current_color > 3) current_color = 0;
        if (rectangles[current_color] == temp)
            if (current_color == 3) current_color = 0;
            else current_color ++;
        Color temp_color = (Color) temp.getFill();
        temp.setFill(rectangles[current_color].getFill());
        rectangles[current_color].setFill(temp_color);
    }

    @FXML
    void getHostID(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println(((TextField) event.getSource()).getCharacters());
        }
    }
}
