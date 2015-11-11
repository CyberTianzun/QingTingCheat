package com.google.android.apps.analytics;

public class Hit
{
  final long hitId;
  final String hitString;

  Hit(String paramString, long paramLong)
  {
    this.hitString = paramString;
    this.hitId = paramLong;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.analytics.Hit
 * JD-Core Version:    0.6.2
 */