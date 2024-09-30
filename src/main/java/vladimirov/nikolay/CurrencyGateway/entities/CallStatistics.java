package vladimirov.nikolay.CurrencyGateway.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import vladimirov.nikolay.CurrencyGateway.enums.Caller;

import java.time.LocalDateTime;

@Entity
public class CallStatistics {
    @Id
    private String requestId;
    private Caller caller;
    private String endClientId;
    private LocalDateTime time;

    public CallStatistics(String requestId, Caller caller, String endClientId, LocalDateTime time) {
        this.requestId = requestId;
        this.caller = caller;
        this.endClientId = endClientId;
        this.time = time;
    }

    public CallStatistics() {

    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Caller getCaller() {
        return caller;
    }

    public void setCaller(Caller caller) {
        this.caller = caller;
    }

    public String getEndClientId() {
        return endClientId;
    }

    public void setEndClientId(String endClientId) {
        this.endClientId = endClientId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
