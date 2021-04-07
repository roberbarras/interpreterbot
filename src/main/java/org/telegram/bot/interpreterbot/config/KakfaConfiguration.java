package org.telegram.bot.interpreterbot.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.telegram.bot.interpreterbot.model.MessageReceived;
import org.telegram.bot.interpreterbot.model.MessageToSend;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KakfaConfiguration {

    public ConsumerFactory<String, MessageReceived> consumerFactory() {
        JsonDeserializer<MessageReceived> deserializer = new JsonDeserializer<>(MessageReceived.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "vy6rnd8d-consumers");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "rocket-01.srvs.cloudkafka.com:9094,rocket-02.srvs.cloudkafka.com:9094,rocket-03.srvs.cloudkafka.com:9094");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        config.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-256");
        config.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"vy6rnd8d\" password=\"jp0Q87pMW1Qiv9jyGNqrQt42X5uqRe9e\";");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageReceived> customConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageReceived> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true);
        return factory;
    }

    public ProducerFactory<String, MessageToSend> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "rocket-01.srvs.cloudkafka.com:9094,rocket-02.srvs.cloudkafka.com:9094,rocket-03.srvs.cloudkafka.com:9094");
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        config.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-256");
        config.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"vy6rnd8d\" password=\"jp0Q87pMW1Qiv9jyGNqrQt42X5uqRe9e\";");

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, MessageToSend> customProducerFactory() {
        return new KafkaTemplate<>(producerFactory());
    }

}
