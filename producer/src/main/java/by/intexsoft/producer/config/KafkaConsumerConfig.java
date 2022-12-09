package by.intexsoft.producer.config;

import by.intexsoft.producer.model.MessageDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.concurrency}")
    private Integer concurrency;

    @Bean
    @Primary
    ConcurrentKafkaListenerContainerFactory<String, MessageDTO> mainListenerContainerFactory(ConsumerFactory<String, MessageDTO> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, MessageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();

//        factory.setCommonErrorHandler(new DefaultErrorHandler(consumerRecordRecoverer, new FixedBackOff(0, 0L)));
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);

        return factory;
    }

//    @Bean
//    ConcurrentKafkaListenerContainerFactory<String, MessageDTO> retryListenerContainerFactory(ConsumerFactory<String, MessageDTO> consumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<String, MessageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
//
//        factory.setCommonErrorHandler(new DefaultErrorHandler(consumerRecordRecoverer, new FixedBackOff(0, 0)));
//        factory.setConsumerFactory(consumerFactory);
//
//        return factory;
//    }

    @Bean
    public ConsumerFactory<String, MessageDTO> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps());
    }


    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }
}
