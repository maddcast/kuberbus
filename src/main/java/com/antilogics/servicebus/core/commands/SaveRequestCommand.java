package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.SaveRequestStepConfig;
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
public class SaveRequestCommand extends Command<SaveRequestStepConfig> {
    public SaveRequestCommand(SaveRequestStepConfig stepConfig) {
        super(stepConfig);
    }


    @Override
    @SneakyThrows
    public CommandResult process(int pipeId, HttpMessage httpMessage, HttpResponder responder) {
        String dir = FileUtils.getSaveDir(stepConfig.getRootDir(), httpMessage);
        log.info("RN: {}. Saving request to dir {}", pipeId, dir);
        String filePrefix = String.format("%1$tH-%1$tM-%1$tS_%2$04d", Calendar.getInstance(), pipeId);
        switch (stepConfig.getFormat()) {
            case TEXT:
                saveAsText(dir, filePrefix, httpMessage);
                break;
            case CURL:
                String endpoint = stepConfig.getCurlEndpoint().evalString(httpMessage);
                saveAsCurl(dir, filePrefix, httpMessage, endpoint);
                break;
        }

        return CommandResult.success();
    }


    private void saveAsText(String dir, String filePrefix, HttpMessage httpMessage) throws IOException {
        var headersOutput = new StringBuilder();
        httpMessage.getHeaders().forEach((name, value) -> headersOutput.append(name).append(": ").append(value).append('\n'));

        Files.writeString(Path.of(dir, filePrefix + ".headers.req.txt"),
                headersOutput,
                StandardOpenOption.CREATE_NEW);
        Files.write(Path.of(dir, filePrefix + ".body.req.txt"),
                httpMessage.getBodyAsBytes(),
                StandardOpenOption.CREATE_NEW);
    }


    private void saveAsCurl(String dir, String filePrefix, HttpMessage httpMessage, String endpoint) throws IOException {
        var shellOutput = new StringBuilder("#!/bin/bash\n\n");
        shellOutput.append("curl -k -X ").append(httpMessage.getMethod()).append(" \\\n");
        httpMessage.getHeaders().forEach((name, value) -> shellOutput.append("-H '").append(name).append(": ").append(value).append("' \\\n"));
        String bodyFileName = filePrefix + ".body.req.txt";
        shellOutput.append("--data-binary \"@").append(bodyFileName).append("\" \\\n");
        shellOutput.append(endpoint);

        var scriptPath = Path.of(dir, filePrefix + ".curl.sh");
        Files.writeString(scriptPath,
                shellOutput,
                StandardOpenOption.CREATE_NEW);
        scriptPath.toFile().setExecutable(true);
        Files.write(Path.of(dir, bodyFileName),
                httpMessage.getBodyAsBytes(),
                StandardOpenOption.CREATE_NEW);
    }
}
