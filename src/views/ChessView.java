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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChessView extends ImageView
{
    public ChessView(String imagePath)
    {
        setImage(new Image(imagePath));
        setFitHeight(30);
        setFitWidth(25);
        toFront();
    }

    public void moveTo(CellView selectedCellView)
    {
        selectedCellView.getChildren().add(this);
    }

}
