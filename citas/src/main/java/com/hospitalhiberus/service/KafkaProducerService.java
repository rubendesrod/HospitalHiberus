package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.HistorialMedicoKey;
import com.hospitalhiberus.avro.HistorialMedicoValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<HistorialMedicoKey, HistorialMedicoValue> kafkaTemplate;

    public void enviarHistorialMedico(String topic, HistorialMedicoValue historialMedicoValue){
        HistorialMedicoKey historialMedicoKey = HistorialMedicoKey.newBuilder()
                        .setIdPaciente(historialMedicoValue.getIdPaciente())
                                .build();

        kafkaTemplate.send(topic, historialMedicoKey, historialMedicoValue);
        System.out.println("Historial médico enviado al topic " + topic + ": \n" + historialMedicoValue);
    }

}
