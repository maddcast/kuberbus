package com.antilogics.servicebus.config.steps;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.antilogics.servicebus.core.commands.Command;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "step")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ReplyStepConfig.class, name = "reply"),
        @JsonSubTypes.Type(value = CustomReplyStepConfig.class, name = "custom_reply"),
        @JsonSubTypes.Type(value = SendStepConfig.class, name = "send"),
        @JsonSubTypes.Type(value = SaveRequestStepConfig.class, name = "save_request"),
        @JsonSubTypes.Type(value = SaveResponseStepConfig.class, name = "save_response"),
        @JsonSubTypes.Type(value = SetHeaderStepConfig.class, name = "set_header"),
        @JsonSubTypes.Type(value = DropDuplicatesConfig.class, name = "drop_duplicates")
})
public abstract class AbstractStepConfig {
    public abstract Command<? extends AbstractStepConfig> toCommand();
}
