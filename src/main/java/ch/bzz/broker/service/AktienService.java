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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("aktien")
public class AktienService {

    /**
     * read a list of all aktien
     * @return aktien as JASON
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAktien(){
        List<Aktien> aktienList = DataHandler.readAllAktien();
        return  Response
                .status(200)
                .entity(aktienList)
                .build();
    }

    /**
     * reads a "aktien" identified by the id
     * @param aktienID
     * @return aktien
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readAktien(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("aktienID") String aktienID
    ) {
        int httpStatus = 200;
        Aktien aktien = DataHandler.readAktienByID(aktienID);
        if (aktien == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(aktien)
                .build();
    }

    /**
     * insert new aktien
     * @param brokerID
     * @return
     */


    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertAktien(
            @Valid @BeanParam Aktien aktien,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("brokerID")String brokerID
    ){
        aktien.setBrokerID(brokerID);
        DataHandler.insertAktien(aktien);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a aktien
     * @param brokerID
     * @return Response
     */

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAktien(
            @Valid @BeanParam Aktien aktien,
            @FormParam("brokerID")String brokerID

    ) {
        int httpStatus = 200;
        Aktien oldAktien = DataHandler.readAktienByID(aktien.getAktienID());
        if (oldAktien != null) {
            oldAktien.setAktienID(aktien.getAktienID());
            oldAktien.setIsin(aktien.getIsin());
            oldAktien.setBrokerID(aktien.getBrokerID());
            oldAktien.setKurs(aktien.getKurs());
            oldAktien.setVolumen(aktien.getVolumen());

            DataHandler.updateAktien();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a aktien identified by its id
     * @param aktienID  the key
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAktien(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("aktienID") String aktienID
    ) {
        int httpStatus = 200;
        if (!DataHandler.deleteAktien(aktienID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
