package pl.edu.pw.zpoif.exchangeit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.pw.zpoif.exchangeit.config.AlphaVintageApiConfig;
import pl.edu.pw.zpoif.exchangeit.constants.alphavintageapi.Endpoint;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlphaVintageApiService {
    private final AlphaVintageApiConfig config;
    private final WebClient webClient;

    @Autowired
    public AlphaVintageApiService(AlphaVintageApiConfig config) {
        this.config = config;
        this.webClient = WebClient.builder().baseUrl(config.getUrl()).build();
    }

    public JsonNode call(Endpoint endpoints) {
        return call(endpoints, new HashMap<>());
    }

    public JsonNode call(Endpoint endpoint, Map<String, String> queryParams) {
        fillParams(endpoint, queryParams);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        String uri = uriBuilder.build().toUriString();


        String data = webClient.get().uri(uri).retrieve().bodyToFlux(String.class).reduce((s1, s2) -> s1 + s2).block();
        try {
            return new ObjectMapper().readTree(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillParams(Endpoint endpoint, Map<String, String> queryParams) {
        queryParams.put("apikey", config.getKey());
        queryParams.put("function", endpoint.toString());
    }
}
