package org.jdom.adapters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jdom.JDOMException;
import org.jdom.input.BuilderErrorHandler;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class XercesDOMAdapter extends AbstractDOMAdapter
{
  private static final String CVS_ID = "@(#) $RCSfile: XercesDOMAdapter.java,v $ $Revision: 1.19 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1_1 $";
  static Class class$java$lang$String;
  static Class class$org$xml$sax$ErrorHandler;
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
      Document localDocument = (Document)Class.forName("org.apache.xerces.dom.DocumentImpl").newInstance();
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
      Class localClass1 = Class.forName("org.apache.xerces.parsers.DOMParser");
      Object localObject = localClass1.newInstance();
      Class[] arrayOfClass1 = new Class[2];
      Class localClass2;
      Class localClass3;
      label158: Class[] arrayOfClass3;
      Class localClass4;
      if (class$java$lang$String == null)
      {
        localClass2 = class$("java.lang.String");
        class$java$lang$String = localClass2;
        arrayOfClass1[0] = localClass2;
        arrayOfClass1[1] = Boolean.TYPE;
        Method localMethod1 = localClass1.getMethod("setFeature", arrayOfClass1);
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = "http://xml.org/sax/features/validation";
        arrayOfObject1[1] = new Boolean(paramBoolean);
        localMethod1.invoke(localObject, arrayOfObject1);
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = "http://xml.org/sax/features/namespaces";
        arrayOfObject2[1] = new Boolean(true);
        localMethod1.invoke(localObject, arrayOfObject2);
        if (paramBoolean)
        {
          Class[] arrayOfClass2 = new Class[1];
          if (class$org$xml$sax$ErrorHandler != null)
            break label297;
          localClass3 = class$("org.xml.sax.ErrorHandler");
          class$org$xml$sax$ErrorHandler = localClass3;
          arrayOfClass2[0] = localClass3;
          Method localMethod2 = localClass1.getMethod("setErrorHandler", arrayOfClass2);
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = new BuilderErrorHandler();
          localMethod2.invoke(localObject, arrayOfObject3);
        }
        arrayOfClass3 = new Class[1];
        if (class$org$xml$sax$InputSource != null)
          break label305;
        localClass4 = class$("org.xml.sax.InputSource");
        class$org$xml$sax$InputSource = localClass4;
      }
      while (true)
      {
        arrayOfClass3[0] = localClass4;
        Method localMethod3 = localClass1.getMethod("parse", arrayOfClass3);
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = new InputSource(paramInputStream);
        localMethod3.invoke(localObject, arrayOfObject4);
        return (Document)localClass1.getMethod("getDocument", null).invoke(localObject, null);
        localClass2 = class$java$lang$String;
        break;
        label297: localClass3 = class$org$xml$sax$ErrorHandler;
        break label158;
        label305: localClass4 = class$org$xml$sax$InputSource;
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Throwable localThrowable = localInvocationTargetException.getTargetException();
      if ((localThrowable instanceof SAXParseException))
      {
        SAXParseException localSAXParseException = (SAXParseException)localThrowable;
        throw new JDOMException("Error on line " + localSAXParseException.getLineNumber() + " of XML document: " + localSAXParseException.getMessage(), localInvocationTargetException);
      }
      if ((localThrowable instanceof IOException))
        throw ((IOException)localThrowable);
      throw new JDOMException(localThrowable.getMessage(), localInvocationTargetException);
    }
    catch (Exception localException)
    {
      throw new JDOMException(localException.getClass().getName() + ": " + localException.getMessage(), localException);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.adapters.XercesDOMAdapter
 * JD-Core Version:    0.6.2
 */