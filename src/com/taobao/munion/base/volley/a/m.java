package com.taobao.munion.base.volley.a;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import java.io.File;

public class m
{
  private static final String a = "volley";

  public static com.taobao.munion.base.volley.m a(Context paramContext)
  {
    return a(paramContext, null);
  }

  public static com.taobao.munion.base.volley.m a(Context paramContext, g paramg)
  {
    File localFile = new File(paramContext.getCacheDir(), "volley");
    Object localObject = "volley/0";
    try
    {
      String str1 = paramContext.getPackageName();
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(str1, 0);
      String str2 = str1 + "/" + localPackageInfo.versionCode;
      localObject = str2;
      label68: if (paramg == null)
        if (Build.VERSION.SDK_INT < 9)
          break label125;
      label125: for (paramg = new h(); ; paramg = new e(AndroidHttpClient.newInstance((String)localObject)))
      {
        a locala = new a(paramg);
        com.taobao.munion.base.volley.m localm = new com.taobao.munion.base.volley.m(new d(localFile), locala);
        localm.a();
        return localm;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label68;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.volley.a.m
 * JD-Core Version:    0.6.2
 */