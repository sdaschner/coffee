package com.sebastian_daschner.coffee_shop;

import com.airhacks.porcupine.configuration.control.ExecutorConfigurator;
import com.airhacks.porcupine.execution.control.ExecutorConfiguration;

import javax.enterprise.inject.Specializes;

@Specializes
public class CustomExecutorConfigurator extends ExecutorConfigurator {

    @Override
    public ExecutorConfiguration defaultConfigurator() {
        return new ExecutorConfiguration.Builder()
                .abortPolicy()
                .maxPoolSize(4)
                .queueCapacity(4)
                .build();
    }
}
