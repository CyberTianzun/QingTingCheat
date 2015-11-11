package fm.qingting.qtradio.tencentAgent;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.OnDemandProgramNode;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.share.ShareInfoBean;
import fm.qingting.qtradio.social.CloudCenter.OnLoginEventListerner;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.social.SocialEventListener;
import fm.qingting.social.api.QZoneApi;
import fm.qingting.social.api.TencentWeiboApi;
import fm.qingting.social.auth.TencentWeiboAuth;
import java.util.HashMap;
import java.util.Map;

public class TencentAgent
{
  private static TencentAgent instance;
  private final String SLOGAN_TEXT = "刚登录#蜻蜓FM# // 终于找到fm神器了，内容爆多，新闻，小说，音乐，相声，脱口秀……想听啥都有，关键还有3000多家电台24小时不间断直播，快去把它收了吧→http://qingting.fm（分享自@蜻蜓fm）";
  private final String SLOGAN_THUMB = "http://qtmisc.qiniudn.com/images/weibo-login-default.jpg";
  private String accessToken = null;
  private boolean hasRestoredFromDB = false;
  private long l_expires = -9223372036854775808L;
  private Context mContext;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage == null);
      do
      {
        return;
        switch (paramAnonymousMessage.what)
        {
        default:
          return;
        case 1:
          Toast.makeText(InfoManager.getInstance().getContext(), "分享成功", 0).show();
          MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "ShareResult", "succ_tencent");
          return;
        case 2:
          Toast.makeText(InfoManager.getInstance().getContext(), "分享失败", 0).show();
          MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "ShareResult", "failed_tencent");
          return;
        case 7:
        }
      }
      while (TencentAgent.this.mLoginEventListerner == null);
      TencentAgent.this.mLoginEventListerner.onLoginSuccessed(2);
    }
  };
  private CloudCenter.OnLoginEventListerner mLoginEventListerner;
  private String mSnsId;
  private UserInfo mUserInfo;
  private String openid = null;

  private void addUserInfoLog()
  {
    if (this.mUserInfo == null);
    while ((this.mUserInfo.snsInfo == null) || (this.mUserInfo.snsInfo.sns_id == null) || (!this.mUserInfo.snsInfo.sns_id.equalsIgnoreCase("")))
      return;
  }

  private void deleteUserInfo()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("site", "tencent");
    DataManager.getInstance().getData("deletedb_user_info", null, localHashMap);
    InfoManager.getInstance().setUserInfo(null);
  }

  public static TencentAgent getInstance()
  {
    if (instance == null)
      instance = new TencentAgent();
    return instance;
  }

  private UserInfo getSimpleUserInfo()
  {
    return getUserInfo();
  }

  private String getSiteString(Node paramNode, String paramString)
  {
    if (paramNode != null)
    {
      if (paramNode.nodeName.equalsIgnoreCase("program"))
      {
        if (paramString != null)
        {
          Object[] arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = paramString;
          arrayOfObject2[1] = InfoManager.getInstance().getContext().getString(2131492874);
          return String.format("http://qingting.fm/%s?os=android&from=weibo&version=%s&action=flower", arrayOfObject2);
        }
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = paramString;
        arrayOfObject1[1] = InfoManager.getInstance().getContext().getString(2131492874);
        return String.format("http://qingting.fm/%s?os=android&from=weibo&version=%s&action=flower", arrayOfObject1);
      }
      if (paramNode.nodeName.equalsIgnoreCase("ondemandprogram"))
      {
        String str1 = ((OnDemandProgramNode)paramNode).programId;
        String str2 = ((OnDemandProgramNode)paramNode).albumId;
        if ((str1 == null) || (str2 == null))
          break label147;
        return String.format("http://qingting.fm/aod/%s/episodes/%s", new Object[] { str2, str1 });
      }
    }
    return null;
    label147: return null;
  }

  private void getTencentAccessToken()
  {
    if (this.mContext == null)
      return;
    this.accessToken = SharedCfg.getInstance().getTencentAccessToken();
    this.l_expires = SharedCfg.getInstance().getTencentExpires();
    this.openid = SharedCfg.getInstance().getTencentOpenId();
  }

  private void init()
  {
    if ((this.hasRestoredFromDB) || (!isSessionValid().booleanValue()));
    Result localResult;
    do
    {
      return;
      HashMap localHashMap = new HashMap();
      localHashMap.put("site", "tencent");
      localResult = DataManager.getInstance().getData("getdb_user_info", null, localHashMap).getResult();
    }
    while (!localResult.getSuccess());
    this.mUserInfo = ((UserInfo)localResult.getData());
    if ((this.mUserInfo != null) && (this.mUserInfo.snsInfo != null))
    {
      this.mUserInfo.snsInfo.sns_gender = SharedCfg.getInstance().getTencentGender();
      this.mUserInfo.userKey = SharedCfg.getInstance().getTencentSocialUserKey();
    }
    if ((InfoManager.getInstance().getUserProfile().getUserInfo() == null) && (this.mUserInfo != null))
      InfoManager.getInstance().setUserInfo(this.mUserInfo);
    if (this.mUserInfo != null)
      TencentWeiboAuth.restoreLogin(this.mContext);
    this.hasRestoredFromDB = true;
  }

  private void onLoginSuccess()
  {
    sendWeiboOnceLogin();
    TencentWeiboApi.readProfile(this.mContext, new SocialEventListener()
    {
      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        TencentAgent.this.getUserInfo();
        TencentAgent.this.saveUserInfoToDB();
        ControllerManager.getInstance().dipatchEventToCurrentController("tencent_login_success");
        if (TencentAgent.this.mLoginEventListerner != null)
        {
          Message localMessage = TencentAgent.this.mHandler.obtainMessage(7);
          TencentAgent.this.mHandler.sendMessage(localMessage);
        }
      }
    });
  }

  private void removeAccessToken()
  {
    this.accessToken = null;
    this.l_expires = 0L;
    this.openid = null;
    saveAccessToken();
  }

  private void saveAccessToken()
  {
    if (this.mContext == null)
      return;
    SharedCfg.getInstance().setTencentAccessToken(this.accessToken);
    SharedCfg.getInstance().setTencentExpires(this.l_expires);
    SharedCfg.getInstance().setTencentOpenId(this.openid);
  }

  private void saveUserInfoToDB()
  {
    if (this.mUserInfo == null);
    while (this.mUserInfo.snsInfo.sns_id.equalsIgnoreCase(""))
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("site", "tencent");
    DataManager.getInstance().getData("deletedb_user_info", null, localHashMap);
    localHashMap.put("userInfo", this.mUserInfo);
    DataManager.getInstance().getData("insertdb_user_info", null, localHashMap);
    SharedCfg.getInstance().setTencentGender(this.mUserInfo.snsInfo.sns_gender);
    InfoManager.getInstance().setUserInfo(this.mUserInfo);
    addUserInfoLog();
  }

  private void sendWeiboOnceLogin()
  {
    if (!isSessionValid().booleanValue())
      return;
    TencentWeiboApi.shareImage(this.mContext, "刚登录#蜻蜓FM# // 终于找到fm神器了，内容爆多，新闻，小说，音乐，相声，脱口秀……想听啥都有，关键还有3000多家电台24小时不间断直播，快去把它收了吧→http://qingting.fm（分享自@蜻蜓fm）", "http://qtmisc.qiniudn.com/images/weibo-login-default.jpg", new SocialEventListener());
  }

  public void addQqSpace(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    while (!isSessionValid().booleanValue())
      return;
    QZoneApi.shareText(getContext(), "蜻蜓fm在线收听 最好的网络收音机", paramString, "蜻蜓fm", "http://qingting.fm/app", new SocialEventListener());
  }

  public void addTencentWeiboWithPic(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    TencentWeiboApi.shareText(this.mContext, paramString, new SocialEventListener());
  }

  public void addTencentWeiboWithoutPic(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    while (!isSessionValid().booleanValue())
      return;
    TencentWeiboApi.shareText(this.mContext, paramString, new SocialEventListener());
  }

  public void checkIn(Node paramNode)
  {
  }

  protected Context getContext()
  {
    return this.mContext;
  }

  public UserInfo getUserInfo()
  {
    if ((this.mUserInfo != null) && (this.mUserInfo.snsInfo.sns_id != null) && (!this.mUserInfo.snsInfo.sns_id.equalsIgnoreCase("")))
      return this.mUserInfo;
    if (!isSessionValid().booleanValue())
      return null;
    if (this.mUserInfo == null)
      this.mUserInfo = new UserInfo();
    this.mUserInfo.snsInfo.sns_account = Util.getSharePersistent(this.mContext, "name");
    this.mUserInfo.snsInfo.sns_avatar = Util.getSharePersistent(this.mContext, "avatar");
    this.mUserInfo.snsInfo.sns_gender = Util.getSharePersistent(this.mContext, "gender");
    this.mUserInfo.snsInfo.sns_site = "tencent";
    this.mUserInfo.snsInfo.sns_id = this.mUserInfo.snsInfo.sns_account;
    this.mUserInfo.snsInfo.sns_name = Util.getSharePersistent(this.mContext, "nick");
    this.mUserInfo.snsInfo.signature = Util.getSharePersistent(this.mContext, "introduction");
    return this.mUserInfo;
  }

  public String getUserSnsId()
  {
    if (this.mUserInfo != null)
      return this.mUserInfo.snsInfo.sns_id;
    return null;
  }

  public void init(Context paramContext, String paramString)
  {
    this.mContext = paramContext;
    init();
  }

  public Boolean isLoggedIn()
  {
    return TencentWeiboAuth.isLoggedIn();
  }

  public Boolean isSessionValid()
  {
    return TencentWeiboApi.isSessionValid(this.mContext);
  }

  public void login(CloudCenter.OnLoginEventListerner paramOnLoginEventListerner)
  {
    if (TencentWeiboAuth.isLoggedIn().booleanValue())
      return;
    this.mLoginEventListerner = paramOnLoginEventListerner;
    TencentWeiboAuth.login(this.mContext, new SocialEventListener()
    {
      public void onCancel(Object paramAnonymousObject)
      {
      }

      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        TencentAgent.this.onLoginSuccess();
      }

      public void onException(Object paramAnonymousObject)
      {
      }
    });
  }

  public void logout()
  {
    TencentWeiboAuth.logout(this.mContext, new SocialEventListener()
    {
      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        TencentAgent.this.deleteUserInfo();
        TencentAgent.this.removeAccessToken();
        TencentAgent.access$402(TencentAgent.this, null);
      }
    });
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
  }

  public void onResume(Context paramContext)
  {
    TencentWeiboAuth.onResume(paramContext);
  }

  public void onSocialLogin(Object paramObject)
  {
    if (InfoManager.getInstance().getUserProfile().getUserInfo() == null)
      onLoginSuccess();
  }

  public void publishTencentWeibo(ShareInfoBean paramShareInfoBean)
  {
    if (paramShareInfoBean == null)
      return;
    String str = paramShareInfoBean.parentCover;
    if ((str != null) && (!str.equalsIgnoreCase("")))
    {
      TencentWeiboApi.shareImage(this.mContext, paramShareInfoBean.content, str, new SocialEventListener()
      {
        public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
        {
          Message localMessage = Message.obtain(TencentAgent.this.mHandler, 1, null);
          TencentAgent.this.mHandler.sendMessage(localMessage);
        }

        public void onException(Object paramAnonymousObject)
        {
          Message localMessage = Message.obtain(TencentAgent.this.mHandler, 2, null);
          TencentAgent.this.mHandler.sendMessage(localMessage);
        }
      });
      return;
    }
    TencentWeiboApi.shareText(this.mContext, paramShareInfoBean.content, new SocialEventListener()
    {
      public void onComplete(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        Message localMessage = Message.obtain(TencentAgent.this.mHandler, 1, null);
        TencentAgent.this.mHandler.sendMessage(localMessage);
      }

      public void onException(Object paramAnonymousObject)
      {
        Message localMessage = Message.obtain(TencentAgent.this.mHandler, 2, null);
        TencentAgent.this.mHandler.sendMessage(localMessage);
      }
    });
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.tencentAgent.TencentAgent
 * JD-Core Version:    0.6.2
 */