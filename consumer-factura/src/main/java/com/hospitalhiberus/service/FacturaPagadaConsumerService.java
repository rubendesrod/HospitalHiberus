package com.hospitalhiberus.service;


import com.hospitalhiberus.avro.FacturaPagadaKey;
import com.hospitalhiberus.avro.FacturaPagadaValue;
import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.repository.FacturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
public class FacturaPagadaConsumerService {

    @Autowired
    private FacturaRepository repository;

    @KafkaListener(topics = "facturasPagadas")
    public void consume(ConsumerRecord<FacturaPagadaKey, FacturaPagadaValue> record) {
        try {
            log.info("Topic: facturasPagadas");
            log.info("Key: " + record.key());
            log.info("Value: " + record.value());

            // Logica para pagar la factura
            Optional<Factura> f = repository.findById(Integer.valueOf(record.key().getIdFactura()));
            if (f.isEmpty()) {
                log.warn("Factura con ID " + record.key().getIdFactura() + " no encontrada.");
                return;
            }

            Factura factura = f.get();

            factura.setEstado(ESTADOS.pagado);
            factura.setFechaPago(LocalDate.now());

            repository.save(factura);

        } catch (Exception e) {
            log.error("Error procesando el mensaje del tópico 'facturasPagadas'", e);
        }
    }

}
