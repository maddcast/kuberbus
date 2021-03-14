package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.CustomRespondStepConfig;
import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.HttpResponder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class CustomRespondCommand extends Command<CustomRespondStepConfig> {
    public CustomRespondCommand(CustomRespondStepConfig stepConfig) {
        super(stepConfig);
    }


    @Override
    @SneakyThrows
    public HttpMessage process(int pipeId, HttpMessage httpMessage, HttpResponder responder) {
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
        responder.respond(responseMessage);

        return httpMessage;
    }
}
