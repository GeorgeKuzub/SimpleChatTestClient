package client;

import common.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient {
    // это IP-адрес сервера, где исполняется наша серверная программа
    private final static String address = "127.0.0.1";

    private final static int serverPort = 7070; // порт

    private static String userName = "";

    public static void main(String[] args) {
        System.out.println("Вас приветствует клиент чата!\n");
        System.out.println("Введите свой ник и нажмите Enter: ");

        // Создаем поток для чтения с клавиатуры
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Ждем пока пользователь введет свой ник и нажмет кнопку Enter
            userName = keyboard.readLine();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket socket = null;
        try  {
            socket = new Socket(InetAddress.getByName(address), serverPort);
            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом
            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());


            objOut.writeObject(new Message(userName, "User joined to the chat(Auto-message)"));

            Thread thread = new Thread(new ServerListener(objIn, objOut));
            thread.start();

            // Создаем поток для чтения с клавиатуры
            String message = null;
            System.out.println("Наберите сообщение и нажмите Enter: \n");

            while (true) { // Бесконечный цикл
                message = keyboard.readLine(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                Message currentMsg = new Message(userName, message);
                objOut.writeObject(currentMsg); // отсылаем введенную строку текста серверу.
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
