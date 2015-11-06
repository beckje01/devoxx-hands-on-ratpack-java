package lab03;

import java.math.BigDecimal;

public class DefaultBookService implements BookService {
  @Override
  public Book getBook(String isbn) {
    return new Book(
                isbn,
                10,
                BigDecimal.valueOf(49.99),
                "Learning Ratpack",
                "Dan Woods",
                "O'Reilly");
  }
}
