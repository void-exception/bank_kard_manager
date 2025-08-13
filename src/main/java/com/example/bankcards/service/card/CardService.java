package com.example.bankcards.service.card;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import com.example.bankcards.dto.card.CardDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.StatementRepository;
import com.example.bankcards.repository.TransactionRepository;
import com.example.bankcards.service.user.UserContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * сервис для управления банковскими картами
 */
@Service
public class CardService {
    private final UserContextService userContextService;
    private final EncodeService encodeService;
    private final CardRepository cardRepository;
    private final StatementRepository statementRepository;
    private final TransactionRepository transactionRepository;

    public CardService(UserContextService userContextService, EncodeService encodeService, CardRepository cardRepository, StatementRepository statementRepository, TransactionRepository transactionRepository) {
        this.userContextService = userContextService;
        this.encodeService = encodeService;
        this.cardRepository = cardRepository;
        this.statementRepository = statementRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<?> currentCard(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Карта", id));
        User user = userContextService.getUser();
        if (!user.getRole().equals("ADMIN") && !card.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Карта не пренадлежит пользователю");
        }
        CardDTO cardDTO = new CardDTO(card.getId(), encodeService.decryption(card.getCardNumber()), card.getUser().getName(), card.getUser().getEmail(), card.getEndDate(), card.getStatus(), card.getBalance());
        return ResponseEntity.ok(cardDTO);
    }

    public ResponseEntity<?> createdCard(String number, YearMonth end, double balance) {
        try {
            String encryptNumber = encodeService.encryption(number);
            if (cardRepository.findByCardNumber(encryptNumber).isPresent()) {
                return ResponseEntity.badRequest().body("Такая карта уже созданна");
            }
            CardStatus status = YearMonth.now().isBefore(end) ? CardStatus.ACTIVE : CardStatus.EXPIRED;
            Card card = new Card(encryptNumber, userContextService.getUser(), end, status, balance);
            cardRepository.save(card);
            return ResponseEntity.ok("Новая карта создана");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Неверные входные данные: " + e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла внутренняя ошибка при создании карты" + e.getMessage());
        }
    }

    public List<CardDTO> getCards() {
        User user = userContextService.getUser();
        List<Card> cards = cardRepository.findAllByUser(user);
        return cards.stream()
                .map(card -> new CardDTO(
                        card.getId(),
                        encodeService.decryption(card.getCardNumber()),
                        card.getUser().getName(),
                        card.getUser().getEmail(),
                        card.getEndDate(),
                        card.getStatus(),
                        card.getBalance()
                )).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<String> deleteCard(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Карта", id));
        statementRepository.deleteByCard(card);
        transactionRepository.deleteAllByCardInvolved(card);
        cardRepository.delete(card);
        return ResponseEntity.ok("Карта удаленнна");
    }

    public ResponseEntity<String> blockCard(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Карта", id));
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
        return ResponseEntity.ok("Карта заблокированна");
    }

    public ResponseEntity<String> activeCard(Long idCard) {
        Card card = cardRepository.findById(idCard).orElseThrow(() -> new ResourceNotFoundException("Карта", idCard));
        card.setStatus(CardStatus.ACTIVE);
        cardRepository.save(card);
        return ResponseEntity.ok("Карта активирована");
    }


}

