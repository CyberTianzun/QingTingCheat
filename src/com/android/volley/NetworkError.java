package com.android.volley;

public class NetworkError extends VolleyError
{
  public NetworkError()
  {
  }

  public NetworkError(NetworkResponse paramNetworkResponse)
  {
    super(paramNetworkResponse);
  }

  public NetworkError(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.NetworkError
 * JD-Core Version:    0.6.2
 */