package org.jdom;

import java.util.HashMap;

public final class Namespace
{
  private static final String CVS_ID = "@(#) $RCSfile: Namespace.java,v $ $Revision: 1.44 $ $Date: 2008/12/17 23:22:48 $ $Name: jdom_1_1_1 $";
  public static final Namespace NO_NAMESPACE = new Namespace("", "");
  public static final Namespace XML_NAMESPACE = new Namespace("xml", "http://www.w3.org/XML/1998/namespace");
  private static HashMap namespaces = new HashMap(16);
  private String prefix;
  private String uri;

  static
  {
    namespaces.put(new NamespaceKey(NO_NAMESPACE), NO_NAMESPACE);
    namespaces.put(new NamespaceKey(XML_NAMESPACE), XML_NAMESPACE);
  }

  private Namespace(String paramString1, String paramString2)
  {
    this.prefix = paramString1;
    this.uri = paramString2;
  }

  public static Namespace getNamespace(String paramString)
  {
    return getNamespace("", paramString);
  }

  public static Namespace getNamespace(String paramString1, String paramString2)
  {
    Namespace localNamespace1;
    if ((paramString1 == null) || (paramString1.trim().equals("")))
      if ((paramString2 == null) || (paramString2.trim().equals("")))
        localNamespace1 = NO_NAMESPACE;
    label41: NamespaceKey localNamespaceKey;
    while (true)
    {
      return localNamespace1;
      paramString1 = "";
      localNamespaceKey = new NamespaceKey(paramString1, paramString2);
      synchronized (namespaces)
      {
        localNamespace1 = (Namespace)namespaces.get(localNamespaceKey);
        if (localNamespace1 == null)
        {
          String str1 = Verifier.checkNamespacePrefix(paramString1);
          if (str1 != null)
          {
            throw new IllegalNameException(paramString1, "Namespace prefix", str1);
            if ((paramString2 != null) && (!paramString2.trim().equals("")))
              break label41;
            paramString2 = "";
          }
        }
      }
    }
    String str2 = Verifier.checkNamespaceURI(paramString2);
    if (str2 != null)
      throw new IllegalNameException(paramString2, "Namespace URI", str2);
    if ((!paramString1.equals("")) && (paramString2.equals("")))
      throw new IllegalNameException("", "namespace", "Namespace URIs must be non-null and non-empty Strings");
    if (paramString1.equals("xml"))
      throw new IllegalNameException(paramString1, "Namespace prefix", "The xml prefix can only be bound to http://www.w3.org/XML/1998/namespace");
    if (paramString2.equals("http://www.w3.org/XML/1998/namespace"))
      throw new IllegalNameException(paramString2, "Namespace URI", "The http://www.w3.org/XML/1998/namespace must be bound to the xml prefix.");
    Namespace localNamespace2 = new Namespace(paramString1, paramString2);
    synchronized (namespaces)
    {
      namespaces.put(localNamespaceKey, localNamespace2);
      return localNamespace2;
    }
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if ((paramObject instanceof Namespace))
      return this.uri.equals(((Namespace)paramObject).uri);
    return false;
  }

  public String getPrefix()
  {
    return this.prefix;
  }

  public String getURI()
  {
    return this.uri;
  }

  public int hashCode()
  {
    return this.uri.hashCode();
  }

  public String toString()
  {
    return "[Namespace: prefix \"" + this.prefix + "\" is mapped to URI \"" + this.uri + "\"]";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.Namespace
 * JD-Core Version:    0.6.2
 */