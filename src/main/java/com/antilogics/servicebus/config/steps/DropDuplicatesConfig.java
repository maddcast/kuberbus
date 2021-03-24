package com.antilogics.servicebus.config.steps;

import com.antilogics.servicebus.core.RequestCacheKey;
import com.antilogics.servicebus.core.commands.Command;
import com.antilogics.servicebus.core.commands.DropDuplicatesCommand;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Duration;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "requestCache")
public class DropDuplicatesConfig extends AbstractStepConfig {
    // TODO: move cache away from config to ServerContext
    private Cache<RequestCacheKey, Integer> requestCache = null;
    private int secondsToWait;


    @Override
    public void afterPropertiesSet() {
        requestCache = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofSeconds(2L * secondsToWait)).build();
    }

    @Override
    public Command<? extends AbstractStepConfig> toCommand() {
        return new DropDuplicatesCommand(this);
    }
}
