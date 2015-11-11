package com.android.volley.toolbox;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.AuthFailureError;

public class AndroidAuthenticator
  implements Authenticator
{
  private final Account mAccount;
  private final String mAuthTokenType;
  private final Context mContext;
  private final boolean mNotifyAuthFailure;

  public AndroidAuthenticator(Context paramContext, Account paramAccount, String paramString)
  {
    this(paramContext, paramAccount, paramString, false);
  }

  public AndroidAuthenticator(Context paramContext, Account paramAccount, String paramString, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mAccount = paramAccount;
    this.mAuthTokenType = paramString;
    this.mNotifyAuthFailure = paramBoolean;
  }

  public Account getAccount()
  {
    return this.mAccount;
  }

  public String getAuthToken()
    throws AuthFailureError
  {
    AccountManagerFuture localAccountManagerFuture = AccountManager.get(this.mContext).getAuthToken(this.mAccount, this.mAuthTokenType, this.mNotifyAuthFailure, null, null);
    Bundle localBundle;
    try
    {
      localBundle = (Bundle)localAccountManagerFuture.getResult();
      boolean bool1 = localAccountManagerFuture.isDone();
      str = null;
      if (!bool1)
        break label113;
      boolean bool2 = localAccountManagerFuture.isCancelled();
      str = null;
      if (bool2)
        break label113;
      if (localBundle.containsKey("intent"))
        throw new AuthFailureError((Intent)localBundle.getParcelable("intent"));
    }
    catch (Exception localException)
    {
      throw new AuthFailureError("Error while retrieving auth token", localException);
    }
    String str = localBundle.getString("authtoken");
    label113: if (str == null)
      throw new AuthFailureError("Got null auth token for type: " + this.mAuthTokenType);
    return str;
  }

  public void invalidateAuthToken(String paramString)
  {
    AccountManager.get(this.mContext).invalidateAuthToken(this.mAccount.type, paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.AndroidAuthenticator
 * JD-Core Version:    0.6.2
 */