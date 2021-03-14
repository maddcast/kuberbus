package com.antilogics.servicebus.config.steps;

import com.antilogics.servicebus.config.Expression;
import com.antilogics.servicebus.core.commands.Command;
import com.antilogics.servicebus.core.commands.SendCommand;
import lombok.Data;

@Data
public class SendStepConfig extends AbstractStepConfig {
    private Expression endpoint;
    private String host;
    private boolean ignoreSSL = true;


    @Override
    public Command<? extends AbstractStepConfig> toCommand() {
        return new SendCommand(this);
    }
}
