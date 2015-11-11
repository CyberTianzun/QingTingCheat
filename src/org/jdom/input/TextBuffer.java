package org.jdom.input;

import org.jdom.Verifier;

class TextBuffer
{
  private static final String CVS_ID = "@(#) $RCSfile: TextBuffer.java,v $ $Revision: 1.10 $ $Date: 2007/11/10 05:29:00 $ $Name: jdom_1_1_1 $";
  private char[] array = new char[4096];
  private int arraySize = 0;
  private String prefixString;

  private void ensureCapacity(int paramInt)
  {
    int i = this.array.length;
    if (paramInt > i)
    {
      char[] arrayOfChar = this.array;
      int j = i;
      while (paramInt > j)
        j += i / 2;
      this.array = new char[j];
      System.arraycopy(arrayOfChar, 0, this.array, 0, this.arraySize);
    }
  }

  void append(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (this.prefixString == null)
    {
      this.prefixString = new String(paramArrayOfChar, paramInt1, paramInt2);
      return;
    }
    ensureCapacity(paramInt2 + this.arraySize);
    System.arraycopy(paramArrayOfChar, paramInt1, this.array, this.arraySize, paramInt2);
    this.arraySize = (paramInt2 + this.arraySize);
  }

  void clear()
  {
    this.arraySize = 0;
    this.prefixString = null;
  }

  boolean isAllWhitespace()
  {
    boolean bool1;
    if ((this.prefixString == null) || (this.prefixString.length() == 0))
    {
      bool1 = true;
      return bool1;
    }
    int i = this.prefixString.length();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label62;
      boolean bool3 = Verifier.isXMLWhitespace(this.prefixString.charAt(j));
      bool1 = false;
      if (!bool3)
        break;
    }
    label62: for (int k = 0; ; k++)
    {
      if (k >= this.arraySize)
        break label99;
      boolean bool2 = Verifier.isXMLWhitespace(this.array[k]);
      bool1 = false;
      if (!bool2)
        break;
    }
    label99: return true;
  }

  int size()
  {
    if (this.prefixString == null)
      return 0;
    return this.prefixString.length() + this.arraySize;
  }

  public String toString()
  {
    if (this.prefixString == null)
      return "";
    if (this.arraySize == 0)
      return this.prefixString;
    return new StringBuffer(this.prefixString.length() + this.arraySize).append(this.prefixString).append(this.array, 0, this.arraySize).toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.input.TextBuffer
 * JD-Core Version:    0.6.2
 */