package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.exception.WeiboHttpException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.WeiboParameters;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;
import org.json.JSONObject;

public class AidTask
{
  private static final String AID_FILE_NAME = "weibo_sdk_aid";
  private static final String TAG = "AidTask";
  private static final int VERSION = 1;
  public static final int WHAT_LOAD_AID_API_ERR = 1002;
  public static final int WHAT_LOAD_AID_IO_ERR = 1003;
  public static final int WHAT_LOAD_AID_SUC = 1001;
  private static AidTask sInstance;
  private String mAppKey;
  private Context mContext;
  private volatile ReentrantLock mTaskLock = new ReentrantLock(true);

  private AidTask(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    new Thread(new Runnable()
    {
      public void run()
      {
        int i = 0;
        while (true)
        {
          if (i >= 1)
            return;
          File localFile = AidTask.this.getAidInfoFile(i);
          try
          {
            localFile.delete();
            label22: i++;
          }
          catch (Exception localException)
          {
            break label22;
          }
        }
      }
    }).start();
  }

  // ERROR //
  private void cacheAidInfo(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic 92	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   6: istore_3
    //   7: iload_3
    //   8: ifeq +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aconst_null
    //   15: astore 4
    //   17: new 94	java/io/FileOutputStream
    //   20: dup
    //   21: aload_0
    //   22: iconst_1
    //   23: invokespecial 66	com/sina/weibo/sdk/utils/AidTask:getAidInfoFile	(I)Ljava/io/File;
    //   26: invokespecial 97	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   29: astore 5
    //   31: aload 5
    //   33: aload_1
    //   34: invokevirtual 103	java/lang/String:getBytes	()[B
    //   37: invokevirtual 107	java/io/FileOutputStream:write	([B)V
    //   40: aload 5
    //   42: ifnull +79 -> 121
    //   45: aload 5
    //   47: invokevirtual 110	java/io/FileOutputStream:close	()V
    //   50: goto -39 -> 11
    //   53: astore 11
    //   55: aload 4
    //   57: ifnull -46 -> 11
    //   60: aload 4
    //   62: invokevirtual 110	java/io/FileOutputStream:close	()V
    //   65: goto -54 -> 11
    //   68: astore 7
    //   70: goto -59 -> 11
    //   73: astore 8
    //   75: aload 4
    //   77: ifnull +8 -> 85
    //   80: aload 4
    //   82: invokevirtual 110	java/io/FileOutputStream:close	()V
    //   85: aload 8
    //   87: athrow
    //   88: astore_2
    //   89: aload_0
    //   90: monitorexit
    //   91: aload_2
    //   92: athrow
    //   93: astore 10
    //   95: goto -84 -> 11
    //   98: astore 9
    //   100: goto -15 -> 85
    //   103: astore 8
    //   105: aload 5
    //   107: astore 4
    //   109: goto -34 -> 75
    //   112: astore 6
    //   114: aload 5
    //   116: astore 4
    //   118: goto -63 -> 55
    //   121: goto -110 -> 11
    //
    // Exception table:
    //   from	to	target	type
    //   17	31	53	java/lang/Exception
    //   60	65	68	java/io/IOException
    //   17	31	73	finally
    //   2	7	88	finally
    //   45	50	88	finally
    //   60	65	88	finally
    //   80	85	88	finally
    //   85	88	88	finally
    //   45	50	93	java/io/IOException
    //   80	85	98	java/io/IOException
    //   31	40	103	finally
    //   31	40	112	java/lang/Exception
  }

