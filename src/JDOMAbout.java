import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class JDOMAbout
{
  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    JDOMAbout.Info localInfo = new JDOMAbout.Info();
    String str = localInfo.title;
    System.out.println(str + " version " + localInfo.version);
    System.out.println("Copyright " + localInfo.copyright);
    System.out.println();
    System.out.println(localInfo.description);
    System.out.println();
    System.out.println("Authors:");
    Iterator localIterator = localInfo.authors.iterator();
    while (localIterator.hasNext())
    {
      JDOMAbout.Author localAuthor = (JDOMAbout.Author)localIterator.next();
      System.out.print("  " + localAuthor.name);
      if (localAuthor.email == null)
        System.out.println();
      else
        System.out.println(" <" + localAuthor.email + ">");
    }
    System.out.println();
    System.out.println(str + " license:");
    System.out.println(localInfo.license);
    System.out.println();
    System.out.println(str + " support:");
    System.out.println(localInfo.support);
    System.out.println();
    System.out.println(str + " web site: " + localInfo.website);
    System.out.println();
  }

  private static class Author
  {
    final String email;
    final String name;

    Author(String paramString1, String paramString2)
    {
      this.name = paramString1;
      this.email = paramString2;
    }
  }

  private static class Info
  {
    private static final String INFO_FILENAME = "META-INF/jdom-info.xml";
    final List authors = new ArrayList();
    final String copyright;
    final String description;
    final String license;
    final String support;
    final String title;
    final String version;
    final String website;

    Info()
      throws Exception
    {
      InputStream localInputStream = getInfoFileStream();
      Element localElement1 = new SAXBuilder().build(localInputStream).getRootElement();
      this.title = localElement1.getChildTextTrim("title");
      this.version = localElement1.getChildTextTrim("version");
      this.copyright = localElement1.getChildTextTrim("copyright");
      this.description = localElement1.getChildTextTrim("description");
      this.license = localElement1.getChildTextTrim("license");
      this.support = localElement1.getChildTextTrim("support");
      this.website = localElement1.getChildTextTrim("web-site");
      Iterator localIterator = localElement1.getChildren("author").iterator();
      while (localIterator.hasNext())
      {
        Element localElement2 = (Element)localIterator.next();
        JDOMAbout.Author localAuthor = new JDOMAbout.Author(localElement2.getChildTextTrim("name"), localElement2.getChildTextTrim("e-mail"));
        this.authors.add(localAuthor);
      }
    }

    private InputStream getInfoFileStream()
      throws FileNotFoundException
    {
      InputStream localInputStream = getClass().getResourceAsStream("META-INF/jdom-info.xml");
      if (localInputStream == null)
        throw new FileNotFoundException("META-INF/jdom-info.xml not found; it should be within the JDOM JAR but wasn't found on the classpath");
      return localInputStream;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     JDOMAbout
 * JD-Core Version:    0.6.2
 */