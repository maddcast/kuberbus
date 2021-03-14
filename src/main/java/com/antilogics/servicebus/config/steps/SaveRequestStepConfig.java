package com.antilogics.servicebus.config.steps;

import com.antilogics.servicebus.config.Expression;
import com.antilogics.servicebus.config.SaveFormatType;
import com.antilogics.servicebus.core.commands.Command;
import com.antilogics.servicebus.core.commands.SaveRequestCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaveRequestStepConfig extends AbstractStepConfig {
    private Expression rootDir;
    private SaveFormatType format = SaveFormatType.TEXT;
    private Expression curlEndpoint;


    @Override
    public Command<? extends AbstractStepConfig> toCommand() {
        return new SaveRequestCommand(this);
    }
}
