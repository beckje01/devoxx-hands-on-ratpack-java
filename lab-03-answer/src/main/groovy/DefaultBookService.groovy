@Singleton
class DefaultBookService implements BookService {
  @Override
  Book getBook(String isbn) {
    return new Book(isbn: isbn, quantity: 10, price: 49.99, title: "Learning Ratpack",
      author: "Dan Woods", publisher: "O'Reilly")
  }
}
