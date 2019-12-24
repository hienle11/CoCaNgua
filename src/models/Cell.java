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

package models;

public class Cell
{
    public enum Id
    {

    }
    private Chess chess;
    private Id id;

    public Chess getChess()
    {
        return chess;
    }

    public void setChess(Chess chess)
    {
        this.chess = chess;
    }

    public Id getId()
    {
        return id;
    }

    public void setId(Id id)
    {
        this.id = id;
    }
}
