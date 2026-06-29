package com.caio.fintrack.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class TransactionRequestDTO {

    @NotNull(message = "O valor é obrigatório.")
    @Positive(message = "O valor deve ser maior que zero.")
    private Double valor;

    @NotNull(message = "A data é obrigatória.")
    @PastOrPresent(message = "A data não pode ser futura.")
    private LocalDate data;

    @NotBlank(message = "A descrição é obrigatório.")
    @Size(max = 80, message = "A descrição deve ter no máximo 80 caracteres.")
    private String descricao;

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
}
