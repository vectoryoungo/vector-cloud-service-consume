/**
 * @create 2019-08-20 15:14
 * @desc implement of StreamService
 **/
package com.magictown.freeworld.peterpan.kongfu.consumerk.service;

import com.magictown.freeworld.peterpan.kongfu.consumerk.stream.VectorCloudStreamBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class StreamServiceImpl implements StreamService{

    @Autowired
    private VectorCloudStreamBroadcaster vectorCloudStreamBroadcaster;

    @Override
    public void sendMsg(String message) {
        Message<String> messages = MessageBuilder.withPayload(message).build();
        vectorCloudStreamBroadcaster.send().send(messages);
    }
}



