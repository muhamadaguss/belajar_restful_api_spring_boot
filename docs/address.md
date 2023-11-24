# Address API Spec

## Create Address

Endpoint : POST /api/contacts/{idContact}/addresses

Request Header :
- X-API-TOKEN : Token(Mandatory)

Request Body :
```json
{
  "street": "Jalan Sesama",
  "city": "Jakarta",
  "province": "DKI Jakarta",
  "postalCode": "17444"
}
```
Response Body (Success) :
```json
{
  "data": {
    "id": "random-string",
    "street": "Jalan Sesama",
    "city": "Jakarta",
    "province": "DKI Jakarta",
    "postalCode": "17444"
  }
}
```
Response Body (Failed) :
```json
{
  "errors": "Contact is not found"
}
```
## Update Address

Endpoint : PUT /api/contacts/{idContact}/addresses/{idAddress}

Request Header :
- X-API-TOKEN : Token(Mandatory)

Request Body :
```json
{
  "street": "Jalan Sesama",
  "city": "Jakarta",
  "province": "DKI Jakarta",
  "country": "Indonesia",
  "postalCode": "17444"
}
```
Response Body (Success) :
```json
{
  "data": {
    "id": "random-string",
    "street": "Jalan Sesama",
    "city": "Jakarta",
    "province": "DKI Jakarta",
    "country": "Indonesia",
    "postalCode": "17444"
  }
}
```
Response Body (Failed) :
```json
{
  "errors": "Address is not found"
}
```
## Get Address

Endpoint : GET /api/contacts/{idContact}/addresses/{idAddres}

Request Header :
- X-API-TOKEN : Token(Mandatory)

Response Body (Success) :
```json
{
  "data": {
    "id": "random-string",
    "street": "Jalan Sesama",
    "city": "Jakarta",
    "province": "DKI Jakarta",
    "country": "Indonesia",
    "postalCode": "17444"
  }
}
```
Response Body (Failed) :
```json
{
  "errors": "Address is not found"
}
```
## Remove Address

Endpoint : DELETE /api/contacts/{idContact}/addresses/{idAddress}

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
  "errors": "Address is not found"
}
```
## List Address

Endpoint : GET /api/contacts/{idContact}/addresses

Request Header :
- X-API-TOKEN : Token(Mandatory)

Response Body (Success) :
```json
{
  "data": [
    {
      "id": "random-string",
      "street": "Jalan Sesama",
      "city": "Jakarta",
      "province": "DKI Jakarta",
      "country": "Indonesia",
      "postalCode": "17444"
    },
    {
      "id": "random-string",
      "street": "Jalan Sesama",
      "city": "Jakarta",
      "province": "DKI Jakarta",
      "country": "Indonesia",
      "postalCode": "17444"
    }
  ]
}
```
Response Body (Failed) :
```json
{
  "errors": "Addresses is not found"
}
```
