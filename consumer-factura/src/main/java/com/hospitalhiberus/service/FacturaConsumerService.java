package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.FacturaKey;
import com.hospitalhiberus.avro.FacturaValue;
import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.repository.FacturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class FacturaConsumerService {

    @Autowired
    private FacturaRepository repository;

    @KafkaListener(topics = "facturas")
    public void consume(ConsumerRecord<FacturaKey, FacturaValue> record) {

        log.info("Topic: facturas");
        log.info("Key: " + record.key());
        log.info("Valor: " + record.value());

        try{
        Factura f = mapearFactura(record.value());
        repository.save(f);
        log.info("La factura ha sido guardada en la DB correctamente");
        }catch (Exception e){
            log.error( e + "\nNo se ha podido guardar la nueva factura, " + record.value());
        }
    }


    Factura mapearFactura(FacturaValue avro){
      Factura factura = new Factura();
      factura.setTotalPagar(avro.getTotalPagar());
      factura.setFechaEmision(LocalDate.parse(avro.getFechaEmision()));
      factura.setIdMedico(avro.getIdMedico());
      factura.setEstado(ESTADOS.pendiente);
      return factura;
    };

}
