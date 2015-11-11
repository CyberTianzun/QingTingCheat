package fm.qingting.qtradio.weixin;

import android.content.Context;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import fm.qingting.social.api.WechatApi;

public class WeixinAgent
  implements IWXAPIEventHandler
{
  private static final String APP_ID = "wxaca5032bab461438";
  private static final String APP_SECRET = "b0ca9cd49f89b1a984bdf496e393c889";
  private static final String WEIXIN_SCOPE = "snsapi_userinfo";
  private static WeixinAgent _instance;
  private Context mContext;

  public static WeixinAgent getInstance()
  {
    if (_instance == null)
      _instance = new WeixinAgent();
    return _instance;
  }

  public void init(Context paramContext)
  {
    if (paramContext == null)
      return;
    this.mContext = paramContext;
    WechatApi.init(paramContext, null, null);
  }

  public boolean isWXInstalled()
  {
    return WechatApi.isWXAppInstalled();
  }

  public void login()
  {
    WechatApi.login();
  }

  public void onReq(BaseReq paramBaseReq)
  {
  }

  public void onResp(BaseResp paramBaseResp)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.weixin.WeixinAgent
 * JD-Core Version:    0.6.2
 */