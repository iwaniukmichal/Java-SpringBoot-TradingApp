package pl.edu.pw.zpoif.exchangeit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoif.exchangeit.model.user.UserTrading;
import pl.edu.pw.zpoif.exchangeit.util.api.TradingUserProcessor;

@RestController
@RequestMapping("app/")
@Tag(name = "User Trading Data", description = "Endpoints responsible for displaying user data")
public class TradingUserDataController {

    TradingUserProcessor tradingUserProcessor;

    @Autowired
    public TradingUserDataController(TradingUserProcessor tradingUserProcessor) {
        this.tradingUserProcessor = tradingUserProcessor;
    }

    @Operation(summary = "Retrieves data used to show current user trading data.", description = "This data contains current user basic info like owned money, value of owned stocks, profit etc.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(implementation = UserTrading.class), examples = {
                    @ExampleObject(summary = "Simple response", value = "{\"userId\":1,\"cashUSD\":100046.4,\"stockUSD\":0.0,\"totalUSD\":100046.4,\"initialUSD\":100000.0,\"profitUSD\":46.39999999999418}")}))})
    @GetMapping(value = "/display_trading_data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTradingData() {
        return tradingUserProcessor.handleTradingDataEndpoint();
    }

    @Operation(summary = "Retrieves data used to show current user owned assets.", description = "This data contains current user owned assets information like quantity, symbol of company, total value of stocks etc.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "array"), examples = {
                    @ExampleObject(summary = "Simple response", value = "[{\"id\":2,\"userId\":1,\"symbol\":\"ibm\",\"quantity\":30.0,\"sharePriceUSD\":166.96,\"totalUSD\":5008.8}," +
                            "{\"id\":3,\"userId\":1,\"symbol\":\"aapl\",\"quantity\":10.0,\"sharePriceUSD\":183.63,\"totalUSD\":1836.3}]")}))})
    @GetMapping(value = "/display_assets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAssetsData() {
        return tradingUserProcessor.handleAssetsDataEndpoint();
    }

    @Operation(summary = "Retrieves data used to show current user trading history.", description = "This data contains current user owned trading history containing bought and sold stocks, date time, value of bought and sold stocks etc.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "array"), examples = {
                    @ExampleObject(summary = "Simple response", value = "[{\"id\":13,\"userId\":5,\"symbol\":\"ibm\",\"date\":[2024,1,17,11,50,38,786049000],\"sharePriceUSD\":166.96,\"quantity\":10.0,\"totalUSD\":1669.6000000000001,\"boughtSold\":\"bought\"}," +
                            "{\"id\":14,\"userId\":5,\"symbol\":\"ibm\",\"date\":[2024,1,17,11,50,43,140486000],\"sharePriceUSD\":166.96,\"quantity\":120.0,\"totalUSD\":20035.2,\"boughtSold\":\"bought\"}," +
                            "{\"id\":15,\"userId\":5,\"symbol\":\"aapl\",\"date\":[2024,1,17,11,51,24,250085000],\"sharePriceUSD\":183.63,\"quantity\":20.0,\"totalUSD\":3672.6,\"boughtSold\":\"bought\"}," +
                            "{\"id\":16,\"userId\":5,\"symbol\":\"aapl\",\"date\":[2024,1,17,11,51,32,342101000],\"sharePriceUSD\":183.63,\"quantity\":10.0,\"totalUSD\":1836.3,\"boughtSold\":\"sold\"},{\"id\":17,\"userId\":5,\"symbol\":\"ibm\",\"date\":[2024,1,17,11,51,37,225258000],\"sharePriceUSD\":166.96,\"quantity\":10.0,\"totalUSD\":1669.6000000000001,\"boughtSold\":\"bought\"}]")}))})
    @GetMapping(value = "/display_history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHistoryData() {
        return tradingUserProcessor.handleHistoryDataEndpoint();
    }

}
