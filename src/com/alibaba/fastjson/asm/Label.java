package com.alibaba.fastjson.asm;

public class Label
{
  static final int RESOLVED = 2;
  public Object info;
  int inputStackTop;
  int line;
  Label next;
  int outputStackMax;
  int position;
  private int referenceCount;
  private int[] srcAndRefPositions;
  int status;
  Label successor;

  private void addReference(int paramInt1, int paramInt2)
  {
    if (this.srcAndRefPositions == null)
      this.srcAndRefPositions = new int[6];
    if (this.referenceCount >= this.srcAndRefPositions.length)
    {
      int[] arrayOfInt3 = new int[6 + this.srcAndRefPositions.length];
      System.arraycopy(this.srcAndRefPositions, 0, arrayOfInt3, 0, this.srcAndRefPositions.length);
      this.srcAndRefPositions = arrayOfInt3;
    }
    int[] arrayOfInt1 = this.srcAndRefPositions;
    int i = this.referenceCount;
    this.referenceCount = (i + 1);
    arrayOfInt1[i] = paramInt1;
    int[] arrayOfInt2 = this.srcAndRefPositions;
    int j = this.referenceCount;
    this.referenceCount = (j + 1);
    arrayOfInt2[j] = paramInt2;
  }

  void put(MethodWriter paramMethodWriter, ByteVector paramByteVector, int paramInt)
  {
    if ((0x2 & this.status) == 0)
    {
      addReference(paramInt, paramByteVector.length);
      paramByteVector.putShort(-1);
      return;
    }
    paramByteVector.putShort(this.position - paramInt);
  }

  void resolve(MethodWriter paramMethodWriter, int paramInt, byte[] paramArrayOfByte)
  {
    this.status = (0x2 | this.status);
    this.position = paramInt;
    int i = 0;
    while (i < this.referenceCount)
    {
      int[] arrayOfInt1 = this.srcAndRefPositions;
      int j = i + 1;
      int k = arrayOfInt1[i];
      int[] arrayOfInt2 = this.srcAndRefPositions;
      i = j + 1;
      int m = arrayOfInt2[j];
      int n = paramInt - k;
      int i1 = m + 1;
      paramArrayOfByte[m] = ((byte)(n >>> 8));
      paramArrayOfByte[i1] = ((byte)n);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.asm.Label
 * JD-Core Version:    0.6.2
 */