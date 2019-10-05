<<Document
	Examples:
		- ./set_telegram_webhook.sh 8080 /connectors/telegram TELEGRAM_API_KEY
                - TELEGRAM_API_KEY=YOUR_TELEGRAM_KEY  ./set_telegram_webhook.sh 9090 / 
Document

port=$1

endpoint=$2

if [ -z $3 ]; then
  key=$TELEGRAM_API_KEY
else
  key=$3
fi

nohup ngrok http $port > ngrok.tmp &

for i in $(seq 1 5); do
  uri=$(curl -s localhost:4040/api/tunnels | jq -r '.tunnels[] | select(.proto=="https") | .public_url')
  if [ -z $uri ]; then
    echo "ngrok is not ready yet..."
    sleep 0.5  
  else
    break
  fi
done 

echo "the ngrok url is $uri"

curl "https://api.telegram.org/bot$key/setWebhook?url=$uri$endpoint"