  // ERROR //
  private String encryptRsa(String paramString1, String paramString2)
    throws Exception
  {
    // Byte code:
    //   0: ldc 114
    //   2: invokestatic 120	javax/crypto/Cipher:getInstance	(Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   5: astore_3
    //   6: aload_3
    //   7: iconst_1
    //   8: aload_0
    //   9: aload_2
    //   10: invokespecial 124	com/sina/weibo/sdk/utils/AidTask:getPublicKey	(Ljava/lang/String;)Ljava/security/PublicKey;
    //   13: invokevirtual 128	javax/crypto/Cipher:init	(ILjava/security/Key;)V
    //   16: aconst_null
    //   17: astore 4
    //   19: aload_1
    //   20: ldc 130
    //   22: invokevirtual 133	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   25: astore 5
    //   27: new 135	java/io/ByteArrayOutputStream
    //   30: dup
    //   31: invokespecial 136	java/io/ByteArrayOutputStream:<init>	()V
    //   34: astore 6
    //   36: iconst_0
    //   37: istore 7
    //   39: aload_0
    //   40: aload 5
    //   42: iload 7
    //   44: bipush 117
    //   46: invokespecial 140	com/sina/weibo/sdk/utils/AidTask:splite	([BII)I
    //   49: istore 10
    //   51: iload 10
    //   53: iconst_m1
    //   54: if_icmpne +135 -> 189
    //   57: aload 6
    //   59: invokevirtual 143	java/io/ByteArrayOutputStream:flush	()V
    //   62: aload 6
    //   64: invokevirtual 146	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   67: astore 11
    //   69: ldc 11
    //   71: new 148	java/lang/StringBuilder
    //   74: dup
    //   75: ldc 150
    //   77: invokespecial 152	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   80: aload 11
    //   82: arraylength
    //   83: invokevirtual 156	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   86: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   89: invokestatic 165	com/sina/weibo/sdk/utils/LogUtil:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   92: aload 11
    //   94: invokestatic 171	com/sina/weibo/sdk/utils/Base64:encodebyte	([B)[B
    //   97: astore 12
    //   99: ldc 11
    //   101: new 148	java/lang/StringBuilder
    //   104: dup
    //   105: ldc 173
    //   107: invokespecial 152	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   110: aload 12
    //   112: arraylength
    //   113: invokevirtual 156	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   116: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   119: invokestatic 165	com/sina/weibo/sdk/utils/LogUtil:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   122: new 99	java/lang/String
    //   125: dup
    //   126: aload 12
    //   128: ldc 130
    //   130: invokespecial 176	java/lang/String:<init>	([BLjava/lang/String;)V
    //   133: astore 13
    //   135: new 148	java/lang/StringBuilder
    //   138: dup
    //   139: ldc 178
    //   141: invokespecial 152	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   144: aload 13
    //   146: invokevirtual 181	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   152: astore 14
    //   154: ldc 11
    //   156: new 148	java/lang/StringBuilder
    //   159: dup
    //   160: ldc 183
    //   162: invokespecial 152	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   165: aload 14
    //   167: invokevirtual 181	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   173: invokestatic 165	com/sina/weibo/sdk/utils/LogUtil:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   176: aload 6
    //   178: ifnull +8 -> 186
    //   181: aload 6
    //   183: invokevirtual 184	java/io/ByteArrayOutputStream:close	()V
    //   186: aload 14
    //   188: areturn
    //   189: aload_3
    //   190: aload 5
    //   192: iload 7
    //   194: iload 10
    //   196: invokevirtual 188	javax/crypto/Cipher:doFinal	([BII)[B
    //   199: astore 16
    //   201: aload 6
    //   203: aload 16
    //   205: invokevirtual 189	java/io/ByteArrayOutputStream:write	([B)V
    //   208: ldc 11
    //   210: new 148	java/lang/StringBuilder
    //   213: dup
    //   214: ldc 191
    //   216: invokespecial 152	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   219: iload 7
    //   221: invokevirtual 156	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   224: ldc 193
    //   226: invokevirtual 181	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   229: iload 10
    //   231: invokevirtual 156	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   234: ldc 195
    //   236: invokevirtual 181	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: aload 16
    //   241: arraylength
    //   242: invokevirtual 156	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   245: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   248: invokestatic 165	com/sina/weibo/sdk/utils/LogUtil:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   251: iload 7
    //   253: iload 10
    //   255: iadd
    //   256: istore 7
    //   258: goto -219 -> 39
    //   261: astore 8
    //   263: aload 4
    //   265: ifnull +8 -> 273
    //   268: aload 4
    //   270: invokevirtual 184	java/io/ByteArrayOutputStream:close	()V
    //   273: aload 8
    //   275: athrow
    //   276: astore 15
    //   278: aload 14
    //   280: areturn
    //   281: astore 9
    //   283: goto -10 -> 273
    //   286: astore 8
    //   288: aload 6
    //   290: astore 4
    //   292: goto -29 -> 263
    //
    // Exception table:
    //   from	to	target	type
    //   27	36	261	finally
    //   181	186	276	java/io/IOException
    //   268	273	281	java/io/IOException
    //   39	51	286	finally
    //   57	176	286	finally
    //   189	251	286	finally
  }

