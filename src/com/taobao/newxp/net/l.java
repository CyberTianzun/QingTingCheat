package com.taobao.newxp.net;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import com.taobao.munion.base.volley.a.a;
import com.taobao.munion.base.volley.a.d;
import com.taobao.munion.base.volley.a.e;
import com.taobao.munion.base.volley.a.g;
import com.taobao.munion.base.volley.a.h;
import com.taobao.munion.base.volley.m;
import java.io.File;

public class l
  implements c
{
  private m b;

  public <T> com.taobao.munion.base.volley.l<T> a(com.taobao.munion.base.volley.l<T> paraml)
  {
    if (this.b != null)
      return this.b.a(paraml);
    throw new RuntimeException("MunionVolley is not initized..");
  }

  public c a(Context paramContext)
  {
    File localFile = new File(paramContext.getCacheDir(), "taobao_munion");
    Object localObject1 = "volley/0";
    try
    {
      String str1 = paramContext.getPackageName();
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(str1, 0);
      String str2 = str1 + "/" + localPackageInfo.versionCode;
      localObject1 = str2;
      label68: if (Build.VERSION.SDK_INT >= 9);
      for (Object localObject2 = new h(); ; localObject2 = new e(AndroidHttpClient.newInstance((String)localObject1)))
      {
        a locala = new a((g)localObject2);
        this.b = new m(new d(localFile), locala);
        this.b.a();
        return this;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label68;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.l
 * JD-Core Version:    0.6.2
 */