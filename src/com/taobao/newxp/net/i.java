package com.taobao.newxp.net;

import android.text.TextUtils;
import com.taobao.munion.base.Log;
import com.taobao.munion.base.download.d;
import com.taobao.newxp.common.b.b;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class i extends d
{
  public i(String paramString)
  {
    super(paramString);
  }

  public static String a(String paramString1, String paramString2, String paramString3)
  {
    if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString3)))
    {
      int i = paramString1.indexOf(paramString2 + "=");
      if (i != -1)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString1.substring(0, i)).append(paramString2 + "=").append(paramString3);
        int j = paramString1.indexOf("&", i);
        if (j != -1)
          localStringBuilder.append(paramString1.substring(j));
        paramString1 = localStringBuilder.toString();
      }
    }
    return paramString1;
  }

  public String f()
  {
    String str1 = super.f();
    while (true)
    {
      try
      {
        String str2 = b.a();
        String str3 = str2.split(" ")[0];
        String str4 = str2.split(" ")[1];
        str1 = a(str1, "date", str3);
        str1 = a(str1, "time", str4);
        str1 = a(str1, "ts", "" + System.currentTimeMillis());
        Object localObject = a();
        StringBuilder localStringBuilder;
        if (localObject != null)
        {
          boolean bool1 = localObject.getClass().isArray();
          if (bool1)
            try
            {
              Long[] arrayOfLong = (Long[])localObject;
              long l1 = arrayOfLong[0].longValue();
              long l2 = arrayOfLong[1].longValue();
              long l3 = arrayOfLong[2].longValue();
              HashMap localHashMap = new HashMap();
              localHashMap.put("dsize", String.valueOf(l1));
              localHashMap.put("dtime", str4);
              boolean bool2 = l2 < 0L;
              float f = 0.0F;
              if (bool2)
                f = (float)l1 / (float)l2;
              localHashMap.put("dpcent", String.valueOf((int)(f * 100.0F)));
              localHashMap.put("ptimes", String.valueOf(l3));
              if (localHashMap == null)
                break label363;
              localStringBuilder = new StringBuilder(str1);
              Iterator localIterator = localHashMap.keySet().iterator();
              if (!localIterator.hasNext())
                continue;
              String str7 = (String)localIterator.next();
              localStringBuilder.append("&" + str7 + "=" + (String)localHashMap.get(str7));
              continue;
            }
            catch (Exception localException2)
            {
              Log.e(localException2, "format extra download params failed.", new Object[0]);
            }
        }
        return str1;
        String str5 = localStringBuilder.toString();
        str6 = str5;
        return str6;
      }
      catch (Exception localException1)
      {
        return str1;
      }
      label363: String str6 = str1;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.i
 * JD-Core Version:    0.6.2
 */