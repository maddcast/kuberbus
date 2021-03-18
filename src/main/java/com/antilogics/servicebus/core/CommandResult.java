package com.antilogics.servicebus.core;

import lombok.Getter;

@Getter
public class CommandResult {
    private final HttpMessage resultMessage;
    private final boolean continuePipeline;


    private CommandResult(HttpMessage resultMessage, boolean continuePipeline) {
        this.resultMessage = resultMessage;
        this.continuePipeline = continuePipeline;
    }


    public static CommandResult success() {
        return new CommandResult(null, true);
    }


    public static CommandResult successAndReplaceMessage(HttpMessage resultMessage) {
        return new CommandResult(resultMessage, true);
    }


    public static CommandResult halt() {
        return new CommandResult(null, false);
    }
}
