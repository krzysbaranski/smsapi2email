package io.github.krzysbaranski.smsapi2email;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.mail.Transport.send;

/**
 * Root resource (exposed at "sms.do" path)
 */
@Path("sms.do")
public class SmsResource {

    private String domain = Optional.ofNullable(System.getenv("DOMAIN")).orElse("localhost");

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
        /**
         * system properties: mail.smtp.host
         */
        Session session = Session.getDefaultInstance(System.getProperties());
        Message mailMessage = new MimeMessage(session);
        try {
            mailMessage.addFrom(new Address[]{new InternetAddress(username + "@" + domain)});
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to + "@" + domain));
            mailMessage.setSubject("SMS sent by:\"" + username + "\" from \"" + from + "\" to \"" + to + "\"");
            mailMessage.setText(message);
        } catch (MessagingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            send(mailMessage);
        } catch (MessagingException e) {
            Response.serverError().build();
        }
        return Response.ok("OK:1234:1:" + to, MediaType.TEXT_PLAIN_TYPE).build();
    }
}
