package com.pet.stock_price_tracker.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PrometheusAspect {

    @Pointcut("execution(public * com.pet.stock_price_tracker.service.tracker.impl.TickerServiceImpl.save(..))")
    public void saveTickersPointcut() {

    }
}
