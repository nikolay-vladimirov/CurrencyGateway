package vladimirov.nikolay.CurrencyGateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vladimirov.nikolay.CurrencyGateway.entities.CallStatistics;

@Repository
public interface CallStatisticsRepo extends JpaRepository<CallStatistics, String> {
}
