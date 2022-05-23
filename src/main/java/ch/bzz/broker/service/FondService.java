package ch.bzz.broker.service;

import ch.bzz.broker.data.DataHandler;
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

@Path("fond")
public class FondService {

    /**
     * read a list of all fond
     * @return fond as JASON
     */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listFonds(){
        List<Fond> fondList = DataHandler.getInstance().readAllFonds();
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
            @QueryParam("id") String fondID
    ) {
        int httpStatus = 200;
        Fond fond = DataHandler.getInstance().readFondByID(fondID);
        if (fond == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(fond)
                .build();
    }

}
