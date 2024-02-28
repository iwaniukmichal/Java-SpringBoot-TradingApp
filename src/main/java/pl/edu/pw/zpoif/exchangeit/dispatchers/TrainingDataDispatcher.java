package pl.edu.pw.zpoif.exchangeit.dispatchers;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoif.exchangeit.constants.alphavintageapi.Endpoint;
import pl.edu.pw.zpoif.exchangeit.constants.api.TradingDataInputType;
import pl.edu.pw.zpoif.exchangeit.constants.api.TradingDataOutputField;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.GlobalQuote;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.TimeSeriesEntry;
import pl.edu.pw.zpoif.exchangeit.util.alphavintageapi.queries.TradingDataQuery;
import pl.edu.pw.zpoif.exchangeit.util.api.TradingDataProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TrainingDataDispatcher extends Dispatcher {
    private final TradingDataProcessor tradingDataProcessor;

    @Autowired
    public TrainingDataDispatcher(TradingDataProcessor tradingDataProcessor) {
        this.tradingDataProcessor = tradingDataProcessor;
    }

    public ResponseEntity<?> dispatchBasicData(String symbol) {
        DispatcherData dispatcherData = new DispatcherData(TradingDataInputType.basic, symbol);
        GlobalQuote response = (GlobalQuote) dispatcherData.data;
        if (response.getSymbol() == null) {
            dispatcherData.errors.put("Symbol", "Symbol unknown.");
        }

        if (!dispatcherData.errors.isEmpty()) {
            return ResponseEntity.badRequest().body(dispatcherData.errors);
        }
        return ResponseEntity.ok((GlobalQuote) dispatcherData.data);
    }

    public ResponseEntity<?> dispatchBasicChartData(
            TradingDataInputType type, String symbol, String interval, String month
                                                   ) {
        return dispatchChartData(type, symbol, interval, month,
                                 TradingDataOutputField.TIME_SERIES_BASIC_FIELDS_TO_SERIALIZE
                                );
    }

    private ResponseEntity<?> dispatchChartData(
            TradingDataInputType type, String symbol, String interval, String month, String[] fields
                                               ) {
        DispatcherData dispatcherData = new DispatcherData(type, symbol, interval, month);
        if (type.equals(TradingDataInputType.basic)) {
            return ResponseEntity.badRequest().body(Map.of("type", "Wrong type."));
        }

        if (!dispatcherData.errors.isEmpty()) {
            return ResponseEntity.badRequest().body(dispatcherData.errors);
        }

        return tradingDataProcessor.handleChartEndpoint(
                (List<TimeSeriesEntry>) dispatcherData.data, SimpleBeanPropertyFilter.filterOutAllExcept(fields));
    }

    public ResponseEntity<?> dispatchVolumeChartData(
            TradingDataInputType type, String symbol, String interval, String month
                                                    ) {
        return dispatchChartData(type, symbol, interval, month,
                                 TradingDataOutputField.TIME_SERIES_VOLUME_FIELDS_TO_SERIALIZE
                                );
    }

    public ResponseEntity<?> dispatchAdvancedData(
            TradingDataInputType type, String symbol, String interval, String month
                                                 ) {
        DispatcherData dispatcherData = new DispatcherData(type, symbol, interval, month);

        if (!dispatcherData.errors.isEmpty()) {
            return ResponseEntity.badRequest().body(dispatcherData.errors);
        }
        return ResponseEntity.ok(TradingDataQuery.getAdvancedData((List<TimeSeriesEntry>) dispatcherData.data));
    }

    private class DispatcherData {
        Map<String, String> errors = new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();
        Endpoint endpoint;
        Object data;

        public DispatcherData(TradingDataInputType type, String symbol) {
            this(type, symbol, null, null);
        }

        public DispatcherData(TradingDataInputType type, String symbol, String interval, String month) {
            queryParams.put("symbol", symbol);
            queryParams.put("outputsize", "full");

            switch (type) {
                case basic -> endpoint = Endpoint.GLOBAL_QUOTE;
                case advanced -> {
                    endpoint = Endpoint.TIME_SERIES_INTRADAY;
                    queryParams.put("interval", interval);
                    queryParams.put("month", month);
                }
                case daily -> endpoint = Endpoint.TIME_SERIES_DAILY;
                case weekly -> endpoint = Endpoint.TIME_SERIES_WEEKLY;
                // monthly
                default -> endpoint = Endpoint.TIME_SERIES_MONTHLY;
            }

            if (endpoint == Endpoint.GLOBAL_QUOTE) {
                data = tradingDataProcessor.handleQuoteEndpoint(endpoint, queryParams, errors);
            } else {
                data = tradingDataProcessor.handleDataEndpoint(endpoint, queryParams, errors);
            }
        }
    }
}
