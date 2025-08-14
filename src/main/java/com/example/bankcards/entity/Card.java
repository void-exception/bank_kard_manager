package com.example.bankcards.entity;

import com.example.bankcards.util.YearMonthConver;
import jakarta.persistence.*;

import java.time.YearMonth;

/**
 * сущности банковской карты
 */
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Convert(converter = YearMonthConver.class)
    private YearMonth endDate;

    @Enumerated(EnumType.STRING)
    private CardStatus status;
    private double balance;


    public Card() {
    }

    public Card(String cardNumber, User user, YearMonth endDate, CardStatus status, double balance) {
        this.cardNumber = cardNumber;
        this.user = user;
        this.endDate = endDate;
        this.status = status;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public void setEndDate(YearMonth endDate) {
        this.endDate = endDate;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
