/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 13/01/2020
  By: Le Quang Hien (s3695516)
  Last modified: 14/01/2020
  By: Le Quang Hien (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package controllers;

import models.Client;
import models.Server;

public class SocketController
{
    private static Server server;
    private static Client client;

    // this method is to establish the connection between server and client
    public static void initialize()
    {
        try
        {
            if (GamePlayController.playerIsHost)
            {
                server = new Server();
                server.sendMessage("hello client");
                System.out.println(server.receiveMessage());
            }else
            {
                client = new Client();
                client.sendMessage("hello server");
                System.out.println(client.receiveMessage());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // this method is created to receive a message to client or server
    public static String getMessage()
    {
        try
        {
            if (GamePlayController.playerIsHost)
            {
                return server.receiveMessage();
            }else
            {
                return client.receiveMessage();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "error";
    }

    // this method is created to send a message to client or server
    public static void sendMessage(String message)
    {
        try
        {
            if (GamePlayController.playerIsHost)
            {
                server.sendMessage(message);
            }else
            {
                client.sendMessage(message);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
