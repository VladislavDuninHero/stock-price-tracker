package com.pet.stock_price_tracker.repository;

import com.pet.stock_price_tracker.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerRepository extends JpaRepository<Ticker, Long> {

}
