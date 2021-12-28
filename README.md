# Simple Docs

### Register

url : /api/auth/register

|param|type|value|
|-----|----|----|
|name|string|username|
|password|string|password|
|mailbox|string|mailbox|

response:

```json
{
  "status": 1,
  "message": "Succeed",
  "data": {}
}
```

|param|type|value|
|-----|----|-----|
|status|bool|1 / 0|
|message|string|"Succeed" / "InvalidArgument" /"UnknownPasswordContentType" / ""UserExist"|
|data| object| null|

### Login

url : /api/auth/login

|param|type|value|
|-----|----|-----|
|mailbox|string|mailbox|
|password|string|password|

response:

```json
{
  "status": 1,
  "message": "Succeed",
  "data": {
    "token": "114514 1919810"
  }
}
```

|param|type|value|
|-----|----|-----|
|status|bool|1 / 0|
|message|string|"Succeed" / "InvalidArgument" / "MailboxUnMatchPassword"|
|data| object| {"token" : tokenString}|

### CreateRoom

url : /api/chat/create

|header|type|value|
|-----|----|-----|
|token|string|get it from `login` interface |

|param|type|value|
|-----|----|-----|
|room_name|string|room name(can change)|
|user_list|Array[Long]|user who is able to in this room|

response:

```json
{
  "status": 1,
  "message": "Succeed",
  "data": {
    "user_list": [
      11,
      4,
      5,
      14
    ],
    "websocket_url": "ws://xxx",
    "room_name": "下北泽"
  }
}
```

|param|type|value|
|-----|----|-----|
|status|bool|1 / 0|
|message|string|"Succeed" / "InvalidArgument" / "FailedCreate"|
|data| object|user_list && websocket_url && room_name |
|room_name| string| as the param name|
|websocket_url|string|user can use websocket connect this url to join this room|
|user_list|Array[Long]|all users in this room ,display it with uuid|
