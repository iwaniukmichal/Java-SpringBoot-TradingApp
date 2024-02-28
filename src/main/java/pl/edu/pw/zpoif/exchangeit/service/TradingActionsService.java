package pl.edu.pw.zpoif.exchangeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.edu.pw.zpoif.exchangeit.dispatchers.TrainingDataDispatcher;
import pl.edu.pw.zpoif.exchangeit.exceptions.tradingActions.NegativeQuantityException;
import pl.edu.pw.zpoif.exchangeit.exceptions.tradingActions.UnknownSymbolException;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.GlobalQuote;
import pl.edu.pw.zpoif.exchangeit.model.user.UserAssets;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class TradingActionsService {

    private final DataBaseService dataBaseService;
    private final TrainingDataDispatcher trainingDataDispatcher;
    private final RegisterLoginService registerLoginService;

    @Autowired
    public TradingActionsService(TrainingDataDispatcher trainingDataDispatcher,
                                 DataBaseService dataBaseService,
                                 RegisterLoginService registerLoginService) {
        this.trainingDataDispatcher = trainingDataDispatcher;
        this.dataBaseService = dataBaseService;
        this.registerLoginService = registerLoginService;
    }

    public String processBuy(double quantity, String symbol, Model model) throws NegativeQuantityException, UnknownSymbolException {
        validateQuantity(quantity);
        ResponseEntity<?> response = trainingDataDispatcher.dispatchBasicData(symbol);

        if (response.getStatusCode().is2xxSuccessful()) {
            GlobalQuote info = (GlobalQuote) response.getBody();
            double sharePrice = info.getPrice();
            double totalUSD = quantity * sharePrice;

            Long userId = registerLoginService.getCurrentlyLoggedUserId();

            double userTradingCash = dataBaseService.getUserTradingRepository().getUserTradingCash(userId);

            if (userTradingCash >= totalUSD) {

                dataBaseService.getUserTradingRepository().updateUserTradingCash(userTradingCash - totalUSD, userId);

                dataBaseService.createAndSaveUserHistory(userId, symbol, LocalDateTime.now(),
                        totalUSD, sharePrice, quantity, "bought");

                Optional<UserAssets> userAssetsOptional = dataBaseService.getUserAssetsRepository().findBySymbol(symbol, userId);

                if (userAssetsOptional.isPresent()) {
                    UserAssets userAssets = userAssetsOptional.get();
                    double quantityNew = quantity + userAssets.getQuantity();
                    double totalUSDNew = quantityNew * sharePrice;
                    dataBaseService.getUserAssetsRepository()
                            .updateUserAssetsBuySell(quantityNew, totalUSDNew, sharePrice, symbol, userId);
                } else {
                    dataBaseService.createAndSaveUserAssets(userId, symbol, quantity, totalUSD, sharePrice);
                }

                updateUserTradingData(userId);

                model.addAttribute("quantity", quantity);
                model.addAttribute("symbol", symbol);
                return "buy_success";
            } else {
                return "buy_failed";
            }
        } else {
            throw new UnknownSymbolException("Unknown company symbol");
        }
    }

    public String processSell(Double quantity, String symbol, Model model) throws NegativeQuantityException, UnknownSymbolException {
        validateQuantity(quantity);
        ResponseEntity<?> response = trainingDataDispatcher.dispatchBasicData(symbol);

        if (response.getStatusCode().is2xxSuccessful()) {
            GlobalQuote info = (GlobalQuote) response.getBody();
            double sharePrice = info.getPrice();

            Long userId = registerLoginService.getCurrentlyLoggedUserId();

            Optional<UserAssets> userAssetsOptional = dataBaseService.getUserAssetsRepository().findBySymbol(symbol, userId);
            if (userAssetsOptional.isPresent()) {
                UserAssets userAssets = userAssetsOptional.get();

                if (quantity == null) {
                    quantity = userAssets.getQuantity();
                }

                double userAssetsQuantity = userAssets.getQuantity();
                if (userAssetsQuantity >= quantity) {
                    double totalUSD = quantity * sharePrice;

                    dataBaseService.createAndSaveUserHistory(userId, symbol, LocalDateTime.now(),
                            totalUSD, sharePrice, quantity, "sold");

                    double userTradingCash = dataBaseService.getUserTradingRepository().getUserTradingCash(userId);
                    dataBaseService.getUserTradingRepository().updateUserTradingCash(userTradingCash + totalUSD, userId);


                    double quantityNew = userAssetsQuantity - quantity;
                    if (quantityNew == 0) {
                        dataBaseService.getUserAssetsRepository().delete(userAssets);
                    } else {
                        double totalUSDNew = quantityNew * sharePrice;
                        dataBaseService.getUserAssetsRepository().updateUserAssetsBuySell(quantityNew, totalUSDNew, sharePrice, symbol, userId);
                    }

                    updateUserTradingData(userId);

                    model.addAttribute("quantity", quantity);
                    model.addAttribute("symbol", symbol);
                    return "sell_success";
                }
            }
            return "sell_failed";
        } else {
            throw new UnknownSymbolException("Unknown company symbol");
        }
    }

    private void updateUserTradingData(Long userId) {
        Double stockTradingNew = dataBaseService.getUserAssetsRepository().getTotalAssetsValue(userId);
        if (stockTradingNew == null) {
            stockTradingNew = 0.0;
        }
        double totalTradingNew = dataBaseService.getUserTradingRepository().getUserTradingCash(userId) + stockTradingNew;
        double profitTradingNew = totalTradingNew - dataBaseService.getUserTradingRepository().getUserTradingInitial(userId);
        dataBaseService.getUserTradingRepository().updateUserTradingData(profitTradingNew, stockTradingNew, totalTradingNew, userId);
    }

    private void validateQuantity(Double quantity) throws NegativeQuantityException {
        if (quantity <= 0) {
            throw new NegativeQuantityException("Quantity must be positive value");
        }
    }

}