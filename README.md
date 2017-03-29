**SMS-API 2 EMAIL**

Smsapi.pl compatible email sender (for testing environments)

API compatible with smsapi.pl that can be used in testing environments with tools like `MailHog`

Application send messages to email: `to@domain` instead of SMS

where
- `to` is query param in REST
- `domain` is environment variable `DOMAIN`

service: `http://HOST:PORT/sms.do`

**compile**

`mvn clean package`

**setup optional environment**
```
export DOMAIN="mydomain"
export PORT="8090"
export HOSTNAME="localhost"
```

**run**

- `mvn exec:java -Dmail.smtp.host=mysmtpserver`

- `java -Dmail.smtp.host=mysmtpserver -Dmail.smtp.port=25 -jar target/smsapi2email-*.jar `

- docker
```
docker build -t smsapi2email .
docker run --rm -d --env DOMAIN=example.com --env SMTP=smtp.example.com -p 8080:8080 smsapi2email
```

**test**

`mvn clean test -Dmail.smtp.host=mysmtpserver`

**email options**
<https://docs.oracle.com/javaee/7/api/javax/mail/package-summary.html>
