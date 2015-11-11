package org.jdom;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import org.xml.sax.SAXException;

public class JDOMException extends Exception
{
  private static final String CVS_ID = "@(#) $RCSfile: JDOMException.java,v $ $Revision: 1.26 $ $Date: 2008/12/10 00:59:51 $ $Name: jdom_1_1_1 $";
  private Throwable cause;

  public JDOMException()
  {
    super("Error occurred in JDOM application.");
  }

  public JDOMException(String paramString)
  {
    super(paramString);
  }

  public JDOMException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.cause = paramThrowable;
  }

  private static Throwable getNestedException(Throwable paramThrowable)
  {
    Throwable localThrowable;
    if ((paramThrowable instanceof JDOMException))
      localThrowable = ((JDOMException)paramThrowable).getCause();
    do
    {
      do
      {
        do
        {
          return localThrowable;
          if ((paramThrowable instanceof SAXException))
            return ((SAXException)paramThrowable).getException();
          if ((paramThrowable instanceof SQLException))
            return ((SQLException)paramThrowable).getNextException();
          if ((paramThrowable instanceof InvocationTargetException))
            return ((InvocationTargetException)paramThrowable).getTargetException();
          if ((paramThrowable instanceof ExceptionInInitializerError))
            return ((ExceptionInInitializerError)paramThrowable).getException();
          localThrowable = getNestedExceptionFromField(paramThrowable, "java.rmi.RemoteException", "detail");
        }
        while (localThrowable != null);
        localThrowable = getNestedException(paramThrowable, "javax.naming.NamingException", "getRootCause");
      }
      while (localThrowable != null);
      localThrowable = getNestedException(paramThrowable, "javax.servlet.ServletException", "getRootCause");
    }
    while (localThrowable != null);
    return null;
  }

  private static Throwable getNestedException(Throwable paramThrowable, String paramString1, String paramString2)
  {
    try
    {
      Class localClass = Class.forName(paramString1);
      if (localClass.isAssignableFrom(paramThrowable.getClass()))
      {
        Throwable localThrowable = (Throwable)localClass.getMethod(paramString2, new Class[0]).invoke(paramThrowable, new Object[0]);
        return localThrowable;
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private static Throwable getNestedExceptionFromField(Throwable paramThrowable, String paramString1, String paramString2)
  {
    try
    {
      Class localClass = Class.forName(paramString1);
      if (localClass.isAssignableFrom(paramThrowable.getClass()))
      {
        new Class[0];
        Throwable localThrowable = (Throwable)localClass.getField(paramString2).get(paramThrowable);
        return localThrowable;
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public Throwable getCause()
  {
    return this.cause;
  }

  public String getMessage()
  {
    Object localObject1 = super.getMessage();
    Throwable localThrowable;
    for (Object localObject2 = this; ; localObject2 = localThrowable)
    {
      localThrowable = getNestedException((Throwable)localObject2);
      String str;
      if (localThrowable != null)
      {
        str = localThrowable.getMessage();
        if ((localThrowable instanceof SAXException))
        {
          Exception localException = ((SAXException)localThrowable).getException();
          if ((localException != null) && (str != null) && (str.equals(localException.getMessage())))
            str = null;
        }
        if (str != null)
          if (localObject1 == null)
            break label107;
      }
      label107: for (localObject1 = (String)localObject1 + ": " + str; (localThrowable instanceof JDOMException); localObject1 = str)
        return localObject1;
    }
  }

  public Throwable initCause(Throwable paramThrowable)
  {
    this.cause = paramThrowable;
    return this;
  }

  public void printStackTrace()
  {
    super.printStackTrace();
    Throwable localThrowable;
    for (Object localObject = this; ; localObject = localThrowable)
    {
      localThrowable = getNestedException((Throwable)localObject);
      if (localThrowable != null)
      {
        System.err.print("Caused by: ");
        localThrowable.printStackTrace();
        if (!(localThrowable instanceof JDOMException));
      }
      else
      {
        return;
      }
    }
  }

  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    Throwable localThrowable;
    for (Object localObject = this; ; localObject = localThrowable)
    {
      localThrowable = getNestedException((Throwable)localObject);
      if (localThrowable != null)
      {
        paramPrintStream.print("Caused by: ");
        localThrowable.printStackTrace(paramPrintStream);
        if (!(localThrowable instanceof JDOMException));
      }
      else
      {
        return;
      }
    }
  }

  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    Throwable localThrowable;
    for (Object localObject = this; ; localObject = localThrowable)
    {
      localThrowable = getNestedException((Throwable)localObject);
      if (localThrowable != null)
      {
        paramPrintWriter.print("Caused by: ");
        localThrowable.printStackTrace(paramPrintWriter);
        if (!(localThrowable instanceof JDOMException));
      }
      else
      {
        return;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.JDOMException
 * JD-Core Version:    0.6.2
 */