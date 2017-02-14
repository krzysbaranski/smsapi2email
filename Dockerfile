FROM jboss/base-jdk:8

ADD target/*.jar /smsapi2email.jar

ENV SMTP localhost
ENV DOMAIN localhost
ENV PORT 8080
EXPOSE 8080

CMD ["sh", "-c", "echo java -jar /smsapi2email.jar-Dmail.smtp.host=${SMTP}"]