  private String genMfpString()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      String str1 = getOS();
      if (!TextUtils.isEmpty(str1))
        localJSONObject.put("1", str1);
      String str2 = getImei();
      if (!TextUtils.isEmpty(str2))
        localJSONObject.put("2", str2);
      String str3 = getMeid();
      if (!TextUtils.isEmpty(str3))
        localJSONObject.put("3", str3);
      String str4 = getImsi();
      if (!TextUtils.isEmpty(str4))
        localJSONObject.put("4", str4);
      String str5 = getMac();
      if (!TextUtils.isEmpty(str5))
        localJSONObject.put("5", str5);
      String str6 = getIccid();
      if (!TextUtils.isEmpty(str6))
        localJSONObject.put("6", str6);
      String str7 = getSerialNo();
      if (!TextUtils.isEmpty(str7))
        localJSONObject.put("7", str7);
      String str8 = getAndroidId();
      if (!TextUtils.isEmpty(str8))
        localJSONObject.put("10", str8);
      String str9 = getCpu();
      if (!TextUtils.isEmpty(str9))
        localJSONObject.put("13", str9);
      String str10 = getModel();
      if (!TextUtils.isEmpty(str10))
        localJSONObject.put("14", str10);
      String str11 = getSdSize();
      if (!TextUtils.isEmpty(str11))
        localJSONObject.put("15", str11);
      String str12 = getResolution();
      if (!TextUtils.isEmpty(str12))
        localJSONObject.put("16", str12);
      String str13 = getSsid();
      if (!TextUtils.isEmpty(str13))
        localJSONObject.put("17", str13);
      String str14 = getDeviceName();
      if (!TextUtils.isEmpty(str14))
        localJSONObject.put("18", str14);
      String str15 = getConnectType();
      if (!TextUtils.isEmpty(str15))
        localJSONObject.put("19", str15);
      String str16 = localJSONObject.toString();
      return str16;
    }
    catch (JSONException localJSONException)
    {
    }
    return "";
  }

  private File getAidInfoFile(int paramInt)
  {
    return new File(this.mContext.getFilesDir(), "weibo_sdk_aid" + paramInt);
  }

  private String getAndroidId()
  {
    try
    {
      String str = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getConnectType()
  {
    String str = "none";
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo == null)
        break label131;
      if (localNetworkInfo.getType() == 0);
      switch (localNetworkInfo.getSubtype())
      {
      default:
        if (localNetworkInfo.getType() != 1)
          break label131;
        return "wifi";
      case 1:
      case 2:
      case 4:
      case 7:
      case 11:
      case 3:
      case 5:
      case 6:
      case 8:
      case 9:
      case 10:
      case 12:
      case 14:
      case 15:
      case 13:
      }
    }
    catch (Exception localException)
    {
      return str;
    }
    str = "none";
    label131: return str;
    return "2G";
    return "3G";
    return "4G";
  }

  private String getCpu()
  {
    try
    {
      String str = Build.CPU_ABI;
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getDeviceName()
  {
    try
    {
      String str = Build.BRAND;
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getIccid()
  {
    try
    {
      String str = ((TelephonyManager)this.mContext.getSystemService("phone")).getSimSerialNumber();
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getImei()
  {
    try
    {
      String str = ((TelephonyManager)this.mContext.getSystemService("phone")).getDeviceId();
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getImsi()
  {
    try
    {
      String str = ((TelephonyManager)this.mContext.getSystemService("phone")).getSubscriberId();
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static AidTask getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null)
        sInstance = new AidTask(paramContext);
      AidTask localAidTask = sInstance;
      return localAidTask;
    }
    finally
    {
    }
  }

  private String getMac()
  {
    try
    {
      WifiManager localWifiManager = (WifiManager)this.mContext.getSystemService("wifi");
      if (localWifiManager == null)
        return "";
      WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
      if (localWifiInfo != null)
        return localWifiInfo.getMacAddress();
      return "";
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getMeid()
  {
    try
    {
      String str = ((TelephonyManager)this.mContext.getSystemService("phone")).getDeviceId();
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getMfp()
  {
    String str1 = genMfpString();
    Object localObject = "";
    try
    {
      String str2 = new String(str1.getBytes(), "UTF-8");
      localObject = str2;
      label25: LogUtil.d("AidTask", "genMfpString() utf-8 string : " + (String)localObject);
      try
      {
        String str3 = encryptRsa((String)localObject, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHHM0Fi2Z6+QYKXqFUX2Cy6AaWq3cPi+GSn9oeAwQbPZR75JB7Netm0HtBVVbtPhzT7UO2p1JhFUKWqrqoYuAjkgMVPmA0sFrQohns5EE44Y86XQopD4ZO+dE5KjUZFE6vrPO3rWW3np2BqlgKpjnYZri6TJApmIpGcQg9/G/3zQIDAQAB");
        LogUtil.d("AidTask", "encryptRsa() string : " + str3);
        return str3;
      }
      catch (Exception localException)
      {
        LogUtil.e("AidTask", localException.getMessage());
        return "";
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      break label25;
    }
  }

  private String getModel()
  {
    try
    {
      String str = Build.MODEL;
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getOS()
  {
    try
    {
      String str = "Android " + Build.VERSION.RELEASE;
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private PublicKey getPublicKey(String paramString)
    throws Exception
  {
    X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(paramString.getBytes()));
    return KeyFactory.getInstance("RSA").generatePublic(localX509EncodedKeySpec);
  }

  private String getResolution()
  {
    try
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      String str = String.valueOf(localDisplayMetrics.widthPixels) + "*" + String.valueOf(localDisplayMetrics.heightPixels);
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getSdSize()
  {
    try
    {
      StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
      String str = Long.toString(localStatFs.getBlockSize() * localStatFs.getBlockCount());
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getSerialNo()
  {
    try
    {
      Class localClass = Class.forName("android.os.SystemProperties");
      String str = (String)localClass.getMethod("get", new Class[] { String.class, String.class }).invoke(localClass, new Object[] { "ro.serialno", "unknown" });
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String getSsid()
  {
    try
    {
      WifiInfo localWifiInfo = ((WifiManager)this.mContext.getSystemService("wifi")).getConnectionInfo();
      if (localWifiInfo != null)
      {
        String str = localWifiInfo.getSSID();
        return str;
      }
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  private String loadAidFromNet()
    throws WeiboException
  {
    String str1 = this.mContext.getPackageName();
    String str2 = Utility.getSign(this.mContext, str1);
    String str3 = getMfp();
    WeiboParameters localWeiboParameters = new WeiboParameters(this.mAppKey);
    localWeiboParameters.put("appkey", this.mAppKey);
    localWeiboParameters.put("mfp", str3);
    localWeiboParameters.put("packagename", str1);
    localWeiboParameters.put("key_hash", str2);
    try
    {
      String str4 = new AsyncWeiboRunner(this.mContext).request("http://api.weibo.com/oauth2/getaid.json", localWeiboParameters, "GET");
      LogUtil.d("AidTask", "loadAidFromNet response : " + str4);
      return str4;
    }
    catch (WeiboException localWeiboException)
    {
      LogUtil.d("AidTask", "loadAidFromNet WeiboException Msg : " + localWeiboException.getMessage());
      throw localWeiboException;
    }
  }

  // ERROR //
  private AidInfo loadAidInfoFromCache()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aconst_null
    //   3: astore_1
    //   4: new 546	java/io/FileInputStream
    //   7: dup
    //   8: aload_0
    //   9: iconst_1
    //   10: invokespecial 66	com/sina/weibo/sdk/utils/AidTask:getAidInfoFile	(I)Ljava/io/File;
    //   13: invokespecial 547	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   16: astore_2
    //   17: aload_2
    //   18: invokevirtual 550	java/io/FileInputStream:available	()I
    //   21: newarray byte
    //   23: astore 9
    //   25: aload_2
    //   26: aload 9
    //   28: invokevirtual 554	java/io/FileInputStream:read	([B)I
    //   31: pop
    //   32: new 99	java/lang/String
    //   35: dup
    //   36: aload 9
    //   38: invokespecial 555	java/lang/String:<init>	([B)V
    //   41: invokestatic 561	com/sina/weibo/sdk/utils/AidTask$AidInfo:parseJson	(Ljava/lang/String;)Lcom/sina/weibo/sdk/utils/AidTask$AidInfo;
    //   44: astore 11
    //   46: aload 11
    //   48: astore 4
    //   50: aload_2
    //   51: ifnull +7 -> 58
    //   54: aload_2
    //   55: invokevirtual 562	java/io/FileInputStream:close	()V
    //   58: aload_0
    //   59: monitorexit
    //   60: aload 4
    //   62: areturn
    //   63: astore 13
    //   65: aload_1
    //   66: ifnull +7 -> 73
    //   69: aload_1
    //   70: invokevirtual 562	java/io/FileInputStream:close	()V
    //   73: aconst_null
    //   74: astore 4
    //   76: goto -18 -> 58
    //   79: astore 7
    //   81: aload_1
    //   82: ifnull +7 -> 89
    //   85: aload_1
    //   86: invokevirtual 562	java/io/FileInputStream:close	()V
    //   89: aload 7
    //   91: athrow
    //   92: astore 6
    //   94: aload_0
    //   95: monitorexit
    //   96: aload 6
    //   98: athrow
    //   99: astore 12
    //   101: goto -43 -> 58
    //   104: astore 5
    //   106: goto -33 -> 73
    //   109: astore 8
    //   111: goto -22 -> 89
    //   114: astore 7
    //   116: aload_2
    //   117: astore_1
    //   118: goto -37 -> 81
    //   121: astore_3
    //   122: aload_2
    //   123: astore_1
    //   124: goto -59 -> 65
    //   127: astore 6
    //   129: goto -35 -> 94
    //
    // Exception table:
    //   from	to	target	type
    //   4	17	63	java/lang/Exception
    //   4	17	79	finally
    //   69	73	92	finally
    //   85	89	92	finally
    //   89	92	92	finally
    //   54	58	99	java/io/IOException
    //   69	73	104	java/io/IOException
    //   85	89	109	java/io/IOException
    //   17	46	114	finally
    //   17	46	121	java/lang/Exception
    //   54	58	127	finally
  }

  private int splite(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramInt1 >= paramArrayOfByte.length)
      return -1;
    return Math.min(paramArrayOfByte.length - paramInt1, paramInt2);
  }

  public void aidTaskInit()
  {
    aidTaskInit(this.mAppKey);
  }

  public void aidTaskInit(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    this.mAppKey = paramString;
    new Thread(new Runnable()
    {
      public void run()
      {
        if (!AidTask.this.mTaskLock.tryLock())
          return;
        if (!TextUtils.isEmpty(AidTask.this.loadAidFromCache()))
        {
          AidTask.this.mTaskLock.unlock();
          return;
        }
        int i = 0;
        while (true)
        {
          if (i >= 3);
          while (true)
          {
            AidTask.this.mTaskLock.unlock();
            return;
            try
            {
              String str = AidTask.this.loadAidFromNet();
              AidTask.AidInfo.parseJson(str);
              AidTask.this.cacheAidInfo(str);
            }
            catch (WeiboException localWeiboException)
            {
              LogUtil.e("AidTask", "AidTaskInit WeiboException Msg : " + localWeiboException.getMessage());
              i++;
            }
          }
        }
      }
    }).start();
  }

  public void getAidAsync(final Handler paramHandler)
  {
    if (TextUtils.isEmpty(this.mAppKey))
      return;
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          String str = AidTask.this.loadAidFromNet();
          AidTask.AidInfo localAidInfo = AidTask.AidInfo.parseJson(str);
          AidTask.this.cacheAidInfo(str);
          this.val$msg.what = 1001;
          this.val$msg.obj = localAidInfo;
          if (paramHandler != null)
            paramHandler.sendMessage(this.val$msg);
          return;
        }
        catch (WeiboException localWeiboException)
        {
          do
          {
            while (((localWeiboException.getCause() instanceof IOException)) || ((localWeiboException instanceof WeiboHttpException)))
            {
              this.val$msg.what = 1003;
              if (paramHandler != null)
              {
                paramHandler.sendMessage(this.val$msg);
                return;
              }
            }
            this.val$msg.what = 1002;
          }
          while (paramHandler == null);
          paramHandler.sendMessage(this.val$msg);
        }
      }
    }).start();
  }

  public AidInfo getAidSync()
    throws WeiboException
  {
    if (TextUtils.isEmpty(this.mAppKey))
      return null;
    String str = loadAidFromNet();
    AidInfo localAidInfo = AidInfo.parseJson(str);
    cacheAidInfo(str);
    return localAidInfo;
  }

  public ReentrantLock getTaskLock()
  {
    return this.mTaskLock;
  }

  public String loadAidFromCache()
  {
    try
    {
      AidInfo localAidInfo = loadAidInfoFromCache();
      String str1;
      if (localAidInfo != null)
        str1 = localAidInfo.getAid();
      for (String str2 = str1; ; str2 = "")
        return str2;
    }
    finally
    {
    }
  }

  public String loadSubCookieFromCache()
  {
    try
    {
      AidInfo localAidInfo = loadAidInfoFromCache();
      String str1;
      if (localAidInfo != null)
        str1 = localAidInfo.getSubCookie();
      for (String str2 = str1; ; str2 = "")
        return str2;
    }
    finally
    {
    }
  }

  public void setAppkey(String paramString)
  {
    this.mAppKey = paramString;
  }

  public static final class AidInfo
  {
    private String mAid;
    private String mSubCookie;

    public static AidInfo parseJson(String paramString)
      throws WeiboException
    {
      AidInfo localAidInfo = new AidInfo();
      JSONObject localJSONObject;
      try
      {
        localJSONObject = new JSONObject(paramString);
        if ((localJSONObject.has("error")) || (localJSONObject.has("error_code")))
        {
          LogUtil.d("AidTask", "loadAidFromNet has error !!!");
          throw new WeiboException("loadAidFromNet has error !!!");
        }
      }
      catch (JSONException localJSONException)
      {
        LogUtil.d("AidTask", "loadAidFromNet JSONException Msg : " + localJSONException.getMessage());
        throw new WeiboException("loadAidFromNet has error !!!");
      }
      localAidInfo.mAid = localJSONObject.optString("aid", "");
      localAidInfo.mSubCookie = localJSONObject.optString("sub", "");
      return localAidInfo;
    }

    public String getAid()
    {
      return this.mAid;
    }

    public String getSubCookie()
    {
      return this.mSubCookie;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.utils.AidTask
 * JD-Core Version:    0.6.2
 */