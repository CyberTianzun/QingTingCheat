package fm.qingting.qtradio.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import fm.qingting.social.api.WechatApi;

public class WXEntryActivity extends Activity
  implements IWXAPIEventHandler
{
  private void log(String paramString)
  {
  }

  private void sendIntent()
  {
    Intent localIntent = new Intent();
    localIntent.setAction("android.intent.action.WECHAT_RESP");
    sendBroadcast(localIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    WechatApi.handleIntent(this, getIntent(), this);
    finish();
  }

  public void onGetMessageFromWXReq(BaseReq paramBaseReq)
  {
    startActivity(getPackageManager().getLaunchIntentForPackage(getPackageName()));
  }

  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    setIntent(paramIntent);
    WechatApi.handleIntent(this, paramIntent, this);
    finish();
  }

  public void onReq(BaseReq paramBaseReq)
  {
    switch (paramBaseReq.getType())
    {
    case 3:
    case 4:
    }
    onGetMessageFromWXReq(paramBaseReq);
    finish();
  }

  public void onResp(BaseResp paramBaseResp)
  {
    onShowMessageFromWXReq(paramBaseResp);
    WechatApi.onResp(paramBaseResp);
    finish();
  }

  public void onShowMessageFromWXReq(BaseResp paramBaseResp)
  {
    switch (paramBaseResp.errCode)
    {
    case -4:
    case -3:
    case -2:
    case -1:
    default:
      return;
    case 0:
    }
    sendIntent();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.wxapi.WXEntryActivity
 * JD-Core Version:    0.6.2
 */