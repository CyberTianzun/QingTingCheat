package fm.qingting.qtradio.model;

import android.content.Context;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.utils.WifiSettingsUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class CheckMediaServer extends Thread
{
  private Context mContext;
  private List<String> mLstValidDomain = new ArrayList();
  private List<String> mLstValidIP = new ArrayList();

  public CheckMediaServer(Context paramContext)
  {
    initIPList();
    initDomainList();
    this.mContext = paramContext;
  }

  private void initDomainList()
  {
    this.mLstValidDomain.add("s.hz.qingting.fm");
    this.mLstValidDomain.add("s.qd.qingting.fm");
    this.mLstValidDomain.add("s.bj.qingting.fm");
    this.mLstValidDomain.add("s.qd.qingtingfm.com");
    this.mLstValidDomain.add("s.hz.qingtingfm.com");
    this.mLstValidDomain.add("s.bj.qingtingfm.com");
  }

  private void initIPList()
  {
    this.mLstValidIP.add("42.121.254.227");
    this.mLstValidIP.add("42.120.60.95");
    this.mLstValidIP.add("42.121.252.83");
    this.mLstValidIP.add("42.96.249.166");
    this.mLstValidIP.add("42.96.251.151");
    this.mLstValidIP.add("42.96.248.161");
    this.mLstValidIP.add("42.62.29.100");
    this.mLstValidIP.add("42.62.56.176");
    this.mLstValidIP.add("42.62.56.184");
    this.mLstValidIP.add("42.62.56.211");
  }

  private boolean isValidIP(String paramString)
  {
    if (paramString == null);
    while (true)
    {
      return false;
      for (int i = 0; i < this.mLstValidIP.size(); i++)
        if (((String)this.mLstValidIP.get(i)).equalsIgnoreCase(paramString))
          return true;
    }
  }

  private String resolveIPAddr(String paramString)
  {
    String str1;
    if (paramString == null)
      str1 = null;
    do
    {
      return str1;
      str1 = "";
    }
    while (paramString == null);
    try
    {
      String str2 = InetAddress.getByName(paramString).getHostAddress();
      return str2;
    }
    catch (UnknownHostException localUnknownHostException)
    {
      localUnknownHostException.printStackTrace();
    }
    return str1;
  }

  public void check()
  {
    if ((InfoManager.getInstance().hasWifi()) && (InfoManager.getInstance().enableSetDNS()))
    {
      WifiSettingsUtil.getInstance().init(this.mContext);
      if (!WifiSettingsUtil.getInstance().isValidDNS())
        WifiSettingsUtil.getInstance().setDNS();
    }
    for (int i = 0; i < this.mLstValidDomain.size(); i++)
    {
      String str = resolveIPAddr((String)this.mLstValidDomain.get(i));
      if ((!isValidIP(str)) && (str != null) && (this.mContext != null))
      {
        MobclickAgent.onEvent(this.mContext, "InvalidMediaIP1", str);
        MobclickAgent.onEvent(this.mContext, "InvalidMediaDomain1", (String)this.mLstValidDomain.get(i));
        int j = InfoManager.getInstance().getNetWorkType();
        MobclickAgent.onEvent(this.mContext, "InvalidMediaNetwork", String.valueOf(j));
        InfoManager.getInstance().setUseIpInMedia(true);
      }
    }
  }

  public void run()
  {
    try
    {
      check();
      return;
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.CheckMediaServer
 * JD-Core Version:    0.6.2
 */