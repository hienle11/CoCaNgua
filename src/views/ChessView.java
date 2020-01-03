package views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChessView extends ImageView
{
    public enum Color
    {
        BLUE,
        RED,
        GREEN,
        YELLOW
    }
    private Color color;
    public ChessView(String imagePath)
    {
        setImage(new Image(imagePath));
        setFitHeight(20);
        setFitWidth(20);

    }
}
