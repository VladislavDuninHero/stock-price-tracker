package com.pet.stock_price_tracker.aop;

import com.pet.stock_price_tracker.constants.OfficialProperties;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PrometheusAspect {

    private final MeterRegistry prometheusMeterRegistry;

    public PrometheusAspect(MeterRegistry prometheusMeterRegistry) {
        this.prometheusMeterRegistry = prometheusMeterRegistry;
    }

    @Pointcut("execution(public * com.pet.stock_price_tracker.service.tracker.impl.TickerServiceImpl.save(..))")
    public void saveTickersPointcut() {}

    @Pointcut("execution(public * com.pet.stock_price_tracker.service.tracker.impl.TickerServiceImpl.getSavedTickers(..))")
    public void savedTickersPointcut() {}

    @Pointcut("execution(public * com.pet.stock_price_tracker.service.user.impl.UserServiceImpl.createUser(..))")
    public void registerUserPointcut() {}

    @After("saveTickersPointcut()")
    public void afterSaveTickersPointcut() {
        prometheusMeterRegistry.counter(OfficialProperties.PROMETHEUS_SAVE_TICKERS_REQUESTS_COUNTER).increment();
    }

    @After("savedTickersPointcut()")
    public void afterSavedTickersPointcut() {
        prometheusMeterRegistry.counter(OfficialProperties.PROMETHEUS_SAVED_TICKERS_REQUESTS_COUNTER).increment();
    }

    @After("registerUserPointcut()")
    public void afterRegisterUserPointcut() {
        prometheusMeterRegistry.counter(OfficialProperties.PROMETHEUS_REGISTRATION_USER_REQUESTS_COUNTER).increment();
    }
}
