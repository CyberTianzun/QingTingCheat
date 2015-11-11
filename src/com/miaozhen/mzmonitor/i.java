package com.miaozhen.mzmonitor;

import android.content.Context;
import java.net.HttpURLConnection;
import java.net.URL;

final class i
{
  private a a;
  private Context b;

  public i(Context paramContext, a parama)
  {
    this.b = paramContext.getApplicationContext();
    this.a = parama;
  }

  private static void a(String paramString)
  {
    if (paramString != null);
    try
    {
      URL localURL = new URL(paramString);
      HttpURLConnection.setFollowRedirects(true);
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
      if (localHttpURLConnection != null)
      {
        localHttpURLConnection.setDefaultUseCaches(false);
        localHttpURLConnection.setUseCaches(false);
        localHttpURLConnection.setConnectTimeout(5000);
        localHttpURLConnection.setReadTimeout(5000);
        localHttpURLConnection.connect();
        localHttpURLConnection.getResponseCode();
        localHttpURLConnection.disconnect();
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  // ERROR //
  public final void a()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: aload_0
    //   5: getfield 21	com/miaozhen/mzmonitor/i:b	Landroid/content/Context;
    //   8: invokestatic 75	com/miaozhen/mzmonitor/b:a	(Landroid/content/Context;)Lcom/miaozhen/mzmonitor/b;
    //   11: astore_3
    //   12: aload_0
    //   13: getfield 21	com/miaozhen/mzmonitor/i:b	Landroid/content/Context;
    //   16: invokestatic 80	com/miaozhen/mzmonitor/c:a	(Landroid/content/Context;)Lcom/miaozhen/mzmonitor/c;
    //   19: aload_0
    //   20: getfield 23	com/miaozhen/mzmonitor/i:a	Lcom/miaozhen/mzmonitor/a;
    //   23: invokevirtual 83	com/miaozhen/mzmonitor/c:a	(Lcom/miaozhen/mzmonitor/a;)Ljava/net/URL;
    //   26: astore 9
    //   28: aload 9
    //   30: invokevirtual 40	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   33: checkcast 32	java/net/HttpURLConnection
    //   36: astore 10
    //   38: aload_0
    //   39: getfield 21	com/miaozhen/mzmonitor/i:b	Landroid/content/Context;
    //   42: invokestatic 88	com/miaozhen/mzmonitor/f:a	(Landroid/content/Context;)Lcom/miaozhen/mzmonitor/f;
    //   45: invokevirtual 92	com/miaozhen/mzmonitor/f:l	()Ljava/lang/String;
    //   48: ldc 94
    //   50: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   53: ifeq +177 -> 230
    //   56: sipush 5000
    //   59: istore 11
    //   61: aload 10
    //   63: iload 11
    //   65: invokevirtual 53	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   68: aload 10
    //   70: iload 11
    //   72: invokevirtual 50	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   75: aload 10
    //   77: iconst_0
    //   78: invokevirtual 46	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   81: aload 10
    //   83: iconst_0
    //   84: invokevirtual 103	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   87: aload_0
    //   88: getfield 21	com/miaozhen/mzmonitor/i:b	Landroid/content/Context;
    //   91: invokestatic 80	com/miaozhen/mzmonitor/c:a	(Landroid/content/Context;)Lcom/miaozhen/mzmonitor/c;
    //   94: aload 9
    //   96: invokevirtual 106	com/miaozhen/mzmonitor/c:a	(Ljava/net/URL;)Lcom/miaozhen/mzmonitor/c$b;
    //   99: getfield 111	com/miaozhen/mzmonitor/c$b:a	Ljava/lang/String;
    //   102: astore 12
    //   104: aload 12
    //   106: ifnull +28 -> 134
    //   109: aload 12
    //   111: ldc 113
    //   113: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   116: ifeq +18 -> 134
    //   119: aload 10
    //   121: ldc 115
    //   123: aload 9
    //   125: invokevirtual 118	java/net/URL:toString	()Ljava/lang/String;
    //   128: invokestatic 123	com/miaozhen/mzmonitor/c$a:a	(Ljava/lang/String;)Ljava/lang/String;
    //   131: invokevirtual 127	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   134: aconst_null
    //   135: astore_2
    //   136: aload 10
    //   138: ifnull +53 -> 191
    //   141: aload 10
    //   143: invokevirtual 56	java/net/HttpURLConnection:connect	()V
    //   146: aload 10
    //   148: invokevirtual 60	java/net/HttpURLConnection:getResponseCode	()I
    //   151: istore 13
    //   153: iload 13
    //   155: iflt +83 -> 238
    //   158: iload 13
    //   160: sipush 400
    //   163: if_icmpge +75 -> 238
    //   166: ldc 129
    //   168: ldc 131
    //   170: invokestatic 137	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   173: pop
    //   174: aload_3
    //   175: aload_0
    //   176: getfield 23	com/miaozhen/mzmonitor/i:a	Lcom/miaozhen/mzmonitor/a;
    //   179: iconst_1
    //   180: invokevirtual 140	com/miaozhen/mzmonitor/b:a	(Lcom/miaozhen/mzmonitor/a;Z)V
    //   183: aload 10
    //   185: ldc 142
    //   187: invokevirtual 145	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   190: astore_2
    //   191: aload_0
    //   192: getfield 21	com/miaozhen/mzmonitor/i:b	Landroid/content/Context;
    //   195: invokestatic 151	com/miaozhen/mzmonitor/j:j	(Landroid/content/Context;)Z
    //   198: ifeq +17 -> 215
    //   201: aload_0
    //   202: getfield 21	com/miaozhen/mzmonitor/i:b	Landroid/content/Context;
    //   205: invokestatic 156	com/miaozhen/mzmonitor/g:a	(Landroid/content/Context;)Lcom/miaozhen/mzmonitor/g;
    //   208: aload_0
    //   209: getfield 21	com/miaozhen/mzmonitor/i:b	Landroid/content/Context;
    //   212: invokevirtual 159	com/miaozhen/mzmonitor/g:b	(Landroid/content/Context;)V
    //   215: aload_2
    //   216: invokestatic 161	com/miaozhen/mzmonitor/i:a	(Ljava/lang/String;)V
    //   219: aload 10
    //   221: ifnull +8 -> 229
    //   224: aload 10
    //   226: invokevirtual 63	java/net/HttpURLConnection:disconnect	()V
    //   229: return
    //   230: sipush 10000
    //   233: istore 11
    //   235: goto -174 -> 61
    //   238: ldc 129
    //   240: ldc 163
    //   242: invokestatic 137	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   245: pop
    //   246: aload_3
    //   247: aload_0
    //   248: getfield 23	com/miaozhen/mzmonitor/i:a	Lcom/miaozhen/mzmonitor/a;
    //   251: iconst_0
    //   252: invokevirtual 140	com/miaozhen/mzmonitor/b:a	(Lcom/miaozhen/mzmonitor/a;Z)V
    //   255: aconst_null
    //   256: astore_2
    //   257: goto -66 -> 191
    //   260: astore 4
    //   262: aload 10
    //   264: astore_1
    //   265: aload 4
    //   267: invokevirtual 164	java/io/IOException:printStackTrace	()V
    //   270: ldc 129
    //   272: new 166	java/lang/StringBuilder
    //   275: dup
    //   276: ldc 168
    //   278: invokespecial 169	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   281: aload 4
    //   283: invokevirtual 172	java/io/IOException:getMessage	()Ljava/lang/String;
    //   286: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   289: invokevirtual 177	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   292: invokestatic 137	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   295: pop
    //   296: aload_3
    //   297: aload_0
    //   298: getfield 23	com/miaozhen/mzmonitor/i:a	Lcom/miaozhen/mzmonitor/a;
    //   301: iconst_0
    //   302: invokevirtual 140	com/miaozhen/mzmonitor/b:a	(Lcom/miaozhen/mzmonitor/a;Z)V
    //   305: aload_2
    //   306: invokestatic 161	com/miaozhen/mzmonitor/i:a	(Ljava/lang/String;)V
    //   309: aload_1
    //   310: ifnull -81 -> 229
    //   313: aload_1
    //   314: invokevirtual 63	java/net/HttpURLConnection:disconnect	()V
    //   317: return
    //   318: astore 7
    //   320: aload 7
    //   322: invokevirtual 178	java/lang/NullPointerException:printStackTrace	()V
    //   325: ldc 129
    //   327: new 166	java/lang/StringBuilder
    //   330: dup
    //   331: ldc 180
    //   333: invokespecial 169	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   336: aload_0
    //   337: getfield 23	com/miaozhen/mzmonitor/i:a	Lcom/miaozhen/mzmonitor/a;
    //   340: invokevirtual 184	com/miaozhen/mzmonitor/a:a	()Ljava/lang/String;
    //   343: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   346: invokevirtual 177	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   349: invokestatic 137	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   352: pop
    //   353: aload_2
    //   354: invokestatic 161	com/miaozhen/mzmonitor/i:a	(Ljava/lang/String;)V
    //   357: aload_1
    //   358: ifnull -129 -> 229
    //   361: aload_1
    //   362: invokevirtual 63	java/net/HttpURLConnection:disconnect	()V
    //   365: return
    //   366: astore 5
    //   368: aload_2
    //   369: invokestatic 161	com/miaozhen/mzmonitor/i:a	(Ljava/lang/String;)V
    //   372: aload_1
    //   373: ifnull +7 -> 380
    //   376: aload_1
    //   377: invokevirtual 63	java/net/HttpURLConnection:disconnect	()V
    //   380: aload 5
    //   382: athrow
    //   383: astore 5
    //   385: aload 10
    //   387: astore_1
    //   388: goto -20 -> 368
    //   391: astore 7
    //   393: aload 10
    //   395: astore_1
    //   396: goto -76 -> 320
    //   399: astore 4
    //   401: aconst_null
    //   402: astore_1
    //   403: aconst_null
    //   404: astore_2
    //   405: goto -140 -> 265
    //
    // Exception table:
    //   from	to	target	type
    //   141	153	260	java/io/IOException
    //   166	191	260	java/io/IOException
    //   191	215	260	java/io/IOException
    //   238	255	260	java/io/IOException
    //   12	56	318	java/lang/NullPointerException
    //   61	104	318	java/lang/NullPointerException
    //   109	134	318	java/lang/NullPointerException
    //   12	56	366	finally
    //   61	104	366	finally
    //   109	134	366	finally
    //   265	305	366	finally
    //   320	353	366	finally
    //   141	153	383	finally
    //   166	191	383	finally
    //   191	215	383	finally
    //   238	255	383	finally
    //   141	153	391	java/lang/NullPointerException
    //   166	191	391	java/lang/NullPointerException
    //   191	215	391	java/lang/NullPointerException
    //   238	255	391	java/lang/NullPointerException
    //   12	56	399	java/io/IOException
    //   61	104	399	java/io/IOException
    //   109	134	399	java/io/IOException
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.i
 * JD-Core Version:    0.6.2
 */