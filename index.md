### SMS-API 2 EMAIL ###
Smsapi.pl compatible email sender (for testing environments)

API compatible with smsapi.pl
that can be used in testing environments with tools like `MailHog`

Application send messages to email: `to@domain` instead of SMS
where`to` is query param in REST
and `domain` is environment variable `DOMAIN`

service: `http://HOST:PORT/sms.do`

**run**
```
export DOMAIN="mydomain"
export PORT="8090"
export HOSTNAME="localhost"
mvn exec:java -Dmail.smtp.host=mysmtpserver
```

**test**

`mvn clean test -Dmail.smtp.host=mysmtpserver`

**email options**
<https://docs.oracle.com/javaee/7/api/javax/mail/package-summary.html>
