package org.json;

public class JSONTokener
{
  private int myIndex = 0;
  private String mySource;

  public JSONTokener(String paramString)
  {
    this.mySource = paramString;
  }

  public static int dehexchar(char paramChar)
  {
    if ((paramChar >= '0') && (paramChar <= '9'))
      return paramChar - '0';
    if ((paramChar >= 'A') && (paramChar <= 'F'))
      return paramChar - '7';
    if ((paramChar >= 'a') && (paramChar <= 'f'))
      return paramChar - 'W';
    return -1;
  }

  public void back()
  {
    if (this.myIndex > 0)
      this.myIndex = (-1 + this.myIndex);
  }

  public boolean more()
  {
    return this.myIndex < this.mySource.length();
  }

  public char next()
  {
    if (more())
    {
      char c = this.mySource.charAt(this.myIndex);
      this.myIndex = (1 + this.myIndex);
      return c;
    }
    return '\000';
  }

  public char next(char paramChar)
    throws JSONException
  {
    char c = next();
    if (c != paramChar)
      throw syntaxError("Expected '" + paramChar + "' and instead saw '" + c + "'");
    return c;
  }

  public String next(int paramInt)
    throws JSONException
  {
    int i = this.myIndex;
    int j = i + paramInt;
    if (j >= this.mySource.length())
      throw syntaxError("Substring bounds error");
    this.myIndex = (paramInt + this.myIndex);
    return this.mySource.substring(i, j);
  }

  public char nextClean()
    throws JSONException
  {
    char c;
    do
      while (true)
      {
        c = next();
        if (c == '/')
        {
          switch (next())
          {
          default:
            back();
            return '/';
          case '/':
            int k;
            do
            {
              k = next();
              if ((k == 10) || (k == 13))
                break;
            }
            while (k != 0);
            break;
          case '*':
          }
          do
          {
            back();
            int j;
            do
            {
              j = next();
              if (j == 0)
                throw syntaxError("Unclosed comment");
            }
            while (j != 42);
          }
          while (next() != '/');
        }
        else
        {
          if (c != '#')
            break;
          int i;
          do
          {
            i = next();
            if ((i == 10) || (i == 13))
              break;
          }
          while (i != 0);
        }
      }
    while ((c != 0) && (c <= ' '));
    return c;
  }

  public String nextString(char paramChar)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    while (true)
    {
      char c1 = next();
      switch (c1)
      {
      default:
        if (c1 == paramChar)
          return localStringBuffer.toString();
        break;
      case '\000':
      case '\n':
      case '\r':
        throw syntaxError("Unterminated string");
      case '\\':
        char c2 = next();
        switch (c2)
        {
        default:
          localStringBuffer.append(c2);
          break;
        case 'b':
          localStringBuffer.append('\b');
          break;
        case 't':
          localStringBuffer.append('\t');
          break;
        case 'n':
          localStringBuffer.append('\n');
          break;
        case 'f':
          localStringBuffer.append('\f');
          break;
        case 'r':
          localStringBuffer.append('\r');
          break;
        case 'u':
          localStringBuffer.append((char)Integer.parseInt(next(4), 16));
          break;
        case 'x':
          localStringBuffer.append((char)Integer.parseInt(next(2), 16));
        }
        break;
      }
      localStringBuffer.append(c1);
    }
  }

  public String nextTo(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    while (true)
    {
      char c = next();
      if ((c == paramChar) || (c == 0) || (c == '\n') || (c == '\r'))
      {
        if (c != 0)
          back();
        return localStringBuffer.toString().trim();
      }
      localStringBuffer.append(c);
    }
  }

  public String nextTo(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    while (true)
    {
      char c = next();
      if ((paramString.indexOf(c) >= 0) || (c == 0) || (c == '\n') || (c == '\r'))
      {
        if (c != 0)
          back();
        return localStringBuffer.toString().trim();
      }
      localStringBuffer.append(c);
    }
  }

  public Object nextValue()
    throws JSONException
  {
    char c1 = nextClean();
    StringBuffer localStringBuffer;
    char c2;
    switch (c1)
    {
    default:
      localStringBuffer = new StringBuffer();
      c2 = c1;
    case '"':
    case '\'':
    case '{':
    case '[':
    }
    while ((c2 >= ' ') && (",:]}/\\\"[{;=#".indexOf(c2) < 0))
    {
      localStringBuffer.append(c2);
      c2 = next();
      continue;
      return nextString(c1);
      back();
      return new JSONObject(this);
      back();
      return new JSONArray(this);
    }
    back();
    String str = localStringBuffer.toString().trim();
    if (str.equals(""))
      throw syntaxError("Missing value");
    if (str.equalsIgnoreCase("true"))
      return Boolean.TRUE;
    if (str.equalsIgnoreCase("false"))
      return Boolean.FALSE;
    if (str.equalsIgnoreCase("null"))
      return JSONObject.NULL;
    if (((c1 >= '0') && (c1 <= '9')) || (c1 == '.') || (c1 == '-') || (c1 == '+'))
    {
      if (c1 == '0')
      {
        if ((str.length() <= 2) || ((str.charAt(1) != 'x') && (str.charAt(1) != 'X')))
          break label334;
        try
        {
          Integer localInteger3 = new Integer(Integer.parseInt(str.substring(2), 16));
          return localInteger3;
        }
        catch (Exception localException5)
        {
        }
      }
      while (true)
      {
        try
        {
          Integer localInteger1 = new Integer(str);
          return localInteger1;
        }
        catch (Exception localException1)
        {
          try
          {
            Long localLong = new Long(str);
            return localLong;
          }
          catch (Exception localException2)
          {
            try
            {
              Double localDouble = new Double(str);
              return localDouble;
            }
            catch (Exception localException3)
            {
              return str;
            }
          }
        }
        try
        {
          label334: Integer localInteger2 = new Integer(Integer.parseInt(str, 8));
          return localInteger2;
        }
        catch (Exception localException4)
        {
        }
      }
    }
    return str;
  }

  public boolean skipPast(String paramString)
  {
    this.myIndex = this.mySource.indexOf(paramString, this.myIndex);
    if (this.myIndex < 0)
    {
      this.myIndex = this.mySource.length();
      return false;
    }
    this.myIndex += paramString.length();
    return true;
  }

  public char skipTo(char paramChar)
  {
    int i = this.myIndex;
    char c;
    do
    {
      c = next();
      if (c == 0)
      {
        this.myIndex = i;
        return c;
      }
    }
    while (c != paramChar);
    back();
    return c;
  }

  public JSONException syntaxError(String paramString)
  {
    return new JSONException(paramString + toString());
  }

  public String toString()
  {
    return " at character " + this.myIndex + " of " + this.mySource;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.json.JSONTokener
 * JD-Core Version:    0.6.2
 */