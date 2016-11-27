package io.github.krzysbaranski.smsapi2email;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

public class MailMessageGenerator implements MessageGenerator {

    private String domain = Optional.ofNullable(System.getenv("DOMAIN")).orElse("localhost");

    private Session session;

    public MailMessageGenerator(Session session) {
        this.session = session;
    }

    @Override
    public Message createMessage(final String username,
                          final String from,
                          final String to,
                          final String message
    ) throws MessagingException {
        MimeMessage mail = new MimeMessage(session);
        mail.addFrom(new Address[]{new InternetAddress(username + "@" + domain)});
        mail.setRecipient(Message.RecipientType.TO, new InternetAddress(to + "@" + domain));
        mail.setSubject("SMS sent by:\"" + username + "\" from \"" + from + "\" to \"" + to + "\"");
        mail.setText(message);
        return mail;
    }
}