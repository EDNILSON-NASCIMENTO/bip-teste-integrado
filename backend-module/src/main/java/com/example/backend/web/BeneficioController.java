package com.example.backend.web;

import com.example.backend.domain.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import com.example.backend.service.BeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Garanta que esta importação exista
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public List<BeneficioDTO> listAll() {
        return repository.findAll().stream()
                .map(service::toDTO)
                .collect(Collectors.toList());
    }

    // A CORREÇÃO ESTÁ NO MÉTODO ABAIXO
    @GetMapping("/{id}")
    public ResponseEntity<BeneficioDTO> findById(@PathVariable("id") Long id) { // Adicionado @PathVariable aqui também por boa prática
        return repository.findById(id)
                .map(beneficio -> ResponseEntity.ok(service.toDTO(beneficio)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BeneficioDTO> create(@RequestBody BeneficioDTO dto) {
        Beneficio savedBeneficio = service.saveFromDTO(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBeneficio.getId()).toUri();
        return ResponseEntity.created(location).body(service.toDTO(savedBeneficio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeneficioDTO> update(@PathVariable("id") Long id, @RequestBody BeneficioDTO dto) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        Beneficio updatedBeneficio = service.saveFromDTO(dto);
        return ResponseEntity.ok(service.toDTO(updatedBeneficio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) { // Adicionado @PathVariable aqui também por boa prática
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