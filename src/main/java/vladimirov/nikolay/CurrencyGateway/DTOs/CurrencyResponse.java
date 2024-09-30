package vladimirov.nikolay.CurrencyGateway.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyResponse {
    private String base;
    private BigDecimal value;
    private LocalDateTime lastUpdated;

    public CurrencyResponse(String base, BigDecimal value, LocalDateTime lastUpdated) {
        this.base = base;
        this.value = value;
        this.lastUpdated = lastUpdated;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
