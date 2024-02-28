package pl.edu.pw.zpoif.exchangeit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.zpoif.exchangeit.model.user.UserAssets;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAssetsRepository extends JpaRepository<UserAssets, Long> {

    @Query("SELECT u FROM UserAssets u WHERE u.symbol = ?1 AND u.userId = ?2")
    Optional<UserAssets> findBySymbol(String symbol, Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE UserAssets u SET u.quantity = ?1, u.totalUSD = ?2, u.sharePriceUSD = ?3 WHERE  u.symbol = ?4 AND u.userId = ?5")
    void updateUserAssetsBuySell(double quantity, double totalUSD, double sharePriceUSD, String symbol, Long userId);

    @Query("SELECT SUM(u.totalUSD) FROM UserAssets u WHERE u.userId = ?1")
    Double getTotalAssetsValue(Long userId);

    @Query("SELECT u FROM UserAssets u WHERE u.userId = ?1")
    List<UserAssets> findAllByUserId(Long userId);
}
