package org.jdom.output;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.Content;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.EntityRef;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.ProcessingInstruction;
import org.jdom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLReaderFactory;

public class SAXOutputter
{
  private static final String CVS_ID = "@(#) $RCSfile: SAXOutputter.java,v $ $Revision: 1.40 $ $Date: 2007/11/10 05:29:01 $ $Name: jdom_1_1_1 $";
  private static final String DECL_HANDLER_ALT_PROPERTY = "http://xml.org/sax/handlers/DeclHandler";
  private static final String DECL_HANDLER_SAX_PROPERTY = "http://xml.org/sax/properties/declaration-handler";
  private static final String LEXICAL_HANDLER_ALT_PROPERTY = "http://xml.org/sax/handlers/LexicalHandler";
  private static final String LEXICAL_HANDLER_SAX_PROPERTY = "http://xml.org/sax/properties/lexical-handler";
  private static final String NAMESPACES_SAX_FEATURE = "http://xml.org/sax/features/namespaces";
  private static final String NS_PREFIXES_SAX_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
  private static final String VALIDATION_SAX_FEATURE = "http://xml.org/sax/features/validation";
  private static final String[] attrTypeToNameMap = { "CDATA", "CDATA", "ID", "IDREF", "IDREFS", "ENTITY", "ENTITIES", "NMTOKEN", "NMTOKENS", "NOTATION", "NMTOKEN" };
  private ContentHandler contentHandler;
  private DeclHandler declHandler;
  private boolean declareNamespaces = false;
  private DTDHandler dtdHandler;
  private EntityResolver entityResolver;
  private ErrorHandler errorHandler;
  private LexicalHandler lexicalHandler;
  private JDOMLocator locator = null;
  private boolean reportDtdEvents = true;

  public SAXOutputter()
  {
  }

  public SAXOutputter(ContentHandler paramContentHandler)
  {
    this(paramContentHandler, null, null, null, null);
  }

  public SAXOutputter(ContentHandler paramContentHandler, ErrorHandler paramErrorHandler, DTDHandler paramDTDHandler, EntityResolver paramEntityResolver)
  {
    this(paramContentHandler, paramErrorHandler, paramDTDHandler, paramEntityResolver, null);
  }

  public SAXOutputter(ContentHandler paramContentHandler, ErrorHandler paramErrorHandler, DTDHandler paramDTDHandler, EntityResolver paramEntityResolver, LexicalHandler paramLexicalHandler)
  {
    this.contentHandler = paramContentHandler;
    this.errorHandler = paramErrorHandler;
    this.dtdHandler = paramDTDHandler;
    this.entityResolver = paramEntityResolver;
    this.lexicalHandler = paramLexicalHandler;
  }

  private AttributesImpl addNsAttribute(AttributesImpl paramAttributesImpl, Namespace paramNamespace)
  {
    if (this.declareNamespaces)
    {
      if (paramAttributesImpl == null)
        paramAttributesImpl = new AttributesImpl();
      if (paramNamespace.getPrefix().equals(""))
      {
        String str3 = paramNamespace.getURI();
        paramAttributesImpl.addAttribute("", "", "xmlns", "CDATA", str3);
      }
    }
    else
    {
      return paramAttributesImpl;
    }
    String str1 = "xmlns:" + paramNamespace.getPrefix();
    String str2 = paramNamespace.getURI();
    paramAttributesImpl.addAttribute("", "", str1, "CDATA", str2);
    return paramAttributesImpl;
  }

  private void cdata(String paramString)
    throws JDOMException
  {
    try
    {
      if (this.lexicalHandler != null)
      {
        this.lexicalHandler.startCDATA();
        characters(paramString);
        this.lexicalHandler.endCDATA();
        return;
      }
      characters(paramString);
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("Exception in CDATA", localSAXException);
    }
  }

  private void characters(String paramString)
    throws JDOMException
  {
    char[] arrayOfChar = paramString.toCharArray();
    try
    {
      this.contentHandler.characters(arrayOfChar, 0, arrayOfChar.length);
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("Exception in characters", localSAXException);
    }
  }

  private void comment(String paramString)
    throws JDOMException
  {
    char[] arrayOfChar;
    if (this.lexicalHandler != null)
      arrayOfChar = paramString.toCharArray();
    try
    {
      this.lexicalHandler.comment(arrayOfChar, 0, arrayOfChar.length);
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("Exception in comment", localSAXException);
    }
  }

