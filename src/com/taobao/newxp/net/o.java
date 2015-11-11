package com.taobao.newxp.net;

import android.text.TextUtils;
import android.util.Log;
import com.taobao.munion.base.volley.i;
import com.taobao.munion.base.volley.k;
import com.taobao.munion.base.volley.n;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.b.b;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class o extends a
{
  Map<String, String> a;
  String b = "";
  MMEntity c;

  public o(d paramd, String paramString, com.taobao.munion.base.f paramf)
  {
    super(paramString, paramf);
    this.b = a(paramd, paramString);
    if ((paramd instanceof MMEntity))
      this.c = ((MMEntity)paramd);
  }

  public static String a(d paramd, String paramString)
  {
    try
    {
      com.taobao.munion.base.a locala = AlimmContext.getAliContext().getAppUtils();
      String str1 = b.a(paramString);
      String str2 = locala.g();
      String str3 = locala.f();
      String str4 = locala.r();
      String str5 = locala.C();
      String str6;
      if (TextUtils.isEmpty(paramd.appkey))
      {
        str6 = "";
        if (!TextUtils.isEmpty(paramd.slotId))
          break label243;
      }
      label243: for (String str7 = ""; ; str7 = paramd.slotId)
      {
        long l = System.currentTimeMillis() / 1000L;
        byte[] arrayOfByte = new byte[4];
        new Random().nextBytes(arrayOfByte);
        int i = 0xFF & arrayOfByte[0] | 0xFF00 & arrayOfByte[1] << 8 | arrayOfByte[2] << 24 >>> 8 | arrayOfByte[3] << 24;
        Object[] arrayOfObject = new Object[10];
        arrayOfObject[0] = str1;
        arrayOfObject[1] = str7;
        arrayOfObject[2] = str6;
        arrayOfObject[3] = str2;
        arrayOfObject[4] = str3;
        arrayOfObject[5] = str4;
        arrayOfObject[6] = "";
        arrayOfObject[7] = str5;
        arrayOfObject[8] = Long.valueOf(l);
        arrayOfObject[9] = Integer.valueOf(i);
        return URLEncoder.encode(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%d,%d", arrayOfObject), "UTF-8");
        str6 = paramd.appkey;
        break;
      }
    }
    catch (Exception localException)
    {
      Log.w(ExchangeConstants.LOG_TAG, "", localException);
    }
    return "";
  }

  protected n<JSONObject> a(i parami)
  {
    try
    {
      if ((this.c != null) && (this.c.timeline[1] == 0L))
        this.c.timeline[1] = System.currentTimeMillis();
      n localn = n.a(new JSONObject(new String(parami.b, com.taobao.munion.base.volley.a.f.a(parami.c))), com.taobao.munion.base.volley.a.f.a(parami));
      return localn;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      return n.a(new k(localUnsupportedEncodingException));
    }
    catch (JSONException localJSONException)
    {
      return n.a(new k(localJSONException));
    }
  }

  public Map<String, String> k()
    throws com.taobao.munion.base.volley.a
  {
    Map localMap = super.k();
    localMap.put("accept-ta", this.b);
    if ((this.c != null) && (this.c.timeline[0] == 0L))
      this.c.timeline[0] = System.currentTimeMillis();
    return localMap;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.o
 * JD-Core Version:    0.6.2
 */