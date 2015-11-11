package com.android.volley.toolbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import com.android.volley.RequestQueue;
import java.io.File;

public class Volley
{
  private static final String DEFAULT_CACHE_DIR = "volley";

  public static RequestQueue newRequestQueue(Context paramContext, int paramInt)
  {
    return newRequestQueue(paramContext, null, paramInt);
  }

  public static RequestQueue newRequestQueue(Context paramContext, HttpStack paramHttpStack, int paramInt)
  {
    File localFile = new File(paramContext.getCacheDir(), "volley");
    Object localObject = "volley/0";
    try
    {
      String str1 = paramContext.getPackageName();
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(str1, 0);
      String str2 = str1 + "/" + localPackageInfo.versionCode;
      localObject = str2;
      label70: if (paramHttpStack == null)
        if (Build.VERSION.SDK_INT < 9)
          break label128;
      label128: for (paramHttpStack = new HurlStack(); ; paramHttpStack = new HttpClientStack(AndroidHttpClient.newInstance((String)localObject)))
      {
        BasicNetwork localBasicNetwork = new BasicNetwork(paramHttpStack);
        RequestQueue localRequestQueue = new RequestQueue(new DiskBasedCache(localFile), localBasicNetwork, paramInt);
        localRequestQueue.start();
        return localRequestQueue;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label70;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.Volley
 * JD-Core Version:    0.6.2
 */