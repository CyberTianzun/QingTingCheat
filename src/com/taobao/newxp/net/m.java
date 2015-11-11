package com.taobao.newxp.net;

import com.taobao.munion.base.Log;
import com.taobao.munion.base.volley.s;
import com.taobao.newxp.common.AlimmContext;
import org.json.JSONObject;

public class m
{
  public void a(String paramString1, String paramString2, final q paramq)
  {
    q local1 = new q()
    {
      public void a(s paramAnonymouss)
      {
        Log.i("onErrorResponse ：" + paramAnonymouss.toString(), new Object[0]);
      }

      public void a(String paramAnonymousString)
      {
        if (paramAnonymousString != "")
          paramq.a(paramAnonymousString);
      }

      public void a(JSONObject paramAnonymousJSONObject)
      {
        Log.i("onResponse ：" + paramAnonymousJSONObject.toString(), new Object[0]);
      }
    };
    r localr = new r(new MMEntity(), paramString1, local1);
    localr.d(paramString2);
    c localc = AlimmContext.getAliContext().getRedirctQueue();
    k localk = (k)localc;
    localk.a(Boolean.valueOf(true));
    localk.a("simba.taobao.com");
    localc.a(localr);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.m
 * JD-Core Version:    0.6.2
 */