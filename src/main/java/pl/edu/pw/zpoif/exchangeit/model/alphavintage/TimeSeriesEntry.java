package pl.edu.pw.zpoif.exchangeit.model.alphavintage;

import com.fasterxml.jackson.annotation.*;
import pl.edu.pw.zpoif.exchangeit.config.Filter;

import java.time.LocalDateTime;
import java.util.Objects;


@JsonPropertyOrder(alphabetic = true)
@JsonFilter(Filter.ALPHA_VINTAGE_TIME_SERIES)
public class TimeSeriesEntry extends AlphaVintageObject {
    @JsonAlias("Symbol")
    @JsonProperty("Symbol")
    private String symbol;
    @JsonAlias("Date Time")
    @JsonProperty("Date Time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    @JsonAlias("Interval")
    @JsonProperty("Interval")
    private int interval;
    @JsonAlias({"1. open", "Open"})
    @JsonProperty("Open")
    private double open;
    @JsonAlias({"2. high", "High"})
    @JsonProperty("High")
    private double high;
    @JsonAlias({"3. low", "Low"})
    @JsonProperty("Low")
    private double low;
    @JsonAlias({"4. close", "Close"})
    @JsonProperty("Close")
    private double close;
    @JsonAlias({"5. volume", "Volume"})
    @JsonProperty("Volume")
    private int volume;
    @JsonAlias("Raise")
    @JsonProperty("Raise")
    private double raise;
    @JsonAlias("Raise (%)")
    @JsonProperty("Raise (%)")
    private double raisePercentage;
    @JsonAlias("Color")
    @JsonProperty("Color")
    private Color color;

    public TimeSeriesEntry calculate() {
        raise = close - open;
        raisePercentage = raise / open;

        if (raise > 0) {
            color = Color.green;
        } else if (raise < 0) {
            color = Color.red;
        } else {
            color = Color.gray;
        }

        return this;
    }

    public double getRaisePercentage() {
        return raisePercentage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, dateTime, interval);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSeriesEntry that = (TimeSeriesEntry) o;
        return interval == that.interval && Objects.equals(symbol, that.symbol) &&
                Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public String toString() {
        return "TimeSeriesEntry{" + "symbol='" + symbol + '\'' + ", dateTime=" + dateTime + ", interval=" + interval +
                ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume +
                ", raise=" + raise + ", raisePercentage=" + raisePercentage + ", color=" + color + '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getInterval() {
        return interval;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public int getVolume() {
        return volume;
    }

    public double getRaise() {
        return raise;
    }

    public Color getColor() {
        return color;
    }

    private enum Color {
        green, gray, red
    }
}
