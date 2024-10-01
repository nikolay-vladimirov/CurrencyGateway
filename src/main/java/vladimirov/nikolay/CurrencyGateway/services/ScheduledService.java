package vladimirov.nikolay.CurrencyGateway.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(ScheduledService.class);

    @Autowired
    public ScheduledService(FixerGateway fixerGateway, BatchInformationService batchInformationService) {
        this.fixerGateway = fixerGateway;
        this.batchInformationService = batchInformationService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialDataFetch() {
        if(batchInformationService.isInitialInsert()) {
            logger.info("Initial api call");
            fixerGateway.updateAllFixerRatesDefaultBase();
        }
    }

    @Scheduled(cron = "${fetch.fixer.data.cron}")
    public void updateFixerData(){
        logger.info("Scheduled api call to fetch data");
        fixerGateway.updateAllFixerRatesDefaultBase();
    }
}
