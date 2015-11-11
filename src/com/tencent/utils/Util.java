package com.tencent.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class Util
{
  private static final String a = Util.class.getName();
  private static boolean b = true;
  private static String c = "0123456789ABCDEF";

  private static char a(int paramInt)
  {
    int i = paramInt & 0xF;
    if (i < 10)
      return (char)(i + 48);
    return (char)(97 + (i - 10));
  }

  private static String a(HttpResponse paramHttpResponse)
    throws IllegalStateException, IOException
  {
    InputStream localInputStream = paramHttpResponse.getEntity().getContent();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    Header localHeader = paramHttpResponse.getFirstHeader("Content-Encoding");
    if ((localHeader != null) && (localHeader.getValue().toLowerCase().indexOf("gzip") > -1));
    for (Object localObject = new GZIPInputStream(localInputStream); ; localObject = localInputStream)
    {
      byte[] arrayOfByte = new byte[512];
      while (true)
      {
        int i = ((InputStream)localObject).read(arrayOfByte);
        if (i == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      return new String(localByteArrayOutputStream.toByteArray());
    }
  }

  private static void a(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent();
    localIntent.setComponent(new ComponentName(paramString1, paramString2));
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.addFlags(1073741824);
    localIntent.addFlags(268435456);
    localIntent.setData(Uri.parse(paramString3));
    paramContext.startActivity(localIntent);
  }

  private static boolean a(Context paramContext)
  {
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo("com.tencent.mtt", 64);
      String str1 = localPackageInfo.versionName;
      int i = SystemUtils.compareVersion(str1, "4.3");
      boolean bool1 = false;
      Signature[] arrayOfSignature;
      if (i >= 0)
      {
        boolean bool2 = str1.startsWith("4.4");
        bool1 = false;
        if (!bool2)
        {
          arrayOfSignature = localPackageInfo.signatures;
          bool1 = false;
          if (arrayOfSignature == null);
        }
      }
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(arrayOfSignature[0].toByteArray());
        String str2 = toHexString(localMessageDigest.digest());
        localMessageDigest.reset();
        boolean bool3 = str2.equals("d8391a394d4a179e6fe7bdb8a301258b");
        bool1 = false;
        if (bool3)
          bool1 = true;
        return bool1;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        localNoSuchAlgorithmException.printStackTrace();
        return false;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return false;
  }

  public static Bundle decodeUrl(String paramString)
  {
    Bundle localBundle = new Bundle();
    if (paramString != null)
    {
      String[] arrayOfString1 = paramString.split("&");
      int i = arrayOfString1.length;
      for (int j = 0; j < i; j++)
      {
        String[] arrayOfString2 = arrayOfString1[j].split("=");
        if (arrayOfString2.length == 2)
          localBundle.putString(URLDecoder.decode(arrayOfString2[0]), URLDecoder.decode(arrayOfString2[1]));
      }
    }
    return localBundle;
  }

  public static JSONObject decodeUrlToJson(JSONObject paramJSONObject, String paramString)
  {
    if (paramJSONObject == null)
      paramJSONObject = new JSONObject();
    if (paramString != null)
    {
      String[] arrayOfString1 = paramString.split("&");
      int i = arrayOfString1.length;
      int j = 0;
      while (true)
        if (j < i)
        {
          String[] arrayOfString2 = arrayOfString1[j].split("=");
          if (arrayOfString2.length == 2);
          try
          {
            paramJSONObject.put(URLDecoder.decode(arrayOfString2[0]), URLDecoder.decode(arrayOfString2[1]));
            j++;
          }
          catch (JSONException localJSONException)
          {
            while (true)
              localJSONException.printStackTrace();
          }
        }
    }
    return paramJSONObject;
  }

  public static String encodePostBody(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramBundle.size();
    Iterator localIterator = paramBundle.keySet().iterator();
    int j = -1;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      int k = j + 1;
      Object localObject = paramBundle.get(str);
      if (!(localObject instanceof String))
      {
        j = k;
      }
      else
      {
        localStringBuilder.append("Content-Disposition: form-data; name=\"" + str + "\"" + "\r\n" + "\r\n" + (String)localObject);
        if (k < i - 1)
          localStringBuilder.append("\r\n--" + paramString + "\r\n");
        j = k;
      }
    }
    return localStringBuilder.toString();
  }

  public static String encodeUrl(Bundle paramBundle)
  {
    if (paramBundle == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramBundle.keySet().iterator();
    int i = 1;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramBundle.get(str);
      if (((localObject instanceof String)) || ((localObject instanceof String[])))
      {
        label132: label174: label209: int j;
        if ((localObject instanceof String[]))
        {
          String[] arrayOfString;
          int k;
          if (i != 0)
          {
            i = 0;
            localStringBuilder.append(URLEncoder.encode(str) + "=");
            arrayOfString = (String[])paramBundle.getStringArray(str);
            if (arrayOfString == null)
              break label209;
            k = 0;
            if (k >= arrayOfString.length)
              break label209;
            if (k != 0)
              break label174;
            localStringBuilder.append(URLEncoder.encode(arrayOfString[k]));
          }
          while (true)
          {
            k++;
            break label132;
            localStringBuilder.append("&");
            break;
            localStringBuilder.append(URLEncoder.encode("," + arrayOfString[k]));
          }
          j = i;
          i = j;
        }
        else
        {
          if (i != 0)
            i = 0;
          while (true)
          {
            localStringBuilder.append(URLEncoder.encode(str) + "=" + URLEncoder.encode(paramBundle.getString(str)));
            j = i;
            break;
            localStringBuilder.append("&");
          }
        }
      }
    }
    return localStringBuilder.toString();
  }

  public static String encrypt(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      if (arrayOfByte != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        int i = arrayOfByte.length;
        for (int j = 0; j < i; j++)
        {
          int k = arrayOfByte[j];
          localStringBuilder.append(a(k >>> 4));
          localStringBuilder.append(a(k));
        }
        String str = localStringBuilder.toString();
        paramString = str;
      }
      return paramString;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    return paramString;
  }

  public static boolean fileExists(String paramString)
  {
    if (paramString == null);
    File localFile;
    do
    {
      return false;
      localFile = new File(paramString);
    }
    while ((localFile == null) || (!localFile.exists()));
    return true;
  }

  public static String getAppVersion(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      String str = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Log.e(a, "getAppVersion error" + localNameNotFoundException.getMessage());
      localNameNotFoundException.printStackTrace();
    }
    return "";
  }

  public static final String getApplicationLable(Context paramContext)
  {
    if (paramContext != null)
    {
      CharSequence localCharSequence = paramContext.getPackageManager().getApplicationLabel(paramContext.getApplicationInfo());
      if (localCharSequence != null)
        return localCharSequence.toString();
    }
    return null;
  }

  public static String getUserIp()
  {
    try
    {
      InetAddress localInetAddress;
      do
      {
        Enumeration localEnumeration1 = NetworkInterface.getNetworkInterfaces();
        Enumeration localEnumeration2;
        while (!localEnumeration2.hasMoreElements())
        {
          if ((localEnumeration1 == null) || (!localEnumeration1.hasMoreElements()))
            break;
          localEnumeration2 = ((NetworkInterface)localEnumeration1.nextElement()).getInetAddresses();
        }
        localInetAddress = (InetAddress)localEnumeration2.nextElement();
      }
      while (localInetAddress.isLoopbackAddress());
      String str = localInetAddress.getHostAddress().toString();
      return str;
    }
    catch (SocketException localSocketException)
    {
      logd("Tencent-Util", localSocketException.toString());
    }
    return "";
  }

  public static boolean hasSDCard()
  {
    boolean bool = Environment.getExternalStorageState().equals("mounted");
    File localFile = null;
    if (bool)
      localFile = Environment.getExternalStorageDirectory();
    return localFile != null;
  }

  public static String hexToString(String paramString)
  {
    int i = 0;
    if ("0x".equals(paramString.substring(0, 2)))
      paramString = paramString.substring(2);
    byte[] arrayOfByte = new byte[paramString.length() / 2];
    while (true)
      if (i < arrayOfByte.length)
      {
        int j = i * 2;
        int k = 2 + i * 2;
        try
        {
          arrayOfByte[i] = ((byte)(0xFF & Integer.parseInt(paramString.substring(j, k), 16)));
          i++;
        }
        catch (Exception localException2)
        {
          while (true)
            localException2.printStackTrace();
        }
      }
    try
    {
      String str = new String(arrayOfByte, "utf-8");
      return str;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return paramString;
  }

  public static boolean isEmpty(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }

  public static boolean isMobileQQSupportShare(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      int i = SystemUtils.compareVersion(localPackageManager.getPackageInfo("com.tencent.mobileqq", 0).versionName, "4.1");
      boolean bool = false;
      if (i >= 0)
        bool = true;
      return bool;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Log.d("checkMobileQQ", "error");
    }
    return false;
  }

  public static final boolean isValidPath(String paramString)
  {
    if (paramString == null);
    File localFile;
    do
    {
      return false;
      localFile = new File(paramString);
    }
    while ((localFile == null) || (!localFile.exists()));
    return true;
  }

  public static final boolean isValidUrl(String paramString)
  {
    if (paramString == null);
    while ((!paramString.startsWith("http://")) && (!paramString.startsWith("https://")))
      return false;
    return true;
  }

  public static void logd(String paramString1, String paramString2)
  {
    if (b)
      Log.d(paramString1, paramString2);
  }

  public static boolean openBrowser(Context paramContext, String paramString)
  {
    try
    {
      boolean bool2 = a(paramContext);
      bool1 = bool2;
      if (bool1);
      try
      {
        a(paramContext, "com.tencent.mtt", "com.tencent.mtt.MainActivity", paramString);
        break label136;
        a(paramContext, "com.android.browser", "com.android.browser.BrowserActivity", paramString);
      }
      catch (Exception localException7)
      {
      }
      if (bool1)
        try
        {
          a(paramContext, "com.android.browser", "com.android.browser.BrowserActivity", paramString);
        }
        catch (Exception localException4)
        {
          try
          {
            a(paramContext, "com.google.android.browser", "com.android.browser.BrowserActivity", paramString);
          }
          catch (Exception localException5)
          {
            try
            {
              a(paramContext, "com.android.chrome", "com.google.android.apps.chrome.Main", paramString);
            }
            catch (Exception localException6)
            {
              return false;
            }
          }
        }
      else
        try
        {
          a(paramContext, "com.google.android.browser", "com.android.browser.BrowserActivity", paramString);
        }
        catch (Exception localException2)
        {
          try
          {
            a(paramContext, "com.android.chrome", "com.google.android.apps.chrome.Main", paramString);
          }
          catch (Exception localException3)
          {
            return false;
          }
        }
    }
    catch (Exception localException1)
    {
      while (true)
        boolean bool1 = false;
    }
    label136: return true;
  }

  public static JSONObject parseJson(String paramString)
    throws JSONException
  {
    if (paramString.equals("false"))
      paramString = "{value : false}";
    if (paramString.equals("true"))
      paramString = "{value : true}";
    if (paramString.contains("allback("))
      paramString = paramString.replaceFirst("[\\s\\S]*allback\\(([\\s\\S]*)\\);[^\\)]*\\z", "$1").trim();
    if (paramString.contains("online"))
      paramString = "{online:" + paramString.charAt(-2 + paramString.length()) + "}";
    return new JSONObject(paramString);
  }

  public static Bundle parseUrl(String paramString)
  {
    String str = paramString.replace("auth://", "http://");
    try
    {
      URL localURL = new URL(str);
      Bundle localBundle = decodeUrl(localURL.getQuery());
      localBundle.putAll(decodeUrl(localURL.getRef()));
      return localBundle;
    }
    catch (MalformedURLException localMalformedURLException)
    {
    }
    return new Bundle();
  }

  public static JSONObject parseUrlToJson(String paramString)
  {
    String str = paramString.replace("auth://", "http://");
    try
    {
      URL localURL = new URL(str);
      JSONObject localJSONObject = decodeUrlToJson(null, localURL.getQuery());
      decodeUrlToJson(localJSONObject, localURL.getRef());
      return localJSONObject;
    }
    catch (MalformedURLException localMalformedURLException)
    {
    }
    return new JSONObject();
  }

  public static void reportBernoulli(Context paramContext, String paramString1, long paramLong, String paramString2)
  {
    final Bundle localBundle = new Bundle();
    localBundle.putString("appid_for_getting_config", paramString2);
    localBundle.putString("strValue", paramString2);
    localBundle.putString("nValue", paramString1);
    localBundle.putString("qver", "2.2.1");
    if (paramLong != 0L)
      localBundle.putLong("elt", paramLong);
    new Thread()
    {
      public void run()
      {
        try
        {
          HttpUtils.openUrl2(this.a, "http://cgi.qplus.com/report/report", "GET", localBundle);
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }
    .start();
  }

  public static void showAlert(Context paramContext, String paramString1, String paramString2)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
    localBuilder.setTitle(paramString1);
    localBuilder.setMessage(paramString2);
    localBuilder.create().show();
  }

  // ERROR //
  public static final String subString(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 4
    //   3: aload_0
    //   4: invokestatic 584	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   7: ifeq +8 -> 15
    //   10: ldc 245
    //   12: astore_0
    //   13: aload_0
    //   14: areturn
    //   15: aload_2
    //   16: invokestatic 584	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   19: ifne +158 -> 177
    //   22: aload_0
    //   23: aload_2
    //   24: invokevirtual 587	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   27: arraylength
    //   28: iload_1
    //   29: if_icmple -16 -> 13
    //   32: iconst_0
    //   33: istore 6
    //   35: iload 4
    //   37: aload_0
    //   38: invokevirtual 416	java/lang/String:length	()I
    //   41: if_icmpge -28 -> 13
    //   44: aload_0
    //   45: iload 4
    //   47: iload 4
    //   49: iconst_1
    //   50: iadd
    //   51: invokevirtual 410	java/lang/String:substring	(II)Ljava/lang/String;
    //   54: aload_2
    //   55: invokevirtual 587	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   58: arraylength
    //   59: istore 7
    //   61: iload 6
    //   63: iload 7
    //   65: iadd
    //   66: iload_1
    //   67: if_icmple +51 -> 118
    //   70: aload_0
    //   71: iconst_0
    //   72: iload 4
    //   74: invokevirtual 410	java/lang/String:substring	(II)Ljava/lang/String;
    //   77: astore 8
    //   79: aload 8
    //   81: astore 9
    //   83: aload_3
    //   84: invokestatic 584	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   87: ifne +28 -> 115
    //   90: new 247	java/lang/StringBuilder
    //   93: dup
    //   94: invokespecial 248	java/lang/StringBuilder:<init>	()V
    //   97: aload 9
    //   99: invokevirtual 282	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: aload_3
    //   103: invokevirtual 282	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   106: invokevirtual 289	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   109: astore 11
    //   111: aload 11
    //   113: astore 9
    //   115: aload 9
    //   117: areturn
    //   118: iload 6
    //   120: iload 7
    //   122: iadd
    //   123: istore 6
    //   125: iinc 4 1
    //   128: goto -93 -> 35
    //   131: astore 5
    //   133: getstatic 593	java/lang/System:out	Ljava/io/PrintStream;
    //   136: new 247	java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial 248	java/lang/StringBuilder:<init>	()V
    //   143: ldc_w 595
    //   146: invokevirtual 282	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: aload 5
    //   151: invokevirtual 596	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   154: invokevirtual 282	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: invokevirtual 289	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   160: invokevirtual 601	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   163: aload_0
    //   164: areturn
    //   165: astore 10
    //   167: aload 9
    //   169: astore_0
    //   170: aload 10
    //   172: astore 5
    //   174: goto -41 -> 133
    //   177: ldc_w 603
    //   180: astore_2
    //   181: goto -159 -> 22
    //
    // Exception table:
    //   from	to	target	type
    //   22	32	131	java/lang/Exception
    //   35	61	131	java/lang/Exception
    //   70	79	131	java/lang/Exception
    //   83	111	165	java/lang/Exception
  }

  public static String toHexString(String paramString)
  {
    byte[] arrayOfByte = paramString.getBytes();
    StringBuilder localStringBuilder = new StringBuilder(2 * arrayOfByte.length);
    for (int i = 0; i < arrayOfByte.length; i++)
    {
      localStringBuilder.append(c.charAt((0xF0 & arrayOfByte[i]) >> 4));
      localStringBuilder.append(c.charAt((0xF & arrayOfByte[i]) >> 0));
    }
    return localStringBuilder.toString();
  }

  public static String toHexString(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      String str = Integer.toString(0xFF & paramArrayOfByte[i], 16);
      if (str.length() == 1)
        str = "0" + str;
      localStringBuilder.append(str);
    }
    return localStringBuilder.toString();
  }

  public static Statistic upload(Context paramContext, String paramString, Bundle paramBundle)
    throws MalformedURLException, IOException, HttpUtils.NetworkUnavailableException, HttpUtils.HttpStatusException
  {
    if (paramContext != null)
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager != null)
      {
        NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
        if ((localNetworkInfo == null) || (!localNetworkInfo.isAvailable()))
          throw new HttpUtils.NetworkUnavailableException("network unavailable");
      }
    }
    Bundle localBundle1 = new Bundle(paramBundle);
    String str1 = localBundle1.getString("appid_for_getting_config");
    localBundle1.remove("appid_for_getting_config");
    HttpClient localHttpClient = HttpUtils.getHttpClient(paramContext, str1, paramString);
    HttpPost localHttpPost = new HttpPost(paramString);
    Bundle localBundle2 = new Bundle();
    Iterator localIterator1 = localBundle1.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str3 = (String)localIterator1.next();
      Object localObject = localBundle1.get(str3);
      if ((localObject instanceof byte[]))
        localBundle2.putByteArray(str3, (byte[])localObject);
    }
    localHttpPost.setHeader("Content-Type", "multipart/form-data; boundary=3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
    localHttpPost.setHeader("Connection", "Keep-Alive");
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    localByteArrayOutputStream.write("--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
    localByteArrayOutputStream.write(encodePostBody(localBundle1, "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f").getBytes());
    if (!localBundle2.isEmpty())
    {
      int k = localBundle2.size();
      localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
      Iterator localIterator2 = localBundle2.keySet().iterator();
      int m = -1;
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        m++;
        localByteArrayOutputStream.write(("Content-Disposition: form-data; name=\"" + str2 + "\"; filename=\"" + "value.file" + "\"" + "\r\n").getBytes());
        localByteArrayOutputStream.write("Content-Type: application/octet-stream\r\n\r\n".getBytes());
        byte[] arrayOfByte2 = localBundle2.getByteArray(str2);
        if (arrayOfByte2 != null)
          localByteArrayOutputStream.write(arrayOfByte2);
        if (m < k - 1)
          localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n".getBytes());
      }
    }
    localByteArrayOutputStream.write("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f--\r\n".getBytes());
    byte[] arrayOfByte1 = localByteArrayOutputStream.toByteArray();
    int i = 0 + arrayOfByte1.length;
    localByteArrayOutputStream.close();
    localHttpPost.setEntity(new ByteArrayEntity(arrayOfByte1));
    HttpResponse localHttpResponse = localHttpClient.execute(localHttpPost);
    int j = localHttpResponse.getStatusLine().getStatusCode();
    if (j == 200)
      return new Statistic(a(localHttpResponse), i);
    throw new HttpUtils.HttpStatusException("http status code error:" + j);
  }

  public static class Statistic
  {
    public long reqSize;
    public String response;
    public long rspSize;

    public Statistic(String paramString, int paramInt)
    {
      this.response = paramString;
      this.reqSize = paramInt;
      if (this.response != null)
        this.rspSize = this.response.length();
    }
  }

  private static class TBufferedOutputStream extends BufferedOutputStream
  {
    private int a = 0;

    public TBufferedOutputStream(OutputStream paramOutputStream)
    {
      super();
    }

    public int getLength()
    {
      return this.a;
    }

    public void write(byte[] paramArrayOfByte)
      throws IOException
    {
      super.write(paramArrayOfByte);
      this.a += paramArrayOfByte.length;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.utils.Util
 * JD-Core Version:    0.6.2
 */