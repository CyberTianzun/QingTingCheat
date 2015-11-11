package org.apache.commons.httpclient;

import java.io.IOException;
import org.apache.commons.httpclient.util.ExceptionUtil;

public class NoHttpResponseException extends IOException
{
  public NoHttpResponseException()
  {
  }

  public NoHttpResponseException(String paramString)
  {
    super(paramString);
  }

  public NoHttpResponseException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    ExceptionUtil.initCause(this, paramThrowable);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.NoHttpResponseException
 * JD-Core Version:    0.6.2
 */