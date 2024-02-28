package pl.edu.pw.zpoif.exchangeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoif.exchangeit.model.user.UserAssets;
import pl.edu.pw.zpoif.exchangeit.model.user.UserHistory;
import pl.edu.pw.zpoif.exchangeit.model.user.UserTrading;
import pl.edu.pw.zpoif.exchangeit.repository.UserAssetsRepository;
import pl.edu.pw.zpoif.exchangeit.repository.UserHistoryRepository;
import pl.edu.pw.zpoif.exchangeit.repository.UserRepository;
import pl.edu.pw.zpoif.exchangeit.repository.UserTradingRepository;

import java.time.LocalDateTime;

@Service
public class DataBaseService {

    private final UserHistoryRepository userHistoryRepository;
    private final UserTradingRepository userTradingRepository;
    private final UserRepository userRepository;
    private final UserAssetsRepository userAssetsRepository;

    @Autowired
    public DataBaseService(UserHistoryRepository userHistoryRepository,
                           UserTradingRepository userTradingRepository,
                           UserRepository userRepository,
                           UserAssetsRepository userAssetsRepository) {
        this.userHistoryRepository = userHistoryRepository;
        this.userTradingRepository = userTradingRepository;
        this.userRepository = userRepository;
        this.userAssetsRepository = userAssetsRepository;
    }

    public void createAndSaveUserHistory(Long userId, String symbol, LocalDateTime date,
                                         double totalUSD, double sharePrice, double quantity, String boughtSold) {
        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(userId);
        userHistory.setSymbol(symbol);
        userHistory.setDate(date);
        userHistory.setTotalUSD(totalUSD);
        userHistory.setSharePriceUSD(sharePrice);
        userHistory.setQuantity(quantity);
        userHistory.setBoughtSold(boughtSold);
        userHistoryRepository.save(userHistory);
    }

    public void createAndSaveUserTrading(Long userId, double initialCash) {
        UserTrading userTrading = new UserTrading();
        userTrading.setUserId(userId);
        userTrading.setCashUSD(initialCash);
        userTrading.setInitialUSD(initialCash);
        userTrading.setProfitUSD(0);
        userTrading.setStockUSD(0);
        userTrading.setTotalUSD(initialCash);
        userTradingRepository.save(userTrading);
    }

    public void createAndSaveUserAssets(Long userId, String symbol, double quantity,
                                        double totalUSD, double sharePrice) {
        UserAssets userAssets = new UserAssets();
        userAssets.setUserId(userId);
        userAssets.setSymbol(symbol);
        userAssets.setQuantity(quantity);
        userAssets.setTotalUSD(totalUSD);
        userAssets.setSharePriceUSD(sharePrice);
        userAssetsRepository.save(userAssets);
    }

    public UserHistoryRepository getUserHistoryRepository() {
        return userHistoryRepository;
    }

    public UserTradingRepository getUserTradingRepository() {
        return userTradingRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public UserAssetsRepository getUserAssetsRepository() {
        return userAssetsRepository;
    }
}
