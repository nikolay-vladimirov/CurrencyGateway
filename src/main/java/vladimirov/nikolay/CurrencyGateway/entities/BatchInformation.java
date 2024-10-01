package vladimirov.nikolay.CurrencyGateway.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class BatchInformation {
    @Id
    private String etag;
    private LocalDateTime dateTime;
    private String baseCurrency;

    @OneToMany(mappedBy = "batchInformation")
    private Set<Rate> rates;

    public BatchInformation() {
    }

    public BatchInformation(LocalDateTime dateTime, String baseCurrency, String etag) {
        this.dateTime = dateTime;
        this.baseCurrency = baseCurrency;
        this.etag = etag;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BatchInformation{");
        sb.append("dateTime=").append(dateTime);
        sb.append(", baseCurrency='").append(baseCurrency).append('\'');
        sb.append(", etag='").append(etag).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
