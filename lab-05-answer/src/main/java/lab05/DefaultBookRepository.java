package lab05;

import java.math.BigDecimal;

public class DefaultBookRepository implements BookRepository {
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
