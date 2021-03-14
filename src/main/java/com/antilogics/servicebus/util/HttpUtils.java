package com.antilogics.servicebus.util;

import okhttp3.OkHttpClient;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    public static String getHeaderValue(Map<String, String> headers, String headerName) {
        String key = headers.keySet().stream()
                .filter(s -> s.equalsIgnoreCase(headerName))
                .findAny()
                .orElse(headerName);
        return headers.get(key);
    }


    public static Map<String, String> replaceHeader(Map<String, String> headers, String headerName, String headerValue) {
        var newHeaders = new HashMap<String, String>();
        headers.forEach((key, value) -> {
            if (!key.equalsIgnoreCase(headerName)) {
                newHeaders.put(key, value);
            }
        });
        newHeaders.put(headerName, headerValue);
        return newHeaders;
    }


    public static OkHttpClient createOkHttpClient(boolean ignoreSSL) {
        var client = new OkHttpClient();
        if (ignoreSSL) {
            client = client.newBuilder().sslSocketFactory(client.sslSocketFactory(), new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }).hostnameVerifier((hostname, session) -> true).build();
        }
        return client;
    }
}
