package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.tencent.b.a.g;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.ServerSetting;
import com.tencent.utils.SystemUtils;
import com.tencent.utils.Util;
import org.json.JSONException;
import org.json.JSONObject;

public class SocialApiIml extends BaseApi
{
  ProgressDialog a;
  private Activity b;

  public SocialApiIml(Context paramContext, QQAuth paramQQAuth, QQToken paramQQToken)
  {
    super(paramContext, paramQQAuth, paramQQToken);
  }

  public SocialApiIml(Context paramContext, QQToken paramQQToken)
  {
    super(paramContext, paramQQToken);
  }

  private b a(Bundle paramBundle, String paramString1, String paramString2, IUiListener paramIUiListener)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(Constants.PACKAGE_QZONE, "com.tencent.open.agent.AgentActivity");
    b localb = new b();
    localb.a = localIntent;
    localb.c = paramBundle;
    localb.d = paramString2;
    localb.e = paramIUiListener;
    localb.b = paramString1;
    return localb;
  }

  private void a(Activity paramActivity, Intent paramIntent, String paramString1, Bundle paramBundle, String paramString2, IUiListener paramIUiListener)
  {
    Log.i("SocialApiIml", "SocialApiIml handleIntent " + paramString1 + " params=" + paramBundle + " activityIntent=" + paramIntent);
    if (paramIntent != null)
    {
      paramIntent.putExtra("key_action", paramString1);
      paramIntent.putExtra("key_params", paramBundle);
      this.mActivityIntent = paramIntent;
      startAssitActivity(paramActivity, paramIUiListener);
    }
    c localc;
    do
    {
      return;
      Intent localIntent1 = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
      localc = new c(paramIUiListener, paramString1, paramString2, paramBundle);
      Intent localIntent2 = getTargetActivityIntent("com.tencent.open.agent.EncryTokenActivity");
      if ((localIntent2 == null) || (localIntent1 == null) || (localIntent1.getComponent() == null) || (localIntent2.getComponent() == null) || (!localIntent1.getComponent().getPackageName().equals(localIntent2.getComponent().getPackageName())))
        break;
      localIntent2.putExtra("oauth_consumer_key", this.mToken.getAppId());
      localIntent2.putExtra("openid", this.mToken.getOpenId());
      localIntent2.putExtra("access_token", this.mToken.getAccessToken());
      localIntent2.putExtra("key_action", "action_check_token");
      this.mActivityIntent = localIntent2;
    }
    while (!hasActivityForIntent());
    startAssitActivity(paramActivity, localc);
    return;
    String str = Util.encrypt("tencent&sdk&qazxc***14969%%" + this.mToken.getAccessToken() + this.mToken.getAppId() + this.mToken.getOpenId() + "qzone3.4");
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("encry_token", str);
      localc.onComplete(localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  private void a(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.voice");
    String str = ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/not_support.html?");
    if ((localIntent == null) && (a()))
    {
      if ((this.a == null) || (!this.a.isShowing()))
      {
        this.a = new ProgressDialog(paramActivity);
        this.a.setTitle("请稍候");
        this.a.show();
      }
      a(paramActivity, "action_voice", new a(a(paramBundle, "action_voice", str, paramIUiListener)));
      return;
    }
    a(paramActivity, localIntent, "action_voice", paramBundle, str, paramIUiListener);
  }

  private void a(Activity paramActivity, String paramString, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.b = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.RequestFreegiftActivity");
    paramBundle.putAll(composeActivityParams());
    if ("action_ask".equals(paramString))
      paramBundle.putString("type", "request");
    while (true)
    {
      a(paramActivity, localIntent, paramString, paramBundle, ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/request/sdk_request.html?"), paramIUiListener);
      return;
      if ("action_gift".equals(paramString))
        paramBundle.putString("type", "freegift");
    }
  }

  private void a(Context paramContext, String paramString1, Bundle paramBundle, String paramString2, IUiListener paramIUiListener)
  {
    g.a("openSDK_LOG", "OpenUi, showDialog --start");
    CookieSyncManager.createInstance(paramContext);
    paramBundle.putString("oauth_consumer_key", this.mToken.getAppId());
    if (this.mToken.isSessionValid())
      paramBundle.putString("access_token", this.mToken.getAccessToken());
    String str1 = this.mToken.getOpenId();
    if (str1 != null)
      paramBundle.putString("openid", str1);
    try
    {
      paramBundle.putString("pf", this.mContext.getSharedPreferences("pfStore", 0).getString("pf", "openmobile_android"));
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString2);
      localStringBuilder.append(Util.encodeUrl(paramBundle));
      str2 = localStringBuilder.toString();
      g.b("openSDK_LOG", "OpenUi, showDialog TDialog");
      if (("action_challenge".equals(paramString1)) || ("action_brag".equals(paramString1)))
      {
        g.b("openSDK_LOG", "OpenUi, showDialog PKDialog");
        new PKDialog(this.b, paramString1, str2, paramIUiListener, this.mToken).show();
        return;
      }
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        localException.printStackTrace();
        paramBundle.putString("pf", "openmobile_android");
      }
      new TDialog(this.b, paramString1, str2, paramIUiListener, this.mToken).show();
    }
  }

  private void b()
  {
    if ((!this.b.isFinishing()) && (this.a != null) && (this.a.isShowing()))
    {
      this.a.dismiss();
      this.a = null;
    }
  }

  protected void a(Activity paramActivity, String paramString, IUiListener paramIUiListener)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(Constants.PACKAGE_QZONE, "com.tencent.open.agent.AgentActivity");
    localIntent.putExtra("key_action", "action_check");
    Bundle localBundle = new Bundle();
    localBundle.putString("apiName", paramString);
    localIntent.putExtra("key_params", localBundle);
    this.mActivityIntent = localIntent;
    startAssitActivity(paramActivity, paramIUiListener);
  }

  protected boolean a()
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(Constants.PACKAGE_QZONE, "com.tencent.open.agent.CheckFunctionActivity");
    return SystemUtils.isActivityExist(this.mContext, localIntent);
  }

  public void ask(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    a(paramActivity, "action_ask", paramBundle, paramIUiListener);
  }

  public void brag(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.b = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.BragActivity");
    paramBundle.putAll(composeActivityParams());
    a(paramActivity, localIntent, "action_brag", paramBundle, ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/brag/sdk_brag.html?"), paramIUiListener);
  }

  public void challenge(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.b = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.ChallengeActivity");
    paramBundle.putAll(composeActivityParams());
    a(paramActivity, localIntent, "action_challenge", paramBundle, ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/brag/sdk_brag.html?"), paramIUiListener);
  }

  public void gift(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    a(paramActivity, "action_gift", paramBundle, paramIUiListener);
  }

  public void grade(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.b = paramActivity;
    paramBundle.putAll(composeActivityParams());
    paramBundle.putString("version", Util.getAppVersion(paramActivity));
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.AppGradeActivity");
    if ((localIntent == null) && (a()))
    {
      this.a = new ProgressDialog(paramActivity);
      this.a.setMessage("请稍候...");
      this.a.show();
      a(paramActivity, "action_grade", new a(a(paramBundle, "action_grade", "http://qzs.qq.com/open/mobile/rate/sdk_rate.html?", paramIUiListener)));
      return;
    }
    a(paramActivity, localIntent, "action_grade", paramBundle, "http://qzs.qq.com/open/mobile/rate/sdk_rate.html?", paramIUiListener);
  }

  public void invite(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.b = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.AppInvitationActivity");
    paramBundle.putAll(composeActivityParams());
    a(paramActivity, localIntent, "action_invite", paramBundle, ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/invite/sdk_invite.html?"), paramIUiListener);
  }

  public void reactive(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.b = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.ReactiveActivity");
    paramBundle.putAll(composeActivityParams());
    String str = ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/reactive/sdk_reactive.html?");
    if ((localIntent == null) && (a()))
    {
      this.a = new ProgressDialog(paramActivity);
      this.a.setMessage("请稍候...");
      this.a.show();
      paramBundle.putString("type", "reactive");
      a(paramActivity, "action_reactive", new a(a(paramBundle, "action_reactive", str, paramIUiListener)));
      return;
    }
    paramBundle.putString("sendImg", paramBundle.getString("img"));
    paramBundle.remove("img");
    a(paramActivity, localIntent, "action_reactive", paramBundle, str, paramIUiListener);
  }

  public void story(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.b = paramActivity;
    Intent localIntent = getAgentIntentWithTarget("com.tencent.open.agent.SendStoryActivity");
    paramBundle.putAll(composeActivityParams());
    a(paramActivity, localIntent, "action_story", paramBundle, ServerSetting.getInstance().getEnvUrl(this.mContext, "http://qzs.qq.com/open/mobile/sendstory/sdk_sendstory_v1.3.html?"), paramIUiListener);
  }

  public void voice(final Activity paramActivity, final Bundle paramBundle, final IUiListener paramIUiListener)
  {
    Log.v("voice", "voice params=" + paramBundle);
    this.b = paramActivity;
    paramBundle.putAll(composeActivityParams());
    paramBundle.putString("version", Util.getAppVersion(paramActivity));
    if (!c.a())
    {
      paramIUiListener.onError(new UiError(-12, "检测不到SD卡，无法发送语音！", "检测不到SD卡，无法发送语音！"));
      return;
    }
    if (paramBundle.containsKey("image_date"))
    {
      Bitmap localBitmap = (Bitmap)paramBundle.getParcelable("image_date");
      if (localBitmap != null)
      {
        this.a = new ProgressDialog(paramActivity);
        this.a.setTitle("请稍候，正在查询…");
        this.a.show();
        new c(new c.a()
        {
          public void a(String paramAnonymousString)
          {
            paramBundle.remove("image_date");
            if (!TextUtils.isEmpty(paramAnonymousString))
              paramBundle.putString("image_date", paramAnonymousString);
            SocialApiIml.a(SocialApiIml.this, paramActivity, paramBundle, paramIUiListener);
          }

          public void b(String paramAnonymousString)
          {
            paramBundle.remove("image_date");
            UiError localUiError = new UiError(-5, "图片读取失败，请检查该图片是否有效", "图片读取失败，请检查该图片是否有效");
            paramIUiListener.onError(localUiError);
            SocialApiIml.a(SocialApiIml.this);
          }
        }).execute(new Bitmap[] { localBitmap });
        return;
      }
    }
    a(paramActivity, paramBundle, paramIUiListener);
  }

  @SuppressLint({"SetJavaScriptEnabled"})
  public void writeEncryToken(Context paramContext)
  {
    String str1 = this.mToken.getAccessToken();
    String str2 = this.mToken.getAppId();
    String str3 = this.mToken.getOpenId();
    if ((str1 != null) && (str1.length() > 0) && (str2 != null) && (str2.length() > 0) && (str3 != null) && (str3.length() > 0));
    for (String str4 = Util.encrypt("tencent&sdk&qazxc***14969%%" + str1 + str2 + str3 + "qzone3.4"); ; str4 = null)
    {
      WebView localWebView = new WebView(paramContext);
      WebSettings localWebSettings = localWebView.getSettings();
      localWebSettings.setDomStorageEnabled(true);
      localWebSettings.setJavaScriptEnabled(true);
      localWebSettings.setDatabaseEnabled(true);
      String str5 = "<!DOCTYPE HTML><html lang=\"en-US\"><head><meta charset=\"UTF-8\"><title>localStorage Test</title><script type=\"text/javascript\">document.domain = 'qq.com';localStorage[\"" + this.mToken.getOpenId() + "_" + this.mToken.getAppId() + "\"]=\"" + str4 + "\";</script></head><body></body></html>";
      String str6 = ServerSetting.getInstance().getEnvUrl(paramContext, "http://qzs.qq.com");
      localWebView.loadDataWithBaseURL(str6, str5, "text/html", "utf-8", str6);
      return;
    }
  }

  protected class a
    implements IUiListener
  {
    SocialApiIml.b a;

    public a(SocialApiIml.b arg2)
    {
      Object localObject;
      this.a = localObject;
    }

    public void onCancel()
    {
      SocialApiIml.a(SocialApiIml.this);
      c.a(this.a.c.getString("image_date"));
      SocialApiIml.a(SocialApiIml.this, SocialApiIml.b(SocialApiIml.this), null, this.a.b, this.a.c, this.a.d, this.a.e);
    }

    public void onComplete(Object paramObject)
    {
      Log.d("TAG", "CheckListener--onComplete--response = " + paramObject.toString());
      int i = 0;
      JSONObject localJSONObject;
      if (paramObject != null)
        localJSONObject = (JSONObject)paramObject;
      try
      {
        boolean bool = localJSONObject.getBoolean("check_result");
        i = bool;
        SocialApiIml.a(SocialApiIml.this);
        if (i != 0)
        {
          Log.d("TAG", "CheckListener---delayStartParam.agentIntent = " + this.a.a + " delayStartParam.action = " + this.a.b);
          SocialApiIml.a(SocialApiIml.this, SocialApiIml.b(SocialApiIml.this), this.a.a, this.a.b, this.a.c, this.a.d, this.a.e);
          return;
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
        {
          localJSONException.printStackTrace();
          i = 0;
        }
        c.a(this.a.c.getString("image_date"));
        SocialApiIml.a(SocialApiIml.this, SocialApiIml.b(SocialApiIml.this), null, this.a.b, this.a.c, this.a.d, this.a.e);
      }
    }

    public void onError(UiError paramUiError)
    {
      SocialApiIml.a(SocialApiIml.this);
      c.a(this.a.c.getString("image_date"));
      SocialApiIml.a(SocialApiIml.this, SocialApiIml.b(SocialApiIml.this), null, this.a.b, this.a.c, this.a.d, this.a.e);
    }
  }

  private static class b
  {
    Intent a;
    String b;
    Bundle c;
    String d;
    IUiListener e;
  }

  private class c
    implements IUiListener
  {
    private IUiListener b;
    private String c;
    private String d;
    private Bundle e;

    c(IUiListener paramString1, String paramString2, String paramBundle, Bundle arg5)
    {
      this.b = paramString1;
      this.c = paramString2;
      this.d = paramBundle;
      Object localObject;
      this.e = localObject;
    }

    public void onCancel()
    {
      this.b.onCancel();
    }

    public void onComplete(Object paramObject)
    {
      JSONObject localJSONObject = (JSONObject)paramObject;
      try
      {
        String str2 = localJSONObject.getString("encry_token");
        str1 = str2;
        this.e.putString("encrytoken", str1);
        SocialApiIml.a(SocialApiIml.this, SocialApiIml.b(SocialApiIml.this), this.c, this.e, this.d, this.b);
        if (TextUtils.isEmpty(str1))
        {
          Log.d("miles", "The token get from qq or qzone is empty. Write temp token to localstorage.");
          SocialApiIml.this.writeEncryToken(SocialApiIml.c(SocialApiIml.this));
        }
        return;
      }
      catch (JSONException localJSONException)
      {
        while (true)
        {
          localJSONException.printStackTrace();
          g.a("openSDK_LOG", "OpenApi, EncrytokenListener() onComplete error", localJSONException);
          String str1 = null;
        }
      }
    }

    public void onError(UiError paramUiError)
    {
      g.b("openSDK_LOG", "OpenApi, EncryptTokenListener() onError" + paramUiError.errorMessage);
      this.b.onError(paramUiError);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.SocialApiIml
 * JD-Core Version:    0.6.2
 */