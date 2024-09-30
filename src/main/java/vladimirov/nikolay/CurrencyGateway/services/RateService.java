package vladimirov.nikolay.CurrencyGateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladimirov.nikolay.CurrencyGateway.DTOs.CurrencyResponse;
import vladimirov.nikolay.CurrencyGateway.entities.BatchInformation;
import vladimirov.nikolay.CurrencyGateway.entities.Rate;
import vladimirov.nikolay.CurrencyGateway.repositories.RateRepo;

import java.util.List;

@Service
public class RateService {

    private final RateRepo rateRepo;
    private final BatchInformationService batchInformationService;

    @Autowired
    public RateService(RateRepo rateRepo, BatchInformationService batchInformationService) {
        this.rateRepo = rateRepo;
        this.batchInformationService = batchInformationService;
    }

    public List<CurrencyResponse> getCurrencyRates(String currency){
        List<BatchInformation> batches = batchInformationService.getMostRecentBatches();
        List<Long> batchIds = batches.stream().map(BatchInformation::getId).toList();
        List<Rate> rates = rateRepo.findByCurrencyAndBatchInformation_IdIn(currency, batchIds);
        return rates.stream().map( rate -> new CurrencyResponse(rate.getBatchInformation().getBaseCurrency(), rate.getBaseValue(), rate.getBatchInformation().getDateTime())).toList();
    }
}
