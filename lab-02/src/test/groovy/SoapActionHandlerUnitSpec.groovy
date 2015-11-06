import lab02.SoapActionHandler
import ratpack.groovy.Groovy
import ratpack.groovy.test.handling.GroovyRequestFixture
import spock.lang.Specification

class SoapActionHandlerUnitSpec extends Specification {

  def "can handle soap requests"() {
    given:
    def handlerUnderTest = new SoapActionHandler("foo", Groovy.groovyHandler{
      response.send(request.headers.get("SOAPAction"))
    })

    when:
    // Using GroovyRequestFixture we can test the handler in isolation with different http methods, headers, uri's and more
    def result = GroovyRequestFixture.handle(handlerUnderTest) {
      header "SOAPAction", soapAction
    }

    then:
    result.sentResponse == responseSent
    result.bodyText == expectedResponse

    where:
    soapAction | responseSent | expectedResponse
    "foo"      | true         | "foo"
    "bar"      | false        | null

    /*
    Hint:
    You will need to complete the `SoapActionHandler` in src/main/groovy, currently it is just delegating to the next
    handler

    Don't forget to update Lab02.java with your new handler and check `HandlerSpec` still passes
    Take a look at `ratpack.groovy.handling.GroovyChain#all(handler)`
    */
  }

}
