package org.apache.commons.httpclient.util;

import java.io.InterruptedIOException;
import java.lang.reflect.Method;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExceptionUtil
{
  private static final Method INIT_CAUSE_METHOD;
  private static final Log LOG;
  private static final Class SOCKET_TIMEOUT_CLASS;
  static Class class$java$lang$Throwable;
  static Class class$org$apache$commons$httpclient$util$ExceptionUtil;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$util$ExceptionUtil == null)
    {
      localClass = class$("org.apache.commons.httpclient.util.ExceptionUtil");
      class$org$apache$commons$httpclient$util$ExceptionUtil = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      INIT_CAUSE_METHOD = getInitCauseMethod();
      SOCKET_TIMEOUT_CLASS = SocketTimeoutExceptionClass();
      return;
      localClass = class$org$apache$commons$httpclient$util$ExceptionUtil;
    }
  }

  private static Class SocketTimeoutExceptionClass()
  {
    try
    {
      Class localClass = Class.forName("java.net.SocketTimeoutException");
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    return null;
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

  private static Method getInitCauseMethod()
  {
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
          break label56;
        localClass2 = class$("java.lang.Throwable");
        class$java$lang$Throwable = localClass2;
      }
      while (true)
      {
        return localClass2.getMethod("initCause", arrayOfClass);
        localClass1 = class$java$lang$Throwable;
        break;
        label56: localClass2 = class$java$lang$Throwable;
      }
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
    }
    return null;
  }

  public static void initCause(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    if (INIT_CAUSE_METHOD != null);
    try
    {
      INIT_CAUSE_METHOD.invoke(paramThrowable1, new Object[] { paramThrowable2 });
      return;
    }
    catch (Exception localException)
    {
      LOG.warn("Exception invoking Throwable.initCause", localException);
    }
  }

  public static boolean isSocketTimeoutException(InterruptedIOException paramInterruptedIOException)
  {
    if (SOCKET_TIMEOUT_CLASS != null)
      return SOCKET_TIMEOUT_CLASS.isInstance(paramInterruptedIOException);
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.util.ExceptionUtil
 * JD-Core Version:    0.6.2
 */