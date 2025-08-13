package com.example.bankcards.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * сущности заявки на блокировку банковской карты
 */
@Entity
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;
    private LocalDateTime date_of_dispatch;
    private String status;

    public Statement() {
    }

    public Statement(Card card) {
        this.card = card;
        this.date_of_dispatch = LocalDateTime.now();
        this.status = "PENDING";
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

    public LocalDateTime getDate_of_dispatch() {
        return date_of_dispatch;
    }

    public void setDate_of_dispatch(LocalDateTime date_of_dispatch) {
        this.date_of_dispatch = date_of_dispatch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
