package com.tencent.connect.auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.b.a.g;
import com.tencent.connect.a.a;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.ApiTask;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.utils.ServerSetting;
import com.tencent.utils.SystemUtils;
import com.tencent.utils.Util;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthAgent extends BaseApi
{
  private IUiListener a;
  private String b;
  private Activity c;
  private IUiListener d = new IUiListener()
  {
    public void onCancel()
    {
    }

    public void onComplete(Object paramAnonymousObject)
    {
      if (paramAnonymousObject == null)
        AuthAgent.e(AuthAgent.this);
      while (true)
      {
        AuthAgent.this.writeEncryToken(AuthAgent.f(AuthAgent.this));
        return;
        JSONObject localJSONObject = (JSONObject)paramAnonymousObject;
        try
        {
          String str2 = localJSONObject.getString("encry_token");
          str1 = str2;
          if (!TextUtils.isEmpty(str1))
          {
            g.b("openSDK_LOG", "OpenUi, EncrytokenListener() onComplete validToken");
            AuthAgent.a(AuthAgent.this, str1);
          }
        }
        catch (JSONException localJSONException)
        {
          while (true)
          {
            localJSONException.printStackTrace();
            g.a("openSDK_LOG", "OpenUi, EncrytokenListener() onComplete error", localJSONException);
            String str1 = null;
          }
          g.b("openSDK_LOG", "OpenUi, EncrytokenListener() onComplete relogin");
          AuthAgent.e(AuthAgent.this);
        }
      }
    }

    public void onError(UiError paramAnonymousUiError)
    {
      g.b("openSDK_LOG", "AuthAgent, EncrytokenListener() onError relogin");
      AuthAgent.e(AuthAgent.this);
    }
  };
  private Handler e = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      g.b("openSDK_LOG", "OpenUi, handleMessage msg.what = " + paramAnonymousMessage.what + "");
      if (paramAnonymousMessage.what == 0)
        try
        {
          int j = Integer.parseInt(((JSONObject)paramAnonymousMessage.obj).getString("ret"));
          i = j;
          if (i == 0)
          {
            AuthAgent.g(AuthAgent.this).onComplete((JSONObject)paramAnonymousMessage.obj);
            return;
          }
        }
        catch (JSONException localJSONException)
        {
          while (true)
          {
            localJSONException.printStackTrace();
            AuthAgent.e(AuthAgent.this);
            int i = 0;
          }
          AuthAgent.e(AuthAgent.this);
          return;
        }
      AuthAgent.g(AuthAgent.this).onError(new UiError(paramAnonymousMessage.what, (String)paramAnonymousMessage.obj, null));
    }
  };

  public AuthAgent(Context paramContext, QQToken paramQQToken)
  {
    super(paramContext, paramQQToken);
  }

  private int a(boolean paramBoolean, IUiListener paramIUiListener)
  {
    g.a("openSDK_LOG", "OpenUi, showDialog --start");
    CookieSyncManager.createInstance(this.mContext);
    Bundle localBundle = composeCGIParams();
    if (paramBoolean)
      localBundle.putString("isadd", "1");
    localBundle.putString("scope", this.b);
    localBundle.putString("client_id", this.mToken.getAppId());
    if (isOEM)
      localBundle.putString("pf", "desktop_m_qq-" + installChannel + "-" + "android" + "-" + registerChannel + "-" + businessId);
    while (true)
    {
      String str1 = System.currentTimeMillis() / 1000L + "";
      localBundle.putString("sign", SystemUtils.getAppSignatureMD5(this.mContext, str1));
      localBundle.putString("time", str1);
      localBundle.putString("display", "mobile");
      localBundle.putString("response_type", "token");
      localBundle.putString("redirect_uri", "auth://tauth.qq.com/");
      localBundle.putString("cancel_display", "1");
      localBundle.putString("switch", "1");
      localBundle.putString("status_userip", Util.getUserIp());
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(ServerSetting.getInstance().getEnvUrl(this.mContext, "https://openmobile.qq.com/oauth2.0/m_authorize?"));
      localStringBuilder.append(Util.encodeUrl(localBundle));
      String str2 = localStringBuilder.toString();
      TokenListener localTokenListener = new TokenListener(this.mContext, paramIUiListener, true, false);
      g.b("openSDK_LOG", "OpenUi, showDialog TDialog");
      new AuthDialog(this.c, "action_login", str2, localTokenListener, this.mToken).show();
      return 2;
      localBundle.putString("pf", "openmobile_android");
    }
  }

  private void a()
  {
    this.mToken.setAccessToken("", "0");
    this.mToken.setOpenId("");
    doLogin(this.c, this.b, this.a, true);
  }

  private void a(String paramString)
  {
    g.b("openSDK_LOG", "OpenUi, EncrytokenListener() validToken()");
    Bundle localBundle = composeCGIParams();
    localBundle.putString("encrytoken", paramString);
    HttpUtils.requestAsync(this.mToken, this.mContext, "https://openmobile.qq.com/user/user_login_statis", localBundle, "POST", new RequestListener());
  }

  private boolean a(Activity paramActivity, boolean paramBoolean)
  {
    Intent localIntent = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
    if (localIntent != null)
    {
      Bundle localBundle = composeCGIParams();
      if (paramBoolean)
        localBundle.putString("isadd", "1");
      localBundle.putString("scope", this.b);
      localBundle.putString("client_id", this.mToken.getAppId());
      if (isOEM)
        localBundle.putString("pf", "desktop_m_qq-" + installChannel + "-" + "android" + "-" + registerChannel + "-" + businessId);
      while (true)
      {
        localBundle.putString("need_pay", "1");
        localBundle.putString("oauth_app_name", SystemUtils.getAppName(this.mContext));
        String str = System.currentTimeMillis() / 1000L + "";
        localBundle.putString("sign", SystemUtils.getAppSignatureMD5(this.mContext, str));
        localBundle.putString("time", str);
        localIntent.putExtra("key_action", "action_login");
        localIntent.putExtra("key_params", localBundle);
        this.mActivityIntent = localIntent;
        if (!hasActivityForIntent())
          break;
        this.a = new FeedConfirmListener(this.a);
        startAssitActivity(paramActivity, this.a);
        return true;
        localBundle.putString("pf", "openmobile_android");
      }
    }
    return false;
  }

  public int doLogin(Activity paramActivity, String paramString, IUiListener paramIUiListener)
  {
    return doLogin(paramActivity, paramString, paramIUiListener, false, false);
  }

  public int doLogin(Activity paramActivity, String paramString, IUiListener paramIUiListener, boolean paramBoolean)
  {
    return doLogin(paramActivity, paramString, paramIUiListener, paramBoolean, false);
  }

  public int doLogin(Activity paramActivity, String paramString, IUiListener paramIUiListener, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.b = paramString;
    this.c = paramActivity;
    this.a = paramIUiListener;
    if (!paramBoolean1)
    {
      String str1 = this.mToken.getAccessToken();
      String str2 = this.mToken.getOpenId();
      String str3 = this.mToken.getAppId();
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str3)))
      {
        Intent localIntent1 = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
        Intent localIntent2 = getTargetActivityIntent("com.tencent.open.agent.EncryTokenActivity");
        if ((localIntent2 != null) && (localIntent1 != null) && (localIntent1.getComponent() != null) && (localIntent2.getComponent() != null) && (localIntent1.getComponent().getPackageName().equals(localIntent2.getComponent().getPackageName())))
        {
          localIntent2.putExtra("oauth_consumer_key", str3);
          localIntent2.putExtra("openid", str2);
          localIntent2.putExtra("access_token", str1);
          localIntent2.putExtra("key_action", "action_check_token");
          this.mActivityIntent = localIntent2;
          if (hasActivityForIntent())
            startAssitActivity(paramActivity, this.d);
        }
        while (true)
        {
          return 3;
          String str4 = Util.encrypt("tencent&sdk&qazxc***14969%%" + str1 + str3 + str2 + "qzone3.4");
          JSONObject localJSONObject = new JSONObject();
          try
          {
            localJSONObject.put("encry_token", str4);
            this.d.onComplete(localJSONObject);
          }
          catch (JSONException localJSONException)
          {
            while (true)
              localJSONException.printStackTrace();
          }
        }
      }
    }
    if (a(paramActivity, paramBoolean2))
    {
      if (paramBoolean1)
        Util.reportBernoulli(paramActivity, "10785", 0L, this.mToken.getAppId());
      g.a("openSDK_LOG", "OpenUi, showUi, return Constants.UI_ACTIVITY");
      return 1;
    }
    this.a = new FeedConfirmListener(this.a);
    return a(paramBoolean2, this.a);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    Iterator localIterator = this.mTaskList.iterator();
    BaseApi.ApiTask localApiTask;
    do
    {
      boolean bool = localIterator.hasNext();
      localIUiListener = null;
      if (!bool)
        break;
      localApiTask = (BaseApi.ApiTask)localIterator.next();
    }
    while (localApiTask.mRequestCode != paramInt1);
    IUiListener localIUiListener = localApiTask.mListener;
    this.mTaskList.remove(localApiTask);
    if (localIUiListener == null)
      return;
    int i;
    String str1;
    if (paramInt2 == -1)
    {
      i = paramIntent.getIntExtra("key_error_code", 0);
      if (i == 0)
      {
        str1 = paramIntent.getStringExtra("key_response");
        if (str1 == null);
      }
    }
    while (true)
    {
      try
      {
        JSONObject localJSONObject = Util.parseJson(str1);
        if (localIUiListener == this.a)
        {
          String str2 = localJSONObject.getString("access_token");
          String str3 = localJSONObject.getString("expires_in");
          String str4 = localJSONObject.getString("openid");
          if ((!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str3)) && (!TextUtils.isEmpty(str4)))
          {
            this.mToken.setAccessToken(str2, str3);
            this.mToken.setOpenId(str4);
          }
        }
        localIUiListener.onComplete(localJSONObject);
        g.a().b();
        return;
      }
      catch (JSONException localJSONException)
      {
        localIUiListener.onError(new UiError(-4, "服务器返回数据格式有误!", str1));
        g.a("openSDK_LOG", "OpenUi, onActivityResult, json error", localJSONException);
        continue;
      }
      g.b("openSDK_LOG", "OpenUi, onActivityResult, onComplete");
      localIUiListener.onComplete(new JSONObject());
      continue;
      g.d("openSDK_LOG", "OpenUi, onActivityResult, onError = " + i + "");
      localIUiListener.onError(new UiError(i, paramIntent.getStringExtra("key_error_msg"), paramIntent.getStringExtra("key_error_detail")));
      continue;
      g.b("openSDK_LOG", "OpenUi, onActivityResult, Constants.ACTIVITY_CANCEL");
      localIUiListener.onCancel();
    }
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

  private class FeedConfirmListener
    implements IUiListener
  {
    IUiListener a;
    private String c = "sendinstall";
    private String d = "installwording";
    private String e = "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi";

    public FeedConfirmListener(IUiListener arg2)
    {
      Object localObject;
      this.a = localObject;
    }

    private Drawable a(String paramString, Context paramContext)
    {
      AssetManager localAssetManager = paramContext.getApplicationContext().getAssets();
      InputStream localInputStream;
      Object localObject;
      IOException localIOException2;
      try
      {
        localInputStream = localAssetManager.open(paramString);
        if (localInputStream == null)
          return null;
        if (paramString.endsWith(".9.png"))
        {
          Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream);
          if (localBitmap != null)
          {
            byte[] arrayOfByte = localBitmap.getNinePatchChunk();
            NinePatch.isNinePatchChunk(arrayOfByte);
            NinePatchDrawable localNinePatchDrawable = new NinePatchDrawable(localBitmap, arrayOfByte, new Rect(), null);
            return localNinePatchDrawable;
          }
        }
      }
      catch (IOException localIOException1)
      {
        localObject = null;
        localIOException2 = localIOException1;
      }
      while (true)
      {
        localIOException2.printStackTrace();
        return localObject;
        return null;
        Drawable localDrawable = Drawable.createFromStream(localInputStream, paramString);
        localObject = localDrawable;
        try
        {
          localInputStream.close();
          return localObject;
        }
        catch (IOException localIOException3)
        {
        }
      }
    }

    private View a(Context paramContext, Drawable paramDrawable, String paramString, View.OnClickListener paramOnClickListener1, View.OnClickListener paramOnClickListener2)
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      float f = localDisplayMetrics.density;
      RelativeLayout localRelativeLayout = new RelativeLayout(paramContext);
      ImageView localImageView = new ImageView(paramContext);
      localImageView.setImageDrawable(paramDrawable);
      localImageView.setScaleType(ImageView.ScaleType.FIT_XY);
      localImageView.setId(1);
      int i = (int)(60.0F * f);
      int j = (int)(60.0F * f);
      ((int)(14.0F * f));
      int k = (int)(18.0F * f);
      int m = (int)(6.0F * f);
      int n = (int)(18.0F * f);
      RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(i, j);
      localLayoutParams1.addRule(9);
      localLayoutParams1.setMargins(0, k, m, n);
      localRelativeLayout.addView(localImageView, localLayoutParams1);
      TextView localTextView = new TextView(paramContext);
      localTextView.setText(paramString);
      localTextView.setTextSize(14.0F);
      localTextView.setGravity(3);
      localTextView.setIncludeFontPadding(false);
      localTextView.setPadding(0, 0, 0, 0);
      localTextView.setLines(2);
      localTextView.setId(5);
      localTextView.setMinWidth((int)(185.0F * f));
      RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams2.addRule(1, 1);
      localLayoutParams2.addRule(6, 1);
      ((int)(10.0F * f));
      localLayoutParams2.setMargins(0, 0, (int)(5.0F * f), 0);
      localRelativeLayout.addView(localTextView, localLayoutParams2);
      View localView = new View(paramContext);
      localView.setBackgroundColor(Color.rgb(214, 214, 214));
      localView.setId(3);
      RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-2, 2);
      localLayoutParams3.addRule(3, 1);
      localLayoutParams3.addRule(5, 1);
      localLayoutParams3.addRule(7, 5);
      localLayoutParams3.setMargins(0, 0, 0, (int)(12.0F * f));
      localRelativeLayout.addView(localView, localLayoutParams3);
      LinearLayout localLinearLayout = new LinearLayout(paramContext);
      RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams4.addRule(5, 1);
      localLayoutParams4.addRule(7, 5);
      localLayoutParams4.addRule(3, 3);
      Button localButton1 = new Button(paramContext);
      localButton1.setText("跳过");
      localButton1.setBackgroundDrawable(a("buttonNegt.png", paramContext));
      localButton1.setTextColor(Color.rgb(36, 97, 131));
      localButton1.setTextSize(20.0F);
      localButton1.setOnClickListener(paramOnClickListener2);
      localButton1.setId(4);
      LinearLayout.LayoutParams localLayoutParams5 = new LinearLayout.LayoutParams(0, (int)(45.0F * f));
      localLayoutParams5.rightMargin = ((int)(14.0F * f));
      localLayoutParams5.leftMargin = ((int)(4.0F * f));
      localLayoutParams5.weight = 1.0F;
      localLinearLayout.addView(localButton1, localLayoutParams5);
      Button localButton2 = new Button(paramContext);
      localButton2.setText("确定");
      localButton2.setTextSize(20.0F);
      localButton2.setTextColor(Color.rgb(255, 255, 255));
      localButton2.setBackgroundDrawable(a("buttonPost.png", paramContext));
      localButton2.setOnClickListener(paramOnClickListener1);
      LinearLayout.LayoutParams localLayoutParams6 = new LinearLayout.LayoutParams(0, (int)(45.0F * f));
      localLayoutParams6.weight = 1.0F;
      localLayoutParams6.rightMargin = ((int)(4.0F * f));
      localLinearLayout.addView(localButton2, localLayoutParams6);
      localRelativeLayout.addView(localLinearLayout, localLayoutParams4);
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams((int)(279.0F * f), (int)(163.0F * f));
      localRelativeLayout.setPadding((int)(14.0F * f), 0, (int)(12.0F * f), (int)(12.0F * f));
      localRelativeLayout.setLayoutParams(localLayoutParams);
      localRelativeLayout.setBackgroundColor(Color.rgb(247, 251, 247));
      PaintDrawable localPaintDrawable = new PaintDrawable(Color.rgb(247, 251, 247));
      localPaintDrawable.setCornerRadius(f * 5.0F);
      localRelativeLayout.setBackgroundDrawable(localPaintDrawable);
      return localRelativeLayout;
    }

    private void a(String paramString, final IUiListener paramIUiListener, final Object paramObject)
    {
      Dialog localDialog = new Dialog(AuthAgent.i(AuthAgent.this));
      localDialog.requestWindowFeature(1);
      PackageManager localPackageManager = AuthAgent.i(AuthAgent.this).getPackageManager();
      try
      {
        PackageInfo localPackageInfo2 = localPackageManager.getPackageInfo(AuthAgent.i(AuthAgent.this).getPackageName(), 0);
        localPackageInfo1 = localPackageInfo2;
        Drawable localDrawable = null;
        if (localPackageInfo1 != null)
          localDrawable = localPackageInfo1.applicationInfo.loadIcon(localPackageManager);
        ButtonListener local1 = new ButtonListener(localDialog, paramIUiListener)
        {
          public void onClick(View paramAnonymousView)
          {
            AuthAgent.FeedConfirmListener.this.a();
            if ((this.d != null) && (this.d.isShowing()))
              this.d.dismiss();
            if (paramIUiListener != null)
              paramIUiListener.onComplete(paramObject);
          }
        };
        ButtonListener local2 = new ButtonListener(localDialog, paramIUiListener)
        {
          public void onClick(View paramAnonymousView)
          {
            if ((this.d != null) && (this.d.isShowing()))
              this.d.dismiss();
            if (paramIUiListener != null)
              paramIUiListener.onComplete(paramObject);
          }
        };
        ColorDrawable localColorDrawable = new ColorDrawable();
        localColorDrawable.setAlpha(0);
        localDialog.getWindow().setBackgroundDrawable(localColorDrawable);
        localDialog.setContentView(a(AuthAgent.i(AuthAgent.this), localDrawable, paramString, local1, local2));
        localDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
          public void onCancel(DialogInterface paramAnonymousDialogInterface)
          {
            if (paramIUiListener != null)
              paramIUiListener.onComplete(paramObject);
          }
        });
        if ((AuthAgent.i(AuthAgent.this) != null) && (!AuthAgent.i(AuthAgent.this).isFinishing()))
          localDialog.show();
        return;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        while (true)
        {
          localNameNotFoundException.printStackTrace();
          PackageInfo localPackageInfo1 = null;
        }
      }
    }

    protected void a()
    {
      Bundle localBundle = AuthAgent.j(AuthAgent.this);
      HttpUtils.requestAsync(AuthAgent.k(AuthAgent.this), AuthAgent.i(AuthAgent.this), this.e, localBundle, "POST", null);
    }

    public void onCancel()
    {
      if (this.a != null)
        this.a.onCancel();
    }

    public void onComplete(Object paramObject)
    {
      int i = 0;
      if (paramObject != null)
      {
        localJSONObject = (JSONObject)paramObject;
        if (localJSONObject == null);
      }
      while (this.a == null)
        try
        {
          JSONObject localJSONObject;
          int m = localJSONObject.getInt(this.c);
          i = 0;
          if (m == 1)
            i = 1;
          String str3 = localJSONObject.getString(this.d);
          str1 = str3;
          k = i;
          String str2 = URLDecoder.decode(str1);
          Log.d("TAG", " WORDING = " + str2 + "xx");
          if ((k != 0) && (!TextUtils.isEmpty(str2)))
          {
            a(str2, this.a, paramObject);
            return;
          }
        }
        catch (JSONException localJSONException)
        {
          do
            while (true)
            {
              int j = i;
              Toast.makeText(AuthAgent.i(AuthAgent.this), "json error", 1);
              localJSONException.printStackTrace();
              int k = j;
              String str1 = "";
            }
          while (this.a == null);
          this.a.onComplete(paramObject);
          return;
        }
      this.a.onComplete(null);
    }

    public void onError(UiError paramUiError)
    {
      if (this.a != null)
        this.a.onError(paramUiError);
    }

    private abstract class ButtonListener
      implements View.OnClickListener
    {
      Dialog d;

      ButtonListener(Dialog arg2)
      {
        Object localObject;
        this.d = localObject;
      }
    }
  }

  private class RequestListener
    implements IRequestListener
  {
    public RequestListener()
    {
      g.b("openSDK_LOG", "OpenUi, RequestListener()");
    }

    public void onComplete(JSONObject paramJSONObject)
    {
      g.b("openSDK_LOG", "OpenUi, RequestListener() onComplete");
      Message localMessage = new Message();
      localMessage.what = 0;
      localMessage.obj = paramJSONObject;
      AuthAgent.h(AuthAgent.this).sendMessage(localMessage);
    }

    public void onConnectTimeoutException(ConnectTimeoutException paramConnectTimeoutException)
    {
      g.a("openSDK_LOG", "OpenUi, RequestListener() onConnectTimeoutException", paramConnectTimeoutException);
      Message localMessage = new Message();
      localMessage.what = -7;
      localMessage.obj = (paramConnectTimeoutException.getMessage() + "");
      AuthAgent.h(AuthAgent.this).sendMessage(localMessage);
    }

    public void onHttpStatusException(HttpUtils.HttpStatusException paramHttpStatusException)
    {
      g.a("openSDK_LOG", "OpenUi, RequestListener() onHttpStatusException", paramHttpStatusException);
      Message localMessage = new Message();
      localMessage.what = -9;
      localMessage.obj = (paramHttpStatusException.getMessage() + "");
      AuthAgent.h(AuthAgent.this).sendMessage(localMessage);
    }

    public void onIOException(IOException paramIOException)
    {
      g.a("openSDK_LOG", "OpenUi, RequestListener() onIOException", paramIOException);
      Message localMessage = new Message();
      localMessage.what = -2;
      localMessage.obj = (paramIOException.getMessage() + "");
      AuthAgent.h(AuthAgent.this).sendMessage(localMessage);
    }

    public void onJSONException(JSONException paramJSONException)
    {
      g.a("openSDK_LOG", "OpenUi, RequestListener() onJSONException", paramJSONException);
      Message localMessage = new Message();
      localMessage.what = -4;
      localMessage.obj = (paramJSONException.getMessage() + "");
      AuthAgent.h(AuthAgent.this).sendMessage(localMessage);
    }

    public void onMalformedURLException(MalformedURLException paramMalformedURLException)
    {
      Message localMessage = new Message();
      localMessage.what = -3;
      localMessage.obj = (paramMalformedURLException.getMessage() + "");
      AuthAgent.h(AuthAgent.this).sendMessage(localMessage);
    }

    public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException paramNetworkUnavailableException)
    {
      g.a("openSDK_LOG", "OpenUi, RequestListener() onNetworkUnavailableException", paramNetworkUnavailableException);
      Message localMessage = new Message();
      localMessage.what = -2;
      localMessage.obj = (paramNetworkUnavailableException.getMessage() + "");
      AuthAgent.h(AuthAgent.this).sendMessage(localMessage);
    }

    public void onSocketTimeoutException(SocketTimeoutException paramSocketTimeoutException)
    {
      g.a("openSDK_LOG", "OpenUi, RequestListener() onSocketTimeoutException", paramSocketTimeoutException);
      Message localMessage = new Message();
      localMessage.what = -8;
      localMessage.obj = (paramSocketTimeoutException.getMessage() + "");
      AuthAgent.h(AuthAgent.this).sendMessage(localMessage);
    }

    public void onUnknowException(Exception paramException)
    {
      g.a("openSDK_LOG", "OpenUi, RequestListener() onUnknowException", paramException);
      Message localMessage = new Message();
      localMessage.what = -6;
      localMessage.obj = (paramException.getMessage() + "");
      AuthAgent.h(AuthAgent.this).sendMessage(localMessage);
    }
  }

  private class TokenListener
    implements IUiListener
  {
    private IUiListener b;
    private boolean c;
    private Context d;

    public TokenListener(Context paramIUiListener, IUiListener paramBoolean1, boolean paramBoolean2, boolean arg5)
    {
      this.d = paramIUiListener;
      this.b = paramBoolean1;
      this.c = paramBoolean2;
      g.b("openSDK_LOG", "OpenUi, TokenListener()");
    }

    public void onCancel()
    {
      g.b("openSDK_LOG", "OpenUi, TokenListener() onCancel");
      this.b.onCancel();
      g.a().b();
    }

    public void onComplete(Object paramObject)
    {
      g.b("openSDK_LOG", "OpenUi, TokenListener() onComplete");
      JSONObject localJSONObject = (JSONObject)paramObject;
      try
      {
        String str1 = localJSONObject.getString("access_token");
        String str2 = localJSONObject.getString("expires_in");
        String str3 = localJSONObject.getString("openid");
        if ((str1 != null) && (AuthAgent.a(AuthAgent.this) != null) && (str3 != null))
        {
          AuthAgent.b(AuthAgent.this).setAccessToken(str1, str2);
          AuthAgent.c(AuthAgent.this).setOpenId(str3);
          a.d(this.d, AuthAgent.d(AuthAgent.this));
        }
        String str4 = localJSONObject.getString("pf");
        if (str4 != null);
        try
        {
          this.d.getSharedPreferences("pfStore", 0).edit().putString("pf", str4).commit();
          if (this.c)
            CookieSyncManager.getInstance().sync();
          this.b.onComplete(localJSONObject);
          g.a().b();
          return;
        }
        catch (Exception localException)
        {
          while (true)
          {
            localException.printStackTrace();
            g.a("openSDK_LOG", "OpenUi, TokenListener() onComplete error", localException);
          }
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
        {
          localJSONException.printStackTrace();
          g.a("openSDK_LOG", "OpenUi, TokenListener() onComplete error", localJSONException);
        }
      }
    }

    public void onError(UiError paramUiError)
    {
      g.b("openSDK_LOG", "OpenUi, TokenListener() onError");
      this.b.onError(paramUiError);
      g.a().b();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.auth.AuthAgent
 * JD-Core Version:    0.6.2
 */