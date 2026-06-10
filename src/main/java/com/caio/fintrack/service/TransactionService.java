package com.caio.fintrack.service;

import com.caio.fintrack.dto.request.ExpenseTransactionRequestDTO;
import com.caio.fintrack.dto.request.TransactionRequestDTO;
import com.caio.fintrack.dto.response.CategoryResponseDTO;
import com.caio.fintrack.dto.response.TransactionResponseDTO;
import com.caio.fintrack.exception.IdNotFoundException;
import com.caio.fintrack.exception.InvalidTransactionException;
import com.caio.fintrack.model.Category;
import com.caio.fintrack.model.Transaction;
import com.caio.fintrack.model.enums.TransactionType;
import com.caio.fintrack.repository.CategoryRepository;
import com.caio.fintrack.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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
            throw new InvalidTransactionException("O campo 'valor' deve ser maior que zero.");
        }

        if(request.getData() == null || request.getData().isAfter(LocalDate.now())) {
            throw new InvalidTransactionException("O campo data não pode ser uma data futura.");
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
                .orElseThrow(IdNotFoundException::new);

        if(request.getValor() == null || request.getValor() <= 0) {
            throw new InvalidTransactionException("O campo 'valor' deve ser maior que zero.");
        }

        if(request.getData() == null || request.getData().isAfter(LocalDate.now())) {
            throw new InvalidTransactionException("O campo data não pode ser uma data futura.");
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
                                toCategoryResponseDTO(transaction.getCategory()),
                                transaction.getCreatedDate()
                        )
                ).collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> findTypeTransactions(TransactionType type) {
        return transactionRepository.findByType(type)
                .stream()
                .map(
                        transaction -> new TransactionResponseDTO(
                                transaction.getId(),
                                transaction.getType(),
                                transaction.getAmount(),
                                transaction.getDate(),
                                transaction.getDescription(),
                                toCategoryResponseDTO(transaction.getCategory()),
                                transaction.getCreatedDate()
                        )
                )
                .toList();
    }

    public void deleteTransactions(UUID id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(IdNotFoundException::new);

        transactionRepository.delete(transaction);
    }

    private CategoryResponseDTO toCategoryResponseDTO(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryResponseDTO(
                category.getId(),
                category.getName()
        );
    }
}
