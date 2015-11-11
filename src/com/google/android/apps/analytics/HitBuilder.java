package com.google.android.apps.analytics;

import java.util.Locale;

class HitBuilder
{
  static final String FAKE_DOMAIN_HASH = "1";
  private static final String GOOGLE_ANALYTICS_GIF_PATH = "/__utm.gif";
  private static final int X10_PROJECT_NAMES = 8;
  private static final int X10_PROJECT_SCOPES = 11;
  private static final int X10_PROJECT_VALUES = 9;

  static void appendCurrencyValue(StringBuilder paramStringBuilder, String paramString, double paramDouble)
  {
    paramStringBuilder.append(paramString).append("=");
    double d = Math.floor(0.5D + paramDouble * 1000000.0D) / 1000000.0D;
    if (d != 0.0D)
      paramStringBuilder.append(Double.toString(d));
  }

  private static void appendStringValue(StringBuilder paramStringBuilder, String paramString1, String paramString2)
  {
    paramStringBuilder.append(paramString1).append("=");
    if ((paramString2 != null) && (paramString2.trim().length() > 0))
      paramStringBuilder.append(AnalyticsParameterEncoder.encode(paramString2));
  }

  private static String constructEventRequestPath(Event paramEvent, Referrer paramReferrer)
  {
    Locale localLocale = Locale.getDefault();
    StringBuilder localStringBuilder1 = new StringBuilder();
    StringBuilder localStringBuilder2 = new StringBuilder();
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = encode(paramEvent.category);
    arrayOfObject1[1] = encode(paramEvent.action);
    localStringBuilder2.append(String.format("5(%s*%s", arrayOfObject1));
    if (paramEvent.label != null)
      localStringBuilder2.append("*").append(encode(paramEvent.label));
    localStringBuilder2.append(")");
    if (paramEvent.value > -1)
    {
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = Integer.valueOf(paramEvent.value);
      localStringBuilder2.append(String.format("(%d)", arrayOfObject4));
    }
    localStringBuilder2.append(getCustomVariableParams(paramEvent));
    localStringBuilder1.append("/__utm.gif");
    localStringBuilder1.append("?utmwv=4.8.1ma");
    localStringBuilder1.append("&utmn=").append(paramEvent.getRandomVal());
    localStringBuilder1.append("&utmt=event");
    localStringBuilder1.append("&utme=").append(localStringBuilder2.toString());
    localStringBuilder1.append("&utmcs=UTF-8");
    Object[] arrayOfObject2 = new Object[2];
    arrayOfObject2[0] = Integer.valueOf(paramEvent.screenWidth);
    arrayOfObject2[1] = Integer.valueOf(paramEvent.screenHeight);
    localStringBuilder1.append(String.format("&utmsr=%dx%d", arrayOfObject2));
    Object[] arrayOfObject3 = new Object[2];
    arrayOfObject3[0] = localLocale.getLanguage();
    arrayOfObject3[1] = localLocale.getCountry();
    localStringBuilder1.append(String.format("&utmul=%s-%s", arrayOfObject3));
    localStringBuilder1.append("&utmac=").append(paramEvent.accountId);
    localStringBuilder1.append("&utmcc=").append(getEscapedCookieString(paramEvent, paramReferrer));
    if (paramEvent.getAdHitId() != 0)
      localStringBuilder1.append("&utmhid=").append(paramEvent.getAdHitId());
    return localStringBuilder1.toString();
  }

