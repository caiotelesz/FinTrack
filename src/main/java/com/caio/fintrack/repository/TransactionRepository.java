package com.caio.fintrack.repository;

import com.caio.fintrack.model.Transaction;
import com.caio.fintrack.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByType(TransactionType type);
    boolean existsByCategoryId(UUID categoryId);
}
