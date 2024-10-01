package vladimirov.nikolay.CurrencyGateway.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class BatchInformation {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime dateTime;
    private String baseCurrency;
    private String etag;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
