package com.example.backend.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BENEFICIO_META")
public class BeneficioMeta implements Serializable {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "BENEFICIO_ID", nullable = false)
    private Beneficio beneficio;

    @Column(nullable = false)
    private String metakey;

    private String metavalue;

    public BeneficioMeta() {

    }

    public BeneficioMeta(Beneficio beneficio, String metakey, String metavalue){
        this.beneficio = beneficio;
        this.metakey = metakey;
        this.metavalue = metavalue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Beneficio getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }

    public String getMetakey() {
        return metakey;
    }

    public void setMetakey(String metakey) {
        this.metakey = metakey;
    }

    public String getMetaValue() {
        return metavalue;
    }

    public void setMetaValue(String metavalue) {
        this.metavalue = metavalue;
    }
}
