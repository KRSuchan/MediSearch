package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReadData extends Thread
{
    private Socket cliSocket;
    private Object readBuf;
    private ObjectInputStream ois;
    public ReadData(Socket cliSocket, ObjectInputStream ois){
        this.cliSocket = cliSocket;
        this.ois = ois;
    }

    public void run(){
        try{
            System.out.println("access to readBuf in ReadData");
            readBuf = ois.readObject();
            System.out.println("accessed to readBuf in ReadData");
            System.out.println(readBuf);
        }catch(IOException e){
            System.out.println("IOException in ReadData run()");
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException in ReadData run()");
            System.err.println(e);
        }
    }
    public Object getData(){
        return readBuf;
    }
}