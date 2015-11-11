package fm.qingting.qtradio.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class PingInfo
{
  private long MDomainTime = 2147483647L;
  private long SDomainTime = 2147483647L;
  private String bestBackupIP;
  private long bestBackupIPTime = 2147483647L;
  private String bestMDomainIP;
  private long bestMDomainTime = 2147483647L;
  public String codename;
  private boolean hasPinged = false;
  private String mDomainIP = null;
  public List<String> mLstBackupIPs;
  public String mdomain;
  public String res;
  private String sDomainIP = null;
  private int sIPCnt = 0;
  public List<String> sLstBackupIPs;
  public String sdomain;
  public String testpath;
  private long totalBackupSIPTime = -1L;

  public String getBestBackupIP()
  {
    return this.bestBackupIP;
  }

  public String getBestMDomainIP()
  {
    if ((this.MDomainTime < 2147483647L) && (this.MDomainTime < 2L * this.bestMDomainTime))
    {
      this.bestMDomainIP = this.mDomainIP;
      this.bestMDomainTime = this.MDomainTime;
    }
    if ((this.bestMDomainIP == null) && (this.mLstBackupIPs != null) && (this.mLstBackupIPs.size() != 0))
      return (String)this.mLstBackupIPs.get(0);
    if (this.bestMDomainIP == null)
    {
      if (InfoManager.getInstance().enableNewCDN())
        return InfoManager.getInstance().getNewCDN();
      return this.mdomain;
    }
    return this.bestMDomainIP;
  }

  public long getBestMDomainTime()
  {
    if ((this.MDomainTime < 2147483647L) && (this.MDomainTime < 2L * this.bestMDomainTime))
    {
      this.bestMDomainIP = this.mDomainIP;
      this.bestMDomainTime = this.MDomainTime;
    }
    return this.bestMDomainTime;
  }

  public String getMDomain()
  {
    return this.mdomain;
  }

  public String getMDomainIP()
  {
    if (this.mDomainIP != null)
      return this.mDomainIP;
    if (this.mdomain != null);
    try
    {
      this.mDomainIP = InetAddress.getByName(this.mdomain).getHostAddress();
      return this.mDomainIP;
    }
    catch (UnknownHostException localUnknownHostException)
    {
      while (true)
        localUnknownHostException.printStackTrace();
    }
  }

  public String getPKURL(String paramString)
  {
    if (paramString == null)
      return null;
    return "http://" + paramString + "/" + this.testpath;
  }

  public String getSDomainIP()
  {
    if (this.sDomainIP != null)
      return this.sDomainIP;
    if (this.sdomain != null);
    try
    {
      this.sDomainIP = InetAddress.getByName(this.sdomain).getHostAddress();
      return this.sDomainIP;
    }
    catch (UnknownHostException localUnknownHostException)
    {
      while (true)
        localUnknownHostException.printStackTrace();
    }
  }

  public long getTotalBackUPSTime()
  {
    if (this.totalBackupSIPTime == -1L);
    while (this.sIPCnt <= 0)
      return 2147483647L;
    return this.totalBackupSIPTime / this.sIPCnt;
  }

  public boolean hasPinged()
  {
    return this.hasPinged;
  }

  public void setBestBackUpIP(String paramString, long paramLong)
  {
    if (paramString == null);
    while (paramLong >= this.bestBackupIPTime)
      return;
    this.bestBackupIPTime = paramLong;
    this.bestBackupIP = paramString;
  }

  public void setBestMDomainIP(String paramString, long paramLong)
  {
    if (paramString == null);
    while ((paramLong >= 2147483647L) || (paramLong >= this.bestMDomainTime))
      return;
    this.bestMDomainTime = paramLong;
    this.bestMDomainIP = paramString;
  }

  public void setMReachTime(long paramLong)
  {
    this.MDomainTime = paramLong;
  }

  public void setPinged(boolean paramBoolean)
  {
    this.hasPinged = paramBoolean;
  }

  public void setSReachTime(long paramLong)
  {
    this.SDomainTime = paramLong;
  }

  public void setTotalBackUPSTime(long paramLong)
  {
    if (this.totalBackupSIPTime == -1L)
      this.totalBackupSIPTime = 0L;
    if (paramLong != 2147483647L)
    {
      this.totalBackupSIPTime = (paramLong + this.totalBackupSIPTime);
      this.sIPCnt = (1 + this.sIPCnt);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.PingInfo
 * JD-Core Version:    0.6.2
 */