package com.tencent.qzone;

import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.utils.HttpUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Albums extends BaseApi
{
  public Albums(Context paramContext, QQAuth paramQQAuth, QQToken paramQQToken)
  {
    super(paramContext, paramQQAuth, paramQQToken);
  }

  public Albums(Context paramContext, QQToken paramQQToken)
  {
    super(paramContext, paramQQToken);
  }

  public void addAlbum(String paramString1, String paramString2, AlbumSecurity paramAlbumSecurity, String paramString3, String paramString4, IUiListener paramIUiListener)
  {
    Bundle localBundle = composeCGIParams();
    if (paramString1 == null)
      paramString1 = "";
    localBundle.putString("albumname", paramString1);
    if (paramString2 == null)
      paramString2 = "";
    localBundle.putString("albumdesc", paramString2);
    if (paramAlbumSecurity == null);
    for (String str = AlbumSecurity.publicToAll.getSecurity(); ; str = paramAlbumSecurity.getSecurity())
    {
      localBundle.putString("priv", str);
      if (paramString3 == null)
        paramString3 = "";
      localBundle.putString("question", paramString3);
      if (paramString4 == null)
        paramString4 = "";
      localBundle.putString("answer", paramString4);
      BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
      HttpUtils.requestAsync(this.mToken, this.mContext, "photo/add_album", localBundle, "POST", localTempRequestListener);
      return;
    }
  }

  public void listAlbum(IUiListener paramIUiListener)
  {
    Bundle localBundle = composeCGIParams();
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.mToken, this.mContext, "photo/list_album", localBundle, "GET", localTempRequestListener);
  }

  public void listPhotos(String paramString, IUiListener paramIUiListener)
  {
    Bundle localBundle = composeCGIParams();
    if (paramString == null)
      paramString = "";
    localBundle.putString("albumid", paramString);
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    HttpUtils.requestAsync(this.mToken, this.mContext, "photo/list_photo", localBundle, "GET", localTempRequestListener);
  }

  public void uploadPicture(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, IUiListener paramIUiListener)
  {
    BaseApi.TempRequestListener localTempRequestListener = new BaseApi.TempRequestListener(this, paramIUiListener);
    FileInputStream localFileInputStream;
    ByteArrayOutputStream localByteArrayOutputStream;
    try
    {
      localFileInputStream = new FileInputStream(paramString1);
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
    File localFile = new File(paramString1);
    localBundle.putByteArray("picture", arrayOfByte2);
    if (paramString2 == null)
      paramString2 = "";
    localBundle.putString("photodesc", paramString2);
    localBundle.putString("title", localFile.getName());
    if (paramString3 != null)
    {
      if (paramString3 == null)
        paramString3 = "";
      localBundle.putString("albumid", paramString3);
    }
    if (paramString4 == null)
      paramString4 = "";
    localBundle.putString("x", paramString4);
    if (paramString5 == null)
      paramString5 = "";
    localBundle.putString("y", paramString5);
    HttpUtils.requestAsync(this.mToken, this.mContext, "photo/upload_pic", localBundle, "POST", localTempRequestListener);
  }

  public static enum AlbumSecurity
  {
    private final String a;

    static
    {
      privateOnly = new AlbumSecurity("privateOnly", 1, "2");
      friendsOnly = new AlbumSecurity("friendsOnly", 2, "4");
      needQuestion = new AlbumSecurity("needQuestion", 3, "5");
      AlbumSecurity[] arrayOfAlbumSecurity = new AlbumSecurity[4];
      arrayOfAlbumSecurity[0] = publicToAll;
      arrayOfAlbumSecurity[1] = privateOnly;
      arrayOfAlbumSecurity[2] = friendsOnly;
      arrayOfAlbumSecurity[3] = needQuestion;
    }

    private AlbumSecurity(String paramString)
    {
      this.a = paramString;
    }

    public String getSecurity()
    {
      return this.a;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.qzone.Albums
 * JD-Core Version:    0.6.2
 */