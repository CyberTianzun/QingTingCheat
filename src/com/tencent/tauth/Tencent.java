package com.tencent.tauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.tencent.b.a.g;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.avatar.QQAvatar;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.SocialApi;
import com.tencent.open.TaskGuide;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.utils.SystemUtils;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class Tencent
{
  private Context mContext;
  private LocationApi mLocationApi;
  private QQAuth mQQAuth;

  private Tencent(String paramString, Context paramContext)
  {
    this.mContext = paramContext;
    this.mQQAuth = QQAuth.createInstance(paramString, paramContext);
  }

  // ERROR //
  private static boolean checkManifestConfig(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: new 31	android/content/ComponentName
    //   3: dup
    //   4: aload_0
    //   5: invokevirtual 37	android/content/Context:getPackageName	()Ljava/lang/String;
    //   8: ldc 39
    //   10: invokespecial 42	android/content/ComponentName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   13: astore_2
    //   14: aload_0
    //   15: invokevirtual 46	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   18: aload_2
    //   19: iconst_0
    //   20: invokevirtual 52	android/content/pm/PackageManager:getActivityInfo	(Landroid/content/ComponentName;I)Landroid/content/pm/ActivityInfo;
    //   23: pop
    //   24: new 31	android/content/ComponentName
    //   27: dup
    //   28: aload_0
    //   29: invokevirtual 37	android/content/Context:getPackageName	()Ljava/lang/String;
    //   32: ldc 54
    //   34: invokespecial 42	android/content/ComponentName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   37: astore 8
    //   39: aload_0
    //   40: invokevirtual 46	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   43: aload 8
    //   45: iconst_0
    //   46: invokevirtual 52	android/content/pm/PackageManager:getActivityInfo	(Landroid/content/ComponentName;I)Landroid/content/pm/ActivityInfo;
    //   49: pop
    //   50: iconst_1
    //   51: ireturn
    //   52: astore_3
    //   53: new 56	java/lang/StringBuilder
    //   56: dup
    //   57: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   60: ldc 59
    //   62: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: aload_1
    //   66: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: ldc 65
    //   71: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: invokevirtual 68	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   77: astore 4
    //   79: new 56	java/lang/StringBuilder
    //   82: dup
    //   83: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   86: aload 4
    //   88: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: ldc 70
    //   93: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: aload_1
    //   97: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: ldc 72
    //   102: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: ldc 74
    //   107: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: ldc 76
    //   112: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: invokevirtual 68	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   118: astore 5
    //   120: invokestatic 82	com/tencent/b/a/g:a	()Lcom/tencent/b/a/g;
    //   123: pop
    //   124: ldc 84
    //   126: aload 5
    //   128: invokestatic 87	com/tencent/b/a/g:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   131: iconst_0
    //   132: ireturn
    //   133: astore 9
    //   135: new 56	java/lang/StringBuilder
    //   138: dup
    //   139: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   142: ldc 89
    //   144: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: ldc 91
    //   149: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: invokevirtual 68	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: astore 10
    //   157: invokestatic 82	com/tencent/b/a/g:a	()Lcom/tencent/b/a/g;
    //   160: pop
    //   161: ldc 93
    //   163: aload 10
    //   165: invokestatic 87	com/tencent/b/a/g:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   168: iconst_0
    //   169: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   0	24	52	android/content/pm/PackageManager$NameNotFoundException
    //   24	50	133	android/content/pm/PackageManager$NameNotFoundException
  }

  public static Tencent createInstance(String paramString, Context paramContext)
  {
    Tencent localTencent = new Tencent(paramString, paramContext);
    if (!checkManifestConfig(paramContext, paramString))
      return null;
    g.a("openSDK_LOG", "createInstance()  --end");
    return localTencent;
  }

  public int ask(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQAuth.getQQToken()).ask(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }

  public int brag(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQAuth.getQQToken()).brag(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }

  public int challenge(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQAuth.getQQToken()).challenge(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }

  public int deleteLocation(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    if (this.mLocationApi == null)
      this.mLocationApi = new LocationApi(paramActivity, this.mQQAuth.getQQToken());
    this.mLocationApi.deleteLocation(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }

  public String getAccessToken()
  {
    return this.mQQAuth.getQQToken().getAccessToken();
  }

  public String getAppId()
  {
    return this.mQQAuth.getQQToken().getAppId();
  }

  public long getExpiresIn()
  {
    return this.mQQAuth.getQQToken().getExpireTimeInSecond();
  }

  public String getOpenId()
  {
    return this.mQQAuth.getQQToken().getOpenId();
  }

  public QQToken getQQToken()
  {
    return this.mQQAuth.getQQToken();
  }

  public int gift(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQAuth.getQQToken()).gift(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }

  public void grade(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQAuth.getQQToken()).grade(paramActivity, paramBundle, paramIUiListener);
  }

  public int invite(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQAuth.getQQToken()).invite(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }

  public boolean isReady()
  {
    return (isSessionValid()) && (getOpenId() != null);
  }

  public boolean isSessionValid()
  {
    return this.mQQAuth.isSessionValid();
  }

  public boolean isSupportSSOLogin(Activity paramActivity)
  {
    if (SystemUtils.getAppVersionName(paramActivity, "com.tencent.mobileqq") == null)
    {
      Toast.makeText(paramActivity, "没有安装手Q", 0).show();
      return false;
    }
    if (SystemUtils.checkMobileQQ(paramActivity))
    {
      Toast.makeText(paramActivity, "已安装的手Q版本支持SSO登陆", 0).show();
      return true;
    }
    Toast.makeText(paramActivity, "已安装的手Q版本不支持SSO登陆", 0).show();
    return false;
  }

  public int login(Activity paramActivity, String paramString, IUiListener paramIUiListener)
  {
    return this.mQQAuth.login(paramActivity, paramString, paramIUiListener);
  }

  public int loginWithOEM(Activity paramActivity, String paramString1, IUiListener paramIUiListener, String paramString2, String paramString3, String paramString4)
  {
    return this.mQQAuth.loginWithOEM(paramActivity, paramString1, paramIUiListener, paramString2, paramString3, paramString4);
  }

  public void logout(Context paramContext)
  {
    this.mQQAuth.getQQToken().setAccessToken(null, "0");
    this.mQQAuth.getQQToken().setOpenId(null);
  }

  public boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    return false;
  }

  public int reAuth(Activity paramActivity, String paramString, IUiListener paramIUiListener)
  {
    return this.mQQAuth.reAuth(paramActivity, paramString, paramIUiListener);
  }

  public int reactive(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQAuth.getQQToken()).reactive(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }

  public JSONObject request(String paramString1, Bundle paramBundle, String paramString2)
    throws IOException, JSONException, HttpUtils.NetworkUnavailableException, HttpUtils.HttpStatusException
  {
    return HttpUtils.request(this.mQQAuth.getQQToken(), this.mContext, paramString1, paramBundle, paramString2);
  }

  public void requestAsync(String paramString1, Bundle paramBundle, String paramString2, IRequestListener paramIRequestListener, Object paramObject)
  {
    HttpUtils.requestAsync(this.mQQAuth.getQQToken(), this.mContext, paramString1, paramBundle, paramString2, paramIRequestListener);
  }

  public int searchNearby(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    if (this.mLocationApi == null)
      this.mLocationApi = new LocationApi(paramActivity, this.mQQAuth.getQQToken());
    this.mLocationApi.searchNearby(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }

  public void setAccessToken(String paramString1, String paramString2)
  {
    g.a("openSDK_LOG", "setAccessToken(), expiresIn = " + paramString2 + "");
    this.mQQAuth.setAccessToken(paramString1, paramString2);
  }

  public void setAvatar(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    String str = paramBundle.getString("picture");
    int i = paramBundle.getInt("exitAnim");
    new QQAvatar(this.mContext, this.mQQAuth.getQQToken()).setAvatar(paramActivity, Uri.parse(str), paramIUiListener, i);
  }

  public void setAvatar(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener, int paramInt1, int paramInt2)
  {
    paramBundle.putInt("exitAnim", paramInt2);
    paramActivity.overridePendingTransition(paramInt1, 0);
    setAvatar(paramActivity, paramBundle, paramIUiListener);
  }

  public void setOpenId(String paramString)
  {
    g.a("openSDK_LOG", "setOpenId() --start");
    this.mQQAuth.setOpenId(this.mContext, paramString);
    g.a("openSDK_LOG", "setOpenId() --end");
  }

  public void shareToQQ(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new QQShare(paramActivity, this.mQQAuth.getQQToken()).shareToQQ(paramActivity, paramBundle, paramIUiListener);
  }

  public void shareToQzone(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new QzoneShare(paramActivity, this.mQQAuth.getQQToken()).shareToQzone(paramActivity, paramBundle, paramIUiListener);
  }

  public void showTaskGuideWindow(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new TaskGuide(paramActivity, this.mQQAuth.getQQToken()).showTaskGuideWindow(paramActivity, paramBundle, paramIUiListener);
  }

  public int story(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQAuth.getQQToken()).story(paramActivity, paramBundle, paramIUiListener);
    return 0;
  }

  public void voice(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQAuth.getQQToken()).voice(paramActivity, paramBundle, paramIUiListener);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.Tencent
 * JD-Core Version:    0.6.2
 */