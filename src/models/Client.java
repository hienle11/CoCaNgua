/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 13/01/2019
  By: Le Quang Hien (s3695516)
  Last modified: 14/01/2020
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client
{
    Socket socket;

    public Client() throws IOException
    {
        socket = new Socket("localhost", Server.PORT);
    }

    public String receiveMessage() throws Exception
    {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        String message = (String) inputStream.readObject();
        return message;
    }

    public void sendMessage(String message) throws Exception
    {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(message);
    }

}

