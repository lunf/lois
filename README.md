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
call per second is always fluctuate dramatically.

### Assumption
* Use FCM Admin SDK to send push notification

### Database structure
It is better to structure database in the way that all required pre-processing is completed before the sending step. In 
other words, we will not do or do as little as possible any business logic during the sending process, all data will be 
prepared and stored in database for sending part.

#### Database table

Job schema

| id | created_timestamp | processing_timestamp | completed_timestamp | sender_email   | message_type | message_title | message_body                | message_data                                       | send_to_type |
| ---|-------------------|----------------------|---------------------|----------------|--------------|---------------|-----------------------------|----------------------------------------------------|--------------|
| 1  | 1525248130        | 1525248173           | 1525248274          | joe@domain.com | 1            | Hello world   | This is hello world message | [{"Nick": "Mario"}, {"Room": "PortugalVSDenmark"}] | 1            |

* send_to_type
    * 1 ~ all devices
    * 2 ~ iOS only
    * 3 ~ Android only
    
* message_type : 
    * 1 ~ normal push message
    * 2..n whatever types that you want


Device schema

| id | registered_timestamp | notification_id               | status | device_os | device_name | device_os_version | device_model |
|----|----------------------|-------------------------------|--------|-----------|-------------|-------------------|--------------|
| 1  | 1525248173           | cDIgA634LOI:A9FP...4pmwKnKYHz | 1      | IOS       | JoeiPhone   | 11.0              | iPhone       |
| 2  | 1525248274           | cDIgA634LOI:A9FP...4pmwKnKYHz | 1      | ANDROID   | Jane Doe    | 7.0               | GT-I9000     |
| 3  | 1525248130           | cDIgA634LOI:A9FP...4pmwKnKYHz | 0      | ANDROID   | Allan B     | 6.0               | Nexus One    |

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

| id | job_id | device_id | notification_id               | message_type | message_title | message_body                | message_data                                       |
|----|--------|-----------|-------------------------------|--------------|---------------|-----------------------------|----------------------------------------------------|
| 1  | 1      | 1         | cDIgA634LOI:A9FP...4pmwKnKYHz | 1            | Hello world   | This is hello world message | [{"Nick": "Mario"}, {"Room": "PortugalVSDenmark"}] |
| 2  | 1      | 2         | cDIgA634LOI:A9FP...4pmwKnKYHz | 1            | Hello world   | This is hello world message | [{"Nick": "Mario"}, {"Room": "PortugalVSDenmark"}] |


### API

### Authentication

Authenticate your account by including your secret key in API requests. Authentication to the API is performed via 
[HTTP Basic Auth](http://en.wikipedia.org/wiki/Basic_access_authentication). Provide your API key as the basic auth 
username value. You do not need to provide a password.

#### Register device's notification


#### Send push notification
Path: ```/job```
Method: ```POST```
Job object arguments
| name         | type        |
|--------------|-------------|
| message_type | int         |

  

### How can we measure the result