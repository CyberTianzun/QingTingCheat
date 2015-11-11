package cn.com.mma.mobile.tracking.bean;

public class OfflineCache
{
  public String length;
  public String queueExpirationSecs;
  public String timeout;

  public String toString()
  {
    return "<offlineCache>\r\n<length>" + this.length + "</length>\r\n<queueExpirationSecs>" + this.queueExpirationSecs + "</queueExpirationSecs>\r\n<timeout>" + this.timeout + "</timeout></offlineCache>\r\n";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.mma.mobile.tracking.bean.OfflineCache
 * JD-Core Version:    0.6.2
 */