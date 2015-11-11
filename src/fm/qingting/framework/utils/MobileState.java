package fm.qingting.framework.utils;

import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteOrder;

public class MobileState
{
  private static MobileState instance;
  private Context mContext;

  private MobileState(Context paramContext)
  {
    this.mContext = paramContext;
  }

  private int getHeadSetStateByMgr()
  {
    if (this.mContext == null);
    while (true)
    {
      return -1;
      try
      {
        AudioManager localAudioManager = (AudioManager)this.mContext.getSystemService("audio");
        if (localAudioManager != null)
        {
          boolean bool = localAudioManager.isWiredHeadsetOn();
          if (bool)
            return 1;
        }
      }
      catch (Exception localException)
      {
      }
    }
    return -1;
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
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo == null)
      return -1;
    int j = localNetworkInfo.getType();
    if (j == 0)
    {
      if (localNetworkInfo.getExtraInfo() == null)
        return i;
      if (localNetworkInfo.getExtraInfo().toLowerCase().equals("cmnet"))
        i = 3;
    }
    while (true)
    {
      return i;
      if (localNetworkInfo.getExtraInfo().toLowerCase().equals("3gnet"))
      {
        i = 2;
      }
      else if (localNetworkInfo.getExtraInfo().toLowerCase().equals("ctnet"))
      {
        i = 2;
      }
      else if (localNetworkInfo.getExtraInfo().toLowerCase().equals("ctwap"))
      {
        i = 3;
      }
      else if (localNetworkInfo.getExtraInfo().toLowerCase().equals("cmwap"))
      {
        i = 3;
        continue;
        if (j == 1)
          i = 1;
      }
    }
  }

  public static String getWifiIpAddress(Context paramContext)
  {
    if (paramContext == null);
    while (true)
    {
      return null;
      try
      {
        int i = ((WifiManager)paramContext.getSystemService("wifi")).getConnectionInfo().getIpAddress();
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN))
          i = Integer.reverseBytes(i);
        byte[] arrayOfByte = BigInteger.valueOf(i).toByteArray();
        if (arrayOfByte != null)
          try
          {
            String str = InetAddress.getByAddress(arrayOfByte).getHostAddress();
            return str;
          }
          catch (Exception localException2)
          {
            return null;
          }
      }
      catch (Exception localException1)
      {
      }
    }
    return null;
  }

  public static boolean isCMNETOR3GNET(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.getType() == 0) && (localNetworkInfo.getExtraInfo() != null);
  }

  public static boolean isNetWorkEnable(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      boolean bool1 = false;
      if (localNetworkInfo != null)
      {
        boolean bool2 = localNetworkInfo.isAvailable();
        bool1 = bool2;
      }
      return bool1;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  // ERROR //
  public int getheadsetState()
  {
    // Byte code:
    //   0: new 137	java/io/FileReader
    //   3: dup
    //   4: ldc 139
    //   6: invokespecial 142	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   9: astore_1
    //   10: aload_1
    //   11: ifnonnull +5 -> 16
    //   14: iconst_0
    //   15: ireturn
    //   16: sipush 1024
    //   19: newarray char
    //   21: astore_3
    //   22: new 60	java/lang/String
    //   25: dup
    //   26: aload_3
    //   27: iconst_0
    //   28: aload_1
    //   29: aload_3
    //   30: iconst_0
    //   31: sipush 1024
    //   34: invokevirtual 146	java/io/FileReader:read	([CII)I
    //   37: invokespecial 149	java/lang/String:<init>	([CII)V
    //   40: invokevirtual 152	java/lang/String:trim	()Ljava/lang/String;
    //   43: invokestatic 155	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   46: invokevirtual 158	java/lang/Integer:intValue	()I
    //   49: istore 4
    //   51: iload 4
    //   53: ireturn
    //   54: astore_2
    //   55: aload_2
    //   56: invokevirtual 161	java/lang/Exception:printStackTrace	()V
    //   59: aload_0
    //   60: invokespecial 163	fm/qingting/framework/utils/MobileState:getHeadSetStateByMgr	()I
    //   63: ireturn
    //   64: astore_2
    //   65: goto -10 -> 55
    //
    // Exception table:
    //   from	to	target	type
    //   0	10	54	java/lang/Exception
    //   16	51	64	java/lang/Exception
  }

  public boolean hasHeadSet()
  {
    if (this.mContext == null);
    while (true)
    {
      return false;
      try
      {
        AudioManager localAudioManager = (AudioManager)this.mContext.getSystemService("audio");
        if (localAudioManager != null)
        {
          boolean bool = localAudioManager.isWiredHeadsetOn();
          if (bool)
            return true;
        }
      }
      catch (Exception localException)
      {
      }
    }
    return false;
  }

  public boolean isMobilEnable(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(0);
    boolean bool = false;
    if (localNetworkInfo != null)
      bool = true;
    return bool;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.MobileState
 * JD-Core Version:    0.6.2
 */