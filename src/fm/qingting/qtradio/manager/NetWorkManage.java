package fm.qingting.qtradio.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

public class NetWorkManage
{
  private static NetWorkManage instance;
  private final String IPV4_REGEX = "((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})";
  public boolean isNetworkValid = false;
  private HashSet<WeakReference<INETEventListener>> listeners = new HashSet();
  private Context mContext;
  public NetWorkType mNetWorkType = NetWorkType.NONE;
  private NetworkReceiver network;

  private void dispatchNetEvent(String paramString)
  {
    removeUnavailableListener();
    Iterator localIterator = new HashSet(this.listeners).iterator();
    while (localIterator.hasNext())
    {
      INETEventListener localINETEventListener = (INETEventListener)((WeakReference)localIterator.next()).get();
      if (localINETEventListener != null)
        localINETEventListener.onNetChanged(paramString);
    }
  }

  private void getCurrentNetworkType()
  {
    if (this.mContext == null)
    {
      this.mNetWorkType = NetWorkType.NONE;
      return;
    }
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo == null)
      {
        this.mNetWorkType = NetWorkType.NONE;
        return;
      }
      int i = localNetworkInfo.getType();
      if (i == 0)
      {
        this.mNetWorkType = NetWorkType.Mobile;
        return;
      }
      if (i == 1)
      {
        this.mNetWorkType = NetWorkType.WiFi;
        return;
      }
      this.mNetWorkType = NetWorkType.NONE;
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static NetWorkManage getInstance()
  {
    if (instance == null)
      instance = new NetWorkManage();
    return instance;
  }

  private boolean ipv4Validate(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return false;
    try
    {
      boolean bool = Pattern.matches(paramString2, paramString1.trim());
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private void removeUnavailableListener()
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      if ((INETEventListener)((WeakReference)localIterator.next()).get() == null)
        localIterator.remove();
  }

  public void addListener(INETEventListener paramINETEventListener)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      if (((WeakReference)localIterator.next()).get() == paramINETEventListener)
        return;
    this.listeners.add(new WeakReference(paramINETEventListener));
  }

