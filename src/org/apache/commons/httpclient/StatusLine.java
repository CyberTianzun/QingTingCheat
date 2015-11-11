package org.apache.commons.httpclient;

public class StatusLine
{
  private final String httpVersion;
  private final String reasonPhrase;
  private final int statusCode;
  private final String statusLine;

  public StatusLine(String paramString)
    throws HttpException
  {
    int i = paramString.length();
    int j = 0;
    int k = 0;
    try
    {
      int n;
      while (true)
      {
        if (!Character.isWhitespace(paramString.charAt(k)))
        {
          n = k + 4;
          try
          {
            if ("HTTP".equals(paramString.substring(k, n)))
              break;
            throw new HttpException("Status-Line '" + paramString + "' does not start with HTTP");
          }
          catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException2)
          {
          }
          label82: throw new HttpException("Status-Line '" + paramString + "' is not valid");
        }
        int m = k + 1;
        j++;
        k = m;
      }
      int i1 = paramString.indexOf(" ", n);
      if (i1 <= 0)
        throw new ProtocolException("Unable to parse HTTP-Version from the status line: '" + paramString + "'");
      this.httpVersion = paramString.substring(j, i1).toUpperCase();
      int i3;
      if (paramString.charAt(i1) != ' ')
      {
        int i2 = paramString.indexOf(" ", i1);
        i3 = i2;
        if (i3 < 0)
          i3 = i;
      }
      while (true)
      {
        try
        {
          this.statusCode = Integer.parseInt(paramString.substring(i1, i3));
          int i4 = i3 + 1;
          if (i4 < i)
          {
            this.reasonPhrase = paramString.substring(i4).trim();
            this.statusLine = paramString;
            return;
            i1++;
          }
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new ProtocolException("Unable to parse status code from status line: '" + paramString + "'");
        }
        this.reasonPhrase = "";
      }
    }
    catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException1)
    {
      break label82;
    }
  }

  public static boolean startsWithHTTP(String paramString)
  {
    int i = 0;
    try
    {
      while (true)
      {
        if (!Character.isWhitespace(paramString.charAt(i)))
        {
          boolean bool = "HTTP".equals(paramString.substring(i, i + 4));
          return bool;
        }
        i++;
      }
    }
    catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException)
    {
    }
    return false;
  }

  public final String getHttpVersion()
  {
    return this.httpVersion;
  }

  public final String getReasonPhrase()
  {
    return this.reasonPhrase;
  }

  public final int getStatusCode()
  {
    return this.statusCode;
  }

  public final String toString()
  {
    return this.statusLine;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.StatusLine
 * JD-Core Version:    0.6.2
 */