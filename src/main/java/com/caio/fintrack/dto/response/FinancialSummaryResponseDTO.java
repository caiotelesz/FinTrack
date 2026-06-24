package com.caio.fintrack.dto.response;

public class FinancialSummaryResponseDTO {

    private PeriodDTO periodo;
    private Double totalEntrada;
    private Double totalSaida;
    private Double saldo;

    public FinancialSummaryResponseDTO(PeriodDTO periodo, Double totalEntrada, Double totalSaida, Double saldo) {
        this.periodo = periodo;
        this.totalEntrada = totalEntrada;
        this.totalSaida = totalSaida;
        this.saldo = saldo;
    }

    public PeriodDTO getPeriodo() {
        return periodo;
    }

    public Double getTotalEntrada() {
        return totalEntrada;
    }

    public Double getTotalSaida() {
        return totalSaida;
    }

    public Double getSaldo() {
        return saldo;
    }
}
