package io.github.krzysbaranski.smsapi2email;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "sms.do" path)
 */
@Path("sms.do")
public class SmsResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt(@QueryParam("username") String username,
                          @QueryParam("password") String password,
                          @QueryParam("from") String from,
                          @QueryParam("to") String to,
                          @QueryParam("message") String message,
                          @QueryParam("format") String format
    ) {
        return Response.ok("OK:1234:1:" + to, MediaType.TEXT_PLAIN_TYPE).build();
    }
}
