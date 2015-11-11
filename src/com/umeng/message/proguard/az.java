package com.umeng.message.proguard;

import android.text.TextUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class az extends at
{
  private static final String l = "HttpURLChunked";
  private volatile HttpURLConnection m = null;

  private String a(List<String> paramList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramList.size();
    for (int j = 0; j < i; j++)
    {
      localStringBuffer.append((String)paramList.get(j));
      if (j < i - 1)
        localStringBuffer.append(",");
    }
    return localStringBuffer.toString();
  }

  private Map<String, String> a(HttpURLConnection paramHttpURLConnection)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramHttpURLConnection.getHeaderFields().entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str1 = (String)localEntry.getKey();
      if (!TextUtils.isEmpty(str1))
      {
        String str2 = a((List)localEntry.getValue());
        if (!TextUtils.isEmpty(str2))
          localHashMap.put(str1, str2);
      }
    }
    return localHashMap;
  }

  private void b(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    if (paramHttpURLConnection == null)
      throw new IOException();
    this.b = paramHttpURLConnection.getInputStream();
  }

  protected void a(String paramString)
  {
    while (true)
    {
      URL localURL;
      try
      {
        Q.c("HttpURLChunked", "http chunked connectId:[" + b() + "]==>" + paramString);
        localURL = new URL(paramString);
        if (k())
        {
          this.m = ((HttpURLConnection)localURL.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getByName(super.i()), super.j()))));
          HttpURLConnection localHttpURLConnection = this.m;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Double.valueOf(2.0D);
          localHttpURLConnection.setRequestProperty("User-agent", String.format("Agoo-sdk-%s", arrayOfObject));
          if (this.f == null)
            break;
          Iterator localIterator = this.f.keySet().iterator();
          if (!localIterator.hasNext())
            break;
          String str = (String)localIterator.next();
          this.m.setRequestProperty(str, (String)this.f.get(str));
          continue;
        }
      }
      catch (Throwable localThrowable)
      {
        if (!hasCallError())
        {
          callError(true);
          Q.d("HttpURLChunked", "http chunked connectId:[" + b() + "]==>[Throwable]", localThrowable);
          a(504, localThrowable);
        }
        return;
      }
      this.m = ((HttpURLConnection)localURL.openConnection());
    }
    this.m.connect();
    int i = this.m.getResponseCode();
    Map localMap = a(this.m);
    if (200 == i)
    {
      b(this.m);
      a(localMap);
      e();
      return;
    }
    a(i, localMap);
    f();
  }

  protected void c()
  {
    if (this.m != null)
    {
      this.m.disconnect();
      this.m = null;
    }
  }

  protected void d()
  {
    if (this.m != null)
    {
      this.m.disconnect();
      this.m = null;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.az
 * JD-Core Version:    0.6.2
 */