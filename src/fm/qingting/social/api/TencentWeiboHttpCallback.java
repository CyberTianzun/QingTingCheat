package fm.qingting.social.api;

import com.tencent.weibo.sdk.android.network.HttpCallback;
import fm.qingting.social.ISocialEventListener;

class TencentWeiboHttpCallback
  implements HttpCallback
{
  private ISocialEventListener mRealListener;

  public TencentWeiboHttpCallback(ISocialEventListener paramISocialEventListener)
  {
    this.mRealListener = paramISocialEventListener;
  }

  public void onResult(Object paramObject)
  {
    if (this.mRealListener != null)
      this.mRealListener.onComplete(paramObject, null);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.social.api.TencentWeiboHttpCallback
 * JD-Core Version:    0.6.2
 */