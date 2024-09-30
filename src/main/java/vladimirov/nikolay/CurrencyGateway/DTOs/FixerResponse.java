package vladimirov.nikolay.CurrencyGateway.DTOs;

import java.math.BigDecimal;
import java.util.Map;

public class FixerResponse {
    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;

    public boolean isSuccess() {
        return success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }
}
