package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Statement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatementRepository extends CrudRepository<Statement, Long> {
    void deleteByCard(Card card);

    List<Statement> findByCardAndStatus(Card card, String status);
}
