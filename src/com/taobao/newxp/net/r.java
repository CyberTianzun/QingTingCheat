package com.taobao.newxp.net;

import android.text.TextUtils;
import com.taobao.munion.base.Log;
import com.taobao.munion.base.volley.a;
import com.taobao.munion.base.volley.a.f;
import com.taobao.munion.base.volley.i;
import com.taobao.munion.base.volley.n;
import java.util.Map;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

public class r extends o
{
  private String d = "";
  private q e;

  public r(d paramd, String paramString, q paramq)
  {
    super(paramd, paramString, paramq);
    this.e = paramq;
  }

  protected n<JSONObject> a(i parami)
  {
    String str = (String)parami.c.get("Location");
    if ((this.e != null) && (str != ""))
      this.e.a(str);
    return n.a(new JSONObject(), f.a(parami));
  }

  public void d(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "";
    this.d = paramString;
  }

  public Map<String, String> k()
    throws a
  {
    Map localMap = super.k();
    Log.i("referer : " + this.d, new Object[0]);
    localMap.put("referer", this.d);
    new BasicHttpParams();
    return localMap;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.r
 * JD-Core Version:    0.6.2
 */