package cn.com.mma.mobile.tracking.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import cn.com.mma.mobile.tracking.bean.Argument;
import cn.com.mma.mobile.tracking.bean.Company;
import cn.com.mma.mobile.tracking.bean.Switch;
import cn.mmachina.JniClient;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil
{
  public static String encodingUTF8(String paramString)
  {
    if (paramString == null)
      return "";
    try
    {
      String str = URLEncoder.encode(paramString, "utf-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return "";
  }

  public static String encodingUTF8(String paramString, Argument paramArgument, Company paramCompany)
  {
    label121: 
    do
    {
      while (true)
      {
        try
        {
          if ((paramCompany.sswitch.encrypt.containsKey(paramArgument.key)) && ("md5".equals(paramCompany.sswitch.encrypt.get(paramArgument.key))))
          {
            if (!"MAC".equals(paramArgument.key))
              break label121;
            if (paramString == null)
            {
              paramString = "";
              break label121;
            }
          }
          else
          {
            if (paramArgument.urlEncode)
              break label133;
            if (paramString != null)
              break;
            return "";
          }
          paramString = paramString.replaceAll(":", "");
          break label121;
          paramString = md5(paramString.toUpperCase());
          continue;
          String str1 = URLEncoder.encode(paramString, "utf-8");
          str2 = str1;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          return "";
        }
        if (paramString == null)
          paramString = "";
      }
      return paramString;
    }
    while (paramString != null);
    label133: String str2 = "";
    return str2;
  }

  public static String getHostURL(String paramString)
  {
    String str = "";
    Matcher localMatcher = Pattern.compile("^([\\w\\d]+):\\/\\/([\\w\\d\\-_]+(?:\\.[\\w\\d\\-_]+)*)").matcher(paramString);
    if (localMatcher.find())
      str = localMatcher.group(0);
    return str;
  }

  public static String getSignature(Context paramContext, String paramString)
  {
    paramString.toLowerCase();
    return JniClient.MDString("", paramContext, paramString);
  }

  public static boolean isMobileConnected(Context paramContext)
  {
    boolean bool = false;
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(0);
      bool = false;
      if (localNetworkInfo != null)
        bool = localNetworkInfo.isAvailable();
    }
    return bool;
  }

  public static boolean isNetWorkEnable(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.isConnectedOrConnecting());
  }

  public static boolean isWifiConnected(Context paramContext)
  {
    boolean bool = false;
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1);
      bool = false;
      if (localNetworkInfo != null)
        bool = localNetworkInfo.isAvailable();
    }
    return bool;
  }

  public static String md5(String paramString)
  {
    Object localObject = paramString;
    if ((paramString != null) && (!"".equals(paramString)))
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(paramString.getBytes());
        String str;
        for (localObject = new BigInteger(1, localMessageDigest.digest()).toString(16); ; localObject = str)
        {
          if (((String)localObject).length() >= 32)
            return localObject;
          str = "0" + (String)localObject;
        }
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        localNoSuchAlgorithmException.printStackTrace();
      }
    return localObject;
  }

  public static Map removeExistArgmentAndGetRedirectURL(String paramString1, List<String> paramList, String paramString2, String paramString3, String paramString4)
  {
    List localList = sortByLength(paramList);
    HashMap localHashMap = new HashMap();
    Iterator localIterator = localList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        localHashMap.put("URL", paramString1);
        return localHashMap;
      }
      String str = (String)localIterator.next();
      if (paramString1.contains(paramString2 + str))
      {
        if (str.equals(paramString4))
        {
          Matcher localMatcher = Pattern.compile(paramString2 + str + "[^" + paramString2 + "]*").matcher(paramString1);
          if (localMatcher.find())
            localMatcher.group(0).replace(paramString2 + str, "");
        }
        paramString1 = paramString1.replaceAll(paramString2 + str + paramString3 + "[^" + paramString2 + "]*", "");
      }
    }
  }

  public static String removeExistEvent(String paramString1, List<String> paramList, String paramString2, String paramString3)
  {
    Iterator localIterator = paramList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return paramString1;
      String str = (String)localIterator.next();
      if (paramString1.contains(paramString2 + str))
      {
        Logger.d("mma_" + paramString2 + str + paramString3 + "[^" + paramString2 + "]*");
        paramString1 = paramString1.replaceAll(paramString2 + str + paramString3 + "[^" + paramString2 + "]*", "");
      }
    }
  }

  private static List<String> sortByLength(List paramList)
  {
    Collections.sort(paramList, new Comparator()
    {
      public int compare(String paramAnonymousString1, String paramAnonymousString2)
      {
        if (paramAnonymousString1.length() > paramAnonymousString2.length())
          return -1;
        return 1;
      }
    });
    return paramList;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.mma.mobile.tracking.util.CommonUtil
 * JD-Core Version:    0.6.2
 */