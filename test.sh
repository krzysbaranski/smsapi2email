#!/bin/bash
set -x -o pipefail
RUN=$(curl "http://localhost:8080/sms.do?username=user&from=smsapi&to=666777888&message=some_message")
RETURN_CODE=$(echo ${RUN} | cut -c1-3)
if [ "${RETURN_CODE}" != "OK:" ]; then
  echo "Invalid return code \"${RETURN_CODE}\""
  exit 1
fi
BODY=$(curl 'http://localhost:8025/api/v2/search?kind=containing&query=666777888' | jq .items[0].Content.Body)
if [ "${BODY}" != "some_message" ]; then
  echo "Invalid message body \"${BODY}\""
  exit 2
fi
