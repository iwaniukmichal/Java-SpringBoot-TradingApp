package pl.edu.pw.zpoif.exchangeit.repositorytest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.zpoif.exchangeit.model.user.UserTrading;
import pl.edu.pw.zpoif.exchangeit.repository.UserTradingRepository;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserTradingRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserTradingRepository userTradingRepository;

    @Test
    public void testSaveUserTrading() {
        UserTrading userTrading = new UserTrading();
        userTrading.setUserId(1L);
        userTrading.setStockUSD(12);
        userTrading.setCashUSD(1241);
        userTrading.setInitialUSD(2);
        userTrading.setProfitUSD(23);
        userTrading.setTotalUSD(42);

        UserTrading savedUserTrading = userTradingRepository.save(userTrading);
        entityManager.flush();

        UserTrading existUserTrading = entityManager.find(UserTrading.class, savedUserTrading.getUserId());
        assertThat(userTrading.getStockUSD()).isEqualTo(existUserTrading.getStockUSD());
        assertThat(userTrading.getCashUSD()).isEqualTo(existUserTrading.getCashUSD());
        assertThat(userTrading.getInitialUSD()).isEqualTo(existUserTrading.getInitialUSD());
        assertThat(userTrading.getProfitUSD()).isEqualTo(existUserTrading.getProfitUSD());
        assertThat(userTrading.getTotalUSD()).isEqualTo(existUserTrading.getTotalUSD());
    }

    @Test
    public void testUpdateUserTrading() {
        UserTrading userTrading = new UserTrading();
        userTrading.setUserId(1L);
        userTrading.setCashUSD(1000.0);
        userTrading.setStockUSD(500.0);
        userTrading.setInitialUSD(1200.0);
        userTradingRepository.save(userTrading);

        userTrading.setCashUSD(1200.0);
        UserTrading updatedUserTrading = userTradingRepository.save(userTrading);

        assertThat(updatedUserTrading.getCashUSD()).isEqualTo(1200.0);
    }


    @Test
    public void testDeleteUserTrading() {
        UserTrading userTrading = new UserTrading();
        userTrading.setUserId(1L);
        userTrading.setCashUSD(1000.0);
        userTrading.setStockUSD(500.0);
        userTrading.setInitialUSD(1200.0);
        UserTrading savedUserTrading = userTradingRepository.save(userTrading);

        userTradingRepository.deleteById(savedUserTrading.getUserId());

        assertThat(userTradingRepository.findById(savedUserTrading.getUserId())).isEmpty();
    }

    @Test
    @Transactional
    public void testUpdateUserTradingCash() {
        UserTrading userTrading = new UserTrading();
        userTrading.setUserId(1L);
        userTrading.setCashUSD(1000.0);
        userTradingRepository.save(userTrading);

        userTradingRepository.updateUserTradingCash(1500.0, 1L);

        Double updatedCashUSD = userTradingRepository.getUserTradingCash(1L);
        assertThat(updatedCashUSD).isEqualTo(1500.0);
    }

    @Test
    @Transactional
    public void testUpdateUserTradingData() {
        UserTrading userTrading = new UserTrading();
        userTrading.setUserId(1L);
        userTrading.setProfitUSD(200.0);
        userTrading.setStockUSD(500.0);
        userTrading.setTotalUSD(1500.0);
        userTradingRepository.save(userTrading);

        userTradingRepository.updateUserTradingData(300.0, 700.0, 2000.0, 1L);

        assertThat(userTradingRepository.getUserTradingTotal(1L)).isEqualTo(2000.0);
    }

    @Test
    @Transactional
    public void testGetUserTradingCash() {
        UserTrading userTrading = new UserTrading();
        userTrading.setUserId(1L);
        userTrading.setCashUSD(1200.0);
        userTradingRepository.save(userTrading);

        Double cashUSD = userTradingRepository.getUserTradingCash(1L);

        assertThat(cashUSD).isEqualTo(1200.0);
    }

    @Test
    @Transactional
    public void testGetUserTradingInitial() {
        UserTrading userTrading = new UserTrading();
        userTrading.setUserId(1L);
        userTrading.setInitialUSD(1000.0);
        userTradingRepository.save(userTrading);

        Double initialUSD = userTradingRepository.getUserTradingInitial(1L);

        assertThat(initialUSD).isEqualTo(1000.0);
    }
}
