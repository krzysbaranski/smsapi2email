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

**build docker image**
`docker build -t smsapi2email .`

**setup optional environment**
```
export DOMAIN="mydomain"
export PORT="8090"
export HOSTNAME="localhost"
```

**run**

- `mvn exec:java -Dmail.smtp.host=mysmtpserver`

- `java -Dmail.smtp.host=mysmtpserver -Dmail.smtp.port=25 -jar target/smsapi2email-*.jar `

**run in docker**

Docker image [krzysbaranski/smsapi2email](https://store.docker.com/community/images/krzysbaranski/smsapi2email "Docker Store")

```
docker run --rm -d --env DOMAIN=example.com --env SMTP_HOST=smtp.example.com --env SMTP_PORT=25 -p 8080:8080 krzysbaranski/smsapi2email
```
**docker-compose**
```
docker-compose up -d
```
call smsapi at <http://localhost:8080/sms.do> 

```
curl 'http://localhost:8080/sms.do?username=user&from=smsapi&to=666777888&message=some_message'
```

open browser and visit: <http://localhost:8025> to see results

**test**

`mvn clean test -Dmail.smtp.host=mysmtpserver`

**email options**
<https://docs.oracle.com/javaee/7/api/javax/mail/package-summary.html>
