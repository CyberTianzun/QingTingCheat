package com.taobao.newxp.net;

import android.content.Context;
import com.taobao.munion.base.download.c;
import com.taobao.newxp.common.ExchangeConstants;

public class h extends c
{
  public h(Context paramContext, String paramString)
  {
    super(paramContext, "tb_munion", paramString);
    if (ExchangeConstants.RICH_NOTIFICATION)
    {
      a(true);
      return;
    }
    a(false);
  }

  public h a(s.a parama)
  {
    String[] arrayOfString1 = new String[1];
    arrayOfString1[0] = parama.a(7).a().f();
    c(arrayOfString1);
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = parama.a(1).a().f();
    a(arrayOfString2);
    String[] arrayOfString3 = new String[1];
    arrayOfString3[0] = parama.a(-2).a().f();
    b(arrayOfString3);
    String[] arrayOfString4 = new String[1];
    arrayOfString4[0] = parama.a(-2).a().f();
    e(arrayOfString4);
    c(i.class.getName());
    return this;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.h
 * JD-Core Version:    0.6.2
 */