package com.antilogics.servicebus.config.steps;

import com.antilogics.servicebus.config.Expression;
import com.antilogics.servicebus.core.commands.Command;
import com.antilogics.servicebus.core.commands.DropDuplicatesCommand;
import com.antilogics.servicebus.core.commands.SendCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DropDuplicatesConfig extends AbstractStepConfig {
    private String poolName;
    private int secondsToWait;


    @Override
    public Command<? extends AbstractStepConfig> toCommand() {
        return new DropDuplicatesCommand(this);
    }
}
