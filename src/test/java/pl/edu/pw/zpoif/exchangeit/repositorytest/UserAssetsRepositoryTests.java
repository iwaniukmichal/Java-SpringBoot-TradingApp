package pl.edu.pw.zpoif.exchangeit.repositorytest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.zpoif.exchangeit.model.user.UserAssets;
import pl.edu.pw.zpoif.exchangeit.repository.UserAssetsRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserAssetsRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserAssetsRepository userAssetsRepository;

    @Test
    public void testSaveUserAssets() {
        UserAssets userAssets = new UserAssets();
        userAssets.setUserId(1L);
        userAssets.setSymbol("ibm");
        userAssets.setSharePriceUSD(2);
        userAssets.setQuantity(23);
        userAssets.setTotalUSD(42);

        UserAssets savedUserAssets = userAssetsRepository.save(userAssets);
        entityManager.flush();

        assertNotNull(savedUserAssets.getId());
        UserAssets existUserAssets = entityManager.find(UserAssets.class, savedUserAssets.getId());
        assertThat(userAssets.getUserId()).isEqualTo(existUserAssets.getUserId());
        assertThat(userAssets.getQuantity()).isEqualTo(existUserAssets.getQuantity());
        assertThat(userAssets.getSymbol()).isEqualTo(existUserAssets.getSymbol());
        assertThat(userAssets.getSharePriceUSD()).isEqualTo(existUserAssets.getSharePriceUSD());
        assertThat(userAssets.getTotalUSD()).isEqualTo(existUserAssets.getTotalUSD());
    }

    @Test
    public void testUpdateUserAssets() {
        UserAssets userAssets = new UserAssets();
        userAssets.setUserId(1L);
        userAssets.setSymbol("AAPL");
        userAssets.setQuantity(10.0);
        userAssets.setSharePriceUSD(150.0);
        userAssets.setTotalUSD(1500.0);
        userAssetsRepository.save(userAssets);

        userAssets.setQuantity(15.0);
        UserAssets updatedUserAssets = userAssetsRepository.save(userAssets);

        assertThat(updatedUserAssets.getQuantity()).isEqualTo(15.0);
    }

    @Test
    public void testDeleteUserAssets() {
        UserAssets userAssets = new UserAssets();
        userAssets.setUserId(1L);
        userAssets.setSymbol("AAPL");
        userAssets.setQuantity(10.0);
        userAssets.setSharePriceUSD(150.0);
        userAssets.setTotalUSD(1500.0);
        userAssetsRepository.save(userAssets);

        userAssetsRepository.deleteById(userAssets.getId());

        assertThat(userAssetsRepository.findById(userAssets.getId())).isEmpty();
    }

    @Test
    public void testFindAllByUserId() {
        UserAssets userAssets1 = new UserAssets();
        userAssets1.setId(1L);
        userAssets1.setUserId(1L);
        userAssets1.setSymbol("AAPL");
        userAssets1.setQuantity(10.0);
        userAssets1.setSharePriceUSD(150.0);
        userAssets1.setTotalUSD(1500.0);

        UserAssets userAssets2 = new UserAssets();
        userAssets2.setId(2L);
        userAssets2.setUserId(2L);
        userAssets2.setSymbol("GOOGL");
        userAssets2.setQuantity(5.0);
        userAssets2.setSharePriceUSD(300.0);
        userAssets2.setTotalUSD(1500.0);
        userAssetsRepository.saveAll(List.of(userAssets1, userAssets2));

        List<UserAssets> userAssetsList = userAssetsRepository.findAllByUserId(1L);

        assertThat(userAssetsList).hasSize(1);
        assertThat(userAssetsList.get(0).getUserId()).isEqualTo(1L);
    }

    @Test
    public void testFindBySymbol() {
        UserAssets userAssets = new UserAssets();
        userAssets.setUserId(1L);
        userAssets.setSymbol("AAPL");
        userAssets.setQuantity(10.0);
        userAssets.setSharePriceUSD(150.0);
        userAssets.setTotalUSD(1500.0);
        userAssetsRepository.save(userAssets);

        Optional<UserAssets> foundUserAssets = userAssetsRepository.findBySymbol("AAPL", 1L);

        assertThat(foundUserAssets).isPresent();
        assertThat(foundUserAssets.get().getSymbol()).isEqualTo("AAPL");
        assertThat(foundUserAssets.get().getUserId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    public void testUpdateUserAssetsBuySell() {
        UserAssets userAssets1 = new UserAssets();
        userAssets1.setUserId(1L);
        userAssets1.setSymbol("aapl");
        userAssets1.setQuantity(10.0);
        userAssets1.setSharePriceUSD(150.0);
        userAssets1.setTotalUSD(1500.0);
        userAssetsRepository.save(userAssets1);

        UserAssets userAssets2 = new UserAssets();
        userAssets2.setUserId(1L);
        userAssets2.setSymbol("ibm");
        userAssets2.setQuantity(10.0);
        userAssets2.setSharePriceUSD(150.0);
        userAssets2.setTotalUSD(1500.0);
        userAssetsRepository.save(userAssets2);

        userAssetsRepository.updateUserAssetsBuySell(15.0, 2000.0, 160.0, "aapl", 1L);

        assertThat(userAssetsRepository.getTotalAssetsValue(1L)).isEqualTo(3500.0);
    }

    @Test
    public void testGetTotalAssetsValue() {
        UserAssets userAssets1 = new UserAssets();
        userAssets1.setUserId(1L);
        userAssets1.setSymbol("AAPL");
        userAssets1.setQuantity(10.0);
        userAssets1.setSharePriceUSD(150.0);
        userAssets1.setTotalUSD(1500.0);

        UserAssets userAssets2 = new UserAssets();
        userAssets2.setUserId(1L);
        userAssets2.setSymbol("GOOGL");
        userAssets2.setQuantity(5.0);
        userAssets2.setSharePriceUSD(300.0);
        userAssets2.setTotalUSD(1500.0);
        userAssetsRepository.saveAll(List.of(userAssets1, userAssets2));

        Double totalAssetsValue = userAssetsRepository.getTotalAssetsValue(1L);

        assertThat(totalAssetsValue).isEqualTo(3000.0);
    }
}
