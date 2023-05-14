package com.uniwa.contract.application.config;

import com.uniwa.contract.application.enumeration.KafkaTopicEnum;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic applicationSubmittedTopic() {
        return TopicBuilder.name(KafkaTopicEnum.SUBMITTED.getTopic())
                .build();
    }

    @Bean
    public NewTopic applicationProcessedTopic() {
        return TopicBuilder.name(KafkaTopicEnum.PROCESSED.getTopic())
                .build();
    }

    @Bean
    public NewTopic applicationCompletedTopic() {
        return TopicBuilder.name(KafkaTopicEnum.COMPLETED.getTopic())
                .build();
    }

    @Bean
    public NewTopic applicationCanceledTopic() {
        return TopicBuilder.name(KafkaTopicEnum.CANCELED.getTopic())
                .build();
    }
}
