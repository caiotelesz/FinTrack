package com.caio.fintrack.service;

import com.caio.fintrack.dto.request.TransactionRequestDTO;
import com.caio.fintrack.dto.response.TransactionResponseDTO;
import com.caio.fintrack.model.Transaction;
import com.caio.fintrack.model.enums.TransactionType;
import com.caio.fintrack.repository.CategoryRepository;
import com.caio.fintrack.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponseDTO saveTransaction(TransactionRequestDTO request) {

        Transaction transaction = new Transaction();

        if(request.getValor() == null || request.getValor() < 0) {
            throw new RuntimeException("O campo 'valor' deve ser maior que zero.");
        }

        transaction.setAmount(request.getValor());
        transaction.setDate(request.getData());
        transaction.setDescription(request.getDescricao());

        transaction.setType(TransactionType.ENTRADA);
        transaction.setCategory(null);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponseDTO(
                savedTransaction.getId(),
                savedTransaction.getType(),
                savedTransaction.getAmount(),
                savedTransaction.getDate(),
                savedTransaction.getDescription(),
                null,
                savedTransaction.getCreatedDate()
        );
    }

    public List<TransactionResponseDTO> findAllTransactions() {

        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(
                        transaction -> new TransactionResponseDTO(
                                transaction.getId(),
                                transaction.getType(),
                                transaction.getAmount(),
                                transaction.getDate(),
                                transaction.getDescription(),
                                null,
                                transaction.getCreatedDate()
                        )
                ).collect(Collectors.toList());
    }
}
