package pl.edu.pw.zpoif.exchangeit.util.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoif.exchangeit.config.Filter;
import pl.edu.pw.zpoif.exchangeit.constants.alphavintageapi.Endpoint;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.AlphaVintageObject;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.GlobalQuote;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.TimeSeriesEntry;
import pl.edu.pw.zpoif.exchangeit.service.AlphaVintageApiService;
import pl.edu.pw.zpoif.exchangeit.util.alphavintageapi.parser.Parser;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class TradingDataProcessor extends Processor {
    private final AlphaVintageApiService alphaVintageApiService;
    private final Parser alphaVintageDataParser;

    @Autowired
    public TradingDataProcessor(
            AlphaVintageApiService alphaVintageApiService, Parser alphaVintageDataParser
                               ) {
        this.alphaVintageApiService = alphaVintageApiService;
        this.alphaVintageDataParser = alphaVintageDataParser;
    }

    public ResponseEntity<?> handleChartEndpoint(
            List<TimeSeriesEntry> data, SimpleBeanPropertyFilter filter
                                                ) {
        try {
            String json =
                    OBJECT_MAPPER.writer(new SimpleFilterProvider().addFilter(Filter.ALPHA_VINTAGE_TIME_SERIES, filter))
                                 .writeValueAsString(data);
            return ResponseEntity.ok().body(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public GlobalQuote handleQuoteEndpoint(
            Endpoint endpoint, Map<String, String> queryParams, Map<String, String> errors
                                          ) {
        List<GlobalQuote> l = handleEndpoint(endpoint, queryParams, errors);
        if (checkL(l, errors)) {
            return l.get(0);
        }
        return null;
    }

    public <T extends AlphaVintageObject> List<T> handleEndpoint(
            Endpoint endpoint, Map<String, String> queryParams, Map<String, String> errors
                                                                ) {
        Map<String, String> newErrors = alphaVintageDataParser.validateQueryParams(queryParams);
        if (newErrors != null) {
            errors.putAll(newErrors);
            return null;
        }

        try {
            return CompletableFuture.supplyAsync(() -> alphaVintageApiService.call(endpoint, queryParams)).thenApply(
                    o -> alphaVintageDataParser.parse(endpoint, o).stream().map(oo -> (T) oo)
                                               .collect(Collectors.toList())).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TimeSeriesEntry> handleDataEndpoint(
            Endpoint endpoint, Map<String, String> queryParams, Map<String, String> errors
                                                   ) {
        List<TimeSeriesEntry> l = handleEndpoint(endpoint, queryParams, errors);
        if (checkL(l, errors)) {
            return l.stream().map(TimeSeriesEntry::calculate).collect(Collectors.toList());
        }
        return null;
    }

    private boolean checkL(List<?> l, Map<String, String> errors) {
        if (l == null) {
            errors.put("error", "AlphaVintageApi returned empty data sequence. Check your query params.");
            return false;
        }
        return true;
    }
}
