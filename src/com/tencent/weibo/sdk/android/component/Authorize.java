package com.tencent.weibo.sdk.android.component;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.api.util.Util;
import java.lang.reflect.Method;
import java.util.Properties;

public class Authorize extends Activity
{
  public static final int ALERT_DOWNLOAD = 0;
  public static final int ALERT_FAV = 1;
  public static final int ALERT_NETWORK = 4;
  public static final int PROGRESS_H = 3;
  public static int WEBVIEWSTATE_1 = 0;
  Dialog _dialog;
  String _fileName;
  String _url;
  private String clientId = null;
  private ProgressDialog dialog;
  Handler handle = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 100:
      }
      Authorize.this.showDialog(4);
    }
  };
  private boolean isShow = false;
  private LinearLayout layout = null;
  String path;
  private String redirectUri = null;
  WebView webView;
  int webview_state = 0;

  public void initLayout()
  {
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, -1);
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
    this.dialog = new ProgressDialog(this);
    this.dialog.setProgressStyle(0);
    this.dialog.requestWindowFeature(1);
    this.dialog.setMessage("请稍后...");
    this.dialog.setIndeterminate(false);
    this.dialog.setCancelable(false);
    this.dialog.show();
    this.layout = new LinearLayout(this);
    this.layout.setLayoutParams(localLayoutParams1);
    this.layout.setOrientation(1);
    RelativeLayout localRelativeLayout = new RelativeLayout(this);
    localRelativeLayout.setLayoutParams(localLayoutParams2);
    localRelativeLayout.setBackgroundDrawable(BackGroudSeletor.getdrawble("up_bg2x", getApplication()));
    localRelativeLayout.setGravity(0);
    Button localButton = new Button(this);
    localButton.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(new String[] { "quxiao_btn2x", "quxiao_btn_hover" }, getApplication()));
    localButton.setText("取消");
    localLayoutParams3.addRule(9, -1);
    localLayoutParams3.addRule(15, -1);
    localLayoutParams3.leftMargin = 10;
    localLayoutParams3.topMargin = 10;
    localLayoutParams3.bottomMargin = 10;
    localButton.setLayoutParams(localLayoutParams3);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Authorize.this.finish();
      }
    });
    localRelativeLayout.addView(localButton);
    TextView localTextView = new TextView(this);
    localTextView.setText("授权");
    localTextView.setTextColor(-1);
    localTextView.setTextSize(22.0F);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams4.addRule(13, -1);
    localTextView.setLayoutParams(localLayoutParams4);
    localRelativeLayout.addView(localTextView);
    this.layout.addView(localRelativeLayout);
    this.webView = new WebView(this);
    Class[] arrayOfClass;
    Object[] arrayOfObject;
    if (Build.VERSION.SDK_INT >= 11)
    {
      arrayOfClass = new Class[] { String.class };
      arrayOfObject = new Object[] { "searchBoxJavaBridge_" };
    }
    try
    {
      this.webView.getClass().getDeclaredMethod("removeJavascriptInterface", arrayOfClass).invoke(this.webView, arrayOfObject);
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
      this.webView.setLayoutParams(localLayoutParams);
      WebSettings localWebSettings = this.webView.getSettings();
      this.webView.setVerticalScrollBarEnabled(false);
      localWebSettings.setJavaScriptEnabled(true);
      localWebSettings.setUseWideViewPort(true);
      localWebSettings.setLoadWithOverviewMode(false);
      this.webView.loadUrl(this.path);
      this.webView.setWebChromeClient(new WebChromeClient()
      {
        public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
        {
          super.onProgressChanged(paramAnonymousWebView, paramAnonymousInt);
          Log.d("newProgress", paramAnonymousInt + "..");
        }
      });
      this.webView.setWebViewClient(new WebViewClient()
      {
        public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          Log.d("backurl", "page finished:" + paramAnonymousString);
          if ((paramAnonymousString.indexOf("access_token") != -1) && (!Authorize.this.isShow))
            Authorize.this.jumpResultParser(paramAnonymousString);
          if ((Authorize.this.dialog != null) && (Authorize.this.dialog.isShowing()))
            Authorize.this.dialog.cancel();
        }

        public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
        {
          Log.d("backurl", "page start:" + paramAnonymousString);
          if ((paramAnonymousString.indexOf("access_token") != -1) && (!Authorize.this.isShow))
            Authorize.this.jumpResultParser(paramAnonymousString);
          if ((Authorize.this.dialog != null) && (Authorize.this.dialog.isShowing()))
            Authorize.this.dialog.cancel();
        }

        public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          if ((paramAnonymousString.indexOf("access_token") != -1) && (!Authorize.this.isShow))
            Authorize.this.jumpResultParser(paramAnonymousString);
          return false;
        }
      });
      this.layout.addView(this.webView);
      setContentView(this.layout);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void jumpResultParser(String paramString)
  {
    String[] arrayOfString = paramString.split("#")[1].split("&");
    String str1 = arrayOfString[0].split("=")[1];
    String str2 = arrayOfString[1].split("=")[1];
    String str3 = arrayOfString[2].split("=")[1];
    String str4 = arrayOfString[3].split("=")[1];
    String str5 = arrayOfString[4].split("=")[1];
    arrayOfString[5].split("=")[1];
    String str6 = arrayOfString[6].split("=")[1];
    String str7 = arrayOfString[7].split("=")[1];
    Context localContext = getApplicationContext();
    if ((str1 != null) && (!"".equals(str1)))
    {
      Util.saveSharePersistent(localContext, "ACCESS_TOKEN", str1);
      Util.saveSharePersistent(localContext, "EXPIRES_IN", str2);
      Util.saveSharePersistent(localContext, "OPEN_ID", str3);
      Util.saveSharePersistent(localContext, "OPEN_KEY", str4);
      Util.saveSharePersistent(localContext, "REFRESH_TOKEN", str5);
      Util.saveSharePersistent(localContext, "NAME", str6);
      Util.saveSharePersistent(localContext, "NICK", str7);
      Util.saveSharePersistent(localContext, "CLIENT_ID", this.clientId);
      Util.saveSharePersistent(localContext, "AUTHORIZETIME", String.valueOf(System.currentTimeMillis() / 1000L));
      Toast.makeText(this, "授权成功", 0).show();
      finish();
      this.isShow = true;
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (!Util.isNetworkAvailable(this))
    {
      showDialog(4);
      return;
    }
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    BackGroudSeletor.setPix(localDisplayMetrics.widthPixels + "x" + localDisplayMetrics.heightPixels);
    try
    {
      this.clientId = Util.getConfig().getProperty("APP_KEY");
      this.redirectUri = Util.getConfig().getProperty("REDIRECT_URI");
      if ((this.clientId == null) || ("".equals(this.clientId)) || (this.redirectUri == null) || ("".equals(this.redirectUri)))
        Toast.makeText(this, "请在配置文件中填写相应的信息", 0).show();
      Log.d("redirectUri", this.redirectUri);
      requestWindowFeature(1);
      int i = 111 + 1000 * (int)Math.random();
      this.path = ("http://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=" + this.clientId + "&response_type=token&redirect_uri=" + this.redirectUri + "&state=" + i);
      Log.e("qtradio", this.path);
      initLayout();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 3:
    case 4:
    }
    while (true)
    {
      return this._dialog;
      this._dialog = new ProgressDialog(this);
      ((ProgressDialog)this._dialog).setMessage("加载中...");
      continue;
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
      localBuilder.setTitle("网络连接异常，是否重新连接？");
      localBuilder.setPositiveButton("是", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          if (Util.isNetworkAvailable(Authorize.this))
          {
            Authorize.this.webView.loadUrl(Authorize.this.path);
            return;
          }
          Message localMessage = Message.obtain();
          localMessage.what = 100;
          Authorize.this.handle.sendMessage(localMessage);
        }
      });
      localBuilder.setNegativeButton("否", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          Authorize.this.finish();
        }
      });
      this._dialog = localBuilder.create();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.component.Authorize
 * JD-Core Version:    0.6.2
 */