package com.sina.weibo.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.MD5;

public class ApiUtils
{
  public static final int BUILD_INT = 10350;
  public static final int BUILD_INT_440 = 10355;
  public static final int BUILD_INT_VER_2_2 = 10351;
  public static final int BUILD_INT_VER_2_3 = 10352;
  public static final int BUILD_INT_VER_2_5 = 10353;
  private static final String TAG = ApiUtils.class.getName();

  private static boolean containSign(Signature[] paramArrayOfSignature, String paramString)
  {
    if ((paramArrayOfSignature == null) || (paramString == null));
    while (true)
    {
      return false;
      int i = paramArrayOfSignature.length;
      for (int j = 0; j < i; j++)
        if (paramString.equals(MD5.hexdigest(paramArrayOfSignature[j].toByteArray())))
        {
          LogUtil.d(TAG, "check pass");
          return true;
        }
    }
  }

  public static boolean validateWeiboSign(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return false;
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramString, 64);
      return containSign(localPackageInfo.signatures, "18da2bf10352443a00a5e046d9fca6bd");
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.ApiUtils
 * JD-Core Version:    0.6.2
 */