package cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary;

import java.util.Arrays;

class BaseNCodec$Context
{
  byte[] buffer;
  int currentLinePos;
  boolean eof;
  int ibitWorkArea;
  long lbitWorkArea;
  int modulus;
  int pos;
  int readPos;

  public String toString()
  {
    Object[] arrayOfObject = new Object[9];
    arrayOfObject[0] = getClass().getSimpleName();
    arrayOfObject[1] = Arrays.toString(this.buffer);
    arrayOfObject[2] = Integer.valueOf(this.currentLinePos);
    arrayOfObject[3] = Boolean.valueOf(this.eof);
    arrayOfObject[4] = Integer.valueOf(this.ibitWorkArea);
    arrayOfObject[5] = Long.valueOf(this.lbitWorkArea);
    arrayOfObject[6] = Integer.valueOf(this.modulus);
    arrayOfObject[7] = Integer.valueOf(this.pos);
    arrayOfObject[8] = Integer.valueOf(this.readPos);
    return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", arrayOfObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary.BaseNCodec.Context
 * JD-Core Version:    0.6.2
 */