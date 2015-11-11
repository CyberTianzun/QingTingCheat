package org.apache.commons.httpclient.methods;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.NameValuePair;

public class StringRequestEntity
  implements RequestEntity
{
  private String charset;
  private byte[] content;
  private String contentType;

  public StringRequestEntity(String paramString)
  {
    if (paramString == null)
      throw new IllegalArgumentException("The content cannot be null");
    this.contentType = null;
    this.charset = null;
    this.content = paramString.getBytes();
  }

  public StringRequestEntity(String paramString1, String paramString2, String paramString3)
    throws UnsupportedEncodingException
  {
    if (paramString1 == null)
      throw new IllegalArgumentException("The content cannot be null");
    this.contentType = paramString2;
    this.charset = paramString3;
    HeaderElement[] arrayOfHeaderElement;
    NameValuePair localNameValuePair;
    int i;
    if (paramString2 != null)
    {
      arrayOfHeaderElement = HeaderElement.parseElements(paramString2);
      localNameValuePair = null;
      i = 0;
      if (i < arrayOfHeaderElement.length)
        break label90;
      label52: if ((paramString3 != null) || (localNameValuePair == null))
        break label113;
      this.charset = localNameValuePair.getValue();
    }
    while (true)
    {
      if (this.charset == null)
        break label152;
      this.content = paramString1.getBytes(this.charset);
      return;
      label90: localNameValuePair = arrayOfHeaderElement[i].getParameterByName("charset");
      if (localNameValuePair != null)
        break label52;
      i++;
      break;
      label113: if ((paramString3 != null) && (localNameValuePair == null))
        this.contentType = (paramString2 + "; charset=" + paramString3);
    }
    label152: this.content = paramString1.getBytes();
  }

  public String getCharset()
  {
    return this.charset;
  }

  public String getContent()
  {
    if (this.charset != null)
      try
      {
        String str = new String(this.content, this.charset);
        return str;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        return new String(this.content);
      }
    return new String(this.content);
  }

  public long getContentLength()
  {
    return this.content.length;
  }

  public String getContentType()
  {
    return this.contentType;
  }

  public boolean isRepeatable()
  {
    return true;
  }

  public void writeRequest(OutputStream paramOutputStream)
    throws IOException
  {
    if (paramOutputStream == null)
      throw new IllegalArgumentException("Output stream may not be null");
    paramOutputStream.write(this.content);
    paramOutputStream.flush();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.methods.StringRequestEntity
 * JD-Core Version:    0.6.2
 */