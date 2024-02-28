package pl.edu.pw.zpoif.exchangeit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pw.zpoif.exchangeit.models.ControllerTest;

import java.util.List;
import java.util.Optional;

public interface ControllerTestsRepository extends JpaRepository<ControllerTest, String> {
    @Query("SELECT t FROM ControllerTest t WHERE t.testId = ?1")
    Optional<ControllerTest> findByTestId(Long testId);

    @Query("SELECT t FROM ControllerTest t WHERE t.endpointName = ?1")
    List<ControllerTest> findByEndpointName(String endpointName);

    @Query("SELECT t FROM ControllerTest t")
    List<ControllerTest> getTests();
}
