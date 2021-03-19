package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.CustomReplyStepConfig;
import com.antilogics.servicebus.core.CommandResult;
import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.ServerContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class CustomReplyCommand extends Command<CustomReplyStepConfig> {
    public CustomReplyCommand(CustomReplyStepConfig stepConfig) {
        super(stepConfig);
    }


    @Override
    @SneakyThrows
    public CommandResult process(int pipeId, HttpMessage httpMessage, ServerContext serverContext) {
        log.info("RN: {}. Sending custom response", pipeId);
        var headers = new HashMap<String, String>();
        if (stepConfig.getContentType() != null) {
            headers.put("Content-type", stepConfig.getContentType());
        }
        var responseMessage = new HttpMessage(
                stepConfig.getStatus(),
                null,
                null,
                headers,
                stepConfig.getBody().evalString(httpMessage).getBytes()
        );
        serverContext.respond(responseMessage);

        return CommandResult.success();
    }
}
