package org.jdom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jdom.filter.Filter;

public class Document
  implements Parent
{
  private static final String CVS_ID = "@(#) $RCSfile: Document.java,v $ $Revision: 1.85 $ $Date: 2007/11/10 05:28:58 $ $Name: jdom_1_1_1 $";
  protected String baseURI = null;
  ContentList content = new ContentList(this);
  private HashMap propertyMap = null;

  public Document()
  {
  }

  public Document(List paramList)
  {
    setContent(paramList);
  }

  public Document(Element paramElement)
  {
    this(paramElement, null, null);
  }

  public Document(Element paramElement, DocType paramDocType)
  {
    this(paramElement, paramDocType, null);
  }

  public Document(Element paramElement, DocType paramDocType, String paramString)
  {
    if (paramElement != null)
      setRootElement(paramElement);
    if (paramDocType != null)
      setDocType(paramDocType);
    if (paramString != null)
      setBaseURI(paramString);
  }

  public Document addContent(int paramInt, Collection paramCollection)
  {
    this.content.addAll(paramInt, paramCollection);
    return this;
  }

  public Document addContent(int paramInt, Content paramContent)
  {
    this.content.add(paramInt, paramContent);
    return this;
  }

  public Document addContent(Collection paramCollection)
  {
    this.content.addAll(paramCollection);
    return this;
  }

  public Document addContent(Content paramContent)
  {
    this.content.add(paramContent);
    return this;
  }

  public Object clone()
  {
    try
    {
      localDocument = (Document)super.clone();
      localDocument.content = new ContentList(localDocument);
      int i = 0;
      if (i < this.content.size())
      {
        Object localObject = this.content.get(i);
        if ((localObject instanceof Element))
        {
          Element localElement = (Element)((Element)localObject).clone();
          localDocument.content.add(localElement);
        }
        while (true)
        {
          i++;
          break;
          if ((localObject instanceof Comment))
          {
            Comment localComment = (Comment)((Comment)localObject).clone();
            localDocument.content.add(localComment);
          }
          else if ((localObject instanceof ProcessingInstruction))
          {
            ProcessingInstruction localProcessingInstruction = (ProcessingInstruction)((ProcessingInstruction)localObject).clone();
            localDocument.content.add(localProcessingInstruction);
          }
          else if ((localObject instanceof DocType))
          {
            DocType localDocType = (DocType)((DocType)localObject).clone();
            localDocument.content.add(localDocType);
          }
        }
      }
      return localDocument;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      while (true)
        Document localDocument = null;
    }
  }

  public List cloneContent()
  {
    int i = getContentSize();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++)
      localArrayList.add(getContent(j).clone());
    return localArrayList;
  }

  public Element detachRootElement()
  {
    int i = this.content.indexOfFirstElement();
    if (i < 0)
      return null;
    return (Element)removeContent(i);
  }

  public final boolean equals(Object paramObject)
  {
    return paramObject == this;
  }

  public final String getBaseURI()
  {
    return this.baseURI;
  }

  public List getContent()
  {
    if (!hasRootElement())
      throw new IllegalStateException("Root element not set");
    return this.content;
  }

  public List getContent(Filter paramFilter)
  {
    if (!hasRootElement())
      throw new IllegalStateException("Root element not set");
    return this.content.getView(paramFilter);
  }

  public Content getContent(int paramInt)
  {
    return (Content)this.content.get(paramInt);
  }

  public int getContentSize()
  {
    return this.content.size();
  }

  public Iterator getDescendants()
  {
    return new DescendantIterator(this);
  }

  public Iterator getDescendants(Filter paramFilter)
  {
    return new FilterIterator(new DescendantIterator(this), paramFilter);
  }

  public DocType getDocType()
  {
    int i = this.content.indexOfDocType();
    if (i < 0)
      return null;
    return (DocType)this.content.get(i);
  }

  public Document getDocument()
  {
    return this;
  }

  public Parent getParent()
  {
    return null;
  }

  public Object getProperty(String paramString)
  {
    if (this.propertyMap == null)
      return null;
    return this.propertyMap.get(paramString);
  }

  public Element getRootElement()
  {
    int i = this.content.indexOfFirstElement();
    if (i < 0)
      throw new IllegalStateException("Root element not set");
    return (Element)this.content.get(i);
  }

  public boolean hasRootElement()
  {
    return this.content.indexOfFirstElement() >= 0;
  }

  public final int hashCode()
  {
    return super.hashCode();
  }

  public int indexOf(Content paramContent)
  {
    return this.content.indexOf(paramContent);
  }

  public List removeContent()
  {
    ArrayList localArrayList = new ArrayList(this.content);
    this.content.clear();
    return localArrayList;
  }

  public List removeContent(Filter paramFilter)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.content.getView(paramFilter).iterator();
    while (localIterator.hasNext())
    {
      localArrayList.add((Content)localIterator.next());
      localIterator.remove();
    }
    return localArrayList;
  }

  public Content removeContent(int paramInt)
  {
    return (Content)this.content.remove(paramInt);
  }

  public boolean removeContent(Content paramContent)
  {
    return this.content.remove(paramContent);
  }

  public final void setBaseURI(String paramString)
  {
    this.baseURI = paramString;
  }

  public Document setContent(int paramInt, Collection paramCollection)
  {
    this.content.remove(paramInt);
    this.content.addAll(paramInt, paramCollection);
    return this;
  }

  public Document setContent(int paramInt, Content paramContent)
  {
    this.content.set(paramInt, paramContent);
    return this;
  }

  public Document setContent(Collection paramCollection)
  {
    this.content.clearAndSet(paramCollection);
    return this;
  }

  public Document setContent(Content paramContent)
  {
    this.content.clear();
    this.content.add(paramContent);
    return this;
  }

  public Document setDocType(DocType paramDocType)
  {
    if (paramDocType == null)
    {
      int j = this.content.indexOfDocType();
      if (j >= 0)
        this.content.remove(j);
      return this;
    }
    if (paramDocType.getParent() != null)
      throw new IllegalAddException(paramDocType, "The DocType already is attached to a document");
    int i = this.content.indexOfDocType();
    if (i < 0)
    {
      this.content.add(0, paramDocType);
      return this;
    }
    this.content.set(i, paramDocType);
    return this;
  }

  public void setProperty(String paramString, Object paramObject)
  {
    if (this.propertyMap == null)
      this.propertyMap = new HashMap();
    this.propertyMap.put(paramString, paramObject);
  }

  public Document setRootElement(Element paramElement)
  {
    int i = this.content.indexOfFirstElement();
    if (i < 0)
    {
      this.content.add(paramElement);
      return this;
    }
    this.content.set(i, paramElement);
    return this;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer().append("[Document: ");
    DocType localDocType = getDocType();
    if (localDocType != null)
    {
      localStringBuffer.append(localDocType.toString()).append(", ");
      Element localElement = getRootElement();
      if (localElement == null)
        break label84;
      localStringBuffer.append("Root is ").append(localElement.toString());
    }
    while (true)
    {
      localStringBuffer.append("]");
      return localStringBuffer.toString();
      localStringBuffer.append(" No DOCTYPE declaration, ");
      break;
      label84: localStringBuffer.append(" No root element");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.Document
 * JD-Core Version:    0.6.2
 */