package lab04;

import java.math.BigDecimal;

public class Book {
  public final String isbn;
  public final long quantity;
  public final BigDecimal price;
  public final String title;
  public final String author;
  public final String publisher;

  public Book(String isbn, long quantity, BigDecimal price, String title, String author, String publisher) {
    this.isbn = isbn;
    this.quantity = quantity;
    this.price = price;
    this.title = title;
    this.author = author;
    this.publisher = publisher;
  }
}
