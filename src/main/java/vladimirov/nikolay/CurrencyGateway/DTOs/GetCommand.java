package vladimirov.nikolay.CurrencyGateway.DTOs;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class GetCommand {
    @JacksonXmlProperty(isAttribute = true)
    private String consumer;

    @JacksonXmlProperty
    private String currency;

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
