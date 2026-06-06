package com.caio.fintrack.dto.response;

import com.caio.fintrack.model.Category;
import com.caio.fintrack.model.enums.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionResponseDTO {

    private UUID idTransacao;
    private TransactionType tipo;
    private Double valor;
    private LocalDate data;
    private String descricao;
    private CategoryResponseDTO categoria;
    private LocalDateTime dataCriacao;

    public TransactionResponseDTO(UUID idTransacao, TransactionType tipo, Double valor, LocalDate data, String descricao, CategoryResponseDTO categoria, LocalDateTime dataCriacao) {
        this.idTransacao = idTransacao;
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dataCriacao = dataCriacao;
    }

    public UUID getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(UUID idTransacao) {
        this.idTransacao = idTransacao;
    }

    public TransactionType getTipo() {
        return tipo;
    }

    public void setTipo(TransactionType tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CategoryResponseDTO getcategoria() {
        return categoria;
    }

    public void setcategoria(CategoryResponseDTO categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
