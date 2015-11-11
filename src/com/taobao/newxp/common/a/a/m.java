package com.taobao.newxp.common.a.a;

import java.util.ArrayList;

public class m
{
  String a;
  int b = 0;
  public ArrayList<String> c;

  public m(String paramString)
  {
    this.a = paramString;
    this.c = new ArrayList();
  }

  public int a()
  {
    ArrayList localArrayList = this.c;
    int i = 0;
    if (localArrayList != null)
      i = this.c.size();
    return i;
  }

  public void a(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0) && (!this.c.contains(paramString.trim())))
      this.c.add(paramString.trim());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.a.a.m
 * JD-Core Version:    0.6.2
 */