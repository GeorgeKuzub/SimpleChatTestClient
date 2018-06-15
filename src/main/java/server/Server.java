package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static UsersList list = new UsersList();
    private static ChatHistory chatHistory = new ChatHistory();

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(7070)) {

            while (true) {
                // ожидание клиента
                Socket socket = server.accept();
                System.out.println(socket.getInetAddress().getHostName() + " connected");
                // создание отдельного потока для обмена данными c соединившимся клиентом
                ClientChanelThread clientChanelThread = new ClientChanelThread(socket);
                // Стартуем поток
                clientChanelThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized static UsersList getUserList() {
        return list;
    }

    public synchronized static ChatHistory getChatHistory() {
        return chatHistory;
    }
}
