package com.caio.fintrack.controller;

import com.caio.fintrack.dto.response.FinancialSummaryResponseDTO;
import com.caio.fintrack.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/resumo")
public class SummaryController {

    private final TransactionService transactionService;

    public SummaryController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<FinancialSummaryResponseDTO> financialSummary(
            @RequestParam("dataInicio") LocalDate initialDate,
            @RequestParam("dataFim") LocalDate finalDate
    ) {

        return ResponseEntity.ok(
                transactionService.financialSummary(initialDate, finalDate)
        );
    }
}
