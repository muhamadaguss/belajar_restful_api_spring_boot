# User API Spec

## Register User
Endpoint : POST /api/users

Request Body :

```json
{
  "username": "muhamadaguss",
  "password": "12345",
  "name": "muhamad agus"
}
```

Response Body (Success):
```json
{
  "data": "Ok"
}
```
Response Body (Error):
```json
{
  "errors": "Username must not blanks, ???"
}
```

## Login User
Endpoint : POST /api/auth/login

Request Body :

```json
{
  "username": "muhamadaguss",
  "password": "12345",
}
```

Response Body (Success):
```json
{
  "data": {
    "token": "TOKEN",
    "expiredAt": 3242151241 //miliseconds
  }
}
```
Response Body (Error):
```json
{
  "errors": "Username or Password wrong"
}
```

## Get User
Endpoint : GET /api/users/current

Request Header :
- X-API-TOKEN : Token(Mandatory)

Response Body (Success):
```json
{
  "data": {
    "username": "muhamadaguss",
    "name": "muhamad agus"
  }
}
```
Response Body (Error):
```json
{
  "errors": "Unauthorized"
}
```

## Update User
Endpoint : PATCH /api/users/current

Request Header :
- X-API-TOKEN : Token(Mandatory)

Request Body :

```json
{
  "name": "muhamad agus supriyantono", //put if only want to update name
  "password": "123456", //put if only want to update password
}
```

Response Body (Success):
```json
{
  "data": {
    "username": "muhamadaguss",
    "name": "muhamad agus"
  }
}
```
Response Body (Error):
```json
{
  "errors": "Unauthorized"
}
```

## Logout User
Endpoint : DELETE /api/auth/logout

Request Header :
- X-API-TOKEN : Token(Mandatory)

Response Body (Success):
```json
{
  "data": "OK"
}
```