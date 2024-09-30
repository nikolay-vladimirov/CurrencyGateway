package vladimirov.nikolay.CurrencyGateway.DTOs;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class HistoryCommand {
    @JacksonXmlProperty(isAttribute = true)
    private String consumer;

    @JacksonXmlProperty(isAttribute = true)
    private String currency;

    @JacksonXmlProperty(isAttribute = true)
    private String period;

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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

}
