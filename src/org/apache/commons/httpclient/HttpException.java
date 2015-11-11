package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class HttpException extends IOException
{
  static Class class$java$lang$Throwable;
  private final Throwable cause;
  private String reason;
  private int reasonCode = 200;

  public HttpException()
  {
    this.cause = null;
  }

  public HttpException(String paramString)
  {
    super(paramString);
    this.cause = null;
  }

  public HttpException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.cause = paramThrowable;
    try
    {
      Class[] arrayOfClass = new Class[1];
      Class localClass1;
      Class localClass2;
      if (class$java$lang$Throwable == null)
      {
        localClass1 = class$("java.lang.Throwable");
        class$java$lang$Throwable = localClass1;
        arrayOfClass[0] = localClass1;
        if (class$java$lang$Throwable != null)
          break label96;
        localClass2 = class$("java.lang.Throwable");
        class$java$lang$Throwable = localClass2;
      }
      while (true)
      {
        localClass2.getMethod("initCause", arrayOfClass).invoke(this, new Object[] { paramThrowable });
        return;
        localClass1 = class$java$lang$Throwable;
        break;
        label96: localClass2 = class$java$lang$Throwable;
      }
    }
    catch (Exception localException)
    {
    }
  }

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

  public Throwable getCause()
  {
    return this.cause;
  }

  public String getReason()
  {
    return this.reason;
  }

  public int getReasonCode()
  {
    return this.reasonCode;
  }

  public void printStackTrace()
  {
    printStackTrace(System.err);
  }

  public void printStackTrace(PrintStream paramPrintStream)
  {
    try
    {
      Class[] arrayOfClass = new Class[0];
      getClass().getMethod("getStackTrace", arrayOfClass);
      super.printStackTrace(paramPrintStream);
      return;
    }
    catch (Exception localException)
    {
      do
        super.printStackTrace(paramPrintStream);
      while (this.cause == null);
      paramPrintStream.print("Caused by: ");
      this.cause.printStackTrace(paramPrintStream);
    }
  }

  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    try
    {
      Class[] arrayOfClass = new Class[0];
      getClass().getMethod("getStackTrace", arrayOfClass);
      super.printStackTrace(paramPrintWriter);
      return;
    }
    catch (Exception localException)
    {
      do
        super.printStackTrace(paramPrintWriter);
      while (this.cause == null);
      paramPrintWriter.print("Caused by: ");
      this.cause.printStackTrace(paramPrintWriter);
    }
  }

  public void setReason(String paramString)
  {
    this.reason = paramString;
  }

  public void setReasonCode(int paramInt)
  {
    this.reasonCode = paramInt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpException
 * JD-Core Version:    0.6.2
 */