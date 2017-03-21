# Hands on Ratpack 
The Devoxx 2017 Hands on Lab for Ratpack.

## Prerequisites
* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Setup
Each Lab has two directories the lab for you to work on getting all the tests to pass and one answer directory that has a complete solution for the lab. Also there is a `LAB.md` file in each directory with some general guidence. 

TIP:
Take advantage of Gradle's [continuous mode](https://docs.gradle.org/current/userguide/continuous_build.html) when modifying your code. This will continually run tests on each modification that Gradle detects to your source files.

example:

`./gradlew :lab-01:test --continuous`

## IDE Integration
If you're using IntelliJ execute `./gradlew idea` before getting started.
