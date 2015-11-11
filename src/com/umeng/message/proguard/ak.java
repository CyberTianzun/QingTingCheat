package com.umeng.message.proguard;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

class ak
  implements HttpResponseInterceptor
{
  ak(ai paramai)
  {
  }

  public void process(HttpResponse paramHttpResponse, HttpContext paramHttpContext)
  {
    HttpEntity localHttpEntity = paramHttpResponse.getEntity();
    if (localHttpEntity == null);
    while (true)
    {
      return;
      Header localHeader = localHttpEntity.getContentEncoding();
      if (localHeader != null)
      {
        HeaderElement[] arrayOfHeaderElement = localHeader.getElements();
        int i = arrayOfHeaderElement.length;
        for (int j = 0; j < i; j++)
          if (arrayOfHeaderElement[j].getName().equalsIgnoreCase("gzip"))
          {
            paramHttpResponse.setEntity(new ai.a(paramHttpResponse.getEntity()));
            return;
          }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.ak
 * JD-Core Version:    0.6.2
 */