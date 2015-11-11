package com.taobao.newxp.common.a.a;

import java.util.HashMap;

public class n
  implements l
{
  private HashMap<String, m> a = new HashMap(7);

  public HashMap<String, m> a()
  {
    return this.a;
  }

  public void a(a parama)
  {
    String str;
    if (parama != null)
    {
      str = parama.b;
      if ((str != null) && (str.trim().length() > 0))
      {
        m localm1 = (m)this.a.get(str);
        if (localm1 == null)
          break label48;
        localm1.a(parama.c);
      }
    }
    return;
    label48: m localm2 = new m(str);
    localm2.a(parama.c);
    this.a.put(str, localm2);
  }

  public void b()
  {
    this.a.clear();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.a.a.n
 * JD-Core Version:    0.6.2
 */