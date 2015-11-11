package fm.qingting.async.http;

import fm.qingting.async.AsyncSocket;

public abstract interface WebSocket extends AsyncSocket
{
  public abstract AsyncSocket getSocket();

  public abstract StringCallback getStringCallback();

  public abstract boolean isBuffering();

  public abstract void send(String paramString);

  public abstract void send(byte[] paramArrayOfByte);

  public abstract void setStringCallback(StringCallback paramStringCallback);

  public static abstract interface StringCallback
  {
    public abstract void onStringAvailable(String paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.WebSocket
 * JD-Core Version:    0.6.2
 */