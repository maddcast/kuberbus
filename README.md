# kuberbus
Cloud-native lightweight service bus middleware

### Configuration examples
1. Echo server. Sending all incoming request's content back to client

```json
{
  "port": 8080,
  "routes": [
    {
      "path": "/",
      "method": "*",
      "steps": [
        {
          "step": "reply"
        }
      ]
    }
  ]
}
```

2. Fixed response. Sending fixed responses to all incoming requests

```json
{
  "port": 8080,
  "routes": [
    {
      "path": "/",
      "method": "*",
      "steps": [
        {
          "step": "custom_reply",
          "status": 200,
          "contentType": "text/html",
          "body": "<h1>example</h1>"
        }
      ]
    }
  ]
}
```

3. Reverse proxy. Proxying requests starting with /jokes to target host api.chucknorris.io

```json
{
  "port": 8080,
  "routes": [
    {
      "path": "/jokes",
      "method": "*",
      "steps": [
        {
          "step": "send",
          "endpoint": "https://api.chucknorris.io${message.path}",
          "ignoreSSL": false
        },
        {
          "step": "reply"
        }
      ]
    }
  ]
}
```
Example of usage: start service bus then open http://localhost:8080/jokes/random in a browser

4. Reverse proxy with tracing. Proxying requests starting with /jokes to target host api.chucknorris.io and saving incoming and outgoing packets in the log dir

```json
{
  "port": 8080,
  "routes": [
    {
      "path": "/jokes",
      "method": "*",
      "steps": [
        {
          "step": "save_request",
          "rootDir": "./target/logs",
          "format": "CURL",
          "curlEndpoint": "https://api.chucknorris.io${message.path}"
        },
        {
          "step": "send",
          "endpoint": "https://api.chucknorris.io${message.path}",
          "ignoreSSL": false
        },
        {
          "step": "save_response",
          "rootDir": "./target/logs"
        },
        {
          "step": "reply"
        }
      ]
    }
  ]
}
```