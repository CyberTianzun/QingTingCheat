package fm.qingting.qtradio.model;

import fm.qingting.framework.data.Result;

public abstract interface IWeiboResultRecvHandler
{
  public abstract void onWeiboRecvResult(Result paramResult, WeiboResultToken paramWeiboResultToken);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.IWeiboResultRecvHandler
 * JD-Core Version:    0.6.2
 */