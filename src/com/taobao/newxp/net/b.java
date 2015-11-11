package com.taobao.newxp.net;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.taobao.munion.base.f;
import com.taobao.newxp.Promoter;
import com.taobao.newxp.UFPResType;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.b.e;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class b<T extends d>
{
  AlimmContext a = AlimmContext.getAliContext();

  public static Location a(Context paramContext, com.taobao.munion.base.a parama)
  {
    Location localLocation1 = com.taobao.newxp.location.a.a(paramContext);
    Location localLocation2 = parama.F();
    if (localLocation1 == null);
    long l;
    do
    {
      return localLocation2;
      if (localLocation2 == null)
        return localLocation1;
      l = localLocation1.getTime();
    }
    while (localLocation2.getTime() - 300000L > l);
    return localLocation1;
  }

  public static String a(String paramString, Map<String, Object> paramMap)
  {
    StringBuilder localStringBuilder1 = new StringBuilder(paramString);
    Iterator localIterator = paramMap.keySet().iterator();
    if (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      while (true)
      {
        try
        {
          StringBuilder localStringBuilder2 = localStringBuilder1.append(URLEncoder.encode(str1, "utf-8")).append("=");
          if (paramMap.get(str1) != null)
            break label110;
          localObject = "";
          localStringBuilder2.append(URLEncoder.encode((String)localObject, "utf-8")).append("&");
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          android.util.Log.e("Alimama", "", localUnsupportedEncodingException);
        }
        break;
        label110: String str2 = paramMap.get(str1).toString();
        Object localObject = str2;
      }
    }
    if (localStringBuilder1.toString().endsWith("&"))
      localStringBuilder1.deleteCharAt(-1 + localStringBuilder1.length());
    return localStringBuilder1.toString().replaceAll(" ", "");
  }

  private <T extends Promoter> void a(Collection paramCollection, Class<T> paramClass, JSONObject paramJSONObject, String paramString)
  {
    com.taobao.newxp.common.Log.a(ExchangeConstants.LOG_TAG, "get promoters use class " + paramClass.toString());
    try
    {
      if (paramJSONObject.has("promoters"))
      {
        JSONArray localJSONArray = paramJSONObject.getJSONArray("promoters");
        for (int i = 0; i < localJSONArray.length(); i++)
        {
          Promoter localPromoter = Promoter.getPromoterFromJson((JSONObject)localJSONArray.get(i), paramClass);
          localPromoter.slot_act_pams = paramString;
          paramCollection.add(localPromoter);
        }
      }
      com.taobao.newxp.common.Log.b(ExchangeConstants.LOG_TAG, "failed requesting");
      return;
    }
    catch (Exception localException)
    {
      com.taobao.newxp.common.Log.b(ExchangeConstants.LOG_TAG, "", localException);
    }
  }

  private final String b(T paramT, boolean paramBoolean)
  {
    Map localMap = a(paramT, paramBoolean);
    return a(n.e[0], localMap);
  }

  public int a(T paramT, Collection<? extends Promoter> paramCollection, Class<? extends Promoter> paramClass, JSONObject paramJSONObject)
  {
    int i = 0;
    if (paramJSONObject == null)
      com.taobao.newxp.common.Log.b(ExchangeConstants.LOG_TAG, "failed requesting");
    do
    {
      return i;
      i = paramJSONObject.optInt("status", 0);
      if (1 == i)
        paramT.warp(paramJSONObject);
    }
    while (paramCollection == null);
    if (paramClass == null)
      paramClass = Promoter.getAdapterPromoterClz(paramT.template, paramT.resType);
    a(paramCollection, paramClass, paramJSONObject, paramT.slot_act_params);
    return i;
  }

  public final o a(T paramT, f paramf)
  {
    return a(paramT, false, paramf);
  }

  public final o a(T paramT, boolean paramBoolean, f paramf)
  {
    return new o(paramT, b(paramT, paramBoolean), paramf);
  }

  public Map<String, Object> a(T paramT, boolean paramBoolean)
  {
    HashMap localHashMap1 = new HashMap();
    com.taobao.munion.base.a locala = this.a.getAppUtils();
    localHashMap1.put("sdk_version", ExchangeConstants.sdk_version);
    localHashMap1.put("sdk_channel", ExchangeConstants.SDK_CHANNEL);
    localHashMap1.put("protocol_version", ExchangeConstants.protocol_version);
    String str1;
    if (TextUtils.isEmpty(ExchangeConstants.CHANNEL))
      str1 = locala.f("MUNION_CHANNEL");
    while (true)
    {
      if (!TextUtils.isEmpty(str1))
        localHashMap1.put("channel", str1);
      localHashMap1.put("utdid", locala.C());
      localHashMap1.put("device_id", locala.r());
      localHashMap1.put("idmd5", com.taobao.newxp.common.b.b.a(locala.r()));
      localHashMap1.put("device_model", Build.MODEL);
      localHashMap1.put("os", "android");
      String str2 = locala.p();
      if (!TextUtils.isEmpty(str2))
        localHashMap1.put("mc", str2);
      localHashMap1.put("os_version", Build.VERSION.RELEASE);
      localHashMap1.put("locale", locala.n());
      localHashMap1.put("language", locala.m());
      localHashMap1.put("timezone", locala.o());
      localHashMap1.put("resolution", locala.t());
      String[] arrayOfString1 = locala.D();
      localHashMap1.put("access", arrayOfString1[0]);
      localHashMap1.put("access_subtype", arrayOfString1[1]);
      localHashMap1.put("carrier", locala.G());
      Location localLocation = a(this.a.getAppContext(), locala);
      if (localLocation != null)
      {
        localHashMap1.put("lat", String.valueOf(localLocation.getLatitude()));
        localHashMap1.put("lng", String.valueOf(localLocation.getLongitude()));
        localHashMap1.put("gps_type", localLocation.getProvider());
        localHashMap1.put("gpst", String.valueOf(localLocation.getTime()));
        localHashMap1.put("gps_accuracy", String.valueOf(localLocation.getAccuracy()));
      }
      localHashMap1.put("cpu", locala.H());
      String str3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
      String str4 = str3.split(" ")[0];
      String str5 = str3.split(" ")[1];
      localHashMap1.put("date", str4);
      localHashMap1.put("time", str5);
      localHashMap1.put("brand", Build.MANUFACTURER);
      localHashMap1.put("timezone", locala.o());
      localHashMap1.put("apnm", locala.g());
      localHashMap1.put("apvn", locala.h());
      localHashMap1.put("apvc", locala.i());
      localHashMap1.put("adnm", locala.f());
      label652: String[] arrayOfString2;
      if (!TextUtils.isEmpty(paramT.slotId))
      {
        localHashMap1.put("slot_id", paramT.slotId);
        if (!TextUtils.isEmpty(paramT.filterPromoter))
          localHashMap1.put("promoter", paramT.filterPromoter);
        localHashMap1.put("layout_type", Integer.valueOf(paramT.layoutType));
        if (!TextUtils.isEmpty(paramT.keywords))
          localHashMap1.put("keywords", e.b(paramT.keywords));
        if (!TextUtils.isEmpty(paramT.slot_act_params))
          arrayOfString2 = paramT.slot_act_params.split("&");
      }
      else
      {
        try
        {
          HashMap localHashMap2 = new HashMap();
          int j = arrayOfString2.length;
          int k = 0;
          while (true)
            if (k < j)
            {
              String[] arrayOfString3 = arrayOfString2[k].split("=");
              if (arrayOfString3.length == 2)
                localHashMap2.put(arrayOfString3[0], arrayOfString3[1]);
              k++;
              continue;
              str1 = ExchangeConstants.CHANNEL;
              break;
              if (!TextUtils.isEmpty(paramT.appkey))
              {
                localHashMap1.put("app_key", paramT.appkey);
                break label652;
              }
              com.taobao.newxp.common.Log.b(ExchangeConstants.LOG_TAG, "Both APPKEY and SLOTID are empty, please specify either one. Request aborted.");
              return null;
            }
          Iterator localIterator = localHashMap2.keySet().iterator();
          while (localIterator.hasNext())
          {
            String str8 = (String)localIterator.next();
            localHashMap1.put(str8, localHashMap2.get(str8));
          }
        }
        catch (Exception localException)
        {
        }
      }
    }
    if (!TextUtils.isEmpty(paramT.urlParams))
      localHashMap1.put("url_params", paramT.urlParams);
    if (!TextUtils.isEmpty(paramT.tag))
      localHashMap1.put("tags", paramT.tag);
    if (paramT.autofill != 1)
      localHashMap1.put("autofill", Integer.valueOf(paramT.autofill));
    if (!TextUtils.isEmpty(paramT.sid))
      localHashMap1.put("sid", paramT.sid);
    if (!TextUtils.isEmpty(paramT.psid))
      localHashMap1.put("psid", paramT.psid);
    int i;
    String str6;
    label1081: StringBuilder localStringBuilder;
    if (ExchangeConstants.DETAIL_PAGE)
    {
      i = 1;
      localHashMap1.put("req_imgs", Integer.valueOf(i));
      localHashMap1.put("req_desc", Integer.valueOf(1));
      if (paramT.resType != null)
        break label1203;
      str6 = "";
      localHashMap1.put("resource_type", str6);
      if (paramT.template != null)
      {
        localStringBuilder = new StringBuilder().append(paramT.template.toString());
        if (!TextUtils.isEmpty(paramT.templateAttrs))
          break label1215;
      }
    }
    label1203: label1215: for (String str7 = ""; ; str7 = "." + paramT.templateAttrs)
    {
      localHashMap1.put("template", str7);
      if (paramT.landing_type > 0)
        localHashMap1.put("landing_type", Integer.valueOf(paramT.landing_type));
      if (paramBoolean)
        localHashMap1.put("cache", Integer.valueOf(1));
      return localHashMap1;
      i = 0;
      break;
      str6 = paramT.resType.toString();
      break label1081;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.b
 * JD-Core Version:    0.6.2
 */