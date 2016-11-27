package io.github.krzysbaranski.smsapi2email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class MailSender implements Sender {
    public void send(Message mailMessage) throws MessagingException {
        Transport.send(mailMessage);
    }
}
