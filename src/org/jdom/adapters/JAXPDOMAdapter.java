package org.jdom.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jdom.JDOMException;
import org.jdom.input.BuilderErrorHandler;
import org.w3c.dom.Document;

public class JAXPDOMAdapter extends AbstractDOMAdapter
{
  private static final String CVS_ID = "@(#) $RCSfile: JAXPDOMAdapter.java,v $ $Revision: 1.13 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1_1 $";
  static Class class$java$io$InputStream;
  static Class class$org$xml$sax$ErrorHandler;

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
      Class.forName("javax.xml.transform.Transformer");
      Class localClass = Class.forName("javax.xml.parsers.DocumentBuilderFactory");
      Object localObject1 = localClass.getMethod("newInstance", null).invoke(null, null);
      Object localObject2 = localClass.getMethod("newDocumentBuilder", null).invoke(localObject1, null);
      Document localDocument = (Document)localObject2.getClass().getMethod("newDocument", null).invoke(localObject2, null);
      return localDocument;
    }
    catch (Exception localException)
    {
      throw new JDOMException("Reflection failed while creating new JAXP document", localException);
    }
  }

  public Document getDocument(InputStream paramInputStream, boolean paramBoolean)
    throws IOException, JDOMException
  {
    try
    {
      Class.forName("javax.xml.transform.Transformer");
      Class localClass1 = Class.forName("javax.xml.parsers.DocumentBuilderFactory");
      Object localObject1 = localClass1.getMethod("newInstance", null).invoke(null, null);
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = Boolean.TYPE;
      Method localMethod1 = localClass1.getMethod("setValidating", arrayOfClass1);
      Object[] arrayOfObject1 = new Object[1];
      Boolean localBoolean = new Boolean(paramBoolean);
      arrayOfObject1[0] = localBoolean;
      localMethod1.invoke(localObject1, arrayOfObject1);
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = Boolean.TYPE;
      Method localMethod2 = localClass1.getMethod("setNamespaceAware", arrayOfClass2);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Boolean.TRUE;
      localMethod2.invoke(localObject1, arrayOfObject2);
      Object localObject2 = localClass1.getMethod("newDocumentBuilder", null).invoke(localObject1, null);
      Class localClass2 = localObject2.getClass();
      Class[] arrayOfClass3 = new Class[1];
      Class localClass3;
      Class[] arrayOfClass4;
      Class localClass4;
      if (class$org$xml$sax$ErrorHandler == null)
      {
        localClass3 = class$("org.xml.sax.ErrorHandler");
        class$org$xml$sax$ErrorHandler = localClass3;
        arrayOfClass3[0] = localClass3;
        Method localMethod3 = localClass2.getMethod("setErrorHandler", arrayOfClass3);
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = new BuilderErrorHandler();
        localMethod3.invoke(localObject2, arrayOfObject3);
        arrayOfClass4 = new Class[1];
        if (class$java$io$InputStream != null)
          break label286;
        localClass4 = class$("java.io.InputStream");
        class$java$io$InputStream = localClass4;
      }
      while (true)
      {
        arrayOfClass4[0] = localClass4;
        return (Document)localClass2.getMethod("parse", arrayOfClass4).invoke(localObject2, new Object[] { paramInputStream });
        localClass3 = class$org$xml$sax$ErrorHandler;
        break;
        label286: localClass4 = class$java$io$InputStream;
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Throwable localThrowable = localInvocationTargetException.getTargetException();
      if ((localThrowable instanceof IOException))
        throw ((IOException)localThrowable);
      throw new JDOMException(localThrowable.getMessage(), localThrowable);
    }
    catch (Exception localException)
    {
      throw new JDOMException("Reflection failed while parsing a document with JAXP", localException);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.adapters.JAXPDOMAdapter
 * JD-Core Version:    0.6.2
 */