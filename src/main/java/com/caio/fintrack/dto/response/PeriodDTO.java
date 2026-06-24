package com.caio.fintrack.dto.response;

import java.time.LocalDate;

public class PeriodDTO {

    private LocalDate initialDate;
    private LocalDate finalDate;

    public PeriodDTO(LocalDate initialDate, LocalDate finalDate) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }
}
