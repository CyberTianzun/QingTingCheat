package com.tencent.tauth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.tencent.connect.auth.AuthDialog;
import com.tencent.connect.auth.AuthMap;
import com.tencent.connect.auth.AuthMap.Auth;
import com.tencent.utils.TemporaryStorage;
import com.tencent.utils.Util;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthActivity extends Activity
{
  private static final String ACTION_KEY = "action";
  private static final String ACTION_SHARE_TO_QQ = "shareToQQ";
  private static final String ACTION_SHARE_TO_QZONE = "shareToQzone";

  private void execAuthCallback(Bundle paramBundle, String paramString)
  {
    AuthMap localAuthMap = AuthMap.getInstance();
    String str1 = paramBundle.getString("serial");
    AuthMap.Auth localAuth = localAuthMap.get(str1);
    if (localAuth != null)
    {
      if (paramString.indexOf("://cancel") == -1)
        break label64;
      localAuth.listener.onCancel();
      localAuth.dialog.dismiss();
    }
    while (true)
    {
      localAuthMap.remove(str1);
      finish();
      return;
      label64: String str2 = paramBundle.getString("access_token");
      if (str2 != null)
        paramBundle.putString("access_token", localAuthMap.decode(str2, localAuth.key));
      String str3 = Util.encodeUrl(paramBundle);
      JSONObject localJSONObject = Util.decodeUrlToJson(new JSONObject(), str3);
      String str4 = localJSONObject.optString("cb");
      if (!"".equals(str4))
      {
        localAuth.dialog.callJs(str4, localJSONObject.toString());
      }
      else
      {
        localAuth.listener.onComplete(localJSONObject);
        localAuth.dialog.dismiss();
      }
    }
  }

  private void execShareToQQCallback(Bundle paramBundle)
  {
    Object localObject = TemporaryStorage.get(paramBundle.getString("action"));
    if (localObject == null)
    {
      finish();
      return;
    }
    IUiListener localIUiListener = (IUiListener)localObject;
    String str1 = paramBundle.getString("result");
    String str2 = paramBundle.getString("response");
    if (str1.equals("cancel"))
      localIUiListener.onCancel();
    do
      while (true)
      {
        finish();
        return;
        if (!str1.equals("error"))
          break;
        localIUiListener.onError(new UiError(-6, "unknown error", str2 + ""));
      }
    while (!str1.equals("complete"));
    if (str2 == null);
    for (String str3 = "{\"ret\": 0}"; ; str3 = str2)
    {
      try
      {
        localIUiListener.onComplete(new JSONObject(str3));
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        localIUiListener.onError(new UiError(-4, "json error", str3 + ""));
      }
      break;
    }
  }

  private void handleActionUri(Uri paramUri)
  {
    if ((paramUri == null) || (paramUri.toString().equals("")))
    {
      finish();
      return;
    }
    String str1 = paramUri.toString();
    Bundle localBundle = Util.decodeUrl(str1.substring(1 + str1.indexOf("#")));
    String str2 = localBundle.getString("action");
    if (str2 == null)
    {
      execAuthCallback(localBundle, str1);
      return;
    }
    if ((str2.equals("shareToQQ")) || (str2.equals("shareToQzone")))
    {
      execShareToQQCallback(localBundle);
      return;
    }
    execAuthCallback(localBundle, str1);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    handleActionUri(getIntent().getData());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.AuthActivity
 * JD-Core Version:    0.6.2
 */