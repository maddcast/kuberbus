package com.antilogics.servicebus.config.steps;

import com.antilogics.servicebus.config.Expression;
import com.antilogics.servicebus.core.commands.Command;
import com.antilogics.servicebus.core.commands.SaveResponseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaveResponseStepConfig extends AbstractStepConfig {
    private Expression rootDir;


    @Override
    public Command<? extends AbstractStepConfig> toCommand() {
        return new SaveResponseCommand(this);
    }
}
