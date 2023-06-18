package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SendData extends Thread
{
    private Socket cliSocket;

    public SendData(Socket cliSocket){
        this.cliSocket = cliSocket;
    }

    public void run(String writeBuf){
        OutputStream os;
        BufferedWriter bw;
        ObjectOutputStream oos;

        try{
            os = cliSocket.getOutputStream();
            oos = new ObjectOutputStream(os);
            oos.writeObject(writeBuf);
            oos.flush();
            System.out.println("in SendData : "+writeBuf);
        }catch(IOException e){
            System.err.println(e);
        }
    }
}