import lab06.Lab06
import ratpack.test.ApplicationUnderTest
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification

class SecuritySpec extends Specification {

  @Shared
  ApplicationUnderTest aut = new MainClassApplicationUnderTest(Lab06)

  @Delegate
  TestHttpClient testHttpClient = aut.httpClient

  def cleanup() {
    resetRequest()
  }

  def "01 - Can get root"() {
    expect:
    getText() == "Root GET"
  }

  def "02 - Can not get secured content"() {
    when:
    def resp = get("secure")

    then:
    resp.statusCode == 401

    /*
    Hint:
    ratpack.pac4j.RatpackPac4j has some static methods that make wiring up security easy.
     */
  }

  def "03 - Can get secured content with basic auth"() {
    given:
    def user = "TestUser"

    and:
    requestSpec({ requestSpec ->
      requestSpec.basicAuth(user, user)
    })

    when:
    def resp = get("secure")

    then:
    resp.statusCode == 200
    resp.body.getText().contains("Secure stuff")
  }

  def "04 - Can display the username of the authenticated user"() {
    given:
    def user = "User" + UUID.randomUUID()

    and:
    requestSpec({ requestSpec ->
      requestSpec.basicAuth(user, user)
    })

    when:
    def resp = get("secure")

    then:
    resp.statusCode == 200
    resp.body.getText().contains(user)

    /*
    Hint: UsernameProfileCreator will create a HttpProfile and it should be in the registry.
     */

  }

}
