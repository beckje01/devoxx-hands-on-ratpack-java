import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import lab07.Lab07
import ratpack.test.ApplicationUnderTest
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification

class BookApiFunctionalSpec extends Specification {
  @Shared
  ApplicationUnderTest appUnderTest = new MainClassApplicationUnderTest(Lab07)

  @Delegate
  TestHttpClient testClient = appUnderTest.httpClient

  def "01 - empty db has no entries"() {
    expect:
    getText('/api/book') == "[]"

    and:
    get('/api/book/1').statusCode == 404
  }

  def "02 - can add and render a Book as Json"() {
    given:
    requestSpec { req ->
      req.body
        .type("application/json")
        .text(JsonOutput.toJson(
          isbn: 1,
          quantity: 10,
          price: 49.99,
          title: "Learning Ratpack",
          author: "Dan Woods",
          publisher: "O'Reilly",
        ))
    }

    and:
    post("api/book")

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

    when:
    get("api/book")

    then:
    def books = new JsonSlurper().parseText(response.body.text)
    with(books.first()) {
      isbn == "1"
      quantity == 10
      price == 49.99
      title == "Learning Ratpack"
      author == "Dan Woods"
      publisher == "O'Reilly"
    }
  }

}
