package com.talkingdata.pingan.sdk;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

class s
  implements HttpRequestInterceptor
{
  s(ag paramag, String paramString)
  {
  }

  public void process(HttpRequest paramHttpRequest, HttpContext paramHttpContext)
  {
    for (Header localHeader : paramHttpRequest.getAllHeaders())
      if (!"Content-Length".equalsIgnoreCase(localHeader.getName()))
        paramHttpRequest.removeHeader(localHeader);
    paramHttpRequest.setHeader("Host", this.a);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.talkingdata.pingan.sdk.s
 * JD-Core Version:    0.6.2
 */