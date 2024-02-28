package pl.edu.pw.zpoif.exchangeit.repositorytest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import pl.edu.pw.zpoif.exchangeit.model.user.UserHistory;
import pl.edu.pw.zpoif.exchangeit.repository.UserHistoryRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserHistoryRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Test
    public void testSaveUserHistory() {
        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(1L);
        userHistory.setSymbol("ibm");
        userHistory.setSharePriceUSD(2);
        userHistory.setQuantity(23);
        userHistory.setTotalUSD(42);
        userHistory.setBoughtSold("bought");
        userHistory.setDate(LocalDateTime.now());

        UserHistory savedUserHistory = userHistoryRepository.save(userHistory);
        entityManager.flush();

        assertNotNull(savedUserHistory.getId());
        UserHistory existUserHistory = entityManager.find(UserHistory.class, savedUserHistory.getId());
        assertThat(userHistory.getUserId()).isEqualTo(existUserHistory.getUserId());
        assertThat(userHistory.getQuantity()).isEqualTo(existUserHistory.getQuantity());
        assertThat(userHistory.getSymbol()).isEqualTo(existUserHistory.getSymbol());
        assertThat(userHistory.getSharePriceUSD()).isEqualTo(existUserHistory.getSharePriceUSD());
        assertThat(userHistory.getTotalUSD()).isEqualTo(existUserHistory.getTotalUSD());
        assertThat(userHistory.getBoughtSold()).isEqualTo(existUserHistory.getBoughtSold());
        assertThat(userHistory.getDate()).isEqualTo(existUserHistory.getDate());
    }

    @Test
    public void testUpdateUserHistory() {
        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(1L);
        userHistory.setSymbol("AAPL");
        userHistory.setDate(LocalDateTime.now());
        userHistory.setSharePriceUSD(150.0);
        userHistory.setQuantity(10.0);
        userHistory.setTotalUSD(1500.0);
        userHistory.setBoughtSold("Bought");
        userHistoryRepository.save(userHistory);

        userHistory.setQuantity(15.0);
        UserHistory updatedUserHistory = userHistoryRepository.save(userHistory);

        assertThat(updatedUserHistory.getQuantity()).isEqualTo(15.0);
    }

    @Test
    public void testFindAllByUserId() {
        UserHistory userHistory1 = new UserHistory();
        userHistory1.setUserId(1L);
        userHistory1.setSymbol("AAPL");
        userHistory1.setDate(LocalDateTime.now());
        userHistory1.setSharePriceUSD(150.0);
        userHistory1.setQuantity(10.0);
        userHistory1.setTotalUSD(1500.0);
        userHistory1.setBoughtSold("Bought");
        userHistoryRepository.save(userHistory1);

        UserHistory userHistory2 = new UserHistory();
        userHistory2.setUserId(1L);
        userHistory2.setSymbol("GOOGL");
        userHistory2.setDate(LocalDateTime.now());
        userHistory2.setSharePriceUSD(300.0);
        userHistory2.setQuantity(5.0);
        userHistory2.setTotalUSD(1500.0);
        userHistory2.setBoughtSold("Sold");
        userHistoryRepository.save(userHistory2);
        userHistoryRepository.saveAll(List.of(userHistory1, userHistory2));

        List<UserHistory> userHistoryList = userHistoryRepository.findAllByUserId(1L);

        assertThat(userHistoryList).hasSize(2);
        assertThat(userHistoryList.get(0).getUserId()).isEqualTo(1L);
        assertThat(userHistoryList.get(0).getSymbol()).isEqualTo("AAPL");
    }
}
