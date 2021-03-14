package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.RespondStepConfig;
import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.HttpResponder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RespondCommand extends Command<RespondStepConfig> {
    public RespondCommand(RespondStepConfig stepConfig) {
        super(stepConfig);
    }


    @Override
    @SneakyThrows
    public HttpMessage process(int pipeId, HttpMessage httpMessage, HttpResponder responder) {
        log.info("RN: {}. Sending response", pipeId);

        responder.respond(httpMessage);

        return httpMessage;
    }
}
