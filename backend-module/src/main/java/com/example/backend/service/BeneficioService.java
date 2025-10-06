package com.example.backend.service;

import com.example.backend.domain.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import com.example.backend.web.BeneficioDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BeneficioService {

    @Autowired
    private BeneficioRepository repository;

    @Transactional
    public void transferir(Long fromId, Long toId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        Beneficio from = repository.findById(fromId)
                .orElseThrow(() -> new EntityNotFoundException("Benefício de origem não encontrado: " + fromId));
        Beneficio to = repository.findById(toId)
                .orElseThrow(() -> new EntityNotFoundException("Benefício de destino não encontrado: " + toId));

        BigDecimal fromValor = new BigDecimal(from.getMetaValue("VALOR")
                .orElseThrow(() -> new IllegalStateException("Benefício de origem " + fromId + " sem valor definido.")));

        if (fromValor.compareTo(amount) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência.");
        }

        BigDecimal toValor = new BigDecimal(to.getMetaValue("VALOR")
                .orElseThrow(() -> new IllegalStateException("Benefício de destino " + toId + " sem valor definido.")));

        from.setMetaValue("VALOR", fromValor.subtract(amount).toPlainString());
        to.setMetaValue("VALOR", toValor.add(amount).toPlainString());

        repository.save(from);
        repository.save(to);
    }

    public BeneficioDTO toDTO(Beneficio beneficio) {
        BeneficioDTO dto = new BeneficioDTO();
        dto.setId(beneficio.getId());
        dto.setNome(beneficio.getNome());
        dto.setMetadata(beneficio.getMetadata().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getMetavalue())));
        return dto;
    }

    @Transactional
    public Beneficio saveFromDTO(BeneficioDTO dto) {
        Beneficio beneficio = repository.findById(dto.getId() != null ? dto.getId() : -1L)
                .orElse(new Beneficio());

        beneficio.setNome(dto.getNome());

        if (dto.getMetadata() != null) {
            dto.getMetadata().forEach(beneficio::setMetaValue);
        }

        return repository.save(beneficio);
    }
}