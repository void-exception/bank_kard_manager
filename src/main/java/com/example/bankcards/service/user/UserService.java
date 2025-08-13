package com.example.bankcards.service.user;


import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для работы с пользователями.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final LimitRepository limitRepository;
    private final StatementRepository statementRepository;
    private final TransactionRepository transactionRepository;

    public UserService(UserRepository userRepository, CardRepository cardRepository, LimitRepository limitRepository, StatementRepository statementRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.limitRepository = limitRepository;
        this.statementRepository = statementRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Удаляет пользователя по email и все связанные с ним данные:
     * - карты,
     * - лимиты,
     * - заявки,
     * - транзакции.
     */
    @Transactional
    public ResponseEntity<String> deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email", email));
        List<Card> cards = cardRepository.findAllByUser(user);
        for (Card card : cards) {
            limitRepository.deleteByCard(card);
            statementRepository.deleteByCard(card);
        }
        transactionRepository.deleteByUser(user);
        userRepository.delete(user);
        return ResponseEntity.ok("Пользователь и все его данные удалены");
    }
}
