package org.apache.commons.httpclient.methods.multipart;

import java.io.IOException;
import java.io.InputStream;

public abstract interface PartSource
{
  public abstract InputStream createInputStream()
    throws IOException;

  public abstract String getFileName();

  public abstract long getLength();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.methods.multipart.PartSource
 * JD-Core Version:    0.6.2
 */