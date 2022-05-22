package ch.bzz.broker.service;

import ch.bzz.broker.data.DataHandler;
import ch.bzz.broker.model.Fond;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

@Path("fond")
public class BrokerService {

    /**
     * read a list of all books
     * @return books as JASON
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

}
