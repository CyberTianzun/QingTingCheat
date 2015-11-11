package org.jdom.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.transform.sax.SAXResult;
import org.jdom.DefaultJDOMFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMFactory;
import org.jdom.input.SAXHandler;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.XMLFilterImpl;

public class JDOMResult extends SAXResult
{
  private static final String CVS_ID = "@(#) $RCSfile: JDOMResult.java,v $ $Revision: 1.24 $ $Date: 2007/11/10 05:29:02 $ $Name: jdom_1_1_1 $";
  public static final String JDOM_FEATURE = "http://org.jdom.transform.JDOMResult/feature";
  private JDOMFactory factory = null;
  private boolean queried = false;
  private Object result = null;

  public JDOMResult()
  {
    DocumentBuilder localDocumentBuilder = new DocumentBuilder();
    super.setHandler(localDocumentBuilder);
    super.setLexicalHandler(localDocumentBuilder);
  }

  private void retrieveResult()
  {
    if (this.result == null)
      setResult(((DocumentBuilder)getHandler()).getResult());
  }

  public Document getDocument()
  {
    retrieveResult();
    Document localDocument;
    if ((this.result instanceof Document))
      localDocument = (Document)this.result;
    while (true)
    {
      this.queried = true;
      return localDocument;
      boolean bool1 = this.result instanceof List;
      localDocument = null;
      if (bool1)
      {
        boolean bool2 = this.queried;
        localDocument = null;
        if (!bool2)
          try
          {
            Object localObject = getFactory();
            localDocument = null;
            if (localObject == null)
              localObject = new DefaultJDOMFactory();
            localDocument = ((JDOMFactory)localObject).document(null);
            localDocument.setContent((List)this.result);
            this.result = localDocument;
          }
          catch (RuntimeException localRuntimeException)
          {
          }
      }
    }
  }

  public JDOMFactory getFactory()
  {
    return this.factory;
  }

  public List getResult()
  {
    Object localObject = Collections.EMPTY_LIST;
    retrieveResult();
    if ((this.result instanceof List))
      localObject = (List)this.result;
    while (true)
    {
      this.queried = true;
      return localObject;
      if (((this.result instanceof Document)) && (!this.queried))
      {
        List localList = ((Document)this.result).getContent();
        localObject = new ArrayList(localList.size());
        while (localList.size() != 0)
          ((List)localObject).add(localList.remove(0));
        this.result = localObject;
      }
    }
  }

  public void setDocument(Document paramDocument)
  {
    this.result = paramDocument;
    this.queried = false;
  }

  public void setFactory(JDOMFactory paramJDOMFactory)
  {
    this.factory = paramJDOMFactory;
  }

  public void setHandler(ContentHandler paramContentHandler)
  {
  }

  public void setLexicalHandler(LexicalHandler paramLexicalHandler)
  {
  }

  public void setResult(List paramList)
  {
    this.result = paramList;
    this.queried = false;
  }

  private class DocumentBuilder extends XMLFilterImpl
    implements LexicalHandler
  {
    private JDOMResult.FragmentHandler saxHandler = null;
    private boolean startDocumentReceived = false;

    public DocumentBuilder()
    {
    }

    private void ensureInitialization()
      throws SAXException
    {
      if (!this.startDocumentReceived)
        startDocument();
    }

    public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
      throws SAXException
    {
      ensureInitialization();
      super.characters(paramArrayOfChar, paramInt1, paramInt2);
    }

    public void comment(char[] paramArrayOfChar, int paramInt1, int paramInt2)
      throws SAXException
    {
      ensureInitialization();
      this.saxHandler.comment(paramArrayOfChar, paramInt1, paramInt2);
    }

    public void endCDATA()
      throws SAXException
    {
      this.saxHandler.endCDATA();
    }

    public void endDTD()
      throws SAXException
    {
      this.saxHandler.endDTD();
    }

    public void endEntity(String paramString)
      throws SAXException
    {
      this.saxHandler.endEntity(paramString);
    }

    public List getResult()
    {
      JDOMResult.FragmentHandler localFragmentHandler = this.saxHandler;
      List localList = null;
      if (localFragmentHandler != null)
      {
        localList = this.saxHandler.getResult();
        this.saxHandler = null;
        this.startDocumentReceived = false;
      }
      return localList;
    }

    public void ignorableWhitespace(char[] paramArrayOfChar, int paramInt1, int paramInt2)
      throws SAXException
    {
      ensureInitialization();
      super.ignorableWhitespace(paramArrayOfChar, paramInt1, paramInt2);
    }

    public void processingInstruction(String paramString1, String paramString2)
      throws SAXException
    {
      ensureInitialization();
      super.processingInstruction(paramString1, paramString2);
    }

    public void skippedEntity(String paramString)
      throws SAXException
    {
      ensureInitialization();
      super.skippedEntity(paramString);
    }

    public void startCDATA()
      throws SAXException
    {
      ensureInitialization();
      this.saxHandler.startCDATA();
    }

    public void startDTD(String paramString1, String paramString2, String paramString3)
      throws SAXException
    {
      ensureInitialization();
      this.saxHandler.startDTD(paramString1, paramString2, paramString3);
    }

    public void startDocument()
      throws SAXException
    {
      this.startDocumentReceived = true;
      JDOMResult.this.setResult(null);
      this.saxHandler = new JDOMResult.FragmentHandler(JDOMResult.this.getFactory());
      super.setContentHandler(this.saxHandler);
      super.startDocument();
    }

    public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
      throws SAXException
    {
      ensureInitialization();
      super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    }

    public void startEntity(String paramString)
      throws SAXException
    {
      ensureInitialization();
      this.saxHandler.startEntity(paramString);
    }

    public void startPrefixMapping(String paramString1, String paramString2)
      throws SAXException
    {
      ensureInitialization();
      super.startPrefixMapping(paramString1, paramString2);
    }
  }

  private static class FragmentHandler extends SAXHandler
  {
    private Element dummyRoot = new Element("root", null, null);

    public FragmentHandler(JDOMFactory paramJDOMFactory)
    {
      super();
      pushElement(this.dummyRoot);
    }

    private List getDetachedContent(Element paramElement)
    {
      List localList = paramElement.getContent();
      ArrayList localArrayList = new ArrayList(localList.size());
      while (localList.size() != 0)
        localArrayList.add(localList.remove(0));
      return localArrayList;
    }

    public List getResult()
    {
      try
      {
        flushCharacters();
        label4: return getDetachedContent(this.dummyRoot);
      }
      catch (SAXException localSAXException)
      {
        break label4;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.transform.JDOMResult
 * JD-Core Version:    0.6.2
 */