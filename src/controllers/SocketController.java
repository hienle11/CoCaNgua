package controllers;

import models.Client;
import models.Server;

public class SocketController
{
    private static Server server;
    private static Client client;


    public static void initialize()
    {
        try
        {
            if (GamePlayController.playerIsHost)
            {
                server = new Server();
                server.sendMessage("hello client");
            }else
            {
                client = new Client();
                client.sendMessage("hello server");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

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
