package org.jdom.output;

import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.DocType;
import org.jdom.EntityRef;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.ProcessingInstruction;
import org.jdom.Text;
import org.jdom.adapters.DOMAdapter;
import org.w3c.dom.Attr;

public class DOMOutputter
{
  private static final String CVS_ID = "@(#) $RCSfile: DOMOutputter.java,v $ $Revision: 1.43 $ $Date: 2007/11/10 05:29:01 $ $Name: jdom_1_1_1 $";
  private static final String DEFAULT_ADAPTER_CLASS = "org.jdom.adapters.XercesDOMAdapter";
  private String adapterClass;
  private boolean forceNamespaceAware;

  public DOMOutputter()
  {
  }

  public DOMOutputter(String paramString)
  {
    this.adapterClass = paramString;
  }

  private org.w3c.dom.Document createDOMDocument(DocType paramDocType)
    throws JDOMException
  {
    if (this.adapterClass != null);
    try
    {
      org.w3c.dom.Document localDocument3 = ((DOMAdapter)Class.forName(this.adapterClass).newInstance()).createDocument(paramDocType);
      return localDocument3;
      try
      {
        org.w3c.dom.Document localDocument2 = ((DOMAdapter)Class.forName("org.jdom.adapters.JAXPDOMAdapter").newInstance()).createDocument(paramDocType);
        return localDocument2;
      }
      catch (InstantiationException localInstantiationException2)
      {
        try
        {
          org.w3c.dom.Document localDocument1 = ((DOMAdapter)Class.forName("org.jdom.adapters.XercesDOMAdapter").newInstance()).createDocument(paramDocType);
          return localDocument1;
        }
        catch (InstantiationException localInstantiationException1)
        {
          throw new JDOMException("No JAXP or default parser available");
        }
        catch (IllegalAccessException localIllegalAccessException1)
        {
          break label79;
        }
        catch (ClassNotFoundException localClassNotFoundException2)
        {
          break label79;
        }
      }
      catch (IllegalAccessException localIllegalAccessException2)
      {
        break label55;
      }
      catch (ClassNotFoundException localClassNotFoundException1)
      {
        label55: label79: break label55;
      }
    }
    catch (InstantiationException localInstantiationException3)
    {
      break label55;
    }
    catch (IllegalAccessException localIllegalAccessException3)
    {
      break label55;
    }
    catch (ClassNotFoundException localClassNotFoundException3)
    {
      break label55;
    }
  }

  private static String getXmlnsTagFor(Namespace paramNamespace)
  {
    String str1 = "xmlns";
    if (!paramNamespace.getPrefix().equals(""))
    {
      String str2 = str1 + ":";
      str1 = str2 + paramNamespace.getPrefix();
    }
    return str1;
  }

  private Attr output(Attribute paramAttribute, org.w3c.dom.Document paramDocument)
    throws JDOMException
  {
    try
    {
      Object localObject;
      if (paramAttribute.getNamespace() == Namespace.NO_NAMESPACE)
        if (this.forceNamespaceAware)
          localObject = paramDocument.createAttributeNS(null, paramAttribute.getQualifiedName());
      while (true)
      {
        ((Attr)localObject).setValue(paramAttribute.getValue());
        return localObject;
        localObject = paramDocument.createAttribute(paramAttribute.getQualifiedName());
        continue;
        Attr localAttr = paramDocument.createAttributeNS(paramAttribute.getNamespaceURI(), paramAttribute.getQualifiedName());
        localObject = localAttr;
      }
    }
    catch (Exception localException)
    {
      throw new JDOMException("Exception outputting Attribute " + paramAttribute.getQualifiedName(), localException);
    }
  }

