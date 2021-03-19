package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.AbstractStepConfig;
import com.antilogics.servicebus.core.CommandResult;
import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.ServerContext;

public abstract class Command<C extends AbstractStepConfig> {
    protected C stepConfig;


    public Command(C stepConfig) {
        this.stepConfig = stepConfig;
    }


    public abstract CommandResult process(int pipeId, HttpMessage httpMessage, ServerContext serverContext);
}
