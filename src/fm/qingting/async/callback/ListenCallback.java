package fm.qingting.async.callback;

import fm.qingting.async.AsyncServerSocket;
import fm.qingting.async.AsyncSocket;

public abstract interface ListenCallback extends CompletedCallback
{
  public abstract void onAccepted(AsyncSocket paramAsyncSocket);

  public abstract void onListening(AsyncServerSocket paramAsyncServerSocket);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.callback.ListenCallback
 * JD-Core Version:    0.6.2
 */