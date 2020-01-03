
package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainMenuController
{
    int current_lang = 0, current_color = 0;
    String[][] lang = new String[][]{{"vi", "en"}, {"VN", "US"}};
    TextField[] plName;
    Pane[] panes;
    Rectangle[] rectangles;
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
        choice.setVisible(true);
        back.setVisible(true);
        done.setVisible(true);
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
        int num = (choice.getValue()).charAt(0) - '0';
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

    @FXML
    void getName() {
        for (TextField i : plName) System.out.println(i);
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
}
