package com.example.bankcards.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * сущности транзакций банковской карты
 */
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "from_card_id")
    private Card fromCard;

    @ManyToOne
    @JoinColumn(name = "to_card_id")
    private Card toCard;

    private LocalDateTime localDateTime;
    private double sumTransaction;

    public Transaction() {
    }

    public Transaction(User user, Card fromCard, Card toCard, double sumTransaction) {
        this.user = user;
        this.fromCard = fromCard;
        this.toCard = toCard;
        this.localDateTime = LocalDateTime.now();
        this.sumTransaction = sumTransaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getFromCard() {
        return fromCard;
    }

    public void setFromCard(Card fromCard) {
        this.fromCard = fromCard;
    }

    public Card getToCard() {
        return toCard;
    }

    public void setToCard(Card toCard) {
        this.toCard = toCard;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public double getSumTransaction() {
        return sumTransaction;
    }

    public void setSumTransaction(double sumTransaction) {
        this.sumTransaction = sumTransaction;
    }
}
