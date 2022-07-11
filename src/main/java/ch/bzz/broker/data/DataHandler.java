package ch.bzz.broker.data;

import ch.bzz.broker.model.Aktien;
import ch.bzz.broker.model.Fond;
import ch.bzz.broker.model.Broker;
import ch.bzz.broker.service.Config;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public final class DataHandler {
    private static List<Fond> fondList;
    private static List<Aktien> aktienList;
    private static List<Broker> brokerList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
    }

    /**
     * reads all fonds
     *
     * @return list of fonds
     */
    public static List<Fond> readAllFonds() {
        return getFondList();
    }

    /**
     * reads a fond by its id
     *
     * @param fondID
     * @return the fond (null=not found)
     */
    public static Fond readFondByID(String fondID) {
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
    public static List<Aktien> readAllAktien() {
        return getAktienList();
    }

    /**
     * reads a aktien by its id
     * @param aktienID
     * @return the Aktien (null=not found)
     */
    public static Aktien readAktienByID(String aktienID) {
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
     *
     * @return list of Brokers
     */
    public static List<Broker> readAllBrokers() {

        return getBrokerList();
    }

    /**
     * reads a Broker by its id
     *
     * @param brokerID
     * @return the Broker (null=not found)
     */
    public static Broker readBrokerByID(String brokerID) {
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
    private static void readFondJSON() {
        try {
            String path = Config.getProperty("fondJSON");
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
    private static void readAktienJSON() {
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
    private static void readBrokerJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("brokerJSON")
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
     * writes the brokerList to the JSON-file
     */
    private static void writeBrokerJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String brokerPath = Config.getProperty("brokerJSON");
        try {
            fileOutputStream = new FileOutputStream(brokerPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getBrokerList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the aktienList to the JSON-file
     */
    private static void writeAktienJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String aktienPath = Config.getProperty("aktienJSON");
        try {
            fileOutputStream = new FileOutputStream(aktienPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getAktienList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the fondList to the JSON-file
     */
    private static void writeFondJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String fondPath = Config.getProperty("fondJSON");
        try {
            fileOutputStream = new FileOutputStream(fondPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getFondList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * inserts a new broker into the brokerList
     *
     * @param broker the publisher to be saved
     */

    public static void insertBroker(Broker broker){
        getBrokerList().add(broker);
         getBrokerList();
        writeBrokerJSON();
    }

    /**
     * inserts a new fond into the brokerList
     *
     * @param fond the publisher to be saved
     */

    public static void insertFond(Fond fond){
        getFondList().add(fond);
        writeFondJSON();
    }

    /**
     * inserts a new aktien into the brokerList
     *
     * @param aktien the publisher to be saved
     */

    public static void insertAktien(Aktien aktien){
        getAktienList().add(aktien);
        writeAktienJSON();
    }

    /**
     * updates the brokerList
     */
    public static void updateBroker() {
        writeBrokerJSON();
    }

    /**
     * updates the fondList
     */
    public static void updateFond() {
        writeFondJSON();
    }

    /**
     * updates the aktienList
     */
    public static void updateAktien() {
        writeAktienJSON();
    }


    /**
     * deletes a broker identified by the brokerID
     *
     * @param brokerID the key
     * @return success=true/false
     **/
    public static boolean deleteBroker(String brokerID) {
        Broker broker = readBrokerByID(brokerID);
        if (broker != null) {
            getBrokerList().remove(broker);
            writeBrokerJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * deletes a aktien identified by the brokerID
     * @param aktienID the key
     * @return success=true/false
     **/

    public static boolean deleteAktien(String aktienID) {
        Aktien aktien = readAktienByID(aktienID);
        if (aktien != null) {
            getAktienList().remove(aktien);
            writeAktienJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * deletes a fond identified by the brokerID
     *
     * @param fondID the key
     * @return success=true/false
     **/
    public static boolean deleteFond(String fondID) {
        Fond fond = readFondByID(fondID);
        if (fond != null) {
            getFondList().remove(fond);
            writeFondJSON();
            return true;
        } else {
            return false;
        }
    }

        /**
         * gets fondList
         *
         * @return value of fondList
         */
        private static List<Fond> getFondList () {
            if (fondList == null) {
                setFondList(new ArrayList<>());
                readFondJSON();
            }
            return fondList;
        }

        /**
         * sets fondList
         *
         * @param fondList the value to set
         */
        private static void setFondList (List < Fond > fondList) {
            DataHandler.fondList = fondList;
        }

        /**
         * gets brokerList
         *
         * @return value of brokerList
         */

        private static List<Broker> getBrokerList () {
            if (brokerList == null) {
                setBrokerList(new ArrayList<>());
                readBrokerJSON();
            }
            return brokerList;
        }

        /**
         * sets brokerList
         *
         * @param brokerList the value to set
         */
        private static void setBrokerList (List < Broker > brokerList) {
            DataHandler.brokerList = brokerList;
        }

        /**
         * gets aktienList
         *
         * @return value of aktienList
         */
        private static List<Aktien> getAktienList () {
            if (aktienList == null) {
                setAktienList(new ArrayList<>());
                readAktienJSON();
            }
            return aktienList;
        }

        /**
         * sets aktienList
         *
         * @param aktienList the value to set
         */
        private static void setAktienList (List < Aktien > aktienList) {
            DataHandler.aktienList = aktienList;
        }


}