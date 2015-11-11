package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import java.io.Closeable;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

public abstract class JSONLexerBase
  implements JSONLexer, Closeable
{
  protected static final int INT_MULTMIN_RADIX_TEN = -214748364;
  protected static final int INT_N_MULTMAX_RADIX_TEN = -214748364;
  protected static final long MULTMIN_RADIX_TEN = -922337203685477580L;
  protected static final long N_MULTMAX_RADIX_TEN = -922337203685477580L;
  private static final ThreadLocal<SoftReference<char[]>> SBUF_REF_LOCAL = new ThreadLocal();
  protected static final int[] digits;
  protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
  protected static boolean[] whitespaceFlags = new boolean[256];
  protected int bp;
  protected Calendar calendar = null;
  protected char ch;
  protected int eofPos;
  protected int features = JSON.DEFAULT_PARSER_FEATURE;
  protected boolean hasSpecial;
  protected Keywords keywods = Keywords.DEFAULT_KEYWORDS;
  public int matchStat = 0;
  protected int np;
  protected int pos;
  protected char[] sbuf;
  protected int sp;
  protected int token;

  static
  {
    whitespaceFlags[32] = true;
    whitespaceFlags[10] = true;
    whitespaceFlags[13] = true;
    whitespaceFlags[9] = true;
    whitespaceFlags[12] = true;
    whitespaceFlags[8] = true;
    digits = new int[103];
    for (int i = 48; i <= 57; i++)
      digits[i] = (i - 48);
    for (int j = 97; j <= 102; j++)
      digits[j] = (10 + (j - 97));
    for (int k = 65; k <= 70; k++)
      digits[k] = (10 + (k - 65));
  }

  public JSONLexerBase()
  {
    SoftReference localSoftReference = (SoftReference)SBUF_REF_LOCAL.get();
    if (localSoftReference != null)
    {
      this.sbuf = ((char[])localSoftReference.get());
      SBUF_REF_LOCAL.set(null);
    }
    if (this.sbuf == null)
      this.sbuf = new char[64];
  }

  public static final boolean isWhitespace(char paramChar)
  {
    return (paramChar == ' ') || (paramChar == '\n') || (paramChar == '\r') || (paramChar == '\t') || (paramChar == '\f') || (paramChar == '\b');
  }

  private final void scanStringSingleQuote()
  {
    this.np = this.bp;
    this.hasSpecial = false;
    while (true)
    {
      char c1 = next();
      if (c1 == '\'')
      {
        this.token = 4;
        next();
        return;
      }
      if (c1 == '\032')
        throw new JSONException("unclosed single-quote string");
      if (c1 == '\\')
      {
        if (!this.hasSpecial)
        {
          this.hasSpecial = true;
          if (this.sp > this.sbuf.length)
          {
            char[] arrayOfChar2 = new char[2 * this.sp];
            System.arraycopy(this.sbuf, 0, arrayOfChar2, 0, this.sbuf.length);
            this.sbuf = arrayOfChar2;
          }
          copyTo(1 + this.np, this.sp, this.sbuf);
        }
        char c2 = next();
        switch (c2)
        {
        default:
          this.ch = c2;
          throw new JSONException("unclosed single-quote string");
        case '0':
          putChar('\000');
          break;
        case '1':
          putChar('\001');
          break;
        case '2':
          putChar('\002');
          break;
        case '3':
          putChar('\003');
          break;
        case '4':
          putChar('\004');
          break;
        case '5':
          putChar('\005');
          break;
        case '6':
          putChar('\006');
          break;
        case '7':
          putChar('\007');
          break;
        case 'b':
          putChar('\b');
          break;
        case 't':
          putChar('\t');
          break;
        case 'n':
          putChar('\n');
          break;
        case 'v':
          putChar('\013');
          break;
        case 'F':
        case 'f':
          putChar('\f');
          break;
        case 'r':
          putChar('\r');
          break;
        case '"':
          putChar('"');
          break;
        case '\'':
          putChar('\'');
          break;
        case '/':
          putChar('/');
          break;
        case '\\':
          putChar('\\');
          break;
        case 'x':
          int j = next();
          int k = next();
          putChar((char)(16 * digits[j] + digits[k]));
          break;
        case 'u':
          putChar((char)Integer.parseInt(new String(new char[] { next(), next(), next(), next() }), 16));
          break;
        }
      }
      else if (!this.hasSpecial)
      {
        this.sp = (1 + this.sp);
      }
      else if (this.sp == this.sbuf.length)
      {
        putChar(c1);
      }
      else
      {
        char[] arrayOfChar1 = this.sbuf;
        int i = this.sp;
        this.sp = (i + 1);
        arrayOfChar1[i] = c1;
      }
    }
  }

  public abstract String addSymbol(int paramInt1, int paramInt2, int paramInt3, SymbolTable paramSymbolTable);

  protected abstract void arrayCopy(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3);

  public abstract byte[] bytesValue();

  protected abstract boolean charArrayCompare(char[] paramArrayOfChar);

  public abstract char charAt(int paramInt);

  public void close()
  {
    if (this.sbuf.length <= 8192)
      SBUF_REF_LOCAL.set(new SoftReference(this.sbuf));
    this.sbuf = null;
  }

  public void config(Feature paramFeature, boolean paramBoolean)
  {
    this.features = Feature.config(this.features, paramFeature, paramBoolean);
  }

  protected abstract void copyTo(int paramInt1, int paramInt2, char[] paramArrayOfChar);

  public final Number decimalValue(boolean paramBoolean)
  {
    int i = charAt(-1 + (this.np + this.sp));
    if (i == 70)
      return Float.valueOf(Float.parseFloat(numberString()));
    if (i == 68)
      return Double.valueOf(Double.parseDouble(numberString()));
    if (paramBoolean)
      return decimalValue();
    return Double.valueOf(doubleValue());
  }

  public final BigDecimal decimalValue()
  {
    return new BigDecimal(numberString());
  }

  public double doubleValue()
  {
    return Double.parseDouble(numberString());
  }

  public float floatValue()
  {
    return Float.parseFloat(numberString());
  }

  public final int getBufferPosition()
  {
    return this.bp;
  }

  public Calendar getCalendar()
  {
    return this.calendar;
  }

  public final char getCurrent()
  {
    return this.ch;
  }

  public abstract int indexOf(char paramChar, int paramInt);

  public final int intValue()
  {
    int i = this.np;
    int j = this.np + this.sp;
    int n;
    int k;
    int m;
    int i1;
    label80: int i2;
    int i3;
    if (charAt(this.np) == '-')
    {
      n = 1;
      k = -2147483648;
      m = i + 1;
      if (n != 0);
      i1 = 0;
      if (m < j)
      {
        int[] arrayOfInt = digits;
        int i6 = m + 1;
        i1 = -arrayOfInt[charAt(m)];
        m = i6;
      }
      if (m >= j)
        break label237;
      i2 = m + 1;
      i3 = charAt(m);
      if ((i3 != 76) && (i3 != 83) && (i3 != 66))
        break label152;
    }
    while (true)
    {
      if (n != 0)
      {
        if (i2 > 1 + this.np)
        {
          return i1;
          k = -2147483647;
          m = i;
          n = 0;
          break;
          label152: int i4 = digits[i3];
          if (i1 < -214748364)
            throw new NumberFormatException(numberString());
          int i5 = i1 * 10;
          if (i5 < k + i4)
            throw new NumberFormatException(numberString());
          i1 = i5 - i4;
          m = i2;
          break label80;
        }
        throw new NumberFormatException(numberString());
      }
      return -i1;
      label237: i2 = m;
    }
  }

  public final Number integerValue()
    throws NumberFormatException
  {
    long l1 = 0L;
    if (this.np == -1)
      this.np = 0;
    int i = this.np;
    int j = this.np + this.sp;
    int k = 32;
    int n;
    long l2;
    label102: int i3;
    switch (charAt(j - 1))
    {
    default:
      if (charAt(this.np) == '-')
      {
        n = 1;
        l2 = -9223372036854775808L;
        m = i + 1;
        if ((n == 0) || (m < j))
        {
          int[] arrayOfInt2 = digits;
          i3 = m + 1;
          l1 = -arrayOfInt2[charAt(m)];
        }
      }
      break;
    case 'L':
    case 'S':
    case 'B':
    }
    int i1;
    for (int m = i3; ; m = i1)
    {
      if (m >= j)
        break label279;
      int[] arrayOfInt1 = digits;
      i1 = m + 1;
      int i2 = arrayOfInt1[charAt(m)];
      if (l1 < -922337203685477580L)
      {
        return new BigInteger(numberString());
        j--;
        k = 76;
        break;
        j--;
        k = 83;
        break;
        j--;
        k = 66;
        break;
        l2 = -9223372036854775807L;
        m = i;
        n = 0;
        break label102;
      }
      long l4 = l1 * 10L;
      if (l4 < l2 + i2)
        return new BigInteger(numberString());
      l1 = l4 - i2;
    }
    label279: if (n != 0)
    {
      if (m > 1 + this.np)
      {
        if ((l1 >= -2147483648L) && (k != 76))
        {
          Integer localInteger2 = Integer.valueOf((int)l1);
          return localInteger2;
        }
        Long localLong2 = Long.valueOf(l1);
        return localLong2;
      }
      throw new NumberFormatException(numberString());
    }
    long l3 = -l1;
    if ((l3 <= 2147483647L) && (k != 76))
    {
      if (k == 83)
      {
        Short localShort = Short.valueOf((short)(int)l3);
        return localShort;
      }
      if (k == 66)
      {
        Byte localByte = Byte.valueOf((byte)(int)l3);
        return localByte;
      }
      Integer localInteger1 = Integer.valueOf((int)l3);
      return localInteger1;
    }
    Long localLong1 = Long.valueOf(l3);
    return localLong1;
  }

  public final boolean isBlankInput()
  {
    for (int i = 0; ; i++)
    {
      char c = charAt(i);
      if (c == '\032')
        return true;
      if (!isWhitespace(c))
        return false;
    }
  }

  public abstract boolean isEOF();

  public final boolean isEnabled(Feature paramFeature)
  {
    return Feature.isEnabled(this.features, paramFeature);
  }

  public final boolean isRef()
  {
    if (this.sp != 4);
    while ((charAt(1 + this.np) != '$') || (charAt(2 + this.np) != 'r') || (charAt(3 + this.np) != 'e') || (charAt(4 + this.np) != 'f'))
      return false;
    return true;
  }

  protected void lexError(String paramString, Object[] paramArrayOfObject)
  {
    this.token = 1;
  }

  public final long longValue()
    throws NumberFormatException
  {
    long l1 = 0L;
    int i = this.np;
    int j = this.np + this.sp;
    int m;
    long l2;
    int k;
    label83: int n;
    int i1;
    if (charAt(this.np) == '-')
    {
      m = 1;
      l2 = -9223372036854775808L;
      k = i + 1;
      if ((m == 0) || (k < j))
      {
        int[] arrayOfInt = digits;
        int i3 = k + 1;
        l1 = -arrayOfInt[charAt(k)];
        k = i3;
      }
      if (k >= j)
        break label245;
      n = k + 1;
      i1 = charAt(k);
      if ((i1 != 76) && (i1 != 83) && (i1 != 66))
        break label157;
    }
    while (true)
    {
      if (m != 0)
      {
        if (n > 1 + this.np)
        {
          return l1;
          l2 = -9223372036854775807L;
          k = i;
          m = 0;
          break;
          label157: int i2 = digits[i1];
          if (l1 < -922337203685477580L)
            throw new NumberFormatException(numberString());
          long l3 = l1 * 10L;
          if (l3 < l2 + i2)
            throw new NumberFormatException(numberString());
          l1 = l3 - i2;
          k = n;
          break label83;
        }
        throw new NumberFormatException(numberString());
      }
      return -l1;
      label245: n = k;
    }
  }

  public final boolean matchField(char[] paramArrayOfChar)
  {
    if (!charArrayCompare(paramArrayOfChar))
      return false;
    this.bp += paramArrayOfChar.length;
    this.ch = charAt(this.bp);
    if (this.ch == '{')
    {
      next();
      this.token = 12;
    }
    while (true)
    {
      return true;
      if (this.ch == '[')
      {
        next();
        this.token = 14;
      }
      else
      {
        nextToken();
      }
    }
  }

  public final int matchStat()
  {
    return this.matchStat;
  }

  public abstract char next();

  public final void nextIdent()
  {
    while (isWhitespace(this.ch))
      next();
    if ((this.ch == '_') || (Character.isLetter(this.ch)))
    {
      scanIdent();
      return;
    }
    nextToken();
  }

  public final void nextToken()
  {
    this.sp = 0;
    while (true)
    {
      this.pos = this.bp;
      if (this.ch == '"')
      {
        scanString();
        return;
      }
      if (this.ch == ',')
      {
        next();
        this.token = 16;
        return;
      }
      if ((this.ch >= '0') && (this.ch <= '9'))
      {
        scanNumber();
        return;
      }
      if (this.ch == '-')
      {
        scanNumber();
        return;
      }
      switch (this.ch)
      {
      default:
        if (!isEOF())
          break label447;
        if (this.token != 20)
          break;
        throw new JSONException("EOF error");
      case '\'':
        if (!isEnabled(Feature.AllowSingleQuotes))
          throw new JSONException("Feature.AllowSingleQuotes is false");
        scanStringSingleQuote();
        return;
      case '\b':
      case '\t':
      case '\n':
      case '\f':
      case '\r':
      case ' ':
        next();
      case 't':
      case 'T':
      case 'S':
      case 'f':
      case 'n':
      case '(':
      case ')':
      case '[':
      case ']':
      case '{':
      case '}':
      case ':':
      }
    }
    scanTrue();
    return;
    scanTreeSet();
    return;
    scanSet();
    return;
    scanFalse();
    return;
    scanNullOrNew();
    return;
    next();
    this.token = 10;
    return;
    next();
    this.token = 11;
    return;
    next();
    this.token = 14;
    return;
    next();
    this.token = 15;
    return;
    next();
    this.token = 12;
    return;
    next();
    this.token = 13;
    return;
    next();
    this.token = 17;
    return;
    this.token = 20;
    int i = this.eofPos;
    this.bp = i;
    this.pos = i;
    return;
    label447: Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = String.valueOf(this.ch);
    lexError("illegal.char", arrayOfObject);
    next();
  }

  public final void nextToken(int paramInt)
  {
    this.sp = 0;
    switch (paramInt)
    {
    case 3:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    case 10:
    case 11:
    case 13:
    case 17:
    case 19:
    default:
    case 12:
    case 16:
    case 2:
    case 4:
    case 14:
    case 15:
    case 20:
    case 18:
    }
    while (true)
      if ((this.ch == ' ') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\f') || (this.ch == '\b'))
      {
        next();
        break;
        if (this.ch == '{')
        {
          this.token = 12;
          next();
          return;
        }
        if (this.ch == '[')
        {
          this.token = 14;
          next();
          return;
          if (this.ch == ',')
          {
            this.token = 16;
            next();
            return;
          }
          if (this.ch == '}')
          {
            this.token = 13;
            next();
            return;
          }
          if (this.ch == ']')
          {
            this.token = 15;
            next();
            return;
          }
          if (this.ch == '\032')
          {
            this.token = 20;
            return;
            if ((this.ch >= '0') && (this.ch <= '9'))
            {
              this.pos = this.bp;
              scanNumber();
              return;
            }
            if (this.ch == '"')
            {
              this.pos = this.bp;
              scanString();
              return;
            }
            if (this.ch == '[')
            {
              this.token = 14;
              next();
              return;
            }
            if (this.ch == '{')
            {
              this.token = 12;
              next();
              return;
              if (this.ch == '"')
              {
                this.pos = this.bp;
                scanString();
                return;
              }
              if ((this.ch >= '0') && (this.ch <= '9'))
              {
                this.pos = this.bp;
                scanNumber();
                return;
              }
              if (this.ch == '[')
              {
                this.token = 14;
                next();
                return;
              }
              if (this.ch == '{')
              {
                this.token = 12;
                next();
                return;
                if (this.ch == '[')
                {
                  this.token = 14;
                  next();
                  return;
                }
                if (this.ch == '{')
                {
                  this.token = 12;
                  next();
                  return;
                  if (this.ch == ']')
                  {
                    this.token = 15;
                    next();
                    return;
                  }
                  if (this.ch == '\032')
                  {
                    this.token = 20;
                    return;
                    nextIdent();
                    return;
                  }
                }
              }
            }
          }
        }
      }
    nextToken();
  }

  public final void nextTokenWithChar(char paramChar)
  {
    this.sp = 0;
    while (true)
    {
      if (this.ch == paramChar)
      {
        next();
        nextToken();
        return;
      }
      if ((this.ch != ' ') && (this.ch != '\n') && (this.ch != '\r') && (this.ch != '\t') && (this.ch != '\f') && (this.ch != '\b'))
        break;
      next();
    }
    throw new JSONException("not match " + paramChar + " - " + this.ch);
  }

  public final void nextTokenWithChar(char paramChar, int paramInt)
  {
    this.sp = 0;
    if (this.ch == paramChar)
      next();
    while (true)
    {
      if (paramInt == 2)
      {
        if ((this.ch >= '0') && (this.ch <= '9'))
        {
          this.pos = this.bp;
          scanNumber();
          return;
          if (isWhitespace(this.ch))
          {
            next();
            break;
          }
          throw new JSONException("not match " + paramInt + " - " + this.ch);
        }
        if (this.ch != '"')
          break label289;
        this.pos = this.bp;
        scanString();
        return;
      }
      if (paramInt == 4)
      {
        if (this.ch == '"')
        {
          this.pos = this.bp;
          scanString();
          return;
        }
        if ((this.ch >= '0') && (this.ch <= '9'))
        {
          this.pos = this.bp;
          scanNumber();
        }
      }
      else if (paramInt == 12)
      {
        if (this.ch == '{')
        {
          this.token = 12;
          next();
          return;
        }
        if (this.ch == '[')
        {
          this.token = 14;
          next();
        }
      }
      else if (paramInt == 14)
      {
        if (this.ch == '[')
        {
          this.token = 14;
          next();
          return;
        }
        if (this.ch == '{')
        {
          this.token = 12;
          next();
          return;
        }
      }
      label289: if (!isWhitespace(this.ch))
        break label307;
      next();
    }
    label307: nextToken();
  }

  public final void nextTokenWithColon()
  {
    nextTokenWithChar(':');
  }

  public final void nextTokenWithColon(int paramInt)
  {
    nextTokenWithChar(':');
  }

  public final void nextTokenWithComma()
  {
    nextTokenWithChar(':');
  }

  public final void nextTokenWithComma(int paramInt)
  {
    nextTokenWithChar(',');
  }

  public abstract String numberString();

  public final Number numberValue()
  {
    int i = charAt(-1 + (this.np + this.sp));
    String str = numberString();
    switch (i)
    {
    case 69:
    default:
      return new BigDecimal(str);
    case 68:
      return Double.valueOf(Double.parseDouble(str));
    case 70:
    }
    return Float.valueOf(Float.parseFloat(str));
  }

  public final int pos()
  {
    return this.pos;
  }

  protected final void putChar(char paramChar)
  {
    if (this.sp == this.sbuf.length)
    {
      char[] arrayOfChar2 = new char[2 * this.sbuf.length];
      System.arraycopy(this.sbuf, 0, arrayOfChar2, 0, this.sbuf.length);
      this.sbuf = arrayOfChar2;
    }
    char[] arrayOfChar1 = this.sbuf;
    int i = this.sp;
    this.sp = (i + 1);
    arrayOfChar1[i] = paramChar;
  }

  public final void resetStringPosition()
  {
    this.sp = 0;
  }

  public boolean scanBoolean(char paramChar)
  {
    this.matchStat = 0;
    int i = this.bp;
    int j = 0 + 1;
    char c = charAt(i + 0);
    int k;
    boolean bool;
    if (c == 't')
      if ((charAt(1 + this.bp) == 'r') && (charAt(1 + (1 + this.bp)) == 'u') && (charAt(2 + (1 + this.bp)) == 'e'))
      {
        int i1 = j + 3;
        int i2 = this.bp;
        k = i1 + 1;
        c = charAt(i2 + 4);
        bool = true;
      }
    while (true)
      if (c == paramChar)
      {
        this.bp += k - 1;
        next();
        this.matchStat = 3;
        return bool;
        this.matchStat = -1;
        return false;
        if (c == 'f')
        {
          if ((charAt(1 + this.bp) == 'a') && (charAt(1 + (1 + this.bp)) == 'l') && (charAt(2 + (1 + this.bp)) == 's') && (charAt(3 + (1 + this.bp)) == 'e'))
          {
            int m = j + 4;
            int n = this.bp;
            k = m + 1;
            c = charAt(n + 5);
            bool = false;
            continue;
          }
          this.matchStat = -1;
          return false;
        }
      }
      else
      {
        this.matchStat = -1;
        return bool;
        k = j;
        bool = false;
      }
  }

  public Enum<?> scanEnum(Class<?> paramClass, SymbolTable paramSymbolTable, char paramChar)
  {
    String str = scanSymbolWithSeperator(paramSymbolTable, paramChar);
    if (str == null)
      return null;
    return Enum.valueOf(paramClass, str);
  }

  public final void scanFalse()
  {
    if (this.ch != 'f')
      throw new JSONException("error parse false");
    next();
    if (this.ch != 'a')
      throw new JSONException("error parse false");
    next();
    if (this.ch != 'l')
      throw new JSONException("error parse false");
    next();
    if (this.ch != 's')
      throw new JSONException("error parse false");
    next();
    if (this.ch != 'e')
      throw new JSONException("error parse false");
    next();
    if ((this.ch == ' ') || (this.ch == ',') || (this.ch == '}') || (this.ch == ']') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\032') || (this.ch == '\f') || (this.ch == '\b'))
    {
      this.token = 7;
      return;
    }
    throw new JSONException("scan false error");
  }

  public boolean scanFieldBoolean(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(paramArrayOfChar))
    {
      this.matchStat = -2;
      return false;
    }
    int i = paramArrayOfChar.length;
    int j = this.bp;
    int k = i + 1;
    int m = charAt(j + i);
    int i8;
    if (m == 116)
    {
      int i15 = this.bp;
      int i16 = k + 1;
      if (charAt(i15 + k) != 'r')
      {
        this.matchStat = -1;
        return false;
      }
      int i17 = this.bp;
      int i18 = i16 + 1;
      if (charAt(i17 + i16) != 'u')
      {
        this.matchStat = -1;
        return false;
      }
      int i19 = this.bp;
      i8 = i18 + 1;
      if (charAt(i19 + i18) != 'e')
      {
        this.matchStat = -1;
        return false;
      }
    }
    int i10;
    int i11;
    for (boolean bool = true; ; bool = false)
    {
      int i9 = this.bp;
      i10 = i8 + 1;
      i11 = charAt(i9 + i8);
      if (i11 != 44)
        break label370;
      this.bp += i10 - 1;
      next();
      this.matchStat = 3;
      this.token = 16;
      return bool;
      if (m != 102)
        break;
      int n = this.bp;
      int i1 = k + 1;
      if (charAt(n + k) != 'a')
      {
        this.matchStat = -1;
        return false;
      }
      int i2 = this.bp;
      int i3 = i1 + 1;
      if (charAt(i2 + i1) != 'l')
      {
        this.matchStat = -1;
        return false;
      }
      int i4 = this.bp;
      int i5 = i3 + 1;
      if (charAt(i4 + i3) != 's')
      {
        this.matchStat = -1;
        return false;
      }
      int i6 = this.bp;
      int i7 = i5 + 1;
      if (charAt(i6 + i5) != 'e')
      {
        this.matchStat = -1;
        return false;
      }
      i8 = i7;
    }
    this.matchStat = -1;
    return false;
    label370: if (i11 == 125)
    {
      int i12 = this.bp;
      int i13 = i10 + 1;
      int i14 = charAt(i12 + i10);
      if (i14 == 44)
      {
        this.token = 16;
        this.bp += i13 - 1;
        next();
      }
      while (true)
      {
        this.matchStat = 4;
        return bool;
        if (i14 == 93)
        {
          this.token = 15;
          this.bp += i13 - 1;
          next();
        }
        else if (i14 == 125)
        {
          this.token = 13;
          this.bp += i13 - 1;
          next();
        }
        else
        {
          if (i14 != 26)
            break;
          this.token = 20;
          this.bp += i13 - 1;
          this.ch = '\032';
        }
      }
      this.matchStat = -1;
      return false;
    }
    this.matchStat = -1;
    return false;
  }

  public final double scanFieldDouble(char paramChar)
  {
    this.matchStat = 0;
    int i = this.bp;
    int j = 0 + 1;
    int k = charAt(i + 0);
    int i1;
    char c;
    int i3;
    if ((k >= 48) && (k <= 57))
    {
      for (int m = j; ; m = i1)
      {
        int n = this.bp;
        i1 = m + 1;
        c = charAt(n + m);
        if ((c < '0') || (c > '9'))
          break;
      }
      if (c == '.')
      {
        int i9 = this.bp;
        int i10 = i1 + 1;
        int i11 = charAt(i9 + i1);
        if ((i11 >= 48) && (i11 <= 57))
          while (true)
          {
            int i12 = this.bp;
            i1 = i10 + 1;
            c = charAt(i12 + i10);
            if ((c < '0') || (c > '9'))
              break;
            i10 = i1;
          }
        this.matchStat = -1;
        return 0.0D;
      }
      if ((c == 'e') || (c == 'E'))
      {
        int i2 = this.bp;
        i3 = i1 + 1;
        c = charAt(i2 + i1);
        if ((c != '+') && (c != '-'))
          break label383;
        int i4 = this.bp;
        i1 = i3 + 1;
        c = charAt(i4 + i3);
      }
    }
    while (true)
      if ((c >= '0') && (c <= '9'))
      {
        int i7 = this.bp;
        int i8 = i1 + 1;
        c = charAt(i7 + i1);
        i1 = i8;
      }
      else
      {
        int i5 = i1;
        int i6 = this.bp;
        double d = Double.parseDouble(subString(i6, -1 + (i5 + this.bp - i6)));
        if (c == paramChar)
        {
          this.bp += i5 - 1;
          next();
          this.matchStat = 3;
          this.token = 16;
          return d;
          this.matchStat = -1;
          return 0.0D;
        }
        this.matchStat = -1;
        return d;
        label383: i1 = i3;
      }
  }

  public final double scanFieldDouble(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(paramArrayOfChar))
    {
      this.matchStat = -2;
      return 0.0D;
    }
    int i = paramArrayOfChar.length;
    int j = this.bp;
    int k = i + 1;
    int m = charAt(j + i);
    int i2;
    int i3;
    int i5;
    if ((m >= 48) && (m <= 57))
    {
      for (int n = k; ; n = i2)
      {
        int i1 = this.bp;
        i2 = n + 1;
        i3 = charAt(i1 + n);
        if ((i3 < 48) || (i3 > 57))
          break;
      }
      if (i3 == 46)
      {
        int i14 = this.bp;
        int i15 = i2 + 1;
        int i16 = charAt(i14 + i2);
        if ((i16 >= 48) && (i16 <= 57))
          while (true)
          {
            int i17 = this.bp;
            i2 = i15 + 1;
            i3 = charAt(i17 + i15);
            if ((i3 < 48) || (i3 > 57))
              break;
            i15 = i2;
          }
        this.matchStat = -1;
        return 0.0D;
      }
      if ((i3 == 101) || (i3 == 69))
      {
        int i4 = this.bp;
        i5 = i2 + 1;
        i3 = charAt(i4 + i2);
        if ((i3 != 43) && (i3 != 45))
          break label586;
        int i6 = this.bp;
        i2 = i5 + 1;
        i3 = charAt(i6 + i5);
      }
    }
    while (true)
      if ((i3 >= 48) && (i3 <= 57))
      {
        int i12 = this.bp;
        int i13 = i2 + 1;
        i3 = charAt(i12 + i2);
        i2 = i13;
      }
      else
      {
        int i7 = i2;
        int i8 = this.bp + paramArrayOfChar.length;
        double d = Double.parseDouble(subString(i8, -1 + (i7 + this.bp - i8)));
        if (i3 == 44)
        {
          this.bp += i7 - 1;
          next();
          this.matchStat = 3;
          this.token = 16;
          return d;
          this.matchStat = -1;
          return 0.0D;
        }
        if (i3 == 125)
        {
          int i9 = this.bp;
          int i10 = i7 + 1;
          int i11 = charAt(i9 + i7);
          if (i11 == 44)
          {
            this.token = 16;
            this.bp += i10 - 1;
            next();
          }
          while (true)
          {
            this.matchStat = 4;
            return d;
            if (i11 == 93)
            {
              this.token = 15;
              this.bp += i10 - 1;
              next();
            }
            else if (i11 == 125)
            {
              this.token = 13;
              this.bp += i10 - 1;
              next();
            }
            else
            {
              if (i11 != 26)
                break;
              this.token = 20;
              this.bp += i10 - 1;
              this.ch = '\032';
            }
          }
          this.matchStat = -1;
          return 0.0D;
        }
        this.matchStat = -1;
        return 0.0D;
        label586: i2 = i5;
      }
  }

  public final float scanFieldFloat(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(paramArrayOfChar))
    {
      this.matchStat = -2;
      return 0.0F;
    }
    int i = paramArrayOfChar.length;
    int j = this.bp;
    int k = i + 1;
    int m = charAt(j + i);
    int i3;
    int i4;
    float f;
    if ((m >= 48) && (m <= 57))
    {
      int i2;
      for (int n = k; ; n = i2)
      {
        int i1 = this.bp;
        i2 = n + 1;
        i3 = charAt(i1 + n);
        if ((i3 < 48) || (i3 > 57))
          break;
      }
      if (i3 == 46)
      {
        int i9 = this.bp;
        int i10 = i2 + 1;
        int i11 = charAt(i9 + i2);
        if ((i11 >= 48) && (i11 <= 57))
          while (true)
          {
            int i12 = this.bp;
            i2 = i10 + 1;
            i3 = charAt(i12 + i10);
            if ((i3 < 48) || (i3 > 57))
              break;
            i10 = i2;
          }
        this.matchStat = -1;
        return 0.0F;
      }
      i4 = i2;
      int i5 = this.bp + paramArrayOfChar.length;
      f = Float.parseFloat(subString(i5, -1 + (i4 + this.bp - i5)));
      if (i3 == 44)
      {
        this.bp += i4 - 1;
        next();
        this.matchStat = 3;
        this.token = 16;
        return f;
      }
    }
    else
    {
      this.matchStat = -1;
      return 0.0F;
    }
    if (i3 == 125)
    {
      int i6 = this.bp;
      int i7 = i4 + 1;
      int i8 = charAt(i6 + i4);
      if (i8 == 44)
      {
        this.token = 16;
        this.bp += i7 - 1;
        next();
      }
      while (true)
      {
        this.matchStat = 4;
        return f;
        if (i8 == 93)
        {
          this.token = 15;
          this.bp += i7 - 1;
          next();
        }
        else if (i8 == 125)
        {
          this.token = 13;
          this.bp += i7 - 1;
          next();
        }
        else
        {
          if (i8 != 26)
            break;
          this.bp += i7 - 1;
          this.token = 20;
          this.ch = '\032';
        }
      }
      this.matchStat = -1;
      return 0.0F;
    }
    this.matchStat = -1;
    return 0.0F;
  }

  public int scanFieldInt(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(paramArrayOfChar))
    {
      this.matchStat = -2;
      return 0;
    }
    int i = paramArrayOfChar.length;
    int j = this.bp;
    int k = i + 1;
    int m = charAt(j + i);
    int n;
    int i3;
    int i4;
    if ((m >= 48) && (m <= 57))
    {
      n = digits[m];
      for (int i1 = k; ; i1 = i3)
      {
        int i2 = this.bp;
        i3 = i1 + 1;
        i4 = charAt(i2 + i1);
        if ((i4 < 48) || (i4 > 57))
          break;
        n = n * 10 + digits[i4];
      }
      if (i4 == 46)
      {
        this.matchStat = -1;
        return 0;
      }
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
    if (i4 == 44)
    {
      this.bp += i3 - 1;
      next();
      this.matchStat = 3;
      this.token = 16;
      return n;
    }
    if (i4 == 125)
    {
      int i5 = this.bp;
      int i6 = i3 + 1;
      int i7 = charAt(i5 + i3);
      if (i7 == 44)
      {
        this.token = 16;
        this.bp += i6 - 1;
        next();
      }
      while (true)
      {
        this.matchStat = 4;
        return n;
        if (i7 == 93)
        {
          this.token = 15;
          this.bp += i6 - 1;
          next();
        }
        else if (i7 == 125)
        {
          this.token = 13;
          this.bp += i6 - 1;
          next();
        }
        else
        {
          if (i7 != 26)
            break;
          this.token = 20;
          this.bp += i6 - 1;
          this.ch = '\032';
        }
      }
      this.matchStat = -1;
      return 0;
    }
    this.matchStat = -1;
    return 0;
  }

  public long scanFieldLong(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(paramArrayOfChar))
    {
      this.matchStat = -2;
      return 0L;
    }
    int i = paramArrayOfChar.length;
    int j = this.bp;
    int k = i + 1;
    int m = charAt(j + i);
    long l;
    int i2;
    int i3;
    if ((m >= 48) && (m <= 57))
    {
      l = digits[m];
      for (int n = k; ; n = i2)
      {
        int i1 = this.bp;
        i2 = n + 1;
        i3 = charAt(i1 + n);
        if ((i3 < 48) || (i3 > 57))
          break;
        l = 10L * l + digits[i3];
      }
      if (i3 == 46)
      {
        this.matchStat = -1;
        return 0L;
      }
      if (l < 0L)
      {
        this.matchStat = -1;
        return 0L;
      }
    }
    else
    {
      this.matchStat = -1;
      return 0L;
    }
    if (i3 == 44)
    {
      this.bp += i2 - 1;
      next();
      this.matchStat = 3;
      this.token = 16;
      return l;
    }
    if (i3 == 125)
    {
      int i4 = this.bp;
      int i5 = i2 + 1;
      int i6 = charAt(i4 + i2);
      if (i6 == 44)
      {
        this.token = 16;
        this.bp += i5 - 1;
        next();
      }
      while (true)
      {
        this.matchStat = 4;
        return l;
        if (i6 == 93)
        {
          this.token = 15;
          this.bp += i5 - 1;
          next();
        }
        else if (i6 == 125)
        {
          this.token = 13;
          this.bp += i5 - 1;
          next();
        }
        else
        {
          if (i6 != 26)
            break;
          this.token = 20;
          this.bp += i5 - 1;
          this.ch = '\032';
        }
      }
      this.matchStat = -1;
      return 0L;
    }
    this.matchStat = -1;
    return 0L;
  }

  public String scanFieldString(char[] paramArrayOfChar)
  {
    this.matchStat = 0;
    if (!charArrayCompare(paramArrayOfChar))
    {
      this.matchStat = -2;
      return stringDefaultValue();
    }
    int i = paramArrayOfChar.length;
    int j = this.bp;
    int k = i + 1;
    if (charAt(j + i) != '"')
    {
      this.matchStat = -1;
      return stringDefaultValue();
    }
    int m = indexOf('"', 1 + (this.bp + paramArrayOfChar.length));
    if (m == -1)
      throw new JSONException("unclosed str");
    int n = 1 + (this.bp + paramArrayOfChar.length);
    String str = subString(n, m - n);
    for (int i1 = 1 + (this.bp + paramArrayOfChar.length); ; i1++)
    {
      int i2 = 0;
      if (i1 < m)
      {
        if (charAt(i1) == '\\')
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
    int i3 = k + (1 + (m - (1 + (this.bp + paramArrayOfChar.length))));
    int i4 = this.bp;
    int i5 = i3 + 1;
    int i6 = charAt(i4 + i3);
    if (i6 == 44)
    {
      this.bp += i5 - 1;
      next();
      this.matchStat = 3;
      return str;
    }
    if (i6 == 125)
    {
      int i7 = this.bp;
      int i8 = i5 + 1;
      int i9 = charAt(i7 + i5);
      if (i9 == 44)
      {
        this.token = 16;
        this.bp += i8 - 1;
        next();
      }
      while (true)
      {
        this.matchStat = 4;
        return str;
        if (i9 == 93)
        {
          this.token = 15;
          this.bp += i8 - 1;
          next();
        }
        else if (i9 == 125)
        {
          this.token = 13;
          this.bp += i8 - 1;
          next();
        }
        else
        {
          if (i9 != 26)
            break;
          this.token = 20;
          this.bp += i8 - 1;
          this.ch = '\032';
        }
      }
      this.matchStat = -1;
      return stringDefaultValue();
    }
    this.matchStat = -1;
    return stringDefaultValue();
  }

  public Collection<String> scanFieldStringArray(char[] paramArrayOfChar, Class<?> paramClass)
  {
    this.matchStat = 0;
    if (!charArrayCompare(paramArrayOfChar))
    {
      this.matchStat = -2;
      return null;
    }
    Object localObject;
    if (paramClass.isAssignableFrom(HashSet.class))
      localObject = new HashSet();
    int k;
    while (true)
    {
      int i = paramArrayOfChar.length;
      int j = this.bp;
      k = i + 1;
      if (charAt(j + i) != '[')
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
    int m = this.bp;
    int n = k + 1;
    int i1 = charAt(m + k);
    if (i1 != 34)
    {
      this.matchStat = -1;
      return null;
    }
    int i2 = n;
    while (true)
    {
      int i3 = this.bp;
      int i4 = n + 1;
      int i5 = charAt(i3 + n);
      int i8;
      int i9;
      if (i5 == 34)
      {
        int i6 = i2 + this.bp;
        ((Collection)localObject).add(subString(i6, -1 + (i4 + this.bp - i6)));
        int i7 = this.bp;
        i8 = i4 + 1;
        i9 = charAt(i7 + i4);
        if (i9 != 44)
          break label304;
        int i16 = this.bp;
        int i17 = i8 + 1;
        i1 = charAt(i16 + i8);
        n = i17;
        break;
      }
      if (i5 == 92)
      {
        this.matchStat = -1;
        return null;
        label304: int i11;
        int i12;
        if (i9 == 93)
        {
          int i10 = this.bp;
          i11 = i8 + 1;
          i12 = charAt(i10 + i8);
          if (i12 == 44)
          {
            this.bp += i11 - 1;
            next();
            this.matchStat = 3;
            return localObject;
          }
        }
        else
        {
          this.matchStat = -1;
          return null;
        }
        if (i12 == 125)
        {
          int i13 = this.bp;
          int i14 = i11 + 1;
          int i15 = charAt(i13 + i11);
          if (i15 == 44)
          {
            this.token = 16;
            this.bp += i14 - 1;
            next();
          }
          while (true)
          {
            this.matchStat = 4;
            return localObject;
            if (i15 == 93)
            {
              this.token = 15;
              this.bp += i14 - 1;
              next();
            }
            else if (i15 == 125)
            {
              this.token = 13;
              this.bp += i14 - 1;
              next();
            }
            else
            {
              if (i15 != 26)
                break;
              this.bp += i14 - 1;
              this.token = 20;
              this.ch = '\032';
            }
          }
          this.matchStat = -1;
          return null;
        }
        this.matchStat = -1;
        return null;
      }
      n = i4;
    }
  }

  public String scanFieldSymbol(char[] paramArrayOfChar, SymbolTable paramSymbolTable)
  {
    this.matchStat = 0;
    if (!charArrayCompare(paramArrayOfChar))
    {
      this.matchStat = -2;
      return null;
    }
    int i = paramArrayOfChar.length;
    int j = this.bp;
    int k = i + 1;
    if (charAt(j + i) != '"')
    {
      this.matchStat = -1;
      return null;
    }
    int m = 0;
    int i2;
    for (int n = k; ; n = i2)
    {
      int i1 = this.bp;
      i2 = n + 1;
      int i3 = charAt(i1 + n);
      String str;
      int i6;
      int i7;
      if (i3 == 34)
      {
        int i4 = 1 + (this.bp + paramArrayOfChar.length);
        str = addSymbol(i4, -1 + (i2 + this.bp - i4), m, paramSymbolTable);
        int i5 = this.bp;
        i6 = i2 + 1;
        i7 = charAt(i5 + i2);
        if (i7 == 44)
        {
          this.bp += i6 - 1;
          next();
          this.matchStat = 3;
          return str;
        }
      }
      else
      {
        m = i3 + m * 31;
        if (i3 != 92)
          continue;
        this.matchStat = -1;
        return null;
      }
      if (i7 == 125)
      {
        int i8 = this.bp;
        int i9 = i6 + 1;
        int i10 = charAt(i8 + i6);
        if (i10 == 44)
        {
          this.token = 16;
          this.bp += i9 - 1;
          next();
        }
        while (true)
        {
          this.matchStat = 4;
          return str;
          if (i10 == 93)
          {
            this.token = 15;
            this.bp += i9 - 1;
            next();
          }
          else if (i10 == 125)
          {
            this.token = 13;
            this.bp += i9 - 1;
            next();
          }
          else
          {
            if (i10 != 26)
              break;
            this.token = 20;
            this.bp += i9 - 1;
            this.ch = '\032';
          }
        }
        this.matchStat = -1;
        return null;
      }
      this.matchStat = -1;
      return null;
    }
  }

  public final float scanFloat(char paramChar)
  {
    this.matchStat = 0;
    int i = this.bp;
    int j = 0 + 1;
    int k = charAt(i + 0);
    float f;
    if ((k >= 48) && (k <= 57))
    {
      int i1;
      char c;
      for (int m = j; ; m = i1)
      {
        int n = this.bp;
        i1 = m + 1;
        c = charAt(n + m);
        if ((c < '0') || (c > '9'))
          break;
      }
      if (c == '.')
      {
        int i4 = this.bp;
        int i5 = i1 + 1;
        int i6 = charAt(i4 + i1);
        if ((i6 >= 48) && (i6 <= 57))
          while (true)
          {
            int i7 = this.bp;
            i1 = i5 + 1;
            c = charAt(i7 + i5);
            if ((c < '0') || (c > '9'))
              break;
            i5 = i1;
          }
        this.matchStat = -1;
        return 0.0F;
      }
      int i2 = i1;
      int i3 = this.bp;
      f = Float.parseFloat(subString(i3, -1 + (i2 + this.bp - i3)));
      if (c == paramChar)
      {
        this.bp += i2 - 1;
        next();
        this.matchStat = 3;
        this.token = 16;
        return f;
      }
    }
    else
    {
      this.matchStat = -1;
      return 0.0F;
    }
    this.matchStat = -1;
    return f;
  }

  public final void scanIdent()
  {
    this.np = (-1 + this.bp);
    this.hasSpecial = false;
    do
    {
      this.sp = (1 + this.sp);
      next();
    }
    while (Character.isLetterOrDigit(this.ch));
    String str = stringVal();
    Integer localInteger = this.keywods.getKeyword(str);
    if (localInteger != null)
    {
      this.token = localInteger.intValue();
      return;
    }
    this.token = 18;
  }

  public int scanInt(char paramChar)
  {
    this.matchStat = 0;
    int i = this.bp;
    int j = 0 + 1;
    int k = charAt(i + 0);
    int m;
    int i2;
    char c;
    if ((k >= 48) && (k <= 57))
    {
      m = digits[k];
      for (int n = j; ; n = i2)
      {
        int i1 = this.bp;
        i2 = n + 1;
        c = charAt(i1 + n);
        if ((c < '0') || (c > '9'))
          break;
        m = m * 10 + digits[c];
      }
      if (c == '.')
      {
        this.matchStat = -1;
        return 0;
      }
      if (m < 0)
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
    if (c == paramChar)
    {
      this.bp += i2 - 1;
      next();
      this.matchStat = 3;
      this.token = 16;
      return m;
    }
    this.matchStat = -1;
    return m;
  }

  public long scanLong(char paramChar)
  {
    this.matchStat = 0;
    int i = this.bp;
    int j = 0 + 1;
    int k = charAt(i + 0);
    long l;
    int i1;
    char c;
    if ((k >= 48) && (k <= 57))
    {
      l = digits[k];
      for (int m = j; ; m = i1)
      {
        int n = this.bp;
        i1 = m + 1;
        c = charAt(n + m);
        if ((c < '0') || (c > '9'))
          break;
        l = 10L * l + digits[c];
      }
      if (c == '.')
      {
        this.matchStat = -1;
        return 0L;
      }
      if (l < 0L)
      {
        this.matchStat = -1;
        return 0L;
      }
    }
    else
    {
      this.matchStat = -1;
      return 0L;
    }
    if (c == paramChar)
    {
      this.bp += i1 - 1;
      next();
      this.matchStat = 3;
      this.token = 16;
      return l;
    }
    this.matchStat = -1;
    return l;
  }

  public final void scanNullOrNew()
  {
    if (this.ch != 'n')
      throw new JSONException("error parse null or new");
    next();
    if (this.ch == 'u')
    {
      next();
      if (this.ch != 'l')
        throw new JSONException("error parse true");
      next();
      if (this.ch != 'l')
        throw new JSONException("error parse true");
      next();
      if ((this.ch == ' ') || (this.ch == ',') || (this.ch == '}') || (this.ch == ']') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\032') || (this.ch == '\f') || (this.ch == '\b'))
      {
        this.token = 8;
        return;
      }
      throw new JSONException("scan true error");
    }
    if (this.ch != 'e')
      throw new JSONException("error parse e");
    next();
    if (this.ch != 'w')
      throw new JSONException("error parse w");
    next();
    if ((this.ch == ' ') || (this.ch == ',') || (this.ch == '}') || (this.ch == ']') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\032') || (this.ch == '\f') || (this.ch == '\b'))
    {
      this.token = 9;
      return;
    }
    throw new JSONException("scan true error");
  }

  public final void scanNumber()
  {
    this.np = this.bp;
    if (this.ch == '-')
    {
      this.sp = (1 + this.sp);
      next();
    }
    while ((this.ch >= '0') && (this.ch <= '9'))
    {
      this.sp = (1 + this.sp);
      next();
    }
    int i = this.ch;
    int j = 0;
    if (i == 46)
    {
      this.sp = (1 + this.sp);
      next();
      j = 1;
      while ((this.ch >= '0') && (this.ch <= '9'))
      {
        this.sp = (1 + this.sp);
        next();
      }
    }
    if (this.ch == 'L')
    {
      this.sp = (1 + this.sp);
      next();
    }
    while (j != 0)
    {
      this.token = 3;
      return;
      if (this.ch == 'S')
      {
        this.sp = (1 + this.sp);
        next();
      }
      else if (this.ch == 'B')
      {
        this.sp = (1 + this.sp);
        next();
      }
      else if (this.ch == 'F')
      {
        this.sp = (1 + this.sp);
        next();
        j = 1;
      }
      else if (this.ch == 'D')
      {
        this.sp = (1 + this.sp);
        next();
        j = 1;
      }
      else if ((this.ch == 'e') || (this.ch == 'E'))
      {
        this.sp = (1 + this.sp);
        next();
        if ((this.ch == '+') || (this.ch == '-'))
        {
          this.sp = (1 + this.sp);
          next();
        }
        while ((this.ch >= '0') && (this.ch <= '9'))
        {
          this.sp = (1 + this.sp);
          next();
        }
        if ((this.ch == 'D') || (this.ch == 'F'))
        {
          this.sp = (1 + this.sp);
          next();
        }
        j = 1;
      }
    }
    this.token = 2;
  }

  public final void scanSet()
  {
    if (this.ch != 'S')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 'e')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 't')
      throw new JSONException("error parse true");
    next();
    if ((this.ch == ' ') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\f') || (this.ch == '\b') || (this.ch == '[') || (this.ch == '('))
    {
      this.token = 21;
      return;
    }
    throw new JSONException("scan set error");
  }

  public String scanString(char paramChar)
  {
    this.matchStat = 0;
    int i = this.bp;
    int j = 0 + 1;
    int k = charAt(i + 0);
    if (k == 110)
    {
      if ((charAt(1 + this.bp) == 'u') && (charAt(1 + (1 + this.bp)) == 'l') && (charAt(2 + (1 + this.bp)) == 'l'))
      {
        int i6 = j + 3;
        int i7 = this.bp;
        (i6 + 1);
        if (charAt(i7 + 4) == paramChar)
        {
          this.bp = (4 + this.bp);
          next();
          this.matchStat = 3;
          return null;
        }
      }
      else
      {
        this.matchStat = -1;
        return null;
      }
      this.matchStat = -1;
      return null;
    }
    if (k != 34)
    {
      this.matchStat = -1;
      return stringDefaultValue();
    }
    int m = 1 + this.bp;
    int n = indexOf('"', m);
    if (n == -1)
      throw new JSONException("unclosed str");
    String str = subString(1 + this.bp, n - m);
    for (int i1 = 1 + this.bp; ; i1++)
    {
      int i2 = 0;
      if (i1 < n)
      {
        if (charAt(i1) == '\\')
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
    int i3 = 1 + (1 + (n - (1 + this.bp)));
    int i4 = this.bp;
    int i5 = i3 + 1;
    if (charAt(i4 + i3) == paramChar)
    {
      this.bp += i5 - 1;
      next();
      this.matchStat = 3;
      return str;
    }
    this.matchStat = -1;
    return str;
  }

  public final void scanString()
  {
    this.np = this.bp;
    this.hasSpecial = false;
    while (true)
    {
      char c1 = next();
      if (c1 == '"')
      {
        this.token = 4;
        this.ch = next();
        return;
      }
      if (c1 == '\032')
        throw new JSONException("unclosed string : " + c1);
      if (c1 == '\\')
      {
        if (!this.hasSpecial)
        {
          this.hasSpecial = true;
          if (this.sp >= this.sbuf.length)
          {
            int m = 2 * this.sbuf.length;
            if (this.sp > m)
              m = this.sp;
            char[] arrayOfChar2 = new char[m];
            System.arraycopy(this.sbuf, 0, arrayOfChar2, 0, this.sbuf.length);
            this.sbuf = arrayOfChar2;
          }
          copyTo(1 + this.np, this.sp, this.sbuf);
        }
        char c2 = next();
        switch (c2)
        {
        default:
          this.ch = c2;
          throw new JSONException("unclosed string : " + c2);
        case '0':
          putChar('\000');
          break;
        case '1':
          putChar('\001');
          break;
        case '2':
          putChar('\002');
          break;
        case '3':
          putChar('\003');
          break;
        case '4':
          putChar('\004');
          break;
        case '5':
          putChar('\005');
          break;
        case '6':
          putChar('\006');
          break;
        case '7':
          putChar('\007');
          break;
        case 'b':
          putChar('\b');
          break;
        case 't':
          putChar('\t');
          break;
        case 'n':
          putChar('\n');
          break;
        case 'v':
          putChar('\013');
          break;
        case 'F':
        case 'f':
          putChar('\f');
          break;
        case 'r':
          putChar('\r');
          break;
        case '"':
          putChar('"');
          break;
        case '\'':
          putChar('\'');
          break;
        case '/':
          putChar('/');
          break;
        case '\\':
          putChar('\\');
          break;
        case 'x':
          int j = next();
          int k = next();
          putChar((char)(16 * digits[j] + digits[k]));
          break;
        case 'u':
          putChar((char)Integer.parseInt(new String(new char[] { next(), next(), next(), next() }), 16));
          break;
        }
      }
      else if (!this.hasSpecial)
      {
        this.sp = (1 + this.sp);
      }
      else if (this.sp == this.sbuf.length)
      {
        putChar(c1);
      }
      else
      {
        char[] arrayOfChar1 = this.sbuf;
        int i = this.sp;
        this.sp = (i + 1);
        arrayOfChar1[i] = c1;
      }
    }
  }

  public Collection<String> scanStringArray(Class<?> paramClass, char paramChar)
  {
    this.matchStat = 0;
    Object localObject;
    if (paramClass.isAssignableFrom(HashSet.class))
      localObject = new HashSet();
    int j;
    int k;
    while (true)
    {
      int i = this.bp;
      j = 0 + 1;
      k = charAt(i + 0);
      if (k != 110)
        break label214;
      if ((charAt(1 + this.bp) == 'u') && (charAt(1 + (1 + this.bp)) == 'l') && (charAt(2 + (1 + this.bp)) == 'l'))
      {
        int i16 = j + 3;
        int i17 = this.bp;
        (i16 + 1);
        if (charAt(i17 + 4) != paramChar)
          break label207;
        this.bp = (4 + this.bp);
        next();
        this.matchStat = 3;
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
    this.matchStat = -1;
    return null;
    label207: this.matchStat = -1;
    return null;
    label214: if (k != 91)
    {
      this.matchStat = -1;
      return null;
    }
    int m = this.bp;
    int n = j + 1;
    int i10;
    int i9;
    int i13;
    for (int i1 = charAt(m + 1); (i1 == 110) && (charAt(n + this.bp) == 'u') && (charAt(1 + (n + this.bp)) == 'l') && (charAt(2 + (n + this.bp)) == 'l'); i1 = charAt(i13 + i10))
    {
      int i14 = n + 3;
      int i15 = this.bp;
      i10 = i14 + 1;
      i9 = charAt(i15 + i14);
      if (i9 != 44)
        break label497;
      i13 = this.bp;
      n = i10 + 1;
    }
    if (i1 != 34)
    {
      this.matchStat = -1;
      return null;
    }
    int i2 = n;
    while (true)
    {
      int i3 = this.bp;
      int i4 = n + 1;
      int i5 = charAt(i3 + n);
      if (i5 == 34)
      {
        int i6 = i2 + this.bp;
        ((Collection)localObject).add(subString(i6, -1 + (i4 + this.bp - i6)));
        int i7 = this.bp;
        int i8 = i4 + 1;
        i9 = charAt(i7 + i4);
        i10 = i8;
        break;
      }
      if (i5 == 92)
      {
        this.matchStat = -1;
        return null;
        label497: if (i9 == 93)
        {
          int i11 = this.bp;
          int i12 = i10 + 1;
          if (charAt(i11 + i10) == paramChar)
          {
            this.bp += i12 - 1;
            next();
            this.matchStat = 3;
            return localObject;
          }
        }
        else
        {
          this.matchStat = -1;
          return null;
        }
        this.matchStat = -1;
        return localObject;
      }
      n = i4;
    }
  }

  public final String scanSymbol(SymbolTable paramSymbolTable)
  {
    skipWhitespace();
    if (this.ch == '"')
      return scanSymbol(paramSymbolTable, '"');
    if (this.ch == '\'')
    {
      if (!isEnabled(Feature.AllowSingleQuotes))
        throw new JSONException("syntax error");
      return scanSymbol(paramSymbolTable, '\'');
    }
    if (this.ch == '}')
    {
      next();
      this.token = 13;
      return null;
    }
    if (this.ch == ',')
    {
      next();
      this.token = 16;
      return null;
    }
    if (this.ch == '\032')
    {
      this.token = 20;
      return null;
    }
    if (!isEnabled(Feature.AllowUnQuotedFieldNames))
      throw new JSONException("syntax error");
    return scanSymbolUnQuoted(paramSymbolTable);
  }

  public final String scanSymbol(SymbolTable paramSymbolTable, char paramChar)
  {
    int i = 0;
    this.np = this.bp;
    this.sp = 0;
    int j = 0;
    char c1 = next();
    int i3;
    if (c1 == paramChar)
    {
      this.token = 4;
      if (j != 0)
        break label908;
      if (this.np != -1)
        break label897;
      i3 = 0;
    }
    label51: for (String str = addSymbol(i3, this.sp, i, paramSymbolTable); ; str = paramSymbolTable.addSymbol(this.sbuf, 0, this.sp, i))
    {
      this.sp = 0;
      next();
      return str;
      if (c1 == '\032')
        throw new JSONException("unclosed.str");
      if (c1 == '\\')
      {
        if (j == 0)
        {
          j = 1;
          if (this.sp >= this.sbuf.length)
          {
            int i2 = 2 * this.sbuf.length;
            if (this.sp > i2)
              i2 = this.sp;
            char[] arrayOfChar2 = new char[i2];
            System.arraycopy(this.sbuf, 0, arrayOfChar2, 0, this.sbuf.length);
            this.sbuf = arrayOfChar2;
          }
          arrayCopy(1 + this.np, this.sbuf, 0, this.sp);
        }
        int m = next();
        switch (m)
        {
        default:
          this.ch = m;
          throw new JSONException("unclosed.str.lit");
        case 48:
          i = m + i * 31;
          putChar('\000');
          break;
        case 49:
          i = m + i * 31;
          putChar('\001');
          break;
        case 50:
          i = m + i * 31;
          putChar('\002');
          break;
        case 51:
          i = m + i * 31;
          putChar('\003');
          break;
        case 52:
          i = m + i * 31;
          putChar('\004');
          break;
        case 53:
          i = m + i * 31;
          putChar('\005');
          break;
        case 54:
          i = m + i * 31;
          putChar('\006');
          break;
        case 55:
          i = m + i * 31;
          putChar('\007');
          break;
        case 98:
          i = 8 + i * 31;
          putChar('\b');
          break;
        case 116:
          i = 9 + i * 31;
          putChar('\t');
          break;
        case 110:
          i = 10 + i * 31;
          putChar('\n');
          break;
        case 118:
          i = 11 + i * 31;
          putChar('\013');
          break;
        case 70:
        case 102:
          i = 12 + i * 31;
          putChar('\f');
          break;
        case 114:
          i = 13 + i * 31;
          putChar('\r');
          break;
        case 34:
          i = 34 + i * 31;
          putChar('"');
          break;
        case 39:
          i = 39 + i * 31;
          putChar('\'');
          break;
        case 47:
          i = 47 + i * 31;
          putChar('/');
          break;
        case 92:
          i = 92 + i * 31;
          putChar('\\');
          break;
        case 120:
          char c2 = next();
          this.ch = c2;
          char c3 = next();
          this.ch = c3;
          int i1 = (char)(16 * digits[c2] + digits[c3]);
          i = i1 + i * 31;
          putChar(i1);
          break;
        case 117:
          int n = Integer.parseInt(new String(new char[] { next(), next(), next(), next() }), 16);
          i = n + i * 31;
          putChar((char)n);
          break;
        }
      }
      i = c1 + i * 31;
      if (j == 0)
      {
        this.sp = (1 + this.sp);
        break;
      }
      if (this.sp == this.sbuf.length)
      {
        putChar(c1);
        break;
      }
      char[] arrayOfChar1 = this.sbuf;
      int k = this.sp;
      this.sp = (k + 1);
      arrayOfChar1[k] = c1;
      break;
      i3 = 1 + this.np;
      break label51;
    }
  }

  public final String scanSymbolUnQuoted(SymbolTable paramSymbolTable)
  {
    boolean[] arrayOfBoolean1 = CharTypes.firstIdentifierFlags;
    int i = this.ch;
    if ((this.ch >= arrayOfBoolean1.length) || (arrayOfBoolean1[i] != 0));
    for (int j = 1; j == 0; j = 0)
      throw new JSONException("illegal identifier : " + this.ch);
    boolean[] arrayOfBoolean2 = CharTypes.identifierFlags;
    int k = i;
    this.np = this.bp;
    for (this.sp = 1; ; this.sp = (1 + this.sp))
    {
      int m = next();
      if ((m < arrayOfBoolean2.length) && (arrayOfBoolean2[m] == 0))
      {
        this.ch = charAt(this.bp);
        this.token = 18;
        if ((this.sp != 4) || (k != 3392903) || (charAt(this.np) != 'n') || (charAt(1 + this.np) != 'u') || (charAt(2 + this.np) != 'l') || (charAt(3 + this.np) != 'l'))
          break;
        return null;
      }
      k = m + k * 31;
    }
    return addSymbol(this.np, this.sp, k, paramSymbolTable);
  }

  public String scanSymbolWithSeperator(SymbolTable paramSymbolTable, char paramChar)
  {
    this.matchStat = 0;
    int i = this.bp;
    int j = 0 + 1;
    int k = charAt(i + 0);
    if (k == 110)
    {
      if ((charAt(1 + this.bp) == 'u') && (charAt(1 + (1 + this.bp)) == 'l') && (charAt(2 + (1 + this.bp)) == 'l'))
      {
        int i7 = j + 3;
        int i8 = this.bp;
        (i7 + 1);
        if (charAt(i8 + 4) == paramChar)
        {
          this.bp = (4 + this.bp);
          next();
          this.matchStat = 3;
          return null;
        }
      }
      else
      {
        this.matchStat = -1;
        return null;
      }
      this.matchStat = -1;
      return null;
    }
    if (k != 34)
    {
      this.matchStat = -1;
      return null;
    }
    int m = 0;
    int i2;
    for (int n = j; ; n = i2)
    {
      int i1 = this.bp;
      i2 = n + 1;
      int i3 = charAt(i1 + n);
      String str;
      if (i3 == 34)
      {
        int i4 = 1 + (0 + this.bp);
        str = addSymbol(i4, -1 + (i2 + this.bp - i4), m, paramSymbolTable);
        int i5 = this.bp;
        int i6 = i2 + 1;
        if (charAt(i5 + i2) == paramChar)
        {
          this.bp += i6 - 1;
          next();
          this.matchStat = 3;
          return str;
        }
      }
      else
      {
        m = i3 + m * 31;
        if (i3 != 92)
          continue;
        this.matchStat = -1;
        return null;
      }
      this.matchStat = -1;
      return str;
    }
  }

  public final void scanTreeSet()
  {
    if (this.ch != 'T')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 'r')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 'e')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 'e')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 'S')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 'e')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 't')
      throw new JSONException("error parse true");
    next();
    if ((this.ch == ' ') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\f') || (this.ch == '\b') || (this.ch == '[') || (this.ch == '('))
    {
      this.token = 22;
      return;
    }
    throw new JSONException("scan set error");
  }

  public final void scanTrue()
  {
    if (this.ch != 't')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 'r')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 'u')
      throw new JSONException("error parse true");
    next();
    if (this.ch != 'e')
      throw new JSONException("error parse true");
    next();
    if ((this.ch == ' ') || (this.ch == ',') || (this.ch == '}') || (this.ch == ']') || (this.ch == '\n') || (this.ch == '\r') || (this.ch == '\t') || (this.ch == '\032') || (this.ch == '\f') || (this.ch == '\b'))
    {
      this.token = 6;
      return;
    }
    throw new JSONException("scan true error");
  }

  public int scanType(String paramString)
  {
    int i = -1;
    this.matchStat = 0;
    if (!charArrayCompare(typeFieldName))
      i = -2;
    label71: int n;
    do
    {
      return i;
      int j = this.bp + typeFieldName.length;
      int k = paramString.length();
      for (int m = 0; ; m++)
      {
        if (m >= k)
          break label71;
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
        break label209;
      this.token = 16;
      i1++;
      this.ch = charAt(i1);
    }
    while (true)
    {
      this.matchStat = 4;
      this.bp = i1;
      return this.matchStat;
      label209: if (this.ch == ']')
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

  public final void skipWhitespace()
  {
    while (whitespaceFlags[this.ch] != 0)
      next();
  }

  public final String stringDefaultValue()
  {
    if (isEnabled(Feature.InitStringFieldAsEmpty))
      return "";
    return null;
  }

  public abstract String stringVal();

  public abstract String subString(int paramInt1, int paramInt2);

  public final int token()
  {
    return this.token;
  }

  public final String tokenName()
  {
    return JSONToken.name(this.token);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.JSONLexerBase
 * JD-Core Version:    0.6.2
 */