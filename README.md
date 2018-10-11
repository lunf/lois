# Lois

## Goals
Backend server for React JS frontend
 
### Description
Using tech stack:
 * Spring boot
 * Spring security
 * Multiple data source
 * Mybatis mapping
 * Liquibase data migration


### Assumption


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


### Database structure
It is better to structure database in the way that all required pre-processing is completed before the sending step. In 
other words, we will not do or do as little as possible any business logic during the sending process, all data will be 
prepared and stored in database for sending part.

I really like the idea of user database model in this blog [Optimal User Database Model](https://www.getdonedone.com/building-the-optimal-user-database-model-for-your-application/)
so I will make copy of it for this solution for authentication purposes.

#### Database table

Login schema

| id | username       | password_hash                                                | user_id | login_type | token                            |
|----|----------------|--------------------------------------------------------------|---------|------------|----------------------------------|
| 1  | joe@domain.com | $2a$12$8vxYfAWCXe0Hm4gNX8nzwuqWNukOkcMJ1a9G2tD71ipotEZ9f80Vu | 1       | 1          | heR4rTcM2L8XfdSG9s6DaAN3YQuZxWVq |
| 2  | joe@domain.com | UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q                             | 1       | 2          | UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q |


 * password_hash: $bcrypt_id$log_rounds$128-bit-salt184-bit-hash [Handle Password BCrypt](http://dustwell.com/how-to-handle-passwords-bcrypt.html)
 * login_type:
    * 1 ~ normal web login
    * 2 ~ API key (aka web service call)

User schema

| id | username       | first_name | last_name |
|----|----------------|------------|-----------|
| 1  | joe@domain.com | Joe        | Doe       |
 

Vehicle schema

| id | created_at           | registration_number | purchased_date         | maker   | model   | manufacture_year | type | odometer | status |
|----|----------------------|---------------------|------------------------|---------|---------|------------------|------|----------|--------|
| 1  | 2017-01-24T20:18:17Z | VIC-888             | 2017-01-24T20:18:17Z   | Ford    | Ranger  | 2016             | 3    | 39765    | 1      |


* type (ENUM)
    * 1 ~ VAN_16
    * 2 ~ TRUCK_15
    * 3 ~ PICK_UP

* status (ENUM)
    * 0 ~ BROKEN
    * 1 ~ OK
    * 2 ~ IN_SERVICE 
    
    
* odometer ```will be updated daily by combined all distances traveled in that day```

Staff schema

| id | name      | mobile_number | driver_license_number  | license_expired_date | status |
|----|-----------|---------------|------------------------|----------------------|--------|
| 1  | Jane Lois | 09xxxxxxx     | USD-887-YED-023        | 2022-01-24T00:00:00Z | 1      |

* status (ENUM)
    * 0 ~ NOK
    * 1 ~ OK


Vehicle Activity schema    
This table is for importing and manipulate data before it was final. As admin confirmed the data, it will be copied over Vehicle trip

| id | registration_number | created_at           | departed_time | arrival_time | origin          | destination  | total_running_in_minute | total_pause_in_minute | distance_with_gps | number_of_stop_start | max_speed | average_speed |
|----|---------------------|----------------------|---------------|--------------|-----------------|--------------|-------------------------|-----------------------|-------------------|----------------------|-----------|---------------|
| 1  | VIC-888             | 2017-01-24T20:18:17Z | 06:18         | 08:30        | 2 St, Lion Gate | 34 Ln, Labor | 110                     | 15                    | 45                | 2                    | 110       | 60            |

Vehicle Trip schema (final copy of Vehicle Activity data)

| id | registration_number | created_at           | departed_time | arrival_time | origin          | destination  | total_running_in_minute | total_pause_in_minute | distance_with_gps | distance_with_map | number_of_stop_start | max_speed | average_speed |
|----|---------------------|----------------------|---------------|--------------|-----------------|--------------|-------------------------|-----------------------|-------------------|-------------------|----------------------|-----------|---------------|
| 1  | VIC-888             | 2017-01-24T20:18:17Z | 06:18         | 08:30        | 2 St, Lion Gate | 34 Ln, Labor | 110                     | 15                    | 45                | 46                | 2                    | 110       | 60            |



Order schema
This table is a copy of GTS table (joborder) with specific parameters for this service

| id | name      | code     | delivery_address  | estimated_completion_date | booking_quota | type |
|----|-----------|----------|-------------------|---------------------------|---------------|------|
| 1  | Kitchen   | ORD-5555 | 23 John St        | 2018-01-24T00:00:00Z      | 12            | 1    |

* type (ENUM)
    * 1 ~ house
    * 2 ~ apartment
    * 3 ~ condominium


Vehicle Booking Request schema

| id | from_user_id | order_code  | title            | description          | delivery_address     | created_at           | booked_for           | request_vehicle_type | status | 
|----|--------------|-------------|------------------|----------------------|----------------------|----------------------|----------------------|----------------------|--------|
| 1  | 1            | ORD-5555    | Xe di cong viec  | Goi so sau de giao   | 23 Tay Mo, Tu Liem   | 2018-01-24T00:00:00Z | 2018-01-24T00:00:00Z | 1                    | 1      |

* request_vehicle_type (ENUM) -> check Vehicle schema type
    
* status (ENUM)
    * 0 ~ REQUESTED
    * 1 ~ CONFIRMED
    * 2 ~ CANCELLED
    * 3 ~ COMPLETED

Vehicle Booking Execution schema

| id | driver_id | booking_request_id | vehicle_trip_id |
|----|-----------|--------------------|-----------------|
| 1  | 1         | 1                  | 1               |

### Connect with Zalo API to send notification to driver


### Connect with HERE Map API to determine the distance


'''http://cleanuitemplate.com/admin/react/preview/#/antdesign/table
