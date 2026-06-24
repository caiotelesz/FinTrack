package com.caio.fintrack.repository;

import com.caio.fintrack.model.Transaction;
import com.caio.fintrack.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByDateBetween(LocalDate initialDate, LocalDate finalDate);

    List<Transaction> findByTypeAndDateBetween(
            TransactionType type,
            LocalDate initialDate,
            LocalDate finalDate
    );

    List<Transaction> findByCategoryId(UUID categoryId);

    List<Transaction> findByCategoryIdAndDateBetween(
            UUID categoryId,
            LocalDate initialDate,
            LocalDate finalDate
    );

    List<Transaction> findByTypeAndCategoryId(
            TransactionType type,
            UUID categoryId
    );

    List<Transaction> findByTypeAndCategoryIdAndDateBetween(
            TransactionType type,
            UUID categoryId,
            LocalDate initialDate,
            LocalDate finalDate
    );

    boolean existsByCategoryId(UUID categoryId);

    @Query("""
            SELECT COALESCE(SUM(t.amount), 0.0)
            FROM Transaction t
            WHERE t.type = :type
            AND t.date BETWEEN :initialDate AND :finalDate
            """)
    Double sumAmountByTypeAndDateBetween(
            TransactionType type,
            LocalDate initialDate,
            LocalDate finalDate
    );
}