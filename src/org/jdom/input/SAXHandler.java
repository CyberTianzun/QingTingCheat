package org.jdom.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jdom.Attribute;
import org.jdom.DefaultJDOMFactory;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.EntityRef;
import org.jdom.JDOMFactory;
import org.jdom.Namespace;
import org.jdom.Parent;
import org.xml.sax.Attributes;
import org.xml.sax.DTDHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler
  implements LexicalHandler, DeclHandler, DTDHandler
{
  private static final String CVS_ID = "@(#) $RCSfile: SAXHandler.java,v $ $Revision: 1.73 $ $Date: 2007/11/10 05:29:00 $ $Name: jdom_1_1_1 $";
  private static final Map attrNameToTypeMap = new HashMap(13);
  private boolean atRoot;
  private Element currentElement;
  private List declaredNamespaces;
  private Document document;
  private int entityDepth = 0;
  private boolean expand = true;
  private Map externalEntities;
  private JDOMFactory factory;
  private boolean ignoringBoundaryWhite = false;
  private boolean ignoringWhite = false;
  private boolean inCDATA = false;
  private boolean inDTD = false;
  private boolean inInternalSubset = false;
  private StringBuffer internalSubset = new StringBuffer();
  private Locator locator;
  private boolean previousCDATA = false;
  private boolean suppress = false;
  private TextBuffer textBuffer = new TextBuffer();

  static
  {
    attrNameToTypeMap.put("CDATA", new Integer(1));
    attrNameToTypeMap.put("ID", new Integer(2));
    attrNameToTypeMap.put("IDREF", new Integer(3));
    attrNameToTypeMap.put("IDREFS", new Integer(4));
    attrNameToTypeMap.put("ENTITY", new Integer(5));
    attrNameToTypeMap.put("ENTITIES", new Integer(6));
    attrNameToTypeMap.put("NMTOKEN", new Integer(7));
    attrNameToTypeMap.put("NMTOKENS", new Integer(8));
    attrNameToTypeMap.put("NOTATION", new Integer(9));
    attrNameToTypeMap.put("ENUMERATION", new Integer(10));
  }

  public SAXHandler()
  {
    this(null);
  }

  public SAXHandler(JDOMFactory paramJDOMFactory)
  {
    if (paramJDOMFactory != null);
    for (this.factory = paramJDOMFactory; ; this.factory = new DefaultJDOMFactory())
    {
      this.atRoot = true;
      this.declaredNamespaces = new ArrayList();
      this.externalEntities = new HashMap();
      this.document = this.factory.document(null);
      return;
    }
  }

  private void appendExternalId(String paramString1, String paramString2)
  {
    if (paramString1 != null)
      this.internalSubset.append(" PUBLIC \"").append(paramString1).append('"');
    if (paramString2 != null)
    {
      if (paramString1 != null)
        break label61;
      this.internalSubset.append(" SYSTEM ");
    }
    while (true)
    {
      this.internalSubset.append('"').append(paramString2).append('"');
      return;
      label61: this.internalSubset.append(' ');
    }
  }

  private static int getAttributeType(String paramString)
  {
    Integer localInteger = (Integer)attrNameToTypeMap.get(paramString);
    if (localInteger == null)
    {
      if ((paramString != null) && (paramString.length() > 0) && (paramString.charAt(0) == '('))
        return 10;
      return 0;
    }
    return localInteger.intValue();
  }

  private void transferNamespaces(Element paramElement)
  {
    Iterator localIterator = this.declaredNamespaces.iterator();
    while (localIterator.hasNext())
    {
      Namespace localNamespace = (Namespace)localIterator.next();
      if (localNamespace != paramElement.getNamespace())
        paramElement.addNamespaceDeclaration(localNamespace);
    }
    this.declaredNamespaces.clear();
  }

  public void attributeDecl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws SAXException
  {
    if (!this.inInternalSubset)
      return;
    this.internalSubset.append("  <!ATTLIST ").append(paramString1).append(' ').append(paramString2).append(' ').append(paramString3).append(' ');
    if (paramString4 != null)
      this.internalSubset.append(paramString4);
    while (true)
    {
      if ((paramString4 != null) && (paramString4.equals("#FIXED")))
        this.internalSubset.append(" \"").append(paramString5).append('"');
      this.internalSubset.append(">\n");
      return;
      this.internalSubset.append('"').append(paramString5).append('"');
    }
  }

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if ((this.suppress) || (paramInt2 == 0))
      return;
    if (this.previousCDATA != this.inCDATA)
      flushCharacters();
    this.textBuffer.append(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void comment(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.suppress);
    String str;
    do
    {
      return;
      flushCharacters();
      str = new String(paramArrayOfChar, paramInt1, paramInt2);
      if ((this.inDTD) && (this.inInternalSubset) && (!this.expand))
      {
        this.internalSubset.append("  <!--").append(str).append("-->\n");
        return;
      }
    }
    while ((this.inDTD) || (str.equals("")));
    if (this.atRoot)
    {
      this.factory.addContent(this.document, this.factory.comment(str));
      return;
    }
    this.factory.addContent(getCurrentElement(), this.factory.comment(str));
  }

  public void elementDecl(String paramString1, String paramString2)
    throws SAXException
  {
    if (!this.inInternalSubset)
      return;
    this.internalSubset.append("  <!ELEMENT ").append(paramString1).append(' ').append(paramString2).append(">\n");
  }

  public void endCDATA()
    throws SAXException
  {
    if (this.suppress)
      return;
    this.previousCDATA = true;
    this.inCDATA = false;
  }

  public void endDTD()
    throws SAXException
  {
    this.document.getDocType().setInternalSubset(this.internalSubset.toString());
    this.inDTD = false;
    this.inInternalSubset = false;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (this.suppress)
      return;
    flushCharacters();
    if (!this.atRoot)
    {
      Parent localParent = this.currentElement.getParent();
      if ((localParent instanceof Document))
      {
        this.atRoot = true;
        return;
      }
      this.currentElement = ((Element)localParent);
      return;
    }
    throw new SAXException("Ill-formed XML document (missing opening tag for " + paramString2 + ")");
  }

  public void endEntity(String paramString)
    throws SAXException
  {
    this.entityDepth = (-1 + this.entityDepth);
    if (this.entityDepth == 0)
      this.suppress = false;
    if (paramString.equals("[dtd]"))
      this.inInternalSubset = true;
  }

  public void externalEntityDecl(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    this.externalEntities.put(paramString1, new String[] { paramString2, paramString3 });
    if (!this.inInternalSubset)
      return;
    this.internalSubset.append("  <!ENTITY ").append(paramString1);
    appendExternalId(paramString2, paramString3);
    this.internalSubset.append(">\n");
  }

  protected void flushCharacters()
    throws SAXException
  {
    if (this.ignoringBoundaryWhite)
      if (!this.textBuffer.isAllWhitespace())
        flushCharacters(this.textBuffer.toString());
    while (true)
    {
      this.textBuffer.clear();
      return;
      flushCharacters(this.textBuffer.toString());
    }
  }

  protected void flushCharacters(String paramString)
    throws SAXException
  {
    if (paramString.length() == 0)
    {
      this.previousCDATA = this.inCDATA;
      return;
    }
    if (this.previousCDATA)
      this.factory.addContent(getCurrentElement(), this.factory.cdata(paramString));
    while (true)
    {
      this.previousCDATA = this.inCDATA;
      return;
      this.factory.addContent(getCurrentElement(), this.factory.text(paramString));
    }
  }

  public Element getCurrentElement()
    throws SAXException
  {
    if (this.currentElement == null)
      throw new SAXException("Ill-formed XML document (multiple root elements detected)");
    return this.currentElement;
  }

  public Document getDocument()
  {
    return this.document;
  }

  public Locator getDocumentLocator()
  {
    return this.locator;
  }

  public boolean getExpandEntities()
  {
    return this.expand;
  }

  public JDOMFactory getFactory()
  {
    return this.factory;
  }

  public boolean getIgnoringBoundaryWhitespace()
  {
    return this.ignoringBoundaryWhite;
  }

  public boolean getIgnoringElementContentWhitespace()
  {
    return this.ignoringWhite;
  }

  public void ignorableWhitespace(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (!this.ignoringWhite)
      characters(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void internalEntityDecl(String paramString1, String paramString2)
    throws SAXException
  {
    if (!this.inInternalSubset)
      return;
    this.internalSubset.append("  <!ENTITY ");
    if (paramString1.startsWith("%"))
      this.internalSubset.append("% ").append(paramString1.substring(1));
    while (true)
    {
      this.internalSubset.append(" \"").append(paramString2).append("\">\n");
      return;
      this.internalSubset.append(paramString1);
    }
  }

  public void notationDecl(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (!this.inInternalSubset)
      return;
    this.internalSubset.append("  <!NOTATION ").append(paramString1);
    appendExternalId(paramString2, paramString3);
    this.internalSubset.append(">\n");
  }

  public void processingInstruction(String paramString1, String paramString2)
    throws SAXException
  {
    if (this.suppress)
      return;
    flushCharacters();
    if (this.atRoot)
    {
      this.factory.addContent(this.document, this.factory.processingInstruction(paramString1, paramString2));
      return;
    }
    this.factory.addContent(getCurrentElement(), this.factory.processingInstruction(paramString1, paramString2));
  }

  protected void pushElement(Element paramElement)
  {
    if (this.atRoot)
    {
      this.document.setRootElement(paramElement);
      this.atRoot = false;
    }
    while (true)
    {
      this.currentElement = paramElement;
      return;
      this.factory.addContent(this.currentElement, paramElement);
    }
  }

  public void setDocumentLocator(Locator paramLocator)
  {
    this.locator = paramLocator;
  }

  public void setExpandEntities(boolean paramBoolean)
  {
    this.expand = paramBoolean;
  }

  public void setIgnoringBoundaryWhitespace(boolean paramBoolean)
  {
    this.ignoringBoundaryWhite = paramBoolean;
  }

  public void setIgnoringElementContentWhitespace(boolean paramBoolean)
  {
    this.ignoringWhite = paramBoolean;
  }

  public void skippedEntity(String paramString)
    throws SAXException
  {
    if (paramString.startsWith("%"))
      return;
    flushCharacters();
    this.factory.addContent(getCurrentElement(), this.factory.entityRef(paramString));
  }

  public void startCDATA()
    throws SAXException
  {
    if (this.suppress)
      return;
    this.inCDATA = true;
  }

  public void startDTD(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    flushCharacters();
    this.factory.addContent(this.document, this.factory.docType(paramString1, paramString2, paramString3));
    this.inDTD = true;
    this.inInternalSubset = true;
  }

  public void startDocument()
  {
    if (this.locator != null)
      this.document.setBaseURI(this.locator.getSystemId());
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (this.suppress)
      return;
    Namespace localNamespace2;
    if ((paramString1 != null) && (!paramString1.equals("")))
    {
      String str3 = "";
      if ((!paramString3.equals(paramString2)) && (paramString3.length() > 0))
        str3 = paramString3.substring(0, paramString3.indexOf(":"));
      localNamespace2 = Namespace.getNamespace(str3, paramString1);
    }
    int i;
    String str1;
    String str2;
    int k;
    for (Element localElement = this.factory.element(paramString2, localNamespace2); ; localElement = this.factory.element(paramString2))
    {
      if (this.declaredNamespaces.size() > 0)
        transferNamespaces(localElement);
      i = 0;
      int j = paramAttributes.getLength();
      while (true)
      {
        if (i >= j)
          break label356;
        str1 = paramAttributes.getLocalName(i);
        str2 = paramAttributes.getQName(i);
        k = getAttributeType(paramAttributes.getType(i));
        if ((!str2.startsWith("xmlns:")) && (!str2.equals("xmlns")))
          break;
        i++;
      }
    }
    Attribute localAttribute;
    if (("".equals(str1)) && (str2.indexOf(":") == -1))
      localAttribute = this.factory.attribute(str2, paramAttributes.getValue(i), k);
    while (true)
    {
      this.factory.setAttribute(localElement, localAttribute);
      break;
      if ((!str2.equals(str1)) && (str2.length() > 0))
      {
        Namespace localNamespace1 = Namespace.getNamespace(str2.substring(0, str2.indexOf(":")), paramAttributes.getURI(i));
        localAttribute = this.factory.attribute(str1, paramAttributes.getValue(i), k, localNamespace1);
      }
      else
      {
        localAttribute = this.factory.attribute(str1, paramAttributes.getValue(i), k);
      }
    }
    label356: flushCharacters();
    if (this.atRoot)
    {
      this.document.setRootElement(localElement);
      this.atRoot = false;
    }
    while (true)
    {
      this.currentElement = localElement;
      return;
      this.factory.addContent(getCurrentElement(), localElement);
    }
  }

  public void startEntity(String paramString)
    throws SAXException
  {
    this.entityDepth = (1 + this.entityDepth);
    if ((this.expand) || (this.entityDepth > 1));
    do
    {
      return;
      if (paramString.equals("[dtd]"))
      {
        this.inInternalSubset = false;
        return;
      }
    }
    while ((this.inDTD) || (paramString.equals("amp")) || (paramString.equals("lt")) || (paramString.equals("gt")) || (paramString.equals("apos")) || (paramString.equals("quot")) || (this.expand));
    String[] arrayOfString = (String[])this.externalEntities.get(paramString);
    String str1 = null;
    String str2 = null;
    if (arrayOfString != null)
    {
      str1 = arrayOfString[0];
      str2 = arrayOfString[1];
    }
    if (!this.atRoot)
    {
      flushCharacters();
      EntityRef localEntityRef = this.factory.entityRef(paramString, str1, str2);
      this.factory.addContent(getCurrentElement(), localEntityRef);
    }
    this.suppress = true;
  }

  public void startPrefixMapping(String paramString1, String paramString2)
    throws SAXException
  {
    if (this.suppress)
      return;
    Namespace localNamespace = Namespace.getNamespace(paramString1, paramString2);
    this.declaredNamespaces.add(localNamespace);
  }

  public void unparsedEntityDecl(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SAXException
  {
    if (!this.inInternalSubset)
      return;
    this.internalSubset.append("  <!ENTITY ").append(paramString1);
    appendExternalId(paramString2, paramString3);
    this.internalSubset.append(" NDATA ").append(paramString4);
    this.internalSubset.append(">\n");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.input.SAXHandler
 * JD-Core Version:    0.6.2
 */