  public static String constructHitRequestPath(Event paramEvent, Referrer paramReferrer)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ("__##GOOGLEPAGEVIEW##__".equals(paramEvent.category))
      localStringBuilder.append(constructPageviewRequestPath(paramEvent, paramReferrer));
    while (true)
    {
      if (paramEvent.getAnonymizeIp())
        localStringBuilder.append("&aip=1");
      if (!paramEvent.getUseServerTime())
        localStringBuilder.append("&utmht=" + System.currentTimeMillis());
      return localStringBuilder.toString();
      if ("__##GOOGLEITEM##__".equals(paramEvent.category))
        localStringBuilder.append(constructItemRequestPath(paramEvent, paramReferrer));
      else if ("__##GOOGLETRANSACTION##__".equals(paramEvent.category))
        localStringBuilder.append(constructTransactionRequestPath(paramEvent, paramReferrer));
      else
        localStringBuilder.append(constructEventRequestPath(paramEvent, paramReferrer));
    }
  }

  private static String constructItemRequestPath(Event paramEvent, Referrer paramReferrer)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/__utm.gif");
    localStringBuilder.append("?utmwv=4.8.1ma");
    localStringBuilder.append("&utmn=").append(paramEvent.getRandomVal());
    localStringBuilder.append("&utmt=item");
    Item localItem = paramEvent.getItem();
    if (localItem != null)
    {
      appendStringValue(localStringBuilder, "&utmtid", localItem.getOrderId());
      appendStringValue(localStringBuilder, "&utmipc", localItem.getItemSKU());
      appendStringValue(localStringBuilder, "&utmipn", localItem.getItemName());
      appendStringValue(localStringBuilder, "&utmiva", localItem.getItemCategory());
      appendCurrencyValue(localStringBuilder, "&utmipr", localItem.getItemPrice());
      localStringBuilder.append("&utmiqt=");
      if (localItem.getItemCount() != 0L)
        localStringBuilder.append(localItem.getItemCount());
    }
    localStringBuilder.append("&utmac=").append(paramEvent.accountId);
    localStringBuilder.append("&utmcc=").append(getEscapedCookieString(paramEvent, paramReferrer));
    return localStringBuilder.toString();
  }

  private static String constructPageviewRequestPath(Event paramEvent, Referrer paramReferrer)
  {
    String str1 = "";
    if (paramEvent.action != null)
      str1 = paramEvent.action;
    if (!str1.startsWith("/"))
      str1 = "/" + str1;
    String str2 = encode(str1);
    String str3 = getCustomVariableParams(paramEvent);
    Locale localLocale = Locale.getDefault();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/__utm.gif");
    localStringBuilder.append("?utmwv=4.8.1ma");
    localStringBuilder.append("&utmn=").append(paramEvent.getRandomVal());
    if (str3.length() > 0)
      localStringBuilder.append("&utme=").append(str3);
    localStringBuilder.append("&utmcs=UTF-8");
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(paramEvent.screenWidth);
    arrayOfObject1[1] = Integer.valueOf(paramEvent.screenHeight);
    localStringBuilder.append(String.format("&utmsr=%dx%d", arrayOfObject1));
    Object[] arrayOfObject2 = new Object[2];
    arrayOfObject2[0] = localLocale.getLanguage();
    arrayOfObject2[1] = localLocale.getCountry();
    localStringBuilder.append(String.format("&utmul=%s-%s", arrayOfObject2));
    localStringBuilder.append("&utmp=").append(str2);
    localStringBuilder.append("&utmac=").append(paramEvent.accountId);
    localStringBuilder.append("&utmcc=").append(getEscapedCookieString(paramEvent, paramReferrer));
    if (paramEvent.getAdHitId() != 0)
      localStringBuilder.append("&utmhid=").append(paramEvent.getAdHitId());
    return localStringBuilder.toString();
  }

  private static String constructTransactionRequestPath(Event paramEvent, Referrer paramReferrer)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/__utm.gif");
    localStringBuilder.append("?utmwv=4.8.1ma");
    localStringBuilder.append("&utmn=").append(paramEvent.getRandomVal());
    localStringBuilder.append("&utmt=tran");
    Transaction localTransaction = paramEvent.getTransaction();
    if (localTransaction != null)
    {
      appendStringValue(localStringBuilder, "&utmtid", localTransaction.getOrderId());
      appendStringValue(localStringBuilder, "&utmtst", localTransaction.getStoreName());
      appendCurrencyValue(localStringBuilder, "&utmtto", localTransaction.getTotalCost());
      appendCurrencyValue(localStringBuilder, "&utmttx", localTransaction.getTotalTax());
      appendCurrencyValue(localStringBuilder, "&utmtsp", localTransaction.getShippingCost());
      appendStringValue(localStringBuilder, "&utmtci", "");
      appendStringValue(localStringBuilder, "&utmtrg", "");
      appendStringValue(localStringBuilder, "&utmtco", "");
    }
    localStringBuilder.append("&utmac=").append(paramEvent.accountId);
    localStringBuilder.append("&utmcc=").append(getEscapedCookieString(paramEvent, paramReferrer));
    return localStringBuilder.toString();
  }

  private static void createX10Project(CustomVariable[] paramArrayOfCustomVariable, StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder.append(paramInt).append("(");
    int i = 1;
    int j = 0;
    if (j < paramArrayOfCustomVariable.length)
    {
      CustomVariable localCustomVariable;
      if (paramArrayOfCustomVariable[j] != null)
      {
        localCustomVariable = paramArrayOfCustomVariable[j];
        if (i != 0)
          break label102;
        paramStringBuilder.append("*");
        label50: paramStringBuilder.append(localCustomVariable.getIndex()).append("!");
        switch (paramInt)
        {
        case 10:
        default:
        case 8:
        case 9:
        case 11:
        }
      }
      while (true)
      {
        j++;
        break;
        label102: i = 0;
        break label50;
        paramStringBuilder.append(x10Escape(encode(localCustomVariable.getName())));
        continue;
        paramStringBuilder.append(x10Escape(encode(localCustomVariable.getValue())));
        continue;
        paramStringBuilder.append(localCustomVariable.getScope());
      }
    }
    paramStringBuilder.append(")");
  }

  private static String encode(String paramString)
  {
    return AnalyticsParameterEncoder.encode(paramString);
  }

  public static String getCustomVariableParams(Event paramEvent)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    CustomVariableBuffer localCustomVariableBuffer = paramEvent.getCustomVariableBuffer();
    if (localCustomVariableBuffer == null)
      return "";
    if (!localCustomVariableBuffer.hasCustomVariables())
      return "";
    CustomVariable[] arrayOfCustomVariable = localCustomVariableBuffer.getCustomVariableArray();
    createX10Project(arrayOfCustomVariable, localStringBuilder, 8);
    createX10Project(arrayOfCustomVariable, localStringBuilder, 9);
    createX10Project(arrayOfCustomVariable, localStringBuilder, 11);
    return localStringBuilder.toString();
  }

  public static String getEscapedCookieString(Event paramEvent, Referrer paramReferrer)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("__utma=");
    localStringBuilder.append("1").append(".");
    localStringBuilder.append(paramEvent.getUserId()).append(".");
    localStringBuilder.append(paramEvent.getTimestampFirst()).append(".");
    localStringBuilder.append(paramEvent.getTimestampPrevious()).append(".");
    localStringBuilder.append(paramEvent.getTimestampCurrent()).append(".");
    localStringBuilder.append(paramEvent.getVisits()).append(";");
    if (paramReferrer != null)
    {
      localStringBuilder.append("+__utmz=");
      localStringBuilder.append("1").append(".");
      localStringBuilder.append(paramReferrer.getTimeStamp()).append(".");
      localStringBuilder.append(Integer.valueOf(paramReferrer.getVisit()).toString()).append(".");
      localStringBuilder.append(Integer.valueOf(paramReferrer.getIndex()).toString()).append(".");
      localStringBuilder.append(paramReferrer.getReferrerString()).append(";");
    }
    return encode(localStringBuilder.toString());
  }

  private static String x10Escape(String paramString)
  {
    return paramString.replace("'", "'0").replace(")", "'1").replace("*", "'2").replace("!", "'3");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.analytics.HitBuilder
 * JD-Core Version:    0.6.2
 */