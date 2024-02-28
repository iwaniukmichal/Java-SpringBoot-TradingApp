package pl.edu.pw.zpoif.exchangeit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoif.exchangeit.exceptions.tradingActions.NegativeQuantityException;
import pl.edu.pw.zpoif.exchangeit.exceptions.tradingActions.UnknownSymbolException;
import pl.edu.pw.zpoif.exchangeit.service.TradingActionsService;


@Controller
@RequestMapping("app/")
@Tag(name = "Trading Actions", description = "Operations responsible for buying and selling stocks")
public class TradingActionsController extends pl.edu.pw.zpoif.exchangeit.controller.Controller {

    TradingActionsService tradingActionsService;

    @Autowired
    public TradingActionsController(TradingActionsService tradingActionsService) {
        this.tradingActionsService = tradingActionsService;
    }

    @Operation(summary = "Operation for buying stocks.", description = "Allows current user to buy company stocks.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "html"), examples = {
                    @ExampleObject(summary = "Simple response", value = """
                            Successfully Bought!
                            100  ibm""")}))})
    @RequestMapping(value = "/buy", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, produces = "text/html")
    public String buyStockAction(
            @Parameter(example = "ibm", description = "Company name") @RequestParam String symbol,
            @Parameter(example = "100", description = "Amount of stocks to buy") @RequestParam double quantity,
            Model model) throws NegativeQuantityException, UnknownSymbolException {
        return tradingActionsService.processBuy(quantity, symbol, model);
    }

    @Operation(summary = "Operation for selling stocks.", description = "Allows current user to sell company stocks.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "html"), examples = {
                    @ExampleObject(summary = "Simple response", value = """
                            Successfully Sold!
                            100  ibm
                            """)}))})
    @RequestMapping(value = "/sell", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, produces = "text/html")
    public String sellStockAction(
            @Parameter(example = "ibm", description = "Company name") @RequestParam String symbol,
            @Parameter(example = "100", description = "Amount of stocks to sell. If parameter is not used it sells all user owned stocks of selected company") @RequestParam(required = false) Double quantity,
            Model model) throws NegativeQuantityException, UnknownSymbolException {
        return tradingActionsService.processSell(quantity, symbol, model);
    }
}
