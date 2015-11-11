package com.tencent.stat.common;

public class User
{
  private String mac = null;
  private int type;
  private String uid = null;

  public User(String paramString1, String paramString2, int paramInt)
  {
    this.uid = paramString1;
    this.mac = paramString2;
    this.type = paramInt;
  }

  public String getMac()
  {
    return this.mac;
  }

  public int getType()
  {
    return this.type;
  }

  public String getUid()
  {
    return this.uid;
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.common.User
 * JD-Core Version:    0.6.2
 */