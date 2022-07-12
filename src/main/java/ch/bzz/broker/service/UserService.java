package ch.bzz.broker.service;

import ch.bzz.broker.data.UserData;
import ch.bzz.broker.model.User;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("user")
public class UserService {
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login (
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        int httpStatus;

        User user = UserData.findUser(username, password);
        if (user.getRole().equals("guest")) {
            httpStatus = 404;
        } else {
            httpStatus = 200;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                user.getRole(),
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );

        Response resonse = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return resonse;
    }
}