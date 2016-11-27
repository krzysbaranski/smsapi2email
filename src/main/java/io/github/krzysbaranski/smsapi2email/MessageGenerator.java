package io.github.krzysbaranski.smsapi2email;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface MessageGenerator {
    Message createMessage(final String username,
                          final String from,
                          final String to,
                          final String message
    ) throws MessagingException;
}
