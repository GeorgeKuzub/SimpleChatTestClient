package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersList {
    private Map<String, Client> onlineUsers = new HashMap<>();

    public void addUser(String login, Socket socket, ObjectOutputStream os, ObjectInputStream is) {
        System.out.println( login +" connected" );

        if (!onlineUsers.containsKey(login)) {
            onlineUsers.put(login , new Client(socket, os, is));
        } else {
            int i = 1;
            while(onlineUsers.containsKey(login)) {
                login = login + i;
                i++;
            }
            onlineUsers.put(login , new Client(socket, os, is));
        }
    }

    public void deleteUser(String login) {
        onlineUsers.remove(login);
    }

    public String[] getUsers() {
        return onlineUsers.keySet().toArray(new String[0]);
    }

    public List<Client> getClientsList() {
        List<Client> clientsList = new ArrayList<Client>(onlineUsers.entrySet().size());

        String s = "";
        for(Map.Entry<String, Client> m : onlineUsers.entrySet()){
            clientsList.add(m.getValue());
            System.out.println(m.getKey());
            s = s + m.getKey();
        }

        return clientsList;
    }
}
