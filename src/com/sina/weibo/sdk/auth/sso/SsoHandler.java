package com.sina.weibo.sdk.auth.sso;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.sina.sso.RemoteSSO;
import com.sina.sso.RemoteSSO.Stub;
import com.sina.weibo.sdk.WeiboAppManager;
import com.sina.weibo.sdk.WeiboAppManager.WeiboInfo;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboDialogException;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.AidTask;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.SecurityHelper;
import com.sina.weibo.sdk.utils.Utility;
import java.util.Iterator;
import java.util.List;

public class SsoHandler
{
  public static final String AUTH_FAILED_MSG = "auth failed!!!!!";
  public static final String AUTH_FAILED_NOT_INSTALL_MSG = "not install weibo client!!!!!";
  private static final String DEFAULT_WEIBO_REMOTE_SSO_SERVICE_NAME = "com.sina.weibo.remotessoservice";
  private static final int REQUEST_CODE_SSO_AUTH = 32973;
  private static final String TAG = "Weibo_SSO_login";
  private Activity mAuthActivity;
  private AuthInfo mAuthInfo;
  private WeiboAuthListener mAuthListener;
  private ServiceConnection mConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      RemoteSSO localRemoteSSO = RemoteSSO.Stub.asInterface(paramAnonymousIBinder);
      try
      {
        String str1 = localRemoteSSO.getPackageName();
        String str2 = localRemoteSSO.getActivityName();
        SsoHandler.this.mAuthActivity.getApplicationContext().unbindService(SsoHandler.this.mConnection);
        if (!SsoHandler.this.startSingleSignOn(str1, str2))
          SsoHandler.this.mWebAuthHandler.anthorize(SsoHandler.this.mAuthListener);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
      }
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      SsoHandler.this.mWebAuthHandler.anthorize(SsoHandler.this.mAuthListener);
    }
  };
  private int mSSOAuthRequestCode;
  private WebAuthHandler mWebAuthHandler;
  private WeiboAppManager.WeiboInfo mWeiboInfo;

  public SsoHandler(Activity paramActivity, AuthInfo paramAuthInfo)
  {
    this.mAuthActivity = paramActivity;
    this.mAuthInfo = paramAuthInfo;
    this.mWebAuthHandler = new WebAuthHandler(paramActivity, paramAuthInfo);
    this.mWeiboInfo = WeiboAppManager.getInstance(paramActivity).getWeiboInfo();
    AidTask.getInstance(this.mAuthActivity).aidTaskInit(paramAuthInfo.getAppKey());
  }

  private void authorize(int paramInt, WeiboAuthListener paramWeiboAuthListener, AuthType paramAuthType)
  {
    this.mSSOAuthRequestCode = paramInt;
    this.mAuthListener = paramWeiboAuthListener;
    AuthType localAuthType = AuthType.SsoOnly;
    int i = 0;
    if (paramAuthType == localAuthType)
      i = 1;
    if (paramAuthType == AuthType.WebOnly)
      if (paramWeiboAuthListener != null)
        this.mWebAuthHandler.anthorize(paramWeiboAuthListener);
    do
    {
      do
        return;
      while (bindRemoteSSOService(this.mAuthActivity.getApplicationContext()));
      if (i == 0)
        break;
    }
    while (this.mAuthListener == null);
    this.mAuthListener.onWeiboException(new WeiboException("not install weibo client!!!!!"));
    return;
    this.mWebAuthHandler.anthorize(this.mAuthListener);
  }

  private boolean bindRemoteSSOService(Context paramContext)
  {
    if (!isWeiboAppInstalled())
      return false;
    String str = this.mWeiboInfo.getPackageName();
    Intent localIntent = new Intent("com.sina.weibo.remotessoservice");
    localIntent.setPackage(str);
    return paramContext.bindService(localIntent, this.mConnection, 1);
  }

  public static ComponentName isServiceExisted(Context paramContext, String paramString)
  {
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(2147483647).iterator();
    ComponentName localComponentName;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localComponentName = ((ActivityManager.RunningServiceInfo)localIterator.next()).service;
    }
    while ((!localComponentName.getPackageName().equals(paramString)) || (!localComponentName.getClassName().equals(paramString + ".business.RemoteSSOService")));
    return localComponentName;
  }

  private boolean startSingleSignOn(String paramString1, String paramString2)
  {
    boolean bool = true;
    Intent localIntent = new Intent();
    localIntent.setClassName(paramString1, paramString2);
    localIntent.putExtras(this.mWebAuthHandler.getAuthInfo().getAuthBundle());
    localIntent.putExtra("_weibo_command_type", 3);
    localIntent.putExtra("_weibo_transaction", String.valueOf(System.currentTimeMillis()));
    localIntent.putExtra("aid", Utility.getAid(this.mAuthActivity, this.mAuthInfo.getAppKey()));
    if (!SecurityHelper.validateAppSignatureForIntent(this.mAuthActivity, localIntent))
      return false;
    String str = Utility.getAid(this.mAuthActivity, this.mAuthInfo.getAppKey());
    if (!TextUtils.isEmpty(str))
      localIntent.putExtra("aid", str);
    try
    {
      this.mAuthActivity.startActivityForResult(localIntent, this.mSSOAuthRequestCode);
      return bool;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      while (true)
        bool = false;
    }
  }

  public void authorize(WeiboAuthListener paramWeiboAuthListener)
  {
    authorize(32973, paramWeiboAuthListener, AuthType.ALL);
  }

  public void authorizeCallBack(int paramInt1, int paramInt2, Intent paramIntent)
  {
    LogUtil.d("Weibo_SSO_login", "requestCode: " + paramInt1 + ", resultCode: " + paramInt2 + ", data: " + paramIntent);
    if (paramInt1 == this.mSSOAuthRequestCode)
    {
      if (paramInt2 != -1)
        break label310;
      if (SecurityHelper.checkResponseAppLegal(this.mAuthActivity, this.mWeiboInfo, paramIntent))
        break label71;
    }
    label71: 
    while (paramInt2 != 0)
    {
      return;
      String str1 = paramIntent.getStringExtra("error");
      if (str1 == null)
        str1 = paramIntent.getStringExtra("error_type");
      if (str1 != null)
      {
        if ((str1.equals("access_denied")) || (str1.equals("OAuthAccessDeniedException")))
        {
          LogUtil.d("Weibo_SSO_login", "Login canceled by user.");
          this.mAuthListener.onCancel();
          return;
        }
        String str2 = paramIntent.getStringExtra("error_description");
        if (str2 != null)
          str1 = str1 + ":" + str2;
        LogUtil.d("Weibo_SSO_login", "Login failed: " + str1);
        this.mAuthListener.onWeiboException(new WeiboDialogException(str1, paramInt2, str2));
        return;
      }
      Bundle localBundle = paramIntent.getExtras();
      Oauth2AccessToken localOauth2AccessToken = Oauth2AccessToken.parseAccessToken(localBundle);
      if ((localOauth2AccessToken != null) && (localOauth2AccessToken.isSessionValid()))
      {
        LogUtil.d("Weibo_SSO_login", "Login Success! " + localOauth2AccessToken.toString());
        this.mAuthListener.onComplete(localBundle);
        return;
      }
      LogUtil.d("Weibo_SSO_login", "Failed to receive access token by SSO");
      this.mWebAuthHandler.anthorize(this.mAuthListener);
      return;
    }
    label310: if (paramIntent != null)
    {
      LogUtil.d("Weibo_SSO_login", "Login failed: " + paramIntent.getStringExtra("error"));
      this.mAuthListener.onWeiboException(new WeiboDialogException(paramIntent.getStringExtra("error"), paramIntent.getIntExtra("error_code", -1), paramIntent.getStringExtra("failing_url")));
      return;
    }
    LogUtil.d("Weibo_SSO_login", "Login canceled by user.");
    this.mAuthListener.onCancel();
  }

  public void authorizeClientSso(WeiboAuthListener paramWeiboAuthListener)
  {
    authorize(32973, paramWeiboAuthListener, AuthType.SsoOnly);
  }

  public void authorizeWeb(WeiboAuthListener paramWeiboAuthListener)
  {
    authorize(32973, paramWeiboAuthListener, AuthType.WebOnly);
  }

  public boolean isWeiboAppInstalled()
  {
    return (this.mWeiboInfo != null) && (this.mWeiboInfo.isLegal());
  }

  private static enum AuthType
  {
    static
    {
      AuthType[] arrayOfAuthType = new AuthType[3];
      arrayOfAuthType[0] = ALL;
      arrayOfAuthType[1] = SsoOnly;
      arrayOfAuthType[2] = WebOnly;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.auth.sso.SsoHandler
 * JD-Core Version:    0.6.2
 */