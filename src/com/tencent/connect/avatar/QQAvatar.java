package com.tencent.connect.avatar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.Util;
import org.json.JSONException;
import org.json.JSONObject;

public class QQAvatar extends BaseApi
{
  private IUiListener a;

  public QQAvatar(Context paramContext, QQToken paramQQToken)
  {
    super(paramContext, paramQQToken);
  }

  private Intent a()
  {
    Intent localIntent = new Intent();
    localIntent.setClass(this.mContext, ImageActivity.class);
    return localIntent;
  }

  private void a(Activity paramActivity, Bundle paramBundle)
  {
    a(paramBundle);
    this.mActivityIntent.putExtra("key_action", "action_avatar");
    this.mActivityIntent.putExtra("key_params", paramBundle);
    startAssitActivity(paramActivity, this.a);
  }

  private void a(Bundle paramBundle)
  {
    if (this.mToken != null)
    {
      paramBundle.putString("appid", this.mToken.getAppId());
      if (this.mToken.isSessionValid())
      {
        paramBundle.putString("keystr", this.mToken.getAccessToken());
        paramBundle.putString("keytype", "0x80");
      }
      String str = this.mToken.getOpenId();
      if (str != null)
        paramBundle.putString("hopenid", str);
      paramBundle.putString("platform", "androidqz");
    }
    try
    {
      paramBundle.putString("pf", this.mContext.getSharedPreferences("pfStore", 0).getString("pf", "openmobile_android"));
      paramBundle.putString("sdkv", "2.2.1");
      paramBundle.putString("sdkp", "a");
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        paramBundle.putString("pf", "openmobile_android");
      }
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 == -1)
    {
      int i = paramIntent.getIntExtra("key_error_code", 0);
      if (i == 0)
      {
        String str3 = paramIntent.getStringExtra("key_response");
        if (str3 != null)
          try
          {
            JSONObject localJSONObject = Util.parseJson(str3);
            this.a.onComplete(localJSONObject);
            return;
          }
          catch (JSONException localJSONException)
          {
            this.a.onError(new UiError(-4, "服务器返回数据格式有误!", str3));
            return;
          }
        this.a.onComplete(new JSONObject());
        return;
      }
      String str1 = paramIntent.getStringExtra("key_error_msg");
      String str2 = paramIntent.getStringExtra("key_error_detail");
      this.a.onError(new UiError(i, str1, str2));
      return;
    }
    this.a.onCancel();
  }

  public void setAvatar(Activity paramActivity, Uri paramUri, IUiListener paramIUiListener, int paramInt)
  {
    if (this.a != null)
      this.a.onCancel();
    this.a = paramIUiListener;
    Bundle localBundle = new Bundle();
    localBundle.putString("picture", paramUri.toString());
    localBundle.putInt("exitAnim", paramInt);
    localBundle.putString("appid", this.mToken.getAppId());
    localBundle.putString("access_token", this.mToken.getAccessToken());
    localBundle.putLong("expires_in", this.mToken.getExpireTimeInSecond());
    localBundle.putString("openid", this.mToken.getOpenId());
    this.mActivityIntent = a();
    if (hasActivityForIntent())
      a(paramActivity, localBundle);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.avatar.QQAvatar
 * JD-Core Version:    0.6.2
 */