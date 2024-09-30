package vladimirov.nikolay.CurrencyGateway.DTOs;

public class GetCurrencyHistoryRequest {
    private String requestId;
    private Long timestamp;
    private String client;
    private String currency;
    private Long period;

    public GetCurrencyHistoryRequest(String requestId, Long timestamp, String client, String currency, Long period) {
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.client = client;
        this.currency = currency;
        this.period = period;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }
}
