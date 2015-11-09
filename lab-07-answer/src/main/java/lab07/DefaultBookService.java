package lab07;

import ratpack.exec.Operation;
import ratpack.exec.Promise;

import javax.inject.Inject;
import java.util.List;

public class DefaultBookService implements BookService {
  final private BookRepository repository;

  @Inject
  public DefaultBookService(BookRepository bookRepository) {
    this.repository = bookRepository;
  }


  @Override
  public Operation addBook(Book book) {
    return repository.addBook(book);
  }

  @Override
  public Promise<List<Book>> getBooks() {
    return repository.getBooks();
  }

  @Override
  public Promise<Book> getBook(String isbn) {
    return repository.getBook(isbn);
  }
}
