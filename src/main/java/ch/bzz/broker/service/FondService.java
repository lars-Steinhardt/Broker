package ch.bzz.broker.service;

import ch.bzz.broker.data.DataHandler;
import ch.bzz.broker.model.Broker;
import ch.bzz.broker.model.Fond;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("fond")
public class FondService {

    /**
     * read a list of all fond
     * @return fond as JASON
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listFonds(
            @CookieParam("userRole") String userRole
    ){
        List<Fond> fondList = DataHandler.readAllFonds();
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            fondList = DataHandler.readAllFonds();
        }
        return  Response
                .status(200)
                .entity(fondList)
                .build();
    }

    /**
     * reads a "fond" identified by the id
     * @param fondID
     * @return fond

     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readFond(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("fondID") String fondID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        Fond fond = null;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            fond = DataHandler.readFondByID(fondID);
        }
        return Response
                .status(httpStatus)
                .entity(fond)
                .build();
    }

    /**
     * insert new fond
     * @param brokerID
     * @return
     */

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertFond(
            @Valid @BeanParam Fond fond,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("brokerID")String brokerID,
            @CookieParam("userRole") String userRole
            ){
        int httpStatus = 200;
        if (userRole == null || !userRole.equals("admin")) {
            httpStatus = 403;
        } else {
            fond.setBrokerID(brokerID);
        }
        DataHandler.insertFond(fond);
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a fond
     * @param brokerID
     * @return Response
     */

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateFond(
            @Valid @BeanParam Fond fond,
            @FormParam("brokerID")String brokerID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        Fond oldFond = DataHandler.readFondByID(fond.getFondID());
        if (userRole == null || !userRole.equals("admin")) {
            httpStatus = 403;
        }else if (oldFond != null) {
            oldFond.setFondID(fond.getFondID());
            oldFond.setIsin(fond.getIsin());
            oldFond.setBrokerID(fond.getBrokerID());
            oldFond.setKurs(fond.getKurs());
            oldFond.setVolumen(fond.getVolumen());

            DataHandler.updateFond();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a fond identified by its id
     * @param fondID  the key
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteFond(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("fondID") String fondID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || !userRole.equals("admin")) {
            httpStatus = 403;
        } else if (!DataHandler.deleteFond(fondID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}