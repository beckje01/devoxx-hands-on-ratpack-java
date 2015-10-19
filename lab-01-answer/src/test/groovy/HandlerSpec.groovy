import groovy.io.GroovyPrintStream
import lab01.Lab01
import org.slf4j.LoggerFactory
import ratpack.http.client.RequestSpec
import ratpack.test.ApplicationUnderTest
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class HandlerSpec extends Specification {
  // Start our application and make it available for testing. `@Shared` means the same app instance will be used for _all_ tests
  @Shared
  ApplicationUnderTest appUnderTest = new MainClassApplicationUnderTest(Lab01.class);

  // ApplicationUnderTest includes a TestHttpClient that we can use for sending requests to our application.
  @Delegate
  TestHttpClient testClient = appUnderTest.httpClient

  def "01 - can send a request to the root path"() {
    when: "a GET request is sent with no path"
    testClient.get() // we don't have to assign the ReceivedResponse returned as TestHttpClient will keep track of this for us

    then: "a response is returned with body text of 'Hello Devoxx!'"
    testClient.response.body.text == "Hello Devoxx!" // `testClient.response` is the ReceivedResponse from the last request sent

    /*
    Hint:
    The `handlers` closure in `Lab01.java` delegates to `ratpack.groovy.handling.GroovyChain`.  It's this class
    and its Builder pattern that will allow us to fluently compose our handler chain.

    Try adding an `all` handler to make this test pass.

    Take a look at `ratpack.handling.Chain` class level Javadoc
    and `ratpack.groovy.handling.GroovyChain#all(handler)`
    */
  }

  def "02 - can send a GET request to the path '/user'"() {
    when: "a GET request is sent to the path '/user'"
    get("user") // Using `@Delegate` on the testClient property means we don't have to keep doing `testClient.get()`

    then: "a response is returned with body text of 'user'"
    response.body.text == "user" // Taking advantage of `@Delegate` here too

    /*
    Hint:
    `GroovyChain` has various methods for adding handlers based on a HTTP method binding, some of which allow you to
    specify a path binding too.

    Take a look at `ratpack.groovy.handling.GroovyChain#get(path, handler)`

    If you used `all` in the previous test, don't forget it will match on "all" requests.  Handler order is important!
    */
  }

  @Unroll
  def "03 - can send a GET request to the path '/user/#username'"() {
    expect:
    getText("user/$username") == "user/$username" // Using `getText()` we can roll 2 method calls into 1

    where:
    username << ["dave", "fred"]

    /*
    Hint:
    Take a look at the "Path Binding" section in `ratpack.handling.Chain` class level Javadoc
    and `ratpack.handling.Context#getPathTokens()`
    and `ratpack.path.PathBinding#getTokens()`
    */
  }

  @Unroll
  def "04 - can send a GET request to the path '/user/#username/tweets'"() {
    expect:
    getText("user/$username/tweets") == "user/$username/tweets"

    where:
    username << ["dave", "fred"]
  }

  @Unroll
  def "05 - can send a GET request to the path '/user/#username/friends'"() {
    expect:
    getText("user/$username/friends") == "user/$username/friends"

    where:
    username << ["dave", "fred"]

    /*
    Hint:
    If you haven't already, you might want to start refactoring

    Take a look at `ratpack.handling.Chain#prefix(prefix, action)`
    and `ratpack.handling.Context#getAllPathTokens()`
    and `ratpack.path.PathBinding#getAllTokens()`
    */
  }

  def "06 - can send a POST request to the path '/user'"() {
    expect:
    postText("user") == "user"

    /*
    Hint:
    Take a look at `ratpack.groovy.handling.GroovyContext#byMethod(closure)`
    */
  }

  def "07 - can not send a PUT request to the path '/user'"() {
    expect:
    put("user").statusCode == 405
  }

  def "08 - can request a static asset"() {
    expect:
    getText("assets/js/app.js") == "var message = 'Hello Devoxx!';"

    /*
    Hint:
    There is already a file available to serve src/ratpack/public/js/app.js

    Take a look at `ratpack.handling.Chain#files(config)`
    */
  }

  def "09 - can serve an index file"() {
    expect:
    getText("home/") == "<html><body><p>Hello Devoxx!</p></body></html>"

    /*
    Hint:
    There is already an index file available to serve src/ratpack/pages/home/index.html
    */
  }

  @Unroll
  def "10 - can log a warning when there is a request for a user starting with 'f'"() {
    // Configure the logger with our own output stream so we can get a handle on the log content
    def loggerOutput = new ByteArrayOutputStream()
    def logger = LoggerFactory.getLogger(HandlerSpec)
    logger.TARGET_STREAM = new GroovyPrintStream(loggerOutput)

    given:
    get("user/$username/tweets")

    expect:
    loggerOutput.toString().contains("Warning, request for $username")

    where:
    username << ["florence", "fred"]

    /*
    Hint:
    Handlers that don't generate a response must delegate to the next handler
    Take a look at `ratpack.handling.Context#next()`

    Path route matching with a regular expression might help here
    Take a look at `ratpack.handling.Chain` class level Javadoc
    */
  }

  def "11 - can log all requests"() {
    // Configure the logger with our own output stream so we can get a handle on the log content
    def loggerOutput = new ByteArrayOutputStream()
    def logger = LoggerFactory.getLogger(HandlerSpec)
    logger.TARGET_STREAM = new GroovyPrintStream(loggerOutput)

    given:
    get(requestPath)

    expect:
    loggerOutput.toString().contains(expectedLogEntry)

    where:
    requestPath | expectedLogEntry
    "user"      | 'GET /user'
    "user/1"    | 'GET /user/1'

    /*
    Hint:
    You'll need a handler that gets called for _all_ requests regardless of their Http method or path
    */
  }

  @Unroll
  def "12 - can send a POST request with a soap action of #soapAction"() {
    given:
    // Using TestHttpClient.requestSpec we can configure details of this request like adding headers and setting the body
    requestSpec { RequestSpec req ->
      req.headers.set("SOAPAction", soapAction)
    }

    expect:
    postText("api/ws") == "api/ws - $soapAction"

    where:
    soapAction << ["getTweets", "getFriends"]

    /*
    Hint:
    The SOAPAction is sent as a HTTP header, try creating a handler that checks for that header

    Take a look at using `ratpack.groovy.handling.GroovyChain#when(test, handlers)`
    or `ratpack.groovy.handling.GroovyChain#onlyIf(test, handler)`
    */
  }
}
