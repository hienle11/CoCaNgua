/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 24/12/2019
  By: Le Quang Hien (s3695516)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package views;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class ChessView extends ImageView
{

    public ChessView(String imagePath)
    {
        setImage(new Image(imagePath));
        setFitHeight(20);
        setFitWidth(20);
    }

    public void moveTo(CellView selectedCellView)
    {
        CellView transporter = new CellView();
        CellView initialCellView = (CellView)this.getParent();
        transporter.setLayoutX(initialCellView.getLayoutX());
        transporter.setLayoutY(initialCellView.getLayoutY());
        transporter.getChildren().add(this);
        Line line = new Line();
        line.setStartX(initialCellView.getLayoutX());
        line.setStartY(initialCellView.getLayoutY());
        line.setEndX(selectedCellView.getLayoutX());
        line.setEndY(selectedCellView.getLayoutY());
        PathTransition transition = new PathTransition();
        transition.setNode(transporter);
        transition.setDuration(Duration.seconds(3));
        transition.setPath(line);
        transition.setCycleCount(1);
        transition.play();
        selectedCellView.getChildren().add(this);
    }
}
