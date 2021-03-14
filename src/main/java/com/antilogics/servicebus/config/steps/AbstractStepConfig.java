package com.antilogics.servicebus.config.steps;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.antilogics.servicebus.core.commands.Command;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "step")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RespondStepConfig.class, name = "reply"),
        @JsonSubTypes.Type(value = CustomRespondStepConfig.class, name = "custom_reply"),
        @JsonSubTypes.Type(value = SendStepConfig.class, name = "send"),
        @JsonSubTypes.Type(value = SaveRequestStepConfig.class, name = "save_request"),
        @JsonSubTypes.Type(value = SaveResponseStepConfig.class, name = "save_response"),
        @JsonSubTypes.Type(value = SetHeaderStepConfig.class, name = "set_header")
})
public abstract class AbstractStepConfig {
    public abstract Command<? extends AbstractStepConfig> toCommand();
}
