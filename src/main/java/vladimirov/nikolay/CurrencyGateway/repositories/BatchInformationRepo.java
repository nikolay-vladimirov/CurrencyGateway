package vladimirov.nikolay.CurrencyGateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vladimirov.nikolay.CurrencyGateway.entities.BatchInformation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BatchInformationRepo extends JpaRepository<BatchInformation, Long> {

    @Query(value = """
            WITH most_recent_updates AS (
            SELECT
                    id,
                    base_currency ,
                    date_time,
                    ROW_NUMBER() OVER (PARTITION BY base_currency ORDER BY date_time DESC) AS rn
                FROM
                    batch_information bi\s
                    )
            select id, date_time, base_currency from most_recent_updates where rn = 1""", nativeQuery = true)
    List<BatchInformation> getMostRecentBatches();

    List<BatchInformation> findBatchInformationByDateTimeAfter(LocalDateTime dateTime);

    BatchInformation findFirstByOrderByDateTimeDesc();
}
