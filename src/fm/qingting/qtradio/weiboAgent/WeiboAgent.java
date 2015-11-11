package fm.qingting.qtradio.weiboAgent;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.IWeiboResultRecvHandler;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.model.WeiboRecordData;
import fm.qingting.qtradio.model.WeiboResultToken;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.social.CloudCenter.OnLoginEventListerner;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.social.SocialEventListener;
import fm.qingting.social.api.SinaWeiboApi;
import fm.qingting.social.auth.SinaWeiboAuth;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import weibo4android.Status;

public class WeiboAgent
  implements IEventHandler, IResultRecvHandler
{
  private static WeiboAgent instance;
  private WeiboResultToken CandFWRT;
  private final String SLOGAN_TEXT = "刚登录#蜻蜓FM# // 终于找到fm神器了，内容爆多，新闻，小说，音乐，相声，脱口秀……想听啥都有，关键还有3000多家电台24小时不间断直播，快去把它收了吧→http://qingting.fm（分享自@蜻蜓FM）";
  private final String SLOGAN_THUMB = "http://qtmisc.qiniudn.com/images/weibo-login-default.jpg";
  private WeakReference<Context> context;
  private List<WeiboResultToken> currentList = new ArrayList();
  private boolean hasRestoredFromDB = false;
  private boolean isCandF = false;
  private boolean islogin = false;
  private CloudCenter.OnLoginEventListerner mEventListerner;
  private final Handler mHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage == null)
        return;
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
        Toast.makeText(WeiboAgent.this.getContext(), "分享成功", 0).show();
        return;
      case 2:
        Toast.makeText(WeiboAgent.this.getContext(), "分享失败", 0).show();
        return;
      case 3:
        Toast.makeText(WeiboAgent.this.getContext(), "分享取消", 0).show();
        return;
      case 7:
        ((CloudCenter.OnLoginEventListerner)paramAnonymousMessage.obj).onLoginSuccessed(1);
        return;
      case 22:
      }
      InfoManager.getInstance().setUserInfo(WeiboAgent.this.mUserInfo);
      if (WeiboAgent.this.mEventListerner != null)
        WeiboAgent.this.mEventListerner.onLoginSuccessed(1);
      ControllerManager.getInstance().dipatchEventToCurrentController("weibo_login_success");
    }
  };
  private UserInfo mUserInfo;
  private Map<IResultToken, WeiboResultToken> queue = new HashMap();
  private WeiboRecordData recordData = new WeiboRecordData();
  private AgentRunnable runAble = new AgentRunnable(null);
  private Handler weiboAgentHandler = new Handler();

  private void addUserInfoLog()
  {
  }

  private void deleteUserInfo()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("site", "weibo");
    DataManager.getInstance().getData("deletedb_user_info", null, localHashMap);
    InfoManager.getInstance().setUserInfo(null);
  }

  public static WeiboAgent getInstance()
  {
    if (instance == null)
      instance = new WeiboAgent();
    return instance;
  }

  private boolean nonEmpty(String paramString)
  {
    return (paramString != null) && (paramString != "");
  }

  private void onLoginSuccess(Object paramObject)
  {
    if (SharedCfg.getInstance().getWeiboAuth("SINA-FOLLOWED", "") == "")
    {
      SinaWeiboApi.follow(getContext(), "蜻蜓FM", new SocialEventListener());
      SharedCfg.getInstance().setWeiboAuth("SINA-FOLLOWED", "true");
    }
    saveAccessToken((Oauth2AccessToken)paramObject);
    if (InfoManager.getInstance().getUserProfile().getUserInfo() != null)
      return;
    sendWeiboOnceLogin();
    SinaWeiboApi.readProfile(getContext(), new SocialEventListener()
    {
      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        try
        {
          JSONObject localJSONObject = (JSONObject)JSON.parse((String)paramAnonymousObject1);
          String str1 = localJSONObject.getString("id");
          String str2 = localJSONObject.getString("name");
          String str3 = localJSONObject.getString("screen_name");
          String str4 = localJSONObject.getString("gender");
          String str5 = localJSONObject.getString("avatar_large");
          String str6 = localJSONObject.getString("description");
          WeiboAgent.access$202(WeiboAgent.this, new UserInfo());
          WeiboAgent.this.mUserInfo.snsInfo.sns_site = "weibo";
          WeiboAgent.this.mUserInfo.snsInfo.sns_account = str2;
          WeiboAgent.this.mUserInfo.snsInfo.sns_avatar = str5;
          WeiboAgent.this.mUserInfo.snsInfo.sns_gender = str4;
          WeiboAgent.this.mUserInfo.snsInfo.sns_id = str1;
          WeiboAgent.this.mUserInfo.snsInfo.sns_name = str3;
          WeiboAgent.this.mUserInfo.snsInfo.signature = str6;
          WeiboAgent.this.saveUserInfoToDB();
          return;
        }
        catch (Exception localException)
        {
        }
      }
    });
  }

  private void openCreateWeibo(String paramString1, String paramString2, String paramString3)
  {
    openCreateWeibo(paramString1, paramString2, "", paramString3);
  }

  private void openCreateWeibo(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    openCreateWeibo(paramString1, paramString2, paramString3, paramString4, true, -2147483648);
  }

  private void openCreateWeibo(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean, int paramInt)
  {
  }

  private void openForward(Status paramStatus, String paramString1, String paramString2)
  {
  }

  private void openForwardJS(String paramString1, String paramString2, String paramString3, String paramString4)
  {
  }

  private void saveAccessToken(Oauth2AccessToken paramOauth2AccessToken)
  {
    if (paramOauth2AccessToken != null)
    {
      SharedCfg.getInstance().setWeiboAuth("SINA-UID", paramOauth2AccessToken.getUid());
      SharedCfg.getInstance().setWeiboAuth("SINA-TOKEN", paramOauth2AccessToken.getToken());
      SharedCfg.getInstance().setWeiboAuth("SINA-REFRESH-TOKEN", paramOauth2AccessToken.getRefreshToken());
      SharedCfg.getInstance().setWeiboAuth("SINA-EXPIRES-TIME", String.valueOf(paramOauth2AccessToken.getExpiresTime()));
    }
  }

  private void saveUserInfoToDB()
  {
    if (this.mUserInfo == null);
    while (this.mUserInfo.snsInfo.sns_id.equalsIgnoreCase(""))
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("site", "weibo");
    DataManager.getInstance().getData("deletedb_user_info", null, localHashMap);
    localHashMap.put("userInfo", this.mUserInfo);
    DataManager.getInstance().getData("insertdb_user_info", null, localHashMap);
    SharedCfg.getInstance().setWeiboGender(this.mUserInfo.snsInfo.sns_gender);
    this.mHandler.sendEmptyMessage(22);
    addUserInfoLog();
  }

  private void switchType(WeiboResultToken paramWeiboResultToken)
  {
  }

  public void CandFWeibo(WeiboDataType paramWeiboDataType, Map<String, Object> paramMap, IEventHandler paramIEventHandler)
  {
    WeiboResultToken localWeiboResultToken = new WeiboResultToken();
    localWeiboResultToken.setWeiboDataType(paramWeiboDataType);
    localWeiboResultToken.setParam(paramMap);
    localWeiboResultToken.setEventHandler(paramIEventHandler);
    this.CandFWRT = localWeiboResultToken;
    this.isCandF = true;
    switchType(localWeiboResultToken);
  }

  public boolean checkSign(Node paramNode)
  {
    return false;
  }

  public void destroy()
  {
    this.context = null;
  }

  public String getChannelName()
  {
    return this.recordData.getChannelName();
  }

  protected Context getContext()
  {
    if (this.context != null)
      return (Context)this.context.get();
    return null;
  }

  public String getDJID()
  {
    return this.recordData.getDJID();
  }

  public String getDJName()
  {
    return this.recordData.getDJName();
  }

  public String getFrom()
  {
    return this.recordData.getFromPage();
  }

  public String getProgramID()
  {
    return this.recordData.getProgramID();
  }

  public String getProgramName()
  {
    return this.recordData.getProgramName();
  }

  public String getSendType()
  {
    return this.recordData.getSendType();
  }

  public String getUserId()
  {
    Oauth2AccessToken localOauth2AccessToken = SinaWeiboAuth.getToken();
    if ((localOauth2AccessToken != null) && (localOauth2AccessToken.isSessionValid()))
      return localOauth2AccessToken.getUid();
    return null;
  }

  public UserInfo getUserInfo()
  {
    return this.mUserInfo;
  }

  public void init()
  {
    try
    {
      Oauth2AccessToken localOauth2AccessToken = new Oauth2AccessToken();
      String str = SharedCfg.getInstance().getWeiboAuth("SINA-EXPIRES-TIME", "");
      if (nonEmpty(str))
        localOauth2AccessToken.setExpiresTime(Long.parseLong(str));
      localOauth2AccessToken.setUid(SharedCfg.getInstance().getWeiboAuth("SINA-UID", ""));
      localOauth2AccessToken.setToken(SharedCfg.getInstance().getWeiboAuth("SINA-TOKEN", ""));
      localOauth2AccessToken.setRefreshToken(SharedCfg.getInstance().getWeiboAuth("SINA-REFRESH-TOKEN", ""));
      SinaWeiboAuth.setToken(localOauth2AccessToken);
      label84: if ((this.hasRestoredFromDB) || (!isSessionValid().booleanValue()));
      Result localResult;
      do
      {
        return;
        HashMap localHashMap = new HashMap();
        localHashMap.put("site", "weibo");
        localResult = DataManager.getInstance().getData("getdb_user_info", null, localHashMap).getResult();
      }
      while (!localResult.getSuccess());
      this.mUserInfo = ((UserInfo)localResult.getData());
      if ((this.mUserInfo != null) && (this.mUserInfo.snsInfo != null))
      {
        this.mUserInfo.snsInfo.sns_gender = SharedCfg.getInstance().getWeiboGender();
        this.mUserInfo.userKey = SharedCfg.getInstance().getWeiboSocialUserKey();
        SinaWeiboAuth.restoreLogin(getContext());
      }
      InfoManager.getInstance().setUserInfo(this.mUserInfo);
      this.hasRestoredFromDB = true;
      return;
    }
    catch (Exception localException)
    {
      break label84;
    }
  }

  public Boolean isLoggedIn()
  {
    return SinaWeiboAuth.isLoggedIn();
  }

  public Boolean isSessionValid()
  {
    return SinaWeiboAuth.isSessionValid(getContext());
  }

  public void login(final CloudCenter.OnLoginEventListerner paramOnLoginEventListerner)
  {
    this.mEventListerner = paramOnLoginEventListerner;
    if (!SinaWeiboAuth.isLoggedIn().booleanValue())
      SinaWeiboAuth.login(getContext(), new SocialEventListener()
      {
        public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
        {
          WeiboAgent.this.onLoginSuccess(paramAnonymousObject1);
        }

        public void onException(Object paramAnonymousObject)
        {
          if (paramOnLoginEventListerner != null)
            paramOnLoginEventListerner.onLoginFailed(1);
        }
      });
  }

  public void logout()
  {
    SocialEventListener local1 = new SocialEventListener()
    {
      public void onCancel(Object paramAnonymousObject)
      {
      }

      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        WeiboAgent.this.deleteUserInfo();
        WeiboAgent.access$202(WeiboAgent.this, null);
      }

      public void onException(Object paramAnonymousObject)
      {
      }
    };
    SinaWeiboApi.logout(getContext(), local1);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("login_Success"));
    while (true)
    {
      this.islogin = false;
      return;
      if ((!paramString.equalsIgnoreCase("CreatWeibo_success")) && (!paramString.equalsIgnoreCase("RepostWeibo_success")) && ((paramString.equalsIgnoreCase("CreatWeibo_cancel")) || (paramString.equalsIgnoreCase("RepostWeibo_cancel"))))
        this.isCandF = false;
    }
  }

  public void onRecvResult(Result paramResult, Object paramObject1, IResultToken paramIResultToken, Object paramObject2)
  {
  }

  public void onSocialLogin(Object paramObject)
  {
    Oauth2AccessToken localOauth2AccessToken = SinaWeiboAuth.getToken();
    if (localOauth2AccessToken != null)
      onLoginSuccess(localOauth2AccessToken);
  }

  public WeiboResultToken sendWeibo(WeiboDataType paramWeiboDataType, Map<String, Object> paramMap1, IWeiboResultRecvHandler paramIWeiboResultRecvHandler, Map<String, Object> paramMap2)
  {
    WeiboResultToken localWeiboResultToken = new WeiboResultToken();
    localWeiboResultToken.setHandler(paramIWeiboResultRecvHandler);
    localWeiboResultToken.setWeiboDataType(paramWeiboDataType);
    localWeiboResultToken.setParam(paramMap1);
    localWeiboResultToken.setSaveParam(paramMap2);
    switchType(localWeiboResultToken);
    return localWeiboResultToken;
  }

  public void sendWeiboOnceLogin()
  {
    if (!getInstance().isSessionValid().booleanValue())
      return;
    SinaWeiboApi.shareImage(getContext(), "刚登录#蜻蜓FM# // 终于找到fm神器了，内容爆多，新闻，小说，音乐，相声，脱口秀……想听啥都有，关键还有3000多家电台24小时不间断直播，快去把它收了吧→http://qingting.fm（分享自@蜻蜓FM）", "http://qtmisc.qiniudn.com/images/weibo-login-default.jpg", null, null, new SocialEventListener()
    {
    });
  }

  public void setContext(Context paramContext)
  {
    this.context = new WeakReference(paramContext);
  }

  public void setFrom(String paramString)
  {
    this.recordData.setFromPage(paramString);
  }

  public void setSendType(String paramString)
  {
    this.recordData.setSendType(paramString);
  }

  public void setWeiboLogParam(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    setWeiboLogParam(paramString1, paramString2, paramString3, "", "", "", paramString4);
  }

  public void setWeiboLogParam(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.recordData.setChannelName(paramString1);
    this.recordData.setProgramName(paramString2);
    this.recordData.setProgramID(paramString3);
    this.recordData.setDJName(paramString4);
    this.recordData.setDJID(paramString5);
    this.recordData.setFromPage(paramString7);
    this.recordData.setSendType(paramString6);
  }

  public boolean share(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return false;
    SinaWeiboApi.shareText(getContext(), paramString, "", "", new SocialEventListener()
    {
      public void onCancel(Object paramAnonymousObject)
      {
        Message localMessage = Message.obtain(WeiboAgent.this.mHandler, 3, null);
        WeiboAgent.this.mHandler.sendMessage(localMessage);
      }

      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        Message localMessage = Message.obtain(WeiboAgent.this.mHandler, 1, null);
        WeiboAgent.this.mHandler.sendMessage(localMessage);
      }

      public void onException(Object paramAnonymousObject)
      {
        Message localMessage = Message.obtain(WeiboAgent.this.mHandler, 2, null);
        WeiboAgent.this.mHandler.sendMessage(localMessage);
      }
    });
    return true;
  }

  private class AgentRunnable
    implements Runnable
  {
    public String msg;

    private AgentRunnable()
    {
    }

    public void run()
    {
      Toast.makeText(WeiboAgent.this.getContext(), this.msg, 1).show();
    }

    public void setMsg(String paramString)
    {
      this.msg = paramString;
    }
  }

  public static enum WeiboDataType
  {
    static
    {
      COMMENTS_WEIBO = new WeiboDataType("COMMENTS_WEIBO", 2);
      REPOST_WEIBO = new WeiboDataType("REPOST_WEIBO", 3);
      TO_LOGIN = new WeiboDataType("TO_LOGIN", 4);
      OWEN_LOGIN = new WeiboDataType("OWEN_LOGIN", 5);
      TO_ADD = new WeiboDataType("TO_ADD", 6);
      TO_SIGN = new WeiboDataType("TO_SIGN", 7);
      TO_FLOWER = new WeiboDataType("TO_FLOWER", 8);
      TO_SAY = new WeiboDataType("TO_SAY", 9);
      JS_GET_SEARCH = new WeiboDataType("JS_GET_SEARCH", 10);
      JS_CREATE_NEW_WEIBO = new WeiboDataType("JS_CREATE_NEW_WEIBO", 11);
      JS_FORWORD_WEIBO = new WeiboDataType("JS_FORWORD_WEIBO", 12);
      JS_TO_SEND = new WeiboDataType("JS_TO_SEND", 13);
      WeiboDataType[] arrayOfWeiboDataType = new WeiboDataType[14];
      arrayOfWeiboDataType[0] = CREATE_NEW_WEIBO;
      arrayOfWeiboDataType[1] = FORWORD_WEIBO;
      arrayOfWeiboDataType[2] = COMMENTS_WEIBO;
      arrayOfWeiboDataType[3] = REPOST_WEIBO;
      arrayOfWeiboDataType[4] = TO_LOGIN;
      arrayOfWeiboDataType[5] = OWEN_LOGIN;
      arrayOfWeiboDataType[6] = TO_ADD;
      arrayOfWeiboDataType[7] = TO_SIGN;
      arrayOfWeiboDataType[8] = TO_FLOWER;
      arrayOfWeiboDataType[9] = TO_SAY;
      arrayOfWeiboDataType[10] = JS_GET_SEARCH;
      arrayOfWeiboDataType[11] = JS_CREATE_NEW_WEIBO;
      arrayOfWeiboDataType[12] = JS_FORWORD_WEIBO;
      arrayOfWeiboDataType[13] = JS_TO_SEND;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.weiboAgent.WeiboAgent
 * JD-Core Version:    0.6.2
 */