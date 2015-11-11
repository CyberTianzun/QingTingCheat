package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.CharTypes;
import com.alibaba.fastjson.util.Base64;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.nio.charset.Charset;

public final class SerializeWriter extends Writer
{
  private static final ThreadLocal<SoftReference<char[]>> bufLocal = new ThreadLocal();
  protected char[] buf;
  protected int count;
  private int features;
  private final Writer writer;

  public SerializeWriter()
  {
    this((Writer)null);
  }

  public SerializeWriter(int paramInt)
  {
    this(null, paramInt);
  }

  public SerializeWriter(Writer paramWriter)
  {
    this.writer = paramWriter;
    this.features = JSON.DEFAULT_GENERATE_FEATURE;
    SoftReference localSoftReference = (SoftReference)bufLocal.get();
    if (localSoftReference != null)
    {
      this.buf = ((char[])localSoftReference.get());
      bufLocal.set(null);
    }
    if (this.buf == null)
      this.buf = new char[1024];
  }

  public SerializeWriter(Writer paramWriter, int paramInt)
  {
    this.writer = paramWriter;
    if (paramInt <= 0)
      throw new IllegalArgumentException("Negative initial size: " + paramInt);
    this.buf = new char[paramInt];
  }

  public SerializeWriter(Writer paramWriter, SerializerFeature[] paramArrayOfSerializerFeature)
  {
    this.writer = paramWriter;
    SoftReference localSoftReference = (SoftReference)bufLocal.get();
    if (localSoftReference != null)
    {
      this.buf = ((char[])localSoftReference.get());
      bufLocal.set(null);
    }
    if (this.buf == null)
      this.buf = new char[1024];
    int i = 0;
    int j = paramArrayOfSerializerFeature.length;
    for (int k = 0; k < j; k++)
      i |= paramArrayOfSerializerFeature[k].getMask();
    this.features = i;
  }

  public SerializeWriter(SerializerFeature[] paramArrayOfSerializerFeature)
  {
    this(null, paramArrayOfSerializerFeature);
  }

  static final boolean isSpecial(char paramChar, int paramInt)
  {
    if (paramChar == ' ');
    do
    {
      return false;
      if ((paramChar == '/') && (SerializerFeature.isEnabled(paramInt, SerializerFeature.WriteSlashAsSpecial)))
        return true;
    }
    while (((paramChar > '#') && (paramChar != '\\')) || ((paramChar > '\r') && (paramChar != '\\') && (paramChar != '"')));
    return true;
  }

  private void writeFieldValueStringWithDoubleQuote(char paramChar, String paramString1, String paramString2, boolean paramBoolean)
  {
    int i = paramString1.length();
    int j = this.count;
    int k;
    if (paramString2 == null)
      k = 4;
    for (int m = j + (i + 8); ; m = j + (6 + (i + k)))
    {
      int n = this.buf.length;
      if (m <= n)
        break label101;
      if (this.writer == null)
        break;
      write(paramChar);
      writeStringWithDoubleQuote(paramString1, ':', paramBoolean);
      writeStringWithDoubleQuote(paramString2, '\000', paramBoolean);
      return;
      k = paramString2.length();
    }
    expandCapacity(m);
    label101: this.buf[this.count] = paramChar;
    int i1 = 2 + this.count;
    int i2 = i1 + i;
    this.buf[(1 + this.count)] = '"';
    paramString1.getChars(0, i, this.buf, i1);
    this.count = m;
    this.buf[i2] = '"';
    int i3 = i2 + 1;
    char[] arrayOfChar1 = this.buf;
    int i4 = i3 + 1;
    arrayOfChar1[i3] = ':';
    if (paramString2 == null)
    {
      char[] arrayOfChar11 = this.buf;
      int i30 = i4 + 1;
      arrayOfChar11[i4] = 'n';
      char[] arrayOfChar12 = this.buf;
      int i31 = i30 + 1;
      arrayOfChar12[i30] = 'u';
      char[] arrayOfChar13 = this.buf;
      int i32 = i31 + 1;
      arrayOfChar13[i31] = 'l';
      char[] arrayOfChar14 = this.buf;
      (i32 + 1);
      arrayOfChar14[i32] = 'l';
      return;
    }
    char[] arrayOfChar2 = this.buf;
    int i5 = i4 + 1;
    arrayOfChar2[i4] = '"';
    int i6 = i5 + k;
    char[] arrayOfChar3 = this.buf;
    paramString2.getChars(0, k, arrayOfChar3, i5);
    int i7;
    int i8;
    int i9;
    int i10;
    if ((paramBoolean) && (!isEnabled(SerializerFeature.DisableCheckSpecialChar)))
    {
      i7 = 0;
      i8 = -1;
      i9 = -1;
      i10 = 0;
      int i11 = i5;
      if (i11 < i6)
      {
        int i29 = this.buf[i11];
        if (i29 == 8232)
        {
          i7++;
          i8 = i11;
          i10 = i29;
          m += 4;
          if (i9 == -1)
            i9 = i11;
        }
        if (i29 >= 93);
        while (true)
        {
          i11++;
          break;
          if (isSpecial(i29, this.features))
          {
            i7++;
            i8 = i11;
            i10 = i29;
            if (i9 == -1)
              i9 = i11;
          }
        }
      }
      if (i7 > 0)
      {
        int i12 = m + i7;
        if (i12 > this.buf.length)
          expandCapacity(i12);
        this.count = i12;
        if (i7 != 1)
          break label705;
        if (i10 != 8232)
          break label640;
        i22 = i8 + 1;
        i23 = i8 + 6;
        i24 = -1 + (i6 - i8);
        System.arraycopy(this.buf, i22, this.buf, i23, i24);
        this.buf[i8] = '\\';
        arrayOfChar7 = this.buf;
        i25 = i8 + 1;
        arrayOfChar7[i25] = 'u';
        arrayOfChar8 = this.buf;
        i26 = i25 + 1;
        arrayOfChar8[i26] = '2';
        arrayOfChar9 = this.buf;
        i27 = i26 + 1;
        arrayOfChar9[i27] = '0';
        arrayOfChar10 = this.buf;
        i28 = i27 + 1;
        arrayOfChar10[i28] = '2';
        this.buf[(i28 + 1)] = '8';
      }
    }
    label640: label705: 
    while (i7 <= 1)
      while (true)
      {
        int i22;
        int i23;
        int i24;
        char[] arrayOfChar7;
        int i25;
        char[] arrayOfChar8;
        int i26;
        char[] arrayOfChar9;
        int i27;
        char[] arrayOfChar10;
        int i28;
        this.buf[(-1 + this.count)] = '"';
        return;
        int i19 = i8 + 1;
        int i20 = i8 + 2;
        int i21 = -1 + (i6 - i8);
        System.arraycopy(this.buf, i19, this.buf, i20, i21);
        this.buf[i8] = '\\';
        this.buf[(i8 + 1)] = CharTypes.replaceChars[i10];
      }
    int i13 = i9 - i5;
    int i14 = i9;
    int i15 = i13;
    label726: int i16;
    if (i15 < paramString2.length())
    {
      i16 = paramString2.charAt(i15);
      if (((i16 >= CharTypes.specicalFlags_doubleQuotes.length) || (CharTypes.specicalFlags_doubleQuotes[i16] == 0)) && ((i16 != 47) || (!isEnabled(SerializerFeature.WriteSlashAsSpecial))))
        break label829;
      char[] arrayOfChar5 = this.buf;
      int i18 = i14 + 1;
      arrayOfChar5[i14] = '\\';
      char[] arrayOfChar6 = this.buf;
      i14 = i18 + 1;
      arrayOfChar6[i18] = CharTypes.replaceChars[i16];
      i6++;
    }
    while (true)
    {
      i15++;
      break label726;
      break;
      label829: char[] arrayOfChar4 = this.buf;
      int i17 = i14 + 1;
      arrayOfChar4[i14] = i16;
      i14 = i17;
    }
  }

