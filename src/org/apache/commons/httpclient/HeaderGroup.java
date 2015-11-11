package org.apache.commons.httpclient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HeaderGroup
{
  private List headers = new ArrayList();

  public void addHeader(Header paramHeader)
  {
    this.headers.add(paramHeader);
  }

  public void clear()
  {
    this.headers.clear();
  }

  public boolean containsHeader(String paramString)
  {
    Iterator localIterator = this.headers.iterator();
    do
      if (!localIterator.hasNext())
        return false;
    while (!((Header)localIterator.next()).getName().equalsIgnoreCase(paramString));
    return true;
  }

  public Header[] getAllHeaders()
  {
    return (Header[])this.headers.toArray(new Header[this.headers.size()]);
  }

  public Header getCondensedHeader(String paramString)
  {
    Header[] arrayOfHeader = getHeaders(paramString);
    if (arrayOfHeader.length == 0)
      return null;
    if (arrayOfHeader.length == 1)
      return new Header(arrayOfHeader[0].getName(), arrayOfHeader[0].getValue());
    StringBuffer localStringBuffer = new StringBuffer(arrayOfHeader[0].getValue());
    for (int i = 1; ; i++)
    {
      if (i >= arrayOfHeader.length)
        return new Header(paramString.toLowerCase(), localStringBuffer.toString());
      localStringBuffer.append(", ");
      localStringBuffer.append(arrayOfHeader[i].getValue());
    }
  }

  public Header getFirstHeader(String paramString)
  {
    Iterator localIterator = this.headers.iterator();
    Header localHeader;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localHeader = (Header)localIterator.next();
    }
    while (!localHeader.getName().equalsIgnoreCase(paramString));
    return localHeader;
  }

  public Header[] getHeaders(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.headers.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return (Header[])localArrayList.toArray(new Header[localArrayList.size()]);
      Header localHeader = (Header)localIterator.next();
      if (localHeader.getName().equalsIgnoreCase(paramString))
        localArrayList.add(localHeader);
    }
  }

  public Iterator getIterator()
  {
    return this.headers.iterator();
  }

  public Header getLastHeader(String paramString)
  {
    for (int i = -1 + this.headers.size(); ; i--)
    {
      Header localHeader;
      if (i < 0)
        localHeader = null;
      do
      {
        return localHeader;
        localHeader = (Header)this.headers.get(i);
      }
      while (localHeader.getName().equalsIgnoreCase(paramString));
    }
  }

  public void removeHeader(Header paramHeader)
  {
    this.headers.remove(paramHeader);
  }

  public void setHeaders(Header[] paramArrayOfHeader)
  {
    clear();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfHeader.length)
        return;
      addHeader(paramArrayOfHeader[i]);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HeaderGroup
 * JD-Core Version:    0.6.2
 */