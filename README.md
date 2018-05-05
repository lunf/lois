# Delilah Springboot Discruptor

## Goals
Send 100k push notification messages per minutes or approx. 1600 messages per second.

### Description
We need high performance push notification server which can make multiple HTTP requests to FCM server concurrently in
order to achieve the target. Currently, FCM API allow up to 1000 registered devices per call and number of thread will
be restricted by CPU core (CPU with 4 core ~ 4 threads and due to Hyper-threaded Intel which can double the thread per
core). Let make assumption that (run command ```lscpu```):
 * AWS T2.medium: 
    * Architecture:          x86_64
    * CPU op-mode(s):        32-bit, 64-bit
    * Byte Order:            Little Endian
    * CPU(s):                2
    * Thread(s) per core:    1
    * Core(s) per socket:    2
    * Socket(s):             1
    * Model name:            Intel(R) Xeon(R) CPU E5-2676 v3 @ 2.40GHz
 * 1000 registered devices per call
 * Approx. 250 ms per call

We have 2 threads (1 socket -> 2 cores per socket = 2 threads) and each of them runs in parallel then we will have the
following formula:

```
    number of theads x 1000 (max registered device) x 4 (1 seconds ~ 250 ms per call)
    2                x 1000                         x 4 = 8000 messages per second (or 480k per minute)
```

The result of the formula is over our expectation, but we need to take into account that the Internet speed and average
call per second always fluctuates dramatically.

### Assumption
* Use FCM Admin SDK to send push notification

### API

### Authentication

