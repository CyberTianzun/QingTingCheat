package fm.qingting.download.qtradiodownload.network.http;

import java.util.HashMap;

public class HttpRequest
{
  private byte[] data = null;
  private HashMap<String, String> headers = null;
  private String method = null;
  private String url;

  public HttpRequest(String paramString)
  {
    this(paramString, "GET");
  }

  public HttpRequest(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, null);
  }

  public HttpRequest(String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    this.url = paramString1;
    this.method = paramString2;
    this.data = paramArrayOfByte;
  }

  public HttpRequest(String paramString, byte[] paramArrayOfByte)
  {
    this(paramString, "POST", paramArrayOfByte);
  }

  public byte[] getData()
  {
    return this.data;
  }

  public String getHeader(String paramString)
  {
    return (String)this.headers.get(paramString);
  }

  public HashMap<String, String> getHeaders()
  {
    return this.headers;
  }

  public String getMethod()
  {
    return this.method;
  }

  public String getUrl()
  {
    return this.url;
  }

  public void setHeader(String paramString1, String paramString2)
  {
    if (this.headers == null)
      this.headers = new HashMap();
    this.headers.put(paramString1, paramString2);
  }

  public void setHeaders(HashMap<String, String> paramHashMap)
  {
    if ((paramHashMap == null) || (paramHashMap.size() <= 0))
      return;
    this.headers.putAll(paramHashMap);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.http.HttpRequest
 * JD-Core Version:    0.6.2
 */