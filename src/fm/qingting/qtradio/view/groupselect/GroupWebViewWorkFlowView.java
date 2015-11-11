package fm.qingting.qtradio.view.groupselect;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout.LayoutParams;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;

public class GroupWebViewWorkFlowView extends ViewGroupViewImpl
{
  private static final String UserAgent = "Android-QingtingFM";
  private static WebView webView = null;
  private boolean isFirstOpen = true;
  private Context mContext;
  private String mUrl = "http://qingting.fm";
  private ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private int viewHeight = 0;
  private int viewWidth = 0;
  private qtWebViewClient webViewClient = new qtWebViewClient(null);
  private final ViewLayout webviewLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL | ViewLayout.SLTR | ViewLayout.SC | ViewLayout.CW | ViewLayout.SVH);

  public GroupWebViewWorkFlowView(Context paramContext, String paramString)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mUrl = paramString;
    init();
  }

  protected void init()
  {
    try
    {
      webView = new WebView(this.mContext);
      WebSettings localWebSettings = webView.getSettings();
      if (localWebSettings != null)
      {
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setUserAgentString("Android-QingtingFM");
        localWebSettings.setSupportZoom(true);
        localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        localWebSettings.setCacheMode(-1);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
      }
      webView.setHorizontalScrollBarEnabled(false);
      webView.setVerticalScrollBarEnabled(false);
      webView.setWebViewClient(this.webViewClient);
      webView.loadUrl(this.mUrl);
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
      addView(webView, localLayoutParams);
      return;
    }
    catch (Exception localException)
    {
    }
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

  protected void onViewWillClose()
  {
  }

  public void update(String paramString, Object paramObject)
  {
  }

  private class qtWebViewClient extends WebViewClient
  {
    private qtWebViewClient()
    {
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      return false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.groupselect.GroupWebViewWorkFlowView
 * JD-Core Version:    0.6.2
 */