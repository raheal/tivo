package com.tivo.download.messaging.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("eventProfile")
public class KafkaMessageHandler implements MessageHandler{

	@Autowired
	private KafkaProducer<Long, String> kafkaProducer;
	
	@Override
	public void send(final String topic, final Long key, final String value) {
		final ProducerRecord<Long, String> producerRecord = new ProducerRecord<>(topic, key, value);
		kafkaProducer.send(producerRecord);
	}

	@Override
	public void send(final String topic, final String value) {
		final ProducerRecord<Long, String> producerRecord = new ProducerRecord<>(topic, value);
		kafkaProducer.send(producerRecord);
	}
	
}
