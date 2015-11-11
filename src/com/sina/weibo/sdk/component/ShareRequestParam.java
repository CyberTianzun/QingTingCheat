package com.sina.weibo.sdk.component;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseRequest;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.utils.MD5;
import com.sina.weibo.sdk.utils.Utility;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareRequestParam extends BrowserRequestParamBase
{
  public static final String REQ_PARAM_AID = "aid";
  public static final String REQ_PARAM_KEY_HASH = "key_hash";
  public static final String REQ_PARAM_PACKAGENAME = "packagename";
  public static final String REQ_PARAM_PICINFO = "picinfo";
  public static final String REQ_PARAM_SOURCE = "source";
  public static final String REQ_PARAM_TITLE = "title";
  public static final String REQ_PARAM_TOKEN = "access_token";
  public static final String REQ_PARAM_VERSION = "version";
  public static final String REQ_UPLOAD_PIC_PARAM_IMG = "img";
  public static final String RESP_UPLOAD_PIC_PARAM_CODE = "code";
  public static final String RESP_UPLOAD_PIC_PARAM_DATA = "data";
  public static final int RESP_UPLOAD_PIC_SUCC_CODE = 1;
  private static final String SHARE_URL = "http://service.weibo.com/share/mobilesdk.php";
  public static final String UPLOAD_PIC_URL = "http://service.weibo.com/share/mobilesdk_uppic.php";
  private String mAppKey;
  private String mAppPackage;
  private WeiboAuthListener mAuthListener;
  private String mAuthListenerKey;
  private byte[] mBase64ImgData;
  private BaseRequest mBaseRequest;
  private String mHashKey;
  private String mShareContent;
  private String mToken;

  public ShareRequestParam(Context paramContext)
  {
    super(paramContext);
    this.mLaucher = BrowserLauncher.SHARE;
  }

  // ERROR //
  private void handleMblogPic(String paramString, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 86	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifne +103 -> 107
    //   7: new 88	java/io/File
    //   10: dup
    //   11: aload_1
    //   12: invokespecial 91	java/io/File:<init>	(Ljava/lang/String;)V
    //   15: astore 4
    //   17: aload 4
    //   19: invokevirtual 95	java/io/File:exists	()Z
    //   22: ifeq +85 -> 107
    //   25: aload 4
    //   27: invokevirtual 98	java/io/File:canRead	()Z
    //   30: ifeq +77 -> 107
    //   33: aload 4
    //   35: invokevirtual 102	java/io/File:length	()J
    //   38: lconst_0
    //   39: lcmp
    //   40: ifle +67 -> 107
    //   43: aload 4
    //   45: invokevirtual 102	java/io/File:length	()J
    //   48: l2i
    //   49: newarray byte
    //   51: astore 5
    //   53: aconst_null
    //   54: astore 6
    //   56: new 104	java/io/FileInputStream
    //   59: dup
    //   60: aload 4
    //   62: invokespecial 107	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   65: astore 7
    //   67: aload 7
    //   69: aload 5
    //   71: invokevirtual 111	java/io/FileInputStream:read	([B)I
    //   74: pop
    //   75: aload_0
    //   76: aload 5
    //   78: invokestatic 117	com/sina/weibo/sdk/utils/Base64:encodebyte	([B)[B
    //   81: putfield 119	com/sina/weibo/sdk/component/ShareRequestParam:mBase64ImgData	[B
    //   84: aload 7
    //   86: ifnull +8 -> 94
    //   89: aload 7
    //   91: invokevirtual 123	java/io/FileInputStream:close	()V
    //   94: return
    //   95: astore 14
    //   97: aload 6
    //   99: ifnull +8 -> 107
    //   102: aload 6
    //   104: invokevirtual 123	java/io/FileInputStream:close	()V
    //   107: aload_2
    //   108: ifnull -14 -> 94
    //   111: aload_2
    //   112: arraylength
    //   113: ifle -19 -> 94
    //   116: aload_0
    //   117: aload_2
    //   118: invokestatic 117	com/sina/weibo/sdk/utils/Base64:encodebyte	([B)[B
    //   121: putfield 119	com/sina/weibo/sdk/component/ShareRequestParam:mBase64ImgData	[B
    //   124: return
    //   125: astore 10
    //   127: aload 6
    //   129: ifnull +8 -> 137
    //   132: aload 6
    //   134: invokevirtual 123	java/io/FileInputStream:close	()V
    //   137: aload 10
    //   139: athrow
    //   140: astore_3
    //   141: goto -34 -> 107
    //   144: astore 13
    //   146: return
    //   147: astore 9
    //   149: goto -42 -> 107
    //   152: astore 11
    //   154: goto -17 -> 137
    //   157: astore 10
    //   159: aload 7
    //   161: astore 6
    //   163: goto -36 -> 127
    //   166: astore 8
    //   168: aload 7
    //   170: astore 6
    //   172: goto -75 -> 97
    //
    // Exception table:
    //   from	to	target	type
    //   56	67	95	java/io/IOException
    //   56	67	125	finally
    //   0	53	140	java/lang/SecurityException
    //   89	94	140	java/lang/SecurityException
    //   102	107	140	java/lang/SecurityException
    //   132	137	140	java/lang/SecurityException
    //   137	140	140	java/lang/SecurityException
    //   89	94	144	java/lang/Exception
    //   102	107	147	java/lang/Exception
    //   132	137	152	java/lang/Exception
    //   67	84	157	finally
    //   67	84	166	java/io/IOException
  }

  private void handleSharedMessage(Bundle paramBundle)
  {
    WeiboMultiMessage localWeiboMultiMessage = new WeiboMultiMessage();
    localWeiboMultiMessage.toObject(paramBundle);
    StringBuilder localStringBuilder = new StringBuilder();
    if ((localWeiboMultiMessage.textObject instanceof TextObject))
      localStringBuilder.append(localWeiboMultiMessage.textObject.text);
    if ((localWeiboMultiMessage.imageObject instanceof ImageObject))
    {
      ImageObject localImageObject2 = localWeiboMultiMessage.imageObject;
      handleMblogPic(localImageObject2.imagePath, localImageObject2.imageData);
    }
    if ((localWeiboMultiMessage.mediaObject instanceof TextObject))
      localStringBuilder.append(((TextObject)localWeiboMultiMessage.mediaObject).text);
    if ((localWeiboMultiMessage.mediaObject instanceof ImageObject))
    {
      ImageObject localImageObject1 = (ImageObject)localWeiboMultiMessage.mediaObject;
      handleMblogPic(localImageObject1.imagePath, localImageObject1.imageData);
    }
    if ((localWeiboMultiMessage.mediaObject instanceof WebpageObject))
    {
      WebpageObject localWebpageObject = (WebpageObject)localWeiboMultiMessage.mediaObject;
      localStringBuilder.append(" ").append(localWebpageObject.actionUrl);
    }
    if ((localWeiboMultiMessage.mediaObject instanceof MusicObject))
    {
      MusicObject localMusicObject = (MusicObject)localWeiboMultiMessage.mediaObject;
      localStringBuilder.append(" ").append(localMusicObject.actionUrl);
    }
    if ((localWeiboMultiMessage.mediaObject instanceof VideoObject))
    {
      VideoObject localVideoObject = (VideoObject)localWeiboMultiMessage.mediaObject;
      localStringBuilder.append(" ").append(localVideoObject.actionUrl);
    }
    if ((localWeiboMultiMessage.mediaObject instanceof VoiceObject))
    {
      VoiceObject localVoiceObject = (VoiceObject)localWeiboMultiMessage.mediaObject;
      localStringBuilder.append(" ").append(localVoiceObject.actionUrl);
    }
    this.mShareContent = localStringBuilder.toString();
  }

  private void sendSdkResponse(Activity paramActivity, int paramInt, String paramString)
  {
    Bundle localBundle = paramActivity.getIntent().getExtras();
    if (localBundle == null)
      return;
    Intent localIntent = new Intent("com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY");
    localIntent.setFlags(131072);
    localIntent.setPackage(localBundle.getString("_weibo_appPackage"));
    localIntent.putExtras(localBundle);
    localIntent.putExtra("_weibo_appPackage", paramActivity.getPackageName());
    localIntent.putExtra("_weibo_resp_errcode", paramInt);
    localIntent.putExtra("_weibo_resp_errstr", paramString);
    try
    {
      paramActivity.startActivityForResult(localIntent, 765);
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
    }
  }

  public WeiboParameters buildUploadPicParam(WeiboParameters paramWeiboParameters)
  {
    if (!hasImage())
      return paramWeiboParameters;
    paramWeiboParameters.put("img", new String(this.mBase64ImgData));
    return paramWeiboParameters;
  }

  public String buildUrl(String paramString)
  {
    Uri.Builder localBuilder = Uri.parse("http://service.weibo.com/share/mobilesdk.php").buildUpon();
    localBuilder.appendQueryParameter("title", this.mShareContent);
    localBuilder.appendQueryParameter("version", "0030105000");
    if (!TextUtils.isEmpty(this.mAppKey))
      localBuilder.appendQueryParameter("source", this.mAppKey);
    if (!TextUtils.isEmpty(this.mToken))
      localBuilder.appendQueryParameter("access_token", this.mToken);
    String str = Utility.getAid(this.mContext, this.mAppKey);
    if (!TextUtils.isEmpty(str))
      localBuilder.appendQueryParameter("aid", str);
    if (!TextUtils.isEmpty(this.mAppPackage))
      localBuilder.appendQueryParameter("packagename", this.mAppPackage);
    if (!TextUtils.isEmpty(this.mHashKey))
      localBuilder.appendQueryParameter("key_hash", this.mHashKey);
    if (!TextUtils.isEmpty(paramString))
      localBuilder.appendQueryParameter("picinfo", paramString);
    return localBuilder.build().toString();
  }

  public void execRequest(Activity paramActivity, int paramInt)
  {
    if (paramInt == 3)
    {
      sendSdkCancleResponse(paramActivity);
      WeiboSdkBrowser.closeBrowser(paramActivity, this.mAuthListenerKey, null);
    }
  }

  public String getAppKey()
  {
    return this.mAppKey;
  }

  public String getAppPackage()
  {
    return this.mAppPackage;
  }

  public WeiboAuthListener getAuthListener()
  {
    return this.mAuthListener;
  }

  public String getAuthListenerKey()
  {
    return this.mAuthListenerKey;
  }

  public byte[] getBase64ImgData()
  {
    return this.mBase64ImgData;
  }

  public String getHashKey()
  {
    return this.mHashKey;
  }

  public String getShareContent()
  {
    return this.mShareContent;
  }

  public String getToken()
  {
    return this.mToken;
  }

  public boolean hasImage()
  {
    return (this.mBase64ImgData != null) && (this.mBase64ImgData.length > 0);
  }

  public void onCreateRequestParamBundle(Bundle paramBundle)
  {
    if (this.mBaseRequest != null)
      this.mBaseRequest.toBundle(paramBundle);
    if (!TextUtils.isEmpty(this.mAppPackage))
      this.mHashKey = MD5.hexdigest(Utility.getSign(this.mContext, this.mAppPackage));
    paramBundle.putString("access_token", this.mToken);
    paramBundle.putString("source", this.mAppKey);
    paramBundle.putString("packagename", this.mAppPackage);
    paramBundle.putString("key_hash", this.mHashKey);
    paramBundle.putString("_weibo_appPackage", this.mAppPackage);
    paramBundle.putString("_weibo_appKey", this.mAppKey);
    paramBundle.putInt("_weibo_flag", 538116905);
    paramBundle.putString("_weibo_sign", this.mHashKey);
    if (this.mAuthListener != null)
    {
      WeiboCallbackManager localWeiboCallbackManager = WeiboCallbackManager.getInstance(this.mContext);
      this.mAuthListenerKey = localWeiboCallbackManager.genCallbackKey();
      localWeiboCallbackManager.setWeiboAuthListener(this.mAuthListenerKey, this.mAuthListener);
      paramBundle.putString("key_listener", this.mAuthListenerKey);
    }
  }

  protected void onSetupRequestParam(Bundle paramBundle)
  {
    this.mAppKey = paramBundle.getString("source");
    this.mAppPackage = paramBundle.getString("packagename");
    this.mHashKey = paramBundle.getString("key_hash");
    this.mToken = paramBundle.getString("access_token");
    this.mAuthListenerKey = paramBundle.getString("key_listener");
    if (!TextUtils.isEmpty(this.mAuthListenerKey))
      this.mAuthListener = WeiboCallbackManager.getInstance(this.mContext).getWeiboAuthListener(this.mAuthListenerKey);
    handleSharedMessage(paramBundle);
    this.mUrl = buildUrl("");
  }

  public void sendSdkCancleResponse(Activity paramActivity)
  {
    sendSdkResponse(paramActivity, 1, "send cancel!!!");
  }

  public void sendSdkErrorResponse(Activity paramActivity, String paramString)
  {
    sendSdkResponse(paramActivity, 2, paramString);
  }

  public void sendSdkOkResponse(Activity paramActivity)
  {
    sendSdkResponse(paramActivity, 0, "send ok!!!");
  }

  public void setAppKey(String paramString)
  {
    this.mAppKey = paramString;
  }

  public void setAppPackage(String paramString)
  {
    this.mAppPackage = paramString;
  }

  public void setAuthListener(WeiboAuthListener paramWeiboAuthListener)
  {
    this.mAuthListener = paramWeiboAuthListener;
  }

  public void setBaseRequest(BaseRequest paramBaseRequest)
  {
    this.mBaseRequest = paramBaseRequest;
  }

  public void setToken(String paramString)
  {
    this.mToken = paramString;
  }

  public static class UploadPicResult
  {
    private int code = -2;
    private String picId;

    public static UploadPicResult parse(String paramString)
    {
      if (TextUtils.isEmpty(paramString))
        return null;
      UploadPicResult localUploadPicResult = new UploadPicResult();
      try
      {
        JSONObject localJSONObject = new JSONObject(paramString);
        localUploadPicResult.code = localJSONObject.optInt("code", -2);
        localUploadPicResult.picId = localJSONObject.optString("data", "");
        return localUploadPicResult;
      }
      catch (JSONException localJSONException)
      {
      }
      return localUploadPicResult;
    }

    public int getCode()
    {
      return this.code;
    }

    public String getPicId()
    {
      return this.picId;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.component.ShareRequestParam
 * JD-Core Version:    0.6.2
 */