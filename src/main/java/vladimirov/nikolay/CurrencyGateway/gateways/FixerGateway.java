package vladimirov.nikolay.CurrencyGateway.gateways;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.Set;

@Service
public class FixerGateway {

    @Value("${fixer.api.key}")
    private String apiKey;
    private final String API_URL = "https://data.fixer.io/api/latest?access_key=";
    private final BatchInformationRepo batchInformationRepo;
    private final RateRepo rateRepo;
    private final Logger logger = LoggerFactory.getLogger(FixerGateway.class);

    @Autowired
    public FixerGateway(BatchInformationRepo batchInformationRepo, RateRepo rateRepo) {
        this.batchInformationRepo = batchInformationRepo;
        this.rateRepo = rateRepo;
    }

    public void updateAllFixerRatesDefaultBase() {
        updateFixerRates(null, null);
    }

    public void updateFixerRates(String baseCurrency, List<String> targetCurrencies) {
        ResponseEntity<FixerResponse> fixerResponse = fetchFixerRates(baseCurrency, targetCurrencies);
        if (fixerResponse != null) {
            insertFixerRates(fixerResponse.getBody(), fixerResponse.getHeaders().getETag());
        }
    }

    private ResponseEntity<FixerResponse> fetchFixerRates(String baseCurrency, List<String> targetCurrencies) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        BatchInformation latestBatch = batchInformationRepo.findFirstByOrderByDateTimeDesc();
        if (latestBatch != null) {
            logger.info("Adding etag: {} to header", latestBatch.getEtag());
            headers.set("If-None-Match", latestBatch.getEtag());
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

        StringBuilder url = new StringBuilder(API_URL + apiKey);
        if (baseCurrency != null && !baseCurrency.isBlank()) {
            url.append("&base=").append(baseCurrency);
        }
        if (targetCurrencies != null && !targetCurrencies.isEmpty()) {
            url.append("&symbols=");
            for(String currency : targetCurrencies){
                url.append(currency).append(",");
            }
        }
        logger.info("Calling API with URL: {}", url);
        ResponseEntity<FixerResponse> response = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, FixerResponse.class);
        if (response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
            logger.info("Rates remain unchanged since last fetch");
            return null;
        }
        if (response.getBody() == null || !response.getBody().isSuccess()) {
            logger.error("Unable to fetch rates from API");
            throw new MissingRatesException("Unable to fetch rates from API");
        }
        return response;
    }

    private void insertFixerRates(FixerResponse fixerResponse, String etag) {
        Instant instant = Instant.ofEpochSecond(fixerResponse.getTimestamp());
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        Set<Rate> rateSet = new HashSet<>();
        BatchInformation batchInformation = new BatchInformation(dateTime, fixerResponse.getBase(), etag);
        fixerResponse.getRates().forEach((key, value) -> {
            Rate rate = new Rate(key, value);
            rate.setBatchInformation(batchInformation);
            rateSet.add(rate);
        });
        logger.info("Inserting rates from batch with etag: {}", batchInformation.getEtag());
        rateRepo.saveAll(rateSet);
        batchInformationRepo.save(batchInformation);
    }
}