  // ERROR //
  private XMLReader createDTDParser()
    throws JDOMException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 179	org/jdom/output/SAXOutputter:createParser	()Lorg/xml/sax/XMLReader;
    //   4: astore_2
    //   5: aload_0
    //   6: invokevirtual 183	org/jdom/output/SAXOutputter:getDTDHandler	()Lorg/xml/sax/DTDHandler;
    //   9: ifnull +13 -> 22
    //   12: aload_2
    //   13: aload_0
    //   14: invokevirtual 183	org/jdom/output/SAXOutputter:getDTDHandler	()Lorg/xml/sax/DTDHandler;
    //   17: invokeinterface 189 2 0
    //   22: aload_0
    //   23: invokevirtual 193	org/jdom/output/SAXOutputter:getEntityResolver	()Lorg/xml/sax/EntityResolver;
    //   26: ifnull +13 -> 39
    //   29: aload_2
    //   30: aload_0
    //   31: invokevirtual 193	org/jdom/output/SAXOutputter:getEntityResolver	()Lorg/xml/sax/EntityResolver;
    //   34: invokeinterface 197 2 0
    //   39: aload_0
    //   40: invokevirtual 201	org/jdom/output/SAXOutputter:getLexicalHandler	()Lorg/xml/sax/ext/LexicalHandler;
    //   43: ifnull +15 -> 58
    //   46: aload_2
    //   47: ldc 20
    //   49: aload_0
    //   50: invokevirtual 201	org/jdom/output/SAXOutputter:getLexicalHandler	()Lorg/xml/sax/ext/LexicalHandler;
    //   53: invokeinterface 205 3 0
    //   58: aload_0
    //   59: invokevirtual 209	org/jdom/output/SAXOutputter:getDeclHandler	()Lorg/xml/sax/ext/DeclHandler;
    //   62: ifnull +15 -> 77
    //   65: aload_2
    //   66: ldc 14
    //   68: aload_0
    //   69: invokevirtual 209	org/jdom/output/SAXOutputter:getDeclHandler	()Lorg/xml/sax/ext/DeclHandler;
    //   72: invokeinterface 205 3 0
    //   77: aload_2
    //   78: new 211	org/xml/sax/helpers/DefaultHandler
    //   81: dup
    //   82: invokespecial 212	org/xml/sax/helpers/DefaultHandler:<init>	()V
    //   85: invokeinterface 216 2 0
    //   90: aload_2
    //   91: areturn
    //   92: astore_1
    //   93: new 138	org/jdom/JDOMException
    //   96: dup
    //   97: ldc 218
    //   99: aload_1
    //   100: invokespecial 156	org/jdom/JDOMException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   103: athrow
    //   104: astore 5
    //   106: aload_2
    //   107: ldc 17
    //   109: aload_0
    //   110: invokevirtual 201	org/jdom/output/SAXOutputter:getLexicalHandler	()Lorg/xml/sax/ext/LexicalHandler;
    //   113: invokeinterface 205 3 0
    //   118: goto -60 -> 58
    //   121: astore 6
    //   123: goto -65 -> 58
    //   126: astore_3
    //   127: aload_2
    //   128: ldc 11
    //   130: aload_0
    //   131: invokevirtual 209	org/jdom/output/SAXOutputter:getDeclHandler	()Lorg/xml/sax/ext/DeclHandler;
    //   134: invokeinterface 205 3 0
    //   139: goto -62 -> 77
    //   142: astore 4
    //   144: goto -67 -> 77
    //
    // Exception table:
    //   from	to	target	type
    //   0	5	92	java/lang/Exception
    //   46	58	104	org/xml/sax/SAXException
    //   106	118	121	org/xml/sax/SAXException
    //   65	77	126	org/xml/sax/SAXException
    //   127	139	142	org/xml/sax/SAXException
  }

  private void documentLocator(Document paramDocument)
  {
    this.locator = new JDOMLocator();
    String str1 = null;
    String str2 = null;
    if (paramDocument != null)
    {
      DocType localDocType = paramDocument.getDocType();
      str1 = null;
      str2 = null;
      if (localDocType != null)
      {
        str1 = localDocType.getPublicID();
        str2 = localDocType.getSystemID();
      }
    }
    this.locator.setPublicId(str1);
    this.locator.setSystemId(str2);
    this.locator.setLineNumber(-1);
    this.locator.setColumnNumber(-1);
    this.contentHandler.setDocumentLocator(this.locator);
  }

  private void dtdEvents(Document paramDocument)
    throws JDOMException
  {
    DocType localDocType = paramDocument.getDocType();
    String str;
    if ((localDocType != null) && ((this.dtdHandler != null) || (this.declHandler != null)))
      str = new XMLOutputter().outputString(localDocType);
    try
    {
      createDTDParser().parse(new InputSource(new StringReader(str)));
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("DTD parsing error", localSAXException);
    }
    catch (IOException localIOException)
    {
      throw new JDOMException("DTD parsing error", localIOException);
    }
    catch (SAXParseException localSAXParseException)
    {
    }
  }

  private void element(Element paramElement, NamespaceStack paramNamespaceStack)
    throws JDOMException
  {
    int i = paramNamespaceStack.size();
    startElement(paramElement, startPrefixMapping(paramElement, paramNamespaceStack));
    elementContent(paramElement.getContent(), paramNamespaceStack);
    if (this.locator != null)
      this.locator.setNode(paramElement);
    endElement(paramElement);
    endPrefixMapping(paramNamespaceStack, i);
  }

  private void elementContent(List paramList, NamespaceStack paramNamespaceStack)
    throws JDOMException
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof Content))
        elementContent((Content)localObject, paramNamespaceStack);
      else
        handleError(new JDOMException("Invalid element content: " + localObject));
    }
  }

  private void elementContent(Content paramContent, NamespaceStack paramNamespaceStack)
    throws JDOMException
  {
    if (this.locator != null)
      this.locator.setNode(paramContent);
    if ((paramContent instanceof Element))
    {
      element((Element)paramContent, paramNamespaceStack);
      return;
    }
    if ((paramContent instanceof CDATA))
    {
      cdata(((CDATA)paramContent).getText());
      return;
    }
    if ((paramContent instanceof Text))
    {
      characters(((Text)paramContent).getText());
      return;
    }
    if ((paramContent instanceof ProcessingInstruction))
    {
      processingInstruction((ProcessingInstruction)paramContent);
      return;
    }
    if ((paramContent instanceof Comment))
    {
      comment(((Comment)paramContent).getText());
      return;
    }
    if ((paramContent instanceof EntityRef))
    {
      entityRef((EntityRef)paramContent);
      return;
    }
    handleError(new JDOMException("Invalid element content: " + paramContent));
  }

  private void endDocument()
    throws JDOMException
  {
    try
    {
      this.contentHandler.endDocument();
      this.locator = null;
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("Exception in endDocument", localSAXException);
    }
  }

  private void endElement(Element paramElement)
    throws JDOMException
  {
    String str1 = paramElement.getNamespaceURI();
    String str2 = paramElement.getName();
    String str3 = paramElement.getQualifiedName();
    try
    {
      this.contentHandler.endElement(str1, str2, str3);
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("Exception in endElement", localSAXException);
    }
  }

  private void endPrefixMapping(NamespaceStack paramNamespaceStack, int paramInt)
    throws JDOMException
  {
    while (paramNamespaceStack.size() > paramInt)
    {
      String str = paramNamespaceStack.pop();
      try
      {
        this.contentHandler.endPrefixMapping(str);
      }
      catch (SAXException localSAXException)
      {
        throw new JDOMException("Exception in endPrefixMapping", localSAXException);
      }
    }
  }

  private void entityRef(EntityRef paramEntityRef)
    throws JDOMException
  {
    if (paramEntityRef != null);
    try
    {
      this.contentHandler.skippedEntity(paramEntityRef.getName());
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("Exception in entityRef", localSAXException);
    }
  }

  private static String getAttributeTypeName(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= attrTypeToNameMap.length))
      paramInt = 0;
    return attrTypeToNameMap[paramInt];
  }

  private void handleError(JDOMException paramJDOMException)
    throws JDOMException
  {
    if (this.errorHandler != null)
      try
      {
        this.errorHandler.error(new SAXParseException(paramJDOMException.getMessage(), null, paramJDOMException));
        return;
      }
      catch (SAXException localSAXException)
      {
        if ((localSAXException.getException() instanceof JDOMException))
          throw ((JDOMException)localSAXException.getException());
        throw new JDOMException(localSAXException.getMessage(), localSAXException);
      }
    throw paramJDOMException;
  }

  private void processingInstruction(ProcessingInstruction paramProcessingInstruction)
    throws JDOMException
  {
    String str1;
    String str2;
    if (paramProcessingInstruction != null)
    {
      str1 = paramProcessingInstruction.getTarget();
      str2 = paramProcessingInstruction.getData();
    }
    try
    {
      this.contentHandler.processingInstruction(str1, str2);
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("Exception in processingInstruction", localSAXException);
    }
  }

  private void startDocument()
    throws JDOMException
  {
    try
    {
      this.contentHandler.startDocument();
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("Exception in startDocument", localSAXException);
    }
  }

  private void startElement(Element paramElement, Attributes paramAttributes)
    throws JDOMException
  {
    String str1 = paramElement.getNamespaceURI();
    String str2 = paramElement.getName();
    String str3 = paramElement.getQualifiedName();
    if (paramAttributes != null);
    for (AttributesImpl localAttributesImpl = new AttributesImpl(paramAttributes); ; localAttributesImpl = new AttributesImpl())
    {
      Iterator localIterator = paramElement.getAttributes().iterator();
      while (localIterator.hasNext())
      {
        Attribute localAttribute = (Attribute)localIterator.next();
        localAttributesImpl.addAttribute(localAttribute.getNamespaceURI(), localAttribute.getName(), localAttribute.getQualifiedName(), getAttributeTypeName(localAttribute.getAttributeType()), localAttribute.getValue());
      }
    }
    try
    {
      this.contentHandler.startElement(str1, str2, str3, localAttributesImpl);
      return;
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("Exception in startElement", localSAXException);
    }
  }

  private Attributes startPrefixMapping(Element paramElement, NamespaceStack paramNamespaceStack)
    throws JDOMException
  {
    Namespace localNamespace1 = paramElement.getNamespace();
    Namespace localNamespace2 = Namespace.XML_NAMESPACE;
    AttributesImpl localAttributesImpl = null;
    String str3;
    if (localNamespace1 != localNamespace2)
    {
      str3 = localNamespace1.getPrefix();
      String str4 = paramNamespaceStack.getURI(str3);
      boolean bool = localNamespace1.getURI().equals(str4);
      localAttributesImpl = null;
      if (!bool)
      {
        paramNamespaceStack.push(localNamespace1);
        localAttributesImpl = addNsAttribute(null, localNamespace1);
      }
    }
    try
    {
      this.contentHandler.startPrefixMapping(str3, localNamespace1.getURI());
      List localList = paramElement.getAdditionalNamespaces();
      if (localList != null)
      {
        Iterator localIterator = localList.iterator();
        while (true)
          if (localIterator.hasNext())
          {
            Namespace localNamespace3 = (Namespace)localIterator.next();
            String str1 = localNamespace3.getPrefix();
            String str2 = paramNamespaceStack.getURI(str1);
            if (localNamespace3.getURI().equals(str2))
              continue;
            paramNamespaceStack.push(localNamespace3);
            localAttributesImpl = addNsAttribute(localAttributesImpl, localNamespace3);
            try
            {
              this.contentHandler.startPrefixMapping(str1, localNamespace3.getURI());
            }
            catch (SAXException localSAXException1)
            {
              throw new JDOMException("Exception in startPrefixMapping", localSAXException1);
            }
          }
      }
    }
    catch (SAXException localSAXException2)
    {
      throw new JDOMException("Exception in startPrefixMapping", localSAXException2);
    }
    return localAttributesImpl;
  }

  protected XMLReader createParser()
    throws Exception
  {
    try
    {
      Class localClass = Class.forName("javax.xml.parsers.SAXParserFactory");
      Object localObject1 = localClass.getMethod("newInstance", null).invoke(null, null);
      Object localObject2 = localClass.getMethod("newSAXParser", null).invoke(localObject1, null);
      localXMLReader = (XMLReader)localObject2.getClass().getMethod("getXMLReader", null).invoke(localObject2, null);
      if (localXMLReader == null)
        localXMLReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
      return localXMLReader;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        localXMLReader = null;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
        localXMLReader = null;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localXMLReader = null;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      while (true)
        XMLReader localXMLReader = null;
    }
  }

  public ContentHandler getContentHandler()
  {
    return this.contentHandler;
  }

  public DTDHandler getDTDHandler()
  {
    return this.dtdHandler;
  }

  public DeclHandler getDeclHandler()
  {
    return this.declHandler;
  }

  public EntityResolver getEntityResolver()
  {
    return this.entityResolver;
  }

  public ErrorHandler getErrorHandler()
  {
    return this.errorHandler;
  }

  public boolean getFeature(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if ("http://xml.org/sax/features/namespace-prefixes".equals(paramString))
      return this.declareNamespaces;
    if ("http://xml.org/sax/features/namespaces".equals(paramString))
      return true;
    if ("http://xml.org/sax/features/validation".equals(paramString))
      return this.reportDtdEvents;
    throw new SAXNotRecognizedException(paramString);
  }

  public LexicalHandler getLexicalHandler()
  {
    return this.lexicalHandler;
  }

  public JDOMLocator getLocator()
  {
    if (this.locator != null)
      return new JDOMLocator(this.locator);
    return null;
  }

  public Object getProperty(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (("http://xml.org/sax/properties/lexical-handler".equals(paramString)) || ("http://xml.org/sax/handlers/LexicalHandler".equals(paramString)))
      return getLexicalHandler();
    if (("http://xml.org/sax/properties/declaration-handler".equals(paramString)) || ("http://xml.org/sax/handlers/DeclHandler".equals(paramString)))
      return getDeclHandler();
    throw new SAXNotRecognizedException(paramString);
  }

  public boolean getReportDTDEvents()
  {
    return this.reportDtdEvents;
  }

  public boolean getReportNamespaceDeclarations()
  {
    return this.declareNamespaces;
  }

  public void output(List paramList)
    throws JDOMException
  {
    if ((paramList == null) || (paramList.size() == 0))
      return;
    documentLocator(null);
    startDocument();
    elementContent(paramList, new NamespaceStack());
    endDocument();
  }

  public void output(Document paramDocument)
    throws JDOMException
  {
    if (paramDocument == null)
      return;
    documentLocator(paramDocument);
    startDocument();
    if (this.reportDtdEvents)
      dtdEvents(paramDocument);
    Iterator localIterator = paramDocument.getContent().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      this.locator.setNode(localObject);
      if ((localObject instanceof Element))
        element(paramDocument.getRootElement(), new NamespaceStack());
      else if ((localObject instanceof ProcessingInstruction))
        processingInstruction((ProcessingInstruction)localObject);
      else if ((localObject instanceof Comment))
        comment(((Comment)localObject).getText());
    }
    endDocument();
  }

  public void output(Element paramElement)
    throws JDOMException
  {
    if (paramElement == null)
      return;
    documentLocator(null);
    startDocument();
    elementContent(paramElement, new NamespaceStack());
    endDocument();
  }

  public void outputFragment(List paramList)
    throws JDOMException
  {
    if ((paramList == null) || (paramList.size() == 0))
      return;
    elementContent(paramList, new NamespaceStack());
  }

  public void outputFragment(Content paramContent)
    throws JDOMException
  {
    if (paramContent == null)
      return;
    elementContent(paramContent, new NamespaceStack());
  }

  public void setContentHandler(ContentHandler paramContentHandler)
  {
    this.contentHandler = paramContentHandler;
  }

  public void setDTDHandler(DTDHandler paramDTDHandler)
  {
    this.dtdHandler = paramDTDHandler;
  }

  public void setDeclHandler(DeclHandler paramDeclHandler)
  {
    this.declHandler = paramDeclHandler;
  }

  public void setEntityResolver(EntityResolver paramEntityResolver)
  {
    this.entityResolver = paramEntityResolver;
  }

  public void setErrorHandler(ErrorHandler paramErrorHandler)
  {
    this.errorHandler = paramErrorHandler;
  }

  public void setFeature(String paramString, boolean paramBoolean)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if ("http://xml.org/sax/features/namespace-prefixes".equals(paramString))
      setReportNamespaceDeclarations(paramBoolean);
    do
    {
      return;
      if (!"http://xml.org/sax/features/namespaces".equals(paramString))
        break;
    }
    while (paramBoolean == true);
    throw new SAXNotSupportedException(paramString);
    if ("http://xml.org/sax/features/validation".equals(paramString))
    {
      setReportDTDEvents(paramBoolean);
      return;
    }
    throw new SAXNotRecognizedException(paramString);
  }

  public void setLexicalHandler(LexicalHandler paramLexicalHandler)
  {
    this.lexicalHandler = paramLexicalHandler;
  }

  public void setProperty(String paramString, Object paramObject)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (("http://xml.org/sax/properties/lexical-handler".equals(paramString)) || ("http://xml.org/sax/handlers/LexicalHandler".equals(paramString)))
    {
      setLexicalHandler((LexicalHandler)paramObject);
      return;
    }
    if (("http://xml.org/sax/properties/declaration-handler".equals(paramString)) || ("http://xml.org/sax/handlers/DeclHandler".equals(paramString)))
    {
      setDeclHandler((DeclHandler)paramObject);
      return;
    }
    throw new SAXNotRecognizedException(paramString);
  }

  public void setReportDTDEvents(boolean paramBoolean)
  {
    this.reportDtdEvents = paramBoolean;
  }

  public void setReportNamespaceDeclarations(boolean paramBoolean)
  {
    this.declareNamespaces = paramBoolean;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.output.SAXOutputter
 * JD-Core Version:    0.6.2
 */