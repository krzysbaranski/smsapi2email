# SMS-API 2 EMAIL
[![Build Status](https://travis-ci.org/krzysbaranski/smsapi2email.svg?branch=master)](https://travis-ci.org/krzysbaranski/smsapi2email)
[![Go Report Card](https://goreportcard.com/badge/github.com/krzysbaranski/smsapi2email)](https://goreportcard.com/report/github.com/krzysbaranski/smsapi2email)
![GitHub](https://img.shields.io/github/license/krzysbaranski/smsapi2email.svg)

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

Supported query parameters:

- `from` sender name or send type (2way)
- `to` phone number 
- `message` - content of message 

**Example**

- sending message using cURL
```
curl 'http://localhost:8080/sms.do?from=MyCompany&to=666777888&message=some_message'
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

- run directly from go

`go run main.go`

- run from binary:

`SMTP_HOST=mysmtpserver SMTP_PORT=25 ./smsapi2email`


**compile**

`go build`

**build docker image**

`docker build -t krzysbaranski/smsapi2email .`
