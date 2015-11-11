package fm.qingting.qtradio.view.webview;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View.MeasureSpec;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.fm.WebViewPlayer;
import fm.qingting.qtradio.manager.advertisement.AdvertisementManage;
import fm.qingting.qtradio.manager.advertisement.UMengLogger;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.view.LoadingView;
import fm.qingting.utils.AppInfo;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LocalWebView extends ViewGroupViewImpl
{
  private static WebView webView = null;
  private String URL = "http://qingting.fm";
  private boolean hasRemoved = false;
  private Context mContext;
  private LoadingView mLoadingView;
  private ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private qtWebViewClient webViewClient = new qtWebViewClient(null);
  private final ViewLayout webviewLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL | ViewLayout.SLTR | ViewLayout.SC | ViewLayout.CW | ViewLayout.SVH);
  private boolean withParam;

  public LocalWebView(Context paramContext, String paramString, boolean paramBoolean)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.URL = paramString;
    this.withParam = paramBoolean;
    init();
    this.mLoadingView = new LoadingView(paramContext);
    addView(this.mLoadingView);
  }

  private String getFromAssets(String paramString)
  {
    Object localObject;
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(getResources().getAssets().open(paramString)));
      String str2;
      for (localObject = ""; ; localObject = str2)
      {
        String str1 = localBufferedReader.readLine();
        if (str1 == null)
          break;
        str2 = (String)localObject + str1;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localObject = "";
    }
    return localObject;
  }

  private void removeLoading()
  {
    if (!this.hasRemoved)
    {
      removeView(this.mLoadingView);
      this.hasRemoved = true;
    }
  }

  protected String buildUrl()
  {
    if (this.URL == null)
      return "";
    this.URL += "&phonetype=android";
    String str1 = AppInfo.getCurrentInternalVersion(this.mContext);
    if (str1 != null)
      this.URL = (this.URL + "&versioncode=" + str1);
    String str2 = InfoManager.getInstance().getDeviceId();
    if (str2 == null)
      str2 = "UnknownUser";
    this.URL = (this.URL + "&deviceId=" + str2);
    return this.URL;
  }

  public boolean canBack()
  {
    if (webView != null)
    {
      String str = WebViewPlayer.getInstance().getBackPolicy();
      if ((str != null) && (!str.equalsIgnoreCase("")))
        return true;
    }
    return false;
  }

  public void destroy()
  {
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent == null)
      return false;
    if ((paramKeyEvent.getKeyCode() == 4) && (paramKeyEvent.getAction() == 1) && (canBack()))
    {
      goBack();
      return true;
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public String getUrl()
  {
    return this.URL;
  }

  public void goBack()
  {
    if (webView != null)
    {
      String str = WebViewPlayer.getInstance().getBackPolicy();
      if ((str != null) && (!str.equalsIgnoreCase("")))
      {
        webView.loadUrl(str);
        WebViewPlayer.getInstance().setbackPolicy(null);
      }
    }
    else
    {
      return;
    }
    webView.goBack();
  }

  @TargetApi(18)
  protected void init()
  {
    try
    {
      webView = new WebView(this.mContext);
      WebSettings localWebSettings = webView.getSettings();
      if (localWebSettings != null)
      {
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setSupportZoom(true);
        localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        localWebSettings.setCacheMode(2);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
      }
      WebViewPlayer.getInstance().setbackPolicy(null);
      webView.addJavascriptInterface(WebViewPlayer.getInstance(), "QTJsPlayer");
      webView.addJavascriptInterface(WebFunc.getInstance(), "QTJsLocal");
      webView.setWebChromeClient(new WebChromeClient()
      {
        public boolean onJsAlert(WebView paramAnonymousWebView, String paramAnonymousString1, String paramAnonymousString2, JsResult paramAnonymousJsResult)
        {
          AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramAnonymousWebView.getContext());
          localBuilder.setTitle("蜻蜓提示").setMessage(paramAnonymousString2).setPositiveButton("确定", null);
          localBuilder.setCancelable(false);
          localBuilder.create().show();
          paramAnonymousJsResult.confirm();
          return true;
        }

        public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
        {
          if (paramAnonymousInt > 70)
            LocalWebView.this.removeLoading();
        }
      });
      webView.setHorizontalScrollBarEnabled(false);
      webView.setVerticalScrollBarEnabled(false);
      webView.setWebViewClient(this.webViewClient);
      if (getUrl().startsWith("http"))
      {
        if (this.withParam)
          buildUrl();
        webView.loadUrl(getUrl());
      }
      while (true)
      {
        webView.setDownloadListener(new DownloadListener()
        {
          public void onDownloadStart(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4, long paramAnonymousLong)
          {
            Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramAnonymousString1));
            LocalWebView.this.getContext().startActivity(localIntent);
          }
        });
        WebViewPlayer.getInstance().setWebview(webView);
        WebFunc.getInstance().setWebview(webView);
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
        addView(webView, localLayoutParams);
        return;
        String str = getFromAssets("page/" + getUrl());
        webView.loadData(str, "text/html; charset=UTF-8", null);
      }
    }
    catch (Exception localException)
    {
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    (paramInt3 - paramInt1);
    (paramInt4 - paramInt2);
    webView.layout(this.webviewLayout.getLeft(), this.webviewLayout.getTop(), this.webviewLayout.getRight(), this.webviewLayout.getBottom());
    this.mLoadingView.layout(0, 0, this.standardLayout.width, this.standardLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.webviewLayout.scaleToBounds(this.standardLayout);
    webView.measure(this.webviewLayout.getWidthMeasureSpec(), this.webviewLayout.getHeightMeasureSpec());
    this.mLoadingView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height, 1073741824));
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  protected void onViewWillClose()
  {
  }

  public void update(String paramString, Object paramObject)
  {
  }

  public class ADWebAppInterface
  {
    Context mContext;

    ADWebAppInterface()
    {
    }

    public void onClickAppDownload()
    {
      UMengLogger.sendmessage(LocalWebView.this.getContext(), "adPageDownload", AdvertisementManage.getInstance().currentADKey);
    }

    public void onClickDraw()
    {
      UMengLogger.sendmessage(LocalWebView.this.getContext(), "adPageRequestCoupon", AdvertisementManage.getInstance().currentADKey);
    }

    public void onClickEmailSend()
    {
      Toast.makeText(LocalWebView.this.getContext(), "正在发送邮件", 1).show();
      UMengLogger.sendmessage(LocalWebView.this.getContext(), "adPageSendEmail", AdvertisementManage.getInstance().currentADKey);
    }

    public void onPageLoaded()
    {
      UMengLogger.sendmessage(LocalWebView.this.getContext(), "adPageDisplay", AdvertisementManage.getInstance().currentADKey);
    }
  }

  private class qtWebViewClient extends WebViewClient
  {
    private qtWebViewClient()
    {
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      if (((paramString != null) && ((paramString.startsWith("rtsp")) || (paramString.startsWith("mms")) || (paramString.endsWith(".mp3")) || (paramString.endsWith(".apk")))) || (paramString.contains("active.coupon.360buy.com")))
      {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
        paramWebView.getContext().startActivity(localIntent);
        return true;
      }
      return false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.webview.LocalWebView
 * JD-Core Version:    0.6.2
 */