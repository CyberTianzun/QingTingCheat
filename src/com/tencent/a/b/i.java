package com.tencent.a.b;

import android.location.Location;
import android.net.wifi.ScanResult;
import android.telephony.NeighboringCellInfo;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public final class i
{
  public static String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
  public static int[] b = { 0, 49345, 49537, 320, 49921, 960, 640, 49729, 50689, 1728, 1920, 51009, 1280, 50625, 50305, 1088, 52225, 3264, 3456, 52545, 3840, 53185, 52865, 3648, 2560, 51905, 52097, 2880, 51457, 2496, 2176, 51265, 55297, 6336, 6528, 55617, 6912, 56257, 55937, 6720, 7680, 57025, 57217, 8000, 56577, 7616, 7296, 56385, 5120, 54465, 54657, 5440, 55041, 6080, 5760, 54849, 53761, 4800, 4992, 54081, 4352, 53697, 53377, 4160, 61441, 12480, 12672, 61761, 13056, 62401, 62081, 12864, 13824, 63169, 63361, 14144, 62721, 13760, 13440, 62529, 15360, 64705, 64897, 15680, 65281, 16320, 16000, 65089, 64001, 15040, 15232, 64321, 14592, 63937, 63617, 14400, 10240, 59585, 59777, 10560, 60161, 11200, 10880, 59969, 60929, 11968, 12160, 61249, 11520, 60865, 60545, 11328, 58369, 9408, 9600, 58689, 9984, 59329, 59009, 9792, 8704, 58049, 58241, 9024, 57601, 8640, 8320, 57409, 40961, 24768, 24960, 41281, 25344, 41921, 41601, 25152, 26112, 42689, 42881, 26432, 42241, 26048, 25728, 42049, 27648, 44225, 44417, 27968, 44801, 28608, 28288, 44609, 43521, 27328, 27520, 43841, 26880, 43457, 43137, 26688, 30720, 47297, 47489, 31040, 47873, 31680, 31360, 47681, 48641, 32448, 32640, 48961, 32000, 48577, 48257, 31808, 46081, 29888, 30080, 46401, 30464, 47041, 46721, 30272, 29184, 45761, 45953, 29504, 45313, 29120, 28800, 45121, 20480, 37057, 37249, 20800, 37633, 21440, 21120, 37441, 38401, 22208, 22400, 38721, 21760, 38337, 38017, 21568, 39937, 23744, 23936, 40257, 24320, 40897, 40577, 24128, 23040, 39617, 39809, 23360, 39169, 22976, 22656, 38977, 34817, 18624, 18816, 35137, 19200, 35777, 35457, 19008, 19968, 36545, 36737, 20288, 36097, 19904, 19584, 35905, 17408, 33985, 34177, 17728, 34561, 18368, 18048, 34369, 33281, 17088, 17280, 33601, 16640, 33217, 32897, 16448 };

  static
  {
    new int[] { 93629, 99879, 79843, 75029, 59699, 55667, 46867, 38039 };
  }

  public static double a(double paramDouble, int paramInt)
  {
    try
    {
      if (Double.isNaN(paramDouble))
        return 0.0D;
      double d = BigDecimal.valueOf(paramDouble).setScale(paramInt, RoundingMode.HALF_DOWN).doubleValue();
      return d;
    }
    catch (Exception localException)
    {
    }
    return 0.0D;
  }

  public static int a(char paramChar)
  {
    int i = 256;
    if ((paramChar >= 'A') && (paramChar <= 'Z'))
      i = paramChar - 'A';
    if ((paramChar >= 'a') && (paramChar <= 'z'))
      i = 64 + (paramChar - 'a');
    if ((paramChar >= '0') && (paramChar <= '9'))
      i = -48 + (paramChar + '');
    return i;
  }

  private static String a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{");
    localStringBuilder.append("\"mcc\":");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append(",\"mnc\":");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append(",\"lac\":");
    localStringBuilder.append(paramInt3);
    localStringBuilder.append(",\"cellid\":");
    localStringBuilder.append(paramInt4);
    localStringBuilder.append(",\"rss\":");
    localStringBuilder.append(paramInt5);
    if ((paramInt6 != 2147483647) && (paramInt7 != 2147483647))
    {
      localStringBuilder.append(",\"stationLat\":");
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Float.valueOf(paramInt6 / 14400.0F);
      localStringBuilder.append(String.format("%.6f", arrayOfObject1));
      localStringBuilder.append(",\"stationLng\":");
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Float.valueOf(paramInt7 / 14400.0F);
      localStringBuilder.append(String.format("%.6f", arrayOfObject2));
    }
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }

  public static String a(d.b paramb, List<NeighboringCellInfo> paramList)
  {
    if (paramb == null)
      return "[]";
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    int i = paramb.b;
    int j = paramb.c;
    boolean bool = a(paramb.a, i, j, paramb.d, paramb.e);
    int k = 0;
    if (bool)
    {
      localStringBuilder.append(a(i, j, paramb.d, paramb.e, paramb.f, paramb.g, paramb.h));
      k = 1;
    }
    if (paramList != null);
    while (true)
    {
      try
      {
        Method localMethod = Class.forName("android.telephony.NeighboringCellInfo").getMethod("getLac", new Class[0]);
        Iterator localIterator = paramList.iterator();
        if (localIterator.hasNext())
        {
          NeighboringCellInfo localNeighboringCellInfo = (NeighboringCellInfo)localIterator.next();
          int m = Integer.parseInt(localMethod.invoke(localNeighboringCellInfo, new Object[0]).toString());
          if (!a(paramb.a, i, j, m, localNeighboringCellInfo.getCid()))
            break label270;
          if (k > 0)
            localStringBuilder.append(",");
          localStringBuilder.append(a(i, j, m, localNeighboringCellInfo.getCid(), -113 + (localNeighboringCellInfo.getRssi() << 1), 2147483647, 2147483647));
          n = k + 1;
          k = n;
          continue;
        }
      }
      catch (Exception localException)
      {
        paramList.clear();
      }
      localStringBuilder.append("]");
      return localStringBuilder.toString();
      label270: int n = k;
    }
  }

  public static String a(e.b paramb)
  {
    if (paramb.b() == null)
      return "{}";
    Location localLocation = paramb.b();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{");
    localStringBuilder.append("\"latitude\":");
    localStringBuilder.append(localLocation.getLatitude());
    localStringBuilder.append(",\"longitude\":");
    localStringBuilder.append(localLocation.getLongitude());
    localStringBuilder.append(",\"additional\":");
    localStringBuilder.append("\"" + localLocation.getAltitude() + "," + localLocation.getAccuracy() + "," + localLocation.getBearing() + "," + localLocation.getSpeed() + "," + localLocation.getTime() + "\"");
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }

  public static String a(g.b paramb)
  {
    if ((paramb == null) || (paramb.a() == null))
      return "[]";
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    if ((paramb.a() == null) || (paramb.a().size() <= 0))
    {
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
    List localList = paramb.a();
    Iterator localIterator = localList.iterator();
    int i = 0;
    label128: label212: label216: 
    while (localIterator.hasNext())
    {
      ScanResult localScanResult = (ScanResult)localIterator.next();
      int j = localList.size();
      int k = localScanResult.level;
      int m;
      if (j < 6)
      {
        m = -95;
        if (k >= m)
          break label212;
      }
      for (int n = 0; ; n = 1)
      {
        if (n == 0)
          break label216;
        if (i > 0)
          localStringBuilder.append(",");
        localStringBuilder.append("{\"mac\":\"").append(localScanResult.BSSID).append("\",");
        localStringBuilder.append("\"rssi\":").append(localScanResult.level).append("}");
        i++;
        break;
        m = -90;
        break label128;
      }
    }
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }

  public static String a(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{");
    localStringBuilder.append("\"imei\":\"");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("\",\"imsi\":\"");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("\",\"phonenum\":\"");
    localStringBuilder.append(paramString3);
    localStringBuilder.append("\",\"roaming\":\"");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.append("\",\"qq\":\"");
    localStringBuilder.append(paramString4);
    localStringBuilder.append("\"");
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }

  public static boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    boolean bool = true;
    if (paramInt1 == 2)
      if ((paramInt2 < 0) || (paramInt3 < 0) || (paramInt4 < 0) || (paramInt4 > 65535) || (paramInt5 < 0) || (paramInt5 > 65535) || ((paramInt3 == 0) && (paramInt4 == 0) && (paramInt5 == 0)))
        bool = false;
    do
    {
      return bool;
      if ((paramInt2 < 0) || (paramInt3 < 0) || (paramInt4 <= 0) || (paramInt4 >= 65535))
        return false;
    }
    while ((paramInt5 != 268435455) && (paramInt5 != 2147483647) && (paramInt5 != 50594049) && (paramInt5 != 65535) && (paramInt5 != 8) && (paramInt5 != 10) && (paramInt5 != 33) && (paramInt5 > 0));
    return false;
  }

  public static boolean a(String paramString)
  {
    if ((paramString == null) || (paramString.length() > 32) || (paramString.length() < 6));
    while (!Pattern.compile("[a-zA-Z0-9_]{6,32}").matcher(paramString).matches())
      return false;
    return true;
  }

  public static boolean b(String paramString)
  {
    if ((paramString == null) || (paramString.length() != 29));
    while (!Pattern.compile("([A-Z2-7]{5}){1}(-[A-Z2-7]{5}){4}").matcher(paramString).matches())
      return false;
    return true;
  }

  public static boolean c(String paramString)
  {
    while (true)
    {
      try
      {
        JSONObject localJSONObject = new JSONObject(paramString);
        if (!paramString.contains("latitude"))
          break label95;
        i = 1;
        if (localJSONObject.getJSONArray("cells").length() <= 0)
          break label89;
        j = 1;
        int k = localJSONObject.getJSONArray("wifis").length();
        if (k > 0)
        {
          m = 1;
          boolean bool;
          if ((i == 0) && (j == 0))
          {
            bool = false;
            if (m == 0);
          }
          else
          {
            bool = true;
          }
          return bool;
        }
      }
      catch (Exception localException)
      {
        return false;
      }
      int m = 0;
      continue;
      label89: int j = 0;
      continue;
      label95: int i = 0;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.a.b.i
 * JD-Core Version:    0.6.2
 */