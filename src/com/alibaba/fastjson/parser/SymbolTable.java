package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;

public class SymbolTable
{
  public static final int DEFAULT_TABLE_SIZE = 256;
  public static final int MAX_BUCKET_LENTH = 8;
  public static final int MAX_SIZE = 2048;
  private final Entry[] buckets;
  private final int indexMask;
  private int size = 0;
  private final String[] symbols;
  private final char[][] symbols_char;

  public SymbolTable()
  {
    this(256);
    addSymbol("$ref", 0, 4, "$ref".hashCode());
    addSymbol(JSON.DEFAULT_TYPE_KEY, 0, 4, JSON.DEFAULT_TYPE_KEY.hashCode());
  }

  public SymbolTable(int paramInt)
  {
    this.indexMask = (paramInt - 1);
    this.buckets = new Entry[paramInt];
    this.symbols = new String[paramInt];
    this.symbols_char = new char[paramInt][];
  }

  public static final int hash(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = 0;
    int n;
    for (int k = paramInt1; j < paramInt2; k = n)
    {
      int m = i * 31;
      n = k + 1;
      i = m + paramArrayOfChar[k];
      j++;
    }
    return i;
  }

  public String addSymbol(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt3 & this.indexMask;
    String str = this.symbols[i];
    int j = 1;
    if (str != null)
    {
      if (str.length() == paramInt2)
      {
        char[] arrayOfChar2 = this.symbols_char[i];
        for (int i1 = 0; ; i1++)
          if (i1 < paramInt2)
          {
            if (paramString.charAt(paramInt1 + i1) != arrayOfChar2[i1])
              j = 0;
          }
          else
          {
            if (j == 0)
              break;
            return str;
          }
      }
      j = 0;
    }
    int k = 0;
    Entry localEntry1 = this.buckets[i];
    if (localEntry1 != null)
    {
      char[] arrayOfChar1 = localEntry1.characters;
      int m;
      if ((paramInt2 == arrayOfChar1.length) && (paramInt3 == localEntry1.hashCode))
        m = 1;
      for (int n = 0; ; n++)
        if (n < paramInt2)
        {
          if (paramString.charAt(paramInt1 + n) != arrayOfChar1[n])
            m = 0;
        }
        else
        {
          if (m != 0)
            break label185;
          k++;
          localEntry1 = localEntry1.next;
          break;
        }
      label185: return localEntry1.symbol;
    }
    if (k >= 8)
      return paramString.substring(paramInt1, paramInt1 + paramInt2);
    if (this.size >= 2048)
      return paramString.substring(paramInt1, paramInt1 + paramInt2);
    Entry localEntry2 = new Entry(paramString, paramInt1, paramInt2, paramInt3, this.buckets[i]);
    this.buckets[i] = localEntry2;
    if (j != 0)
    {
      this.symbols[i] = localEntry2.symbol;
      this.symbols_char[i] = localEntry2.characters;
    }
    this.size = (1 + this.size);
    return localEntry2.symbol;
  }

  public String addSymbol(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    return addSymbol(paramArrayOfChar, paramInt1, paramInt2, hash(paramArrayOfChar, paramInt1, paramInt2));
  }

  public String addSymbol(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt3 & this.indexMask;
    String str = this.symbols[i];
    int j = 1;
    if (str != null)
    {
      if (str.length() == paramInt2)
      {
        char[] arrayOfChar2 = this.symbols_char[i];
        for (int i1 = 0; ; i1++)
          if (i1 < paramInt2)
          {
            if (paramArrayOfChar[(paramInt1 + i1)] != arrayOfChar2[i1])
              j = 0;
          }
          else
          {
            if (j == 0)
              break;
            return str;
          }
      }
      j = 0;
    }
    int k = 0;
    Entry localEntry1 = this.buckets[i];
    if (localEntry1 != null)
    {
      char[] arrayOfChar1 = localEntry1.characters;
      int m;
      if ((paramInt2 == arrayOfChar1.length) && (paramInt3 == localEntry1.hashCode))
        m = 1;
      for (int n = 0; ; n++)
        if (n < paramInt2)
        {
          if (paramArrayOfChar[(paramInt1 + n)] != arrayOfChar1[n])
            m = 0;
        }
        else
        {
          if (m != 0)
            break label181;
          k++;
          localEntry1 = localEntry1.next;
          break;
        }
      label181: return localEntry1.symbol;
    }
    if (k >= 8)
      return new String(paramArrayOfChar, paramInt1, paramInt2);
    if (this.size >= 2048)
      return new String(paramArrayOfChar, paramInt1, paramInt2);
    Entry localEntry2 = new Entry(paramArrayOfChar, paramInt1, paramInt2, paramInt3, this.buckets[i]);
    this.buckets[i] = localEntry2;
    if (j != 0)
    {
      this.symbols[i] = localEntry2.symbol;
      this.symbols_char[i] = localEntry2.characters;
    }
    this.size = (1 + this.size);
    return localEntry2.symbol;
  }

  public int size()
  {
    return this.size;
  }

  protected static final class Entry
  {
    public final byte[] bytes;
    public final char[] characters;
    public final int hashCode;
    public Entry next;
    public final String symbol;

    public Entry(String paramString, int paramInt1, int paramInt2, int paramInt3, Entry paramEntry)
    {
      this.symbol = paramString.substring(paramInt1, paramInt1 + paramInt2).intern();
      this.characters = this.symbol.toCharArray();
      this.next = paramEntry;
      this.hashCode = paramInt3;
      this.bytes = null;
    }

    public Entry(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3, Entry paramEntry)
    {
      this.characters = new char[paramInt2];
      System.arraycopy(paramArrayOfChar, paramInt1, this.characters, 0, paramInt2);
      this.symbol = new String(this.characters).intern();
      this.next = paramEntry;
      this.hashCode = paramInt3;
      this.bytes = null;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.SymbolTable
 * JD-Core Version:    0.6.2
 */