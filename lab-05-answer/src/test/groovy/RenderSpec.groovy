import groovy.json.JsonSlurper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification

class RenderSpec extends Specification {
  @Shared
  ApplicationUnderTest appUnderTest = new GroovyRatpackMainApplicationUnderTest()
  @Delegate
  TestHttpClient testClient = appUnderTest.httpClient

  def "01 - can render a String"() {
    expect:
    getText() == "Hello Devoxx!"

    /*
    Hint:
    Take a look at `ratpack.handling.Context#render(object)`
    Ratpack already has a renderer for String
    */
  }

  def "02 - can render a Groovy Markup Template"() {
    expect:
    getText("welcome") == "<!DOCTYPE html><html><body><p>Hello Devoxx!</p></body></html>"

    /*
    Hint:
    Take a look at `ratpack.groovy.template.MarkupTemplateModule`
    A markup template is already provided in `src/ratpack/templates`
    */
  }

  def "03 - can render a Book as Json"() {
    given:
    requestSpec { req ->
      req.body.type("application/json")
    }

    when:
    get("api/book/1")

    then:
    def book = new JsonSlurper().parseText(response.body.text)
    with(book) {
      isbn == "1"
      quantity == 10
      price == 49.99
      title == "Learning Ratpack"
      author == "Dan Woods"
      publisher == "O'Reilly"
    }

    and:
    response.headers['content-type'] == 'application/json'

    /*
    Hint:
    Take a look at `ratpack.groovy.render.GroovyRendererSupport` to create your own renderer for `Book`
    Renderers are looked up in the Context by type, you will need to make your new renderer available.

    Ratpack's Jackson module (`ratpack.jackson.guice.JacksonModule`) has a support for rendering as JSON
    To use this in your renderer you will need to add a new build dependency on `ratpack-jackson-guice`
    */
  }

  def "04 - can render a Book as Xml"() {
    given:
    requestSpec { req ->
      req.headers.set("Accept", "application/xml")
    }

    when:
    get("api/book/1")

    then:
    def book = new XmlSlurper().parseText(response.body.text)
    with(book) {
      isbn == "1"
      quantity == 10
      price == 49.99
      title == "Learning Ratpack"
      author == "Dan Woods"
      publisher == "O'Reilly"
    }

    and:
    response.headers['content-type'] == 'application/xml'

    /*
    Hint:
    Take a look at `ratpack.groovy.Groovy#markupBuilder(contentType, encoding, closure)`

    Different content types can be rendered using the same renderer using `ratpack.groovy.handling.GroovyContext#byContent(closure)`
    */
  }

}
