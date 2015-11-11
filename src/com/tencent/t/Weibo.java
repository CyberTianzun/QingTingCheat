package com.tencent.t;

import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.utils.HttpUtils;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Weibo extends BaseApi
{
  public Weibo(Context paramContext, QQAuth paramQQAuth, QQToken paramQQToken)
  {
    super(paramContext, paramQQAuth, paramQQToken);
  }

  public Weibo(Context paramContext, QQToken paramQQToken)
  {
    super(paramContext, paramQQToken);
  }

  public void atFriends(int paramInt, IUiListener paramIUiListener)
  {
    Bundle localBundle = composeCGIParams();
    localBundle.putString("reqnum", paramInt + "");
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.mToken, this.mContext, "friends/get_intimate_friends_weibo", localBundle, "GET", localTempRequestListener);
  }

  public void deleteText(String paramString, IUiListener paramIUiListener)
  {
    Bundle localBundle = composeCGIParams();
    localBundle.putString("id", paramString);
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.mToken, this.mContext, "t/del_t", localBundle, "POST", localTempRequestListener);
  }

  public void getWeiboInfo(IUiListener paramIUiListener)
  {
    Bundle localBundle = composeCGIParams();
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.mToken, this.mContext, "user/get_info", localBundle, "GET", localTempRequestListener);
  }

  public void nickTips(String paramString, int paramInt, IUiListener paramIUiListener)
  {
    Bundle localBundle = composeCGIParams();
    if (paramString == null)
      paramString = "";
    localBundle.putString("match", paramString);
    localBundle.putString("reqnum", paramInt + "");
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.mToken, this.mContext, "friends/match_nick_tips_weibo", localBundle, "GET", localTempRequestListener);
  }

  public void sendPicText(String paramString1, String paramString2, IUiListener paramIUiListener)
  {
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    FileInputStream localFileInputStream;
    ByteArrayOutputStream localByteArrayOutputStream;
    try
    {
      localFileInputStream = new FileInputStream(paramString2);
      localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte1 = new byte[1024];
      while (true)
      {
        int i = localFileInputStream.read(arrayOfByte1);
        if (i == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte1, 0, i);
      }
    }
    catch (IOException localIOException)
    {
      localTempRequestListener.onIOException(localIOException);
      return;
    }
    localByteArrayOutputStream.close();
    localFileInputStream.close();
    byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
    Bundle localBundle = composeCGIParams();
    if (paramString1 == null)
      paramString1 = "";
    localBundle.putString("content", paramString1);
    localBundle.putByteArray("pic", arrayOfByte2);
    HttpUtils.requestAsync(this.mToken, this.mContext, "t/add_pic_t", localBundle, "POST", localTempRequestListener);
  }

  public void sendText(String paramString, IUiListener paramIUiListener)
  {
    Bundle localBundle = composeCGIParams();
    if (paramString == null)
      paramString = "";
    localBundle.putString("content", paramString);
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.mToken, this.mContext, "t/add_t", localBundle, "POST", localTempRequestListener);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.t.Weibo
 * JD-Core Version:    0.6.2
 */