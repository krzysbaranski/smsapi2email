FROM maven:3-jdk-8-alpine as builder
ADD pom.xml /build/
WORKDIR /build
RUN mvn -DskipTests -s /usr/share/maven/ref/settings-docker.xml verify clean --fail-never
ADD . /build
RUN mvn -DskipTests -s /usr/share/maven/ref/settings-docker.xml package

FROM openjdk:8-jre-alpine
COPY --from=builder /build/target/*.jar /smsapi2email.jar
ENV SMTP_HOST localhost
ENV SMTP_PORT 25
ENV DOMAIN localhost
ENV PORT 8080

EXPOSE 8080

CMD java -Dmail.smtp.host=${SMTP_HOST} -Dmail.smtp.port=${SMTP_PORT} -jar /smsapi2email.jar
