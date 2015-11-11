package fm.qingting.async.http;

import java.net.URI;

public class AsyncHttpGet extends AsyncHttpRequest
{
  public static final String METHOD = "GET";

  public AsyncHttpGet(String paramString)
  {
    super(URI.create(paramString), "GET");
  }

  public AsyncHttpGet(URI paramURI)
  {
    super(paramURI, "GET");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.AsyncHttpGet
 * JD-Core Version:    0.6.2
 */