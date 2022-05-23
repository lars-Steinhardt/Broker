package ch.bzz.broker.service;

import ch.bzz.broker.data.DataHandler;
import ch.bzz.broker.model.Aktien;
import ch.bzz.broker.model.Fond;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

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
        List<Aktien> aktienList = DataHandler.getInstance().readAllAktien();
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
            @QueryParam("id") String aktienID
    ) {
        int httpStatus = 200;
        Aktien aktien = DataHandler.getInstance().readAktienByID(aktienID);
        if (aktien == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(aktien)
                .build();
    }

}
