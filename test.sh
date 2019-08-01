#!/bin/bash
set -ex -o pipefail

RUN=$(curl --silent "http://localhost:8080/sms.do?username=user&from=smsapi&to=666777888&message=some_message")
RETURN_CODE=$(echo ${RUN} | cut -c1-3)
if [ "${RETURN_CODE}" != "OK:" ]; then
  echo "Invalid return code \"${RETURN_CODE}\""
  exit 1
fi
# check output using MailHog API
BODY=$(curl --silent 'http://localhost:8025/api/v2/search?kind=containing&query=666777888' | jq -r .items[0].Content.Body)
if [ "${BODY}" != "some_message" ]; then
  echo "Invalid message body \"${BODY}\""
  exit 2
fi
