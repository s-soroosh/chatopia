port=$1
key=$2
nohup ngrok http $port &

for i in `seq 1 5`; do
  uri=`curl -s localhost:4040/api/tunnels | jq -r '.tunnels[] | select(.proto=="https") | .public_url'`
  if [ -z $uri ]; then
    echo "ngrok is not ready yet..."
    sleep 0.5  
  else
    echo "ngrok url is $uri"
    break
  fi
done 

echo "the ngrok url is $uri"

curl "https://api.telegram.org/bot$key/setWebhook?url=$uri/connectors/telegram"
