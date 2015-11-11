package com.taobao.munion.base.caches;

import android.text.TextUtils;
import android.webkit.CookieManager;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class q
  implements Runnable
{
  public static final String a = "url";
  public static final String b = "response-code";
  public static final String c = "content-type";
  public static final String d = "Content-Type";
  public static final String e = "content-length";
  public static final String f = "location";
  public static final String g = "last-modified";
  public static final String h = "expires";
  public static final String i = "date";
  public static final String j = "set-cookie";
  public static final String k = "cache-control";
  public static final String l = "etag";
  public static final int m = 15000;
  public static final int n = 15000;
  private static Object s = new Object();
  String o;
  v p;
  String q;
  private m r;

  public q(String paramString1, v paramv, String paramString2, m paramm)
  {
    this.o = paramString1;
    this.p = paramv;
    this.q = paramString2;
    this.r = paramm;
  }

  public void run()
  {
    HashMap localHashMap;
    InputStream localInputStream;
    ByteArrayOutputStream localByteArrayOutputStream;
    while (true)
    {
      HttpURLConnection localHttpURLConnection;
      int i2;
      String str1;
      try
      {
        localHttpURLConnection = (HttpURLConnection)new URL(this.o).openConnection();
        localHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        localHttpURLConnection.setReadTimeout(15000);
        localHttpURLConnection.setConnectTimeout(15000);
        if (this.o.contains("taobao.com"))
          localHttpURLConnection.setRequestProperty("Cookie", CookieManager.getInstance().getCookie("re.m.taobao.com"));
        localHttpURLConnection.connect();
        int i1 = localHttpURLConnection.getResponseCode();
        if ((i1 != 200) && (i1 != 304))
          break label373;
        localHashMap = new HashMap();
        i2 = 1;
        str1 = localHttpURLConnection.getHeaderFieldKey(i2);
        if (str1 != null)
          break label225;
        localHashMap.put("response-code", String.valueOf(i1));
        localHashMap.put("url", this.o);
        localInputStream = localHttpURLConnection.getInputStream();
        localByteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrayOfByte1 = new byte[2048];
        int i3 = localInputStream.read(arrayOfByte1);
        if (i3 == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte1, 0, i3);
        continue;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        if (this.r != null)
        {
          this.r.a = -1;
          this.r.b();
        }
      }
      this.p = null;
      return;
      label225: i2++;
      String str3 = localHttpURLConnection.getHeaderField(str1);
      localHashMap.put(str1.toLowerCase(), str3);
    }
    byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
    localByteArrayOutputStream.close();
    localInputStream.close();
    if ((b.f) && (!TextUtils.isEmpty(b.c)) && (b.c.equals(this.o)))
    {
      String str2 = new String(arrayOfByte2, "utf-8");
      b.a().e(str2);
    }
    while (true)
    {
      synchronized (s)
      {
        if (this.p != null)
          this.p.a(arrayOfByte3, (Map)localObject1, this.q, this.r);
      }
      label373: if (this.r != null)
        this.r.a = -1;
      Object localObject1 = null;
      byte[] arrayOfByte3 = null;
      continue;
      arrayOfByte3 = arrayOfByte2;
      localObject1 = localHashMap;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.q
 * JD-Core Version:    0.6.2
 */