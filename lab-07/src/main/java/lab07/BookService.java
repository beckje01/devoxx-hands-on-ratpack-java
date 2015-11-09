package lab07;

import ratpack.exec.Operation;
import ratpack.exec.Promise;

import java.util.List;

public interface BookService {
  Operation addBook(Book book);
  Promise<List<Book>> getBooks();
  Promise<Book> getBook(String isbn);
}
