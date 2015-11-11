package fm.qingting.utils;

import android.content.Context;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cn.com.mma.mobile.tracking.api.Countly;
import com.miaozhen.mzmonitor.MZMonitor;
import fm.qingting.qtradio.ad.AdConfig;
import fm.qingting.qtradio.jd.data.CommodityInfo;
import fm.qingting.qtradio.jd.data.JDApi;
import fm.qingting.qtradio.jd.data.JDApi.ErrorCode;
import fm.qingting.qtradio.jd.data.JDApi.OnResponseListener;
import fm.qingting.qtradio.jd.data.Response;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.AdvertisementInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.QTADLocation;
import fm.qingting.qtradio.model.QTLocation;
import fm.qingting.qtradio.model.RootNode;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ThirdTracker
{
  public static final String AndroidUA = "QingTing Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22;) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
  private static ThirdTracker _instance;
  private static WebView webView = null;
  private int INTERVAL = 10;
  private long changeRecvJdTime = 0L;
  private int mADIndex = 0;
  private String mADRegions;
  private int mAMIndex = 0;
  private Context mContext;
  private int mJDIndex = 0;
  private int mJDSeed = 1;
  private List<Integer> mLstADPercents;
  private List<String> mLstADUrls;
  private List<CommodityInfo> mLstJDAdv = null;
  private List<Integer> mLstMZPercents;
  private List<String> mLstMZUrls;
  private int mMZIndex = 0;
  private qtWebViewClient webViewClient = new qtWebViewClient(null);

  public static ThirdTracker getInstance()
  {
    if (_instance == null)
      _instance = new ThirdTracker();
    return _instance;
  }

  private boolean inRegion(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equalsIgnoreCase("CN"));
      label61: String str;
      do
      {
        QTADLocation localQTADLocation;
        do
        {
          return true;
          if (!InfoManager.getInstance().enableADVLocation())
            break label61;
          localQTADLocation = InfoManager.getInstance().getADVLocation();
          if (localQTADLocation == null)
            break;
          if (localQTADLocation.regionCode == null)
            break label61;
        }
        while (paramString.contains(localQTADLocation.regionCode));
        return false;
        return false;
        QTLocation localQTLocation = InfoManager.getInstance().getCurrentLocation();
        if (localQTLocation == null)
          break;
        str = localQTLocation.getRegionCode();
      }
      while ((str != null) && (paramString.contains(str)));
    }
    return false;
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

  private boolean isInRegion()
  {
    if ((this.mADRegions != null) && (!this.mADRegions.equalsIgnoreCase("")) && (!this.mADRegions.equalsIgnoreCase("#")))
    {
      if (this.mADRegions.equalsIgnoreCase("all"));
      String str;
      do
      {
        return true;
        str = InfoManager.getInstance().getCurrentRegion();
        if (str == null)
          return false;
      }
      while (this.mADRegions.contains(str));
    }
    return false;
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

  private void sendLog(AdConfig paramAdConfig)
  {
    if (paramAdConfig != null);
  }

  public void changeJD()
  {
    if (InfoManager.getInstance().hasWifi())
    {
      if ((InfoManager.getInstance().enableJDCity()) && (this.mJDSeed > 0) && (this.mLstJDAdv != null))
      {
        if (this.mJDIndex < this.mLstJDAdv.size())
          return;
        this.changeRecvJdTime = (1L + (System.currentTimeMillis() / 1000L + RangeRandom.Random(this.INTERVAL)));
        this.mJDSeed = (-1 + this.mJDSeed);
        JDApi.request(new JDApi.OnResponseListener()
        {
          public void onResponse(Response paramAnonymousResponse)
          {
            if (paramAnonymousResponse.getErrorCode() == JDApi.ErrorCode.SUCCESS)
            {
              ThirdTracker.access$102(ThirdTracker.this, (ArrayList)paramAnonymousResponse.getData());
              ThirdTracker.access$202(ThirdTracker.this, 0);
              InfoManager.getInstance().root().setInfoUpdate(12);
            }
          }
        });
        return;
      }
      this.changeRecvJdTime = 0L;
      return;
    }
    this.changeRecvJdTime = 0L;
  }

  public List<CommodityInfo> getJDAdv()
  {
    return this.mLstJDAdv;
  }

  public long getJDAdvTime()
  {
    return this.changeRecvJdTime;
  }

  public void init(Context paramContext)
  {
    if (paramContext == null)
      return;
    this.mContext = paramContext;
  }

  public void setADPercent(String paramString)
  {
    String[] arrayOfString;
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")) && (!paramString.equalsIgnoreCase("#")))
    {
      this.mLstADPercents = new ArrayList();
      arrayOfString = paramString.split(";;");
      if (arrayOfString != null)
        break label46;
    }
    while (true)
    {
      return;
      label46: for (int i = 0; i < arrayOfString.length; i++)
        this.mLstADPercents.add(Integer.valueOf(arrayOfString[i]));
    }
  }

  public void setADUrl(String paramString)
  {
    String[] arrayOfString;
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")) && (!paramString.equalsIgnoreCase("#")))
    {
      this.mLstADUrls = new ArrayList();
      arrayOfString = paramString.split(";;");
      if (arrayOfString != null)
        break label46;
    }
    while (true)
    {
      return;
      label46: for (int i = 0; i < arrayOfString.length; i++)
        this.mLstADUrls.add(arrayOfString[i]);
    }
  }

  public void setJDAdv(List<CommodityInfo> paramList)
  {
    this.changeRecvJdTime = (1L + RangeRandom.Random(this.INTERVAL) + System.currentTimeMillis() / 1000L);
    this.mLstJDAdv = paramList;
  }

  public void setJDSeed(int paramInt)
  {
    this.mJDSeed = paramInt;
  }

  public void setMZPercent(String paramString)
  {
    String[] arrayOfString;
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")) && (!paramString.equalsIgnoreCase("#")))
    {
      this.mLstMZPercents = new ArrayList();
      arrayOfString = paramString.split(";;");
      if (arrayOfString != null)
        break label46;
    }
    while (true)
    {
      return;
      label46: for (int i = 0; i < arrayOfString.length; i++)
        this.mLstMZPercents.add(Integer.valueOf(arrayOfString[i]));
    }
  }

  public void setMZUrl(String paramString)
  {
    String[] arrayOfString;
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")) && (!paramString.equalsIgnoreCase("#")))
    {
      this.mLstMZUrls = new ArrayList();
      arrayOfString = paramString.split(";;");
      if (arrayOfString != null)
        break label46;
    }
    while (true)
    {
      return;
      label46: for (int i = 0; i < arrayOfString.length; i++)
        this.mLstMZUrls.add(arrayOfString[i]);
    }
  }

  public void setThirdAdv(ActivityNode paramActivityNode)
  {
    int i;
    int j;
    int k;
    try
    {
      if ((InfoManager.getInstance().hasWifi()) && (paramActivityNode != null) && (InfoManager.getInstance().enableAirWave()) && (InfoManager.getInstance().enableAirWaveCity()))
      {
        initWebView();
        if (webView != null)
        {
          i = InfoManager.getInstance().getAirWaveShow();
          if ((paramActivityNode.imageTracking != null) && (i > 0))
          {
            j = 0;
            break label274;
            while (k < paramActivityNode.imageTracking.size())
            {
              webView.loadUrl((String)paramActivityNode.imageTracking.get(k));
              k++;
            }
            label105: QTMSGManage.getInstance().sendStatistcsMessage("ThirdAdv", "image:" + paramActivityNode.contentUrl);
          }
          if (((paramActivityNode.clickTracking != null) || (paramActivityNode.contentUrl != null)) && (RangeRandom.random(InfoManager.getInstance().getAirWaveClick() / 1000.0D)))
          {
            List localList = paramActivityNode.clickTracking;
            int m = 0;
            if (localList != null)
              while (m < paramActivityNode.clickTracking.size())
              {
                webView.loadUrl((String)paramActivityNode.clickTracking.get(m));
                m++;
              }
            if (paramActivityNode.contentUrl != null)
              webView.loadUrl(paramActivityNode.contentUrl);
            QTMSGManage.getInstance().sendStatistcsMessage("ThirdAdv", "click" + paramActivityNode.contentUrl);
          }
        }
      }
      return;
    }
    catch (Exception localException)
    {
      return;
    }
    while (true)
    {
      label274: if (j >= i)
        break label105;
      k = 0;
      break;
      j++;
    }
  }

  public void setTrackerRegions(String paramString)
  {
    this.mADRegions = paramString;
  }

  public void start()
  {
    if (isInRegion())
    {
      startAD();
      startMZ();
    }
  }

  public void startAD()
  {
    if ((this.mLstADUrls != null) && (this.mLstADPercents != null) && (InfoManager.getInstance().hasWifi()) && (this.mADIndex < this.mLstADUrls.size()))
    {
      List localList = this.mLstADPercents;
      boolean bool = false;
      if (localList != null)
      {
        int i = this.mADIndex;
        int j = this.mLstADPercents.size();
        bool = false;
        if (i < j)
          bool = RangeRandom.random(((Integer)this.mLstADPercents.get(this.mADIndex)).intValue() / 100.0D);
      }
      if ((bool) && (!((String)this.mLstADUrls.get(this.mADIndex)).equalsIgnoreCase("#")))
      {
        if (this.mADIndex % 2 != 0)
          break label216;
        Countly.sharedInstance().onExpose((String)this.mLstADUrls.get(this.mADIndex));
      }
    }
    while (true)
    {
      QTMSGManage.getInstance().sendStatistcsMessage("ZeusTracker", "ad:" + (String)this.mLstADUrls.get(this.mADIndex));
      this.mADIndex = (1 + this.mADIndex);
      return;
      label216: Countly.sharedInstance().onClick((String)this.mLstADUrls.get(this.mADIndex));
    }
  }

  public void startAM()
  {
    if ((!InfoManager.getInstance().hasWifi()) || ((InfoManager.getInstance().enableADVLocation()) && (InfoManager.getInstance().getADVLocation() == null)))
      return;
    List localList = InfoManager.getInstance().root().mAdvertisementInfoNode.getADAMConfigs();
    int i;
    boolean bool;
    if ((localList != null) && (this.mAMIndex < localList.size()))
      if (inRegion(((AdConfig)localList.get(this.mAMIndex)).mRegions))
      {
        i = ((AdConfig)localList.get(this.mAMIndex)).percent;
        bool = RangeRandom.random(i / 1000.0D);
        if (i <= 1000)
          break label573;
      }
    label526: label573: for (int j = i / 1000; ; j = 1)
    {
      String str1 = ((AdConfig)localList.get(this.mAMIndex)).mEventType;
      String str2 = ((AdConfig)localList.get(this.mAMIndex)).mAdMasterUrl;
      String str3 = ((AdConfig)localList.get(this.mAMIndex)).mMiaozhenUrl;
      String str4 = ((AdConfig)localList.get(this.mAMIndex)).mCustomerUrl;
      String str5 = ((AdConfig)localList.get(this.mAMIndex)).mMMAUrl;
      if (bool)
      {
        int k = 0;
        if (k < j)
        {
          if ((str2 != null) && (!str2.equalsIgnoreCase("")))
          {
            if ((str1 == null) || (!str1.equalsIgnoreCase("click")))
              break label526;
            Countly.sharedInstance().onClick(str2);
          }
          while (true)
          {
            QTMSGManage.getInstance().sendStatistcsMessage("ThirdTracker1", "ad:" + str2);
            if ((str3 != null) && (!str3.equalsIgnoreCase("")))
            {
              String str8 = macroReplace(str3);
              MZMonitor.adTrack(InfoManager.getInstance().getContext(), str8);
              QTMSGManage.getInstance().sendStatistcsMessage("ThirdTracker", "mz:" + str3);
            }
            if ((str4 != null) && (!str4.equalsIgnoreCase("")))
            {
              initWebView();
              if (webView != null)
              {
                String str7 = macroReplace(str4);
                webView.loadUrl(str7);
                QTMSGManage.getInstance().sendStatistcsMessage("ThirdTracker", "zs:" + str4);
              }
            }
            if ((str5 != null) && (!str5.equalsIgnoreCase("")))
            {
              initWebView();
              if (webView != null)
              {
                String str6 = macroReplace(str5);
                webView.loadUrl(str6);
                QTMSGManage.getInstance().sendStatistcsMessage("ThirdTracker", "zs:" + str5);
              }
            }
            sendLog((AdConfig)localList.get(this.mAMIndex));
            k++;
            break;
            Countly.sharedInstance().onExpose(str2);
          }
        }
      }
      this.mAMIndex = (1 + this.mAMIndex);
      return;
      if (webView == null)
        break;
      try
      {
        webView.removeAllViews();
        webView.destroy();
        webView = null;
        return;
      }
      catch (Exception localException)
      {
        return;
      }
    }
  }

  public void startMZ()
  {
    if ((this.mLstMZUrls != null) && (this.mLstMZPercents != null) && (InfoManager.getInstance().hasWifi()) && (this.mMZIndex < this.mLstMZUrls.size()))
    {
      List localList = this.mLstMZPercents;
      boolean bool = false;
      if (localList != null)
      {
        int i = this.mMZIndex;
        int j = this.mLstMZPercents.size();
        bool = false;
        if (i < j)
          bool = RangeRandom.random(((Integer)this.mLstMZPercents.get(this.mMZIndex)).intValue() / 100.0D);
      }
      if (bool)
      {
        String str = macroReplace((String)this.mLstMZUrls.get(this.mMZIndex));
        MZMonitor.adTrack(InfoManager.getInstance().getContext(), str);
        QTMSGManage.getInstance().sendStatistcsMessage("ZeusTracker", "mz:" + (String)this.mLstMZUrls.get(this.mMZIndex));
      }
      this.mMZIndex = (1 + this.mMZIndex);
    }
  }

  public void trackJD()
  {
    if ((InfoManager.getInstance().hasWifi()) && (this.mLstJDAdv != null) && (this.mLstJDAdv.size() > 0) && (this.mJDIndex < this.mLstJDAdv.size()) && (InfoManager.getInstance().enableJDCity()))
    {
      int i = InfoManager.getInstance().getJDShow();
      for (int j = 0; j < i; j++)
      {
        JDApi.feedback(null, ((CommodityInfo)this.mLstJDAdv.get(this.mJDIndex)).getTrackUrl());
        QTMSGManage.getInstance().sendStatistcsMessage("jdimpression");
      }
      if (RangeRandom.random(InfoManager.getInstance().getJDClick() / 1000.0D))
      {
        JDApi.feedback(null, ((CommodityInfo)this.mLstJDAdv.get(this.mJDIndex)).getShopUrl());
        QTMSGManage.getInstance().sendStatistcsMessage("jdclick");
      }
      this.mJDIndex = (1 + this.mJDIndex);
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
 * Qualified Name:     fm.qingting.utils.ThirdTracker
 * JD-Core Version:    0.6.2
 */