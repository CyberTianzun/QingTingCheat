package com.tencent.stat.common;

import android.util.Log;
import com.tencent.stat.StatConfig;

public final class StatLogger
{
  private boolean debugEnable = true;
  private int logLevel = 2;
  private String tag = "default";

  public StatLogger()
  {
  }

  public StatLogger(String paramString)
  {
    this.tag = paramString;
  }

  private String getLoggerClassInfo()
  {
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    if (arrayOfStackTraceElement == null)
      return null;
    int i = arrayOfStackTraceElement.length;
    int j = 0;
    label18: StackTraceElement localStackTraceElement;
    if (j < i)
    {
      localStackTraceElement = arrayOfStackTraceElement[j];
      if (!localStackTraceElement.isNativeMethod())
        break label42;
    }
    label42: 
    while ((localStackTraceElement.getClassName().equals(Thread.class.getName())) || (localStackTraceElement.getClassName().equals(getClass().getName())))
    {
      j++;
      break label18;
      break;
    }
    return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId() + "): " + localStackTraceElement.getFileName() + ":" + localStackTraceElement.getLineNumber() + "]";
  }

  public void d(Object paramObject)
  {
    if (isDebugEnable())
      debug(paramObject);
  }

  public void debug(Object paramObject)
  {
    String str1;
    if (this.logLevel <= 3)
    {
      str1 = getLoggerClassInfo();
      if (str1 != null)
        break label32;
    }
    label32: for (String str2 = paramObject.toString(); ; str2 = str1 + " - " + paramObject)
    {
      Log.d(this.tag, str2);
      return;
    }
  }

  public void e(Exception paramException)
  {
    if (StatConfig.isDebugEnable())
      error(paramException);
  }

  public void e(Object paramObject)
  {
    if (isDebugEnable())
      error(paramObject);
  }

  public void error(Exception paramException)
  {
    if (this.logLevel <= 6)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      String str = getLoggerClassInfo();
      StackTraceElement[] arrayOfStackTraceElement = paramException.getStackTrace();
      if (str != null)
        localStringBuffer.append(str + " - " + paramException + "\r\n");
      while ((arrayOfStackTraceElement != null) && (arrayOfStackTraceElement.length > 0))
      {
        int i = arrayOfStackTraceElement.length;
        for (int j = 0; j < i; j++)
        {
          StackTraceElement localStackTraceElement = arrayOfStackTraceElement[j];
          if (localStackTraceElement != null)
            localStringBuffer.append("[ " + localStackTraceElement.getFileName() + ":" + localStackTraceElement.getLineNumber() + " ]\r\n");
        }
        localStringBuffer.append(paramException + "\r\n");
      }
      Log.e(this.tag, localStringBuffer.toString());
    }
  }

  public void error(Object paramObject)
  {
    String str1;
    if (this.logLevel <= 6)
    {
      str1 = getLoggerClassInfo();
      if (str1 != null)
        break label33;
    }
    label33: for (String str2 = paramObject.toString(); ; str2 = str1 + " - " + paramObject)
    {
      Log.e(this.tag, str2);
      return;
    }
  }

  public int getLogLevel()
  {
    return this.logLevel;
  }

  public void i(Object paramObject)
  {
    if (isDebugEnable())
      info(paramObject);
  }

  public void info(Object paramObject)
  {
    String str1;
    if (this.logLevel <= 4)
    {
      str1 = getLoggerClassInfo();
      if (str1 != null)
        break label32;
    }
    label32: for (String str2 = paramObject.toString(); ; str2 = str1 + " - " + paramObject)
    {
      Log.i(this.tag, str2);
      return;
    }
  }

  public boolean isDebugEnable()
  {
    return this.debugEnable;
  }

  public void setDebugEnable(boolean paramBoolean)
  {
    this.debugEnable = paramBoolean;
  }

  public void setLogLevel(int paramInt)
  {
    this.logLevel = paramInt;
  }

  public void setTag(String paramString)
  {
    this.tag = paramString;
  }

  public void v(Object paramObject)
  {
    if (isDebugEnable())
      verbose(paramObject);
  }

  public void verbose(Object paramObject)
  {
    String str1;
    if (this.logLevel <= 2)
    {
      str1 = getLoggerClassInfo();
      if (str1 != null)
        break label32;
    }
    label32: for (String str2 = paramObject.toString(); ; str2 = str1 + " - " + paramObject)
    {
      Log.v(this.tag, str2);
      return;
    }
  }

  public void w(Object paramObject)
  {
    if (isDebugEnable())
      warn(paramObject);
  }

  public void warn(Object paramObject)
  {
    String str1;
    if (this.logLevel <= 5)
    {
      str1 = getLoggerClassInfo();
      if (str1 != null)
        break label32;
    }
    label32: for (String str2 = paramObject.toString(); ; str2 = str1 + " - " + paramObject)
    {
      Log.w(this.tag, str2);
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.common.StatLogger
 * JD-Core Version:    0.6.2
 */