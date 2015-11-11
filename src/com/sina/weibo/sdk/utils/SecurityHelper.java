package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import com.sina.weibo.sdk.ApiUtils;
import com.sina.weibo.sdk.WeiboAppManager.WeiboInfo;

public class SecurityHelper
{
  public static boolean checkResponseAppLegal(Context paramContext, WeiboAppManager.WeiboInfo paramWeiboInfo, Intent paramIntent)
  {
    if ((paramWeiboInfo != null) && (paramWeiboInfo.getSupportApi() <= 10352));
    while (true)
    {
      return true;
      if (paramWeiboInfo != null)
      {
        if (paramIntent != null);
        for (String str = paramIntent.getStringExtra("_weibo_appPackage"); (str == null) || (paramIntent.getStringExtra("_weibo_transaction") == null) || (!ApiUtils.validateWeiboSign(paramContext, str)); str = null)
          return false;
      }
    }
  }

  public static boolean containSign(Signature[] paramArrayOfSignature, String paramString)
  {
    if ((paramArrayOfSignature == null) || (paramString == null));
    while (true)
    {
      return false;
      int i = paramArrayOfSignature.length;
      for (int j = 0; j < i; j++)
        if (paramString.equals(MD5.hexdigest(paramArrayOfSignature[j].toByteArray())))
          return true;
    }
  }

  public static boolean validateAppSignatureForIntent(Context paramContext, Intent paramIntent)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    if (localPackageManager == null);
    ResolveInfo localResolveInfo;
    do
    {
      return false;
      localResolveInfo = localPackageManager.resolveActivity(paramIntent, 0);
    }
    while (localResolveInfo == null);
    String str = localResolveInfo.activityInfo.packageName;
    try
    {
      boolean bool = containSign(localPackageManager.getPackageInfo(str, 64).signatures, "18da2bf10352443a00a5e046d9fca6bd");
      return bool;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
      return false;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.utils.SecurityHelper
 * JD-Core Version:    0.6.2
 */