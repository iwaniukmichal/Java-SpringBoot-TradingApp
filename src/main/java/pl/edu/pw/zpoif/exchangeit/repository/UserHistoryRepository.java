package pl.edu.pw.zpoif.exchangeit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.zpoif.exchangeit.model.user.UserHistory;

import java.util.List;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

    @Query("SELECT u FROM UserHistory u WHERE u.userId = ?1")
    List<UserHistory> findAllByUserId(Long userId);
}
