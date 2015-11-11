package org.jdom.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jdom.JDOMException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class OracleV2DOMAdapter extends AbstractDOMAdapter
{
  private static final String CVS_ID = "@(#) $RCSfile: OracleV2DOMAdapter.java,v $ $Revision: 1.19 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1_1 $";
  static Class class$org$xml$sax$InputSource;

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

  public Document createDocument()
    throws JDOMException
  {
    try
    {
      Document localDocument = (Document)Class.forName("oracle.xml.parser.v2.XMLDocument").newInstance();
      return localDocument;
    }
    catch (Exception localException)
    {
      throw new JDOMException(localException.getClass().getName() + ": " + localException.getMessage() + " when creating document", localException);
    }
  }

  public Document getDocument(InputStream paramInputStream, boolean paramBoolean)
    throws IOException, JDOMException
  {
    try
    {
      Class localClass1 = Class.forName("oracle.xml.parser.v2.DOMParser");
      Object localObject = localClass1.newInstance();
      Class[] arrayOfClass = new Class[1];
      Class localClass2;
      if (class$org$xml$sax$InputSource == null)
      {
        localClass2 = class$("org.xml.sax.InputSource");
        class$org$xml$sax$InputSource = localClass2;
      }
      while (true)
      {
        arrayOfClass[0] = localClass2;
        Method localMethod = localClass1.getMethod("parse", arrayOfClass);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = new InputSource(paramInputStream);
        localMethod.invoke(localObject, arrayOfObject);
        return (Document)localClass1.getMethod("getDocument", null).invoke(localObject, null);
        localClass2 = class$org$xml$sax$InputSource;
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Throwable localThrowable = localInvocationTargetException.getTargetException();
      if ((localThrowable instanceof SAXParseException))
      {
        SAXParseException localSAXParseException = (SAXParseException)localThrowable;
        throw new JDOMException("Error on line " + localSAXParseException.getLineNumber() + " of XML document: " + localSAXParseException.getMessage(), localSAXParseException);
      }
      if ((localThrowable instanceof IOException))
        throw ((IOException)localThrowable);
      throw new JDOMException(localThrowable.getMessage(), localThrowable);
    }
    catch (Exception localException)
    {
      throw new JDOMException(localException.getClass().getName() + ": " + localException.getMessage(), localException);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.adapters.OracleV2DOMAdapter
 * JD-Core Version:    0.6.2
 */