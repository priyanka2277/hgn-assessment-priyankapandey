1.System Design

The service follows a standard layered architecture:

Controller -> Service ->Repository

Controller Layer: exposes the REST endpoints and handles request/response mapping.It contains no business logic.

Service Layer: owns the actual logic - Validating incoming alerts and ao on and resolving which order and trek group alert belongs to,checking for duplicates,handling the claim workflow and coordination escalation.

Repository Layer: talks to MySQL through Spring Data JPA.


2.Data Model

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

3.Device Ambiguity

A device is not permanently assigned to one booking.Instead a device has only one booking within the start and end date of the booking.In that period other cannot book that device.

When an SOS alert arrives, the system determines the correct booking by searching for an order whose:

Device id match the incomig device

Device timestamp falls between the order start date and end date.

4.Deduplication

Satellite communication can cause the same SOS message to be retransmitted multiple times.

To prevent duplicate alerts, the system retrives the most recent alert from the same device

If the difference between the new devicetimestamp and previous alert timestamp is less than or equal to  five minutes, the new alert is treated as the duplicate and is rejected.
Why five Minutes?
within five minutes there is less change of second sos alert.
The threshold can easily be configured in the future if business requirement change.

5.Concurrency

The system must ensure that two coordinators cannnot claim the same alert simultaneously.

To achieve this, claim operation is execute inside a database transaction using a pessimistic locking

If another coordinator has already claimed the alert,the second request receives the error insted of overwriting the existing claim

6.Escalation

Each newly created alert start with:
status = NEW 
escalated = false

To provide escalation -implemented schedular so that it checks with the threshold of the 10 minutes if the alert is not assigned to any coordinator within 10 minutes it is marked as ESCALATED

7.Tradeoffs

Several design decision were made to balance simplicity and functionality

Simplicity
A monolithic application was chosen

MySQL was used as a relational database.

Robustness

Scheduled escalation automatically handles unattended alerts.

Every relationship maintain data consistency.

Future Improvement 

Redis for caching

websocket notifications for coordinators,

email notifications for escalated alerts

Conclusion

The implemented solution satisfies the assignment requirement while remaining simple,maintainable .The layered architecture separates responsibilities clearly,data model supports reusable devices,shared trek groups ,duplicate detection,escalation and coordinator management,The design also leaves room for future scalability .








