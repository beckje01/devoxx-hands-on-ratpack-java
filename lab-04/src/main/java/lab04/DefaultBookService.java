package lab04;

import javax.inject.Inject;

public class DefaultBookService implements BookService {
  final private BookRepository repository;

  @Inject
  public DefaultBookService(BookRepository bookRepository) {
    this.repository = bookRepository;
  }

  @Override
  public Book getBook(String isbn) {
    return repository.getBook(isbn);
  }
}
