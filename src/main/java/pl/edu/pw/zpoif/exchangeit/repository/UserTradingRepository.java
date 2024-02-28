package pl.edu.pw.zpoif.exchangeit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.zpoif.exchangeit.model.user.UserTrading;

@Repository
public interface UserTradingRepository extends JpaRepository<UserTrading, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE UserTrading u SET u.cashUSD = ?1 WHERE u.userId = ?2")
    void updateUserTradingCash(double cashUSD, Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE UserTrading u SET u.profitUSD = ?1, u.stockUSD = ?2, u.totalUSD = ?3 WHERE u.userId = ?4")
    void updateUserTradingData(double profitUSD, double stockUSD, double totalUSD, Long userId);

    @Query("SELECT u.cashUSD FROM UserTrading u WHERE u.userId = ?1")
    Double getUserTradingCash(Long userId);

    @Query("SELECT u.initialUSD FROM UserTrading u WHERE u.userId = ?1")
    Double getUserTradingInitial(Long userId);

    @Query("SELECT u.totalUSD FROM UserTrading u WHERE u.userId = ?1")
    Double getUserTradingTotal(Long userId);
}