  // ERROR //
  public String getLocalIpAddress(Context paramContext)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: aload_1
    //   7: ldc 84
    //   9: invokevirtual 90	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   12: checkcast 92	android/net/ConnectivityManager
    //   15: invokevirtual 96	android/net/ConnectivityManager:getActiveNetworkInfo	()Landroid/net/NetworkInfo;
    //   18: astore_3
    //   19: aload_3
    //   20: ifnull -16 -> 4
    //   23: aload_3
    //   24: invokevirtual 102	android/net/NetworkInfo:getType	()I
    //   27: ifne +95 -> 122
    //   30: invokestatic 147	java/net/NetworkInterface:getNetworkInterfaces	()Ljava/util/Enumeration;
    //   33: astore 5
    //   35: aconst_null
    //   36: astore 4
    //   38: aload 5
    //   40: invokeinterface 152 1 0
    //   45: ifeq +80 -> 125
    //   48: aload 5
    //   50: invokeinterface 155 1 0
    //   55: checkcast 143	java/net/NetworkInterface
    //   58: invokevirtual 158	java/net/NetworkInterface:getInetAddresses	()Ljava/util/Enumeration;
    //   61: astore 7
    //   63: aload 7
    //   65: invokeinterface 152 1 0
    //   70: ifeq +75 -> 145
    //   73: aload 7
    //   75: invokeinterface 155 1 0
    //   80: checkcast 160	java/net/InetAddress
    //   83: astore 9
    //   85: aload 9
    //   87: invokevirtual 163	java/net/InetAddress:isLoopbackAddress	()Z
    //   90: ifne -27 -> 63
    //   93: aload 9
    //   95: instanceof 165
    //   98: ifeq -35 -> 63
    //   101: aload 9
    //   103: invokevirtual 168	java/net/InetAddress:getHostAddress	()Ljava/lang/String;
    //   106: invokevirtual 171	java/lang/String:toString	()Ljava/lang/String;
    //   109: astore 10
    //   111: aload 10
    //   113: astore 8
    //   115: aload 8
    //   117: astore 4
    //   119: goto -81 -> 38
    //   122: aconst_null
    //   123: astore 4
    //   125: aload_0
    //   126: aload 4
    //   128: invokevirtual 175	fm/qingting/qtradio/manager/NetWorkManage:ipV4Validate	(Ljava/lang/String;)Z
    //   131: ifeq -127 -> 4
    //   134: aload 4
    //   136: areturn
    //   137: astore_2
    //   138: aconst_null
    //   139: areturn
    //   140: astore 6
    //   142: aload 4
    //   144: areturn
    //   145: aload 4
    //   147: astore 8
    //   149: goto -34 -> 115
    //
    // Exception table:
    //   from	to	target	type
    //   6	19	137	java/lang/Exception
    //   23	35	137	java/lang/Exception
    //   38	63	140	java/lang/Exception
    //   63	111	140	java/lang/Exception
  }

  public String getNetWorkType()
  {
    String str1;
    if (this.mContext == null)
      str1 = "noNet";
    label229: 
    while (true)
    {
      return str1;
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo == null)
        return "NoNet";
      int i = localNetworkInfo.getType();
      int j;
      if (i == 0)
      {
        j = localNetworkInfo.getSubtype();
        if ((j == 3) || (j == 8))
        {
          str1 = "3G";
          String str2 = localNetworkInfo.getExtraInfo();
          if ((str2 != null) && ((str2.equalsIgnoreCase("3gnet")) || (str2.equalsIgnoreCase("3gwap"))))
            str1 = "3G";
        }
      }
      while (true)
      {
        if (str1 != null)
          break label229;
        return "noNet";
        if ((j == 1) || (j == 2))
        {
          str1 = "2G";
          break;
        }
        if (j == 4)
        {
          str1 = "2G";
          break;
        }
        if (j == 5)
        {
          str1 = "3G";
          break;
        }
        if ((j == 7) || (j == 11))
        {
          str1 = "2G";
          break;
        }
        if ((j == 6) || (j == 9) || (j == 15) || (j == 14))
        {
          str1 = "3G";
          break;
        }
        str1 = null;
        if (j != 13)
          break;
        str1 = "4G";
        break;
        str1 = null;
        if (i == 1)
          str1 = "WIFI";
      }
    }
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    getCurrentNetworkType();
  }

  public boolean ipV4Validate(String paramString)
  {
    return ipv4Validate(paramString, "((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})");
  }

  public void register()
  {
    if (this.network != null)
      return;
    this.network = new NetworkReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    this.mContext.registerReceiver(this.network, localIntentFilter);
  }

  public void removeListener(INETEventListener paramINETEventListener)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      if (((WeakReference)localIterator.next()).get() == paramINETEventListener)
        localIterator.remove();
  }

  public void unregister()
  {
    try
    {
      if (this.network != null)
        this.mContext.unregisterReceiver(this.network);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    finally
    {
      this.network = null;
    }
  }

  public static enum NetWorkType
  {
    static
    {
      Mobile = new NetWorkType("Mobile", 2);
      NetWorkType[] arrayOfNetWorkType = new NetWorkType[3];
      arrayOfNetWorkType[0] = NONE;
      arrayOfNetWorkType[1] = WiFi;
      arrayOfNetWorkType[2] = Mobile;
    }

    public boolean isMobile()
    {
      return this == Mobile;
    }

    public boolean isNone()
    {
      return this == NONE;
    }

    public boolean isWiFi()
    {
      return this == WiFi;
    }
  }

  public class NetworkReceiver extends BroadcastReceiver
  {
    public NetworkReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      while (true)
      {
        int i;
        int j;
        String str1;
        try
        {
          NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
          if (localNetworkInfo == null)
          {
            NetWorkManage.this.mNetWorkType = NetWorkManage.NetWorkType.NONE;
            NetWorkManage.this.dispatchNetEvent("NoNet");
            return;
          }
          i = localNetworkInfo.getType();
          if (i != 0)
            break label168;
          NetWorkManage.this.mNetWorkType = NetWorkManage.NetWorkType.Mobile;
          j = localNetworkInfo.getSubtype();
          if (j == 3)
            break label194;
          if (j == 8)
          {
            break label194;
            String str2 = localNetworkInfo.getExtraInfo();
            if ((str2 != null) && ((str2.equalsIgnoreCase("3gnet")) || (str2.equalsIgnoreCase("3gwap"))))
              break label201;
            if (str1 == null)
              break label193;
            NetWorkManage.this.dispatchNetEvent(str1);
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          NetWorkManage.this.dispatchNetEvent("NoNet");
          return;
        }
        if ((j == 1) || (j == 2))
        {
          str1 = "2G";
          continue;
          label168: if (i == 1)
          {
            NetWorkManage.this.mNetWorkType = NetWorkManage.NetWorkType.WiFi;
            NetWorkManage.this.dispatchNetEvent("WiFi");
          }
          label193: return;
          label194: str1 = "3G";
          continue;
          label201: str1 = "3G";
        }
        else if (j == 4)
        {
          str1 = "2G";
        }
        else if (j == 5)
        {
          str1 = "3G";
        }
        else if ((j == 7) || (j == 11))
        {
          str1 = "2G";
        }
        else if ((j == 6) || (j == 9) || (j == 15) || (j == 14))
        {
          str1 = "3G";
        }
        else
        {
          str1 = null;
          if (j == 13)
            str1 = "4G";
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.manager.NetWorkManage
 * JD-Core Version:    0.6.2
 */