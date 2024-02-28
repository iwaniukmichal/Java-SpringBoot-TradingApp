package pl.edu.pw.zpoif.exchangeit.constants.api;

public interface TradingDataOutputField {
    String[] TIME_SERIES_BASIC_FIELDS_TO_SERIALIZE =
            {"Date Time", "Open", "Close", "High", "Low", "Raise", "Raise (%)", "Color"};
    String[] TIME_SERIES_VOLUME_FIELDS_TO_SERIALIZE =
            {"Date Time", "Volume"};
}
