package pl.edu.pw.zpoif.exchangeit.util.alphavintageapi.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

public abstract class JsonParser {
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    protected static final DateTimeFormatter DATE_TIME_FORMATTER_IN =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    protected static final DateTimeFormatter DATE_FORMATTER_IN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    protected static final DateTimeFormatter DATE_TIME_FORMATTER_OUT_DATE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    protected static Map<String, String> getMetaData(JsonNode root) {
        Map<String, String> metaData = new HashMap<>();
        Function<Map.Entry<String, JsonNode>, String> getVal =
                field -> field.getValue().toString().strip().replace("\"", "");

        JsonNode meta = root.path("Meta Data");

        Map.Entry<String, JsonNode> field;
        String key;
        for (Iterator<Map.Entry<String, JsonNode>> it = meta.fields(); it.hasNext(); ) {
            field = it.next();
            key = field.getKey().toLowerCase();

            if (key.endsWith("symbol")) {
                metaData.put("Symbol", getVal.apply(field));
            } else if (key.endsWith("time zone")) {
                metaData.put("Time Zone", getVal.apply(field));
            } else if (key.endsWith("interval")) {
                metaData.put("Interval", getVal.apply(field).replaceAll("[^0-9]", ""));
            }
        }

        return metaData;
    }

    protected static Map<String, String> getDateTime(String dateTime, ZoneId zoneId, String name) {
        Map<String, String> res = new HashMap<>();

        LocalDateTime lastRefreshedDateTime;
        try {
            lastRefreshedDateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER_IN.withZone(zoneId));
        } catch (DateTimeException e) {
            lastRefreshedDateTime = LocalDate.parse(dateTime, DATE_FORMATTER_IN.withZone(zoneId)).atTime(LocalTime.MAX);
        }

        res.put(name, lastRefreshedDateTime.format(DATE_TIME_FORMATTER_OUT_DATE));
        return res;
    }

    protected static ZoneId getZoneId(Map<String, String> metaData) {
        if (!metaData.containsKey("Time Zone")) {
            return DEFAULT_ZONE_ID;
        }

        String zone = metaData.get("Time Zone");
        try {
            return ZoneId.of(zone);
        } catch (DateTimeException e1) {
            try {
                return ZoneId.of(TimeZone.getTimeZone(zone).toZoneId().getId());
            } catch (DateTimeException e2) {
                return null;
            }
        }
    }
}
