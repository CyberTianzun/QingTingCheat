package com.taobao.munion.base.volley.a;

import android.os.SystemClock;
import com.taobao.munion.base.Log;
import com.taobao.munion.base.volley.b.a;
import com.taobao.munion.base.volley.f;
import com.taobao.munion.base.volley.l;
import com.taobao.munion.base.volley.p;
import com.taobao.munion.base.volley.q;
import com.taobao.munion.base.volley.s;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.cookie.DateUtils;

public class a
  implements f
{
  protected static final boolean a = Log.DEBUG;
  private static int d = 3000;
  private static int e = 4096;
  protected final g b;
  protected final b c;

  public a(g paramg)
  {
    this(paramg, new b(e));
  }

  public a(g paramg, b paramb)
  {
    this.b = paramg;
    this.c = paramb;
  }

  private static Map<String, String> a(Header[] paramArrayOfHeader)
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0; i < paramArrayOfHeader.length; i++)
      localHashMap.put(paramArrayOfHeader[i].getName(), paramArrayOfHeader[i].getValue());
    return localHashMap;
  }

  private void a(long paramLong, l<?> paraml, byte[] paramArrayOfByte, StatusLine paramStatusLine)
  {
    Object[] arrayOfObject;
    if ((a) || (paramLong > d))
    {
      arrayOfObject = new Object[5];
      arrayOfObject[0] = paraml;
      arrayOfObject[1] = Long.valueOf(paramLong);
      if (paramArrayOfByte == null)
        break label91;
    }
    label91: for (Object localObject = Integer.valueOf(paramArrayOfByte.length); ; localObject = "null")
    {
      arrayOfObject[2] = localObject;
      arrayOfObject[3] = Integer.valueOf(paramStatusLine.getStatusCode());
      arrayOfObject[4] = Integer.valueOf(paraml.w().b());
      Log.d("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", arrayOfObject);
      return;
    }
  }

  private static void a(String paramString, l<?> paraml, s params)
    throws s
  {
    p localp = paraml.w();
    int i = paraml.v();
    try
    {
      localp.a(params);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramString;
      arrayOfObject2[1] = Integer.valueOf(i);
      paraml.a(String.format("%s-retry [timeout=%s]", arrayOfObject2));
      return;
    }
    catch (s locals)
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramString;
      arrayOfObject1[1] = Integer.valueOf(i);
      paraml.a(String.format("%s-timeout-giveup [timeout=%s]", arrayOfObject1));
      throw locals;
    }
  }

  private void a(Map<String, String> paramMap, b.a parama)
  {
    if (parama == null);
    do
    {
      return;
      if (parama.b != null)
        paramMap.put("If-None-Match", parama.b);
    }
    while (parama.c <= 0L);
    paramMap.put("If-Modified-Since", DateUtils.formatDate(new Date(parama.c)));
  }

  private byte[] a(HttpEntity paramHttpEntity)
    throws IOException, q
  {
    j localj = new j(this.c, (int)paramHttpEntity.getContentLength());
    byte[] arrayOfByte1 = null;
    InputStream localInputStream;
    try
    {
      localInputStream = paramHttpEntity.getContent();
      arrayOfByte1 = null;
      if (localInputStream == null)
        throw new q();
    }
    finally
    {
    }
    try
    {
      paramHttpEntity.consumeContent();
      this.c.a(arrayOfByte1);
      localj.close();
      throw localObject;
      arrayOfByte1 = this.c.a(1024);
      while (true)
      {
        int i = localInputStream.read(arrayOfByte1);
        if (i == -1)
          break;
        localj.write(arrayOfByte1, 0, i);
      }
      byte[] arrayOfByte2 = localj.toByteArray();
      try
      {
        paramHttpEntity.consumeContent();
        this.c.a(arrayOfByte1);
        localj.close();
        return arrayOfByte2;
      }
      catch (IOException localIOException2)
      {
        while (true)
          Log.v("Error occured when calling consumingContent", new Object[0]);
      }
    }
    catch (IOException localIOException1)
    {
      while (true)
        Log.v("Error occured when calling consumingContent", new Object[0]);
    }
  }

  // ERROR //
  public com.taobao.munion.base.volley.i a(l<?> paraml)
    throws s
  {
    // Byte code:
    //   0: invokestatic 207	android/os/SystemClock:elapsedRealtime	()J
    //   3: lstore_2
    //   4: aconst_null
    //   5: astore 4
    //   7: aconst_null
    //   8: astore 5
    //   10: new 47	java/util/HashMap
    //   13: dup
    //   14: invokespecial 48	java/util/HashMap:<init>	()V
    //   17: astore 6
    //   19: new 47	java/util/HashMap
    //   22: dup
    //   23: invokespecial 48	java/util/HashMap:<init>	()V
    //   26: astore 7
    //   28: aload_0
    //   29: aload 7
    //   31: aload_1
    //   32: invokevirtual 211	com/taobao/munion/base/volley/l:h	()Lcom/taobao/munion/base/volley/b$a;
    //   35: invokespecial 213	com/taobao/munion/base/volley/a/a:a	(Ljava/util/Map;Lcom/taobao/munion/base/volley/b$a;)V
    //   38: aload_0
    //   39: getfield 42	com/taobao/munion/base/volley/a/a:b	Lcom/taobao/munion/base/volley/a/g;
    //   42: aload_1
    //   43: aload 7
    //   45: invokeinterface 218 3 0
    //   50: astore 15
    //   52: aload 15
    //   54: invokeinterface 224 1 0
    //   59: astore 16
    //   61: aload 16
    //   63: invokeinterface 81 1 0
    //   68: istore 17
    //   70: aload 15
    //   72: invokeinterface 228 1 0
    //   77: invokestatic 230	com/taobao/munion/base/volley/a/a:a	([Lorg/apache/http/Header;)Ljava/util/Map;
    //   80: astore 18
    //   82: iload 17
    //   84: sipush 304
    //   87: if_icmpne +41 -> 128
    //   90: aload_1
    //   91: invokevirtual 211	com/taobao/munion/base/volley/l:h	()Lcom/taobao/munion/base/volley/b$a;
    //   94: ifnonnull +22 -> 116
    //   97: aconst_null
    //   98: astore 21
    //   100: new 232	com/taobao/munion/base/volley/i
    //   103: dup
    //   104: sipush 304
    //   107: aload 21
    //   109: aload 18
    //   111: iconst_1
    //   112: invokespecial 235	com/taobao/munion/base/volley/i:<init>	(I[BLjava/util/Map;Z)V
    //   115: areturn
    //   116: aload_1
    //   117: invokevirtual 211	com/taobao/munion/base/volley/l:h	()Lcom/taobao/munion/base/volley/b$a;
    //   120: getfield 238	com/taobao/munion/base/volley/b$a:a	[B
    //   123: astore 21
    //   125: goto -25 -> 100
    //   128: aload 15
    //   130: invokeinterface 242 1 0
    //   135: astore 19
    //   137: aconst_null
    //   138: astore 5
    //   140: aload 19
    //   142: ifnull +80 -> 222
    //   145: aload_0
    //   146: aload 15
    //   148: invokeinterface 242 1 0
    //   153: invokespecial 244	com/taobao/munion/base/volley/a/a:a	(Lorg/apache/http/HttpEntity;)[B
    //   156: astore 5
    //   158: aload_0
    //   159: invokestatic 207	android/os/SystemClock:elapsedRealtime	()J
    //   162: lload_2
    //   163: lsub
    //   164: aload_1
    //   165: aload 5
    //   167: aload 16
    //   169: invokespecial 246	com/taobao/munion/base/volley/a/a:a	(JLcom/taobao/munion/base/volley/l;[BLorg/apache/http/StatusLine;)V
    //   172: iload 17
    //   174: sipush 200
    //   177: if_icmplt +11 -> 188
    //   180: iload 17
    //   182: sipush 299
    //   185: if_icmple +45 -> 230
    //   188: iload 17
    //   190: sipush 302
    //   193: if_icmpeq +37 -> 230
    //   196: new 147	java/io/IOException
    //   199: dup
    //   200: invokespecial 247	java/io/IOException:<init>	()V
    //   203: athrow
    //   204: astore 14
    //   206: ldc 249
    //   208: aload_1
    //   209: new 251	com/taobao/munion/base/volley/r
    //   212: dup
    //   213: invokespecial 252	com/taobao/munion/base/volley/r:<init>	()V
    //   216: invokestatic 254	com/taobao/munion/base/volley/a/a:a	(Ljava/lang/String;Lcom/taobao/munion/base/volley/l;Lcom/taobao/munion/base/volley/s;)V
    //   219: goto -215 -> 4
    //   222: iconst_0
    //   223: newarray byte
    //   225: astore 5
    //   227: goto -69 -> 158
    //   230: new 232	com/taobao/munion/base/volley/i
    //   233: dup
    //   234: iload 17
    //   236: aload 5
    //   238: aload 18
    //   240: iconst_0
    //   241: invokespecial 235	com/taobao/munion/base/volley/i:<init>	(I[BLjava/util/Map;Z)V
    //   244: astore 20
    //   246: aload 20
    //   248: areturn
    //   249: astore 13
    //   251: ldc_w 256
    //   254: aload_1
    //   255: new 251	com/taobao/munion/base/volley/r
    //   258: dup
    //   259: invokespecial 252	com/taobao/munion/base/volley/r:<init>	()V
    //   262: invokestatic 254	com/taobao/munion/base/volley/a/a:a	(Ljava/lang/String;Lcom/taobao/munion/base/volley/l;Lcom/taobao/munion/base/volley/s;)V
    //   265: goto -261 -> 4
    //   268: astore 12
    //   270: new 258	java/lang/RuntimeException
    //   273: dup
    //   274: new 260	java/lang/StringBuilder
    //   277: dup
    //   278: invokespecial 261	java/lang/StringBuilder:<init>	()V
    //   281: ldc_w 263
    //   284: invokevirtual 267	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: aload_1
    //   288: invokevirtual 270	com/taobao/munion/base/volley/l:f	()Ljava/lang/String;
    //   291: invokevirtual 267	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   294: invokevirtual 273	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   297: aload 12
    //   299: invokespecial 276	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   302: athrow
    //   303: astore 8
    //   305: aload 4
    //   307: ifnull +104 -> 411
    //   310: aload 4
    //   312: invokeinterface 224 1 0
    //   317: invokeinterface 81 1 0
    //   322: istore 9
    //   324: iconst_2
    //   325: anewarray 4	java/lang/Object
    //   328: astore 10
    //   330: aload 10
    //   332: iconst_0
    //   333: iload 9
    //   335: invokestatic 75	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   338: aastore
    //   339: aload 10
    //   341: iconst_1
    //   342: aload_1
    //   343: invokevirtual 270	com/taobao/munion/base/volley/l:f	()Ljava/lang/String;
    //   346: aastore
    //   347: ldc_w 278
    //   350: aload 10
    //   352: invokestatic 280	com/taobao/munion/base/Log:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   355: aload 5
    //   357: ifnull +74 -> 431
    //   360: new 232	com/taobao/munion/base/volley/i
    //   363: dup
    //   364: iload 9
    //   366: aload 5
    //   368: aload 6
    //   370: iconst_0
    //   371: invokespecial 235	com/taobao/munion/base/volley/i:<init>	(I[BLjava/util/Map;Z)V
    //   374: astore 11
    //   376: iload 9
    //   378: sipush 401
    //   381: if_icmpeq +11 -> 392
    //   384: iload 9
    //   386: sipush 403
    //   389: if_icmpne +32 -> 421
    //   392: ldc_w 282
    //   395: aload_1
    //   396: new 284	com/taobao/munion/base/volley/a
    //   399: dup
    //   400: aload 11
    //   402: invokespecial 287	com/taobao/munion/base/volley/a:<init>	(Lcom/taobao/munion/base/volley/i;)V
    //   405: invokestatic 254	com/taobao/munion/base/volley/a/a:a	(Ljava/lang/String;Lcom/taobao/munion/base/volley/l;Lcom/taobao/munion/base/volley/s;)V
    //   408: goto -404 -> 4
    //   411: new 289	com/taobao/munion/base/volley/j
    //   414: dup
    //   415: aload 8
    //   417: invokespecial 292	com/taobao/munion/base/volley/j:<init>	(Ljava/lang/Throwable;)V
    //   420: athrow
    //   421: new 149	com/taobao/munion/base/volley/q
    //   424: dup
    //   425: aload 11
    //   427: invokespecial 293	com/taobao/munion/base/volley/q:<init>	(Lcom/taobao/munion/base/volley/i;)V
    //   430: athrow
    //   431: new 295	com/taobao/munion/base/volley/h
    //   434: dup
    //   435: aconst_null
    //   436: invokespecial 296	com/taobao/munion/base/volley/h:<init>	(Lcom/taobao/munion/base/volley/i;)V
    //   439: athrow
    //   440: astore 8
    //   442: aload 15
    //   444: astore 4
    //   446: aconst_null
    //   447: astore 5
    //   449: goto -144 -> 305
    //   452: astore 8
    //   454: aload 18
    //   456: astore 6
    //   458: aload 15
    //   460: astore 4
    //   462: goto -157 -> 305
    //
    // Exception table:
    //   from	to	target	type
    //   19	52	204	java/net/SocketTimeoutException
    //   52	82	204	java/net/SocketTimeoutException
    //   90	97	204	java/net/SocketTimeoutException
    //   100	116	204	java/net/SocketTimeoutException
    //   116	125	204	java/net/SocketTimeoutException
    //   128	137	204	java/net/SocketTimeoutException
    //   145	158	204	java/net/SocketTimeoutException
    //   158	172	204	java/net/SocketTimeoutException
    //   196	204	204	java/net/SocketTimeoutException
    //   222	227	204	java/net/SocketTimeoutException
    //   230	246	204	java/net/SocketTimeoutException
    //   19	52	249	org/apache/http/conn/ConnectTimeoutException
    //   52	82	249	org/apache/http/conn/ConnectTimeoutException
    //   90	97	249	org/apache/http/conn/ConnectTimeoutException
    //   100	116	249	org/apache/http/conn/ConnectTimeoutException
    //   116	125	249	org/apache/http/conn/ConnectTimeoutException
    //   128	137	249	org/apache/http/conn/ConnectTimeoutException
    //   145	158	249	org/apache/http/conn/ConnectTimeoutException
    //   158	172	249	org/apache/http/conn/ConnectTimeoutException
    //   196	204	249	org/apache/http/conn/ConnectTimeoutException
    //   222	227	249	org/apache/http/conn/ConnectTimeoutException
    //   230	246	249	org/apache/http/conn/ConnectTimeoutException
    //   19	52	268	java/net/MalformedURLException
    //   52	82	268	java/net/MalformedURLException
    //   90	97	268	java/net/MalformedURLException
    //   100	116	268	java/net/MalformedURLException
    //   116	125	268	java/net/MalformedURLException
    //   128	137	268	java/net/MalformedURLException
    //   145	158	268	java/net/MalformedURLException
    //   158	172	268	java/net/MalformedURLException
    //   196	204	268	java/net/MalformedURLException
    //   222	227	268	java/net/MalformedURLException
    //   230	246	268	java/net/MalformedURLException
    //   19	52	303	java/io/IOException
    //   52	82	440	java/io/IOException
    //   90	97	452	java/io/IOException
    //   100	116	452	java/io/IOException
    //   116	125	452	java/io/IOException
    //   128	137	452	java/io/IOException
    //   145	158	452	java/io/IOException
    //   158	172	452	java/io/IOException
    //   196	204	452	java/io/IOException
    //   222	227	452	java/io/IOException
    //   230	246	452	java/io/IOException
  }

  protected void a(String paramString1, String paramString2, long paramLong)
  {
    long l = SystemClock.elapsedRealtime();
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramString1;
    arrayOfObject[1] = Long.valueOf(l - paramLong);
    arrayOfObject[2] = paramString2;
    Log.v("HTTP ERROR(%s) %d ms to fetch %s", arrayOfObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.volley.a.a
 * JD-Core Version:    0.6.2
 */