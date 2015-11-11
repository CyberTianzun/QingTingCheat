package com.talkingdata.pingan.sdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpHost;

public class b
{
  static TelephonyManager a;
  static boolean b = false;
  static final long c = 300000L;
  static long d = -300000L;
  private static final String[] e = { "UNKNOWN", "GPRS", "EDGE", "UMTS", "CDMA", "EVDO_0", "EVDO_A", "1xRTT", "HSDPA", "HSUPA", "HSPA", "IDEN", "EVDO_B", "LTE", "EHRPD", "HSPAP" };

  static void a(Context paramContext)
  {
    a = (TelephonyManager)paramContext.getSystemService("phone");
  }

  public static boolean a()
  {
    return !TextUtils.isEmpty(Proxy.getDefaultHost());
  }

  public static HttpHost b()
  {
    if (a())
      return new HttpHost(Proxy.getDefaultHost(), Proxy.getDefaultPort());
    return null;
  }

  // ERROR //
  public static boolean b(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 106
    //   3: invokestatic 111	com/talkingdata/pingan/sdk/aa:a	(Landroid/content/Context;Ljava/lang/String;)Z
    //   6: ifeq +59 -> 65
    //   9: aload_0
    //   10: ldc 113
    //   12: invokevirtual 72	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   15: checkcast 115	android/net/ConnectivityManager
    //   18: astore 13
    //   20: aload 13
    //   22: invokevirtual 119	android/net/ConnectivityManager:getActiveNetworkInfo	()Landroid/net/NetworkInfo;
    //   25: astore 14
    //   27: aload 14
    //   29: ifnull +9 -> 38
    //   32: aload 14
    //   34: invokevirtual 124	android/net/NetworkInfo:isConnected	()Z
    //   37: ireturn
    //   38: aload 13
    //   40: iconst_0
    //   41: invokevirtual 128	android/net/ConnectivityManager:getNetworkInfo	(I)Landroid/net/NetworkInfo;
    //   44: astore 15
    //   46: aload 15
    //   48: ifnull +89 -> 137
    //   51: aload 15
    //   53: invokevirtual 132	android/net/NetworkInfo:getState	()Landroid/net/NetworkInfo$State;
    //   56: getstatic 137	android/net/NetworkInfo$State:UNKNOWN	Landroid/net/NetworkInfo$State;
    //   59: invokevirtual 141	android/net/NetworkInfo$State:equals	(Ljava/lang/Object;)Z
    //   62: ifeq +75 -> 137
    //   65: invokestatic 147	android/os/SystemClock:elapsedRealtime	()J
    //   68: getstatic 60	com/talkingdata/pingan/sdk/b:d	J
    //   71: lsub
    //   72: ldc2_w 12
    //   75: lcmp
    //   76: ifle +57 -> 133
    //   79: invokestatic 147	android/os/SystemClock:elapsedRealtime	()J
    //   82: putstatic 60	com/talkingdata/pingan/sdk/b:d	J
    //   85: aconst_null
    //   86: astore_1
    //   87: invokestatic 149	com/talkingdata/pingan/sdk/b:b	()Lorg/apache/http/HttpHost;
    //   90: astore 9
    //   92: aconst_null
    //   93: astore_1
    //   94: aload 9
    //   96: ifnonnull +43 -> 139
    //   99: new 151	java/net/Socket
    //   102: dup
    //   103: aload 9
    //   105: invokevirtual 154	org/apache/http/HttpHost:getHostName	()Ljava/lang/String;
    //   108: aload 9
    //   110: invokevirtual 157	org/apache/http/HttpHost:getPort	()I
    //   113: invokespecial 158	java/net/Socket:<init>	(Ljava/lang/String;I)V
    //   116: astore 10
    //   118: aload 10
    //   120: astore_1
    //   121: iconst_1
    //   122: putstatic 56	com/talkingdata/pingan/sdk/b:b	Z
    //   125: aload_1
    //   126: ifnull +7 -> 133
    //   129: aload_1
    //   130: invokevirtual 161	java/net/Socket:close	()V
    //   133: getstatic 56	com/talkingdata/pingan/sdk/b:b	Z
    //   136: ireturn
    //   137: iconst_0
    //   138: ireturn
    //   139: new 151	java/net/Socket
    //   142: dup
    //   143: ldc 163
    //   145: bipush 80
    //   147: invokespecial 158	java/net/Socket:<init>	(Ljava/lang/String;I)V
    //   150: astore 12
    //   152: aload 12
    //   154: astore_1
    //   155: goto -34 -> 121
    //   158: astore 6
    //   160: iconst_0
    //   161: putstatic 56	com/talkingdata/pingan/sdk/b:b	Z
    //   164: aload_1
    //   165: ifnull -32 -> 133
    //   168: aload_1
    //   169: invokevirtual 161	java/net/Socket:close	()V
    //   172: goto -39 -> 133
    //   175: astore 8
    //   177: goto -44 -> 133
    //   180: astore_2
    //   181: aconst_null
    //   182: astore_3
    //   183: aload_2
    //   184: astore 4
    //   186: aload_3
    //   187: ifnull +7 -> 194
    //   190: aload_3
    //   191: invokevirtual 161	java/net/Socket:close	()V
    //   194: aload 4
    //   196: athrow
    //   197: astore 11
    //   199: goto -66 -> 133
    //   202: astore 5
    //   204: goto -10 -> 194
    //   207: astore 7
    //   209: aload_1
    //   210: astore_3
    //   211: aload 7
    //   213: astore 4
    //   215: goto -29 -> 186
    //
    // Exception table:
    //   from	to	target	type
    //   87	92	158	java/lang/Exception
    //   99	118	158	java/lang/Exception
    //   121	125	158	java/lang/Exception
    //   139	152	158	java/lang/Exception
    //   168	172	175	java/lang/Exception
    //   87	92	180	finally
    //   99	118	180	finally
    //   139	152	180	finally
    //   129	133	197	java/lang/Exception
    //   190	194	202	java/lang/Exception
    //   121	125	207	finally
    //   160	164	207	finally
  }

