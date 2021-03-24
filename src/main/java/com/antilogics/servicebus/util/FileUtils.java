package com.antilogics.servicebus.util;

import com.antilogics.servicebus.config.Expression;
import com.antilogics.servicebus.core.HttpMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {
    public static String getSaveDir(Expression dirExpression, HttpMessage httpMessage) throws IOException {
        String dir = dirExpression.evalString(httpMessage);
        Path dirPath = Path.of(dir, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        if (Files.notExists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        return dirPath.normalize().toFile().getAbsolutePath();
    }
}
