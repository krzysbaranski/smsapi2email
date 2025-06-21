FROM golang:1.21 AS builder
WORKDIR /go/src/github.com/krzysbaranski/smsapi2email
COPY go.mod go.sum ./
RUN go mod download
COPY . .
RUN CGO_ENABLED=0 GOOS=linux go build -o /go/bin/smsapi2email .

FROM alpine:latest
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=builder /go/bin/smsapi2email .
COPY .env .
EXPOSE 8080
CMD ["./smsapi2email"]
