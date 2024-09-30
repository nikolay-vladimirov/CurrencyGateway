package vladimirov.nikolay.CurrencyGateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import vladimirov.nikolay.CurrencyGateway.entities.CallStatistics;
import vladimirov.nikolay.CurrencyGateway.exceptions.DuplicateRequestIdException;
import vladimirov.nikolay.CurrencyGateway.repositories.CallStatisticsRepo;

@Service
public class CallStatisticsService {

    private final CallStatisticsRepo callStatisticsRepo;

    @Autowired
    public CallStatisticsService(CallStatisticsRepo callStatisticsRepo) {
        this.callStatisticsRepo = callStatisticsRepo;
    }

    public CallStatistics insertServiceCall(CallStatistics callStatistics){
        boolean isDuplicate = callStatisticsRepo.existsById(callStatistics.getRequestId());
        if(isDuplicate){
            throw new DuplicateRequestIdException("Duplicate request id: " + callStatistics.getRequestId());
        }
        return callStatisticsRepo.save(callStatistics);
    }
}
