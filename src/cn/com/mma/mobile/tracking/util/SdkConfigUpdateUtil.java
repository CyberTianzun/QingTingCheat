package cn.com.mma.mobile.tracking.util;

import android.content.Context;
import android.content.res.AssetManager;
import cn.com.mma.mobile.tracking.bean.OfflineCache;
import cn.com.mma.mobile.tracking.bean.SDK;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SdkConfigUpdateUtil
{
  private static SDK sdk = null;

  private static boolean JudgeUpdateAccordingDate(Context paramContext)
  {
    long l1 = 3L * 86400000L;
    long l2 = System.currentTimeMillis();
    long l3 = SharedPreferencedUtil.getLong(paramContext, "cn.com.mma.mobile.tracking.other", "updateTime");
    Logger.d("mma_config lastUpdateTimeStamp:" + l3);
    StringBuilder localStringBuilder1 = new StringBuilder("mma_config wifi:").append(CommonUtil.isWifiConnected(paramContext)).append(" | ");
    boolean bool1;
    boolean bool2;
    label127: int i;
    label161: int j;
    label180: boolean bool3;
    if (l2 - l3 >= 86400000L)
    {
      bool1 = true;
      Logger.d(bool1);
      StringBuilder localStringBuilder2 = new StringBuilder("mma_config mobile:").append(CommonUtil.isMobileConnected(paramContext)).append(" | ");
      if (l2 - l3 < l1)
        break label231;
      bool2 = true;
      Logger.d(bool2);
      if ((!CommonUtil.isWifiConnected(paramContext)) || (l2 - l3 < 86400000L))
        break label237;
      i = 1;
      if ((!CommonUtil.isMobileConnected(paramContext)) || (l2 - l3 < l1))
        break label243;
      j = 1;
      if ((i == 0) && (j == 0))
        break label249;
      bool3 = true;
      SharedPreferencedUtil.putLong(paramContext, "cn.com.mma.mobile.tracking.other", "updateTime", l2);
    }
    while (true)
    {
      Logger.d("mma_config File need Update：" + bool3);
      return bool3;
      bool1 = false;
      break;
      label231: bool2 = false;
      break label127;
      label237: i = 0;
      break label161;
      label243: j = 0;
      break label180;
      label249: bool3 = false;
    }
  }

  public static SDK dealUpdateConfig(Context paramContext, String paramString)
  {
    String str = getConfigFromNetWork(paramString);
    SDK localSDK = null;
    if (str != null);
    try
    {
      localSDK = XmlUtil.doParser(new ByteArrayInputStream(str.getBytes("UTF-8")));
      if ((localSDK != null) && (localSDK.companies != null) && (localSDK.companies.size() > 0))
      {
        SharedPreferencedUtil.putString(paramContext, "cn.com.mma.mobile.tracking.sdkconfig", "trackingConfig", str);
        Logger.d("mma_网络更新sdkconfig.xml成功");
      }
      return localSDK;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
    return localSDK;
  }

  private static String getConfigFromNetWork(String paramString)
  {
    if (paramString == null)
      return null;
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setConnectTimeout(10000);
      localHttpURLConnection.connect();
      InputStream localInputStream = localHttpURLConnection.getInputStream();
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
      StringBuffer localStringBuffer = new StringBuffer();
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
        {
          localInputStream.close();
          return localStringBuffer.toString();
        }
        localStringBuffer.append(str);
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  private static SDK getNewestSDK(Context paramContext, String paramString)
  {
    SDK localSDK;
    if (JudgeUpdateAccordingDate(paramContext))
    {
      localSDK = dealUpdateConfig(paramContext, paramString);
      if (localSDK == null)
        localSDK = getSDKFromPreferences(paramContext);
    }
    do
    {
      return localSDK;
      localSDK = getSDKFromPreferences(paramContext);
    }
    while (localSDK != null);
    return dealUpdateConfig(paramContext, paramString);
  }

  public static SDK getSDKFromPreferences(Context paramContext)
  {
    try
    {
      String str = SharedPreferencedUtil.getString(paramContext, "cn.com.mma.mobile.tracking.sdkconfig", "trackingConfig");
      Object localObject = null;
      if (str != null)
      {
        SDK localSDK = XmlUtil.doParser(new ByteArrayInputStream(str.getBytes()));
        localObject = localSDK;
      }
      return localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static SDK getSdk(Context paramContext)
  {
    if ((sdk == null) || (sdk.companies == null));
    try
    {
      sdk = XmlUtil.doParser(paramContext.getAssets().open("sdkconfig.xml"));
      setSdk(sdk);
      return sdk;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public static void initSdkConfigResult(Context paramContext, String paramString)
  {
    sdk = getNewestSDK(paramContext, paramString);
  }

  private static void setSdk(SDK paramSDK)
  {
    Logger.d("mma_setSdk");
    if (paramSDK != null);
    try
    {
      if ((paramSDK.offlineCache.length != null) && (!"".equals(paramSDK.offlineCache.length)))
        cn.com.mma.mobile.tracking.api.Global.OFFLINECACHE_LENGTH = Integer.parseInt(paramSDK.offlineCache.length);
      if ((paramSDK.offlineCache.queueExpirationSecs != null) && (!"".equals(paramSDK.offlineCache.queueExpirationSecs)))
        cn.com.mma.mobile.tracking.api.Global.OFFLINECACHE_QUEUEEXPIRATIONSECS = Integer.parseInt(paramSDK.offlineCache.queueExpirationSecs);
      if ((paramSDK.offlineCache.timeout != null) && (!"".equals(paramSDK.offlineCache.timeout)))
        cn.com.mma.mobile.tracking.api.Global.OFFLINECACHE_TIMEOUT = Integer.parseInt(paramSDK.offlineCache.timeout);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.mma.mobile.tracking.util.SdkConfigUpdateUtil
 * JD-Core Version:    0.6.2
 */