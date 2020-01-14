package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static final int PORT = 3191;
    private ServerSocket serverSocket;
    private Socket socket;

    public Server() throws IOException
    {
        serverSocket = new ServerSocket(PORT);
        socket = serverSocket.accept();
    }

    public Socket getSocket()
    {
        return socket;
    }

    public String receiveMessage() throws Exception
    {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        String message =(String) inputStream.readObject();
        System.out.println("receive = " + message);
        return message;
    }

    public void sendMessage(String message) throws Exception
    {
        System.out.println("send = " + message);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(message);
    }
}
