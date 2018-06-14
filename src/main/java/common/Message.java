package common;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;

public class Message implements Serializable {
    private String login;
    private String msg;
    private String[] users;
    private LocalTime time;

    public Message(String login, String msg) {
        this.login = login;
        this.msg = msg;
        this.time = LocalTime.now();
    }

    // Setter для online users
    public void setOnlineUsers(String[] users) {
        this.users = users;
    }

    public String getLogin() {
        return login;
    }

    public String getMsg() {
        return msg;
    }

    public String[] getUsers() {
        return users;
    }

    public String getTime() {
        String result = time.toString();

        // Отрезаем milliseconds из результата
        return result.substring(0, result.length() - 4);
    }

    @Override
    public String toString() {
        return "Message{" +
                "login='" + login + '\'' +
                ", msg='" + msg + '\'' +
                ", users=" + Arrays.toString(users) +
                ", time=" + time +
                '}';
    }
}
