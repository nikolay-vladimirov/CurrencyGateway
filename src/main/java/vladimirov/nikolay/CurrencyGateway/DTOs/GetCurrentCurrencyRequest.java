package vladimirov.nikolay.CurrencyGateway.DTOs;

import java.time.LocalDateTime;

public class GetCurrentCurrencyRequest {
    private String requestId;
    private Long timestamp;
    private String client;
    private String currency;

    public GetCurrentCurrencyRequest(String requestId, Long timestamp, String client, String currency) {
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.client = client;
        this.currency = currency;
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
}
