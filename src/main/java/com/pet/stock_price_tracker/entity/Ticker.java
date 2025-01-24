package com.pet.stock_price_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickers")
public class Ticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "open")
    private BigDecimal open;

    @Column(name = "close")
    private BigDecimal close;

    @Column(name = "highest")
    private BigDecimal highest;

    @Column(name = "lowest")
    private BigDecimal lowest;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "date")
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticker ticker1 = (Ticker) o;
        return Objects.equals(id, ticker1.id)
                && Objects.equals(user, ticker1.user)
                && Objects.equals(open, ticker1.open)
                && Objects.equals(close, ticker1.close)
                && Objects.equals(highest, ticker1.highest)
                && Objects.equals(lowest, ticker1.lowest)
                && Objects.equals(ticker, ticker1.ticker)
                && Objects.equals(date, ticker1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, open, close, highest, lowest, ticker, date);
    }
}
