package de.cogame.messageservice.initializr;

import de.cogame.messageservice.model.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageInitializr {

    public static List<Message> getMessages(){

        Message message=new Message("1",
                "1",
                "Name1",
                "1",
                "A great place" , LocalDateTime.of(2021,05,10,13,8));
        Message message2=new Message("2",
                "2",
                "Name2",
                "1",
                "A great place" , LocalDateTime.of(2021,05,10,13,8));
        Message message3=new Message("3",
                "3",
                "Name3",
                "1",
                "A great place" , LocalDateTime.of(2021,05,10,13,8));

        List<Message> messages=new ArrayList<>();
        messages.add(message);
        messages.add(message2);
        messages.add(message3);
        return messages;
    }
}
