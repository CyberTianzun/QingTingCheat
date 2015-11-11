package fm.qingting.async.http.callback;

import fm.qingting.async.http.AsyncHttpResponse;

public abstract interface HttpConnectCallback
{
  public abstract void onConnectCompleted(Exception paramException, AsyncHttpResponse paramAsyncHttpResponse);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.callback.HttpConnectCallback
 * JD-Core Version:    0.6.2
 */