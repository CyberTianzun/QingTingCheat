package fm.qingting.download.qtradiodownload.network.access;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import fm.qingting.download.qtradiodownload.platform.AndroidFactory;

public class WifiNetworkAccess extends NetworkAccess
{
  private WifiManager wifiManager = (WifiManager)AndroidFactory.getApplicationContext().getSystemService("wifi");

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
    if (this.wifiManager.getConnectionInfo().getLinkSpeed() <= 11);
    for (String str = "Wi-Fi " + "802.11b"; ; str = "Wi-Fi " + "802.11a")
      return str + ", SSID=" + this.wifiManager.getConnectionInfo().getSSID();
  }

  public String getType()
  {
    if (this.wifiManager.getConnectionInfo().getLinkSpeed() <= 11)
      return "IEEE-802.11b";
    return "IEEE-802.11a";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.access.WifiNetworkAccess
 * JD-Core Version:    0.6.2
 */