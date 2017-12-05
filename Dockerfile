FROM maven:3-jdk-8-alpine

ADD . /build
RUN cd /build && mvn -DskipTests package
RUN cp /build/target/*.jar /smsapi2email.jar
RUN rm -rf /build
ENV SMTP_HOST localhost
ENV SMTP_PORT 25
ENV DOMAIN localhost
ENV PORT 8080

EXPOSE 8080

CMD java -Dmail.smtp.host=${SMTP_HOST} -Dmail.smtp.port=${SMTP_PORT} -jar /smsapi2email.jar
