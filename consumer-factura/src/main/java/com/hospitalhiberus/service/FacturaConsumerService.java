package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.FacturaKey;
import com.hospitalhiberus.avro.FacturaValue;
import com.hospitalhiberus.repository.FacturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FacturaConsumerService {

    private FacturaRepository repository;

    @KafkaListener(topics = "facturas")
    public void consume(ConsumerRecord<FacturaKey, FacturaValue> record) {

        log.info("Key: " + record.value());
        log.info("Valor: " + record.value());
        log.info("IdMedico: " + record.value().getIdMedico());
    }

}
