package vladimirov.nikolay.CurrencyGateway.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String currency;
    private BigDecimal baseValue;

    @ManyToOne( cascade = CascadeType.ALL,
            optional = false)
    private BatchInformation batchInformation;

    public Rate() {
    }

    public Rate(String currency, BigDecimal baseValue) {
        this.currency = currency;
        this.baseValue = baseValue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String code) {
        this.currency = code;
    }

    public BigDecimal getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(BigDecimal baseValue) {
        this.baseValue = baseValue;
    }

    public BatchInformation getBatchInformation() {
        return batchInformation;
    }

    public void setBatchInformation(BatchInformation batchInformation) {
        this.batchInformation = batchInformation;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Rate{");
        sb.append("currency='").append(currency).append('\'');
        sb.append(", baseValue=").append(baseValue);
        sb.append(", batchInformationEtag=").append(batchInformation.getEtag());
        sb.append('}');
        return sb.toString();
    }
}
