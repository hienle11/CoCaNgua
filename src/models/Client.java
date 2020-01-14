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

    public Socket getSocket()
    {
        return socket;
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

