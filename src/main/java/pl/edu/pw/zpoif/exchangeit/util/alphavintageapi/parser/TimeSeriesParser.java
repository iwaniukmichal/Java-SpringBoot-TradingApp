package pl.edu.pw.zpoif.exchangeit.util.alphavintageapi.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import pl.edu.pw.zpoif.exchangeit.constants.alphavintageapi.Endpoint;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.AlphaVintageObject;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.TimeSeriesEntry;

import java.time.ZoneId;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TimeSeriesParser extends JsonParser {
    public static List<AlphaVintageObject> parse(Endpoint endpoint, JsonNode root) {
        Map<String, String> metaData = getMetaData(root);

        return switch (endpoint) {
            case TIME_SERIES_INTRADAY, TIME_SERIES_DAILY, TIME_SERIES_WEEKLY, TIME_SERIES_MONTHLY ->
                    TimeSeriesParser.parseIntraday(root, metaData);
            default -> null;
        };
    }

    private static List<AlphaVintageObject> parseIntraday(JsonNode root, Map<String, String> metaData) {
        JsonNode timeSeries = getTimeSeriesNode(root);
        assert timeSeries != null;

        ZoneId zoneId = getZoneId(metaData);
        metaData.remove("Time Zone");

        List<Map<String, String>> entries = new LinkedList<>();

        Map<String, String> entry;
        String key;
        Map.Entry<String, JsonNode> field;
        for (Iterator<Map.Entry<String, JsonNode>> it = timeSeries.fields(); it.hasNext(); ) {
            field = it.next();
            key = field.getKey();

            entry = getDateTime(key, zoneId, "Date Time");
            entry.putAll(metaData);

            try {
                entry.putAll(OBJECT_MAPPER.readValue(field.getValue().toString(), Map.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            entries.add(entry);
        }

        List<AlphaVintageObject> timeSeriesEntries = new LinkedList<>();
        for (Map<String, String> map : entries) {
            timeSeriesEntries.add(OBJECT_MAPPER.convertValue(map, TimeSeriesEntry.class));
        }

        return timeSeriesEntries;
    }

    private static JsonNode getTimeSeriesNode(JsonNode root) {
        Map.Entry<String, JsonNode> field;
        for (Iterator<Map.Entry<String, JsonNode>> it = root.fields(); it.hasNext(); ) {
            field = it.next();

            if (field.getKey().strip().toLowerCase().contains("time series")) {
                return field.getValue();
            }
        }

        return null;
    }
}
