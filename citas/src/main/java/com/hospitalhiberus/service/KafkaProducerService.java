package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.HistorialMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void enviarHistorialMedico(String topic, HistorialMedico historialMedico){
        kafkaTemplate.send(topic, historialMedico.getIdPaciente(), historialMedico);
        System.out.println("Historial médico enviado al topic " + topic + ": \n" + historialMedico);
    }

}
