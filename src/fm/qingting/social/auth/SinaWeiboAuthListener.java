package fm.qingting.social.auth;

import android.os.Bundle;
import android.util.Log;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import fm.qingting.social.ISocialEventListener;

public class SinaWeiboAuthListener
  implements WeiboAuthListener
{
  protected ISocialEventListener mRealListener = null;

  public SinaWeiboAuthListener(ISocialEventListener paramISocialEventListener)
  {
    this.mRealListener = paramISocialEventListener;
  }

  public void onCancel()
  {
    Log.d("ZHENLI", "WeiboAuth onCancel");
    if (this.mRealListener != null)
      this.mRealListener.onCancel(null);
  }

  public void onComplete(Bundle paramBundle)
  {
    Oauth2AccessToken localOauth2AccessToken = Oauth2AccessToken.parseAccessToken(paramBundle);
    SinaWeiboAuth.setToken(localOauth2AccessToken);
    Log.d("ZHENLI", "WeiboAuth onComplete token: " + localOauth2AccessToken.getToken());
    if (this.mRealListener != null)
      this.mRealListener.onComplete(localOauth2AccessToken, null);
  }

  public void onWeiboException(WeiboException paramWeiboException)
  {
    Log.d("ZHENLI", "WeiboException: " + paramWeiboException);
    if (this.mRealListener != null)
      this.mRealListener.onException(null);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.social.auth.SinaWeiboAuthListener
 * JD-Core Version:    0.6.2
 */