/**
 * @create 2019-08-20 14:59
 * @desc send stream message
 **/
package com.magictown.freeworld.peterpan.kongfu.consumerk.stream;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface VectorCloudStreamBroadcaster {
    @Output("vector-stream-input")
    SubscribableChannel send();
}

