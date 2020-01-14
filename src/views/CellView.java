/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 05/01/2020
  By: Le Quang Hien (s3695516)
  Last modified: 14/01/2020
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package views;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class CellView extends StackPane
{
    Shape shape = null;
    Paint color = null;

    public void showCellSelection()
    {
        if (shape == null)
        {
            shape = ((Shape) (this.getChildren().get(0)));
            if (shape instanceof Rectangle)
                color = shape.getFill();
            else
                color = Color.WHITE;
        }
        shape.setFill(Color.VIOLET);
    }

    public void hideCellSelection()
    {
        if (shape != null)
            shape.setFill(color);
    }
}
