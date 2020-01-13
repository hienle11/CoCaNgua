package controllers;

import models.*;

public class SocketController
{
    private static Server server;
    private static Client client;


    public static void initialize()
    {
        try
        {
            server = new Server();
            System.out.println("server is ready");
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("server is not ready");
        }
    }

    public static String getMessage()
    {
        try
        {
            return server.receiveMessage();
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
            server.sendMessage(message);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
