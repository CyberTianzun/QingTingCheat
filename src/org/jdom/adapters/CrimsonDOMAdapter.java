package org.jdom.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jdom.JDOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

public class CrimsonDOMAdapter extends AbstractDOMAdapter
{
  private static final String CVS_ID = "@(#) $RCSfile: CrimsonDOMAdapter.java,v $ $Revision: 1.17 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1_1 $";

  public Document createDocument()
    throws JDOMException
  {
    try
    {
      Document localDocument = (Document)Class.forName("org.apache.crimson.tree.XmlDocument").newInstance();
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
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = Class.forName("java.io.InputStream");
      arrayOfClass[1] = Boolean.TYPE;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramInputStream;
      arrayOfObject[1] = new Boolean(false);
      Document localDocument = (Document)Class.forName("org.apache.crimson.tree.XmlDocument").getMethod("createXmlDocument", arrayOfClass).invoke(null, arrayOfObject);
      return localDocument;
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
 * Qualified Name:     org.jdom.adapters.CrimsonDOMAdapter
 * JD-Core Version:    0.6.2
 */