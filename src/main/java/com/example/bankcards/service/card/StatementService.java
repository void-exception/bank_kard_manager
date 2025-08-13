package com.example.bankcards.service.card;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.Statement;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.StatementRepository;
import com.example.bankcards.service.user.UserContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с заявками на блокировку карт.
 */
@Service
public class StatementService {
    private final CardRepository cardRepository;
    private final StatementRepository statementRepository;
    private final UserContextService userContextService;

    public StatementService(CardRepository cardRepository, StatementRepository statementRepository, UserContextService userContextService) {
        this.cardRepository = cardRepository;
        this.statementRepository = statementRepository;
        this.userContextService = userContextService;
    }

    public ResponseEntity<String> requestBlocking(Long idCard) {
        Card card = cardRepository.findById(idCard).orElseThrow(() -> new ResourceNotFoundException("Карта", idCard));
        if (!card.getUser().getId().equals(userContextService.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Карта не пренадлежит пользователю");
        }
        if (card.getStatus() == CardStatus.BLOCKED) {
            return ResponseEntity.badRequest().body("Карта уже заблокированна");
        }
        if (!statementRepository.findByCardAndStatus(card, "PENDING").isEmpty()) {
            return ResponseEntity.badRequest().body("Заявка уже созданна");
        }

        Statement statement = new Statement(card);
        statementRepository.save(statement);
        return ResponseEntity.ok("Заявка созданна");
    }
}
