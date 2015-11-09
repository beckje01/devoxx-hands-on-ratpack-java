# Lab 07 - Blocking lab

So far we have been working with CPU bound code. We have not had to perform any kind of synchronous or blocking actions.
Ratpack is asynchronous and non-blocking from the ground up. Ratpack's APIs are marked as `ratpack.api.NonBlocking` where
appropriate to denote the API's non-blocking and asynchronous nature.

In the real world our existing libraries tend to be blocking and synchronous like the JDBC library.

Ratpack uses a much smaller set of Threads to manage HTTP Request handling, a single thread could be responsible for handling
hundreds of active connections. If you perform a blocking action one of Ratpack's main compute threads you will impede
throughput and prevent other requests from being processed until the blocking code completes.

Ratpack provides a great utility for integrating synchronous/blocking code with Ratpack's Execution Model via
`ratpack.exec.Blocking`. This `Blocking` utility allows users to specify blocking code that will inform Ratpack to
 execute the blocking code on Ratpack's blocking scheduler.

Ratpack provides two constructs for representing an asynchronous units of work: `ratpack.exec.Promise` and `ratpack.exec.Operation`.

The `Blocking` utility creates `Promise` and `Operation` from user supplied code which can then be further mapped or consumed.


To complete this lab you will need to implement the `DefaultBookRepository` to perform blocking database calls.
This lab provides generated jOOQ library that specifies a typed representation of the `book` table.

The database connection to an H2 in-memory database is provided via the `ratpack.h2.H2Module` and connection pooling is
 made available through `ratpack.hikari.HikariModule`.

## This lab covers

* Working with synchronous/blocking libraries
* Working with Promises and Operations
* Working with Blocking api
* Working with Ratpack's integration with H2 and Hikari
* Parsing incoming JSON
* Rendering a custom object with a different content type depending on what has been requested

## Sign Posts

`ratpack.exec.Promise`

`ratpack.exec.Operation`

`ratpack.exec.Blocking`

`ratpack.hikari.HikariModule`

`ratpack.guice.ConfigurableModule`

`ratpack.handling.Context#clientError(int)`

`ratpack.jackson.Jackson.json(Object)`

`ratpack.handling.Context#parse(Class)`
