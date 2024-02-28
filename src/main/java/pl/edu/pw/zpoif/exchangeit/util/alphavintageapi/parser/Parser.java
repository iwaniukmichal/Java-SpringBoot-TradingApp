package pl.edu.pw.zpoif.exchangeit.util.alphavintageapi.parser;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoif.exchangeit.config.AlphaVintageApiConfig;
import pl.edu.pw.zpoif.exchangeit.constants.alphavintageapi.Endpoint;
import pl.edu.pw.zpoif.exchangeit.constants.alphavintageapi.Interval;
import pl.edu.pw.zpoif.exchangeit.exceptions.AlphaVintageAPI.RequestsNumExceededException;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.AlphaVintageObject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class Parser {
    private final AlphaVintageApiConfig config;

    @Autowired
    public Parser(AlphaVintageApiConfig config) {
        this.config = config;
    }

    public List<AlphaVintageObject> parse(Endpoint endpoint, JsonNode root) {
        if (root.has("Information") &&
                root.findValue("Information").toString().contains(config.getRequestsExceeded())) {
            throw new RequestsNumExceededException("Number of calls to AlphaVintageAPI has been exceeded for today.");
        }

        return switch (endpoint) {
            case TIME_SERIES_INTRADAY, TIME_SERIES_DAILY, TIME_SERIES_WEEKLY, TIME_SERIES_MONTHLY->
                    TimeSeriesParser.parse(endpoint, root);
            case GLOBAL_QUOTE -> GlobalQuoteParser.parse(endpoint, root);
        };
    }

    public Map<String, String> validateQueryParams(Map<String, String> queryParams) {
        Map<String, String> errorMap = new HashMap<>();

        Map.Entry<String, String> entry;
        String key;
        Iterator<Map.Entry<String, String>> iterator = queryParams.entrySet().iterator();
        while (iterator.hasNext()) {
            entry = iterator.next();

            if (entry.getValue() == null) {
                iterator.remove();
                continue;
            }

            key = entry.getKey();
            switch (key) {
                case "interval" -> {
                    if (!Interval.isCorrect(queryParams.get(key))) {
                        errorMap.put(key, "Wrong interval value: " + queryParams.get(key) + ", should be one of " +
                                Interval.concatenate());
                    }
                }
                case "month" -> {
                    if (queryParams.get(key) != null) {
                        try {
                            LocalDate yearMonth = YearMonth.parse(queryParams.get(key)).atDay(1);
                            if (yearMonth.isBefore(config.getStartDate())) {
                                errorMap.put(
                                        key, "Wrong month value: " + queryParams.get(key) + ", should be after " +
                                                config.getStartDate());
                            }
                        } catch (Exception e) {
                            errorMap.put(key, "Wrong month value: " + queryParams.get(key) + ", should be after " +
                                    config.getStartDate() + " in format rrrr-mm");
                        }
                    }
                }
            }
        }

        return !errorMap.isEmpty() ? errorMap : null;
    }
}
