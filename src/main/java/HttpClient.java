import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class HttpClient {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(body);
            getListFormJson(body);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        };
    }
    public static List<String> getListFormJson(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Facts[] facts = mapper.readValue(jsonText, Facts[].class);
            List<Facts> factsList = Arrays.asList(facts);
            factsList.stream().filter(value -> value.getUpvotes() > 0).forEach(System.out :: println);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
