package com.antilogics.servicebus.config.steps;

import com.antilogics.servicebus.core.commands.Command;
import com.antilogics.servicebus.core.commands.RespondCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RespondStepConfig extends AbstractStepConfig {
    @Override
    public Command<? extends AbstractStepConfig> toCommand() {
        return new RespondCommand(this);
    }
}
