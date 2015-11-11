package org.jdom.xpath;

import java.util.List;
import org.jaxen.JaxenException;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.SimpleVariableContext;
import org.jaxen.VariableContext;
import org.jaxen.jdom.JDOMXPath;
import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;

class JaxenXPath extends XPath
{
  private static final String CVS_ID = "@(#) $RCSfile: JaxenXPath.java,v $ $Revision: 1.20 $ $Date: 2007/11/10 05:29:02 $ $Name: jdom_1_1_1 $";
  private Object currentContext;
  private transient JDOMXPath xPath;

  public JaxenXPath(String paramString)
    throws JDOMException
  {
    setXPath(paramString);
  }

  private void setXPath(String paramString)
    throws JDOMException
  {
    try
    {
      this.xPath = new JDOMXPath(paramString);
      this.xPath.setNamespaceContext(new NSContext());
      return;
    }
    catch (Exception localException)
    {
      throw new JDOMException("Invalid XPath expression: \"" + paramString + "\"", localException);
    }
  }

  public void addNamespace(Namespace paramNamespace)
  {
    try
    {
      this.xPath.addNamespace(paramNamespace.getPrefix(), paramNamespace.getURI());
      return;
    }
    catch (JaxenException localJaxenException)
    {
    }
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof JaxenXPath;
    boolean bool2 = false;
    if (bool1)
    {
      JaxenXPath localJaxenXPath = (JaxenXPath)paramObject;
      boolean bool3 = super.equals(paramObject);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.xPath.toString().equals(localJaxenXPath.xPath.toString());
        bool2 = false;
        if (bool4)
          bool2 = true;
      }
    }
    return bool2;
  }

  public String getXPath()
  {
    return this.xPath.toString();
  }

  public int hashCode()
  {
    return this.xPath.hashCode();
  }

  public Number numberValueOf(Object paramObject)
    throws JDOMException
  {
    try
    {
      this.currentContext = paramObject;
      Number localNumber = this.xPath.numberValueOf(paramObject);
      return localNumber;
    }
    catch (JaxenException localJaxenException)
    {
      throw new JDOMException("XPath error while evaluating \"" + this.xPath.toString() + "\": " + localJaxenException.getMessage(), localJaxenException);
    }
    finally
    {
      this.currentContext = null;
    }
  }

  public List selectNodes(Object paramObject)
    throws JDOMException
  {
    try
    {
      this.currentContext = paramObject;
      List localList = this.xPath.selectNodes(paramObject);
      return localList;
    }
    catch (JaxenException localJaxenException)
    {
      throw new JDOMException("XPath error while evaluating \"" + this.xPath.toString() + "\": " + localJaxenException.getMessage(), localJaxenException);
    }
    finally
    {
      this.currentContext = null;
    }
  }

  public Object selectSingleNode(Object paramObject)
    throws JDOMException
  {
    try
    {
      this.currentContext = paramObject;
      Object localObject2 = this.xPath.selectSingleNode(paramObject);
      return localObject2;
    }
    catch (JaxenException localJaxenException)
    {
      throw new JDOMException("XPath error while evaluating \"" + this.xPath.toString() + "\": " + localJaxenException.getMessage(), localJaxenException);
    }
    finally
    {
      this.currentContext = null;
    }
  }

  public void setVariable(String paramString, Object paramObject)
    throws IllegalArgumentException
  {
    VariableContext localVariableContext = this.xPath.getVariableContext();
    if ((localVariableContext instanceof SimpleVariableContext))
      ((SimpleVariableContext)localVariableContext).setVariableValue(null, paramString, paramObject);
  }

  public String toString()
  {
    return this.xPath.toString();
  }

  public String valueOf(Object paramObject)
    throws JDOMException
  {
    try
    {
      this.currentContext = paramObject;
      String str = this.xPath.stringValueOf(paramObject);
      return str;
    }
    catch (JaxenException localJaxenException)
    {
      throw new JDOMException("XPath error while evaluating \"" + this.xPath.toString() + "\": " + localJaxenException.getMessage(), localJaxenException);
    }
    finally
    {
      this.currentContext = null;
    }
  }

  private class NSContext extends SimpleNamespaceContext
  {
    public NSContext()
    {
    }

    public String translateNamespacePrefixToUri(String paramString)
    {
      String str;
      if ((paramString == null) || (paramString.length() == 0))
        str = null;
      label136: 
      while (true)
      {
        return str;
        str = super.translateNamespacePrefixToUri(paramString);
        if (str == null)
        {
          Object localObject = JaxenXPath.this.currentContext;
          if (localObject != null)
          {
            Element localElement;
            if ((localObject instanceof Element))
              localElement = (Element)localObject;
            while (true)
            {
              if (localElement == null)
                break label136;
              Namespace localNamespace = localElement.getNamespace(paramString);
              if (localNamespace == null)
                break;
              return localNamespace.getURI();
              if ((localObject instanceof Attribute))
              {
                localElement = ((Attribute)localObject).getParent();
              }
              else if ((localObject instanceof Content))
              {
                localElement = ((Content)localObject).getParentElement();
              }
              else
              {
                boolean bool = localObject instanceof Document;
                localElement = null;
                if (bool)
                  localElement = ((Document)localObject).getRootElement();
              }
            }
          }
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.xpath.JaxenXPath
 * JD-Core Version:    0.6.2
 */