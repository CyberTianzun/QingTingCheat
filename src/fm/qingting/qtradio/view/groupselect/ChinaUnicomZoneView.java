package fm.qingting.qtradio.view.groupselect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View.MeasureSpec;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;

@SuppressLint({"SetJavaScriptEnabled"})
public class ChinaUnicomZoneView extends ViewGroupViewImpl
{
  private static final String URL = "http://iread.wo.com.cn/st/womediawap/qingting/";
  private static WebView webView;
  private ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private final ViewLayout webviewLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL | ViewLayout.SLTR | ViewLayout.SC | ViewLayout.CW | ViewLayout.SVH);

  @SuppressLint({"JavascriptInterface"})
  public ChinaUnicomZoneView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(17170443);
    webView = new WebView(paramContext);
    webView.setWebChromeClient(new WebChromeClient()
    {
    });
    webView.setWebViewClient(new WebViewClient()
    {
    });
    WebSettings localWebSettings = webView.getSettings();
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    localWebSettings.setAppCacheEnabled(false);
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setPluginState(WebSettings.PluginState.ON);
    localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    webView.addJavascriptInterface(new QTPlayerJavascriptInterface(), "QTPlayer");
    webView.loadUrl("http://iread.wo.com.cn/st/womediawap/qingting/");
    addView(webView);
  }

  public void goBack()
  {
    webView.loadUrl("javascript:goBack()");
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 - paramInt1;
    int j = paramInt4 - paramInt2;
    this.standardLayout.scaleToBounds(i, j);
    this.webviewLayout.scaleToBounds(this.standardLayout);
    webView.layout(this.webviewLayout.getLeft(), this.webviewLayout.getTop(), this.webviewLayout.getRight(), this.webviewLayout.getBottom());
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    webView.measure(this.webviewLayout.getWidthMeasureSpec(), this.webviewLayout.getHeightMeasureSpec());
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.groupselect.ChinaUnicomZoneView
 * JD-Core Version:    0.6.2
 */