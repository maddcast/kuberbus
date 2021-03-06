package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.SetHeaderStepConfig;
import com.antilogics.servicebus.core.CommandResult;
import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.HttpResponder;
import com.antilogics.servicebus.core.ServerContext;
import com.antilogics.servicebus.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SetHeaderCommand extends Command<SetHeaderStepConfig> {
    public SetHeaderCommand(SetHeaderStepConfig stepConfig) {
        super(stepConfig);
    }


    @Override
    public CommandResult process(int pipeId, HttpMessage httpMessage, ServerContext serverContext) {
        log.info("RN: {}. Setting header {}", pipeId, stepConfig.getName());
        var headers = HttpUtils.replaceHeader(httpMessage.getHeaders(),
                stepConfig.getName(),
                stepConfig.getValue());
        return CommandResult.successAndReplaceMessage(httpMessage.withHeaders(headers));
    }
}
