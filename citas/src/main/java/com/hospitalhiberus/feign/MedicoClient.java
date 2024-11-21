package com.hospitalhiberus.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "MEDICOS")
public interface MedicoClient {

    @GetMapping("/medicos/{dni}")
    ResponseEntity<Map<String, Object>> obtenerMedico(@PathVariable("dni") String dni);
}