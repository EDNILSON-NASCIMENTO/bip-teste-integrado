package com.example.backend.web;

import com.example.backend.domain.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import com.example.backend.service.BeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.sound.sampled.AudioFileFormat;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/beneficios")
@CrossOrigin(origins = "http://localhost:4200")

public class BeneficioController {

    @Autowired
    private BeneficioRepository repository;

    @Autowired
    private BeneficioService service;

    @GetMapping
    public List<BeneficioDTO> listAll(){
        return repository.findAll().stream()
                .map(service::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficioDTO> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(beneficio -> ResponseEntity.ok(service.toDTO(beneficio)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BeneficioDTO> create(@RequestBody BeneficioDTO dto) {
        Beneficio saveBeneficio = service.saveFromDTO(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saveBeneficio.getId()).toUri();
        return ResponseEntity.created(location).body(service.toDTO(saveBeneficio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeneficioDTO> update(@PathVariable Long id, @RequestBody BeneficioDTO dto) {
        if (!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        Beneficio updateBeneficio = service.saveFromDTO(dto);
        return  ResponseEntity.ok(service.toDTO(updateBeneficio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transferir")
    public ResponseEntity<String> transfer(@RequestBody TransferenciaRequest request) {
        service.transferir(request.getFromId(), request.getToId(), request.getAmount());
        return ResponseEntity.ok("Transferência realizada com sucesso.");
    }







}
