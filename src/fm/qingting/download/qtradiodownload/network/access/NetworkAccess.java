package fm.qingting.download.qtradiodownload.network.access;

public abstract class NetworkAccess
{
  protected String ipAddress = null;
  protected String type = null;

  public abstract void connect(String paramString);

  public abstract void disconnect();

  public String getIpAddress()
  {
    return this.ipAddress;
  }

  public abstract String getNetworkName();

  public abstract String getType();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.access.NetworkAccess
 * JD-Core Version:    0.6.2
 */