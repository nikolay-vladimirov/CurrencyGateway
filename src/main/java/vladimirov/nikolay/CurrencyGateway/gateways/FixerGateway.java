package vladimirov.nikolay.CurrencyGateway.gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vladimirov.nikolay.CurrencyGateway.DTOs.FixerResponse;
import vladimirov.nikolay.CurrencyGateway.entities.BatchInformation;
import vladimirov.nikolay.CurrencyGateway.entities.Rate;
import vladimirov.nikolay.CurrencyGateway.repositories.BatchInformationRepo;
import vladimirov.nikolay.CurrencyGateway.repositories.RateRepo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Service
public class FixerGateway {

    @Value("${fixer.api.key}")
    private String apiKey;
    private final String API_URL = "https://data.fixer.io/api/latest?access_key=";
    private final BatchInformationRepo batchInformationRepo;
    private final RateRepo rateRepo;

    @Autowired
    public FixerGateway(BatchInformationRepo batchInformationRepo, RateRepo rateRepo) {
        this.batchInformationRepo = batchInformationRepo;
        this.rateRepo = rateRepo;
    }

    public void updateFixerRates(String baseCurrency, String targetCurrencies){
        FixerResponse fixerResponse = fetchFixerRates(baseCurrency, targetCurrencies);
        insertFixerRates(fixerResponse);
    }
    private FixerResponse fetchFixerRates(String baseCurrency, String targetCurrencies){
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder stringBuilder = new StringBuilder(API_URL + apiKey);
        if(baseCurrency != null && !baseCurrency.isBlank()){
            stringBuilder.append("&base=").append(baseCurrency);
        }
        if(targetCurrencies != null && !targetCurrencies.isBlank()){
            stringBuilder.append("&symbols=").append(targetCurrencies);
        }
       return restTemplate.getForObject(stringBuilder.toString(), FixerResponse.class);
    }

    private void insertFixerRates(FixerResponse fixerResponse){
        Instant instant = Instant.ofEpochSecond(fixerResponse.getTimestamp());
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        Set<Rate> rateSet = new HashSet<>();
        BatchInformation batchInformation = new BatchInformation(dateTime, fixerResponse.getBase());
        fixerResponse.getRates().forEach((key, value) -> {
            Rate rate = new Rate(key, value);
            rate.setBatchInformation(batchInformation);
            rateSet.add(rate);
        });
        rateRepo.saveAll(rateSet);
        batchInformationRepo.save(batchInformation);
    }
}
