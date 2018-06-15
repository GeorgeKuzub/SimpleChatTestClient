package server;

import common.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatHistory implements Serializable {
    private List<Message> history;

    public ChatHistory() {
        this.history = new ArrayList<>(ConfigServer.HISTORY_LENGTH);
    }

    public void addMessage(Message message){
        if (this.history.size() > ConfigServer.HISTORY_LENGTH){
            this.history.remove(0);
        }

        this.history.add(message);
    }

    public List<Message> getHistory(){
        return this.history;
    }
}
