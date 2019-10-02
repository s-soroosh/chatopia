port=$1
key=$2
nohup ngrok http $port > ngrok.tmp &
sleep 2
uri=`curl -s localhost:4040/api/tunnels | jq -r '.tunnels[] | select(.proto=="https") | .public_url'`
echo "the ngrok url is $uri"

curl "https://api.telegram.org/bot$key/setWebhook?url=$uri/connectors/telegram"
