package ch.bzz.broker.model;

import ch.bzz.broker.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * a Fond in Broker
 */
public class Fond {
    private Broker broker;

    private List<Aktien> aktienList;

    @FormParam("fondID")
    @Pattern(regexp = "(?=[0-9]{13}|[- 0-9]{17})97[89](-[0-9]{1,5}){3}-[0-9]")
    private String fondID;

    @FormParam("isin")
    @NotEmpty
    @Size(min=4, max=50)
    private String isin;

    @FormParam("kurs")
    @NotNull
    @DecimalMax(value="19900000.95")
    @DecimalMin(value="0.05")
    private BigDecimal kurs;

    @FormParam("volumen")
    @NotNull
    @DecimalMax(value="19900000.95")
    @DecimalMin(value="0.05")
    private BigDecimal volumen;

    /**
     * default constructor
     */
    public Fond() {
        setAktienList(new ArrayList<>());
    }

    public String getBrokerID() {
        return getBroker().getBrokerID();
    }

    /**
     * creates a Broker-object without the brokerlist
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

    public String getFondID() {
        return fondID;
    }

    public void setFondID(String fondID) {
        this.fondID = fondID;
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

    public List<Aktien> getAktienList() {
        return aktienList;
    }

    public void setAktienList(List<Aktien> aktienList) {
        this.aktienList = aktienList;
    }
}