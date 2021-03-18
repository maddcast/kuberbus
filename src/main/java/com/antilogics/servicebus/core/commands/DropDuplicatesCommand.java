package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.DropDuplicatesConfig;
import com.antilogics.servicebus.core.CommandResult;
import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.HttpResponder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DropDuplicatesCommand extends Command<DropDuplicatesConfig> {
    public DropDuplicatesCommand(DropDuplicatesConfig stepConfig) {
        super(stepConfig);
    }


    @Override
    @SneakyThrows
    public CommandResult process(int pipeId, HttpMessage httpMessage, HttpResponder responder) {
        log.info("RN: {}. Wait {} seconds for duplicate", pipeId, stepConfig.getSecondsToWait());

        return CommandResult.halt();
    }
}
