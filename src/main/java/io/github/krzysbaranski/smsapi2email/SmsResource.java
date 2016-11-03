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
import java.util.Properties;

import static javax.mail.Transport.send;

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
        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", 25);
        props.put("mail.smtp.connectiontimeout", 20);
        props.put("mail.smtp.timeout", 20);

        Session session = Session.getDefaultInstance(props);
        Message mailMessage = new MimeMessage(session);
        try {
            mailMessage.addFrom(new Address[]{new InternetAddress(from + "@from.office.local")});
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("krzysztof.baranski@ziemia.xyz")); //to+"@to.office.local
            mailMessage.setSubject("SMS " + username);
            mailMessage.setText(message);

        } catch (MessagingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            send(mailMessage);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Response.serverError().build();
        }

        return Response.ok("OK:1234:1:" + to, MediaType.TEXT_PLAIN_TYPE).build();
    }
}
