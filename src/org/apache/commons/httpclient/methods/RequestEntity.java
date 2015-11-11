package org.apache.commons.httpclient.methods;

import java.io.IOException;
import java.io.OutputStream;

public abstract interface RequestEntity
{
  public abstract long getContentLength();

  public abstract String getContentType();

  public abstract boolean isRepeatable();

  public abstract void writeRequest(OutputStream paramOutputStream)
    throws IOException;
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.methods.RequestEntity
 * JD-Core Version:    0.6.2
 */