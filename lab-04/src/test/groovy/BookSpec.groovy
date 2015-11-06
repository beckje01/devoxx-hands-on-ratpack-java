import lab04.Lab04
import ratpack.test.ApplicationUnderTest
import ratpack.test.MainClassApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification

class BookSpec extends Specification {
  @Shared
  ApplicationUnderTest appUnderTest = new MainClassApplicationUnderTest(Lab04)
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
