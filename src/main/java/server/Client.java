package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectOutputStream os;
    private ObjectInputStream is;

    public Client(Socket socket){
        this.socket = socket;
    }

    public Client(Socket socket , ObjectOutputStream os, ObjectInputStream is){
        this.socket = socket;
        this.os = os;
        this.is = is;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public ObjectOutputStream getThisObjectOutputStream() {
        return this.os;
    }

    public ObjectInputStream getThisObjectInputStream() {
        return this.is;
    }

    public void setThisObjectOutputStream(ObjectOutputStream oos) {
        this.os = oos;
    }

    public void setThisObjectInputStream(ObjectInputStream ois) {
        this.is = ois;
    }
}
