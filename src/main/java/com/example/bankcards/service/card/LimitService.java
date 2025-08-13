package com.example.bankcards.service.card;

import com.example.bankcards.dto.limit.LimitResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.Limit;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.LimitRepository;
import com.example.bankcards.service.user.UserContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Сервис для управления лимитами по картам.
 */
@Service
public class LimitService {
    private final CardRepository cardRepository;
    private final UserContextService userContextService;
    private final LimitRepository limitRepository;

    public LimitService(CardRepository cardRepository, UserContextService userContextService, LimitRepository limitRepository) {
        this.cardRepository = cardRepository;
        this.userContextService = userContextService;
        this.limitRepository = limitRepository;
    }

    public ResponseEntity<String> debitingFunds(Long idCard, double sum) {
        Card card = cardRepository.findById(idCard).orElseThrow(() -> new ResourceNotFoundException("Карта", idCard));
        if (!card.getUser().getId().equals(userContextService.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Карта не пренадлежит пользователю");
        }
        if (card.getStatus() != CardStatus.ACTIVE) {
            return ResponseEntity.badRequest().body("Карта недоступна для проведения транзакций");
        }
        if (card.getBalance() < sum) {
            return ResponseEntity.badRequest().body("На карте недостаточно средств");
        }
        Limit limit = limitRepository.findByCardAndEndLimitAfter(card, LocalDateTime.now()).orElse(null);
        if (limit != null) {
            if (limit.getSum() < sum) {
                return ResponseEntity.badRequest().body("Сумма списания превышает лимит");
            }
            limit.setSum(limit.getSum() - sum);
            limitRepository.save(limit);
        }
        card.setBalance(card.getBalance() - sum);
        cardRepository.save(card);
        return ResponseEntity.ok("Деньги списаны");
    }

    public ResponseEntity<String> createLimit(LimitResponse limitResponce) {
        Card card = cardRepository.findById(limitResponce.cardId()).orElseThrow(() -> new ResourceNotFoundException("Карта", limitResponce.cardId()));
        if (limitRepository.findByCardAndEndLimitAfter(card, LocalDateTime.now()).isPresent()) {
            return ResponseEntity.badRequest().body("Лимит на карту уже есть");
        }
        Limit limit = new Limit(card, limitResponce.sum(), limitResponce.endLimit());
        limitRepository.save(limit);
        return ResponseEntity.ok("Лимит добавлен созданна");
    }
}
