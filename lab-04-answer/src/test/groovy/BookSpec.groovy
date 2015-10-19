import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification

class BookSpec extends Specification {
  @Shared
  ApplicationUnderTest appUnderTest = new GroovyRatpackMainApplicationUnderTest()
  @Delegate
  TestHttpClient testClient = appUnderTest.httpClient

  def "01 - can get a book's title"() {
    expect:
    getText("book/1/title") == "Learning Ratpack"
  }

  def "02 - can get a book's author"() {
    expect:
    getText("book/1/author") == "Dan Woods"
  }
}
