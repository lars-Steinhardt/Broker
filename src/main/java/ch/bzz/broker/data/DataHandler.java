package ch.bzz.broker.data;

import ch.bzz.broker.model.Aktien;
import ch.bzz.broker.model.Fond;
import ch.bzz.broker.model.Broker;
import ch.bzz.broker.service.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Fond> fondList;
    private List<Aktien> aktienList;
    private List<Broker> brokerList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setBrokerList(new ArrayList<>());
        readBrokerJSON();
        setFondList(new ArrayList<>());
        readFondJSON();
        setAktienList(new ArrayList<>());
        readAktienJSON();
    }

    /**
     * gets the only instance of this class
     * @return
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }


    /**
     * reads all fonds
     * @return list of fonds
     */
    public List<Fond> readAllFonds() {
        return getFondList();
    }

    /**
     * reads a book by its id
     * @param fondID
     * @return the book (null=not found)
     */
    public Fond readFondByID(String fondID) {
        Fond fond = null;
        for (Fond entry : getFondList()) {
            if (entry.getFondID().equals(fondID)) {
                fond = entry;
            }
        }
        return fond;
    }

    /**
     * reads all aktien
     * @return list of aktien
     */
    public List<Aktien> readAllAktien() {
        return getAktienList();
    }

    /**
     * reads a aktien by its id
     * @param aktienID
     * @return the Aktien (null=not found)
     */
    public Aktien readAktienByID(String aktienID) {
        Aktien aktien = null;
        for (Aktien entry : getAktienList()) {
            if (entry.getAktienID().equals(aktienID)) {
                aktien = entry;
            }
        }
        return aktien;
    }

    /**
     * reads all Brokers
     * @return list of Brokers
     */
    public List<Broker> readAllBrokers() {

        return getBrokerList();
    }

    /**
     * reads a Broker by its id
     * @param brokerID
     * @return the Broker (null=not found)
     */
    public Broker readBrokerByID(String brokerID) {
        Broker broker = null;
        for (Broker entry : getBrokerList()) {
            if (entry.getBrokerID().equals(brokerID)) {
                broker = entry;
            }
        }
        return broker;
    }

    /**
     * reads the fonds from the JSON-file
     */
    private void readFondJSON() {
        try {
            String path = Config.getProperty("fondsJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Fond[] fonds = objectMapper.readValue(jsonData, Fond[].class);
            for (Fond fond : fonds) {
                getFondList().add(fond);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the aktien from the JSON-file
     */
    private void readAktienJSON() {
        try {
            String path = Config.getProperty("aktienJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Aktien[] aktien = objectMapper.readValue(jsonData, Aktien[].class);
            for (Aktien aktie : aktien) {
                getAktienList().add(aktie);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the brokers from the JSON-file
     */
    private void readBrokerJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("brokersJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Broker[] brokers = objectMapper.readValue(jsonData, Broker[].class);
            for (Broker broker : brokers) {
                getBrokerList().add(broker);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * gets fondList
     *
     * @return value of fondList
     */
    private List<Fond> getFondList() {
        return fondList;
    }

    /**
     * sets fondList
     *
     * @param fondList the value to set
     */
    private void setFondList(List<Fond> fondList) {
        this.fondList = fondList;
    }

    /**
     * gets brokerList
     *
     * @return value of brokerList
     */
    private List<Broker> getBrokerList() {
        return brokerList;
    }

    /**
     * sets brokerList
     *
     * @param brokerList the value to set
     */
    private void setBrokerList(List<Broker> brokerList) {
        this.brokerList = brokerList;
    }

    /**
     * gets aktienList
     *
     * @return value of aktienList
     */
    private List<Aktien> getAktienList() {
        return aktienList;
    }

    /**
     * sets brokerList
     *
     * @param aktienList the value to set
     */
    private void setAktienList(List<Aktien> aktienList) {
        this.aktienList = aktienList;
    }

}