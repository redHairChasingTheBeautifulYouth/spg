package com.spg.web.webSocket.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: trevor
 * @Date: 2019\3\30 0030 00:13
 * @Description:
 */
public class Transcript {

    private List<String> messages = new ArrayList<>();

    private List<String> userNames = new ArrayList<>();

    private int maxLines;

    public Transcript(int maxLines) {
        this.maxLines = maxLines;
    }

    public String getLastUserName(){
        return userNames.get(userNames.size()-1);
    }

    public String getLastMessage(){
        return messages.get(messages.size()-1);
    }

    public void addEntry(String userName ,String message){
        if (userNames.size()>maxLines) {
            userNames.remove(0);
            messages.remove(0);
        }
        userNames.add(userName);
        messages.add(message);
    }
}
