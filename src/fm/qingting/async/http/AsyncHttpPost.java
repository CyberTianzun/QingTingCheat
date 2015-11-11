package fm.qingting.async.http;

import java.net.URI;

public class AsyncHttpPost extends AsyncHttpRequest
{
  public static final String METHOD = "POST";

  public AsyncHttpPost(String paramString)
  {
    this(URI.create(paramString));
  }

  public AsyncHttpPost(URI paramURI)
  {
    super(paramURI, "POST");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.AsyncHttpPost
 * JD-Core Version:    0.6.2
 */