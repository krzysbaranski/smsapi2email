package io.github.krzysbaranski.smsapi2email;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface Sender {
    void send(Message message) throws MessagingException;
}
