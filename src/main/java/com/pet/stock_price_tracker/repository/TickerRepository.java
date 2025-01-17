package com.pet.stock_price_tracker.repository;

import com.pet.stock_price_tracker.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TickerRepository extends JpaRepository<Ticker, Long> {

    @Query("FROM Ticker t WHERE t.userId.id = :userId AND t.ticker = :symbol")
    List<Ticker> findBySymbolAndUserId(String symbol, long userId);
}
