package com.umeng.message.proguard;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

class ar
  implements HttpRequestRetryHandler
{
  private static final int a = 3000;
  private static HashSet<Class<?>> b = new HashSet();
  private static HashSet<Class<?>> c = new HashSet();
  private final int d;

  static
  {
    b.add(NoHttpResponseException.class);
    b.add(UnknownHostException.class);
    b.add(SocketException.class);
    c.add(InterruptedIOException.class);
    c.add(SSLHandshakeException.class);
  }

  public ar(int paramInt)
  {
    this.d = paramInt;
  }

  public boolean retryRequest(IOException paramIOException, int paramInt, HttpContext paramHttpContext)
  {
    Boolean localBoolean = (Boolean)paramHttpContext.getAttribute("http.request_sent");
    int i;
    boolean bool1;
    if ((localBoolean != null) && (localBoolean.booleanValue()))
    {
      i = 1;
      int j = this.d;
      bool1 = false;
      if (paramInt <= j)
        break label64;
    }
    while (true)
    {
      if (!bool1)
        break label151;
      SystemClock.sleep(3000L);
      return bool1;
      i = 0;
      break;
      label64: boolean bool2 = c.contains(paramIOException.getClass());
      bool1 = false;
      if (!bool2)
        if (b.contains(paramIOException.getClass()))
        {
          bool1 = true;
        }
        else if (i == 0)
        {
          bool1 = true;
        }
        else
        {
          boolean bool3 = ((HttpUriRequest)paramHttpContext.getAttribute("http.request")).getMethod().equals("POST");
          bool1 = false;
          if (!bool3)
            bool1 = true;
        }
    }
    label151: paramIOException.printStackTrace();
    return bool1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.ar
 * JD-Core Version:    0.6.2
 */