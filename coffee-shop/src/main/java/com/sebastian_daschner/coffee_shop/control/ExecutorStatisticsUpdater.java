package com.sebastian_daschner.coffee_shop.control;

import com.sebastian_daschner.porcupine_metrics.PorcupineMetrics;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ExecutorStatisticsUpdater {

    @Inject
    PorcupineMetrics porcupineMetrics;

    @Resource
    ManagedScheduledExecutorService scheduler;

    @PostConstruct
    public void init() {
        scheduler.scheduleAtFixedRate(porcupineMetrics::updateMetrics, 0, 5, TimeUnit.SECONDS);
    }

}
