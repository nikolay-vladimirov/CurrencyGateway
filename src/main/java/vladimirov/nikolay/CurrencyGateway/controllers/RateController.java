package vladimirov.nikolay.CurrencyGateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import vladimirov.nikolay.CurrencyGateway.DTOs.*;
import vladimirov.nikolay.CurrencyGateway.entities.CallStatistics;
import vladimirov.nikolay.CurrencyGateway.enums.Caller;
import vladimirov.nikolay.CurrencyGateway.exceptions.UnhandledCommandException;
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

    @PostMapping(value = "/json_api/current", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CurrencyResponse> getCurrentCurrencyInfo(@RequestBody GetCurrentCurrencyRequest request){
        Instant instant = Instant.ofEpochMilli(request.getTimestamp());
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        callStatisticsService.insertServiceCall(new CallStatistics(request.getRequestId(), Caller.EXIT_SERVICE_2, request.getClient(), dateTime));
        return rateService.getCurrentRates(request.getCurrency());
    }

    @PostMapping(value = "/json_api/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CurrencyResponse> getCurrencyHistory(@RequestBody GetCurrencyHistoryRequest request){
        Instant instant = Instant.ofEpochMilli(request.getTimestamp());
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        callStatisticsService.insertServiceCall(new CallStatistics(request.getRequestId(), Caller.EXIT_SERVICE_2, request.getClient(), dateTime));
        return rateService.getCurrencyHistory(request.getCurrency(), request.getPeriod());
    }

    @PostMapping(value = "/xml_api/command", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public List<CurrencyResponse> handleCommand(@RequestBody Command command) throws UnhandledCommandException {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("UTC"));
        if(command.getGetCommand() != null){
            GetCommand getCommand = command.getGetCommand();
            callStatisticsService.insertServiceCall(new CallStatistics(command.getId(), Caller.EXIT_SERVICE_1, getCommand.getConsumer(), dateTime));
            return rateService.getCurrentRates(getCommand.getCurrency());
        }
        if(command.getHistoryCommand() != null){
            HistoryCommand historyCommand = command.getHistoryCommand();
            Long period = Long.parseLong(historyCommand.getPeriod());
            callStatisticsService.insertServiceCall(new CallStatistics(command.getId(), Caller.EXIT_SERVICE_1, historyCommand.getConsumer(), dateTime));
            return rateService.getCurrencyHistory(historyCommand.getCurrency(), period);
        }
        throw new UnhandledCommandException("The command you have chosen is currently unhandled");
    }

}
