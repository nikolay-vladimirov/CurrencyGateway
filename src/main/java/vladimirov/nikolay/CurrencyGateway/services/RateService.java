package vladimirov.nikolay.CurrencyGateway.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(RateService.class);

    @Autowired
    public RateService(RateRepo rateRepo, BatchInformationService batchInformationService) {
        this.rateRepo = rateRepo;
        this.batchInformationService = batchInformationService;
    }

    public List<CurrencyResponse> getCurrentRates(String currency){
        List<BatchInformation> batches = batchInformationService.getMostRecentBatches();
        logger.info("Getting rates for batches: {}", batches);
        return getCurrencyRatesForBatches(currency, batches);
    }

    public List<CurrencyResponse> getCurrencyHistory(String currency, Long period){
        List<BatchInformation> batches = batchInformationService.getBatchInformationForPeriod(period);
        return getCurrencyRatesForBatches(currency, batches);
    }

    private List<CurrencyResponse> getCurrencyRatesForBatches(String currency, List<BatchInformation> batches){
        List<String> batchIds = batches.stream().map(BatchInformation::getEtag).toList();
        List<Rate> rates = rateRepo.findByCurrencyAndBatchInformation_EtagIn(currency, batchIds);
        logger.info("Mapping rates to response {}", rates);
        return rates.stream().map( rate -> new CurrencyResponse(rate.getBatchInformation().getBaseCurrency(), rate.getBaseValue(), rate.getBatchInformation().getDateTime())).toList();
    }
}
