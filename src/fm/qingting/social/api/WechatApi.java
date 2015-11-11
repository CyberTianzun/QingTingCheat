package fm.qingting.social.api;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth.Req;
import com.tencent.mm.sdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import fm.qingting.social.ISocialEventListener;

public class WechatApi
{
  private static final String APP_ID = "wxaca5032bab461438";
  private static final String WEIXIN_SCOPE = "snsapi_userinfo";
  private static IWXAPI api = null;
  private static String mRegisterToken = null;

  public static void handleIntent(Context paramContext, Intent paramIntent, IWXAPIEventHandler paramIWXAPIEventHandler)
  {
    register(paramContext);
    api.handleIntent(paramIntent, paramIWXAPIEventHandler);
  }

  public static void init(Context paramContext, Intent paramIntent, IWXAPIEventHandler paramIWXAPIEventHandler)
  {
    register(paramContext);
  }

  public static boolean isWXAppInstalled()
  {
    if (api == null)
      return false;
    return api.isWXAppInstalled();
  }

  private static void log(String paramString)
  {
  }

  public static void login()
  {
    SendAuth.Req localReq = new SendAuth.Req();
    localReq.scope = "snsapi_userinfo";
    localReq.state = "Master";
    api.sendReq(localReq);
  }

  public static void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
  }

  public static void onReq(BaseReq paramBaseReq)
  {
  }

  public static void onResp(BaseResp paramBaseResp)
  {
    if ((paramBaseResp != null) && ((paramBaseResp instanceof SendAuth.Resp)));
  }

  private static void register(Context paramContext)
  {
    if (api == null)
    {
      api = WXAPIFactory.createWXAPI(paramContext, "wxaca5032bab461438", true);
      api.registerApp("wxaca5032bab461438");
    }
  }

  public static void shareAudio(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, Bitmap paramBitmap, Boolean paramBoolean, ISocialEventListener paramISocialEventListener)
  {
    register(paramContext);
    if (((!api.isWXAppInstalled()) || (!api.isWXAppSupportAPI())) && (paramISocialEventListener != null))
      paramISocialEventListener.onException(new Exception("wechat not installed."));
    if ((paramString1 == null) && (paramString2 == null))
      return;
    if (paramString3 == null)
      paramString3 = "有声世界,无限精彩";
    if (paramString4 == null)
      paramString4 = "有声世界,无限精彩";
    WXMusicObject localWXMusicObject = new WXMusicObject();
    localWXMusicObject.musicUrl = paramString1;
    localWXMusicObject.musicLowBandUrl = paramString1;
    localWXMusicObject.musicDataUrl = paramString2;
    localWXMusicObject.musicLowBandDataUrl = paramString2;
    WXMediaMessage localWXMediaMessage = new WXMediaMessage();
    localWXMediaMessage.mediaObject = localWXMusicObject;
    localWXMediaMessage.title = paramString3;
    localWXMediaMessage.description = paramString4;
    localWXMediaMessage.setThumbImage(paramBitmap);
    SendMessageToWX.Req localReq = new SendMessageToWX.Req();
    localReq.transaction = String.valueOf(System.currentTimeMillis());
    localReq.message = localWXMediaMessage;
    if (paramBoolean.booleanValue());
    for (int i = 1; ; i = 0)
    {
      localReq.scene = i;
      api.sendReq(localReq);
      return;
    }
  }

  public static void shareUrlToMoments(Context paramContext, String paramString1, String paramString2, String paramString3, Bitmap paramBitmap, ISocialEventListener paramISocialEventListener)
  {
    register(paramContext);
    if (((!api.isWXAppInstalled()) || (!api.isWXAppSupportAPI())) && (paramISocialEventListener != null))
      paramISocialEventListener.onException(new Exception("wechat not installed."));
    WXWebpageObject localWXWebpageObject = new WXWebpageObject();
    localWXWebpageObject.webpageUrl = paramString1;
    WXMediaMessage localWXMediaMessage = new WXMediaMessage();
    localWXMediaMessage.mediaObject = localWXWebpageObject;
    localWXMediaMessage.title = paramString2;
    localWXMediaMessage.description = paramString3;
    localWXMediaMessage.setThumbImage(paramBitmap);
    SendMessageToWX.Req localReq = new SendMessageToWX.Req();
    localReq.transaction = String.valueOf(System.currentTimeMillis());
    localReq.message = localWXMediaMessage;
    localReq.scene = 1;
    api.sendReq(localReq);
  }

  public static void shareWebPage(Context paramContext, String paramString1, String paramString2, String paramString3, Bitmap paramBitmap, Boolean paramBoolean, ISocialEventListener paramISocialEventListener)
  {
    register(paramContext);
    if (((!api.isWXAppInstalled()) || (!api.isWXAppSupportAPI())) && (paramISocialEventListener != null))
      paramISocialEventListener.onException(new Exception("wechat not installed."));
    WXWebpageObject localWXWebpageObject = new WXWebpageObject();
    localWXWebpageObject.webpageUrl = paramString1;
    WXMediaMessage localWXMediaMessage = new WXMediaMessage();
    localWXMediaMessage.mediaObject = localWXWebpageObject;
    localWXMediaMessage.title = paramString2;
    localWXMediaMessage.description = paramString3;
    localWXMediaMessage.setThumbImage(paramBitmap);
    SendMessageToWX.Req localReq = new SendMessageToWX.Req();
    localReq.transaction = String.valueOf(System.currentTimeMillis());
    localReq.message = localWXMediaMessage;
    if (paramBoolean.booleanValue());
    for (int i = 1; ; i = 0)
    {
      localReq.scene = i;
      api.sendReq(localReq);
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.social.api.WechatApi
 * JD-Core Version:    0.6.2
 */