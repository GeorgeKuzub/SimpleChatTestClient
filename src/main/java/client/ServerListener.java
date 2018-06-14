package client;

import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;

public class ServerListener implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerListener(ObjectInputStream in, ObjectOutputStream out) {
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = in.readObject();
                Message msg = null;
                if (obj instanceof Collection) {
                    List<Message> chatHistory = (List<Message>) obj;
                    if (!chatHistory.isEmpty()) {
                        for (Message current : chatHistory) {
                            System.out.printf("[%s], %s : %s \n", current.getLogin(), current.getTime(), current.getMsg());
                        }
                    }
                } else if (obj.getClass().getName().endsWith("Message") ) {
                    msg = (Message) obj;
                } else {
                    throw new ClassNotFoundException("The appropriate deserialization class wasn't found.");
                }

                if (msg != null) {
                    System.out.println("[" + msg.getTime() + " ] " + msg.getLogin() + " : " + msg.getMsg());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}