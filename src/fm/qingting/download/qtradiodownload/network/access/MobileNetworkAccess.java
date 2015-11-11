package fm.qingting.download.qtradiodownload.network.access;

import android.content.Context;
import android.telephony.TelephonyManager;
import fm.qingting.download.qtradiodownload.platform.AndroidFactory;

public class MobileNetworkAccess extends NetworkAccess
{
  private TelephonyManager telephonyManager = (TelephonyManager)AndroidFactory.getApplicationContext().getSystemService("phone");

  public void connect(String paramString)
  {
    this.ipAddress = paramString;
  }

  public void disconnect()
  {
    this.ipAddress = null;
  }

  public String getNetworkName()
  {
    switch (this.telephonyManager.getNetworkType())
    {
    case 4:
    case 5:
    case 6:
    case 7:
    default:
      return "unknown";
    case 1:
      return "GPRS";
    case 2:
      return "EDGE";
    case 3:
      return "UMTS";
    case 8:
      return "HSDPA";
    case 9:
      return "HSUPA";
    case 10:
    }
    return "HSPA";
  }

  public String getType()
  {
    switch (this.telephonyManager.getNetworkType())
    {
    case 4:
    case 5:
    case 6:
    case 7:
    default:
      return null;
    case 1:
    case 2:
      return "3GPP-GERAN";
    case 3:
    case 8:
    case 9:
    case 10:
    }
    return "3GPP-UTRAN-FDD";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.access.MobileNetworkAccess
 * JD-Core Version:    0.6.2
 */