package pl.edu.pw.zpoif.exchangeit.util.alphavintageapi.queries;

import pl.edu.pw.zpoif.exchangeit.model.alphavintage.TimeSeriesEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradingDataQuery extends Query {
    public static Map<String, String> getAdvancedData(List<TimeSeriesEntry> data) {
        Map<String, String> advancedData = new HashMap<>();
        advancedData.put("RSI", String.valueOf(getRSI(data)));
        return advancedData;
    }

    private static Double getRSI(List<TimeSeriesEntry> data) {
        double avgRaise = data.stream().mapToDouble(TimeSeriesEntry::getRaisePercentage)
                              .filter(raisePercentage -> raisePercentage > 0).average().orElse(Double.NaN);
        double avgDecrease = data.stream().mapToDouble(TimeSeriesEntry::getRaisePercentage)
                                 .filter(raisePercentage -> raisePercentage < 0).average().orElse(Double.NaN);

        if (Double.isNaN(avgRaise) || Double.isNaN(avgDecrease)) {
            return Double.NaN;
        }
        return 100 - 100 / (1 + (avgRaise / avgDecrease));
    }
}
