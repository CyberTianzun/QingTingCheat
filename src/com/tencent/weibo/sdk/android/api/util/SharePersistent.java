package com.tencent.weibo.sdk.android.api.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.tencent.weibo.sdk.android.model.AccountModel;

public class SharePersistent
{
  private static final String FILE_NAME = "ANDROID_SDK";
  private static SharePersistent instance;

  public static SharePersistent getInstance()
  {
    if (instance == null)
      instance = new SharePersistent();
    return instance;
  }

  public boolean clear(Context paramContext, String paramString)
  {
    return paramContext.getSharedPreferences("ANDROID_SDK", 0).edit().clear().commit();
  }

  public String get(Context paramContext, String paramString)
  {
    return paramContext.getSharedPreferences("ANDROID_SDK", 0).getString(paramString, "");
  }

  public AccountModel getAccount(Context paramContext)
  {
    AccountModel localAccountModel = new AccountModel();
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("ANDROID_SDK", 0);
    localAccountModel.setAccessToken(localSharedPreferences.getString("ACCESS_TOKEN", ""));
    localAccountModel.setExpiresIn(localSharedPreferences.getLong("EXPIRES_IN", 0L));
    localAccountModel.setOpenID(localSharedPreferences.getString("OPEN_ID", ""));
    localAccountModel.setOpenKey(localSharedPreferences.getString("OPEN_KEY", ""));
    localAccountModel.setRefreshToken(localSharedPreferences.getString("REFRESH_TOKEN", ""));
    localAccountModel.setName(localSharedPreferences.getString("NAME", ""));
    localAccountModel.setNike(localSharedPreferences.getString("NICK", ""));
    return localAccountModel;
  }

  public long getLong(Context paramContext, String paramString)
  {
    return paramContext.getSharedPreferences("ANDROID_SDK", 0).getLong(paramString, 0L);
  }

  public boolean put(Context paramContext, String paramString, long paramLong)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("ANDROID_SDK", 0).edit();
    localEditor.putLong(paramString, paramLong);
    return localEditor.commit();
  }

  public boolean put(Context paramContext, String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("ANDROID_SDK", 0).edit();
    localEditor.putString(paramString1, paramString2);
    return localEditor.commit();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.api.util.SharePersistent
 * JD-Core Version:    0.6.2
 */