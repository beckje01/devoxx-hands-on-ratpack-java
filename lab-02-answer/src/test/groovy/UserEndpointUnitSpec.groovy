import lab02.UserEndpoint
import ratpack.groovy.test.handling.GroovyRequestFixture
import spock.lang.Specification
import spock.lang.Unroll

class UserEndpointUnitSpec extends Specification {

  @Unroll
  def "can send a #requestHttpMethod request to #requestUri"() {
    given:
    def endpointUnderTest = new UserEndpoint()

    when:
    // Using GroovyRequestFixture we can test the chain in isolation with different http methods, headers, uri's and more
    def result = GroovyRequestFixture.handle(endpointUnderTest) {
      method requestHttpMethod
      uri requestUri
    }

    then:
    result.sentResponse
    result.bodyText == expectedResponse

    where:
    requestUri      | requestHttpMethod | expectedResponse
    ""              | "get"             | "user"
    ""              | "post"            | "user"
    "/dave"         | "get"             | "user/dave"
    "/fred"         | "get"             | "user/fred"
    "/dave/tweets"  | "get"             | "user/dave/tweets"
    "/fred/tweets"  | "get"             | "user/fred/tweets"
    "/dave/friends" | "get"             | "user/dave/friends"
    "fred/friends"  | "get"             | "user/fred/friends"

    /*
    Hint:
    You will need to create `UserEndpoint` in src/main/groovy, which doesn't exist yet

    Take a look at `ratpack.groovy.handling.GroovyChainAction` class level Javadoc

    Don't forget to update Lab02.java with your new handler and check `HandlerSpec` still passes
    Take a look at `ratpack.groovy.handling.GroovyChain#prefix(prefix, action)
    */
  }

}
