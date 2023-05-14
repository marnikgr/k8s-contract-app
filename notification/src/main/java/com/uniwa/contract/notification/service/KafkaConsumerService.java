package com.uniwa.contract.notification.service;

import com.uniwa.contract.notification.model.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumerService {

    private NotificationService notificationService;

    @KafkaListener(topicPattern = "application.*")
    public void consume(ConsumerRecord<String, KafkaMessage> consumerRecord) {

        log.info("Message with body: {} consumed at topic: {}", consumerRecord.value(), consumerRecord.topic());
        notificationService.notifyApplicant(consumerRecord.topic(), consumerRecord.value());
    }
}
