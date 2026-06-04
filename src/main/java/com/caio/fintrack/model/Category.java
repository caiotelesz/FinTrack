package com.caio.fintrack.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "categorias")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, name = "idCategoria")
    private UUID id;

    @Column(nullable = false, name = "nome")
    private String name;

    public Category() {}

    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
