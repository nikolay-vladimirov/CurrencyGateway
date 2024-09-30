package vladimirov.nikolay.CurrencyGateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladimirov.nikolay.CurrencyGateway.entities.BatchInformation;
import vladimirov.nikolay.CurrencyGateway.repositories.BatchInformationRepo;

import java.util.List;

@Service
public class BatchInformationService {

    private final BatchInformationRepo batchInformationRepo;

    @Autowired
    public BatchInformationService(BatchInformationRepo batchInformationRepo) {
        this.batchInformationRepo = batchInformationRepo;
    }

    public List<BatchInformation> getMostRecentBatches(){
        return batchInformationRepo.getMostRecentBatches();
    }
}
