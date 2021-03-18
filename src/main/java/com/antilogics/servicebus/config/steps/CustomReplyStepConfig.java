package com.antilogics.servicebus.config.steps;

import com.antilogics.servicebus.config.Expression;
import com.antilogics.servicebus.core.commands.Command;
import com.antilogics.servicebus.core.commands.CustomReplyCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomReplyStepConfig extends AbstractStepConfig {
    private int status;
    private String contentType;
    private Expression body;

    @Override
    public Command<? extends AbstractStepConfig> toCommand() {
        return new CustomReplyCommand(this);
    }
}