Authenticate your account by including your secret key in API requests. Authentication to the API is performed via 
[HTTP Basic Auth](http://en.wikipedia.org/wiki/Basic_access_authentication). Provide your API key as the basic auth 
username value. You do not need to provide a password.

```Authorization: Basic QWxhZGRpbjpPcGVuU2VzYW1l```

API requests without authentication will also fail.

### Errors

We use conventional HTTP response codes to indicate the success or failure of an API request. 

In general: 
* Codes in the ```2xx``` range indicate success. 
* Codes in the ```4xx``` range indicate an error that failed given the information provided (e.g., a required 
parameter was omitted, a charge failed, etc.). 
* Codes in the ```5xx``` range indicate an error with the server

HTTP status code summary

| code               | mean              | description                                                          |
|--------------------|-------------------|----------------------------------------------------------------------|
| 200                | OK                | Everything worked as expected.                                       |
| 400                | Bad Request       | The request was unacceptable, often due to missing a                 |
|                    |                   | required parameter.                                                  |
| 401                | Unauthorized      | No valid API key provided.                                           |
| 402                | Request Failed    | The parameters were valid but the request failed.                    |
| 404                | Not Found         | The requested resource doesn't exist.                                |
| 409                | Conflict          | The request conflicts with another request (perhaps due to using the | 
|                    |                   | same idempotent key).                                                |
| 429                | Too Many Requests | Too many requests hit the API too quickly. We recommend an           |
|                    |                   | exponential backoff of your requests.                                |
| 500, 502, 503, 504 | Server Errors     | Something went wrong on server                                       |


Some ```4xx``` errors that could be handled programmatically included an error code that briefly explains the 
error reported.

Attributes

| name    | type            | description |
|---------|-----------------|-------------|
| type    | string          | The type of error returned. One of  ```api_error```, ```authentication_error```,      |
|         |                 | ```idempotency_error```, ```invalid_request_error```, or  ```rate_limit_error```      |
| code    | optional string | For some errors that could be handled programmatically.                               |
| message | optional string | A human-readable message providing more details about the error.                      |

Error types:

| name                   | description                                                                          |
|------------------------|--------------------------------------------------------------------------------------|
| api_error	             | API errors cover any other type of problem (e.g: a temporary problem with servers    |
| authentication_error   | Failure to properly authenticate yourself in the request.                            |
| idempotency_error      | Idempotency errors occur when an Idempotency-Key is re-used on a request that does   |
|                        | not match the first request's API endpoint and parameters.                           |
| invalid_request_error  | Invalid request errors arise when your request has invalid parameters.               |
| rate_limit_error       | Too many requests hit the API too quickly.                                           |

### Idempotent Requests

The API supports [idempotency](https://en.wikipedia.org/wiki/Idempotence) for safely retrying requests without accidentally performing the same operation twice.
For example, if a request to create a job fails due to a network connection error, you can retry the request 
with the same idempotency key to guarantee that only a single job is created.

```GET``` and ```DELETE``` requests are idempotent by definition, meaning that the same backend work will occur 
no matter how many times the same request is issued. You shouldn't send an idempotency key with these verbs because 
it will have no effect.

To perform an idempotent request, provide an additional ```Idempotency-Key: <key>``` header to the request.

How you create unique keys is up to you, but we suggest using V4 UUIDs or another appropriately random string. 
We'll always send back the same response for requests made with the same key, and keys can't be reused with different 
request parameters. Keys expire after 24 hours.

#### Register device's notification
Path: ```/devices```
Method: ```POST```
Device object arguments

| name              | type   | description                       |
|-------------------|--------|-----------------------------------|
| notification_id   | string | FCM notification id of the device |
| device_os         | string | OS type of the device             |
| device_name       | string | The device name                   |
| device_os_version | string | OS version of the device          |
| device_model      | string | The device model                  |

Returns

Returns a device object if the call succeeded.

```
{
     "id": 12,
     "created_at": 1515750198,
     "notification_id": "cDIgA634LOI:A9FP",
     "device_os": "iOS",
     "device_name": "DoeiPhone",
     "device_os_version": "11.0",
     "device_model": "iPhone"
}
```


#### Send push notification
Path: ```/jobs```
Method: ```POST```
Job object arguments

| name             | type               | description                                                          |
|------------------|--------------------|----------------------------------------------------------------------|
| message_type     | int                | 1 ~ normal push message                                              |
|                  |                    | 2 ~ whatever message type which you want                             |
| message_title    | string (optional)  | A message title of the push notification                             |
| message_body     | string             | A message body of the push notification                              |
| message_metadata | hash (optional)    | A dictionary of attributes and values for the attributes defined by  |
|                  |                    | the push notification {"Nick": "Mario", "Room": "PortugalVSDenmark"} |
| send_to_type     | int                | 1 ~ all devices                                                      |
|                  |                    | 2 ~ iOS only                                                         |
|                  |                    | 3 ~ Android only                                                     |
|                  |                    | 4 ~ Specific device                                                  |
| devices          | array containing   | ["cDIgA634LOI:A9FP...4pmwKnKYHz", "cDIgA634LOI:A9FP...4pmwKnKYHz"]   |
|                  | strings (optional) | array containing notification_id of devices                          |

Returns

Returns a job object if the call succeeded.
```
{
  "id": 7,
  "created_at": 1525248130,
  "message_type": 1,
  "message_title": "Hello world",
  "message_body": "This is my world",
  "message_metadata": {
      "Nick": "Mario", 
      "Room": "PortugalVSDenmark"
  },
  "send_to_type": 1,
  "devices": [
      "cDIgA634LOI:A9FP...4pmwKnKYHz", 
      "cDIgA634LOI:A9FP...4pmwKnKYHz"
  ]
}
```

### Database structure
It is better to structure database in the way that all required pre-processing is completed before the sending step. In 
other words, we will not do or do as little as possible any business logic during the sending process, all data will be 
prepared and stored in database for sending part.

I really like the idea of user database model in this blog [Optimal User Database Model](https://www.getdonedone.com/building-the-optimal-user-database-model-for-your-application/)
so I will make copy of it for this solution for authentication purposes.

#### Database table

Login schema

| id | username       | password_hash                                                | user_id | login_type |
|----|----------------|--------------------------------------------------------------|---------|------------|
| 1  | joe@domain.com | $2a$12$8vxYfAWCXe0Hm4gNX8nzwuqWNukOkcMJ1a9G2tD71ipotEZ9f80Vu | 1       | 1          |
| 2  | joe@domain.com | UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q                             | 1       | 2          |


 * password_hash: $bcrypt_id$log_rounds$128-bit-salt184-bit-hash [Handle Password BCrypt](http://dustwell.com/how-to-handle-passwords-bcrypt.html)
 * login_type:
    * 1 ~ normal web login
    * 2 ~ API key (aka web service call)

User schema

| id | username       | first_name | last_name |
|----|----------------|------------|-----------|
| 1  | joe@domain.com | Joe        | Doe       |
 

Job schema

| id | created_at | processing_at | completed_at | sender_email   | message_type | message_title | message_body                | message_metadata                                       | send_to_type |
|----|------------|---------------|--------------|----------------|--------------|---------------|-----------------------------|--------------------------------------------------------|--------------|
| 1  | 1525248130 | 1525248173    | 1525248274   | joe@domain.com | 1            | Hello world   | This is hello world message | [{"Nick": "Mario"}, {"Room": "PortugalVSDenmark"}]     | 1            |

* send_to_type
    * 1 ~ all devices (default)
    * 2 ~ iOS only
    * 3 ~ Android only
    * 4 ~ Specific device
    
* message_type : 
    * 1 ~ normal push message
    * 2..n whatever types that you want


Device schema

| id | created_at | notification_id               | status | device_os | device_name | device_os_version | device_model |
|----|------------|-------------------------------|--------|-----------|-------------|-------------------|--------------|
| 1  | 1525248173 | cDIgA634LOI:A9FP...4pmwKnKYHz | 1      | IOS       | JoeiPhone   | 11.0              | iPhone       |
| 2  | 1525248274 | cDIgA634LOI:A9FP...4pmwKnKYHz | 1      | ANDROID   | Jane Doe    | 7.0               | GT-I9000     |
| 3  | 1525248130 | cDIgA634LOI:A9FP...4pmwKnKYHz | 0      | ANDROID   | Allan B     | 6.0               | Nexus One    |

* status
    * 1 ~ OK
    * 0 ~ NOK  (need to be removed from database)

* device_os
    * IOS ~ iOS device
    * ANDROID ~ Android device

* device_name
    * iOS -> Settings -> General -> About -> Name
    * Android -> Contact Profile

* device_os_version
    * `[[UIDevice currentDevice] systemVersion]`
    * `Build.VERSION.RELEASE`

* device_model
    * ```[[UIDevice currentDevice] model]```
    * ```os.android.Build.MODEL```

Messaging schema

| id | job_id | device_id | notification_id               | message_type | message_title | message_body                | message_metadata                                       |
|----|--------|-----------|-------------------------------|--------------|---------------|-----------------------------|--------------------------------------------------------|
| 1  | 1      | 1         | cDIgA634LOI:A9FP...4pmwKnKYHz | 1            | Hello world   | This is hello world message | [{"Nick": "Mario"}, {"Room": "PortugalVSDenmark"}]     |
| 2  | 1      | 2         | cDIgA634LOI:A9FP...4pmwKnKYHz | 1            | Hello world   | This is hello world message | [{"Nick": "Mario"}, {"Room": "PortugalVSDenmark"}]     |



### How can we measure the result