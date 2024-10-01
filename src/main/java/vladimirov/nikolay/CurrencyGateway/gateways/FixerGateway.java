package vladimirov.nikolay.CurrencyGateway.gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vladimirov.nikolay.CurrencyGateway.DTOs.FixerResponse;
import vladimirov.nikolay.CurrencyGateway.entities.BatchInformation;
import vladimirov.nikolay.CurrencyGateway.entities.Rate;
import vladimirov.nikolay.CurrencyGateway.exceptions.MissingRatesException;
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

    public void updateAllFixerRatesDefaultBase(){
        updateFixerRates(null, null);
    }
    public void updateFixerRates(String baseCurrency, String targetCurrencies){
        ResponseEntity<FixerResponse> fixerResponse = fetchFixerRates(baseCurrency, targetCurrencies);
        if (fixerResponse != null) {
            insertFixerRates(fixerResponse.getBody(), fixerResponse.getHeaders().getETag());
        }
    }
    private ResponseEntity<FixerResponse> fetchFixerRates(String baseCurrency, String targetCurrencies){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        BatchInformation latestBatch = batchInformationRepo.findFirstByOrderByDateTimeDesc();
        if(latestBatch != null){
            headers.set("If-None-Match", latestBatch.getEtag());
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

        StringBuilder url = new StringBuilder(API_URL + apiKey);
        if(baseCurrency != null && !baseCurrency.isBlank()){
            url.append("&base=").append(baseCurrency);
        }
        if(targetCurrencies != null && !targetCurrencies.isBlank()){
            url.append("&symbols=").append(targetCurrencies);
        }
        ResponseEntity<FixerResponse> response = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, FixerResponse.class);
        if(response.getStatusCode() == HttpStatus.NOT_MODIFIED){
            return null;
        }
        if(response.getBody() == null || !response.getBody().isSuccess()){
            throw new MissingRatesException("Unable to fetch rates from API");
        }
        return response;
    }

    private void insertFixerRates(FixerResponse fixerResponse, String etag){
        Instant instant = Instant.ofEpochSecond(fixerResponse.getTimestamp());
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        Set<Rate> rateSet = new HashSet<>();
        BatchInformation batchInformation = new BatchInformation(dateTime, fixerResponse.getBase(), etag);
        fixerResponse.getRates().forEach((key, value) -> {
            Rate rate = new Rate(key, value);
            rate.setBatchInformation(batchInformation);
            rateSet.add(rate);
        });
        rateRepo.saveAll(rateSet);
        batchInformationRepo.save(batchInformation);
    }
}
