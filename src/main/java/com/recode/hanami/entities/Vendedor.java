package com.recode.hanami.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "vendedores")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vendedor {

    @Id
    @Column(name = "vendedor_id")
    private String id;

    public Vendedor() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}