package lab07;

import com.google.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ratpack.exec.Operation;
import ratpack.exec.Promise;

import javax.sql.DataSource;
import java.util.List;

public class DefaultBookRepository implements BookRepository {

  private final DSLContext create;

  @Inject
  public DefaultBookRepository(DataSource ds) {
    create = DSL.using(ds, SQLDialect.MYSQL);
  }

  @Override
  public Operation addBook(Book book) {
    // TODO - Implement addBook function
    // Hint - checkout Blocking#op(Block) to see how to integrate blocking code with Ratpack
    // Hint - checkout DSLContext#newRecord(Table, Object)
    // Hint - lab07.jooq.tables.Book.BOOK is the representation for the underlying `book` table
    return null;
  }

  @Override
  public Promise<List<Book>> getBooks() {
    // TODO - Implement getBooks function
    // Hint - checkout Blocking#get(Block) to see how to integrate blocking code with Ratpack
    // Hint - lab07.jooq.tables.Book.BOOK is the representation for the underlying `book` table
    // Hint - checkout DSLContext#select() and ResultQuery#fetchInto(Class)
    return null;
  }

  @Override
  public Promise<Book> getBook(String isbn) {
    // TODO - Implement getBooks function
    // Hint - checkout Blocking#get(Block) to see how to integrate blocking code with Ratpack
    // Hint - lab07.jooq.tables.Book.BOOK is the representation for the underlying `book` table
    // Hint - checkout SelectWhereStep#where(Condition...) and ResultQuery#fetchOneInto(Class)
    return null;
  }

}
