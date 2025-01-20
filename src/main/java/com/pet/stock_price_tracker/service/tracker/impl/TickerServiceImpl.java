package com.pet.stock_price_tracker.service.tracker.impl;

import com.pet.stock_price_tracker.client.dto.polygon.request.TickerRequestDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerDataDTO;
import com.pet.stock_price_tracker.client.dto.polygon.response.TickerIntegrationResponseDTO;
import com.pet.stock_price_tracker.entity.Ticker;
import com.pet.stock_price_tracker.entity.User;
import com.pet.stock_price_tracker.exception.UserNotFoundException;
import com.pet.stock_price_tracker.repository.TickerRepository;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.security.SecurityUser;
import com.pet.stock_price_tracker.service.integration.service.PolygonService;
import com.pet.stock_price_tracker.service.tracker.TickerService;
import com.pet.stock_price_tracker.service.utils.mapping.TickerMapper;
import com.pet.stock_price_tracker.service.validation.manager.impl.TickerRequestValidationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TickerServiceImpl implements TickerService {

    private final TickerRepository tickerRepository;
    private final UserRepository userRepository;
    private final TickerMapper tickerMapper;
    private final PolygonService polygonService;
    private final TickerRequestValidationManager tickerRequestValidationManager;

    public TickerServiceImpl(
            TickerRepository tickerRepository,
            UserRepository userRepository,
            PolygonService polygonService,
            TickerMapper tickerMapper,
            TickerRequestValidationManager tickerRequestValidationManager
    ) {
        this.tickerRepository = tickerRepository;
        this.polygonService = polygonService;
        this.tickerMapper = tickerMapper;
        this.userRepository = userRepository;
        this.tickerRequestValidationManager = tickerRequestValidationManager;
    }

    @Override
    public void save(TickerRequestDTO tickerRequestDTO) {
        tickerRequestValidationManager.validate(tickerRequestDTO);

        TickerIntegrationResponseDTO tickerIntegrationResponseDTO = polygonService.save(tickerRequestDTO);

        if (tickerIntegrationResponseDTO.getResults() != null) {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();

            User user = userRepository.findUserByLogin(login)
                    .orElseThrow(() -> new UserNotFoundException(login));


            List<LocalDate> dates = user.getTickers().stream()
                    .filter(d -> d.getTicker().equalsIgnoreCase(tickerIntegrationResponseDTO.getTicker()))
                    .map(Ticker::getDate)
                    .toList();


            List<Ticker> tickers = tickerIntegrationResponseDTO.getResults()
                    .stream()
                    .filter(t -> !dates.contains(t.getT().toLocalDateTime().toLocalDate()))
                    .map(tickerDTO -> {
                        Ticker tickerEntity = tickerMapper.toEntity(tickerDTO);
                        tickerEntity.setUserId(user);
                        tickerEntity.setTicker(tickerRequestDTO.getTicker());

                        return tickerEntity;
                    })
                    .toList();

            tickerRepository.saveAll(tickers);
        }
    }

    @Override
    public TickerDTO getSavedTickers(String symbol) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByLogin(login).orElseThrow(() -> new UserNotFoundException(login));

        List<Ticker> tickers = tickerRepository.findBySymbolAndUserId(symbol, user.getId());

        List<TickerDataDTO> tickersData = tickers.stream().map(tickerMapper::toTickerDataDTO).toList();

        return new TickerDTO(symbol, tickersData);
    }
}
