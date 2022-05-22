package ch.bzz.broker.data;

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
    private List<Broker> brokerList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setBrokerList(new ArrayList<>());
        readBrokerJSON();
        setFondList(new ArrayList<>());
        readFondJSON();
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
     * reads all books
     * @return list of books
     */
    public List<Fond> readAllFonds() {
        return getFondList();
    }

    /**
     * reads a book by its uuid
     * @param fondID
     * @return the Book (null=not found)
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
     * reads all Publishers
     * @return list of publishers
     */
    public List<Broker> readAllBrokers() {

        return getBrokerList();
    }

    /**
     * reads a publisher by its uuid
     * @param brokerID
     * @return the Publisher (null=not found)
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
     * reads the books from the JSON-file
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
     * reads the publishers from the JSON-file
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
     * gets bookList
     *
     * @return value of bookList
     */
    private List<Fond> getFondList() {
        return fondList;
    }

    /**
     * sets bookList
     *
     * @param fondList the value to set
     */
    private void setFondList(List<Fond> fondList) {
        this.fondList = fondList;
    }

    /**
     * gets publisherList
     *
     * @return value of publisherList
     */
    private List<Broker> getBrokerList() {
        return brokerList;
    }

    /**
     * sets publisherList
     *
     * @param brokerList the value to set
     */
    private void setBrokerList(List<Broker> brokerList) {
        this.brokerList = brokerList;
    }


}