package com.sina.weibo.sdk.auth.sso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.WbAppInstallActivator;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.component.AuthRequestParam;
import com.sina.weibo.sdk.component.WeiboSdkBrowser;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.utils.NetworkHelper;
import com.sina.weibo.sdk.utils.UIUtils;
import com.sina.weibo.sdk.utils.Utility;

class WebAuthHandler
{
  private static final String NETWORK_NOT_AVAILABLE_EN = "Network is not available";
  private static final String NETWORK_NOT_AVAILABLE_ZH_CN = "无法连接到网络，请检查网络配置";
  private static final String NETWORK_NOT_AVAILABLE_ZH_TW = "無法連接到網络，請檢查網络配置";
  private static final String OAUTH2_BASE_URL = "https://open.weibo.cn/oauth2/authorize?";
  private static final int OBTAIN_AUTH_CODE = 0;
  private static final int OBTAIN_AUTH_TOKEN = 1;
  private static final String TAG = WebAuthHandler.class.getName();
  private AuthInfo mAuthInfo;
  private Context mContext;

  public WebAuthHandler(Context paramContext, AuthInfo paramAuthInfo)
  {
    this.mContext = paramContext;
    this.mAuthInfo = paramAuthInfo;
  }

  private void startDialog(WeiboAuthListener paramWeiboAuthListener, int paramInt)
  {
    if (paramWeiboAuthListener == null)
      return;
    WeiboParameters localWeiboParameters = new WeiboParameters(this.mAuthInfo.getAppKey());
    localWeiboParameters.put("client_id", this.mAuthInfo.getAppKey());
    localWeiboParameters.put("redirect_uri", this.mAuthInfo.getRedirectUrl());
    localWeiboParameters.put("scope", this.mAuthInfo.getScope());
    localWeiboParameters.put("response_type", "code");
    localWeiboParameters.put("version", "0030105000");
    String str1 = Utility.getAid(this.mContext, this.mAuthInfo.getAppKey());
    if (!TextUtils.isEmpty(str1))
      localWeiboParameters.put("aid", str1);
    if (1 == paramInt)
    {
      localWeiboParameters.put("packagename", this.mAuthInfo.getPackageName());
      localWeiboParameters.put("key_hash", this.mAuthInfo.getKeyHash());
    }
    String str2 = "https://open.weibo.cn/oauth2/authorize?" + localWeiboParameters.encodeUrl();
    if (!NetworkHelper.hasInternetPermission(this.mContext))
    {
      UIUtils.showAlert(this.mContext, "Error", "Application requires permission to access the Internet");
      return;
    }
    AuthRequestParam localAuthRequestParam = new AuthRequestParam(this.mContext);
    localAuthRequestParam.setAuthInfo(this.mAuthInfo);
    localAuthRequestParam.setAuthListener(paramWeiboAuthListener);
    localAuthRequestParam.setUrl(str2);
    localAuthRequestParam.setSpecifyTitle("微博登录");
    Bundle localBundle = localAuthRequestParam.createRequestParamBundle();
    Intent localIntent = new Intent(this.mContext, WeiboSdkBrowser.class);
    localIntent.putExtras(localBundle);
    this.mContext.startActivity(localIntent);
  }

  public void anthorize(WeiboAuthListener paramWeiboAuthListener)
  {
    authorize(paramWeiboAuthListener, 1);
  }

  public void authorize(WeiboAuthListener paramWeiboAuthListener, int paramInt)
  {
    startDialog(paramWeiboAuthListener, paramInt);
    WbAppInstallActivator.getInstance(this.mContext, this.mAuthInfo.getAppKey()).activateWeiboInstall();
  }

  public AuthInfo getAuthInfo()
  {
    return this.mAuthInfo;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.auth.sso.WebAuthHandler
 * JD-Core Version:    0.6.2
 */