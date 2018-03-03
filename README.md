**SMS-API 2 EMAIL**

Drop-in replacement for SMSAPI for testing environments

Features:

- Send messages to mail address instead of SMS using simple API 
- Very simple API
- REST API compatible with popular Smsapi.pl service
- Easy integration with `MailHog`

How it works:

Application send messages to email address `to@domain` instead of SMS

where

- `to` is query param in REST (this is usually phone number)
- `domain` is environment variable `DOMAIN` (can be anything when using MailHog)

**API**

Endpoint: `http://HOST:PORT/sms.do`

Query parameters:

- `to` phone number 
- `message` - content of message 

**Example**

- sending message using cURL
```
curl 'http://localhost:8080/sms.do?username=user&from=smsapi&to=666777888&message=some_message'
```

**run in docker**

Docker image [krzysbaranski/smsapi2email](https://store.docker.com/community/images/krzysbaranski/smsapi2email "Docker Store")

```
docker run --rm -d --env DOMAIN=example.com --env SMTP_HOST=smtp.example.com --env SMTP_PORT=25 -p 8080:8080 krzysbaranski/smsapi2email
```

**docker-compose**

docker compose setup both smsapi2email and MailHog

- mailhog: http://localhost:8025
- API endpoint: http://localhost:8080/sms.do

```
docker-compose up -d
```

**run**

- setup environment (optional)

```
export DOMAIN="mydomain"
export PORT="8090"
export HOSTNAME="localhost"
```

- run directly from maven

`mvn exec:java -Dmail.smtp.host=mysmtpserver`

- run from jar:

`java -Dmail.smtp.host=mysmtpserver -Dmail.smtp.port=25 -jar target/smsapi2email-*.jar `


**compile**

`mvn clean package`

**build docker image**

`docker build -t smsapi2email .`

**test**

`mvn clean test -Dmail.smtp.host=mysmtpserver`

**email options**
<https://docs.oracle.com/javaee/7/api/javax/mail/package-summary.html>
