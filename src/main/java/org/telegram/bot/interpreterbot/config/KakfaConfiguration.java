package org.telegram.bot.interpreterbot.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.telegram.bot.interpreterbot.model.kafka.*;
import org.telegram.bot.interpreterbot.util.Constants;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KakfaConfiguration {

    @Value("${kafka.consumer}")
    private String consumer;

    @Value("${kafka.server}")
    private String server;

    @Value("${kafka.jaas}")
    private String jaasConfig;

    private <T> ConsumerFactory<String, T> consumerFactory(Class<T> messageType) {
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(messageType);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumer);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, Constants.KAFKA_OFFSET_CONFIG);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, Constants.KAFKA_PROTOCOL);
        config.put(SaslConfigs.SASL_MECHANISM, Constants.KAFKA_MECHANISM);
        config.put(SaslConfigs.SASL_JAAS_CONFIG, jaasConfig);

        return new DefaultKafkaConsumerFactory(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageReceived> messageReceivedConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageReceived> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(MessageReceived.class));
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AvailableSizesResponse> availableSizesConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AvailableSizesResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(AvailableSizesResponse.class));
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GarmentAdvice> garmentConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GarmentAdvice> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(GarmentAdvice.class));
        factory.setBatchListener(true);
        return factory;
    }

    private <T> ProducerFactory<String, T> producerFactory(Class<T> messageType) {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, Constants.KAFKA_PROTOCOL);
        config.put(SaslConfigs.SASL_MECHANISM, Constants.KAFKA_MECHANISM);
        config.put(SaslConfigs.SASL_JAAS_CONFIG, jaasConfig);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, MessageToSend> messageToSendProducerFactory() {
        return new KafkaTemplate<>(producerFactory(MessageToSend.class));
    }

    @Bean
    public KafkaTemplate<String, AvailableSizesRequest> availableSizesRequestProducerFactory() {
        return new KafkaTemplate<>(producerFactory(AvailableSizesRequest.class));
    }

}
