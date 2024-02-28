package pl.edu.pw.zpoif.exchangeit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Configuration
public class AlphaVintageApiConfig {
    @Value("${environment.externalApiUrl:https://www.alphavantage.co/query}")
    private String url;
    @Value("${environment.externalCallTimeOut:10}")
    private Integer timeOut;
    @Value("${environment.externalApiKey:demo}")
    private String key;
    @Value("${environment.startDate:2000-01-01}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @Value("Thank you for using Alpha Vantage! Our standard API rate limit is 25 requests per day.")
    private String requestsExceeded;

    public String getUrl() {
        return url;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public String getKey() {
        return key;
    }

    public String getRequestsExceeded() {
        return requestsExceeded;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
