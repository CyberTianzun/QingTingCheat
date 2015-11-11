package com.taobao.munion.base;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.taobao.newxp.common.AlimmContext;

public class d
{
  public static boolean a = false;

  public static void a(Context paramContext)
  {
    if (a);
    CookieManager localCookieManager;
    do
    {
      return;
      a = true;
      CookieSyncManager.createInstance(paramContext);
      localCookieManager = CookieManager.getInstance();
    }
    while (localCookieManager == null);
    localCookieManager.setAcceptCookie(true);
    try
    {
      String str1 = AlimmContext.getAliContext().getAppUtils().C();
      long l = System.currentTimeMillis();
      StringBuilder localStringBuilder1 = new StringBuilder("utsid=");
      localStringBuilder1.append(str1);
      localStringBuilder1.append("_");
      localStringBuilder1.append("21570058");
      localStringBuilder1.append("_");
      localStringBuilder1.append(l);
      localCookieManager.setCookie("taobao.com", localStringBuilder1.toString() + ";domain=taobao.com;path=/");
      localCookieManager.setCookie("etao.com", localStringBuilder1.toString() + ";domain=etao.com;path=/");
      localCookieManager.setCookie("tmall.com", localStringBuilder1.toString() + ";domain=tmall.com;path=/");
      localCookieManager.setCookie("mmstat.com", localStringBuilder1.toString() + ";domain=mmstat.com;path=/");
      a locala = AlimmContext.getAliContext().getAppUtils();
      String str2 = locala.h();
      String str3 = locala.g();
      StringBuilder localStringBuilder2 = new StringBuilder("utkey=umengappkey=");
      localStringBuilder2.append(str3);
      localStringBuilder2.append("-");
      localStringBuilder2.append(str2);
      localCookieManager.setCookie("taobao.com", localStringBuilder2.toString() + ";domain=taobao.com;path=/");
      localCookieManager.setCookie("etao.com", localStringBuilder2.toString() + ";domain=etao.com;path=/");
      localCookieManager.setCookie("tmall.com", localStringBuilder2.toString() + ";domain=tmall.com;path=/");
      localCookieManager.setCookie("mmstat.com", localStringBuilder2.toString() + ";domain=mmstat.com;path=/");
      CookieSyncManager.getInstance().sync();
      return;
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.d
 * JD-Core Version:    0.6.2
 */