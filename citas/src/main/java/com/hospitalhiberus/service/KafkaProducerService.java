package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.FacturaKey;
import com.hospitalhiberus.avro.FacturaValue;
import com.hospitalhiberus.avro.HistorialMedicoKey;
import com.hospitalhiberus.avro.HistorialMedicoValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<HistorialMedicoKey, HistorialMedicoValue> kafkaTemplateHisotorial;

    @Autowired
    private KafkaTemplate<FacturaKey, FacturaValue> kafkaTemplateFactura;

    public void enviarHistorialMedico(String topic, HistorialMedicoValue historialMedicoValue){
        HistorialMedicoKey historialMedicoKey = HistorialMedicoKey.newBuilder()
                        .setIdPaciente(historialMedicoValue.getIdPaciente())
                                .build();

        kafkaTemplateHisotorial.send(topic, historialMedicoKey, historialMedicoValue);
        log.info("Historial médico enviado al topic " + topic + ": \n" + historialMedicoValue);
    }

    public void enviarFactura(String topic, FacturaValue facturaValue) {
        FacturaKey facturaKey = FacturaKey.newBuilder()
                .setIdMedico(facturaValue.getIdMedico())
                .build();

        kafkaTemplateFactura.send(topic, facturaKey, facturaValue);
        log.info("Factura enviada al topic " + topic + ": \n" + facturaValue);
    }

}
