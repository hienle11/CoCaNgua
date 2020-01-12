/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 05/01/2020
  By: Le Quang Hien (s3695516)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
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

    public void showPossibleCells()
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
       /* test = true;
        CellView cellView = this;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    double defaultStrokeWidth = ((Circle)(cellView.getChildren().get(0))).getStrokeWidth();
                    for (int i = 0; i < 10; i ++)
                    {
                        ((Circle) (cellView.getChildren().get(0))).setStrokeWidth(20);
                        Thread.sleep(500);
                        ((Circle) (cellView.getChildren().get(0))).setStrokeWidth(defaultStrokeWidth);
                        Thread.sleep(500);
                    }
                }catch(InterruptedException e){}
            }
        }).start();*/
    }

    public void hidePossibleCells()
    {
        shape.setFill(color);
    }
}
