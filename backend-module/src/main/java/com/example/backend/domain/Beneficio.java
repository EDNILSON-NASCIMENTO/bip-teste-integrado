package com.example.backend.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Entity
@Table(name="BENEFICIO")

public class Beneficio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Version
    private Long version;

    @OneToMany(mappedBy = "beneficio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @MapKey(name = "metakey")
    private Map<String, BeneficioMeta> metadata = new HashMap<>();

    public Optional<String> getMetaValue(String key){
        return Optional.ofNullable(metadata.get(key)).map(BeneficioMeta::getMetavalue);
    }

    public void setMetadata(String key, String value) {
        BeneficioMeta meta= metadata.get(key);
        if (meta != null){
            meta.setMetavalue(value);
        }
        else {
            meta = new BeneficioMeta(this, key, value);
            metadata.put(key, meta);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Map<String, BeneficioMeta> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, BeneficioMeta> metadata) {
        this.metadata = metadata;
    }





}
