package com.caio.fintrack.dto.response;

import java.util.UUID;

public class CategoryResponseDTO {

    private UUID idCategoria;
    private String nome;

    public CategoryResponseDTO(UUID idCategoria, String nome) {
        this.idCategoria = idCategoria;
        this.nome = nome;
    }

    public UUID getIdCategoria() {
        return idCategoria;
    }

    public String getNome() {
        return nome;
    }
}
