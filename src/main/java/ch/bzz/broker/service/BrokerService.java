package ch.bzz.broker.service;

import ch.bzz.broker.data.DataHandler;
import ch.bzz.broker.model.Aktien;
import ch.bzz.broker.model.Broker;
import ch.bzz.broker.model.Fond;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("broker")
public class BrokerService {

    /**
     * read a list of all brokers
     *
     * @return brokers as JASON
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBrokers(
            @CookieParam("userRole") String userRole
    ) {
        List<Broker> brokerList = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            brokerList = DataHandler.readAllBrokers();
        }
        return Response
                .status(httpStatus)
                .entity(brokerList)
                .build();
    }

    /**
     * reads a "broker" identified by the id
     *
     * @param brokerID
     * @return broker
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBroker(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("brokerID") String brokerID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        Broker broker = null;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            broker = DataHandler.readBrokerByID(brokerID);
        }
        return Response
                .status(httpStatus)
                .entity(broker)
                .build();
    }

    /**
     * insert a new broker
     *
     * @param brokerID
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertBroker(
            @Valid @BeanParam Broker broker,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("brokerID") String brokerID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || !userRole.equals("admin")) {
            httpStatus = 403;
        } else {
        broker.setBrokerID(brokerID);
        }

        DataHandler.insertBroker(broker);
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a broker
     *
     * @param brokerID the key
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBroker(
            @Valid @BeanParam Broker broker,
            @FormParam("brokerID") String brokerID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        Broker oldBroker = DataHandler.readBrokerByID(broker.getBrokerID());
        if (userRole == null || !userRole.equals("admin")) {
            httpStatus = 403;
        } else if (oldBroker != null) {
            oldBroker.setBrokerName(broker.getBrokerName());

            DataHandler.updateBroker();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a broker identified by its id
     *
     * @param brokerID the key
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBroker(
            @QueryParam("brokerID") String brokerID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || !userRole.equals("admin")) {
            httpStatus = 403;
        } else if (!DataHandler.deleteBroker(brokerID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}