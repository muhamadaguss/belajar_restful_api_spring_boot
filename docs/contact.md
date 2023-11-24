# Contact API Spec

## Create Contact
Endpoint : POST /api/contacts

Request Header :
- X-API-TOKEN : Token(Mandatory)

Request Body :
```json
{
  "firstName": "Muhamad Agus",
  "lastName": "Supriyantono",
  "email": "agus@gmail.com",
  "phone": "1234567890"
}
```
Response Body (Success) :
```json
{
  "data": {
    "id": "random-string",
    "firstName": "Muhamad Agus",
    "lastName": "Supriyantono",
    "email": "agus@gmail.com",
    "phone": "1234567890"
  }
}
```
Response Body (Failed) :
```json
{
  "errors": "Email format invalid"
}
```
## Update Contact
Endpoint : PUT /api/contacts/{idContact}

Request Header :
- X-API-TOKEN : Token(Mandatory)

Request Body :
```json
{
  "firstName": "Muhamad Agus",
  "lastName": "Supriyantono",
  "email": "agus@gmail.com",
  "phone": "1234567890"
}
```
Response Body (Success) :
```json
{
  "data": {
    "id": "random-string",
    "firstName": "Muhamad Agus",
    "lastName": "Supriyantono",
    "email": "agus@gmail.com",
    "phone": "1234567890"
  }
}
```
Response Body (Failed) :
```json
{
  "errors": "Email format invalid"
}
```
## Get Contact
Endpoint : GET /api/contacts/{idContact}

Request Header :
- X-API-TOKEN : Token(Mandatory)

Response Body (Success) :
```json
{
  "data": {
    "id": "random-string",
    "firstName": "Muhamad Agus",
    "lastName": "Supriyantono",
    "email": "agus@gmail.com",
    "phone": "1234567890"
  }
}
```
Response Body (Failed) :
```json
{
  "errors": "Contact is not found"
}
```
## Search Contact
Endpoint : GET /api/contacts

Query Param :
- name : String,contact first name or last name,using like query,optional
- phone : String,contact phone,using like query,optional
- email : String,contact email,using like query,optional
- page : Integer,start from 0,default 0
- size : Integer, default 10

Request Header :
- X-API-TOKEN : Token(Mandatory)

Response Body (Success) :
```json
{
  "data": [
    {
      "data": {
        "id": "random-string",
        "firstName": "Muhamad Agus",
        "lastName": "Supriyantono",
        "email": "agus@gmail.com",
        "phone": "1234567890"
      }
    },
    {
      "data": {
        "id": "random-string",
        "firstName": "Muhamad Agus",
        "lastName": "Supriyantono",
        "email": "agus@gmail.com",
        "phone": "1234567890"
      }
    }
  ],
  "paging": {
    "currentPage": 0,
    "totalPage": 10,
    "size": 10
  }
}
```
Response Body (Failed) :
```json
{
  "errors": "Contacts is not found"
}
```
## Remove Contact
Endpoint : DELETE /api/contacts/{idContact}

Request Header :
- X-API-TOKEN : Token(Mandatory)

Response Body (Success) :
```json
{
  "data": "Ok"
}
```
Response Body (Failed) :
```json
{
  "errors": "Contact is not found"
}
```