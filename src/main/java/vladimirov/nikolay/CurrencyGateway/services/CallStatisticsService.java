package vladimirov.nikolay.CurrencyGateway.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladimirov.nikolay.CurrencyGateway.entities.CallStatistics;
import vladimirov.nikolay.CurrencyGateway.exceptions.DuplicateRequestIdException;
import vladimirov.nikolay.CurrencyGateway.repositories.CallStatisticsRepo;

@Service
public class CallStatisticsService {

    private final CallStatisticsRepo callStatisticsRepo;
    private final Logger logger = LoggerFactory.getLogger(CallStatisticsService.class);

    @Autowired
    public CallStatisticsService(CallStatisticsRepo callStatisticsRepo) {
        this.callStatisticsRepo = callStatisticsRepo;
    }

    public void insertServiceCall(CallStatistics callStatistics){
        boolean isDuplicate = callStatisticsRepo.existsById(callStatistics.getRequestId());
        if(isDuplicate){
            logger.error("Duplicate request id: {}", callStatistics.getRequestId());
            throw new DuplicateRequestIdException("Duplicate request id: " + callStatistics.getRequestId());
        }
        callStatisticsRepo.save(callStatistics);
    }
}
