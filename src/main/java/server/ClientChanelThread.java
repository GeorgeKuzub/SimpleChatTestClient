package server;

import common.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static server.Server.getChatHistory;
import static server.Server.getUserList;

public class ClientChanelThread extends Thread {
    private static AtomicInteger attemptsSocketExceptions = new AtomicInteger(0);
    private Socket socket;
    private Message msg;
    private String login;

    public ClientChanelThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            //Создаем потоки ввода-вывода для работы с сокетом
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                //Читаем Message из потока
                msg = (Message) inputStream.readObject();

                //Читаем логин отправителя
                login = msg.getLogin();

                if (msg.getMsg().equalsIgnoreCase(ConfigServer.CONNECTION_MSG)) {
                    //отправляем новичку историю чата
                    outputStream.writeObject(getChatHistory().getHistory());

                    getUserList().addUser(login, socket, outputStream, inputStream); // Добавляем к списку пользователей
                    msg = new Message("Server-Bot", "The user " + login + " has been connected");
                    //Для ответа, указываем список доступных пользователей
                    msg.setOnlineUsers(getUserList().getUsers());
                } else { //Если это не регистрационное сообщение, то добавляем его к истории чата
                    System.out.println("[" + msg.getLogin() + "]: " + msg.getMsg());
                    getChatHistory().addMessage(msg);
                }
                //Передаем всем сообщение пользователя
                broadcast(getUserList().getClientsList(), msg);
            }
        } catch (EOFException e) {
            System.out.println(login + " disconnected!");
            getUserList().deleteUser(login);
            broadcast(getUserList().getClientsList(),
                    new Message("Server-Bot", "The user " + login + " has been disconnect"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(List<Client> clientList, Message message) {
        try {
            for (Client client : clientList) {
                client.getThisObjectOutputStream().writeObject(message);
            }
        } catch (SocketException e) {
            System.out.println("in broadcast: " + login + " disconnected!");
            getUserList().deleteUser(login);
            if (attemptsSocketExceptions.getAndIncrement() < 3) {
                broadcast(getUserList().getClientsList(), new Message("Server-Bot",
                        "The user " + login + " has been disconnected"));
            } else {
                e.printStackTrace();
                this.interrupt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}