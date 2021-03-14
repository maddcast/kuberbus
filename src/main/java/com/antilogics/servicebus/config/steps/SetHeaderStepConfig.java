package com.antilogics.servicebus.config.steps;

import com.antilogics.servicebus.core.commands.Command;
import com.antilogics.servicebus.core.commands.SetHeaderCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SetHeaderStepConfig extends AbstractStepConfig {
    private String name;
    private String value;


    @Override
    public Command<? extends AbstractStepConfig> toCommand() {
        return new SetHeaderCommand(this);
    }
}
