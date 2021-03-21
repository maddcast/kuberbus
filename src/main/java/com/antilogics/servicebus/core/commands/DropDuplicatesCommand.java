package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.DropDuplicatesConfig;
import com.antilogics.servicebus.core.CommandResult;
import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.RequestCacheKey;
import com.antilogics.servicebus.core.ServerContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class DropDuplicatesCommand extends Command<DropDuplicatesConfig> {
    public DropDuplicatesCommand(DropDuplicatesConfig stepConfig) {
        super(stepConfig);
    }


    @Override
    @SneakyThrows
    public CommandResult process(int pipeId, HttpMessage httpMessage, ServerContext serverContext) {
        log.info("RN: {}. Wait {} seconds for duplicate", pipeId, stepConfig.getSecondsToWait());

        var requestCacheKey = new RequestCacheKey(httpMessage.getPath(), httpMessage.getBodyAsBytes());
        stepConfig.getRequestCache().put(requestCacheKey, pipeId);

        serverContext.sleep(Duration.ofSeconds(stepConfig.getSecondsToWait()).toMillis());

        Integer checkPipeId = stepConfig.getRequestCache().getIfPresent(requestCacheKey);

        if (checkPipeId == null) {
            log.error("RN: {}. Pipe id for request in cache is null. What's happening?", pipeId);
            throw new IllegalStateException("Pipe id for request in cache is null. What's happening?");
        }

        if (checkPipeId != pipeId) {
            log.warn("RN: {}. Drop duplicate request in favor of RN {}", pipeId, checkPipeId);
            return CommandResult.halt();
        }

        return CommandResult.success();
    }
}
