package main

import (
	"bytes"
	"fmt"
	"github.com/joho/godotenv"
	"log"
	"net/http"
	"net/smtp"
	"os"
)

var (
	domain   string
	port     string
	smtpHost string
	smtpPort string
)

func main() {
	if err := godotenv.Load(); err != nil {
		log.Fatalf("Cannot load env: %s", err)
	}

	domain = getenv("DOMAIN", "localhost")
	port = getenv("PORT", "8080")
	smtpHost = getenv("SMTP_HOST", "localhost")
	smtpPort = getenv("SMTP_PORT", "25")

	log.Printf("Loaded configuration: DOMAIN=%s; PORT=%s; SMTP_HOST=%s; SMTP_PORT=%s;", domain, port, smtpHost, smtpPort)

	http.HandleFunc("/sms.do", func(w http.ResponseWriter, r *http.Request) {
		from := r.URL.Query().Get("from")
		to := r.URL.Query().Get("to")
		message := r.URL.Query().Get("message")
		w.Header().Add("Content-Type", "text/plain")

		if status, err := sendMessage(from, to, message); err != nil {
			http.Error(w, err.Error(), status)
			return
		}

		if _, err := fmt.Fprint(w, "OK:1234:1"); err != nil {
			log.Printf("Error: %s", err)
		}
	})

	log.Printf("Listening on :%s/sms.do", port)
	if err := http.ListenAndServe(fmt.Sprintf(":%s", port), nil); err != nil {
		log.Fatalf("Cannot start server: %s", err)
	}
}

func getenv(variable string, defaultVal string) string {
	val := os.Getenv(variable)
	if val == "" {
		return defaultVal
	}
	return val
}

func sendMessage(from string, to string, message string) (status int, error error) {
	c, err := smtp.Dial(fmt.Sprintf("%s:%s", smtpHost, smtpPort))
	if err != nil {
		log.Printf("Cannot connect to smtp host: %s:%s: %s", smtpHost, smtpPort, err)
		return http.StatusServiceUnavailable, fmt.Errorf("smtp service unavailable")
	}
	defer c.Quit()
	if err := c.Mail(fmt.Sprintf("%s@%s", from, domain)); err != nil {
		return http.StatusBadRequest, fmt.Errorf(fmt.Sprintf("Bad from email address: %s@%s", from, domain))
	}
	if err := c.Rcpt(fmt.Sprintf("%s@%s", to, domain)); err != nil {
		return http.StatusBadRequest, fmt.Errorf(fmt.Sprintf("Bad to email address: %s@%s", to, domain))
	}
	wc, err := c.Data()
	if err != nil {
		return http.StatusBadRequest, fmt.Errorf(fmt.Sprintf("Bad data format: %s", err))
	}
	defer wc.Close()
	buf := bytes.NewBufferString(fmt.Sprintf("From: %s@%s\r\nSubject: SMS sent from \"%s\" to \"%s\"\r\n\r\n%s", from, domain, from, to, message))
	if _, err = buf.WriteTo(wc); err != nil {
		return http.StatusInternalServerError, fmt.Errorf(fmt.Sprintf("Error writing message body: %s", err))
	}
	return http.StatusOK, nil
}
