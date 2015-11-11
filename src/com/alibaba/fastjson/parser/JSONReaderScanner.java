package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.Base64;
import com.alibaba.fastjson.util.IOUtils;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.ref.SoftReference;

public final class JSONReaderScanner extends JSONLexerBase
{
  public static final int BUF_INIT_LEN = 8192;
  private static final ThreadLocal<SoftReference<char[]>> BUF_REF_LOCAL = new ThreadLocal();
  private char[] buf;
  private int bufLength;
  private Reader reader;

  public JSONReaderScanner(Reader paramReader)
  {
    this(paramReader, JSON.DEFAULT_PARSER_FEATURE);
  }

  public JSONReaderScanner(Reader paramReader, int paramInt)
  {
    this.reader = paramReader;
    this.features = paramInt;
    SoftReference localSoftReference = (SoftReference)BUF_REF_LOCAL.get();
    if (localSoftReference != null)
    {
      this.buf = ((char[])localSoftReference.get());
      BUF_REF_LOCAL.set(null);
    }
    if (this.buf == null)
      this.buf = new char[8192];
    try
    {
      this.bufLength = paramReader.read(this.buf);
      this.bp = -1;
      next();
      if (this.ch == 65279)
        next();
      return;
    }
    catch (IOException localIOException)
    {
      throw new JSONException(localIOException.getMessage(), localIOException);
    }
  }

  public JSONReaderScanner(String paramString)
  {
    this(paramString, JSON.DEFAULT_PARSER_FEATURE);
  }

  public JSONReaderScanner(String paramString, int paramInt)
  {
    this(new StringReader(paramString), paramInt);
  }

  public JSONReaderScanner(char[] paramArrayOfChar, int paramInt)
  {
    this(paramArrayOfChar, paramInt, JSON.DEFAULT_PARSER_FEATURE);
  }

  public JSONReaderScanner(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    this(new CharArrayReader(paramArrayOfChar, 0, paramInt1), paramInt2);
  }

  public final String addSymbol(int paramInt1, int paramInt2, int paramInt3, SymbolTable paramSymbolTable)
  {
    return paramSymbolTable.addSymbol(this.buf, paramInt1, paramInt2, paramInt3);
  }

  protected final void arrayCopy(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
  {
    System.arraycopy(this.buf, paramInt1, paramArrayOfChar, paramInt2, paramInt3);
  }

  public byte[] bytesValue()
  {
    return Base64.decodeFast(this.buf, 1 + this.np, this.sp);
  }

  public final boolean charArrayCompare(char[] paramArrayOfChar)
  {
    for (int i = 0; i < paramArrayOfChar.length; i++)
      if (charAt(i + this.bp) != paramArrayOfChar[i])
        return false;
    return true;
  }

  public final char charAt(int paramInt)
  {
    char c = '\032';
    if (paramInt >= this.bufLength)
    {
      if (this.bufLength == -1)
        if (paramInt < this.sp)
          c = this.buf[paramInt];
      int i;
      do
      {
        return c;
        i = this.bufLength - this.bp;
        if (i > 0)
          System.arraycopy(this.buf, this.bp, this.buf, 0, i);
        try
        {
          this.bufLength = this.reader.read(this.buf, i, this.buf.length - i);
          if (this.bufLength == 0)
            throw new JSONException("illegal stat, textLength is zero");
        }
        catch (IOException localIOException)
        {
          throw new JSONException(localIOException.getMessage(), localIOException);
        }
      }
      while (this.bufLength == -1);
      this.bufLength = (i + this.bufLength);
      paramInt -= this.bp;
      this.np -= this.bp;
      this.bp = 0;
    }
    return this.buf[paramInt];
  }

  public void close()
  {
    super.close();
    BUF_REF_LOCAL.set(new SoftReference(this.buf));
    this.buf = null;
    IOUtils.close(this.reader);
  }

  protected final void copyTo(int paramInt1, int paramInt2, char[] paramArrayOfChar)
  {
    System.arraycopy(this.buf, paramInt1, paramArrayOfChar, 0, paramInt2);
  }

  public final int indexOf(char paramChar, int paramInt)
  {
    for (int i = paramInt - this.bp; ; i++)
    {
      if (paramChar == charAt(i + this.bp))
        return i + this.bp;
      if (paramChar == '\032')
        return -1;
    }
  }

  public boolean isEOF()
  {
    return (this.bufLength == -1) || (this.bp == this.buf.length) || ((this.ch == '\032') && (1 + this.bp == this.buf.length));
  }

  public final char next()
  {
    int i = 1 + this.bp;
    this.bp = i;
    if (i >= this.bufLength)
    {
      if (this.bufLength == -1)
        return '\032';
      if (this.sp > 0)
      {
        int m = this.bufLength - this.sp;
        if (this.ch == '"')
          m--;
        System.arraycopy(this.buf, m, this.buf, 0, this.sp);
      }
      this.np = -1;
      i = this.sp;
      this.bp = i;
      try
      {
        int j = this.bp;
        int k = this.buf.length - j;
        this.bufLength = this.reader.read(this.buf, this.bp, k);
        if (this.bufLength == 0)
          throw new JSONException("illegal stat, textLength is zero");
      }
      catch (IOException localIOException)
      {
        throw new JSONException(localIOException.getMessage(), localIOException);
      }
      if (this.bufLength == -1)
      {
        this.ch = '\032';
        return '\032';
      }
      this.bufLength += this.bp;
    }
    char c = this.buf[i];
    this.ch = c;
    return c;
  }

  public final String numberString()
  {
    int i = this.np;
    if (i == -1)
      i = 0;
    int j = charAt(-1 + (i + this.sp));
    int k = this.sp;
    if ((j == 76) || (j == 83) || (j == 66) || (j == 70) || (j == 68))
      k--;
    return new String(this.buf, i, k);
  }

  public final String stringVal()
  {
    if (!this.hasSpecial)
    {
      int i = 1 + this.np;
      if (i < 0)
        throw new IllegalStateException();
      if (i > this.buf.length - this.sp)
        throw new IllegalStateException();
      return new String(this.buf, i, this.sp);
    }
    return new String(this.sbuf, 0, this.sp);
  }

  public final String subString(int paramInt1, int paramInt2)
  {
    if (paramInt2 < 0)
      throw new StringIndexOutOfBoundsException(paramInt2);
    return new String(this.buf, paramInt1, paramInt2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.JSONReaderScanner
 * JD-Core Version:    0.6.2
 */