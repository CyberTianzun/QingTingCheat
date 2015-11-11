package com.tencent.utils;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenConfig
{
  private static HashMap<String, OpenConfig> a = null;
  private static String b = null;
  private Context c = null;
  private String d = null;
  private JSONObject e = null;
  private long f = 0L;
  private int g = 0;
  private boolean h = true;

  private OpenConfig(Context paramContext, String paramString)
  {
    this.c = paramContext;
    this.d = paramString;
    a();
    b();
  }

  // ERROR //
  private String a(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 34	com/tencent/utils/OpenConfig:d	Ljava/lang/String;
    //   4: ifnull +116 -> 120
    //   7: new 55	java/lang/StringBuilder
    //   10: dup
    //   11: invokespecial 56	java/lang/StringBuilder:<init>	()V
    //   14: aload_1
    //   15: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc 62
    //   20: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: aload_0
    //   24: getfield 34	com/tencent/utils/OpenConfig:d	Ljava/lang/String;
    //   27: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   33: astore 16
    //   35: aload_0
    //   36: getfield 32	com/tencent/utils/OpenConfig:c	Landroid/content/Context;
    //   39: aload 16
    //   41: invokevirtual 72	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   44: astore 17
    //   46: aload 17
    //   48: astore 5
    //   50: new 74	java/io/BufferedReader
    //   53: dup
    //   54: new 76	java/io/InputStreamReader
    //   57: dup
    //   58: aload 5
    //   60: invokespecial 79	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   63: invokespecial 82	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   66: astore 6
    //   68: new 84	java/lang/StringBuffer
    //   71: dup
    //   72: invokespecial 85	java/lang/StringBuffer:<init>	()V
    //   75: astore 7
    //   77: aload 6
    //   79: invokevirtual 88	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   82: astore 12
    //   84: aload 12
    //   86: ifnull +69 -> 155
    //   89: aload 7
    //   91: aload 12
    //   93: invokevirtual 91	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   96: pop
    //   97: goto -20 -> 77
    //   100: astore 10
    //   102: aload 10
    //   104: invokevirtual 94	java/io/IOException:printStackTrace	()V
    //   107: aload 5
    //   109: invokevirtual 99	java/io/InputStream:close	()V
    //   112: aload 6
    //   114: invokevirtual 100	java/io/BufferedReader:close	()V
    //   117: ldc 102
    //   119: areturn
    //   120: aload_1
    //   121: astore 16
    //   123: goto -88 -> 35
    //   126: astore_2
    //   127: aload_0
    //   128: getfield 32	com/tencent/utils/OpenConfig:c	Landroid/content/Context;
    //   131: invokevirtual 106	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   134: aload_1
    //   135: invokevirtual 112	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   138: astore 4
    //   140: aload 4
    //   142: astore 5
    //   144: goto -94 -> 50
    //   147: astore_3
    //   148: aload_3
    //   149: invokevirtual 94	java/io/IOException:printStackTrace	()V
    //   152: ldc 102
    //   154: areturn
    //   155: aload 7
    //   157: invokevirtual 113	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   160: astore 14
    //   162: aload 5
    //   164: invokevirtual 99	java/io/InputStream:close	()V
    //   167: aload 6
    //   169: invokevirtual 100	java/io/BufferedReader:close	()V
    //   172: aload 14
    //   174: areturn
    //   175: astore 15
    //   177: aload 15
    //   179: invokevirtual 94	java/io/IOException:printStackTrace	()V
    //   182: aload 14
    //   184: areturn
    //   185: astore 11
    //   187: aload 11
    //   189: invokevirtual 94	java/io/IOException:printStackTrace	()V
    //   192: ldc 102
    //   194: areturn
    //   195: astore 8
    //   197: aload 5
    //   199: invokevirtual 99	java/io/InputStream:close	()V
    //   202: aload 6
    //   204: invokevirtual 100	java/io/BufferedReader:close	()V
    //   207: aload 8
    //   209: athrow
    //   210: astore 9
    //   212: aload 9
    //   214: invokevirtual 94	java/io/IOException:printStackTrace	()V
    //   217: goto -10 -> 207
    //
    // Exception table:
    //   from	to	target	type
    //   77	84	100	java/io/IOException
    //   89	97	100	java/io/IOException
    //   155	162	100	java/io/IOException
    //   0	35	126	java/io/FileNotFoundException
    //   35	46	126	java/io/FileNotFoundException
    //   127	140	147	java/io/IOException
    //   162	172	175	java/io/IOException
    //   107	117	185	java/io/IOException
    //   77	84	195	finally
    //   89	97	195	finally
    //   102	107	195	finally
    //   155	162	195	finally
    //   197	207	210	java/io/IOException
  }

  private void a()
  {
    String str = a("com.tencent.open.config.json");
    try
    {
      this.e = new JSONObject(str);
      return;
    }
    catch (JSONException localJSONException)
    {
      this.e = new JSONObject();
    }
  }

  private void a(String paramString1, String paramString2)
  {
    try
    {
      if (this.d != null)
        paramString1 = paramString1 + "." + this.d;
      OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(this.c.openFileOutput(paramString1, 0));
      localOutputStreamWriter.write(paramString2);
      localOutputStreamWriter.flush();
      localOutputStreamWriter.close();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  private void a(JSONObject paramJSONObject)
  {
    b("cgi back, do update");
    this.e = paramJSONObject;
    a("com.tencent.open.config.json", paramJSONObject.toString());
    this.f = SystemClock.elapsedRealtime();
  }

  private void b()
  {
    if (this.g != 0)
    {
      b("update thread is running, return");
      return;
    }
    this.g = 1;
    final Bundle localBundle = new Bundle();
    localBundle.putString("appid", this.d);
    localBundle.putString("appid_for_getting_config", this.d);
    localBundle.putString("status_os", Build.VERSION.RELEASE);
    localBundle.putString("status_machine", Build.MODEL);
    localBundle.putString("status_version", Build.VERSION.SDK);
    localBundle.putString("sdkv", "2.2.1");
    localBundle.putString("sdkp", "a");
    new Thread()
    {
      public void run()
      {
        try
        {
          JSONObject localJSONObject = Util.parseJson(HttpUtils.openUrl2(OpenConfig.a(OpenConfig.this), "http://cgi.connect.qq.com/qqconnectopen/openapi/policy_conf", "GET", localBundle).response);
          OpenConfig.a(OpenConfig.this, localJSONObject);
          OpenConfig.a(OpenConfig.this, 0);
          return;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }
    .start();
  }

  private void b(String paramString)
  {
    if (this.h)
      Log.i("OpenConfig", paramString + "; appid: " + this.d);
  }

  private void c()
  {
    int i = this.e.optInt("Common_frequency");
    if (i == 0)
      i = 1;
    long l = i * 3600000;
    if (SystemClock.elapsedRealtime() - this.f >= l)
      b();
  }

  public static OpenConfig getInstance(Context paramContext, String paramString)
  {
    if (a == null)
      a = new HashMap();
    if (paramString != null)
      b = paramString;
    if (paramString == null)
      if (b == null)
        break label74;
    label74: for (paramString = b; ; paramString = "0")
    {
      OpenConfig localOpenConfig = (OpenConfig)a.get(paramString);
      if (localOpenConfig == null)
      {
        localOpenConfig = new OpenConfig(paramContext, paramString);
        a.put(paramString, localOpenConfig);
      }
      return localOpenConfig;
    }
  }

  public Object get(String paramString)
  {
    b("get " + paramString);
    c();
    return this.e.opt(paramString);
  }

  public boolean getBoolean(String paramString)
  {
    b("get " + paramString);
    c();
    Object localObject = this.e.opt(paramString);
    if (localObject == null);
    do
    {
      return false;
      if ((localObject instanceof Integer))
      {
        if (!localObject.equals(Integer.valueOf(0)));
        for (boolean bool = true; ; bool = false)
          return bool;
      }
    }
    while (!(localObject instanceof Boolean));
    return ((Boolean)localObject).booleanValue();
  }

  public double getDouble(String paramString)
  {
    b("get " + paramString);
    c();
    return this.e.optDouble(paramString, 0.0D);
  }

  public int getInt(String paramString)
  {
    b("get " + paramString);
    c();
    return this.e.optInt(paramString);
  }

  public long getLong(String paramString)
  {
    b("get " + paramString);
    c();
    return this.e.optLong(paramString);
  }

  public String getString(String paramString)
  {
    b("get " + paramString);
    c();
    return this.e.optString(paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.utils.OpenConfig
 * JD-Core Version:    0.6.2
 */