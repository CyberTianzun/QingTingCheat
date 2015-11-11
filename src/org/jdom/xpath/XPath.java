package org.jdom.xpath;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import org.jdom.JDOMException;
import org.jdom.Namespace;

public abstract class XPath
  implements Serializable
{
  private static final String CVS_ID = "@(#) $RCSfile: XPath.java,v $ $Revision: 1.17 $ $Date: 2007/11/10 05:29:02 $ $Name: jdom_1_1_1 $";
  private static final String DEFAULT_XPATH_CLASS = "org.jdom.xpath.JaxenXPath";
  public static final String JDOM_OBJECT_MODEL_URI = "http://jdom.org/jaxp/xpath/jdom";
  private static final String XPATH_CLASS_PROPERTY = "org.jdom.xpath.class";
  static Class class$java$lang$String;
  static Class class$org$jdom$xpath$XPath;
  private static Constructor constructor = null;

  static Class class$(String paramString)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }

  public static XPath newInstance(String paramString)
    throws JDOMException
  {
    try
    {
      Constructor localConstructor = constructor;
      if (localConstructor == null);
      try
      {
        String str2 = System.getProperty("org.jdom.xpath.class", "org.jdom.xpath.JaxenXPath");
        str1 = str2;
        setXPathClass(Class.forName(str1));
        return (XPath)constructor.newInstance(new Object[] { paramString });
      }
      catch (SecurityException localSecurityException)
      {
        while (true)
          String str1 = "org.jdom.xpath.JaxenXPath";
      }
    }
    catch (JDOMException localJDOMException2)
    {
      throw localJDOMException2;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Throwable localThrowable = localInvocationTargetException.getTargetException();
      if ((localThrowable instanceof JDOMException));
      for (JDOMException localJDOMException1 = (JDOMException)localThrowable; ; localJDOMException1 = new JDOMException(localThrowable.toString(), localThrowable))
        throw localJDOMException1;
    }
    catch (Exception localException)
    {
      throw new JDOMException(localException.toString(), localException);
    }
  }

  public static List selectNodes(Object paramObject, String paramString)
    throws JDOMException
  {
    return newInstance(paramString).selectNodes(paramObject);
  }

  public static Object selectSingleNode(Object paramObject, String paramString)
    throws JDOMException
  {
    return newInstance(paramString).selectSingleNode(paramObject);
  }

  public static void setXPathClass(Class paramClass)
    throws JDOMException
  {
    if (paramClass == null)
      throw new IllegalArgumentException("aClass");
    try
    {
      Class localClass1;
      Class[] arrayOfClass;
      Class localClass2;
      if (class$org$jdom$xpath$XPath == null)
      {
        localClass1 = class$("org.jdom.xpath.XPath");
        class$org$jdom$xpath$XPath = localClass1;
        if ((!localClass1.isAssignableFrom(paramClass)) || (Modifier.isAbstract(paramClass.getModifiers())))
          break label103;
        arrayOfClass = new Class[1];
        if (class$java$lang$String != null)
          break label95;
        localClass2 = class$("java.lang.String");
        class$java$lang$String = localClass2;
      }
      while (true)
      {
        arrayOfClass[0] = localClass2;
        constructor = paramClass.getConstructor(arrayOfClass);
        return;
        localClass1 = class$org$jdom$xpath$XPath;
        break;
        label95: localClass2 = class$java$lang$String;
      }
      label103: throw new JDOMException(paramClass.getName() + " is not a concrete JDOM XPath implementation");
    }
    catch (JDOMException localJDOMException)
    {
      throw localJDOMException;
    }
    catch (Exception localException)
    {
      throw new JDOMException(localException.toString(), localException);
    }
  }

  public void addNamespace(String paramString1, String paramString2)
  {
    addNamespace(Namespace.getNamespace(paramString1, paramString2));
  }

  public abstract void addNamespace(Namespace paramNamespace);

  public abstract String getXPath();

  public abstract Number numberValueOf(Object paramObject)
    throws JDOMException;

  public abstract List selectNodes(Object paramObject)
    throws JDOMException;

  public abstract Object selectSingleNode(Object paramObject)
    throws JDOMException;

  public abstract void setVariable(String paramString, Object paramObject);

  public abstract String valueOf(Object paramObject)
    throws JDOMException;

  protected final Object writeReplace()
    throws ObjectStreamException
  {
    return new XPathString(getXPath());
  }

  private static final class XPathString
    implements Serializable
  {
    private String xPath = null;

    public XPathString(String paramString)
    {
      this.xPath = paramString;
    }

    private Object readResolve()
      throws ObjectStreamException
    {
      try
      {
        XPath localXPath = XPath.newInstance(this.xPath);
        return localXPath;
      }
      catch (JDOMException localJDOMException)
      {
        throw new InvalidObjectException("Can't create XPath object for expression \"" + this.xPath + "\": " + localJDOMException.toString());
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.xpath.XPath
 * JD-Core Version:    0.6.2
 */