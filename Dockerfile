FROM golang as builder
WORKDIR /go/src/github.com/krzysbaranski/smsapi2email
COPY . .
ENV GO111MODULE=on
RUN go get -d -v ./...
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o /go/bin/smsapi2email .

FROM alpine:latest
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=builder /go/bin/smsapi2email .
COPY .env .
EXPOSE 8080
CMD ["./smsapi2email"]
