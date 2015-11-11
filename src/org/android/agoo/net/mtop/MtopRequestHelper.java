package org.android.agoo.net.mtop;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.message.proguard.aN;
import com.umeng.message.proguard.aq;
import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.android.agoo.helper.PhoneHelper;
import org.json.JSONObject;

public class MtopRequestHelper
{
  public static final String SPLIT_STR = "&";
  private static final String a = "MtopRequestHelper";

  private static long a()
  {
    return System.currentTimeMillis() / 1000L;
  }

  private static String a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, long paramLong, String paramString7, String paramString8)
    throws Throwable
  {
    try
    {
      String str1 = aN.a(new ByteArrayInputStream(paramString1.getBytes("UTF-8")));
      StringBuffer localStringBuffer = new StringBuffer();
      if ((paramString8 != null) && (!"".equals(paramString8)))
      {
        localStringBuffer.append(paramString8);
        localStringBuffer.append("&");
      }
      localStringBuffer.append(paramString2);
      localStringBuffer.append("&");
      localStringBuffer.append(str1);
      localStringBuffer.append("&");
      localStringBuffer.append(paramString3);
      localStringBuffer.append("&");
      localStringBuffer.append(paramString4);
      localStringBuffer.append("&");
      localStringBuffer.append(paramString5);
      localStringBuffer.append("&");
      localStringBuffer.append(paramString6);
      localStringBuffer.append("&");
      localStringBuffer.append(aN.a(new ByteArrayInputStream(paramString7.getBytes("UTF-8"))));
      localStringBuffer.append("&");
      localStringBuffer.append(paramLong);
      String str2 = aN.a(new ByteArrayInputStream(localStringBuffer.toString().getBytes("UTF-8")));
      return str2;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }

  private static String a(Map<String, Object> paramMap)
  {
    return new JSONObject(paramMap).toString();
  }

  public static void checkAppKeyAndAppSecret(MtopRequest paramMtopRequest, String paramString1, String paramString2)
  {
    if ((!TextUtils.isEmpty(paramString1)) && (TextUtils.isEmpty(paramMtopRequest.getAppKey())))
      paramMtopRequest.setAppKey(paramString1);
    if ((!TextUtils.isEmpty(paramString2)) && (TextUtils.isEmpty(paramMtopRequest.getAppSecret())))
      paramMtopRequest.setAppSecret(paramString2);
  }

  public static aq getUrlWithRequestParams(Context paramContext, MtopRequest paramMtopRequest)
    throws Throwable
  {
    aq localaq = new aq();
    localaq.a("api", paramMtopRequest.getApi());
    localaq.a("v", paramMtopRequest.getV());
    long l = paramMtopRequest.getTime();
    if (l <= 0L)
      l = a();
    localaq.a("t", "" + l);
    String str1 = PhoneHelper.getImei(paramContext);
    localaq.a("imei", str1);
    String str2 = PhoneHelper.getImsi(paramContext);
    localaq.a("imsi", str2);
    localaq.a("ttid", paramMtopRequest.getTtId());
    localaq.a("appKey", paramMtopRequest.getAppKey());
    if (!TextUtils.isEmpty(paramMtopRequest.getDeviceId()))
      localaq.a("deviceId", paramMtopRequest.getDeviceId());
    Map localMap = paramMtopRequest.getSysParams();
    if (localMap != null)
    {
      Iterator localIterator = localMap.entrySet().iterator();
      if (localIterator != null)
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          if ((localEntry != null) && (!TextUtils.isEmpty((CharSequence)localEntry.getKey())) && (!TextUtils.isEmpty((CharSequence)localEntry.getValue())))
            localaq.a((String)localEntry.getKey(), (String)localEntry.getValue());
        }
    }
    String str3 = a(paramMtopRequest.getParams());
    String str4 = paramMtopRequest.getAppKey();
    String str5 = paramMtopRequest.getAppSecret();
    if (TextUtils.isEmpty(str4))
      throw new NullPointerException("appKey is null");
    if (paramMtopRequest.isHasSigin())
      localaq.a("sign", a(str4, str5, paramMtopRequest.getApi(), paramMtopRequest.getV(), str1, str2, l, str3, paramMtopRequest.getEcode()));
    localaq.a("data", str3);
    if ((paramMtopRequest.getSId() != null) || (!"".equals(paramMtopRequest.getSId())))
      localaq.a("sid", paramMtopRequest.getSId());
    return localaq;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.net.mtop.MtopRequestHelper
 * JD-Core Version:    0.6.2
 */