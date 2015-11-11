package com.tencent.connect.auth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.webkit.CookieSyncManager;
import android.widget.Toast;
import com.tencent.b.a.g;
import com.tencent.b.b.b;
import com.tencent.connect.a.a;
import com.tencent.tauth.IUiListener;
import java.util.HashMap;

public class QQAuth
{
  private static HashMap<String, QQAuth> c = null;
  private AuthAgent a;
  private QQToken b;

  private QQAuth(String paramString, Context paramContext)
  {
    g.a("openSDK_LOG", "new Tencent() --start");
    this.b = new QQToken(paramString);
    this.a = new AuthAgent(paramContext, this.b);
    a.c(paramContext, this.b);
    g.a("openSDK_LOG", "new Tencent() --end");
  }

  public static QQAuth createInstance(String paramString, Context paramContext)
  {
    b.a(paramContext.getApplicationContext());
    g.a("openSDK_LOG", "createInstance() --start");
    if (c == null)
      c = new HashMap();
    try
    {
      do
      {
        PackageManager localPackageManager = paramContext.getPackageManager();
        localPackageManager.getActivityInfo(new ComponentName(paramContext.getPackageName(), "com.tencent.tauth.AuthActivity"), 0);
        localPackageManager.getActivityInfo(new ComponentName(paramContext.getPackageName(), "com.tencent.connect.common.AssistActivity"), 0);
        QQAuth localQQAuth = new QQAuth(paramString, paramContext);
        c.put(paramString, localQQAuth);
        g.a("openSDK_LOG", "createInstance()  --end");
        return localQQAuth;
      }
      while (!c.containsKey(paramString));
      g.a("openSDK_LOG", "createInstance() ,sessionMap.containsKey --end");
      return (QQAuth)c.get(paramString);
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      g.a("openSDK_LOG", "createInstance() error --end", localNameNotFoundException);
      Toast.makeText(paramContext.getApplicationContext(), "请参照文档在Androidmanifest.xml加上AuthActivity和AssitActivity的定义 ", 1).show();
    }
    return null;
  }

  public QQToken getQQToken()
  {
    return this.b;
  }

  public boolean isSessionValid()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("isSessionValid(), result = ");
    if (this.b.isSessionValid());
    for (String str = "true"; ; str = "false")
    {
      g.a("openSDK_LOG", str + "");
      return this.b.isSessionValid();
    }
  }

  public int login(Activity paramActivity, String paramString, IUiListener paramIUiListener)
  {
    g.a("openSDK_LOG", "login()");
    com.tencent.connect.common.BaseApi.isOEM = false;
    return this.a.doLogin(paramActivity, paramString, paramIUiListener);
  }

  public int loginWithOEM(Activity paramActivity, String paramString1, IUiListener paramIUiListener, String paramString2, String paramString3, String paramString4)
  {
    g.b("openSDK_LOG", "loginWithOEM");
    com.tencent.connect.common.BaseApi.isOEM = true;
    if (paramString2.equals(""))
      paramString2 = "null";
    if (paramString3.equals(""))
      paramString3 = "null";
    if (paramString4.equals(""))
      paramString4 = "null";
    com.tencent.connect.common.BaseApi.installChannel = paramString3;
    com.tencent.connect.common.BaseApi.registerChannel = paramString2;
    com.tencent.connect.common.BaseApi.businessId = paramString4;
    return this.a.doLogin(paramActivity, paramString1, paramIUiListener);
  }

  public void logout(Context paramContext)
  {
    g.a("openSDK_LOG", "logout() --start");
    CookieSyncManager.createInstance(paramContext);
    setAccessToken(null, null);
    setOpenId(paramContext, null);
    g.a("openSDK_LOG", "logout() --end");
  }

  public boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    g.c("openSDK_LOG", "onActivityResult() ,resultCode = " + paramInt2 + "");
    this.a.onActivityResult(paramInt1, paramInt2, paramIntent);
    return true;
  }

  public int reAuth(Activity paramActivity, String paramString, IUiListener paramIUiListener)
  {
    g.a("openSDK_LOG", "reAuth()");
    return this.a.doLogin(paramActivity, paramString, paramIUiListener, true, true);
  }

  public void setAccessToken(String paramString1, String paramString2)
  {
    g.a("openSDK_LOG", "setAccessToken(), validTimeInSecond = " + paramString2 + "");
    this.b.setAccessToken(paramString1, paramString2);
  }

  public void setOpenId(Context paramContext, String paramString)
  {
    g.a("openSDK_LOG", "setOpenId() --start");
    this.b.setOpenId(paramString);
    a.d(paramContext, this.b);
    g.a("openSDK_LOG", "setOpenId() --end");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.auth.QQAuth
 * JD-Core Version:    0.6.2
 */