package vladimirov.nikolay.CurrencyGateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vladimirov.nikolay.CurrencyGateway.DTOs.CurrencyResponse;
import vladimirov.nikolay.CurrencyGateway.DTOs.GetCurrentCurrencyRequest;
import vladimirov.nikolay.CurrencyGateway.entities.CallStatistics;
import vladimirov.nikolay.CurrencyGateway.enums.Caller;
import vladimirov.nikolay.CurrencyGateway.gateways.FixerGateway;
import vladimirov.nikolay.CurrencyGateway.services.CallStatisticsService;
import vladimirov.nikolay.CurrencyGateway.services.RateService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("v1/currency_gateway")
public class RateController {
    private final FixerGateway fixerGateway;
    private final CallStatisticsService callStatisticsService;
    private final RateService rateService;

    @Autowired
    public RateController(FixerGateway fixerGateway, CallStatisticsService callStatisticsService, RateService rateService) {
        this.fixerGateway = fixerGateway;
        this.callStatisticsService = callStatisticsService;
        this.rateService = rateService;
    }

    //TO DO delete
    @GetMapping("")
    public void test(){
        fixerGateway.updateFixerRates(null, null);
    }

    @PostMapping("/json_api/current")
    public List<CurrencyResponse> getCurrentCurrencyInfo(@RequestBody GetCurrentCurrencyRequest request){
        Instant instant = Instant.ofEpochMilli(request.getTimestamp());
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        callStatisticsService.insertServiceCall(new CallStatistics(request.getRequestId(), Caller.EXIT_SERVICE_2, request.getClient(), dateTime));
        return rateService.getCurrencyRates(request.getCurrency());
    }

}
