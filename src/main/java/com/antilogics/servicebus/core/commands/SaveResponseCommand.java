package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.SaveResponseStepConfig;
import com.antilogics.servicebus.core.CommandResult;
import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.HttpResponder;
import com.antilogics.servicebus.util.FileUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;

@Slf4j
public class SaveResponseCommand extends Command<SaveResponseStepConfig> {
    public SaveResponseCommand(SaveResponseStepConfig stepConfig) {
        super(stepConfig);
    }


    @Override
    @SneakyThrows
    public CommandResult process(int pipeId, HttpMessage httpMessage, HttpResponder responder) {
        String dir = FileUtils.getSaveDir(stepConfig.getRootDir(), httpMessage);
        log.info("RN: {}. Saving response to dir {}", pipeId, dir);
        String filePrefix = String.format("%1$tH-%1$tM-%1$tS_%2$04d", Calendar.getInstance(), pipeId);
        saveAsText(dir, filePrefix, httpMessage);
        return CommandResult.success();
    }


    private void saveAsText(String dir, String filePrefix, HttpMessage httpMessage) throws IOException {
        var headersOutput = new StringBuilder();
        httpMessage.getHeaders().forEach((name, value) -> headersOutput.append(name).append(": ").append(value).append('\n'));

        Files.writeString(Path.of(dir, filePrefix + ".headers.resp.txt"),
                headersOutput,
                StandardOpenOption.CREATE_NEW);
        Files.write(Path.of(dir, filePrefix + ".body.resp.txt"),
                httpMessage.getBodyAsBytes(),
                StandardOpenOption.CREATE_NEW);
    }
}
