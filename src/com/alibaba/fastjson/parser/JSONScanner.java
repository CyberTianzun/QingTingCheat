package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.Base64;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

public final class JSONScanner extends JSONLexerBase
{
  protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
  public final int ISO8601_LEN_0 = "0000-00-00".length();
  public final int ISO8601_LEN_1 = "0000-00-00T00:00:00".length();
  public final int ISO8601_LEN_2 = "0000-00-00T00:00:00.000".length();
  private final String text;

  public JSONScanner(String paramString)
  {
    this(paramString, JSON.DEFAULT_PARSER_FEATURE);
  }

  public JSONScanner(String paramString, int paramInt)
  {
    this.features = paramInt;
    this.text = paramString;
    this.bp = -1;
    next();
    if (this.ch == 65279)
      next();
  }

  public JSONScanner(char[] paramArrayOfChar, int paramInt)
  {
    this(paramArrayOfChar, paramInt, JSON.DEFAULT_PARSER_FEATURE);
  }

  public JSONScanner(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    this(new String(paramArrayOfChar, 0, paramInt1), paramInt2);
  }

  static final boolean charArrayCompare(String paramString, int paramInt, char[] paramArrayOfChar)
  {
    int i = paramArrayOfChar.length;
    if (i + paramInt > paramString.length())
      return false;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label45;
      if (paramArrayOfChar[j] != paramString.charAt(paramInt + j))
        break;
    }
    label45: return true;
  }

  static boolean checkDate(char paramChar1, char paramChar2, char paramChar3, char paramChar4, char paramChar5, char paramChar6, int paramInt1, int paramInt2)
  {
    if ((paramChar1 != '1') && (paramChar1 != '2'));
    label71: 
    do
    {
      do
      {
        do
          return false;
        while ((paramChar2 < '0') || (paramChar2 > '9') || (paramChar3 < '0') || (paramChar3 > '9') || (paramChar4 < '0') || (paramChar4 > '9'));
        if (paramChar5 != '0')
          break;
      }
      while ((paramChar6 < '1') || (paramChar6 > '9'));
      if (paramInt1 != 48)
        break label124;
    }
    while ((paramInt2 < 49) || (paramInt2 > 57));
    label124: 
    do
    {
      do
      {
        return true;
        if (paramChar5 != '1')
          break;
        if ((paramChar6 == '0') || (paramChar6 == '1') || (paramChar6 == '2'))
          break label71;
        return false;
        if ((paramInt1 != 49) && (paramInt1 != 50))
          break label154;
        if (paramInt2 < 48)
          break;
      }
      while (paramInt2 <= 57);
      return false;
      if (paramInt1 != 51)
        break;
    }
    while ((paramInt2 == 48) || (paramInt2 == 49));
    label154: return false;
  }

  private boolean checkTime(char paramChar1, char paramChar2, char paramChar3, char paramChar4, char paramChar5, char paramChar6)
  {
    if (paramChar1 == '0')
      if ((paramChar2 >= '0') && (paramChar2 <= '9'))
        break label38;
    label38: label64: 
    do
    {
      do
      {
        do
        {
          return false;
          if (paramChar1 != '1')
            break;
        }
        while ((paramChar2 < '0') || (paramChar2 > '9'));
        if ((paramChar3 < '0') || (paramChar3 > '5'))
          break label114;
      }
      while ((paramChar4 < '0') || (paramChar4 > '9'));
      if ((paramChar5 < '0') || (paramChar5 > '5'))
        break label129;
    }
    while ((paramChar6 < '0') || (paramChar6 > '9'));
    label114: 
    do
    {
      return true;
      if ((paramChar1 != '2') || (paramChar2 < '0'))
        break;
      if (paramChar2 <= '4')
        break label38;
      return false;
      if (paramChar3 != '6')
        break;
      if (paramChar4 == '0')
        break label64;
      return false;
      if (paramChar5 != '6')
        break;
    }
    while (paramChar6 == '0');
    label129: return false;
  }

  private void setCalendar(char paramChar1, char paramChar2, char paramChar3, char paramChar4, char paramChar5, char paramChar6, char paramChar7, char paramChar8)
  {
    Locale localLocale = Locale.getDefault();
    this.calendar = Calendar.getInstance(TimeZone.getDefault(), localLocale);
    int i = 1000 * digits[paramChar1] + 100 * digits[paramChar2] + 10 * digits[paramChar3] + digits[paramChar4];
    int j = -1 + (10 * digits[paramChar5] + digits[paramChar6]);
    int k = 10 * digits[paramChar7] + digits[paramChar8];
    this.calendar.set(1, i);
    this.calendar.set(2, j);
    this.calendar.set(5, k);
  }

  public final String addSymbol(int paramInt1, int paramInt2, int paramInt3, SymbolTable paramSymbolTable)
  {
    return paramSymbolTable.addSymbol(this.text, paramInt1, paramInt2, paramInt3);
  }

  protected final void arrayCopy(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
  {
    this.text.getChars(paramInt1, paramInt1 + paramInt3, paramArrayOfChar, paramInt2);
  }

  public byte[] bytesValue()
  {
    return Base64.decodeFast(this.text, 1 + this.np, this.sp);
  }

  public final boolean charArrayCompare(char[] paramArrayOfChar)
  {
    return charArrayCompare(this.text, this.bp, paramArrayOfChar);
  }

  public final char charAt(int paramInt)
  {
    if (paramInt >= this.text.length())
      return '\032';
    return this.text.charAt(paramInt);
  }

  protected final void copyTo(int paramInt1, int paramInt2, char[] paramArrayOfChar)
  {
    this.text.getChars(paramInt1, paramInt1 + paramInt2, paramArrayOfChar, 0);
  }

  public final int indexOf(char paramChar, int paramInt)
  {
    return this.text.indexOf(paramChar, paramInt);
  }

  public boolean isEOF()
  {
    return (this.bp == this.text.length()) || ((this.ch == '\032') && (1 + this.bp == this.text.length()));
  }

  public final char next()
  {
    int i = 1 + this.bp;
    this.bp = i;
    char c = charAt(i);
    this.ch = c;
    return c;
  }

  public final String numberString()
  {
    int i = charAt(-1 + (this.np + this.sp));
    int j = this.sp;
    if ((i == 76) || (i == 83) || (i == 66) || (i == 70) || (i == 68))
      j--;
    return this.text.substring(this.np, j + this.np);
  }

  public boolean scanFieldBoolean(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return false;
    }
    int i = this.bp + paramArrayOfChar.length;
    int j = i + 1;
    int k = charAt(i);
    int i3;
    boolean bool;
    if (k == 116)
    {
      int i10 = j + 1;
      if (charAt(j) != 'r')
      {
        this.matchStat = -1;
        return false;
      }
      int i11 = i10 + 1;
      if (charAt(i10) != 'u')
      {
        this.matchStat = -1;
        return false;
      }
      int i12 = i11 + 1;
      if (charAt(i11) != 'e')
      {
        this.matchStat = -1;
        return false;
      }
      this.bp = i12;
      i3 = charAt(this.bp);
      bool = true;
    }
    while (i3 == 44)
    {
      int i9 = 1 + this.bp;
      this.bp = i9;
      this.ch = charAt(i9);
      this.matchStat = 3;
      this.token = 16;
      return bool;
      if (k == 102)
      {
        int m = j + 1;
        if (charAt(j) != 'a')
        {
          this.matchStat = -1;
          return false;
        }
        int n = m + 1;
        if (charAt(m) != 'l')
        {
          this.matchStat = -1;
          return false;
        }
        int i1 = n + 1;
        if (charAt(n) != 's')
        {
          this.matchStat = -1;
          return false;
        }
        int i2 = i1 + 1;
        if (charAt(i1) != 'e')
        {
          this.matchStat = -1;
          return false;
        }
        this.bp = i2;
        i3 = charAt(this.bp);
        bool = false;
      }
      else
      {
        this.matchStat = -1;
        return false;
      }
    }
    if (i3 == 125)
    {
      int i4 = 1 + this.bp;
      this.bp = i4;
      int i5 = charAt(i4);
      if (i5 == 44)
      {
        this.token = 16;
        int i8 = 1 + this.bp;
        this.bp = i8;
        this.ch = charAt(i8);
      }
      while (true)
      {
        this.matchStat = 4;
        return bool;
        if (i5 == 93)
        {
          this.token = 15;
          int i7 = 1 + this.bp;
          this.bp = i7;
          this.ch = charAt(i7);
        }
        else if (i5 == 125)
        {
          this.token = 13;
          int i6 = 1 + this.bp;
          this.bp = i6;
          this.ch = charAt(i6);
        }
        else
        {
          if (i5 != 26)
            break;
          this.token = 20;
        }
      }
      this.matchStat = -1;
      return false;
    }
    this.matchStat = -1;
    return false;
  }

  public int scanFieldInt(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    int i = this.bp;
    char c = this.ch;
    int n;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      n = 0;
    }
    int i3;
    do
    {
      return n;
      int j = this.bp + paramArrayOfChar.length;
      int k = j + 1;
      int m = charAt(j);
      if ((m >= 48) && (m <= 57))
      {
        n = digits[m];
        int i2;
        for (int i1 = k; ; i1 = i2)
        {
          i2 = i1 + 1;
          i3 = charAt(i1);
          if ((i3 < 48) || (i3 > 57))
            break;
          n = n * 10 + digits[i3];
        }
        if (i3 == 46)
        {
          this.matchStat = -1;
          return 0;
        }
        this.bp = (i2 - 1);
        if (n < 0)
        {
          this.matchStat = -1;
          return 0;
        }
      }
      else
      {
        this.matchStat = -1;
        return 0;
      }
      if (i3 == 44)
      {
        int i9 = 1 + this.bp;
        this.bp = i9;
        this.ch = charAt(i9);
        this.matchStat = 3;
        this.token = 16;
        return n;
      }
    }
    while (i3 != 125);
    int i4 = 1 + this.bp;
    this.bp = i4;
    int i5 = charAt(i4);
    if (i5 == 44)
    {
      this.token = 16;
      int i8 = 1 + this.bp;
      this.bp = i8;
      this.ch = charAt(i8);
    }
    while (true)
    {
      this.matchStat = 4;
      return n;
      if (i5 == 93)
      {
        this.token = 15;
        int i7 = 1 + this.bp;
        this.bp = i7;
        this.ch = charAt(i7);
      }
      else if (i5 == 125)
      {
        this.token = 13;
        int i6 = 1 + this.bp;
        this.bp = i6;
        this.ch = charAt(i6);
      }
      else
      {
        if (i5 != 26)
          break;
        this.token = 20;
      }
    }
    this.bp = i;
    this.ch = c;
    this.matchStat = -1;
    return 0;
  }

  public long scanFieldLong(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    int i = this.bp;
    char c = this.ch;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return 0L;
    }
    int j = this.bp + paramArrayOfChar.length;
    int k = j + 1;
    int m = charAt(j);
    long l;
    int i2;
    if ((m >= 48) && (m <= 57))
    {
      l = digits[m];
      int i1;
      for (int n = k; ; n = i1)
      {
        i1 = n + 1;
        i2 = charAt(n);
        if ((i2 < 48) || (i2 > 57))
          break;
        l = 10L * l + digits[i2];
      }
      if (i2 == 46)
      {
        this.matchStat = -1;
        return 0L;
      }
      this.bp = (i1 - 1);
      if (l < 0L)
      {
        this.bp = i;
        this.ch = c;
        this.matchStat = -1;
        return 0L;
      }
    }
    else
    {
      this.bp = i;
      this.ch = c;
      this.matchStat = -1;
      return 0L;
    }
    if (i2 == 44)
    {
      int i8 = 1 + this.bp;
      this.bp = i8;
      charAt(i8);
      this.matchStat = 3;
      this.token = 16;
      return l;
    }
    if (i2 == 125)
    {
      int i3 = 1 + this.bp;
      this.bp = i3;
      int i4 = charAt(i3);
      if (i4 == 44)
      {
        this.token = 16;
        int i7 = 1 + this.bp;
        this.bp = i7;
        this.ch = charAt(i7);
      }
      while (true)
      {
        this.matchStat = 4;
        return l;
        if (i4 == 93)
        {
          this.token = 15;
          int i6 = 1 + this.bp;
          this.bp = i6;
          this.ch = charAt(i6);
        }
        else if (i4 == 125)
        {
          this.token = 13;
          int i5 = 1 + this.bp;
          this.bp = i5;
          this.ch = charAt(i5);
        }
        else
        {
          if (i4 != 26)
            break;
          this.token = 20;
        }
      }
      this.bp = i;
      this.ch = c;
      this.matchStat = -1;
      return 0L;
    }
    this.matchStat = -1;
    return 0L;
  }

  public String scanFieldString(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    int i = this.bp;
    char c1 = this.ch;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return stringDefaultValue();
    }
    int j = this.bp + paramArrayOfChar.length;
    int k = j + 1;
    if (charAt(j) != '"')
    {
      this.matchStat = -1;
      return stringDefaultValue();
    }
    int m = this.text.indexOf('"', k);
    if (m == -1)
      throw new JSONException("unclosed str");
    String str = subString(k, m - k);
    for (int n = 0; ; n++)
    {
      int i1 = str.length();
      int i2 = 0;
      if (n < i1)
      {
        if (str.charAt(n) == '\\')
          i2 = 1;
      }
      else
      {
        if (i2 == 0)
          break;
        this.matchStat = -1;
        return stringDefaultValue();
      }
    }
    this.bp = (m + 1);
    char c2 = charAt(this.bp);
    this.ch = c2;
    if (c2 == ',')
    {
      int i8 = 1 + this.bp;
      this.bp = i8;
      this.ch = charAt(i8);
      this.matchStat = 3;
      return str;
    }
    if (c2 == '}')
    {
      int i3 = 1 + this.bp;
      this.bp = i3;
      int i4 = charAt(i3);
      if (i4 == 44)
      {
        this.token = 16;
        int i7 = 1 + this.bp;
        this.bp = i7;
        this.ch = charAt(i7);
      }
      while (true)
      {
        this.matchStat = 4;
        return str;
        if (i4 == 93)
        {
          this.token = 15;
          int i6 = 1 + this.bp;
          this.bp = i6;
          this.ch = charAt(i6);
        }
        else if (i4 == 125)
        {
          this.token = 13;
          int i5 = 1 + this.bp;
          this.bp = i5;
          this.ch = charAt(i5);
        }
        else
        {
          if (i4 != 26)
            break;
          this.token = 20;
        }
      }
      this.bp = i;
      this.ch = c1;
      this.matchStat = -1;
      return stringDefaultValue();
    }
    this.matchStat = -1;
    return stringDefaultValue();
  }

  public Collection<String> scanFieldStringArray(char[] paramArrayOfChar, Class<?> paramClass)
  {
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return null;
    }
    Object localObject;
    if (paramClass.isAssignableFrom(HashSet.class))
      localObject = new HashSet();
    int j;
    while (true)
    {
      int i = this.bp + paramArrayOfChar.length;
      j = i + 1;
      if (charAt(i) != '[')
      {
        this.matchStat = -1;
        return null;
        if (paramClass.isAssignableFrom(ArrayList.class))
        {
          localObject = new ArrayList();
          continue;
        }
        try
        {
          localObject = (Collection)paramClass.newInstance();
        }
        catch (Exception localException)
        {
          throw new JSONException(localException.getMessage(), localException);
        }
      }
    }
    int k = j + 1;
    int m = charAt(j);
    if (m != 34)
    {
      this.matchStat = -1;
      return null;
    }
    int n = k;
    while (true)
    {
      int i1 = k + 1;
      int i2 = charAt(k);
      int i3;
      int i4;
      if (i2 == 34)
      {
        ((Collection)localObject).add(this.text.substring(n, i1 - 1));
        i3 = i1 + 1;
        i4 = charAt(i1);
        if (i4 != 44)
          break label255;
        int i10 = i3 + 1;
        m = charAt(i3);
        k = i10;
        break;
      }
      if (i2 == 92)
      {
        this.matchStat = -1;
        return null;
        label255: int i6;
        if (i4 == 93)
        {
          int i5 = i3 + 1;
          i6 = charAt(i3);
          this.bp = i5;
          if (i6 == 44)
          {
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return localObject;
          }
        }
        else
        {
          this.matchStat = -1;
          return null;
        }
        if (i6 == 125)
        {
          char c = charAt(this.bp);
          if (c == ',')
          {
            this.token = 16;
            int i9 = 1 + this.bp;
            this.bp = i9;
            this.ch = charAt(i9);
          }
          while (true)
          {
            this.matchStat = 4;
            return localObject;
            if (c == ']')
            {
              this.token = 15;
              int i8 = 1 + this.bp;
              this.bp = i8;
              this.ch = charAt(i8);
            }
            else if (c == '}')
            {
              this.token = 13;
              int i7 = 1 + this.bp;
              this.bp = i7;
              this.ch = charAt(i7);
            }
            else
            {
              if (c != '\032')
                break;
              this.token = 20;
              this.ch = c;
            }
          }
          this.matchStat = -1;
          return null;
        }
        this.matchStat = -1;
        return null;
      }
      k = i1;
    }
  }

  public String scanFieldSymbol(char[] paramArrayOfChar, SymbolTable paramSymbolTable)
  {
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, paramArrayOfChar))
    {
      this.matchStat = -2;
      return null;
    }
    int i = this.bp + paramArrayOfChar.length;
    int j = i + 1;
    if (charAt(i) != '"')
    {
      this.matchStat = -1;
      return null;
    }
    int k = 0;
    int n;
    for (int m = j; ; m = n)
    {
      n = m + 1;
      int i1 = charAt(m);
      char c;
      String str;
      if (i1 == 34)
      {
        this.bp = n;
        c = charAt(this.bp);
        this.ch = c;
        str = paramSymbolTable.addSymbol(this.text, j, -1 + (n - j), k);
        if (c == ',')
        {
          int i7 = 1 + this.bp;
          this.bp = i7;
          this.ch = charAt(i7);
          this.matchStat = 3;
          return str;
        }
      }
      else
      {
        k = i1 + k * 31;
        if (i1 != 92)
          continue;
        this.matchStat = -1;
        return null;
      }
      if (c == '}')
      {
        int i2 = 1 + this.bp;
        this.bp = i2;
        int i3 = charAt(i2);
        if (i3 == 44)
        {
          this.token = 16;
          int i6 = 1 + this.bp;
          this.bp = i6;
          this.ch = charAt(i6);
        }
        while (true)
        {
          this.matchStat = 4;
          return str;
          if (i3 == 93)
          {
            this.token = 15;
            int i5 = 1 + this.bp;
            this.bp = i5;
            this.ch = charAt(i5);
          }
          else if (i3 == 125)
          {
            this.token = 13;
            int i4 = 1 + this.bp;
            this.bp = i4;
            this.ch = charAt(i4);
          }
          else
          {
            if (i3 != 26)
              break;
            this.token = 20;
          }
        }
        this.matchStat = -1;
        return null;
      }
      this.matchStat = -1;
      return null;
    }
  }

  public boolean scanISO8601DateIfMatch()
  {
    return scanISO8601DateIfMatch(true);
  }

  public boolean scanISO8601DateIfMatch(boolean paramBoolean)
  {
    int i = this.text.length() - this.bp;
    if ((!paramBoolean) && (i > 13))
    {
      int i15 = charAt(this.bp);
      int i16 = charAt(1 + this.bp);
      int i17 = charAt(2 + this.bp);
      int i18 = charAt(3 + this.bp);
      int i19 = charAt(4 + this.bp);
      int i20 = charAt(5 + this.bp);
      int i21 = charAt(-1 + (i + this.bp));
      int i22 = charAt(-2 + (i + this.bp));
      if ((i15 == 47) && (i16 == 68) && (i17 == 97) && (i18 == 116) && (i19 == 101) && (i20 == 40) && (i21 == 47) && (i22 == 41))
      {
        int i23 = -1;
        int i24 = 6;
        if (i24 < i)
        {
          int i26 = charAt(i24 + this.bp);
          if (i26 == 43)
            i23 = i24;
          while ((i26 >= 48) && (i26 <= 57))
          {
            i24++;
            break;
          }
        }
        if (i23 == -1)
          return false;
        int i25 = 6 + this.bp;
        long l = Long.parseLong(subString(i25, i23 - i25));
        Locale localLocale = Locale.getDefault();
        this.calendar = Calendar.getInstance(TimeZone.getDefault(), localLocale);
        this.calendar.setTimeInMillis(l);
        this.token = 5;
        return true;
      }
    }
    if ((i == 8) || (i == 14) || (i == 17))
    {
      if (paramBoolean)
        return false;
      char c1 = charAt(this.bp);
      char c2 = charAt(1 + this.bp);
      char c3 = charAt(2 + this.bp);
      char c4 = charAt(3 + this.bp);
      char c5 = charAt(4 + this.bp);
      char c6 = charAt(5 + this.bp);
      char c7 = charAt(6 + this.bp);
      char c8 = charAt(7 + this.bp);
      if (!checkDate(c1, c2, c3, c4, c5, c6, c7, c8))
        return false;
      setCalendar(c1, c2, c3, c4, c5, c6, c7, c8);
      char c13;
      char c14;
      int k;
      int j;
      int m;
      if (i != 8)
      {
        char c9 = charAt(8 + this.bp);
        char c10 = charAt(9 + this.bp);
        char c11 = charAt(10 + this.bp);
        char c12 = charAt(11 + this.bp);
        c13 = charAt(12 + this.bp);
        c14 = charAt(13 + this.bp);
        if (!checkTime(c9, c10, c11, c12, c13, c14))
          return false;
        if (i == 17)
        {
          int i1 = charAt(14 + this.bp);
          int i2 = charAt(15 + this.bp);
          int i3 = charAt(16 + this.bp);
          if ((i1 < 48) || (i1 > 57))
            return false;
          if ((i2 < 48) || (i2 > 57))
            return false;
          if ((i3 < 48) || (i3 > 57))
            return false;
          k = 100 * digits[i1] + 10 * digits[i2] + digits[i3];
          j = 10 * digits[c9] + digits[c10];
          m = 10 * digits[c11] + digits[c12];
        }
      }
      for (int n = 10 * digits[c13] + digits[c14]; ; n = 0)
      {
        this.calendar.set(11, j);
        this.calendar.set(12, m);
        this.calendar.set(13, n);
        this.calendar.set(14, k);
        this.token = 5;
        return true;
        k = 0;
        break;
        j = 0;
        k = 0;
        m = 0;
      }
    }
    if (i < this.ISO8601_LEN_0)
      return false;
    if (charAt(4 + this.bp) != '-')
      return false;
    if (charAt(7 + this.bp) != '-')
      return false;
    char c15 = charAt(this.bp);
    char c16 = charAt(1 + this.bp);
    char c17 = charAt(2 + this.bp);
    char c18 = charAt(3 + this.bp);
    char c19 = charAt(5 + this.bp);
    char c20 = charAt(6 + this.bp);
    char c21 = charAt(8 + this.bp);
    char c22 = charAt(9 + this.bp);
    if (!checkDate(c15, c16, c17, c18, c19, c20, c21, c22))
      return false;
    setCalendar(c15, c16, c17, c18, c19, c20, c21, c22);
    int i4 = charAt(10 + this.bp);
    if ((i4 == 84) || ((i4 == 32) && (!paramBoolean)))
    {
      if (i < this.ISO8601_LEN_1)
        return false;
    }
    else
    {
      if ((i4 == 34) || (i4 == 26))
      {
        this.calendar.set(11, 0);
        this.calendar.set(12, 0);
        this.calendar.set(13, 0);
        this.calendar.set(14, 0);
        int i14 = 10 + this.bp;
        this.bp = i14;
        this.ch = charAt(i14);
        this.token = 5;
        return true;
      }
      return false;
    }
    if (charAt(13 + this.bp) != ':')
      return false;
    if (charAt(16 + this.bp) != ':')
      return false;
    char c23 = charAt(11 + this.bp);
    char c24 = charAt(12 + this.bp);
    char c25 = charAt(14 + this.bp);
    char c26 = charAt(15 + this.bp);
    char c27 = charAt(17 + this.bp);
    char c28 = charAt(18 + this.bp);
    if (!checkTime(c23, c24, c25, c26, c27, c28))
      return false;
    int i5 = 10 * digits[c23] + digits[c24];
    int i6 = 10 * digits[c25] + digits[c26];
    int i7 = 10 * digits[c27] + digits[c28];
    this.calendar.set(11, i5);
    this.calendar.set(12, i6);
    this.calendar.set(13, i7);
    if (charAt(19 + this.bp) == '.')
    {
      if (i < this.ISO8601_LEN_2)
        return false;
    }
    else
    {
      this.calendar.set(14, 0);
      int i8 = 19 + this.bp;
      this.bp = i8;
      this.ch = charAt(i8);
      this.token = 5;
      return true;
    }
    int i9 = charAt(20 + this.bp);
    int i10 = charAt(21 + this.bp);
    int i11 = charAt(22 + this.bp);
    if ((i9 < 48) || (i9 > 57))
      return false;
    if ((i10 < 48) || (i10 > 57))
      return false;
    if ((i11 < 48) || (i11 > 57))
      return false;
    int i12 = 100 * digits[i9] + 10 * digits[i10] + digits[i11];
    this.calendar.set(14, i12);
    int i13 = 23 + this.bp;
    this.bp = i13;
    this.ch = charAt(i13);
    this.token = 5;
    return true;
  }

  public final int scanType(String paramString)
  {
    int i = -1;
    this.matchStat = 0;
    if (!charArrayCompare(this.text, this.bp, typeFieldName))
      i = -2;
    label78: int n;
    do
    {
      return i;
      int j = this.bp + typeFieldName.length;
      int k = paramString.length();
      for (int m = 0; ; m++)
      {
        if (m >= k)
          break label78;
        if (paramString.charAt(m) != charAt(j + m))
          break;
      }
      n = j + k;
    }
    while (charAt(n) != '"');
    int i1 = n + 1;
    this.ch = charAt(i1);
    if (this.ch == ',')
    {
      int i2 = i1 + 1;
      this.ch = charAt(i2);
      this.bp = i2;
      this.token = 16;
      return 3;
    }
    if (this.ch == '}')
    {
      i1++;
      this.ch = charAt(i1);
      if (this.ch != ',')
        break label216;
      this.token = 16;
      i1++;
      this.ch = charAt(i1);
    }
    while (true)
    {
      this.matchStat = 4;
      this.bp = i1;
      return this.matchStat;
      label216: if (this.ch == ']')
      {
        this.token = 15;
        i1++;
        this.ch = charAt(i1);
      }
      else if (this.ch == '}')
      {
        this.token = 13;
        i1++;
        this.ch = charAt(i1);
      }
      else
      {
        if (this.ch != '\032')
          break;
        this.token = 20;
      }
    }
  }

  public final String stringVal()
  {
    if (!this.hasSpecial)
      return this.text.substring(1 + this.np, 1 + this.np + this.sp);
    return new String(this.sbuf, 0, this.sp);
  }

  public final String subString(int paramInt1, int paramInt2)
  {
    return this.text.substring(paramInt1, paramInt1 + paramInt2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.JSONScanner
 * JD-Core Version:    0.6.2
 */