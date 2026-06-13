package com.caio.fintrack.controller;

import com.caio.fintrack.dto.request.ExpenseTransactionRequestDTO;
import com.caio.fintrack.dto.request.TransactionPatchDTO;
import com.caio.fintrack.dto.request.TransactionRequestDTO;
import com.caio.fintrack.dto.response.TransactionResponseDTO;
import com.caio.fintrack.model.enums.TransactionType;
import com.caio.fintrack.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/v1/transacoes")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/entradas")
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.saveIncomeTransaction(request));
    }

    @PostMapping("/saidas")
    public ResponseEntity<TransactionResponseDTO> saveTransaction(@RequestBody ExpenseTransactionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.saveExpenseTransaction(request));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        List<TransactionResponseDTO> response = transactionService.findAllTransactions();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/entradas")
    public ResponseEntity<List<TransactionResponseDTO>> getIncomeTransactions() {
        List<TransactionResponseDTO> response = transactionService.findTypeTransactions(TransactionType.ENTRADA);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/saidas")
    public ResponseEntity<List<TransactionResponseDTO>> getExpenseTransactions() {
        List<TransactionResponseDTO> response = transactionService.findTypeTransactions(TransactionType.SAIDA);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(
            @PathVariable UUID id,
            @RequestBody TransactionPatchDTO request
    ) {
        TransactionResponseDTO response = transactionService.updateTransaction(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransactions(id);
        return ResponseEntity.noContent().build();
    }
}
