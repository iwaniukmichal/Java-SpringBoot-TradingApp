package pl.edu.pw.zpoif.exchangeit.util.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.edu.pw.zpoif.exchangeit.model.user.UserAssets;
import pl.edu.pw.zpoif.exchangeit.model.user.UserHistory;
import pl.edu.pw.zpoif.exchangeit.model.user.UserTrading;
import pl.edu.pw.zpoif.exchangeit.service.DataBaseService;
import pl.edu.pw.zpoif.exchangeit.service.RegisterLoginService;

import java.util.List;
import java.util.Optional;

@Component
public class TradingUserProcessor extends Processor{

    private final RegisterLoginService registerLoginService;
    private final DataBaseService dataBaseService;

    @Autowired
    public TradingUserProcessor(DataBaseService dataBaseService, RegisterLoginService registerLoginService) {
        this.dataBaseService = dataBaseService;
        this.registerLoginService = registerLoginService;
    }

    public ResponseEntity<?> handleAssetsDataEndpoint() {
        List<UserAssets> userAssetsList = dataBaseService.getUserAssetsRepository()
                .findAllByUserId(registerLoginService.getCurrentlyLoggedUserId());
        return writeDataToJson(userAssetsList);
    }

    public ResponseEntity<?> handleTradingDataEndpoint() {
        Optional<UserTrading> userTrading = dataBaseService.getUserTradingRepository()
                .findById(registerLoginService.getCurrentlyLoggedUserId());
        return writeDataToJson(userTrading.get());
    }

    public ResponseEntity<?> handleHistoryDataEndpoint() {
        List<UserHistory> userHistoryList = dataBaseService.getUserHistoryRepository()
                .findAllByUserId(registerLoginService.getCurrentlyLoggedUserId());
        return writeDataToJson(userHistoryList);
    }

    private <T> ResponseEntity<?> writeDataToJson(T data){
        try {
            String json = OBJECT_MAPPER.writeValueAsString(data);
            return ResponseEntity.ok().body(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
