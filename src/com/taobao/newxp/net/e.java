package com.taobao.newxp.net;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.taobao.munion.base.Log;
import com.taobao.munion.base.volley.a.f;
import com.taobao.munion.base.volley.i;
import com.taobao.munion.base.volley.n;
import com.taobao.newxp.common.AlimmContext;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONObject;

public class e extends a
{
  private static String[] b = { ".taobao.com", ".tmall.com", ".etao.com", ".mmstat.com" };
  private static e c = new e();
  protected boolean a = false;

  private e()
  {
    super("http://log.mmstat.com", null);
  }

  public static e a()
  {
    if (c.a)
      return null;
    return c;
  }

  private void a(Context paramContext, CookieStore paramCookieStore)
  {
    CookieSyncManager.createInstance(paramContext);
    CookieManager localCookieManager = CookieManager.getInstance();
    if (localCookieManager != null)
    {
      localCookieManager.setAcceptCookie(true);
      Iterator localIterator = paramCookieStore.getCookies().iterator();
      while (localIterator.hasNext())
      {
        Cookie localCookie = (Cookie)localIterator.next();
        String str1 = localCookie.getDomain();
        String str2 = localCookie.getName();
        String str3 = localCookie.getPath();
        String str4 = localCookie.getValue();
        Date localDate = localCookie.getExpiryDate();
        String str5 = "";
        if (localDate != null)
          str5 = localCookie.getExpiryDate().toString();
        int i = localCookie.getVersion();
        if (str1.contains("mmstat.com"))
          for (String str6 : b)
          {
            BasicClientCookie localBasicClientCookie = new BasicClientCookie(str2, localCookie.getValue());
            localBasicClientCookie.setVersion(localCookie.getVersion());
            localBasicClientCookie.setDomain(str6);
            localBasicClientCookie.setPath(localCookie.getPath());
            localBasicClientCookie.setExpiryDate(localCookie.getExpiryDate());
            paramCookieStore.addCookie(localBasicClientCookie);
            StringBuilder localStringBuilder = new StringBuilder(str2);
            localStringBuilder.append("=").append(str4).append(";domain=").append(str6).append(";path=").append(str3).append(";expiry=").append(str5).append(";version=").append(i);
            localCookieManager.setCookie(str6, localStringBuilder.toString());
            Log.i("synchronize webview cookie:" + localStringBuilder.toString(), new Object[0]);
          }
      }
      CookieSyncManager.getInstance().sync();
    }
  }

  public static final boolean a(CookieStore paramCookieStore)
  {
    while (true)
    {
      try
      {
        CookieManager localCookieManager = CookieManager.getInstance();
        localCookieManager.setAcceptCookie(true);
        try
        {
          List localList = paramCookieStore.getCookies();
          String[] arrayOfString = b;
          int i = arrayOfString.length;
          int j = 0;
          if (j >= i)
            break label245;
          String str1 = arrayOfString[j];
          Iterator localIterator1 = localList.iterator();
          if (!localIterator1.hasNext())
            break label239;
          if (!str1.equals(((Cookie)localIterator1.next()).getDomain()))
            continue;
          k = 1;
          break label251;
          if (m != 0)
          {
            Iterator localIterator2 = paramCookieStore.getCookies().iterator();
            if (localIterator2.hasNext())
            {
              Cookie localCookie = (Cookie)localIterator2.next();
              if (!localCookie.getName().equals("cna"))
                continue;
              String str2 = localCookie.getDomain();
              String str3 = localCookie.getValue();
              boolean bool2 = localCookieManager.getCookie(str2).contains(str3);
              if (bool2)
                continue;
              n = 0;
              if ((m != 0) && (n != 0))
              {
                bool1 = true;
                return bool1;
                j++;
                continue;
              }
              bool1 = false;
              continue;
            }
          }
        }
        catch (Exception localException)
        {
          boolean bool1 = false;
          continue;
        }
      }
      finally
      {
      }
      int n = 1;
      continue;
      label239: int k = 0;
      break label251;
      label245: int m = 1;
      continue;
      label251: if (k == 0)
        m = 0;
    }
  }

  public static final boolean z()
  {
    try
    {
      boolean bool = a(AlimmContext.getAliContext().getCookieStore());
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  protected n<JSONObject> a(i parami)
  {
    AlimmContext localAlimmContext = AlimmContext.getAliContext();
    CookieStore localCookieStore = localAlimmContext.getCookieStore();
    a(localAlimmContext.getAppContext(), localCookieStore);
    this.a = false;
    return n.a(new JSONObject(), f.a(parami));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.e
 * JD-Core Version:    0.6.2
 */