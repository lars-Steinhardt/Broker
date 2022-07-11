package ch.bzz.broker.model;

import ch.bzz.broker.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;

/**
 * a Aktien in the Broker
 */
public class Aktien {
    private Broker broker;

    @FormParam("aktienID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String aktienID;

    @FormParam("isin")
    @NotEmpty
    @Size(min=5, max=40)
    private String isin;

    @FormParam("kurs")
    @NotNull
    @DecimalMax(value="19900000.95")
    @DecimalMin(value="0.05")
    private BigDecimal kurs;

    @FormParam("volumen")
    @NotNull
    @DecimalMax(value="199000000.95")
    @DecimalMin(value="0.05")
    private BigDecimal volumen;

    public String getBrokerID() {
        return getBroker().getBrokerID();
    }

    /**
     * creates a Broker-object without the aktienlist
     * @param brokerID
     */
    public void setBrokerID(String brokerID) {
        setBroker( new Broker());
        Broker broker = DataHandler.readBrokerByID(brokerID);
        getBroker().setBrokerID(brokerID);
        getBroker().setBrokerName(broker.getBrokerName());

    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public String getAktienID() {
        return aktienID;
    }

    public void setAktienID(String aktienID) {
        this.aktienID = aktienID;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public BigDecimal getKurs() {
        return kurs;
    }

    public void setKurs(BigDecimal kurs) {
        this.kurs = kurs;
    }

    public BigDecimal getVolumen() {
        return volumen;
    }

    public void setVolumen(BigDecimal volumen) {
        this.volumen = volumen;
    }
}