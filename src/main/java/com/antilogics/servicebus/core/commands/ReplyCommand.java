package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.ReplyStepConfig;
import com.antilogics.servicebus.core.CommandResult;
import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.ServerContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReplyCommand extends Command<ReplyStepConfig> {
    public ReplyCommand(ReplyStepConfig stepConfig) {
        super(stepConfig);
    }


    @Override
    @SneakyThrows
    public CommandResult process(int pipeId, HttpMessage httpMessage, ServerContext serverContext) {
        log.info("RN: {}. Sending response", pipeId);

        serverContext.respond(httpMessage);

        return CommandResult.success();
    }
}
