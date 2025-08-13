package com.example.bankcards.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * сущности лимита по тратам у банковской карты
 */
@Entity
@Table(name = "card_limit")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private double sum;
    private LocalDateTime startLimit;
    private LocalDateTime endLimit;

    public Limit() {
    }

    public Limit(Card card, double sum, LocalDateTime endLimit) {
        this.card = card;
        this.sum = sum;
        this.startLimit = LocalDateTime.now();
        this.endLimit = endLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public LocalDateTime getStartLimit() {
        return startLimit;
    }

    public void setStartLimit(LocalDateTime startLimit) {
        this.startLimit = startLimit;
    }

    public LocalDateTime getEndLimit() {
        return endLimit;
    }

    public void setEndLimit(LocalDateTime endLimit) {
        this.endLimit = endLimit;
    }
}
