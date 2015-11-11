package com.tencent.weibo.sdk.android.component.sso;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import com.tencent.weibo.sdk.android.component.sso.tools.Base64;
import com.tencent.weibo.sdk.android.component.sso.tools.Cryptor;
import com.tencent.weibo.sdk.android.component.sso.tools.MD5Tools;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class AuthHelper
{
  static final String ENCRYPT_KEY = "&-*)Wb5_U,[^!9'+";
  static final int ERROR_WEIBO_INSTALL_NEEDED = -2;
  static final int ERROR_WEIBO_UPGRADE_NEEDED = -1;
  static final byte SDK_VERSION = 1;
  static final int SUPPORT_WEIBO_MIN_VERSION = 44;
  static final String WEIBO_AUTH_URL = "TencentAuth://weibo";
  static final String WEIBO_PACKAGE = "com.tencent.WBlog";
  static final int WEIBO_VALIDATE_OK;
  protected static String appSecret;
  protected static long appid;
  protected static OnAuthListener listener;
  private static AuthReceiver mReceiver = new AuthReceiver();

  public static final boolean auth(Context paramContext, String paramString)
  {
    int i = validateWeiboApp(paramContext);
    long l1;
    long l2;
    String str1;
    byte[] arrayOfByte2;
    String str2;
    PackageManager localPackageManager;
    Object localObject;
    if (i == 0)
    {
      l1 = System.currentTimeMillis() / 1000L;
      l2 = Math.abs(new Random().nextInt());
      str1 = getApkSignature(paramContext);
      byte[] arrayOfByte1 = generateSignature(appid, appSecret, l1, l2);
      if (arrayOfByte1 == null)
      {
        if (listener != null)
          listener.onAuthFail(-1, "");
        return false;
      }
      arrayOfByte2 = encypt(arrayOfByte1);
      str2 = paramContext.getPackageName();
      localPackageManager = paramContext.getPackageManager();
      localObject = "";
    }
    try
    {
      String str3 = localPackageManager.getApplicationLabel(localPackageManager.getApplicationInfo(str2, 0)).toString();
      localObject = str3;
      label124: Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("TencentAuth://weibo"));
      localIntent.putExtra("com.tencent.sso.APP_ID", appid);
      localIntent.putExtra("com.tencent.sso.TIMESTAMP", l1);
      localIntent.putExtra("com.tencent.sso.NONCE", l2);
      localIntent.putExtra("com.tencent.sso.SDK_VERSION", (byte)1);
      localIntent.putExtra("com.tencent.sso.PACKAGE_NAME", str2);
      localIntent.putExtra("com.tencent.sso.ICON_MD5", str1);
      localIntent.putExtra("com.tencent.sso.APP_NAME", (String)localObject);
      localIntent.putExtra("com.tencent.sso.SIGNATURE", arrayOfByte2);
      localIntent.putExtra("com.tencent.sso.RESERVER", paramString);
      paramContext.startActivity(localIntent);
      return true;
      if (i == -1)
      {
        if (listener != null)
          listener.onWeiboVersionMisMatch();
        return false;
      }
      if (i == -2)
      {
        if (listener != null)
          listener.onWeiBoNotInstalled();
        return false;
      }
      return false;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label124;
    }
  }

  private static byte[] encypt(byte[] paramArrayOfByte)
  {
    return new Cryptor().encrypt(paramArrayOfByte, "&-*)Wb5_U,[^!9'+".getBytes());
  }

  private static byte[] generateSignature(long paramLong1, String paramString, long paramLong2, long paramLong3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramLong1);
    localStringBuffer.append(paramLong2);
    localStringBuffer.append(paramLong3);
    localStringBuffer.append(1);
    try
    {
      Mac localMac = Mac.getInstance("HmacSHA1");
      localMac.init(new SecretKeySpec(paramString.getBytes("UTF-8"), localMac.getAlgorithm()));
      byte[] arrayOfByte2 = localMac.doFinal(localStringBuffer.toString().getBytes("UTF-8"));
      arrayOfByte1 = arrayOfByte2;
      return Base64.encode(arrayOfByte1).getBytes();
    }
    catch (Exception localException)
    {
      while (true)
        byte[] arrayOfByte1 = null;
    }
  }

  private static String getApkSignature(Context paramContext)
  {
    try
    {
      Signature localSignature = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 64).signatures[0];
      X509Certificate localX509Certificate = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(localSignature.toByteArray()));
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(localX509Certificate.getPublicKey().toString());
      localStringBuffer.append(localX509Certificate.getSerialNumber().toString());
      String str = MD5Tools.toMD5(localStringBuffer.toString());
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
      return "";
    }
    catch (CertificateException localCertificateException)
    {
      while (true)
        localCertificateException.printStackTrace();
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public static final void register(Context paramContext, long paramLong, String paramString, OnAuthListener paramOnAuthListener)
  {
    appid = paramLong;
    appSecret = paramString;
    listener = paramOnAuthListener;
    IntentFilter localIntentFilter = new IntentFilter("com.tencent.sso.AUTH");
    localIntentFilter.addCategory("android.intent.category.DEFAULT");
    paramContext.registerReceiver(mReceiver, localIntentFilter);
  }

  public static final void unregister(Context paramContext)
  {
    paramContext.unregisterReceiver(mReceiver);
  }

  private static int validateWeiboApp(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      PackageInfo localPackageInfo = localPackageManager.getPackageInfo("com.tencent.WBlog", 16);
      if ((localPackageInfo != null) && (localPackageInfo.versionCode >= 44))
      {
        int i = localPackageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("TencentAuth://weibo")), 65536).size();
        if (i > 0)
          return 0;
      }
      return -1;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return -2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.component.sso.AuthHelper
 * JD-Core Version:    0.6.2
 */