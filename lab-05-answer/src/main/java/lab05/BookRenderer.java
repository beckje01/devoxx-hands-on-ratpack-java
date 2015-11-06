package lab05;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ratpack.handling.Context;
import ratpack.jackson.Jackson;
import ratpack.render.RendererSupport;

import static com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator.Feature.WRITE_XML_DECLARATION;

public class BookRenderer extends RendererSupport<Book> {
  @Override
  public void render(Context context, Book book) throws Exception {
    context.byContent(byContentSpec -> byContentSpec
      .json(() ->
        context.render(Jackson.json(book))
      )
      .xml(() -> {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(WRITE_XML_DECLARATION, true);
        context.render(xmlMapper.writeValueAsString(book));
      })
    );
  }
}
