package com.tencent.open.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.tencent.utils.OpenConfig;
import java.util.ArrayList;
import java.util.Random;

public class c
{
  private static c a = null;
  private long b = 0L;
  private int c = 3;
  private boolean d = false;
  private Random e = new Random();
  private b f;
  private ArrayList<d> g = new ArrayList();
  private ArrayList<d> h = new ArrayList();

  public static c a()
  {
    if (a == null)
      a = new c();
    return a;
  }

  private String a(Context paramContext)
  {
    try
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager == null)
      {
        Log.e("cgi_report_debug", "ReportManager getAPN failed:ConnectivityManager == null");
        return "no_net";
      }
      NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      if ((localNetworkInfo == null) || (!localNetworkInfo.isAvailable()))
      {
        Log.e("cgi_report_debug", "ReportManager getAPN failed:NetworkInfo == null");
        return "no_net";
      }
      if (localNetworkInfo.getTypeName().toUpperCase().equals("WIFI"))
      {
        Log.i("cgi_report_debug", "ReportManager getAPN type = wifi");
        return "wifi";
      }
      String str1 = localNetworkInfo.getExtraInfo();
      if (str1 == null)
      {
        Log.e("cgi_report_debug", "ReportManager getAPN failed:extraInfo == null");
        return "mobile_unknow";
      }
      String str2 = str1.toLowerCase();
      Log.i("cgi_report_debug", "ReportManager getAPN type = " + str2);
      return str2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "unknow";
  }

  private void a(Context paramContext, String paramString)
  {
    Log.i("cgi_report_debug", "ReportManager doUpload start");
    this.d = true;
    this.g = this.f.c();
    this.f.b();
    this.h = this.f.d();
    this.f.a();
    Bundle localBundle = new Bundle();
    localBundle.putString("appid", paramString);
    localBundle.putString("releaseversion", "QQConnect_SDK_Android_1_7");
    localBundle.putString("device", Build.DEVICE);
    localBundle.putString("qua", "V1_AND_OpenSDK_2.2.1_1077_RDM_B");
    localBundle.putString("key", "apn,frequency,commandid,resultcode,tmcost,reqsize,rspsize,detail,deviceinfo");
    for (int i = 0; i < this.g.size(); i++)
    {
      localBundle.putString(i + "_1", ((d)this.g.get(i)).a());
      localBundle.putString(i + "_2", ((d)this.g.get(i)).b());
      localBundle.putString(i + "_3", ((d)this.g.get(i)).c());
      localBundle.putString(i + "_4", ((d)this.g.get(i)).d());
      localBundle.putString(i + "_5", ((d)this.g.get(i)).e());
      localBundle.putString(i + "_6", ((d)this.g.get(i)).f());
      localBundle.putString(i + "_7", ((d)this.g.get(i)).g());
      localBundle.putString(i + "_8", ((d)this.g.get(i)).h());
      String str2 = a.b(paramContext) + ((d)this.g.get(i)).i();
      localBundle.putString(i + "_9", str2);
    }
    for (int j = this.g.size(); j < this.h.size() + this.g.size(); j++)
    {
      int k = j - this.g.size();
      localBundle.putString(j + "_1", ((d)this.h.get(k)).a());
      localBundle.putString(j + "_2", ((d)this.h.get(k)).b());
      localBundle.putString(j + "_3", ((d)this.h.get(k)).c());
      localBundle.putString(j + "_4", ((d)this.h.get(k)).d());
      localBundle.putString(j + "_5", ((d)this.h.get(k)).e());
      localBundle.putString(j + "_6", ((d)this.h.get(k)).f());
      localBundle.putString(j + "_7", ((d)this.h.get(k)).g());
      localBundle.putString(j + "_8", ((d)this.h.get(k)).h());
      String str1 = a.b(paramContext) + ((d)this.h.get(k)).i();
      localBundle.putString(j + "_9", str1);
    }
    a(paramContext, "http://wspeed.qq.com/w.cgi", "POST", localBundle);
  }

  private void a(Context paramContext, String paramString1, long paramLong1, long paramLong2, long paramLong3, int paramInt, String paramString2, String paramString3)
  {
    long l = SystemClock.elapsedRealtime() - paramLong1;
    Log.i("cgi_report_debug", "ReportManager updateDB url=" + paramString1 + ",resultCode=" + paramInt + ",timeCost=" + l + ",reqSize=" + paramLong2 + ",rspSize=" + paramLong3);
    int i = 100 / b(paramContext, paramInt);
    int j;
    if (i <= 0)
      j = 1;
    while (true)
    {
      String str = a(paramContext);
      this.f.a(str, j + "", paramString1, paramInt, l, paramLong2, paramLong3, paramString3);
      return;
      if (i > 100)
        j = 100;
      else
        j = i;
    }
  }

  private void a(final Context paramContext, final String paramString1, String paramString2, final Bundle paramBundle)
  {
    new Thread()
    {
      // ERROR //
      public void run()
      {
        // Byte code:
        //   0: ldc 38
        //   2: new 40	java/lang/StringBuilder
        //   5: dup
        //   6: invokespecial 41	java/lang/StringBuilder:<init>	()V
        //   9: ldc 43
        //   11: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   14: aload_0
        //   15: getfield 22	com/tencent/open/a/c$1:a	Ljava/lang/String;
        //   18: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   21: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   24: invokestatic 57	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   27: pop
        //   28: aload_0
        //   29: getfield 20	com/tencent/open/a/c$1:d	Lcom/tencent/open/a/c;
        //   32: aload_0
        //   33: getfield 24	com/tencent/open/a/c$1:b	Landroid/content/Context;
        //   36: aconst_null
        //   37: invokestatic 63	com/tencent/utils/OpenConfig:getInstance	(Landroid/content/Context;Ljava/lang/String;)Lcom/tencent/utils/OpenConfig;
        //   40: ldc 65
        //   42: invokevirtual 69	com/tencent/utils/OpenConfig:getInt	(Ljava/lang/String;)I
        //   45: invokestatic 72	com/tencent/open/a/c:a	(Lcom/tencent/open/a/c;I)I
        //   48: pop
        //   49: aload_0
        //   50: getfield 20	com/tencent/open/a/c$1:d	Lcom/tencent/open/a/c;
        //   53: astore_3
        //   54: aload_0
        //   55: getfield 20	com/tencent/open/a/c$1:d	Lcom/tencent/open/a/c;
        //   58: invokestatic 75	com/tencent/open/a/c:a	(Lcom/tencent/open/a/c;)I
        //   61: ifne +200 -> 261
        //   64: iconst_3
        //   65: istore 4
        //   67: aload_3
        //   68: iload 4
        //   70: invokestatic 72	com/tencent/open/a/c:a	(Lcom/tencent/open/a/c;I)I
        //   73: pop
        //   74: iconst_0
        //   75: istore 6
        //   77: iconst_0
        //   78: istore 7
        //   80: iinc 7 1
        //   83: ldc 38
        //   85: new 40	java/lang/StringBuilder
        //   88: dup
        //   89: invokespecial 41	java/lang/StringBuilder:<init>	()V
        //   92: ldc 77
        //   94: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   97: iload 7
        //   99: invokevirtual 80	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   102: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   105: invokestatic 57	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   108: pop
        //   109: aload_0
        //   110: getfield 24	com/tencent/open/a/c$1:b	Landroid/content/Context;
        //   113: aconst_null
        //   114: aload_0
        //   115: getfield 22	com/tencent/open/a/c$1:a	Ljava/lang/String;
        //   118: invokestatic 86	com/tencent/utils/HttpUtils:getHttpClient	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Lorg/apache/http/client/HttpClient;
        //   121: astore 19
        //   123: new 88	org/apache/http/client/methods/HttpPost
        //   126: dup
        //   127: aload_0
        //   128: getfield 22	com/tencent/open/a/c$1:a	Ljava/lang/String;
        //   131: invokespecial 91	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
        //   134: astore 20
        //   136: aload 20
        //   138: ldc 93
        //   140: ldc 95
        //   142: invokevirtual 99	org/apache/http/client/methods/HttpPost:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
        //   145: aload 20
        //   147: ldc 101
        //   149: ldc 103
        //   151: invokevirtual 106	org/apache/http/client/methods/HttpPost:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
        //   154: aload 20
        //   156: new 108	org/apache/http/entity/ByteArrayEntity
        //   159: dup
        //   160: aload_0
        //   161: getfield 26	com/tencent/open/a/c$1:c	Landroid/os/Bundle;
        //   164: invokestatic 114	com/tencent/utils/Util:encodeUrl	(Landroid/os/Bundle;)Ljava/lang/String;
        //   167: invokevirtual 120	java/lang/String:getBytes	()[B
        //   170: invokespecial 123	org/apache/http/entity/ByteArrayEntity:<init>	([B)V
        //   173: invokevirtual 127	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
        //   176: aload 19
        //   178: aload 20
        //   180: invokeinterface 133 2 0
        //   185: invokeinterface 139 1 0
        //   190: invokeinterface 145 1 0
        //   195: sipush 200
        //   198: if_icmpeq +75 -> 273
        //   201: ldc 38
        //   203: ldc 147
        //   205: invokestatic 150	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   208: pop
        //   209: aload_0
        //   210: getfield 20	com/tencent/open/a/c$1:d	Lcom/tencent/open/a/c;
        //   213: iconst_0
        //   214: invokestatic 153	com/tencent/open/a/c:a	(Lcom/tencent/open/a/c;Z)Z
        //   217: pop
        //   218: ldc 38
        //   220: new 40	java/lang/StringBuilder
        //   223: dup
        //   224: invokespecial 41	java/lang/StringBuilder:<init>	()V
        //   227: ldc 155
        //   229: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   232: aload_0
        //   233: getfield 22	com/tencent/open/a/c$1:a	Ljava/lang/String;
        //   236: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   239: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   242: invokestatic 57	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   245: pop
        //   246: iload 6
        //   248: iconst_1
        //   249: if_icmpne +96 -> 345
        //   252: ldc 38
        //   254: ldc 157
        //   256: invokestatic 57	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   259: pop
        //   260: return
        //   261: aload_0
        //   262: getfield 20	com/tencent/open/a/c$1:d	Lcom/tencent/open/a/c;
        //   265: invokestatic 75	com/tencent/open/a/c:a	(Lcom/tencent/open/a/c;)I
        //   268: istore 4
        //   270: goto -203 -> 67
        //   273: ldc 38
        //   275: ldc 159
        //   277: invokestatic 57	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   280: pop
        //   281: iconst_1
        //   282: istore 6
        //   284: goto -75 -> 209
        //   287: astore 17
        //   289: aload 17
        //   291: invokevirtual 162	org/apache/http/conn/ConnectTimeoutException:printStackTrace	()V
        //   294: ldc 38
        //   296: ldc 164
        //   298: invokestatic 150	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   301: pop
        //   302: iload 7
        //   304: aload_0
        //   305: getfield 20	com/tencent/open/a/c$1:d	Lcom/tencent/open/a/c;
        //   308: invokestatic 75	com/tencent/open/a/c:a	(Lcom/tencent/open/a/c;)I
        //   311: if_icmplt -231 -> 80
        //   314: goto -105 -> 209
        //   317: astore 16
        //   319: aload 16
        //   321: invokevirtual 165	java/net/SocketTimeoutException:printStackTrace	()V
        //   324: goto -22 -> 302
        //   327: astore 9
        //   329: aload 9
        //   331: invokevirtual 166	java/lang/Exception:printStackTrace	()V
        //   334: ldc 38
        //   336: ldc 168
        //   338: invokestatic 150	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   341: pop
        //   342: goto -133 -> 209
        //   345: ldc 38
        //   347: ldc 170
        //   349: invokestatic 150	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   352: pop
        //   353: aload_0
        //   354: getfield 20	com/tencent/open/a/c$1:d	Lcom/tencent/open/a/c;
        //   357: invokestatic 173	com/tencent/open/a/c:c	(Lcom/tencent/open/a/c;)Lcom/tencent/open/a/b;
        //   360: aload_0
        //   361: getfield 20	com/tencent/open/a/c$1:d	Lcom/tencent/open/a/c;
        //   364: invokestatic 176	com/tencent/open/a/c:b	(Lcom/tencent/open/a/c;)Ljava/util/ArrayList;
        //   367: invokevirtual 181	com/tencent/open/a/b:a	(Ljava/util/ArrayList;)I
        //   370: pop
        //   371: return
        //   372: astore 23
        //   374: aload 23
        //   376: astore 9
        //   378: iconst_1
        //   379: istore 6
        //   381: goto -52 -> 329
        //   384: astore 22
        //   386: aload 22
        //   388: astore 16
        //   390: iconst_1
        //   391: istore 6
        //   393: goto -74 -> 319
        //   396: astore 21
        //   398: aload 21
        //   400: astore 17
        //   402: iconst_1
        //   403: istore 6
        //   405: goto -116 -> 289
        //
        // Exception table:
        //   from	to	target	type
        //   109	209	287	org/apache/http/conn/ConnectTimeoutException
        //   109	209	317	java/net/SocketTimeoutException
        //   109	209	327	java/lang/Exception
        //   273	281	372	java/lang/Exception
        //   273	281	384	java/net/SocketTimeoutException
        //   273	281	396	org/apache/http/conn/ConnectTimeoutException
      }
    }
    .start();
  }

  private boolean a(Context paramContext, int paramInt)
  {
    int i = b(paramContext, paramInt);
    if (this.e.nextInt(100) < i)
    {
      Log.i("cgi_report_debug", "ReportManager availableForFrequency = ture");
      return true;
    }
    Log.i("cgi_report_debug", "ReportManager availableForFrequency = false");
    return false;
  }

  private int b(Context paramContext, int paramInt)
  {
    if (paramInt == 0)
    {
      int j = OpenConfig.getInstance(paramContext, null).getInt("Common_CGIReportFrequencySuccess");
      Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencySuccess     config_value:" + j);
      if (j == 0)
        j = 10;
      Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencySuccess     result_value:" + j);
      return j;
    }
    int i = OpenConfig.getInstance(paramContext, null).getInt("Common_CGIReportFrequencyFailed");
    Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencyFailed     config_value:" + i);
    if (i == 0)
      i = 100;
    Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencyFailed     result_value:" + i);
    return i;
  }

  private boolean b(Context paramContext)
  {
    long l1 = OpenConfig.getInstance(paramContext, null).getLong("Common_CGIReportTimeinterval");
    Log.d("OpenConfig_test", "config 5:Common_CGIReportTimeinterval     config_value:" + l1);
    if (l1 == 0L)
      l1 = 1200L;
    Log.d("OpenConfig_test", "config 5:Common_CGIReportTimeinterval     result_value:" + l1);
    long l2 = System.currentTimeMillis() / 1000L;
    if ((this.b == 0L) || (l1 + this.b <= l2))
    {
      this.b = l2;
      Log.i("cgi_report_debug", "ReportManager availableForTime = ture");
      return true;
    }
    Log.i("cgi_report_debug", "ReportManager availableForTime = false");
    return false;
  }

  private boolean c(Context paramContext)
  {
    int i = OpenConfig.getInstance(paramContext, null).getInt("Common_CGIReportMaxcount");
    Log.d("OpenConfig_test", "config 6:Common_CGIReportMaxcount     config_value:" + i);
    if (i == 0)
      i = 20;
    Log.d("OpenConfig_test", "config 6:Common_CGIReportMaxcount     result_value:" + i);
    if (this.f.e() >= i)
    {
      Log.i("cgi_report_debug", "ReportManager availableForCount = ture");
      return true;
    }
    Log.i("cgi_report_debug", "ReportManager availableForCount = false");
    return false;
  }

  public void a(Context paramContext, String paramString1, long paramLong1, long paramLong2, long paramLong3, int paramInt, String paramString2)
  {
    a(paramContext, paramString1, paramLong1, paramLong2, paramLong3, paramInt, paramString2, "", null);
  }

  public void a(Context paramContext, String paramString1, long paramLong1, long paramLong2, long paramLong3, int paramInt, String paramString2, String paramString3, String paramString4)
  {
    if (paramString4 == null)
      paramString4 = "1000067";
    if (this.f == null)
      this.f = new b(paramContext);
    if (a(paramContext, paramInt) == true)
    {
      a(paramContext, paramString1, paramLong1, paramLong2, paramLong3, paramInt, paramString2, paramString3);
      if (this.d != true)
        break label66;
    }
    label66: 
    do
    {
      return;
      if (b(paramContext) == true)
      {
        a(paramContext, paramString4);
        return;
      }
    }
    while (c(paramContext) != true);
    a(paramContext, paramString4);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.a.c
 * JD-Core Version:    0.6.2
 */