1.System Design

The service follows a standard layered architecture:

Controller -> Service ->Repository

Controller Layer: exposes the REST endpoints and handles request/response mapping.It contains no business logic.

Service Layer: owns the actual logic - Validating incoming alerts and ao on and resolving which order and trek group alert belongs to,checking for duplicates,handling the claim workflow and coordination escalation.

Repository Layer: talks to MySQL through Spring Data JPA.


Data Model

Entity:Device

A physical device

Resuable across orders over time 

Entity:Order

A single trek booking

References one Device;has a start/end date range

Entity:TrekGroup

The group of people tied to a booking

One-to-One with Order

Entity:Trekker

An individual person on the treak

Many-to-One with TrekGroup

Alert 

A single SOS event

References Device,Order,and TrekGroup







