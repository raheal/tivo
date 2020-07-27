package com.tivo.download.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.apache.kafka.common.serialization.LongSerializer;

@Configuration
@Profile("eventProfile")
public class KafkaConfiguration {

	@Value("${kafka.bootstrap.server}")
	private String bootstrapServers;
	
	@Value("${kafka.client.id}")
	private String clientId;
	
	private Properties kafkaProperties() {
		final Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}
	
	@Bean
	public KafkaProducer<Long, String> kafkaProducer() {
		return new KafkaProducer<>(kafkaProperties());
	}
	
}
