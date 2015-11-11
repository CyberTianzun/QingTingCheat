package fm.qingting.qtradio.fm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.helper.PodcasterHelper;
import fm.qingting.qtradio.helper.ProgramHelper;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.ProgramScheduleList;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.share.ShareUtil;
import fm.qingting.social.SocialEventListener;

public class WebViewPlayer
{
  private static WebViewPlayer _instance;
  private final int REDIRECT_CHANNEL = 0;
  private final int REDIRECT_DOWNLOAD = 5;
  private final int REDIRECT_IM_CHAT = 9;
  private final int REDIRECT_LISTCHANNEL_ATTR = 8;
  private final int REDIRECT_LIVECATEGORY = 3;
  private final int REDIRECT_NOVEL_CATEGORY = 7;
  private final int REDIRECT_PAGE = 1;
  private final int REDIRECT_PODCASTER = 4;
  private final int REDIRECT_RECOMMENDCATEGORY = 6;
  private final int REDIRECT_RESET_FILTER = 11;
  private final int REDIRECT_SPEICAL_TOPIC = 10;
  private final int REDIRECT_VIRTUALCATEGORY = 2;
  private String _attrs = null;
  private String _callBack;
  private String _callBackJs;
  private String _callBackParams;
  private String _catid;
  private String _channelid;
  private int _channeltype;
  private String _cname;
  private String _desc;
  private boolean _enterchat;
  private int _groupId = 0;
  private String _pageTitle = "";
  private String _pageUrl = null;
  private int _playSource = -1;
  private int _podcasterId = 0;
  private String _programid;
  private int _sectionId = 0;
  private String _shareContent = "";
  private String _shareImage = null;
  private String _shareTitle = "";
  private String _shareUrl = "";
  private String _source;
  private String _thumb;
  private int _topicId = 0;
  private WebView _webview;
  private final Handler addPlaySourceHandler = new Handler();
  private Runnable addPlaySourceRunnable = new Runnable()
  {
    public void run()
    {
      WebViewPlayer.this.addPlaySource();
    }
  };
  private String backUrl = null;
  private final Handler callbackHandler = new Handler();
  private Runnable callbackRunnable = new Runnable()
  {
    public void run()
    {
      WebViewPlayer.this.doCallback();
    }
  };
  private Context mContext;
  public boolean mPreventParentTouch = false;
  private final Handler playHandler = new Handler();
  private Runnable playRunnable = new Runnable()
  {
    public void run()
    {
      WebViewPlayer.this.doPlay();
    }
  };
  private final Handler redirectHandler = new Handler();
  private Runnable redirectRunnable = new Runnable()
  {
    public void run()
    {
      WebViewPlayer.this.doRedirect();
    }
  };
  private int redirectType = 0;
  private String shareCallback = null;
  SocialEventListener shareCallbackListener = new SocialEventListener()
  {
    public void onCancel(Object paramAnonymousObject)
    {
    }

    public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
    {
      WebViewPlayer.this.invokeCallBack(null);
    }

    public void onException(Object paramAnonymousObject)
    {
    }
  };
  private String shareCallbackParam = null;
  private final Handler shareHandler = new Handler();
  private Runnable shareRunnable = new Runnable()
  {
    public void run()
    {
      WebViewPlayer.this.doShare();
    }
  };
  private int shareType = 0;
  private final Handler stopHandler = new Handler();
  private Runnable stopRunnable = new Runnable()
  {
    public void run()
    {
      WebViewPlayer.this.doStop();
    }
  };
  private WechatReceiver wcReceiver;

  private void addPlaySource()
  {
    if (this._playSource != -1)
      PlayerAgent.getInstance().addPlaySource(this._playSource);
  }

  private void doCallback()
  {
    if (this.shareCallback == null)
      return;
    this._callBackJs = "javascript:";
    this._callBackJs += this.shareCallback;
    if (this.shareCallbackParam == null);
    for (this._callBackJs += "("; ; this._callBackJs = (this._callBackJs + "('" + this.shareCallbackParam + "'"))
    {
      this._callBackJs += ")";
      if ((this._webview == null) || (this._callBackJs == null))
        break;
      this._webview.loadUrl(this._callBackJs);
      return;
    }
  }

