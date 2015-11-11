package org.apache.commons.httpclient;

class HttpMethodBase$1
  implements ResponseConsumedWatcher
{
  private final HttpMethodBase this$0;

  HttpMethodBase$1(HttpMethodBase paramHttpMethodBase)
  {
    this.this$0 = paramHttpMethodBase;
  }

  public void responseConsumed()
  {
    this.this$0.responseBodyConsumed();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HttpMethodBase.1
 * JD-Core Version:    0.6.2
 */