  private org.w3c.dom.Element output(org.jdom.Element paramElement, org.w3c.dom.Document paramDocument, NamespaceStack paramNamespaceStack)
    throws JDOMException
  {
    int i;
    org.w3c.dom.Element localElement;
    while (true)
    {
      try
      {
        i = paramNamespaceStack.size();
        if (paramElement.getNamespace() != Namespace.NO_NAMESPACE)
          break label250;
        if (this.forceNamespaceAware)
        {
          localElement = paramDocument.createElementNS(null, paramElement.getQualifiedName());
          Namespace localNamespace1 = paramElement.getNamespace();
          if ((localNamespace1 != Namespace.XML_NAMESPACE) && ((localNamespace1 != Namespace.NO_NAMESPACE) || (paramNamespaceStack.getURI("") != null)))
          {
            String str3 = paramNamespaceStack.getURI(localNamespace1.getPrefix());
            if (!localNamespace1.getURI().equals(str3))
            {
              paramNamespaceStack.push(localNamespace1);
              localElement.setAttribute(getXmlnsTagFor(localNamespace1), localNamespace1.getURI());
            }
          }
          Iterator localIterator1 = paramElement.getAdditionalNamespaces().iterator();
          if (!localIterator1.hasNext())
            break;
          Namespace localNamespace3 = (Namespace)localIterator1.next();
          String str2 = paramNamespaceStack.getURI(localNamespace3.getPrefix());
          if (localNamespace3.getURI().equals(str2))
            continue;
          localElement.setAttribute(getXmlnsTagFor(localNamespace3), localNamespace3.getURI());
          paramNamespaceStack.push(localNamespace3);
          continue;
        }
      }
      catch (Exception localException)
      {
        JDOMException localJDOMException = new JDOMException("Exception outputting Element " + paramElement.getQualifiedName(), localException);
        throw localJDOMException;
      }
      localElement = paramDocument.createElement(paramElement.getQualifiedName());
      continue;
      label250: localElement = paramDocument.createElementNS(paramElement.getNamespaceURI(), paramElement.getQualifiedName());
    }
    Iterator localIterator2 = paramElement.getAttributes().iterator();
    while (localIterator2.hasNext())
    {
      Attribute localAttribute = (Attribute)localIterator2.next();
      localElement.setAttributeNode(output(localAttribute, paramDocument));
      Namespace localNamespace2 = localAttribute.getNamespace();
      if ((localNamespace2 != Namespace.NO_NAMESPACE) && (localNamespace2 != Namespace.XML_NAMESPACE))
      {
        String str1 = paramNamespaceStack.getURI(localNamespace2.getPrefix());
        if (!localNamespace2.getURI().equals(str1))
        {
          localElement.setAttribute(getXmlnsTagFor(localNamespace2), localNamespace2.getURI());
          paramNamespaceStack.push(localNamespace2);
        }
      }
      if (localAttribute.getNamespace() == Namespace.NO_NAMESPACE)
      {
        if (this.forceNamespaceAware)
          localElement.setAttributeNS(null, localAttribute.getQualifiedName(), localAttribute.getValue());
        else
          localElement.setAttribute(localAttribute.getQualifiedName(), localAttribute.getValue());
      }
      else
        localElement.setAttributeNS(localAttribute.getNamespaceURI(), localAttribute.getQualifiedName(), localAttribute.getValue());
    }
    Iterator localIterator3 = paramElement.getContent().iterator();
    while (localIterator3.hasNext())
    {
      Object localObject = localIterator3.next();
      if ((localObject instanceof org.jdom.Element))
      {
        localElement.appendChild(output((org.jdom.Element)localObject, paramDocument, paramNamespaceStack));
      }
      else if ((localObject instanceof String))
      {
        localElement.appendChild(paramDocument.createTextNode((String)localObject));
      }
      else if ((localObject instanceof CDATA))
      {
        localElement.appendChild(paramDocument.createCDATASection(((CDATA)localObject).getText()));
      }
      else if ((localObject instanceof Text))
      {
        localElement.appendChild(paramDocument.createTextNode(((Text)localObject).getText()));
      }
      else if ((localObject instanceof Comment))
      {
        localElement.appendChild(paramDocument.createComment(((Comment)localObject).getText()));
      }
      else if ((localObject instanceof ProcessingInstruction))
      {
        ProcessingInstruction localProcessingInstruction = (ProcessingInstruction)localObject;
        localElement.appendChild(paramDocument.createProcessingInstruction(localProcessingInstruction.getTarget(), localProcessingInstruction.getData()));
      }
      else if ((localObject instanceof EntityRef))
      {
        localElement.appendChild(paramDocument.createEntityReference(((EntityRef)localObject).getName()));
      }
      else
      {
        throw new JDOMException("Element contained content with type:" + localObject.getClass().getName());
      }
    }
    while (paramNamespaceStack.size() > i)
      paramNamespaceStack.pop();
    return localElement;
  }

  public boolean getForceNamespaceAware()
  {
    return this.forceNamespaceAware;
  }

  public org.w3c.dom.Document output(org.jdom.Document paramDocument)
    throws JDOMException
  {
    NamespaceStack localNamespaceStack = new NamespaceStack();
    org.w3c.dom.Document localDocument;
    Object localObject;
    label125: 
    do
      while (true)
      {
        org.w3c.dom.Element localElement1;
        org.w3c.dom.Element localElement2;
        try
        {
          localDocument = createDOMDocument(paramDocument.getDocType());
          Iterator localIterator = paramDocument.getContent().iterator();
          if (!localIterator.hasNext())
            break label245;
          localObject = localIterator.next();
          if (!(localObject instanceof org.jdom.Element))
            break label125;
          localElement1 = output((org.jdom.Element)localObject, localDocument, localNamespaceStack);
          localElement2 = localDocument.getDocumentElement();
          if (localElement2 == null)
          {
            localDocument.appendChild(localElement1);
            continue;
          }
        }
        catch (Throwable localThrowable)
        {
          throw new JDOMException("Exception outputting Document", localThrowable);
        }
        localDocument.replaceChild(localElement1, localElement2);
        continue;
        if ((localObject instanceof Comment))
        {
          localDocument.appendChild(localDocument.createComment(((Comment)localObject).getText()));
        }
        else
        {
          if (!(localObject instanceof ProcessingInstruction))
            break;
          ProcessingInstruction localProcessingInstruction = (ProcessingInstruction)localObject;
          localDocument.appendChild(localDocument.createProcessingInstruction(localProcessingInstruction.getTarget(), localProcessingInstruction.getData()));
        }
      }
    while ((localObject instanceof DocType));
    throw new JDOMException("Document contained top-level content with type:" + localObject.getClass().getName());
    label245: return localDocument;
  }

  public void setForceNamespaceAware(boolean paramBoolean)
  {
    this.forceNamespaceAware = paramBoolean;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.output.DOMOutputter
 * JD-Core Version:    0.6.2
 */