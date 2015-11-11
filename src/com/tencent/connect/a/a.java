package com.tencent.connect.a;

import android.content.Context;
import android.util.Log;
import com.tencent.connect.auth.QQToken;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatReportStrategy;
import com.tencent.stat.StatService;
import com.tencent.utils.OpenConfig;

public class a
{
  public static void a(Context paramContext, QQToken paramQQToken, String paramString, String[] paramArrayOfString)
  {
    b(paramContext, paramQQToken);
    StatService.trackCustomEvent(paramContext, paramString, paramArrayOfString);
  }

  public static boolean a(Context paramContext, QQToken paramQQToken)
  {
    return OpenConfig.getInstance(paramContext, paramQQToken.getAppId()).getBoolean("Common_ta_enable");
  }

  public static void b(Context paramContext, QQToken paramQQToken)
  {
    if (a(paramContext, paramQQToken))
    {
      StatConfig.setEnableStatService(true);
      return;
    }
    StatConfig.setEnableStatService(false);
  }

  public static void c(Context paramContext, QQToken paramQQToken)
  {
    b(paramContext, paramQQToken);
    String str1 = paramQQToken.getAppId();
    String str2 = "Aqc" + str1;
    StatConfig.setAutoExceptionCaught(false);
    StatConfig.setEnableSmartReporting(true);
    StatConfig.setSendPeriodMinutes(1440);
    StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
    StatConfig.setStatReportUrl("http://cgi.connect.qq.com/qqconnectutil/sdk");
    try
    {
      StatService.startStatService(paramContext, str2, "1.0.0");
      return;
    }
    catch (MtaSDkException localMtaSDkException)
    {
      Log.e("DEBUG", "MTA init Failed.");
    }
  }

  public static void d(Context paramContext, QQToken paramQQToken)
  {
    b(paramContext, paramQQToken);
    if (paramQQToken.getOpenId() != null)
      StatService.reportQQ(paramContext, paramQQToken.getOpenId());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.a.a
 * JD-Core Version:    0.6.2
 */