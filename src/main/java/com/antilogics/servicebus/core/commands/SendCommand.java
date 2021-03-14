package com.antilogics.servicebus.core.commands;

import com.antilogics.servicebus.config.steps.SendStepConfig;
import com.antilogics.servicebus.core.HttpMessage;
import com.antilogics.servicebus.core.HttpResponder;
import com.antilogics.servicebus.util.HttpUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
public class SendCommand extends Command<SendStepConfig> {
    public SendCommand(SendStepConfig stepConfig) {
        super(stepConfig);
    }


    @Override
    @SneakyThrows
    public HttpMessage process(int pipeId, HttpMessage httpMessage, HttpResponder responder) {
        var url = new URL(stepConfig.getEndpoint().evalString(httpMessage));
        log.info("RN: {}. Sending request to {}", pipeId, url);
        RequestBody requestBody = null;
        if (httpMessage.getBodyAsBytes().length > 0) {
            requestBody = RequestBody.create(httpMessage.getBodyAsBytes());
        }

        var headers = HttpUtils.replaceHeader(httpMessage.getHeaders(),
                "Host",
                stepConfig.getHost() != null ? stepConfig.getHost() : url.getHost());
        var request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .method(httpMessage.getMethod(), requestBody)
                .build();
        var client = HttpUtils.createOkHttpClient(stepConfig.isIgnoreSSL());
        var response = client.newCall(request).execute();
        var responseHeaders = new HashMap<String, String>();
        response.headers().forEach(pair -> responseHeaders.put(pair.getFirst(), pair.getSecond()));
        return new HttpMessage(
                response.code(),
                httpMessage.getPath(),
                httpMessage.getMethod(),
                responseHeaders,
                Objects.requireNonNull(response.body()).bytes()
        );
    }
}
