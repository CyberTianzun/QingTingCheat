package org.jdom.filter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.jdom.Element;
import org.jdom.Namespace;

public class ElementFilter extends AbstractFilter
{
  private static final String CVS_ID = "@(#) $RCSfile: ElementFilter.java,v $ $Revision: 1.20 $ $Date: 2007/11/10 05:29:00 $ $Name: jdom_1_1_1 $";
  private String name;
  private transient Namespace namespace;

  public ElementFilter()
  {
  }

  public ElementFilter(String paramString)
  {
    this.name = paramString;
  }

  public ElementFilter(String paramString, Namespace paramNamespace)
  {
    this.name = paramString;
    this.namespace = paramNamespace;
  }

  public ElementFilter(Namespace paramNamespace)
  {
    this.namespace = paramNamespace;
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    Object localObject1 = paramObjectInputStream.readObject();
    Object localObject2 = paramObjectInputStream.readObject();
    if (localObject1 != null)
      this.namespace = Namespace.getNamespace((String)localObject1, (String)localObject2);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    if (this.namespace != null)
    {
      paramObjectOutputStream.writeObject(this.namespace.getPrefix());
      paramObjectOutputStream.writeObject(this.namespace.getURI());
      return;
    }
    paramObjectOutputStream.writeObject(null);
    paramObjectOutputStream.writeObject(null);
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    ElementFilter localElementFilter;
    do
    {
      return true;
      if (!(paramObject instanceof ElementFilter))
        return false;
      localElementFilter = (ElementFilter)paramObject;
      if (this.name != null)
      {
        if (this.name.equals(localElementFilter.name));
      }
      else
        while (localElementFilter.name != null)
          return false;
      if (this.namespace == null)
        break;
    }
    while (this.namespace.equals(localElementFilter.namespace));
    while (true)
    {
      return false;
      if (localElementFilter.namespace == null)
        break;
    }
  }

  public int hashCode()
  {
    if (this.name != null);
    for (int i = this.name.hashCode(); ; i = 0)
    {
      int j = i * 29;
      Namespace localNamespace = this.namespace;
      int k = 0;
      if (localNamespace != null)
        k = this.namespace.hashCode();
      return j + k;
    }
  }

  public boolean matches(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Element;
    boolean bool2 = false;
    if (bool1)
    {
      Element localElement = (Element)paramObject;
      if (this.name != null)
      {
        boolean bool4 = this.name.equals(localElement.getName());
        bool2 = false;
        if (!bool4);
      }
      else if (this.namespace != null)
      {
        boolean bool3 = this.namespace.equals(localElement.getNamespace());
        bool2 = false;
        if (!bool3);
      }
      else
      {
        bool2 = true;
      }
    }
    return bool2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.filter.ElementFilter
 * JD-Core Version:    0.6.2
 */