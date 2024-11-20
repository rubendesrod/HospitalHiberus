package com.hospitalhiberus.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "PACIENTES")
public interface PacienteClient {

    @GetMapping("/pacientes/{dni}")
    ResponseEntity<Map<String, Object>> obtenerPaciente(@PathVariable("dni") String dni);

}
