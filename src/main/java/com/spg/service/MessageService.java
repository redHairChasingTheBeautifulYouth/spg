package com.spg.service;

import com.spg.domin.Message;

import java.util.List;

/**
 * @author trevor
 * @date 04/01/19 13:33
 */
public interface MessageService {

    void generateMessage(Message message);

    List<Message> findMeeagePage(Long roomId ,Integer pageSize ,Integer pageNo);
}
