package com.caio.fintrack.service;

import com.caio.fintrack.dto.request.ExpenseTransactionRequestDTO;
import com.caio.fintrack.dto.request.TransactionPatchDTO;
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

    /**
     * Registra uma transação de entrada
     *
     * Representa valores recebidos pelo usuário, como salário ou freelancer.
     * Por regra de negócio, entradas não possuem categoria.
     *
     * @param request dados da transação de entrada
     * @return transação criada com seus dados de resposta
     * @throws InvalidTransactionException quando o valor ou data forem inválidos
     */
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

    /**
     * Registra uma transação de saída.
     *
     * Representa gastos do usuário e deve estar obrigatoriamente vinculadas a uma categoria existente.
     *
     * @param request dados da transação de saída
     * @return transação criada com categoria vinculada
     * @throws IdNotFoundException quando a categoria informada não existir
     * @throws InvalidTransactionException quando valor ou data forem inválidos
     */
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

    /**
     * Consulta o extrato financeiro aplicando filtros opcionais
     *
     * Filtros aceitos:
     * - Tipo da transação
     * - Categoria
     * - Data do periodo: inicial e final
     *
     * Caso nenhum filtro seja informado, retorna todas as transações cadastrada
     *
     * @param type tipo da transação: ENTRADA ou SAIDA
     * @param categoryId identificador da categoria
     * @param initialDate data inicial do período
     * @param finalDate data final do período
     * @return lista de transações encontradas
     */
    public List<TransactionResponseDTO> findTransactions(
            TransactionType type,
            UUID categoryId,
            LocalDate initialDate,
            LocalDate finalDate
    ) {
        List<Transaction> transactions;

        if (type != null && categoryId != null && initialDate != null && finalDate != null) {
            transactions = transactionRepository.findByTypeAndCategoryIdAndDateBetween(
                    type, categoryId, initialDate, finalDate
            );
        } else if (categoryId != null && initialDate != null && finalDate != null) {
            transactions = transactionRepository.findByCategoryIdAndDateBetween(
                    categoryId, initialDate, finalDate
            );
        } else if (type != null && initialDate != null && finalDate != null) {
            transactions = transactionRepository.findByTypeAndDateBetween(
                    type, initialDate, finalDate
            );
        } else if (type != null && categoryId != null) {
            transactions = transactionRepository.findByTypeAndCategoryId(type, categoryId);
        } else if (initialDate != null && finalDate != null) {
            transactions = transactionRepository.findByDateBetween(initialDate, finalDate);
        } else if (categoryId != null) {
            transactions = transactionRepository.findByCategoryId(categoryId);
        } else if (type != null) {
            transactions = transactionRepository.findByType(type);
        } else {
            transactions = transactionRepository.findAll();
        }

        return transactions.stream()
                .map(transaction -> new TransactionResponseDTO(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getDate(),
                        transaction.getDescription(),
                        toCategoryResponseDTO(transaction.getCategory()),
                        transaction.getCreatedDate()
                ))
                .collect(Collectors.toList());
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

    /**
     * Atualiza parcialmente uma transação existente.
     *
     * Apenas os campos informados no request são alterados.
     * As regras de validação da criação são as mesmas utiizadas na atualização
     *
     * Regras:
     * - Valor: quando informado, deve ser maior que zero
     * - Data: quando informado, não pode ser futura
     * - Entrada não pode receber categoria
     *
     * @param id identificador de transação
     * @param request dados parciais para atualização
     * @return transação atualizada
     */
    public TransactionResponseDTO updateTransaction(UUID id, TransactionPatchDTO request) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(IdNotFoundException::new);

        if (request.getValor() != null) {
            if (request.getValor() <= 0) {
                throw new InvalidTransactionException("O campo 'valor' deve ser maior que zero.");
            }

            transaction.setAmount(request.getValor());
        }

        if (request.getData() != null) {
            if (request.getData().isAfter(LocalDate.now())) {
                throw new InvalidTransactionException("O campo data não pode ser uma data futura.");
            }

            transaction.setDate(request.getData());
        }

        if (request.getDescricao() != null) {
            transaction.setDescription(request.getDescricao());
        }

        if (request.getIdCategoria() != null) {
            if (transaction.getType() == TransactionType.ENTRADA) {
                throw new InvalidTransactionException("Transações de entrada não possuem categoria.");
            }

            Category category = categoryRepository.findById(request.getIdCategoria())
                    .orElseThrow(IdNotFoundException::new);

            transaction.setCategory(category);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponseDTO(
                savedTransaction.getId(),
                savedTransaction.getType(),
                savedTransaction.getAmount(),
                savedTransaction.getDate(),
                savedTransaction.getDescription(),
                toCategoryResponseDTO(savedTransaction.getCategory()),
                savedTransaction.getCreatedDate()
        );
    }

    /**
     * Remove uma trasação existente.
     *
     * @param id identificador da transação
     * @throws IdNotFoundException quando a transação não existir
     */
    public void deleteTransactions(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(IdNotFoundException::new);

        transactionRepository.delete(transaction);
    }

    /**
     * Converte uma entidade Category para CategoryResponseDTO
     *
     * Retorna null quando a transação não possui categoria,
     * como as transações de entrada.
     */
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
