package fm.qingting.qtradio.view.groupselect;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
import fm.qingting.qtradio.view.MiniPlayerView;
import fm.qingting.qtradio.webview.WebViewFunc;
import fm.qingting.utils.AppInfo;

public class GroupWebView extends ViewGroupViewImpl
{
  public static final String AndroidUA = "Android-QingtingFM Mozilla/5.0 (Linux; U; Android 4.4.0; zh-cn; MB200 Build/GRJ22;) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
  private String URL = "http://qingting.fm";
  private boolean hasRemoved = false;
  private Context mContext;
  private LoadingView mLoadingView;
  private MiniPlayerView mMiniView;
  private final ViewLayout miniLayout = this.standardLayout.createChildLT(720, 110, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private boolean needMiniPlayer = false;
  private ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private MyWebView webView = null;
  private qtWebViewClient webViewClient = new qtWebViewClient(null);
  private final ViewLayout webviewLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL | ViewLayout.SLTR | ViewLayout.SC | ViewLayout.CW | ViewLayout.SVH);

  public GroupWebView(Context paramContext, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.needMiniPlayer = paramBoolean2;
    this.URL = paramString;
    if (paramBoolean1)
      buildUrl();
    init();
    this.mLoadingView = new LoadingView(paramContext);
    addView(this.mLoadingView);
    if (this.needMiniPlayer)
    {
      this.mMiniView = new MiniPlayerView(paramContext);
      addView(this.mMiniView);
    }
  }

  private void log(String paramString)
  {
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
    if (this.webView != null)
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
    if (this.webView != null)
    {
      String str = WebViewPlayer.getInstance().getBackPolicy();
      if ((str != null) && (!str.equalsIgnoreCase("")))
      {
        this.webView.loadUrl(str);
        WebViewPlayer.getInstance().setbackPolicy(null);
      }
    }
    else
    {
      return;
    }
    this.webView.goBack();
  }

  @TargetApi(18)
  protected void init()
  {
    try
    {
      this.webView = new MyWebView(this.mContext);
      WebSettings localWebSettings = this.webView.getSettings();
      if (localWebSettings != null)
      {
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setUserAgentString("Android-QingtingFM Mozilla/5.0 (Linux; U; Android 4.4.0; zh-cn; MB200 Build/GRJ22;) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        localWebSettings.setSupportZoom(true);
        localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        localWebSettings.setCacheMode(2);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
      }
      WebViewPlayer.getInstance().setbackPolicy(null);
      this.webView.addJavascriptInterface(WebViewPlayer.getInstance(), "QTJsPlayer");
      this.webView.addJavascriptInterface(WebViewFunc.getInstance(), "QTJsReserve");
      this.webView.setWebChromeClient(new WebChromeClient()
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
            GroupWebView.this.removeLoading();
        }

        public void onReceivedTitle(WebView paramAnonymousWebView, String paramAnonymousString)
        {
        }
      });
      this.webView.setHorizontalScrollBarEnabled(false);
      this.webView.setVerticalScrollBarEnabled(false);
      this.webView.setWebViewClient(this.webViewClient);
      this.webView.loadUrl(getUrl());
      this.webView.setDownloadListener(new DownloadListener()
      {
        public void onDownloadStart(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4, long paramAnonymousLong)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramAnonymousString1));
          GroupWebView.this.getContext().startActivity(localIntent);
        }
      });
      WebViewPlayer.getInstance().setWebview(this.webView);
      WebViewFunc.getInstance().setWebview(this.webView);
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
      addView(this.webView, localLayoutParams);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    (paramInt3 - paramInt1);
    (paramInt4 - paramInt2);
    if (this.needMiniPlayer)
      this.mMiniView.layout(0, this.standardLayout.height - this.miniLayout.height, this.standardLayout.width, this.standardLayout.height);
    for (int i = this.miniLayout.height; ; i = 0)
    {
      this.webView.layout(this.webviewLayout.getLeft(), this.webviewLayout.getTop(), this.webviewLayout.getRight(), this.webviewLayout.getBottom() - i);
      this.mLoadingView.layout(0, 0, this.standardLayout.width, this.standardLayout.height);
      return;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.webviewLayout.scaleToBounds(this.standardLayout);
    boolean bool = this.needMiniPlayer;
    int k = 0;
    if (bool)
    {
      this.miniLayout.scaleToBounds(this.standardLayout);
      this.miniLayout.measureView(this.mMiniView);
      k = this.miniLayout.height;
    }
    this.webView.measure(this.webviewLayout.getWidthMeasureSpec(), this.webviewLayout.getHeightMeasureSpec() - k);
    this.mLoadingView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - k, 1073741824));
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  protected void onViewWillClose()
  {
  }

  public void release()
  {
    if (this.webView != null)
    {
      this.webView.removeAllViews();
      this.webView.destroy();
      this.webView = null;
    }
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
      UMengLogger.sendmessage(GroupWebView.this.getContext(), "adPageDownload", AdvertisementManage.getInstance().currentADKey);
    }

    public void onClickDraw()
    {
      UMengLogger.sendmessage(GroupWebView.this.getContext(), "adPageRequestCoupon", AdvertisementManage.getInstance().currentADKey);
    }

    public void onClickEmailSend()
    {
      Toast.makeText(GroupWebView.this.getContext(), "正在发送邮件", 1).show();
      UMengLogger.sendmessage(GroupWebView.this.getContext(), "adPageSendEmail", AdvertisementManage.getInstance().currentADKey);
    }

    public void onPageLoaded()
    {
      UMengLogger.sendmessage(GroupWebView.this.getContext(), "adPageDisplay", AdvertisementManage.getInstance().currentADKey);
    }
  }

  private class MyWebView extends WebView
  {
    public MyWebView(Context arg2)
    {
      super();
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      Log.e("groupwebview", "" + WebViewPlayer.getInstance().mPreventParentTouch);
      boolean bool = super.onTouchEvent(paramMotionEvent);
      if (WebViewPlayer.getInstance().mPreventParentTouch)
        switch (paramMotionEvent.getAction())
        {
        default:
        case 2:
        case 1:
        case 3:
        }
      while (true)
      {
        return bool;
        requestDisallowInterceptTouchEvent(true);
        return true;
        requestDisallowInterceptTouchEvent(false);
        WebViewPlayer.getInstance().mPreventParentTouch = false;
      }
    }
  }

  private class qtWebViewClient extends WebViewClient
  {
    private qtWebViewClient()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
    }

    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      if (paramString2 != null)
      {
        String str = GroupWebView.this.getUrl();
        if ((str != null) && (str.equalsIgnoreCase(paramString2)))
          paramWebView.loadUrl("http://wx.qingting.fm");
      }
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      if (paramString.startsWith("tel:"))
      {
        Intent localIntent1 = new Intent("android.intent.action.DIAL", Uri.parse(paramString));
        GroupWebView.this.getContext().startActivity(localIntent1);
        return true;
      }
      if (((paramString != null) && ((paramString.startsWith("rtsp")) || (paramString.startsWith("mms")) || (paramString.endsWith(".mp3")) || (paramString.endsWith(".apk")))) || (paramString.contains("active.coupon.360buy.com")))
      {
        Intent localIntent2 = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
        paramWebView.getContext().startActivity(localIntent2);
        return true;
      }
      return false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.groupselect.GroupWebView
 * JD-Core Version:    0.6.2
 */