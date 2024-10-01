package vladimirov.nikolay.CurrencyGateway.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladimirov.nikolay.CurrencyGateway.entities.BatchInformation;
import vladimirov.nikolay.CurrencyGateway.repositories.BatchInformationRepo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class BatchInformationService {

    private final BatchInformationRepo batchInformationRepo;
    private final Logger logger = LoggerFactory.getLogger(BatchInformationService.class);

    @Autowired
    public BatchInformationService(BatchInformationRepo batchInformationRepo) {
        this.batchInformationRepo = batchInformationRepo;
    }

    public List<BatchInformation> getMostRecentBatches(){
        return batchInformationRepo.getMostRecentBatches();
    }
    public List<BatchInformation> getBatchInformationForPeriod(Long period){
        LocalDateTime periodFromDate = LocalDateTime.now(ZoneId.of("UTC")).minusHours(period);
        logger.info("Getting batches from last {} hours", period);
        return batchInformationRepo.findBatchInformationByDateTimeAfter(periodFromDate);
    }
    public boolean isInitialInsert(){
        return batchInformationRepo.count()  == 0L;
    }
}
