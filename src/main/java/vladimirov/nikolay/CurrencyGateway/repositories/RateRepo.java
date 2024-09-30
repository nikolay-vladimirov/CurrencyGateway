package vladimirov.nikolay.CurrencyGateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vladimirov.nikolay.CurrencyGateway.entities.Rate;

import java.util.Collection;
import java.util.List;

@Repository
public interface RateRepo extends JpaRepository<Rate, Long> {

    List<Rate> findByCurrencyAndBatchInformation_IdIn(String currency, Collection<Long> batchIds);
}
