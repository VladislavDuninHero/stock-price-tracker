package com.pet.stock_price_tracker.service.tracker.impl;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerIntegrationResponseDTO;
import com.pet.stock_price_tracker.entity.Ticker;
import com.pet.stock_price_tracker.entity.User;
import com.pet.stock_price_tracker.exception.UserNotFoundException;
import com.pet.stock_price_tracker.repository.TickerRepository;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.service.integration.service.PolygonService;
import com.pet.stock_price_tracker.service.tracker.TickerService;
import com.pet.stock_price_tracker.service.utils.mapping.TickerMapper;
import jakarta.persistence.Entity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TickerServiceImpl implements TickerService {

    private final TickerRepository tickerRepository;
    private final UserRepository userRepository;
    private final TickerMapper tickerMapper;
    private final PolygonService polygonService;

    public TickerServiceImpl(
            TickerRepository tickerRepository,
            UserRepository userRepository,
            PolygonService polygonService,
            TickerMapper tickerMapper
    ) {
        this.tickerRepository = tickerRepository;
        this.polygonService = polygonService;
        this.tickerMapper = tickerMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void save(TickerRequestDTO ticker) {
        TickerIntegrationResponseDTO tickerIntegrationResponseDTO = polygonService.save(ticker);

        String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findUserByLogin(login).orElseThrow(() -> new UserNotFoundException(login));


        List<Ticker> tickers = tickerIntegrationResponseDTO.getResults()
                .stream()
                .map(tickerDTO -> {
                    Ticker tickerEntity = tickerMapper.toEntity(tickerDTO);
                    tickerEntity.setUserId(user);

                    return tickerEntity;
                })
                .toList();

        tickerRepository.saveAll(tickers);
    }
}
