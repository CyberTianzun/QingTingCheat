package fm.qingting.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.FileReader;

public class MobileState
{
  private static MobileState instance;

  private MobileState(Context paramContext)
  {
  }

  public static MobileState getInstance(Context paramContext)
  {
    if (instance == null)
      instance = new MobileState(paramContext);
    return instance;
  }

  public static int getNetWorkType(Context paramContext)
  {
    int i = 5;
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    NetworkInfo localNetworkInfo1;
    int j;
    int k;
    while (true)
    {
      try
      {
        NetworkInfo localNetworkInfo2 = localConnectivityManager.getActiveNetworkInfo();
        localNetworkInfo1 = localNetworkInfo2;
        if (localNetworkInfo1 == null)
        {
          i = -1;
          return i;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        localNetworkInfo1 = null;
        continue;
        j = localNetworkInfo1.getType();
        if (j != 0)
          break label172;
      }
      if (localNetworkInfo1.getExtraInfo() != null)
        if (localNetworkInfo1.getExtraInfo().toLowerCase().equals("cmnet"))
          k = 3;
    }
    while (true)
    {
      return k;
      if (localNetworkInfo1.getExtraInfo().toLowerCase().equals("3gnet"))
      {
        k = 2;
      }
      else if (localNetworkInfo1.getExtraInfo().toLowerCase().equals("ctnet"))
      {
        k = 2;
      }
      else if (localNetworkInfo1.getExtraInfo().toLowerCase().equals("ctwap"))
      {
        k = 3;
      }
      else if (localNetworkInfo1.getExtraInfo().toLowerCase().equals("cmwap"))
      {
        k = 3;
        continue;
        label172: if (j == 1)
          k = 1;
      }
      else
      {
        k = i;
      }
    }
  }

  public static boolean isCMNETOR3GNET(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.getType() == 0) && ((localNetworkInfo.getExtraInfo().toLowerCase().equals("cmnet")) || (localNetworkInfo.getExtraInfo().toLowerCase().equals("3gnet")));
  }

  public static boolean isNetWorkEnable(Context paramContext)
  {
    return ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo() != null;
  }

  public int getheadsetState()
  {
    try
    {
      FileReader localFileReader = new FileReader("/sys/class/switch/h2w/state");
      if (localFileReader == null)
        return 0;
      char[] arrayOfChar = new char[1024];
      int i = Integer.valueOf(new String(arrayOfChar, 0, localFileReader.read(arrayOfChar, 0, 1024)).trim()).intValue();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  public boolean isMobilEnable(Context paramContext)
  {
    return ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(0) != null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.MobileState
 * JD-Core Version:    0.6.2
 */