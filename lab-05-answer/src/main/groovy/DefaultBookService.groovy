import javax.inject.Inject

class DefaultBookService implements BookService {
  final private BookRepository repository

  @Inject
  public DefaultBookService(BookRepository bookRepository) {
    this.repository = bookRepository
  }

  @Override
  Book getBook(String isbn) {
    return repository.getBook(isbn)
  }
}
