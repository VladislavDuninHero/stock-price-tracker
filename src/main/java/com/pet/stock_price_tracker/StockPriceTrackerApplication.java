package com.pet.stock_price_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StockPriceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockPriceTrackerApplication.class, args);
	}

}