  private void writeKeyWithDoubleQuoteIfHasSpecial(String paramString)
  {
    boolean[] arrayOfBoolean = CharTypes.specicalFlags_doubleQuotes;
    int i = paramString.length();
    int j = 1 + (i + this.count);
    if (j > this.buf.length)
    {
      if (this.writer != null)
      {
        if (i == 0)
        {
          write('"');
          write('"');
          write(':');
          return;
        }
        int i7 = 0;
        int i8 = 0;
        int i9;
        label110: int i10;
        if (i7 < i)
        {
          int i11 = paramString.charAt(i7);
          if ((i11 < arrayOfBoolean.length) && (arrayOfBoolean[i11] != 0))
            i8 = 1;
        }
        else
        {
          if (i8 != 0)
            write('"');
          i9 = 0;
          if (i9 >= i)
            break label175;
          i10 = paramString.charAt(i9);
          if ((i10 >= arrayOfBoolean.length) || (arrayOfBoolean[i10] == 0))
            break label166;
          write('\\');
          write(CharTypes.replaceChars[i10]);
        }
        while (true)
        {
          i9++;
          break label110;
          i7++;
          break;
          label166: write(i10);
        }
        label175: if (i8 != 0)
          write('"');
        write(':');
        return;
      }
      expandCapacity(j);
    }
    if (i == 0)
    {
      if (3 + this.count > this.buf.length)
        expandCapacity(3 + this.count);
      char[] arrayOfChar4 = this.buf;
      int i4 = this.count;
      this.count = (i4 + 1);
      arrayOfChar4[i4] = '"';
      char[] arrayOfChar5 = this.buf;
      int i5 = this.count;
      this.count = (i5 + 1);
      arrayOfChar5[i5] = '"';
      char[] arrayOfChar6 = this.buf;
      int i6 = this.count;
      this.count = (i6 + 1);
      arrayOfChar6[i6] = ':';
      return;
    }
    int k = this.count;
    int m = k + i;
    paramString.getChars(0, i, this.buf, k);
    this.count = j;
    int n = 0;
    int i1 = k;
    if (i1 < m)
    {
      int i2 = this.buf[i1];
      if ((i2 < arrayOfBoolean.length) && (arrayOfBoolean[i2] != 0))
      {
        if (n != 0)
          break label524;
        j += 3;
        if (j > this.buf.length)
          expandCapacity(j);
        this.count = j;
        System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 3, -1 + (m - i1));
        System.arraycopy(this.buf, 0, this.buf, 1, i1);
        this.buf[k] = '"';
        char[] arrayOfChar2 = this.buf;
        int i3 = i1 + 1;
        arrayOfChar2[i3] = '\\';
        char[] arrayOfChar3 = this.buf;
        i1 = i3 + 1;
        arrayOfChar3[i1] = CharTypes.replaceChars[i2];
        m += 2;
        this.buf[(-2 + this.count)] = '"';
        n = 1;
      }
      while (true)
      {
        i1++;
        break;
        label524: j++;
        if (j > this.buf.length)
          expandCapacity(j);
        this.count = j;
        System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 2, m - i1);
        this.buf[i1] = '\\';
        char[] arrayOfChar1 = this.buf;
        i1++;
        arrayOfChar1[i1] = CharTypes.replaceChars[i2];
        m++;
      }
    }
    this.buf[(-1 + this.count)] = ':';
  }

  private void writeKeyWithSingleQuoteIfHasSpecial(String paramString)
  {
    boolean[] arrayOfBoolean = CharTypes.specicalFlags_singleQuotes;
    int i = paramString.length();
    int j = 1 + (i + this.count);
    if (j > this.buf.length)
    {
      if (this.writer != null)
      {
        if (i == 0)
        {
          write('\'');
          write('\'');
          write(':');
          return;
        }
        int i7 = 0;
        int i8 = 0;
        int i9;
        label110: int i10;
        if (i7 < i)
        {
          int i11 = paramString.charAt(i7);
          if ((i11 < arrayOfBoolean.length) && (arrayOfBoolean[i11] != 0))
            i8 = 1;
        }
        else
        {
          if (i8 != 0)
            write('\'');
          i9 = 0;
          if (i9 >= i)
            break label175;
          i10 = paramString.charAt(i9);
          if ((i10 >= arrayOfBoolean.length) || (arrayOfBoolean[i10] == 0))
            break label166;
          write('\\');
          write(CharTypes.replaceChars[i10]);
        }
        while (true)
        {
          i9++;
          break label110;
          i7++;
          break;
          label166: write(i10);
        }
        label175: if (i8 != 0)
          write('\'');
        write(':');
        return;
      }
      expandCapacity(j);
    }
    if (i == 0)
    {
      if (3 + this.count > this.buf.length)
        expandCapacity(3 + this.count);
      char[] arrayOfChar4 = this.buf;
      int i4 = this.count;
      this.count = (i4 + 1);
      arrayOfChar4[i4] = '\'';
      char[] arrayOfChar5 = this.buf;
      int i5 = this.count;
      this.count = (i5 + 1);
      arrayOfChar5[i5] = '\'';
      char[] arrayOfChar6 = this.buf;
      int i6 = this.count;
      this.count = (i6 + 1);
      arrayOfChar6[i6] = ':';
      return;
    }
    int k = this.count;
    int m = k + i;
    paramString.getChars(0, i, this.buf, k);
    this.count = j;
    int n = 0;
    int i1 = k;
    if (i1 < m)
    {
      int i2 = this.buf[i1];
      if ((i2 < arrayOfBoolean.length) && (arrayOfBoolean[i2] != 0))
      {
        if (n != 0)
          break label524;
        j += 3;
        if (j > this.buf.length)
          expandCapacity(j);
        this.count = j;
        System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 3, -1 + (m - i1));
        System.arraycopy(this.buf, 0, this.buf, 1, i1);
        this.buf[k] = '\'';
        char[] arrayOfChar2 = this.buf;
        int i3 = i1 + 1;
        arrayOfChar2[i3] = '\\';
        char[] arrayOfChar3 = this.buf;
        i1 = i3 + 1;
        arrayOfChar3[i1] = CharTypes.replaceChars[i2];
        m += 2;
        this.buf[(-2 + this.count)] = '\'';
        n = 1;
      }
      while (true)
      {
        i1++;
        break;
        label524: j++;
        if (j > this.buf.length)
          expandCapacity(j);
        this.count = j;
        System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 2, m - i1);
        this.buf[i1] = '\\';
        char[] arrayOfChar1 = this.buf;
        i1++;
        arrayOfChar1[i1] = CharTypes.replaceChars[i2];
        m++;
      }
    }
    this.buf[(j - 1)] = ':';
  }

  private void writeStringWithDoubleQuote(String paramString, char paramChar)
  {
    writeStringWithDoubleQuote(paramString, paramChar, true);
  }

  private void writeStringWithDoubleQuote(String paramString, char paramChar, boolean paramBoolean)
  {
    if (paramString == null)
    {
      writeNull();
      if (paramChar != 0)
        write(paramChar);
    }
    int i;
    int j;
    do
    {
      return;
      i = paramString.length();
      j = 2 + (i + this.count);
      if (paramChar != 0)
        j++;
      if (j <= this.buf.length)
        break label400;
      if (this.writer == null)
        break;
      write('"');
      int i28 = 0;
      if (i28 < paramString.length())
      {
        int i29 = paramString.charAt(i28);
        if (isEnabled(SerializerFeature.BrowserCompatible))
          if ((i29 == 8) || (i29 == 12) || (i29 == 10) || (i29 == 13) || (i29 == 9) || (i29 == 34) || (i29 == 47) || (i29 == 92))
          {
            write('\\');
            write(CharTypes.replaceChars[i29]);
          }
        while (true)
        {
          i28++;
          break;
          if (i29 < 32)
          {
            write('\\');
            write('u');
            write('0');
            write('0');
            write(CharTypes.ASCII_CHARS[(i29 * 2)]);
            write(CharTypes.ASCII_CHARS[(1 + i29 * 2)]);
          }
          else if (i29 >= 127)
          {
            write('\\');
            write('u');
            write(CharTypes.digits[(0xF & i29 >>> 12)]);
            write(CharTypes.digits[(0xF & i29 >>> 8)]);
            write(CharTypes.digits[(0xF & i29 >>> 4)]);
            write(CharTypes.digits[(i29 & 0xF)]);
            continue;
            if (((i29 < CharTypes.specicalFlags_doubleQuotes.length) && (CharTypes.specicalFlags_doubleQuotes[i29] != 0)) || ((i29 == 47) && (isEnabled(SerializerFeature.WriteSlashAsSpecial))))
            {
              write('\\');
              write(CharTypes.replaceChars[i29]);
            }
          }
          else
          {
            write(i29);
          }
        }
      }
      write('"');
    }
    while (paramChar == 0);
    write(paramChar);
    return;
    expandCapacity(j);
    label400: int k = 1 + this.count;
    int m = k + i;
    this.buf[this.count] = '"';
    paramString.getChars(0, i, this.buf, k);
    this.count = j;
    if (isEnabled(SerializerFeature.BrowserCompatible))
    {
      int i23 = -1;
      int i24 = k;
      if (i24 < m)
      {
        int i27 = this.buf[i24];
        if ((i27 == 34) || (i27 == 47) || (i27 == 92))
        {
          i23 = i24;
          j++;
        }
        while (true)
        {
          i24++;
          break;
          if ((i27 == 8) || (i27 == 12) || (i27 == 10) || (i27 == 13) || (i27 == 9))
          {
            i23 = i24;
            j++;
          }
          else if (i27 < 32)
          {
            i23 = i24;
            j += 5;
          }
          else if (i27 >= 127)
          {
            i23 = i24;
            j += 5;
          }
        }
      }
      if (j > this.buf.length)
        expandCapacity(j);
      this.count = j;
      int i25 = i23;
      if (i25 >= k)
      {
        int i26 = this.buf[i25];
        if ((i26 == 8) || (i26 == 12) || (i26 == 10) || (i26 == 13) || (i26 == 9))
        {
          System.arraycopy(this.buf, i25 + 1, this.buf, i25 + 2, -1 + (m - i25));
          this.buf[i25] = '\\';
          this.buf[(i25 + 1)] = CharTypes.replaceChars[i26];
          m++;
        }
        while (true)
        {
          i25--;
          break;
          if ((i26 == 34) || (i26 == 47) || (i26 == 92))
          {
            System.arraycopy(this.buf, i25 + 1, this.buf, i25 + 2, -1 + (m - i25));
            this.buf[i25] = '\\';
            this.buf[(i25 + 1)] = i26;
            m++;
          }
          else if (i26 < 32)
          {
            System.arraycopy(this.buf, i25 + 1, this.buf, i25 + 6, -1 + (m - i25));
            this.buf[i25] = '\\';
            this.buf[(i25 + 1)] = 'u';
            this.buf[(i25 + 2)] = '0';
            this.buf[(i25 + 3)] = '0';
            this.buf[(i25 + 4)] = CharTypes.ASCII_CHARS[(i26 * 2)];
            this.buf[(i25 + 5)] = CharTypes.ASCII_CHARS[(1 + i26 * 2)];
            m += 5;
          }
          else if (i26 >= 127)
          {
            System.arraycopy(this.buf, i25 + 1, this.buf, i25 + 6, -1 + (m - i25));
            this.buf[i25] = '\\';
            this.buf[(i25 + 1)] = 'u';
            this.buf[(i25 + 2)] = CharTypes.digits[(0xF & i26 >>> 12)];
            this.buf[(i25 + 3)] = CharTypes.digits[(0xF & i26 >>> 8)];
            this.buf[(i25 + 4)] = CharTypes.digits[(0xF & i26 >>> 4)];
            this.buf[(i25 + 5)] = CharTypes.digits[(i26 & 0xF)];
            m += 5;
          }
        }
      }
      if (paramChar != 0)
      {
        this.buf[(-2 + this.count)] = '"';
        this.buf[(-1 + this.count)] = paramChar;
        return;
      }
      this.buf[(-1 + this.count)] = '"';
      return;
    }
    int n = -1;
    int i1 = -1;
    int i2 = 0;
    int i3 = 0;
    if (paramBoolean)
    {
      int i21 = k;
      if (i21 < m)
      {
        int i22 = this.buf[i21];
        if (i22 >= 93)
          if (i22 == 8232)
          {
            i3++;
            n = i21;
            i2 = i22;
            j += 4;
            if (i1 == -1)
              i1 = i21;
          }
        while (true)
        {
          i21++;
          break;
          if ((i22 != 32) && ((i22 < 48) || (i22 == 92)) && (((i22 < CharTypes.specicalFlags_doubleQuotes.length) && (CharTypes.specicalFlags_doubleQuotes[i22] != 0)) || ((i22 == 47) && (isEnabled(SerializerFeature.WriteSlashAsSpecial)))))
          {
            i3++;
            n = i21;
            i2 = i22;
            if (i1 == -1)
              i1 = i21;
          }
        }
      }
    }
    int i4 = j + i3;
    if (i4 > this.buf.length)
      expandCapacity(i4);
    this.count = i4;
    if (i3 == 1)
      if (i2 == 8232)
      {
        int i14 = n + 1;
        int i15 = n + 6;
        int i16 = -1 + (m - n);
        System.arraycopy(this.buf, i14, this.buf, i15, i16);
        this.buf[n] = '\\';
        char[] arrayOfChar4 = this.buf;
        int i17 = n + 1;
        arrayOfChar4[i17] = 'u';
        char[] arrayOfChar5 = this.buf;
        int i18 = i17 + 1;
        arrayOfChar5[i18] = '2';
        char[] arrayOfChar6 = this.buf;
        int i19 = i18 + 1;
        arrayOfChar6[i19] = '0';
        char[] arrayOfChar7 = this.buf;
        int i20 = i19 + 1;
        arrayOfChar7[i20] = '2';
        this.buf[(i20 + 1)] = '8';
      }
    while (paramChar != 0)
    {
      this.buf[(-2 + this.count)] = '"';
      this.buf[(-1 + this.count)] = paramChar;
      return;
      int i11 = n + 1;
      int i12 = n + 2;
      int i13 = -1 + (m - n);
      System.arraycopy(this.buf, i11, this.buf, i12, i13);
      this.buf[n] = '\\';
      this.buf[(n + 1)] = CharTypes.replaceChars[i2];
      continue;
      if (i3 > 1)
      {
        int i5 = i1 - k;
        int i6 = i1;
        int i7 = i5;
        label1559: int i8;
        if (i7 < paramString.length())
        {
          i8 = paramString.charAt(i7);
          if (((i8 >= CharTypes.specicalFlags_doubleQuotes.length) || (CharTypes.specicalFlags_doubleQuotes[i8] == 0)) && ((i8 != 47) || (!isEnabled(SerializerFeature.WriteSlashAsSpecial))))
            break label1662;
          char[] arrayOfChar2 = this.buf;
          int i10 = i6 + 1;
          arrayOfChar2[i6] = '\\';
          char[] arrayOfChar3 = this.buf;
          i6 = i10 + 1;
          arrayOfChar3[i10] = CharTypes.replaceChars[i8];
          m++;
        }
        while (true)
        {
          i7++;
          break label1559;
          break;
          label1662: char[] arrayOfChar1 = this.buf;
          int i9 = i6 + 1;
          arrayOfChar1[i6] = i8;
          i6 = i9;
        }
      }
    }
    this.buf[(-1 + this.count)] = '"';
  }

  private void writeStringWithSingleQuote(String paramString)
  {
    if (paramString == null)
    {
      int i11 = 4 + this.count;
      if (i11 > this.buf.length)
        expandCapacity(i11);
      "null".getChars(0, 4, this.buf, this.count);
      this.count = i11;
      return;
    }
    int i = paramString.length();
    int j = 2 + (i + this.count);
    if (j > this.buf.length)
    {
      if (this.writer != null)
      {
        write('\'');
        int i10 = 0;
        if (i10 < paramString.length())
        {
          char c = paramString.charAt(i10);
          if ((c <= '\r') || (c == '\\') || (c == '\'') || ((c == '/') && (isEnabled(SerializerFeature.WriteSlashAsSpecial))))
          {
            write('\\');
            write(CharTypes.replaceChars[c]);
          }
          while (true)
          {
            i10++;
            break;
            write(c);
          }
        }
        write('\'');
        return;
      }
      expandCapacity(j);
    }
    int k = 1 + this.count;
    int m = k + i;
    this.buf[this.count] = '\'';
    paramString.getChars(0, i, this.buf, k);
    this.count = j;
    int n = 0;
    int i1 = -1;
    int i2 = 0;
    for (int i3 = k; i3 < m; i3++)
    {
      int i9 = this.buf[i3];
      if ((i9 <= 13) || (i9 == 92) || (i9 == 39) || ((i9 == 47) && (isEnabled(SerializerFeature.WriteSlashAsSpecial))))
      {
        n++;
        i1 = i3;
        i2 = i9;
      }
    }
    int i4 = j + n;
    if (i4 > this.buf.length)
      expandCapacity(i4);
    this.count = i4;
    if (n == 1)
    {
      System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 2, -1 + (m - i1));
      this.buf[i1] = '\\';
      this.buf[(i1 + 1)] = CharTypes.replaceChars[i2];
    }
    while (true)
    {
      this.buf[(-1 + this.count)] = '\'';
      return;
      if (n > 1)
      {
        System.arraycopy(this.buf, i1 + 1, this.buf, i1 + 2, -1 + (m - i1));
        this.buf[i1] = '\\';
        char[] arrayOfChar = this.buf;
        int i5 = i1 + 1;
        arrayOfChar[i5] = CharTypes.replaceChars[i2];
        int i6 = m + 1;
        for (int i7 = i5 - 2; i7 >= k; i7--)
        {
          int i8 = this.buf[i7];
          if ((i8 <= 13) || (i8 == 92) || (i8 == 39) || ((i8 == 47) && (isEnabled(SerializerFeature.WriteSlashAsSpecial))))
          {
            System.arraycopy(this.buf, i7 + 1, this.buf, i7 + 2, -1 + (i6 - i7));
            this.buf[i7] = '\\';
            this.buf[(i7 + 1)] = CharTypes.replaceChars[i8];
            i6++;
          }
        }
      }
    }
  }

  public SerializeWriter append(char paramChar)
  {
    write(paramChar);
    return this;
  }

  public SerializeWriter append(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null);
    for (String str = "null"; ; str = paramCharSequence.toString())
    {
      write(str, 0, str.length());
      return this;
    }
  }

  public SerializeWriter append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    if (paramCharSequence == null)
      paramCharSequence = "null";
    String str = paramCharSequence.subSequence(paramInt1, paramInt2).toString();
    write(str, 0, str.length());
    return this;
  }

  public void close()
  {
    if ((this.writer != null) && (this.count > 0))
      flush();
    if (this.buf.length <= 8192)
      bufLocal.set(new SoftReference(this.buf));
    this.buf = null;
  }

  public void config(SerializerFeature paramSerializerFeature, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.features |= paramSerializerFeature.getMask();
      return;
    }
    this.features &= (0xFFFFFFFF ^ paramSerializerFeature.getMask());
  }

  public void expandCapacity(int paramInt)
  {
    int i = 1 + 3 * this.buf.length / 2;
    if (i < paramInt)
      i = paramInt;
    char[] arrayOfChar = new char[i];
    System.arraycopy(this.buf, 0, arrayOfChar, 0, this.count);
    this.buf = arrayOfChar;
  }

  public void flush()
  {
    if (this.writer == null)
      return;
    try
    {
      this.writer.write(this.buf, 0, this.count);
      this.writer.flush();
      this.count = 0;
      return;
    }
    catch (IOException localIOException)
    {
      throw new JSONException(localIOException.getMessage(), localIOException);
    }
  }

  public int getBufferLength()
  {
    return this.buf.length;
  }

  public boolean isEnabled(SerializerFeature paramSerializerFeature)
  {
    return SerializerFeature.isEnabled(this.features, paramSerializerFeature);
  }

  public void reset()
  {
    this.count = 0;
  }

  public int size()
  {
    return this.count;
  }

  public byte[] toBytes(String paramString)
  {
    if (this.writer != null)
      throw new UnsupportedOperationException("writer not null");
    if (paramString == null)
      paramString = "UTF-8";
    return new SerialWriterStringEncoder(Charset.forName(paramString)).encode(this.buf, 0, this.count);
  }

  public char[] toCharArray()
  {
    if (this.writer != null)
      throw new UnsupportedOperationException("writer not null");
    char[] arrayOfChar = new char[this.count];
    System.arraycopy(this.buf, 0, arrayOfChar, 0, this.count);
    return arrayOfChar;
  }

  public String toString()
  {
    return new String(this.buf, 0, this.count);
  }

  public void write(char paramChar)
  {
    int i = 1 + this.count;
    if (i > this.buf.length)
    {
      if (this.writer != null)
        break label44;
      expandCapacity(i);
    }
    while (true)
    {
      this.buf[this.count] = paramChar;
      this.count = i;
      return;
      label44: flush();
      i = 1;
    }
  }

  public void write(int paramInt)
  {
    int i = 1 + this.count;
    if (i > this.buf.length)
    {
      if (this.writer != null)
        break label45;
      expandCapacity(i);
    }
    while (true)
    {
      this.buf[this.count] = ((char)paramInt);
      this.count = i;
      return;
      label45: flush();
      i = 1;
    }
  }

  public void write(String paramString)
  {
    if (paramString == null)
    {
      writeNull();
      return;
    }
    write(paramString, 0, paramString.length());
  }

  public void write(String paramString, int paramInt1, int paramInt2)
  {
    int i = paramInt2 + this.count;
    if (i > this.buf.length)
    {
      if (this.writer != null)
        break label54;
      expandCapacity(i);
    }
    while (true)
    {
      paramString.getChars(paramInt1, paramInt1 + paramInt2, this.buf, this.count);
      this.count = i;
      return;
      label54: 
      do
      {
        int j = this.buf.length - this.count;
        paramString.getChars(paramInt1, paramInt1 + j, this.buf, this.count);
        this.count = this.buf.length;
        flush();
        paramInt2 -= j;
        paramInt1 += j;
      }
      while (paramInt2 > this.buf.length);
      i = paramInt2;
    }
  }

  public void write(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      write("true");
      return;
    }
    write("false");
  }

  public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 > paramArrayOfChar.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length) || (paramInt1 + paramInt2 < 0))
      throw new IndexOutOfBoundsException();
    if (paramInt2 == 0)
      return;
    int i = paramInt2 + this.count;
    if (i > this.buf.length)
    {
      if (this.writer != null)
        break label93;
      expandCapacity(i);
    }
    while (true)
    {
      System.arraycopy(paramArrayOfChar, paramInt1, this.buf, this.count, paramInt2);
      this.count = i;
      return;
      label93: 
      do
      {
        int j = this.buf.length - this.count;
        System.arraycopy(paramArrayOfChar, paramInt1, this.buf, this.count, j);
        this.count = this.buf.length;
        flush();
        paramInt2 -= j;
        paramInt1 += j;
      }
      while (paramInt2 > this.buf.length);
      i = paramInt2;
    }
  }

  public void writeBooleanAndChar(boolean paramBoolean, char paramChar)
  {
    if (paramBoolean)
    {
      if (paramChar == ',')
      {
        write("true,");
        return;
      }
      if (paramChar == ']')
      {
        write("true]");
        return;
      }
      write("true");
      write(paramChar);
      return;
    }
    if (paramChar == ',')
    {
      write("false,");
      return;
    }
    if (paramChar == ']')
    {
      write("false]");
      return;
    }
    write("false");
    write(paramChar);
  }

  public void writeByteArray(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    if (i == 0)
    {
      write("\"\"");
      return;
    }
    char[] arrayOfChar1 = Base64.CA;
    int j = 3 * (i / 3);
    int k = 1 + (i - 1) / 3 << 2;
    int m = this.count;
    int n = 2 + (k + this.count);
    if (n > this.buf.length)
    {
      if (this.writer != null)
      {
        write('"');
        int i28;
        for (int i19 = 0; i19 < j; i19 = i28)
        {
          int i24 = i19 + 1;
          int i25 = (0xFF & paramArrayOfByte[i19]) << 16;
          int i26 = i24 + 1;
          int i27 = i25 | (0xFF & paramArrayOfByte[i24]) << 8;
          i28 = i26 + 1;
          int i29 = i27 | 0xFF & paramArrayOfByte[i26];
          write(arrayOfChar1[(0x3F & i29 >>> 18)]);
          write(arrayOfChar1[(0x3F & i29 >>> 12)]);
          write(arrayOfChar1[(0x3F & i29 >>> 6)]);
          write(arrayOfChar1[(i29 & 0x3F)]);
        }
        int i20 = i - j;
        int i22;
        int i23;
        if (i20 > 0)
        {
          int i21 = (0xFF & paramArrayOfByte[j]) << 10;
          if (i20 != 2)
            break label316;
          i22 = (0xFF & paramArrayOfByte[(i - 1)]) << 2;
          i23 = i21 | i22;
          write(arrayOfChar1[(i23 >> 12)]);
          write(arrayOfChar1[(0x3F & i23 >>> 6)]);
          if (i20 != 2)
            break label322;
        }
        label316: label322: for (char c = arrayOfChar1[(i23 & 0x3F)]; ; c = '=')
        {
          write(c);
          write('=');
          write('"');
          return;
          i22 = 0;
          break;
        }
      }
      expandCapacity(n);
    }
    this.count = n;
    char[] arrayOfChar2 = this.buf;
    int i1 = m + 1;
    arrayOfChar2[m] = '"';
    int i2 = i1;
    int i14;
    for (int i3 = 0; i3 < j; i3 = i14)
    {
      int i10 = i3 + 1;
      int i11 = (0xFF & paramArrayOfByte[i3]) << 16;
      int i12 = i10 + 1;
      int i13 = i11 | (0xFF & paramArrayOfByte[i10]) << 8;
      i14 = i12 + 1;
      int i15 = i13 | 0xFF & paramArrayOfByte[i12];
      char[] arrayOfChar4 = this.buf;
      int i16 = i2 + 1;
      arrayOfChar4[i2] = arrayOfChar1[(0x3F & i15 >>> 18)];
      char[] arrayOfChar5 = this.buf;
      int i17 = i16 + 1;
      arrayOfChar5[i16] = arrayOfChar1[(0x3F & i15 >>> 12)];
      char[] arrayOfChar6 = this.buf;
      int i18 = i17 + 1;
      arrayOfChar6[i17] = arrayOfChar1[(0x3F & i15 >>> 6)];
      char[] arrayOfChar7 = this.buf;
      i2 = i18 + 1;
      arrayOfChar7[i18] = arrayOfChar1[(i15 & 0x3F)];
    }
    int i4 = i - j;
    int i6;
    int i7;
    char[] arrayOfChar3;
    int i8;
    if (i4 > 0)
    {
      int i5 = (0xFF & paramArrayOfByte[j]) << 10;
      if (i4 != 2)
        break label688;
      i6 = (0xFF & paramArrayOfByte[(i - 1)]) << 2;
      i7 = i5 | i6;
      this.buf[(n - 5)] = arrayOfChar1[(i7 >> 12)];
      this.buf[(n - 4)] = arrayOfChar1[(0x3F & i7 >>> 6)];
      arrayOfChar3 = this.buf;
      i8 = n - 3;
      if (i4 != 2)
        break label694;
    }
    label688: label694: for (int i9 = arrayOfChar1[(i7 & 0x3F)]; ; i9 = 61)
    {
      arrayOfChar3[i8] = i9;
      this.buf[(n - 2)] = '=';
      this.buf[(n - 1)] = '"';
      return;
      i6 = 0;
      break;
    }
  }

  public void writeCharacterAndChar(char paramChar1, char paramChar2)
  {
    writeString(Character.toString(paramChar1));
    write(paramChar2);
  }

  public void writeDoubleAndChar(double paramDouble, char paramChar)
  {
    String str = Double.toString(paramDouble);
    if (str.endsWith(".0"))
      str = str.substring(0, -2 + str.length());
    write(str);
    write(paramChar);
  }

  public void writeEnum(Enum<?> paramEnum, char paramChar)
  {
    if (paramEnum == null)
    {
      writeNull();
      write(',');
      return;
    }
    if (isEnabled(SerializerFeature.WriteEnumUsingToString))
    {
      if (isEnabled(SerializerFeature.UseSingleQuotes))
      {
        write('\'');
        write(paramEnum.name());
        write('\'');
        write(paramChar);
        return;
      }
      write('"');
      write(paramEnum.name());
      write('"');
      write(paramChar);
      return;
    }
    writeIntAndChar(paramEnum.ordinal(), paramChar);
  }

  public void writeFieldEmptyList(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    write("[]");
  }

  public void writeFieldName(String paramString)
  {
    writeFieldName(paramString, false);
  }

  public void writeFieldName(String paramString, boolean paramBoolean)
  {
    if (paramString == null)
    {
      write("null:");
      return;
    }
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      if (isEnabled(SerializerFeature.QuoteFieldNames))
      {
        writeStringWithSingleQuote(paramString);
        write(':');
        return;
      }
      writeKeyWithSingleQuoteIfHasSpecial(paramString);
      return;
    }
    if (isEnabled(SerializerFeature.QuoteFieldNames))
    {
      writeStringWithDoubleQuote(paramString, ':', paramBoolean);
      return;
    }
    writeKeyWithDoubleQuoteIfHasSpecial(paramString);
  }

  public void writeFieldNull(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    writeNull();
  }

  public void writeFieldNullBoolean(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (isEnabled(SerializerFeature.WriteNullBooleanAsFalse))
    {
      write("false");
      return;
    }
    writeNull();
  }

  public void writeFieldNullList(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (isEnabled(SerializerFeature.WriteNullListAsEmpty))
    {
      write("[]");
      return;
    }
    writeNull();
  }

  public void writeFieldNullNumber(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (isEnabled(SerializerFeature.WriteNullNumberAsZero))
    {
      write('0');
      return;
    }
    writeNull();
  }

  public void writeFieldNullString(char paramChar, String paramString)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (isEnabled(SerializerFeature.WriteNullStringAsEmpty))
    {
      writeString("");
      return;
    }
    writeNull();
  }

  public void writeFieldValue(char paramChar1, String paramString, char paramChar2)
  {
    write(paramChar1);
    writeFieldName(paramString);
    if (paramChar2 == 0)
    {
      writeString("");
      return;
    }
    writeString(Character.toString(paramChar2));
  }

  public void writeFieldValue(char paramChar, String paramString, double paramDouble)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (paramDouble == 0.0D)
    {
      write('0');
      return;
    }
    if (Double.isNaN(paramDouble))
    {
      writeNull();
      return;
    }
    if (Double.isInfinite(paramDouble))
    {
      writeNull();
      return;
    }
    String str = Double.toString(paramDouble);
    if (str.endsWith(".0"))
      str = str.substring(0, -2 + str.length());
    write(str);
  }

  public void writeFieldValue(char paramChar, String paramString, float paramFloat)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (paramFloat == 0.0F)
    {
      write('0');
      return;
    }
    if (Float.isNaN(paramFloat))
    {
      writeNull();
      return;
    }
    if (Float.isInfinite(paramFloat))
    {
      writeNull();
      return;
    }
    String str = Float.toString(paramFloat);
    if (str.endsWith(".0"))
      str = str.substring(0, -2 + str.length());
    write(str);
  }

  public void writeFieldValue(char paramChar, String paramString, int paramInt)
  {
    if ((paramInt == -2147483648) || (!isEnabled(SerializerFeature.QuoteFieldNames)))
    {
      writeFieldValue1(paramChar, paramString, paramInt);
      return;
    }
    int i;
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      i = 39;
      if (paramInt >= 0)
        break label104;
    }
    int k;
    int m;
    label104: for (int j = 1 + IOUtils.stringSize(-paramInt); ; j = IOUtils.stringSize(paramInt))
    {
      k = paramString.length();
      m = j + (4 + (k + this.count));
      if (m <= this.buf.length)
        break label119;
      if (this.writer == null)
        break label113;
      writeFieldValue1(paramChar, paramString, paramInt);
      return;
      i = 34;
      break;
    }
    label113: expandCapacity(m);
    label119: int n = this.count;
    this.count = m;
    this.buf[n] = paramChar;
    int i1 = 1 + (n + k);
    this.buf[(n + 1)] = i;
    paramString.getChars(0, k, this.buf, n + 2);
    this.buf[(i1 + 1)] = i;
    this.buf[(i1 + 2)] = ':';
    IOUtils.getChars(paramInt, this.count, this.buf);
  }

  public void writeFieldValue(char paramChar, String paramString, long paramLong)
  {
    if ((paramLong == -9223372036854775808L) || (!isEnabled(SerializerFeature.QuoteFieldNames)))
    {
      writeFieldValue1(paramChar, paramString, paramLong);
      return;
    }
    int i;
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      i = 39;
      if (paramLong >= 0L)
        break label115;
    }
    int k;
    int m;
    label115: for (int j = 1 + IOUtils.stringSize(-paramLong); ; j = IOUtils.stringSize(paramLong))
    {
      k = paramString.length();
      m = j + (4 + (k + this.count));
      if (m <= this.buf.length)
        break label130;
      if (this.writer == null)
        break label124;
      write(paramChar);
      writeFieldName(paramString);
      writeLong(paramLong);
      return;
      i = 34;
      break;
    }
    label124: expandCapacity(m);
    label130: int n = this.count;
    this.count = m;
    this.buf[n] = paramChar;
    int i1 = 1 + (n + k);
    this.buf[(n + 1)] = i;
    paramString.getChars(0, k, this.buf, n + 2);
    this.buf[(i1 + 1)] = i;
    this.buf[(i1 + 2)] = ':';
    IOUtils.getChars(paramLong, this.count, this.buf);
  }

  public void writeFieldValue(char paramChar, String paramString, Enum<?> paramEnum)
  {
    if (paramEnum == null)
    {
      write(paramChar);
      writeFieldName(paramString);
      writeNull();
      return;
    }
    if (isEnabled(SerializerFeature.WriteEnumUsingToString))
    {
      if (isEnabled(SerializerFeature.UseSingleQuotes))
      {
        writeFieldValue(paramChar, paramString, paramEnum.name());
        return;
      }
      writeFieldValueStringWithDoubleQuote(paramChar, paramString, paramEnum.name(), false);
      return;
    }
    writeFieldValue(paramChar, paramString, paramEnum.ordinal());
  }

  public void writeFieldValue(char paramChar, String paramString1, String paramString2)
  {
    if (isEnabled(SerializerFeature.QuoteFieldNames))
    {
      if (isEnabled(SerializerFeature.UseSingleQuotes))
      {
        write(paramChar);
        writeFieldName(paramString1);
        if (paramString2 == null)
        {
          writeNull();
          return;
        }
        writeString(paramString2);
        return;
      }
      if (isEnabled(SerializerFeature.BrowserCompatible))
      {
        write(paramChar);
        writeStringWithDoubleQuote(paramString1, ':');
        writeStringWithDoubleQuote(paramString2, '\000');
        return;
      }
      writeFieldValueStringWithDoubleQuote(paramChar, paramString1, paramString2, true);
      return;
    }
    write(paramChar);
    writeFieldName(paramString1);
    if (paramString2 == null)
    {
      writeNull();
      return;
    }
    writeString(paramString2);
  }

  public void writeFieldValue(char paramChar, String paramString, BigDecimal paramBigDecimal)
  {
    write(paramChar);
    writeFieldName(paramString);
    if (paramBigDecimal == null)
    {
      writeNull();
      return;
    }
    write(paramBigDecimal.toString());
  }

  public void writeFieldValue(char paramChar, String paramString, boolean paramBoolean)
  {
    int i;
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      i = 39;
      if (!paramBoolean)
        break label87;
    }
    int k;
    int m;
    label87: for (int j = 4; ; j = 5)
    {
      k = paramString.length();
      m = j + (4 + (k + this.count));
      if (m <= this.buf.length)
        break label99;
      if (this.writer == null)
        break label93;
      write(paramChar);
      writeString(paramString);
      write(':');
      write(paramBoolean);
      return;
      i = 34;
      break;
    }
    label93: expandCapacity(m);
    label99: int n = this.count;
    this.count = m;
    this.buf[n] = paramChar;
    int i1 = 1 + (n + k);
    this.buf[(n + 1)] = i;
    paramString.getChars(0, k, this.buf, n + 2);
    this.buf[(i1 + 1)] = i;
    if (paramBoolean)
    {
      System.arraycopy(":true".toCharArray(), 0, this.buf, i1 + 2, 5);
      return;
    }
    System.arraycopy(":false".toCharArray(), 0, this.buf, i1 + 2, 6);
  }

  public void writeFieldValue1(char paramChar, String paramString, int paramInt)
  {
    write(paramChar);
    writeFieldName(paramString);
    writeInt(paramInt);
  }

  public void writeFieldValue1(char paramChar, String paramString, long paramLong)
  {
    write(paramChar);
    writeFieldName(paramString);
    writeLong(paramLong);
  }

  public void writeFloatAndChar(float paramFloat, char paramChar)
  {
    String str = Float.toString(paramFloat);
    if (str.endsWith(".0"))
      str = str.substring(0, -2 + str.length());
    write(str);
    write(paramChar);
  }

  public void writeInt(int paramInt)
  {
    if (paramInt == -2147483648)
    {
      write("-2147483648");
      return;
    }
    if (paramInt < 0);
    for (int i = 1 + IOUtils.stringSize(-paramInt); ; i = IOUtils.stringSize(paramInt))
    {
      int j = i + this.count;
      if (j > this.buf.length)
      {
        if (this.writer != null)
          break;
        expandCapacity(j);
      }
      IOUtils.getChars(paramInt, j, this.buf);
      this.count = j;
      return;
    }
    char[] arrayOfChar = new char[i];
    IOUtils.getChars(paramInt, i, arrayOfChar);
    write(arrayOfChar, 0, arrayOfChar.length);
  }

  public void writeIntAndChar(int paramInt, char paramChar)
  {
    if (paramInt == -2147483648)
    {
      write("-2147483648");
      write(paramChar);
      return;
    }
    if (paramInt < 0);
    int j;
    int k;
    for (int i = 1 + IOUtils.stringSize(-paramInt); ; i = IOUtils.stringSize(paramInt))
    {
      j = i + this.count;
      k = j + 1;
      if (k <= this.buf.length)
        break label88;
      if (this.writer == null)
        break;
      writeInt(paramInt);
      write(paramChar);
      return;
    }
    expandCapacity(k);
    label88: IOUtils.getChars(paramInt, j, this.buf);
    this.buf[j] = paramChar;
    this.count = k;
  }

  public void writeLong(long paramLong)
  {
    if (paramLong == -9223372036854775808L)
    {
      write("-9223372036854775808");
      return;
    }
    if (paramLong < 0L);
    for (int i = 1 + IOUtils.stringSize(-paramLong); ; i = IOUtils.stringSize(paramLong))
    {
      int j = i + this.count;
      if (j > this.buf.length)
      {
        if (this.writer != null)
          break;
        expandCapacity(j);
      }
      IOUtils.getChars(paramLong, j, this.buf);
      this.count = j;
      return;
    }
    char[] arrayOfChar = new char[i];
    IOUtils.getChars(paramLong, i, arrayOfChar);
    write(arrayOfChar, 0, arrayOfChar.length);
  }

  public void writeLongAndChar(long paramLong, char paramChar)
    throws IOException
  {
    if (paramLong == -9223372036854775808L)
    {
      write("-9223372036854775808");
      write(paramChar);
      return;
    }
    if (paramLong < 0L);
    int j;
    int k;
    for (int i = 1 + IOUtils.stringSize(-paramLong); ; i = IOUtils.stringSize(paramLong))
    {
      j = i + this.count;
      k = j + 1;
      if (k <= this.buf.length)
        break label94;
      if (this.writer == null)
        break;
      writeLong(paramLong);
      write(paramChar);
      return;
    }
    expandCapacity(k);
    label94: IOUtils.getChars(paramLong, j, this.buf);
    this.buf[j] = paramChar;
    this.count = k;
  }

  public void writeNull()
  {
    write("null");
  }

  public void writeString(String paramString)
  {
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      writeStringWithSingleQuote(paramString);
      return;
    }
    writeStringWithDoubleQuote(paramString, '\000');
  }

  public void writeString(String paramString, char paramChar)
  {
    if (isEnabled(SerializerFeature.UseSingleQuotes))
    {
      writeStringWithSingleQuote(paramString);
      write(paramChar);
      return;
    }
    writeStringWithDoubleQuote(paramString, paramChar);
  }

  public void writeTo(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    writeTo(paramOutputStream, Charset.forName(paramString));
  }

  public void writeTo(OutputStream paramOutputStream, Charset paramCharset)
    throws IOException
  {
    if (this.writer != null)
      throw new UnsupportedOperationException("writer not null");
    paramOutputStream.write(new String(this.buf, 0, this.count).getBytes(paramCharset));
  }

  public void writeTo(Writer paramWriter)
    throws IOException
  {
    if (this.writer != null)
      throw new UnsupportedOperationException("writer not null");
    paramWriter.write(this.buf, 0, this.count);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.SerializeWriter
 * JD-Core Version:    0.6.2
 */