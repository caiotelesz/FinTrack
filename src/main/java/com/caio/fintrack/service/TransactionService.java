package com.caio.fintrack.service;

import com.caio.fintrack.dto.request.ExpenseTransactionRequestDTO;
import com.caio.fintrack.dto.request.TransactionRequestDTO;
import com.caio.fintrack.dto.response.CategoryResponseDTO;
import com.caio.fintrack.dto.response.TransactionResponseDTO;
import com.caio.fintrack.exception.CategoryIdNotFoundException;
import com.caio.fintrack.exception.TransactionNotValidException;
import com.caio.fintrack.model.Category;
import com.caio.fintrack.model.Transaction;
import com.caio.fintrack.model.enums.TransactionType;
import com.caio.fintrack.repository.CategoryRepository;
import com.caio.fintrack.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public TransactionResponseDTO saveIncomeTransaction(TransactionRequestDTO request) {

        Transaction transaction = new Transaction();

        if(request.getValor() == null || request.getValor() <= 0) {
            throw new TransactionNotValidException();
        }

        transaction.setType(TransactionType.ENTRADA);
        transaction.setAmount(request.getValor());
        transaction.setDate(request.getData());
        transaction.setDescription(request.getDescricao());
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

    public TransactionResponseDTO saveExpenseTransaction(ExpenseTransactionRequestDTO request) {
        Transaction transaction = new Transaction();

        Category category = categoryRepository.findById(request.getIdCategoria())
                .orElseThrow(CategoryIdNotFoundException::new);

        if(request.getValor() == null || request.getValor() <= 0) {
            throw new TransactionNotValidException();
        }

        transaction.setType(TransactionType.SAIDA);
        transaction.setAmount(request.getValor());
        transaction.setDate(request.getData());
        transaction.setDescription(request.getDescricao());
        transaction.setCategory(category);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponseDTO(
                savedTransaction.getId(),
                savedTransaction.getType(),
                savedTransaction.getAmount(),
                savedTransaction.getDate(),
                savedTransaction.getDescription(),
                new CategoryResponseDTO(
                        savedTransaction.getCategory().getId(),
                        savedTransaction.getCategory().getName()
                ),
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
