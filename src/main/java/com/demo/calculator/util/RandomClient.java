package com.demo.calculator.util;

import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@PropertySource("classpath:random.properties")
public class RandomClient {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(15))
            .build();

    @Value("${config.random.quota.url}")
    private String quotaUrl;

    @Value("${config.random.api.url}")
    private String apiUrl;

    @Value("${config.random.api.key}")
    private String apiKey;

    /**
     * Request a random string from external api
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String getRandomString() throws IOException, InterruptedException {

        int quota = getQuota();

        if (quota > 0) {

            ObjectMapper mapper = new ObjectMapper();

            // post request object
            StringMethodRequest stringMethod = new StringMethodRequest();
            stringMethod.params.put("apiKey", apiKey);
            stringMethod.params.put("n", 1);
            stringMethod.params.put("length", 20);
            stringMethod.params.put("characters", "abcdefghijklmnopqrstuvwxyz");
            stringMethod.params.put("replacement", true);

            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(stringMethod);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-type", "application/json")
                    .uri(URI.create(apiUrl))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body() != null) {
                StringMethodResponse stringMethodResponse = mapper.readValue(response.body(), StringMethodResponse.class);
                return stringMethodResponse.getResult().getRandom().getData().get(0);
            }

        }

        return null;

    }

    private int getQuota() throws IOException, InterruptedException {

        HttpRequest requestQuota = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(quotaUrl))
                .build();

        HttpResponse<String> httpResponseQuota = httpClient.send(requestQuota, HttpResponse.BodyHandlers.ofString());
        if (httpResponseQuota.body() != null) {
            String response = httpResponseQuota.body().trim();
            return Integer.parseInt(response);
        }

        return 0;

    }

    class StringMethodRequest {
        public String jsonrpc = "2.0";
        public String method = "generateStrings";
        public Map<String, Object> params = new LinkedHashMap<>();
        public Integer id = 42;
    }


}