  private void doPlay()
  {
    if ((this._source != null) && (!this._source.equalsIgnoreCase("")))
    {
      PlayerAgent.getInstance().stop();
      PlayerAgent.getInstance().play(this._source);
    }
    ProgramNode localProgramNode;
    do
    {
      ProgramScheduleList localProgramScheduleList;
      do
      {
        do
          return;
        while ((this._channelid == null) || (this._channelid.equalsIgnoreCase("")));
        if (this._enterchat)
          InfoManager.getInstance().addChatRoom(this._channelid);
        if (this._programid == null)
          this._programid = "0";
        localProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(Integer.valueOf(this._channelid).intValue(), this._channeltype, false);
      }
      while (localProgramScheduleList == null);
      localProgramNode = localProgramScheduleList.getProgramNode(Integer.valueOf(this._programid).intValue());
    }
    while (localProgramNode == null);
    if (this._playSource != -1)
      PlayerAgent.getInstance().addPlaySource(this._playSource);
    PlayerAgent.getInstance().play(localProgramNode);
  }

  private void doRedirect()
  {
    try
    {
      if (this._playSource != 0)
        PlayerAgent.getInstance().addPlaySource(this._playSource);
      switch (this.redirectType)
      {
      case 0:
        if ((this._channelid != null) && (!this._channelid.equalsIgnoreCase("")))
        {
          ChannelNode localChannelNode = ChannelHelper.getInstance().getChannel(Integer.valueOf(this._channelid).intValue(), this._channeltype);
          if ((localChannelNode == null) && (this._catid != null) && (!this._catid.equalsIgnoreCase("")))
            localChannelNode = ChannelHelper.getInstance().getFakeVirtualChannel(Integer.valueOf(this._channelid).intValue(), Integer.valueOf(this._catid).intValue(), "专辑");
          if (localChannelNode != null)
            ControllerManager.getInstance().openChannelDetailController(localChannelNode, false);
          if ((this._programid != null) && (!this._programid.equalsIgnoreCase("")) && (this._catid != null) && (!this._catid.equalsIgnoreCase("")) && (this._channelid != null) && (!this._channelid.equalsIgnoreCase("")))
          {
            PlayerAgent.getInstance().playAndLoadData(Integer.valueOf(this._catid).intValue(), Integer.valueOf(this._channelid).intValue(), Integer.valueOf(this._programid).intValue(), this._channeltype, "专辑");
            return;
          }
        }
        break;
      case 1:
        doRedirectPage();
        return;
      case 2:
        doRedirectVirtualCateogry();
        return;
      case 3:
        doRedirectLiveCategory();
        return;
      case 4:
        doRedirectPodcaster();
        return;
      case 5:
        doRedirectDownload();
        return;
      case 6:
        doRedirectRecommendCategory();
        return;
      case 7:
        doRedirectNovelAllContentController();
        return;
      case 8:
        doRedirectChannelListByAttr();
        return;
      case 9:
        doRedirectIMChat();
        return;
      case 10:
        doRedirectSpecialTopic();
        return;
      case 11:
        doRedirectResetFilter();
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void doRedirectChannelListByAttr()
  {
    if ((this._catid != null) && (!this._catid.equalsIgnoreCase("")))
      ControllerManager.getInstance().openVirtualCategoryAllContentController(Integer.valueOf(this._catid).intValue(), this._sectionId);
  }

  private void doRedirectDownload()
  {
    if ((this._channelid != null) && (!this._channelid.equalsIgnoreCase("")))
    {
      ChannelNode localChannelNode = ChannelHelper.getInstance().getChannel(Integer.valueOf(this._channelid).intValue(), this._channeltype);
      if (localChannelNode != null)
        ControllerManager.getInstance().redirectToBatchDownloadView(localChannelNode, false, true);
    }
  }

  private void doRedirectIMChat()
  {
    if (this._groupId != 0)
      ControllerManager.getInstance().openImChatController(String.valueOf(this._groupId));
  }

  private void doRedirectLiveCategory()
  {
    if ((this._catid == null) || (this._catid.equalsIgnoreCase("")))
      return;
    ControllerManager.getInstance().openTraditionalChannelsView(Integer.valueOf(this._catid).intValue());
  }

  private void doRedirectNovelAllContentController()
  {
    if ((this._catid != null) && (!this._catid.equalsIgnoreCase("")))
      ControllerManager.getInstance().openNovelAllContentController(Integer.valueOf(this._catid).intValue());
  }

  private void doRedirectPage()
  {
    if ((this._pageUrl != null) && (!this._pageUrl.equalsIgnoreCase("")))
      ControllerManager.getInstance().redirectToActiviyByUrl(this._pageUrl, this._pageTitle, false);
  }

  private void doRedirectPodcaster()
  {
    if (this._podcasterId != 0)
    {
      UserInfo localUserInfo = PodcasterHelper.getInstance().getPodcaster(this._podcasterId);
      if (localUserInfo != null)
        ControllerManager.getInstance().openPodcasterInfoController(localUserInfo);
    }
  }

  private void doRedirectRecommendCategory()
  {
    ControllerManager.getInstance().openDiscoverCategoryController(this._sectionId);
  }

  private void doRedirectResetFilter()
  {
    ControllerManager.getInstance().openVirtualCategoryAllContentController(this._sectionId, this._attrs);
  }

  private void doRedirectSpecialTopic()
  {
    ControllerManager.getInstance().openSpecialTopicController(this._topicId);
  }

  private void doRedirectVirtualCateogry()
  {
    ControllerManager.getInstance().openVirtualCategoryAllContentController(this._sectionId);
  }

  private void doShare()
  {
    if (this._shareUrl != null)
    {
      ShareUtil.shareToPlatform(this._shareUrl, this._shareTitle, this._shareContent, this._shareImage, this.shareType, this.shareCallbackListener);
      return;
    }
    if ((this._channelid != null) && (!this._channelid.equalsIgnoreCase("")))
    {
      ChannelNode localChannelNode = ChannelHelper.getInstance().getChannel(Integer.valueOf(this._channelid).intValue(), this._channeltype);
      if (localChannelNode != null)
      {
        EventDispacthManager.getInstance().dispatchAction("shareChoose", localChannelNode);
        return;
      }
      EventDispacthManager.getInstance().dispatchAction("shareChoose", InfoManager.getInstance().root().getCurrentPlayingNode());
      return;
    }
    EventDispacthManager.getInstance().dispatchAction("shareChoose", InfoManager.getInstance().root().getCurrentPlayingNode());
  }

  private void doStop()
  {
    PlayerAgent.getInstance().stop();
  }

  public static WebViewPlayer getInstance()
  {
    try
    {
      if (_instance == null)
        _instance = new WebViewPlayer();
      WebViewPlayer localWebViewPlayer = _instance;
      return localWebViewPlayer;
    }
    finally
    {
    }
  }

  private void invokeCallBack(String paramString)
  {
    if (this._callBack == null)
      return;
    this._callBackJs = "javascript:";
    this._callBackJs += this._callBack;
    if (paramString == null);
    for (this._callBackJs += "(null"; ; this._callBackJs = (this._callBackJs + "('" + paramString + "'"))
    {
      this._callBackJs += ")";
      if ((this._webview == null) || (this._callBackJs == null))
        break;
      this._webview.loadUrl(this._callBackJs);
      return;
    }
  }

  private void log(String paramString)
  {
  }

  private void parseResInfo(String paramString)
  {
    while (true)
    {
      try
      {
        this._shareUrl = null;
        this._catid = null;
        this._channelid = null;
        this._programid = null;
        this._groupId = 0;
        this._sectionId = 0;
        this._podcasterId = 0;
        if (paramString == null)
          return;
        JSONObject localJSONObject = JSON.parseObject(paramString);
        this._catid = localJSONObject.getString("category_id");
        this._channelid = localJSONObject.getString("channel_id");
        this._channeltype = localJSONObject.getIntValue("channel_type");
        this._programid = localJSONObject.getString("program_id");
        this.redirectType = localJSONObject.getIntValue("redirect");
        this._cname = localJSONObject.getString("cname");
        this._thumb = localJSONObject.getString("thumb");
        this._desc = localJSONObject.getString("desc");
        this._source = localJSONObject.getString("source");
        this._playSource = localJSONObject.getIntValue("play_source");
        this._pageUrl = localJSONObject.getString("page_url");
        this._pageTitle = localJSONObject.getString("page_title");
        this._sectionId = localJSONObject.getIntValue("section_id");
        this._podcasterId = localJSONObject.getIntValue("podcaster_id");
        this._attrs = localJSONObject.getString("attribute_id");
        if (localJSONObject.getIntValue("enter_chat") == 1)
        {
          this._enterchat = true;
          this.shareType = localJSONObject.getIntValue("share");
          this._shareContent = localJSONObject.getString("shareContent");
          this._shareImage = localJSONObject.getString("shareImage");
          this._shareTitle = localJSONObject.getString("shareTitle");
          this._shareUrl = localJSONObject.getString("shareUrl");
          this._groupId = localJSONObject.getIntValue("group_id");
          this._topicId = localJSONObject.getIntValue("topic_id");
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      this._enterchat = false;
    }
  }

  @JavascriptInterface
  public void AddPlaySource(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1 != null);
    try
    {
      this._playSource = JSON.parseObject(paramString1).getIntValue("_playSource");
      label18: this.addPlaySourceHandler.postDelayed(this.addPlaySourceRunnable, 1L);
      return;
    }
    catch (Exception localException)
    {
      break label18;
    }
  }

  @JavascriptInterface
  public void Play(String paramString1, String paramString2, String paramString3)
  {
    this._callBack = paramString2;
    this._callBackParams = paramString3;
    parseResInfo(paramString1);
    this.playHandler.postDelayed(this.playRunnable, 1L);
  }

  @JavascriptInterface
  public void Redirect(String paramString1, String paramString2, String paramString3)
  {
    this._callBack = paramString2;
    this._callBackParams = paramString3;
    parseResInfo(paramString1);
    this.redirectHandler.postDelayed(this.redirectRunnable, 1L);
  }

  @JavascriptInterface
  public void RegisterCallback(String paramString1, String paramString2)
  {
    this.shareCallback = paramString1;
    this.shareCallbackParam = paramString2;
  }

  @JavascriptInterface
  public void Share(String paramString1, String paramString2, String paramString3)
  {
    this._callBack = paramString2;
    this._callBackParams = paramString3;
    parseResInfo(paramString1);
    this.shareHandler.postDelayed(this.shareRunnable, 1L);
  }

  @JavascriptInterface
  public void Stop()
  {
    this.stopHandler.postDelayed(this.stopRunnable, 1L);
  }

  public void callback()
  {
    this.callbackHandler.postDelayed(this.callbackRunnable, 1L);
  }

  public String getBackPolicy()
  {
    return this.backUrl;
  }

  public void init(Context paramContext)
  {
    if (paramContext == null)
      return;
    this.mContext = paramContext;
    this.wcReceiver = new WechatReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.WECHAT_RESP");
    this.mContext.registerReceiver(this.wcReceiver, localIntentFilter);
  }

  @JavascriptInterface
  public void preventParentTouchEvent()
  {
    this.mPreventParentTouch = true;
    this._webview.requestDisallowInterceptTouchEvent(true);
  }

  public void release()
  {
    try
    {
      if (this.wcReceiver != null)
      {
        this.mContext.unregisterReceiver(this.wcReceiver);
        this.wcReceiver = null;
      }
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
    }
  }

  @JavascriptInterface
  public void setShareInfo(String paramString)
  {
    parseResInfo(paramString);
  }

  public void setWebview(WebView paramWebView)
  {
    this._webview = paramWebView;
  }

  @JavascriptInterface
  public void setbackPolicy(String paramString)
  {
    this.backUrl = paramString;
  }

  class WechatReceiver extends BroadcastReceiver
  {
    WechatReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent == null);
      while (true)
      {
        return;
        try
        {
          if ((paramIntent.getAction().equalsIgnoreCase("android.intent.action.WECHAT_RESP")) && ((WebViewPlayer.this.shareType == 0) || (WebViewPlayer.this.shareType == 1)))
          {
            WebViewPlayer.this.invokeCallBack(null);
            return;
          }
        }
        catch (Exception localException)
        {
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.fm.WebViewPlayer
 * JD-Core Version:    0.6.2
 */