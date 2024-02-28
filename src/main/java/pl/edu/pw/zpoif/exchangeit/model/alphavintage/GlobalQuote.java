package pl.edu.pw.zpoif.exchangeit.model.alphavintage;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.edu.pw.zpoif.exchangeit.util.alphavintageapi.PercentageDeserializer;

import java.time.LocalDate;
import java.util.Objects;

public class GlobalQuote extends AlphaVintageObject {
    @JsonAlias({"01. symbol", "Symbol"})
    @JsonProperty("Symbol")
    private String symbol;
    @JsonAlias({"02. open", "Open"})
    @JsonProperty("Open")
    private double open;
    @JsonAlias({"03. high", "High"})
    @JsonProperty("High")
    private double high;
    @JsonAlias({"04. low", "Low"})
    @JsonProperty("Low")
    private double low;
    @JsonAlias({"05. price", "Price"})
    @JsonProperty("Price")
    private double price;
    @JsonAlias({"06. volume", "Volume"})
    @JsonProperty("Volume")
    private double volume;
    @JsonAlias({"07. latest trading day", "Latest Trading Day"})
    @JsonProperty("Latest Trading Day")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate latestTradingDay;
    @JsonAlias({"08. previous close", "Previous Close"})
    @JsonProperty("Previous Close")
    private double previousClose;
    @JsonAlias({"09. change", "Change"})
    @JsonProperty("Change")
    private double change;
    @JsonAlias({"10. change percent", "Change (%)"})
    @JsonProperty("Change (%)")
    @JsonDeserialize(using = PercentageDeserializer.class)
    private double changePercent;

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlobalQuote that = (GlobalQuote) o;
        return Objects.equals(symbol, that.symbol);
    }

    @Override
    public String toString() {
        return "GlobalQuote{" + "symbol='" + symbol + '\'' + ", open=" + open + ", high=" + high + ", low=" + low +
                ", price=" + price + ", volume=" + volume + ", latestTradingDay=" + latestTradingDay +
                ", previousClose=" + previousClose + ", change=" + change + ", changePercent=" + changePercent + '}';
    }

    public String getSymbol() {
        return symbol;
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

    public double getPrice() {
        return price;
    }

    public double getVolume() {
        return volume;
    }

    public LocalDate getLatestTradingDay() {
        return latestTradingDay;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public double getChange() {
        return change;
    }

    public double getChangePercent() {
        return changePercent;
    }
}
