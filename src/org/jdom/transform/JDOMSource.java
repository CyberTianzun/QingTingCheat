package org.jdom.transform;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.sax.SAXSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.SAXOutputter;
import org.jdom.output.XMLOutputter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

public class JDOMSource extends SAXSource
{
  private static final String CVS_ID = "@(#) $RCSfile: JDOMSource.java,v $ $Revision: 1.20 $ $Date: 2007/11/10 05:29:02 $ $Name: jdom_1_1_1 $";
  public static final String JDOM_FEATURE = "http://org.jdom.transform.JDOMSource/feature";
  private EntityResolver resolver = null;
  private XMLReader xmlReader = null;

  public JDOMSource(List paramList)
  {
    setNodes(paramList);
  }

  public JDOMSource(Document paramDocument)
  {
    setDocument(paramDocument);
  }

  public JDOMSource(Document paramDocument, EntityResolver paramEntityResolver)
  {
    setDocument(paramDocument);
    this.resolver = paramEntityResolver;
  }

  public JDOMSource(Element paramElement)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramElement);
    setNodes(localArrayList);
  }

  private XMLReader buildDocumentReader()
  {
    DocumentReader localDocumentReader = new DocumentReader();
    if (this.resolver != null)
      localDocumentReader.setEntityResolver(this.resolver);
    return localDocumentReader;
  }

  public Document getDocument()
  {
    Object localObject = ((JDOMInputSource)getInputSource()).getSource();
    boolean bool = localObject instanceof Document;
    Document localDocument = null;
    if (bool)
      localDocument = (Document)localObject;
    return localDocument;
  }

  public List getNodes()
  {
    Object localObject = ((JDOMInputSource)getInputSource()).getSource();
    boolean bool = localObject instanceof List;
    List localList = null;
    if (bool)
      localList = (List)localObject;
    return localList;
  }

  public XMLReader getXMLReader()
  {
    if (this.xmlReader == null)
      this.xmlReader = buildDocumentReader();
    return this.xmlReader;
  }

  public void setDocument(Document paramDocument)
  {
    super.setInputSource(new JDOMInputSource(paramDocument));
  }

  public void setInputSource(InputSource paramInputSource)
    throws UnsupportedOperationException
  {
    throw new UnsupportedOperationException();
  }

  public void setNodes(List paramList)
  {
    super.setInputSource(new JDOMInputSource(paramList));
  }

  public void setXMLReader(XMLReader paramXMLReader)
    throws UnsupportedOperationException
  {
    if ((paramXMLReader instanceof XMLFilter))
    {
      for (XMLFilter localXMLFilter = (XMLFilter)paramXMLReader; (localXMLFilter.getParent() instanceof XMLFilter); localXMLFilter = (XMLFilter)localXMLFilter.getParent());
      localXMLFilter.setParent(buildDocumentReader());
      this.xmlReader = paramXMLReader;
      return;
    }
    throw new UnsupportedOperationException();
  }

  private static class DocumentReader extends SAXOutputter
    implements XMLReader
  {
    public void parse(String paramString)
      throws SAXNotSupportedException
    {
      throw new SAXNotSupportedException("Only JDOM Documents are supported as input");
    }

    public void parse(InputSource paramInputSource)
      throws SAXException
    {
      if ((paramInputSource instanceof JDOMSource.JDOMInputSource))
        try
        {
          Object localObject = ((JDOMSource.JDOMInputSource)paramInputSource).getSource();
          if ((localObject instanceof Document))
          {
            output((Document)localObject);
            return;
          }
          output((List)localObject);
          return;
        }
        catch (JDOMException localJDOMException)
        {
          throw new SAXException(localJDOMException.getMessage(), localJDOMException);
        }
      throw new SAXNotSupportedException("Only JDOM Documents are supported as input");
    }
  }

  private static class JDOMInputSource extends InputSource
  {
    private Object source = null;

    public JDOMInputSource(List paramList)
    {
      this.source = paramList;
    }

    public JDOMInputSource(Document paramDocument)
    {
      this.source = paramDocument;
    }

    public Reader getCharacterStream()
    {
      Object localObject = getSource();
      StringReader localStringReader;
      if ((localObject instanceof Document))
        localStringReader = new StringReader(new XMLOutputter().outputString((Document)localObject));
      boolean bool;
      do
      {
        return localStringReader;
        bool = localObject instanceof List;
        localStringReader = null;
      }
      while (!bool);
      return new StringReader(new XMLOutputter().outputString((List)localObject));
    }

    public Object getSource()
    {
      return this.source;
    }

    public void setCharacterStream(Reader paramReader)
      throws UnsupportedOperationException
    {
      throw new UnsupportedOperationException();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.transform.JDOMSource
 * JD-Core Version:    0.6.2
 */