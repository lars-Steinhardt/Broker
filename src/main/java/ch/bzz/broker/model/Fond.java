package ch.bzz.broker.model;

import ch.bzz.broker.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

/**
 * a book in the bookshelf
 */
public class Fond {
    @JsonIgnore
    private Broker broker;

    private String fondID;
    private String isin;
    private Float kurs;
    private Float volumen;


    public String getBrokerID() {
        return getBroker().getBrokerID();
    }

    /**
     * creates a Broker-object without the fondlist
     * @param brokerID
     */
    public void setBrokerID(String brokerID) {
        setBroker( new Broker());
        Broker broker = DataHandler.getInstance().readBrokerByID(brokerID);
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

    public Float getKurs() {
        return kurs;
    }

    public void setKurs(Float kurs) {
        this.kurs = kurs;
    }

    public Float getVolumen() {
        return volumen;
    }

    public void setVolumen(Float volumen) {
        this.volumen = volumen;
    }
}