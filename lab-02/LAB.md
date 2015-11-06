# Lab 02 - Handler Refactor lab

There are many good reasons to refactor parts of your handler chain into their own classes.  For example:

* Improve readability
* Allow handlers to be easily unit tested
* Share common handlers across applications
* Extend the handler DSL with your own shortcut methods

In this lab you will see how easy this is and still take advantage of the Chain API.

Simply make all the feature methods in `UserEndpointSpec` and `SoapActionHandlerUnitSpec` pass.  There are more hints available
within the specs.  Don't forget, `HandlerSpec` should also still completely pass.

## This lab covers

* Refactor a Chain into its own class
* Refactor a repeating handler pattern into a handler shortcut

## Sign Posts

`ratpack.handling.Chain#prefix(prefix, action)`

`ratpack.handling.Handler`
