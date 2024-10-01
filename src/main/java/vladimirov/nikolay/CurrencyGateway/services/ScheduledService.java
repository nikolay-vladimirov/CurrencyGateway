package vladimirov.nikolay.CurrencyGateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vladimirov.nikolay.CurrencyGateway.gateways.FixerGateway;

@Service
public class ScheduledService {
    private final FixerGateway fixerGateway;
    private final BatchInformationService batchInformationService;

    @Autowired
    public ScheduledService(FixerGateway fixerGateway, BatchInformationService batchInformationService) {
        this.fixerGateway = fixerGateway;
        this.batchInformationService = batchInformationService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialDataFetch() {
        if(batchInformationService.isInitialInsert()) {
            fixerGateway.updateAllFixerRatesDefaultBase();
        }
    }

    @Scheduled(cron = "${fetch.fixer.data.cron}")
    public void updateFixerData(){
        fixerGateway.updateAllFixerRatesDefaultBase();
    }
}
