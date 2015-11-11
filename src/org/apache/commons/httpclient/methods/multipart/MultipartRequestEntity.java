package org.apache.commons.httpclient.methods.multipart;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultipartRequestEntity
  implements RequestEntity
{
  private static byte[] MULTIPART_CHARS;
  private static final String MULTIPART_FORM_CONTENT_TYPE = "multipart/form-data";
  static Class class$org$apache$commons$httpclient$methods$multipart$MultipartRequestEntity;
  private static final Log log;
  private byte[] multipartBoundary;
  private HttpMethodParams params;
  protected Part[] parts;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$methods$multipart$MultipartRequestEntity == null)
    {
      localClass = class$("org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity");
      class$org$apache$commons$httpclient$methods$multipart$MultipartRequestEntity = localClass;
    }
    while (true)
    {
      log = LogFactory.getLog(localClass);
      MULTIPART_CHARS = EncodingUtil.getAsciiBytes("-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
      return;
      localClass = class$org$apache$commons$httpclient$methods$multipart$MultipartRequestEntity;
    }
  }

  public MultipartRequestEntity(Part[] paramArrayOfPart, HttpMethodParams paramHttpMethodParams)
  {
    if (paramArrayOfPart == null)
      throw new IllegalArgumentException("parts cannot be null");
    if (paramHttpMethodParams == null)
      throw new IllegalArgumentException("params cannot be null");
    this.parts = paramArrayOfPart;
    this.params = paramHttpMethodParams;
  }

  static Class class$(String paramString)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }

  private static byte[] generateMultipartBoundary()
  {
    Random localRandom = new Random();
    byte[] arrayOfByte = new byte[30 + localRandom.nextInt(11)];
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfByte.length)
        return arrayOfByte;
      arrayOfByte[i] = MULTIPART_CHARS[localRandom.nextInt(MULTIPART_CHARS.length)];
    }
  }

  public long getContentLength()
  {
    try
    {
      long l = Part.getLengthOfParts(this.parts, getMultipartBoundary());
      return l;
    }
    catch (Exception localException)
    {
      log.error("An exception occurred while getting the length of the parts", localException);
    }
    return 0L;
  }

  public String getContentType()
  {
    StringBuffer localStringBuffer = new StringBuffer("multipart/form-data");
    localStringBuffer.append("; boundary=");
    localStringBuffer.append(EncodingUtil.getAsciiString(getMultipartBoundary()));
    return localStringBuffer.toString();
  }

  protected byte[] getMultipartBoundary()
  {
    String str;
    if (this.multipartBoundary == null)
    {
      str = (String)this.params.getParameter("http.method.multipart.boundary");
      if (str == null)
        break label37;
    }
    label37: for (this.multipartBoundary = EncodingUtil.getAsciiBytes(str); ; this.multipartBoundary = generateMultipartBoundary())
      return this.multipartBoundary;
  }

  public boolean isRepeatable()
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.parts.length)
        return true;
      if (!this.parts[i].isRepeatable())
        return false;
    }
  }

  public void writeRequest(OutputStream paramOutputStream)
    throws IOException
  {
    Part.sendParts(paramOutputStream, this.parts, getMultipartBoundary());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity
 * JD-Core Version:    0.6.2
 */