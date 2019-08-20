/**
 * @create 2019-08-12 16:33
 * @desc message processor
 **/
package com.magictown.freeworld.peterpan.kongfu.consumerk.message;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Broadcaster {

    @Autowired
    private AmqpTemplate amqpTemplate;

    //@Value("${vrabbitmq.config.exchange}")
    private String exchange;
    //@Value("${vrabbitmq.config.queue.error.routing.key}")
    private String routingKey;


    public void send(LogMessage logMessage) {
        amqpTemplate.convertAndSend(exchange,routingKey,logMessage);
    }

    public void sendWithManyString(String message) {
        amqpTemplate.convertAndSend("log.topic","order.log.debug","order.log.debug......" + message);
        amqpTemplate.convertAndSend("log.topic","order.log.info","order.info.debug......" + message);
        amqpTemplate.convertAndSend("log.topic","order.log.warning","order.log.warning......" + message);
        amqpTemplate.convertAndSend("log.topic","order.log.error","order.log.error......" + message);

    }

    public void sendWithFanout(String message) {
        amqpTemplate.convertAndSend("fanout","",message);
    }
}

