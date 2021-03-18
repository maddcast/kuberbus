package com.antilogics.servicebus.config.steps;

import com.antilogics.servicebus.core.commands.Command;
import com.antilogics.servicebus.core.commands.ReplyCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReplyStepConfig extends AbstractStepConfig {
    @Override
    public Command<? extends AbstractStepConfig> toCommand() {
        return new ReplyCommand(this);
    }
}
