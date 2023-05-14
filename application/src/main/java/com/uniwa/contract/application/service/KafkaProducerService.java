package com.uniwa.contract.application.service;

import com.uniwa.contract.application.enumeration.KafkaTopicEnum;
import com.uniwa.contract.application.model.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void produceMessage(KafkaMessage message, KafkaTopicEnum topic) {

        ListenableFuture<SendResult<String, KafkaMessage>> future = kafkaTemplate.send(topic.getTopic(), message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message: {} to topic: {} - cause: {}", message, topic.getTopic(),
                        ex.getMessage());
            }
            @Override
            public void onSuccess(SendResult<String, KafkaMessage> result) {
                log.info("Message: {} is sent to topic: {} with offset: {}", message, topic.getTopic(),
                                result.getRecordMetadata().offset());
            }
        });
    }
}
