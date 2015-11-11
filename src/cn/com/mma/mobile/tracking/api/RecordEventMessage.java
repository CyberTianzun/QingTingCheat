package cn.com.mma.mobile.tracking.api;

import android.content.Context;
import android.content.SharedPreferences;
import cn.com.mma.mobile.tracking.bean.Argument;
import cn.com.mma.mobile.tracking.bean.Company;
import cn.com.mma.mobile.tracking.bean.Config;
import cn.com.mma.mobile.tracking.bean.Domain;
import cn.com.mma.mobile.tracking.bean.SDK;
import cn.com.mma.mobile.tracking.bean.SendEvent;
import cn.com.mma.mobile.tracking.bean.Signature;
import cn.com.mma.mobile.tracking.bean.Switch;
import cn.com.mma.mobile.tracking.util.CommonUtil;
import cn.com.mma.mobile.tracking.util.DeviceInfoUtil;
import cn.com.mma.mobile.tracking.util.LocationUtil;
import cn.com.mma.mobile.tracking.util.Logger;
import cn.com.mma.mobile.tracking.util.SdkConfigUpdateUtil;
import cn.com.mma.mobile.tracking.util.SharedPreferencedUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecordEventMessage
{
  private Context context;
  private Map<String, String> params;

  public RecordEventMessage(Context paramContext)
  {
    this.context = paramContext;
    this.params = DeviceInfoUtil.fulfillTrackingInfo(paramContext);
  }

  private long getExpirationTime(Company paramCompany, Long paramLong)
  {
    if (paramCompany.sswitch.offlineCacheExpiration != null)
      return 1000L * Long.parseLong(paramCompany.sswitch.offlineCacheExpiration.trim()) + paramLong.longValue();
    return 86400000L + paramLong.longValue();
  }

  private void recordEvent(SendEvent paramSendEvent)
  {
    this.params.put("LBS", LocationUtil.getInstance(this.context).getLocation());
    Object localObject = new StringBuilder();
    String str1 = CommonUtil.getHostURL(paramSendEvent.getUrl());
    SDK localSDK = SdkConfigUpdateUtil.getSdk(this.context);
    Iterator localIterator1;
    if ((localSDK != null) && (localSDK.companies != null))
      localIterator1 = localSDK.companies.iterator();
    while (true)
    {
      if (!localIterator1.hasNext())
        return;
      Company localCompany = (Company)localIterator1.next();
      if (str1.endsWith(localCompany.domain.url))
      {
        ArrayList localArrayList1 = new ArrayList();
        String str2 = "";
        String str3 = "";
        Iterator localIterator2 = localCompany.config.arguments.iterator();
        label144: Iterator localIterator3;
        label199: StringBuilder localStringBuilder1;
        String str4;
        StringBuilder localStringBuilder2;
        if (!localIterator2.hasNext())
        {
          ((StringBuilder)localObject).append((String)CommonUtil.removeExistArgmentAndGetRedirectURL(paramSendEvent.getUrl(), localArrayList1, str2, str3, "").get("URL"));
          localIterator3 = localCompany.config.arguments.iterator();
          if (localIterator3.hasNext())
            break label457;
          ArrayList localArrayList2 = new ArrayList();
          localStringBuilder1 = new StringBuilder(CommonUtil.removeExistEvent(((StringBuilder)localObject).toString(), localArrayList2, str2, str3));
          localStringBuilder1.append("");
          if ((localCompany.signature != null) && (localCompany.signature.paramKey != null))
          {
            str4 = CommonUtil.getSignature(this.context, localStringBuilder1.toString());
            localStringBuilder2 = new StringBuilder(String.valueOf(localCompany.separator)).append(localCompany.signature.paramKey);
            if (localCompany.equalizer == null)
              break label771;
          }
        }
        label771: for (String str5 = localCompany.equalizer; ; str5 = "")
        {
          localStringBuilder1.append(str5 + CommonUtil.encodingUTF8(str4));
          Logger.d("mma_request_url" + localStringBuilder1.toString());
          SharedPreferencedUtil.putLong(this.context, "cn.com.mma.mobile.tracking.normal", localStringBuilder1.toString().trim(), getExpirationTime(localCompany, Long.valueOf(paramSendEvent.getTimestamp())));
          localObject = localStringBuilder1;
          break;
          Argument localArgument1 = (Argument)localIterator2.next();
          if (!localArgument1.isRequired)
            break label144;
          str2 = localCompany.separator;
          str3 = localCompany.equalizer;
          localArrayList1.add(localArgument1.value);
          break label144;
          label457: Argument localArgument2 = (Argument)localIterator3.next();
          if (!localArgument2.isRequired)
            break label199;
          if ("TS".equals(localArgument2.key))
          {
            StringBuilder localStringBuilder5 = new StringBuilder(String.valueOf(localCompany.separator)).append(localArgument2.value);
            String str8;
            label530: StringBuilder localStringBuilder6;
            if (localCompany.equalizer != null)
            {
              str8 = localCompany.equalizer;
              localStringBuilder6 = localStringBuilder5.append(str8);
              if (!localCompany.timeStampUseSecond)
                break label578;
            }
            label578: for (long l = paramSendEvent.getTimestamp(); ; l = paramSendEvent.getTimestamp())
            {
              ((StringBuilder)localObject).append(l);
              break;
              str8 = "";
              break label530;
            }
          }
          if ("MUDS".equals(localArgument2.key))
          {
            StringBuilder localStringBuilder4 = new StringBuilder(String.valueOf(localCompany.separator)).append(localArgument2.value);
            if (localCompany.equalizer != null);
            for (String str7 = localCompany.equalizer; ; str7 = "")
            {
              ((StringBuilder)localObject).append(str7 + CommonUtil.encodingUTF8(paramSendEvent.muds, localArgument2, localCompany));
              break;
            }
          }
          StringBuilder localStringBuilder3 = new StringBuilder(String.valueOf(localCompany.separator)).append(localArgument2.value);
          if (localCompany.equalizer != null);
          for (String str6 = localCompany.equalizer; ; str6 = "")
          {
            ((StringBuilder)localObject).append(str6 + CommonUtil.encodingUTF8((String)this.params.get(localArgument2.key), localArgument2, localCompany));
            break;
          }
        }
      }
      Logger.d("domain不匹配" + str1 + " company.domain.url:" + localCompany.domain.url);
    }
  }

  public void recordEventWithUrl(String paramString)
  {
    if (DeviceInfoUtil.isEmulator(this.context))
      Logger.d("模拟器不记录，不发送数据");
    do
    {
      return;
      SendEvent localSendEvent = new SendEvent();
      localSendEvent.setTimestamp(System.currentTimeMillis());
      localSendEvent.setUrl(paramString);
      recordEvent(localSendEvent);
    }
    while (SharedPreferencedUtil.getSharedPreferences(this.context, "cn.com.mma.mobile.tracking.normal").getAll().keySet().size() < Global.OFFLINECACHE_LENGTH);
    SendEventMessage.getSendEventMessage(this.context).sendNromalList();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.mma.mobile.tracking.api.RecordEventMessage
 * JD-Core Version:    0.6.2
 */