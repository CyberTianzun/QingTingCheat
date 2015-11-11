package org.apache.commons.httpclient.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.NameValuePair;

public class ParameterParser
{
  private char[] chars = null;
  private int i1 = 0;
  private int i2 = 0;
  private int len = 0;
  private int pos = 0;

  private String getToken(boolean paramBoolean)
  {
    if ((this.i1 >= this.i2) || (!Character.isWhitespace(this.chars[this.i1])));
    while (true)
    {
      if ((this.i2 <= this.i1) || (!Character.isWhitespace(this.chars[(-1 + this.i2)])))
      {
        if ((paramBoolean) && (this.i2 - this.i1 >= 2) && (this.chars[this.i1] == '"') && (this.chars[(-1 + this.i2)] == '"'))
        {
          this.i1 = (1 + this.i1);
          this.i2 = (-1 + this.i2);
        }
        int i = this.i2;
        int j = this.i1;
        String str = null;
        if (i >= j)
          str = new String(this.chars, this.i1, this.i2 - this.i1);
        return str;
        this.i1 = (1 + this.i1);
        break;
      }
      this.i2 = (-1 + this.i2);
    }
  }

  private boolean hasChar()
  {
    return this.pos < this.len;
  }

  private boolean isOneOf(char paramChar, char[] paramArrayOfChar)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfChar.length)
        return false;
      if (paramChar == paramArrayOfChar[i])
        return true;
    }
  }

  private String parseQuotedToken(char[] paramArrayOfChar)
  {
    this.i1 = this.pos;
    this.i2 = this.pos;
    int i = 0;
    int j = 0;
    if (!hasChar());
    char c;
    do
    {
      return getToken(true);
      c = this.chars[this.pos];
    }
    while ((i == 0) && (isOneOf(c, paramArrayOfChar)));
    if ((j == 0) && (c == '"'))
    {
      if (i == 0)
        i = 1;
    }
    else
      label75: if ((j != 0) || (c != '\\'))
        break label116;
    label116: for (j = 1; ; j = 0)
    {
      this.i2 = (1 + this.i2);
      this.pos = (1 + this.pos);
      break;
      i = 0;
      break label75;
    }
  }

  private String parseToken(char[] paramArrayOfChar)
  {
    this.i1 = this.pos;
    this.i2 = this.pos;
    while (true)
    {
      if (!hasChar());
      while (isOneOf(this.chars[this.pos], paramArrayOfChar))
        return getToken(false);
      this.i2 = (1 + this.i2);
      this.pos = (1 + this.pos);
    }
  }

  public List parse(String paramString, char paramChar)
  {
    if (paramString == null)
      return new ArrayList();
    return parse(paramString.toCharArray(), paramChar);
  }

  public List parse(char[] paramArrayOfChar, char paramChar)
  {
    if (paramArrayOfChar == null)
      return new ArrayList();
    return parse(paramArrayOfChar, 0, paramArrayOfChar.length, paramChar);
  }

  public List parse(char[] paramArrayOfChar, int paramInt1, int paramInt2, char paramChar)
  {
    ArrayList localArrayList;
    if (paramArrayOfChar == null)
      localArrayList = new ArrayList();
    while (true)
    {
      return localArrayList;
      localArrayList = new ArrayList();
      this.chars = paramArrayOfChar;
      this.pos = paramInt1;
      this.len = paramInt2;
      while (hasChar())
      {
        String str1 = parseToken(new char[] { '=', paramChar });
        boolean bool = hasChar();
        String str2 = null;
        if (bool)
        {
          int i = paramArrayOfChar[this.pos];
          str2 = null;
          if (i == 61)
          {
            this.pos = (1 + this.pos);
            str2 = parseQuotedToken(new char[] { paramChar });
          }
        }
        if ((hasChar()) && (paramArrayOfChar[this.pos] == paramChar))
          this.pos = (1 + this.pos);
        if ((str1 != null) && ((!str1.equals("")) || (str2 != null)))
          localArrayList.add(new NameValuePair(str1, str2));
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.util.ParameterParser
 * JD-Core Version:    0.6.2
 */