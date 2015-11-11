package com.umeng.message.proguard;

import android.os.Message;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

public class ap extends an
{
  private String[] a = { "image/jpeg", "image/png" };

  public ap()
  {
  }

  public ap(String[] paramArrayOfString)
  {
    this();
    this.a = paramArrayOfString;
  }

  void a(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      super.a(paramMessage);
      return;
    case 0:
      c((byte[])paramMessage.obj);
      return;
    case 1:
    }
    Object[] arrayOfObject = (Object[])paramMessage.obj;
    c((Throwable)arrayOfObject[0], (byte[])arrayOfObject[1]);
  }

  public void a(Throwable paramThrowable, byte[] paramArrayOfByte)
  {
    b(a(1, new Object[] { paramThrowable, paramArrayOfByte }));
  }

  void a(HttpResponse paramHttpResponse)
  {
    int i = 0;
    StatusLine localStatusLine = paramHttpResponse.getStatusLine();
    Header[] arrayOfHeader = paramHttpResponse.getHeaders("Content-Type");
    if (arrayOfHeader.length != 1)
    {
      a(new HttpResponseException(localStatusLine.getStatusCode(), "None, or more than one, Content-Type Header found!"), null);
      return;
    }
    Header localHeader = arrayOfHeader[0];
    String[] arrayOfString = this.a;
    int j = arrayOfString.length;
    for (int k = 0; k < j; k++)
      if (arrayOfString[k].equals(localHeader.getValue()))
        i = 1;
    if (i == 0)
    {
      a(new HttpResponseException(localStatusLine.getStatusCode(), "Content-Type not allowed!"), null);
      return;
    }
    while (true)
    {
      try
      {
        HttpEntity localHttpEntity = paramHttpResponse.getEntity();
        if (localHttpEntity != null)
        {
          localBufferedHttpEntity = new BufferedHttpEntity(localHttpEntity);
          byte[] arrayOfByte2 = EntityUtils.toByteArray(localBufferedHttpEntity);
          arrayOfByte1 = arrayOfByte2;
          if (localStatusLine.getStatusCode() >= 300)
          {
            a(new HttpResponseException(localStatusLine.getStatusCode(), localStatusLine.getReasonPhrase()), arrayOfByte1);
            return;
          }
        }
      }
      catch (IOException localIOException)
      {
        a(localIOException, (byte[])null);
        byte[] arrayOfByte1 = null;
        continue;
        b(arrayOfByte1);
        return;
      }
      BufferedHttpEntity localBufferedHttpEntity = null;
    }
  }

  public void a(byte[] paramArrayOfByte)
  {
  }

  public void b(Throwable paramThrowable, byte[] paramArrayOfByte)
  {
    onFailure(paramThrowable);
  }

  public void b(byte[] paramArrayOfByte)
  {
    b(a(0, paramArrayOfByte));
  }

  void c(Throwable paramThrowable, byte[] paramArrayOfByte)
  {
    b(paramThrowable, paramArrayOfByte);
  }

  void c(byte[] paramArrayOfByte)
  {
    a(paramArrayOfByte);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.ap
 * JD-Core Version:    0.6.2
 */