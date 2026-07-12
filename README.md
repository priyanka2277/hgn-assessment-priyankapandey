Himalayan Guardian Network
The Himalayan Guardian Network (HGN) is a Spring Boot REST API designed to manage trekking operations and emergency SOS alerts. It allows administrators to manage devices, bookings, trek groups, trekkers, coordinators, and emergency alerts while supporting alert deduplication, escalation, and concurrent alert management.
Technologies Used
java
Spring Boot
Spring Data JPA
MYSQL
Maven
Swagger
Docker

Alert Management

Receive SOS alerts from tracking devices.

Associate an alert with the correct active order.

Support a single device being reused across multiple orders over time.

Support multiple trekkers sharing one tracking device through a trek group.

Prevent duplicate SOS alerts within a configurable time window.

Escalate alerts automatically if they are not acknowledged within the configured threshold.

Features

Device Management

Create Device

Update Device

Fetch Devices

Order Management

Create Order

Update Order

Trek Group Management

Create Trek Group

Update Trek Group


Fetch Trek Group(s)

Trekker Management

Create Trekker

Update Trekker

Fetch Trekker(s)

Coordinator Management

Create Coordinator

Update Coordinator

Fetch Coordinator(s)

claim management

claim alert

Example API Request

Create Device

POST
/api/v1/device/create

Example Request

{

"deviceId":"device-01",

"deviceName":"Garmin trekking device"

}



Create Order

/api/v1/order/create

Exampe Request

{

"bookingName":"mt everest trekking",

"startDate":"2026-07-13",

"endDate":"2026-07-25",

"deviceId":"device-01"

}



Create Treak Group

/api/v1/trekgroup/create

Example Request

{

"groupName": "family group",

"trekGroupType": "SOLO",

"bookingNumber": "HGN-20260712-2470"

}



Create Trekker

POST

/api/v1/trekker/create

ExampleRequest

{

"firstName": "anita",

"lastName": "pandey",

"phoneNumber": "9681896502",

"trekGroupId": "a5f3ad28-464c-4bf4-9bfe-3475a8c2aec5"

}


Create Coordinator

POST

Example Request

/api/v1/coordinator/create

{

"firstName": "kalpana",

"lastName": "joshi",

"email": "kalpana@gmail.com",

"phoneNumber": "9848798734"

}


Send sos alert

/api/v1/alert/save

Example Request

{

"deviceId": "device-01",

"latitude": 27.71,

"longitude": 85.32,

"deviceTimeStamp": "2026-07-14T17:45:43.177Z"

}

Alert WorkFlow

Device sends an SOS alert.

System identifies the active order using the device and timestamp.

Associated trek group is identified.

Duplicate alerts are ignored if received within the configured time window.

Alert is stored with status NEW.

If not acknowledged within the configured threshold, the alert is automatically escalated.



