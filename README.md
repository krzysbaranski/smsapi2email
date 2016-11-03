**smsapi2email**

SMSAPI.pl compatible service
send messages to email: `to@domain`
where `to` is query param in REST
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