  public static boolean c(Context paramContext)
  {
    if (aa.a(paramContext, "android.permission.ACCESS_NETWORK_STATE"))
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      return (localNetworkInfo != null) && (1 == localNetworkInfo.getType()) && (localNetworkInfo.isConnected());
    }
    return false;
  }

  public static String d(Context paramContext)
  {
    String str;
    if (!b(paramContext))
      str = "OFFLINE";
    do
    {
      return str;
      if (c(paramContext))
        return "WIFI";
      str = e[0];
    }
    while (!aa.a(paramContext, "android.permission.READ_PHONE_STATE"));
    if (a == null)
      a(paramContext);
    int i = a.getNetworkType();
    if ((i >= 0) && (i < e.length))
      return e[i];
    return e[0];
  }

  public static String e(Context paramContext)
  {
    if (aa.a(paramContext, "android.permission.READ_PHONE_STATE"))
    {
      if (a == null)
        a(paramContext);
      return a.getNetworkOperator();
    }
    return "";
  }

  public static String f(Context paramContext)
  {
    if (aa.a(paramContext, "android.permission.READ_PHONE_STATE"))
    {
      if (a == null)
        a(paramContext);
      return a.getSimOperator();
    }
    return "";
  }

  public static String g(Context paramContext)
  {
    if (aa.a(paramContext, "android.permission.READ_PHONE_STATE"))
    {
      if (a == null)
        a(paramContext);
      return a.getSimOperatorName();
    }
    return "";
  }

  public static String h(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((aa.a(paramContext, "android.permission.ACCESS_COARSE_LOCATION")) || (aa.a(paramContext, "android.permission.ACCESS_FINE_LOCATION")));
    try
    {
      if (a == null)
        a(paramContext);
      CellLocation localCellLocation = a.getCellLocation();
      if ((localCellLocation instanceof GsmCellLocation))
      {
        GsmCellLocation localGsmCellLocation = (GsmCellLocation)localCellLocation;
        if (localGsmCellLocation != null)
        {
          localStringBuilder.append("gsm:");
          localStringBuilder.append(localGsmCellLocation.getCid()).append(':').append(localGsmCellLocation.getLac());
          if (Integer.valueOf(Build.VERSION.SDK).intValue() >= 9)
            localStringBuilder.append(':').append(a.a(localGsmCellLocation));
        }
      }
      while (true)
      {
        label120: return ':' + e(paramContext);
        if ((localCellLocation instanceof CdmaCellLocation))
        {
          CdmaCellLocation localCdmaCellLocation = (CdmaCellLocation)localCellLocation;
          if (localCdmaCellLocation != null)
          {
            localStringBuilder.append("cdma:");
            localStringBuilder.append(localCdmaCellLocation.getBaseStationId()).append(':').append(localCdmaCellLocation.getNetworkId()).append(':').append(localCdmaCellLocation.getSystemId()).append(':').append(localCdmaCellLocation.getBaseStationLatitude()).append(':').append(localCdmaCellLocation.getBaseStationLongitude());
          }
        }
      }
    }
    catch (Exception localException)
    {
      break label120;
    }
  }

  public static String i(Context paramContext)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    if (aa.a(paramContext, "android.permission.ACCESS_WIFI_STATE"))
    {
      WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
      if (localWifiManager.isWifiEnabled())
      {
        WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
        Object localObject = null;
        char c2;
        if (localWifiInfo != null)
        {
          String str1 = localWifiInfo.getBSSID();
          localObject = null;
          if (str1 != null)
          {
            String str2 = localWifiInfo.getBSSID();
            StringBuilder localStringBuilder3 = localStringBuilder1.append(str2).append('/').append(localWifiInfo.getRssi()).append('/').append(localWifiInfo.getSSID()).append('/');
            if (!localWifiInfo.getHiddenSSID())
              break label269;
            c2 = 'y';
            localStringBuilder3.append(c2).append(';').append(';');
            localObject = str2;
          }
        }
        List localList = localWifiManager.getScanResults();
        if (localList != null)
        {
          Iterator localIterator = localList.iterator();
          label158: ScanResult localScanResult;
          do
          {
            if (!localIterator.hasNext())
              break;
            localScanResult = (ScanResult)localIterator.next();
          }
          while ((localScanResult.BSSID == null) || (localScanResult.BSSID.equals(localObject)));
          StringBuilder localStringBuilder2 = localStringBuilder1.append(localScanResult.BSSID).append('/').append(localScanResult.level).append('/').append(localWifiInfo.getSSID()).append('/');
          if (localWifiInfo.getHiddenSSID());
          for (char c1 = 'y'; ; c1 = 'n')
          {
            localStringBuilder2.append(c1).append(';');
            break label158;
            label269: c2 = 'n';
            break;
          }
        }
      }
    }
    return localStringBuilder1.toString();
  }

  private static class a
  {
    static long a(GsmCellLocation paramGsmCellLocation)
    {
      try
      {
        int i = paramGsmCellLocation.getPsc();
        return i;
      }
      catch (Exception localException)
      {
      }
      return -1L;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.b
 * JD-Core Version:    0.6.2
 */