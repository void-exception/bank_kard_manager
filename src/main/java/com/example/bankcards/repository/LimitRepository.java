package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.Optional;

public interface LimitRepository extends JpaRepository<Limit, Long> {
    Optional<Limit> findByCardAndEndLimitAfter(Card card, LocalDateTime now);

    void deleteByCard(Card card);
}
