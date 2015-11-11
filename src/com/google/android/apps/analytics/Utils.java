package com.google.android.apps.analytics;

import android.net.Uri;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

class Utils
{
  static String addQueueTimeParameter(String paramString, long paramLong)
  {
    String str1 = Uri.parse(paramString).getQueryParameter("utmht");
    if (str1 != null);
    try
    {
      Long localLong = Long.valueOf(Long.parseLong(str1));
      String str2 = paramString + "&utmqt=" + (paramLong - localLong.longValue());
      paramString = str2;
      return paramString;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Log.e("GoogleAnalyticsTracker", "Error parsing utmht parameter: " + localNumberFormatException.toString());
    }
    return paramString;
  }

  static Map<String, String> parseURLParameters(String paramString)
  {
    HashMap localHashMap = new HashMap();
    String[] arrayOfString1 = paramString.split("&");
    int i = arrayOfString1.length;
    int j = 0;
    if (j < i)
    {
      String[] arrayOfString2 = arrayOfString1[j].split("=");
      if (arrayOfString2.length > 1)
        localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
      while (true)
      {
        j++;
        break;
        if (arrayOfString2.length == 1)
          localHashMap.put(arrayOfString2[0], null);
      }
    }
    return localHashMap;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.analytics.Utils
 * JD-Core Version:    0.6.2
 */