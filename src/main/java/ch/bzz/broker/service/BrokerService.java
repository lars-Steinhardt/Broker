package ch.bzz.broker.service;

import ch.bzz.broker.data.DataHandler;
import ch.bzz.broker.model.Aktien;
import ch.bzz.broker.model.Broker;
import ch.bzz.broker.model.Fond;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

@Path("broker")
public class BrokerService {

    /**
     * read a list of all brokers
     * @return brokers as JASON
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBrokers(){
        List<Broker> brokerList = DataHandler.getInstance().readAllBrokers();
        return  Response
                .status(200)
                .entity(brokerList)
                .build();
    }

    /**
     * reads a "broker" identified by the id
     * @param brokerID
     * @return broker
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBroker(
            @QueryParam("id") String brokerID
    ) {
        int httpStatus = 200;
        Broker broker = DataHandler.getInstance().readBrokerByID(brokerID);
        if (broker == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(broker)
                .build();
    }

}
