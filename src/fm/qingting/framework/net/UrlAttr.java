package fm.qingting.framework.net;

public class UrlAttr
{
  private String lastModified = null;

  public UrlAttr(String paramString)
  {
    this.lastModified = paramString;
  }

  public String getLastModified()
  {
    return this.lastModified;
  }

  public void setLastModified(String paramString)
  {
    this.lastModified = paramString;
  }

  public String toString()
  {
    return "Last-Modified:" + this.lastModified;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.net.UrlAttr
 * JD-Core Version:    0.6.2
 */