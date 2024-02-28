package pl.edu.pw.zpoif.exchangeit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoif.exchangeit.constants.api.TradingDataInputType;
import pl.edu.pw.zpoif.exchangeit.dispatchers.TrainingDataDispatcher;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.GlobalQuote;

@RestController
@Tag(name = "Trading data API", description = "Endpoints responsible for serving real live stock exchange data.")
@RequestMapping("trading_data/")
public class TradingDataController extends Controller {
    private final TrainingDataDispatcher trainingDataDispatcher;

    @Autowired
    public TradingDataController(TrainingDataDispatcher trainingDataDispatcher) {
        this.trainingDataDispatcher = trainingDataDispatcher;
    }

    @Operation(summary = "Retrieves basic information about a global quote", description =
            "This operation allows clients to fetch basic information about a global quote. " +
                    "The returned data includes various details such as the open price, high price, low price, close " +
                    "price, etc. " +
                    "Clients can use this information to analyze market trends and make informed decisions.",
            responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(implementation = GlobalQuote.class), examples = {
                    @ExampleObject(summary = "Simple response", value = """
                            {
                              "Symbol": "IBM",
                              "Open": 162.97,
                              "High": 165.98,
                              "Low": 162.355,
                              "Price": 165.8,
                              "Volume": 4958261,
                              "Latest Trading Day": "2024-01-12",
                              "Previous Close": 162.16,
                              "Change": 3.64,
                              "Change (%)": 2.2447
                            }""")}))})
    @GetMapping(value = "/basic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBasicInfo(
            @Parameter(example = "ibm", description = "Company name") @RequestParam String symbol
                                         ) {
        return trainingDataDispatcher.dispatchBasicData(symbol);
    }

    @Operation(summary = "Retrieves data used to create basic chart.", description = "This chart allows user to " +
            "analise company stocks etc.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "array"), examples = {
                    @ExampleObject(summary = "Sample response", value = "[{\"Date Time\": \"2024-01-12 23:59:59\"," +
                            "\"Volume\": 17643392},{\"Date Time\": \"2024-01-05 23:59:59\",\"Volume\": " +
                            "14822074}]")}))})
    @GetMapping(value = "/{type}/basic_chart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBasicChartData(
            @Parameter(example = "weekly", schema = @Schema(implementation = TradingDataInputType.class),
                    description = "Time interval between each stock update. 'basic' is forbidden.") @PathVariable TradingDataInputType type,
            @Parameter(example = "ibm", description = "Company name") @RequestParam String symbol,
            @Parameter(example = "15min", description = "Time interval between each stock update. Will be considered " +
                    "only if type='advanced'.") @RequestParam(required = false, defaultValue = "5min") String interval,
            @Parameter(example = "2011-09", description = "Month to retrieve data from. Will be considered only if " +
                    "type='advanced'.") @RequestParam(required = false) String month
                                              ) {
        return trainingDataDispatcher.dispatchBasicChartData(type, symbol, interval, month);
    }

    @Operation(summary = "Retrieves data used to create volume chart.", description = "This chart allows user to " +
            "analise company volume over time.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "array"), examples = {
                    @ExampleObject(summary = "Sample response", value = """
                            [
                              {
                                "Date Time": "2024-01-12 23:59:59",
                                "Volume": 17643392
                              },
                              {
                                "Date Time": "2024-01-05 23:59:59",
                                "Volume": 14822074
                              }]""")}))})
    @GetMapping(value = "/{type}/volume_chart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVolumeChartData(
            @Parameter(example = "weekly", schema = @Schema(implementation = TradingDataInputType.class),
                    description = "Time interval between each stock update. 'basic' is forbidden.") @PathVariable TradingDataInputType type,
            @Parameter(example = "ibm", description = "Company name") @RequestParam String symbol,
            @Parameter(example = "15min", description = "Time interval between each stock update. Will be considered " +
                    "only if type='advanced'.") @RequestParam(required = false, defaultValue = "5min") String interval,
            @Parameter(example = "2011-09", description = "Month to retrieve data from. Will be considered only if " +
                    "type='advanced'.") @RequestParam(required = false) String month
                                               ) {
        return trainingDataDispatcher.dispatchVolumeChartData(type, symbol, interval, month);
    }

    @Operation(summary = "Retrieves advanced data about company.", description = "This endpoint allows user to " +
            "retrieve advanced financial statistics about company.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "array"), examples = {
                    @ExampleObject(summary = "Simple response", value = """
                            {
                              "RSI": "2502.2120437604003"
                            }""")}))})
    @GetMapping(value = "/{type}/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdvancedData(
            @Parameter(example = "weekly", schema = @Schema(implementation = TradingDataInputType.class),
                    description = "Time interval between each stock update. 'basic' is forbidden.") @PathVariable TradingDataInputType type,
            @Parameter(example = "ibm", description = "Company name") @RequestParam String symbol,
            @Parameter(example = "15min", description = "Time interval between each stock update. Will be considered " +
                    "only if type='advanced'.") @RequestParam(required = false, defaultValue = "5min") String interval,
            @Parameter(example = "2011-09", description = "Month to retrieve data from. Will be considered only if " +
                    "type='advanced'.") @RequestParam(required = false) String month
                                            ) {
        return trainingDataDispatcher.dispatchAdvancedData(type, symbol, interval, month);
    }
}
