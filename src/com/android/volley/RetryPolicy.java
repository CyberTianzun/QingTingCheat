package com.android.volley;

public abstract interface RetryPolicy
{
  public abstract int getCurrentRetryCount();

  public abstract int getCurrentTimeout();

  public abstract void retry(VolleyError paramVolleyError)
    throws VolleyError;
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.RetryPolicy
 * JD-Core Version:    0.6.2
 */