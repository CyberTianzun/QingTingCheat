package fm.qingting.async.callback;

import fm.qingting.async.AsyncSocket;

public abstract interface ConnectCallback
{
  public abstract void onConnectCompleted(Exception paramException, AsyncSocket paramAsyncSocket);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.callback.ConnectCallback
 * JD-Core Version:    0.6.2
 */