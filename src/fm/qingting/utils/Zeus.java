package fm.qingting.utils;

import android.content.Context;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.QTLocation;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Zeus
{
  private static final String AndroidUA = "QingTing Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22;) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
  private static Zeus _instance;
  private static WebView webView = null;
  private Context mContext;
  private int mZeusIndex = 0;
  private List<Integer> mZeusLstPercents;
  private List<String> mZeusLstUrls;
  private qtWebViewClient webViewClient = new qtWebViewClient(null);

  public static Zeus getInstance()
  {
    if (_instance == null)
      _instance = new Zeus();
    return _instance;
  }

  private void initWebView()
  {
    if ((this.mContext == null) || (webView != null))
      return;
    webView = new WebView(this.mContext);
    WebSettings localWebSettings = webView.getSettings();
    if (localWebSettings != null)
    {
      localWebSettings.setJavaScriptEnabled(true);
      localWebSettings.setUserAgentString("QingTing Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22;) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
      localWebSettings.setCacheMode(2);
      localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }
    webView.setWebChromeClient(new WebChromeClient()
    {
      public boolean onJsAlert(WebView paramAnonymousWebView, String paramAnonymousString1, String paramAnonymousString2, JsResult paramAnonymousJsResult)
      {
        return true;
      }
    });
    webView.setHorizontalScrollBarEnabled(false);
    webView.setVerticalScrollBarEnabled(false);
    webView.setWebViewClient(this.webViewClient);
  }

  private void log(String paramString)
  {
  }

  private String macroReplace(String paramString)
  {
    String str1 = "";
    try
    {
      if (InfoManager.getInstance().getCurrentLocation() != null)
        str1 = InfoManager.getInstance().getCurrentLocation().ip;
      String str2 = URLEncoder.encode("蜻蜓FM", "UTF-8");
      String str3 = DeviceInfo.getDeviceIMEI(InfoManager.getInstance().getContext());
      String str4 = paramString.replace("__OS__", "0").replace("__IP__", str1).replace("__APP__", str2).replace("__IMEI__", md5(str3));
      return str4;
    }
    catch (Exception localException)
    {
    }
    return paramString;
  }

  private String md5(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i < arrayOfByte.length; i++)
        localStringBuffer.append(Integer.toHexString(0xFF & arrayOfByte[i]));
      String str = localStringBuffer.toString();
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    return "";
  }

  public void init(Context paramContext)
  {
    if (paramContext == null)
      return;
    this.mContext = paramContext;
  }

  public void setZeusPercent(String paramString)
  {
    String[] arrayOfString;
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")) && (!paramString.equalsIgnoreCase("#")))
    {
      this.mZeusLstPercents = new ArrayList();
      arrayOfString = paramString.split(";;");
      if (arrayOfString != null)
        break label45;
    }
    while (true)
    {
      return;
      label45: for (int i = 0; i < arrayOfString.length; i++)
        this.mZeusLstPercents.add(Integer.valueOf(arrayOfString[i]));
    }
  }

  public void setZeusUrl(String paramString)
  {
    String[] arrayOfString;
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")) && (!paramString.equalsIgnoreCase("#")))
    {
      this.mZeusLstUrls = new ArrayList();
      arrayOfString = paramString.split(";;");
      if (arrayOfString != null)
        break label45;
    }
    while (true)
    {
      return;
      label45: for (int i = 0; i < arrayOfString.length; i++)
        this.mZeusLstUrls.add(arrayOfString[i]);
    }
  }

  public void startZeus()
  {
    if ((this.mZeusLstUrls != null) && (InfoManager.getInstance().hasWifi()))
    {
      if (this.mZeusIndex >= this.mZeusLstUrls.size())
        break label177;
      initWebView();
      if (webView != null)
      {
        localList = this.mZeusLstPercents;
        bool = false;
        if (localList != null)
        {
          i = this.mZeusIndex;
          j = this.mZeusLstPercents.size();
          bool = false;
          if (i < j)
            bool = RangeRandom.random(((Integer)this.mZeusLstPercents.get(this.mZeusIndex)).intValue() / 100.0D);
        }
        if (bool)
        {
          str = macroReplace((String)this.mZeusLstUrls.get(this.mZeusIndex));
          webView.loadUrl(str);
          QTMSGManage.getInstance().sendStatistcsMessage("zeus", (String)this.mZeusLstUrls.get(this.mZeusIndex));
        }
        this.mZeusIndex = (1 + this.mZeusIndex);
      }
    }
    label177: 
    while (webView == null)
    {
      List localList;
      boolean bool;
      int i;
      int j;
      String str;
      return;
    }
    try
    {
      webView.removeAllViews();
      webView.destroy();
      webView = null;
      return;
    }
    catch (Exception localException)
    {
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

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      return false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.Zeus
 * JD-Core Version:    0.6.2
 */