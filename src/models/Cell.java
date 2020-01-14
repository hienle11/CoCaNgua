/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 23/12/2019
  By: Le Quang Hien (s3695516)
  Last modified: 14/01/2020
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package models;

public class Cell
{

    private Chess chess;
    private String id;

    public Cell(String id)
    {
        this.id = id;
    }

    public Chess getChess()
    {
        return chess;
    }

    public void setChess(Chess chess)
    {
        this.chess = chess;
    }

    public String getId()
    {
        return id;
    }

}
