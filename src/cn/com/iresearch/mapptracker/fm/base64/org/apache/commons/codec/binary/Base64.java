package cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary;

import java.math.BigInteger;

public class Base64 extends BaseNCodec
{
  private static final int BITS_PER_ENCODED_BYTE = 6;
  private static final int BYTES_PER_ENCODED_BLOCK = 4;
  private static final int BYTES_PER_UNENCODED_BLOCK = 3;
  static final byte[] CHUNK_SEPARATOR = { 13, 10 };
  private static final byte[] DECODE_TABLE = arrayOfByte;
  private static final int MASK_6BITS = 63;
  private static final byte[] STANDARD_ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
  private static final byte[] URL_SAFE_ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
  private final int decodeSize;
  private final byte[] decodeTable;
  private final int encodeSize;
  private final byte[] encodeTable;
  private final byte[] lineSeparator;

  static
  {
    byte[] arrayOfByte = new byte[123];
    arrayOfByte[0] = -1;
    arrayOfByte[1] = -1;
    arrayOfByte[2] = -1;
    arrayOfByte[3] = -1;
    arrayOfByte[4] = -1;
    arrayOfByte[5] = -1;
    arrayOfByte[6] = -1;
    arrayOfByte[7] = -1;
    arrayOfByte[8] = -1;
    arrayOfByte[9] = -1;
    arrayOfByte[10] = -1;
    arrayOfByte[11] = -1;
    arrayOfByte[12] = -1;
    arrayOfByte[13] = -1;
    arrayOfByte[14] = -1;
    arrayOfByte[15] = -1;
    arrayOfByte[16] = -1;
    arrayOfByte[17] = -1;
    arrayOfByte[18] = -1;
    arrayOfByte[19] = -1;
    arrayOfByte[20] = -1;
    arrayOfByte[21] = -1;
    arrayOfByte[22] = -1;
    arrayOfByte[23] = -1;
    arrayOfByte[24] = -1;
    arrayOfByte[25] = -1;
    arrayOfByte[26] = -1;
    arrayOfByte[27] = -1;
    arrayOfByte[28] = -1;
    arrayOfByte[29] = -1;
    arrayOfByte[30] = -1;
    arrayOfByte[31] = -1;
    arrayOfByte[32] = -1;
    arrayOfByte[33] = -1;
    arrayOfByte[34] = -1;
    arrayOfByte[35] = -1;
    arrayOfByte[36] = -1;
    arrayOfByte[37] = -1;
    arrayOfByte[38] = -1;
    arrayOfByte[39] = -1;
    arrayOfByte[40] = -1;
    arrayOfByte[41] = -1;
    arrayOfByte[42] = -1;
    arrayOfByte[43] = 62;
    arrayOfByte[44] = -1;
    arrayOfByte[45] = 62;
    arrayOfByte[46] = -1;
    arrayOfByte[47] = 63;
    arrayOfByte[48] = 52;
    arrayOfByte[49] = 53;
    arrayOfByte[50] = 54;
    arrayOfByte[51] = 55;
    arrayOfByte[52] = 56;
    arrayOfByte[53] = 57;
    arrayOfByte[54] = 58;
    arrayOfByte[55] = 59;
    arrayOfByte[56] = 60;
    arrayOfByte[57] = 61;
    arrayOfByte[58] = -1;
    arrayOfByte[59] = -1;
    arrayOfByte[60] = -1;
    arrayOfByte[61] = -1;
    arrayOfByte[62] = -1;
    arrayOfByte[63] = -1;
    arrayOfByte[64] = -1;
    arrayOfByte[66] = 1;
    arrayOfByte[67] = 2;
    arrayOfByte[68] = 3;
    arrayOfByte[69] = 4;
    arrayOfByte[70] = 5;
    arrayOfByte[71] = 6;
    arrayOfByte[72] = 7;
    arrayOfByte[73] = 8;
    arrayOfByte[74] = 9;
    arrayOfByte[75] = 10;
    arrayOfByte[76] = 11;
    arrayOfByte[77] = 12;
    arrayOfByte[78] = 13;
    arrayOfByte[79] = 14;
    arrayOfByte[80] = 15;
    arrayOfByte[81] = 16;
    arrayOfByte[82] = 17;
    arrayOfByte[83] = 18;
    arrayOfByte[84] = 19;
    arrayOfByte[85] = 20;
    arrayOfByte[86] = 21;
    arrayOfByte[87] = 22;
    arrayOfByte[88] = 23;
    arrayOfByte[89] = 24;
    arrayOfByte[90] = 25;
    arrayOfByte[91] = -1;
    arrayOfByte[92] = -1;
    arrayOfByte[93] = -1;
    arrayOfByte[94] = -1;
    arrayOfByte[95] = 63;
    arrayOfByte[96] = -1;
    arrayOfByte[97] = 26;
    arrayOfByte[98] = 27;
    arrayOfByte[99] = 28;
    arrayOfByte[100] = 29;
    arrayOfByte[101] = 30;
    arrayOfByte[102] = 31;
    arrayOfByte[103] = 32;
    arrayOfByte[104] = 33;
    arrayOfByte[105] = 34;
    arrayOfByte[106] = 35;
    arrayOfByte[107] = 36;
    arrayOfByte[108] = 37;
    arrayOfByte[109] = 38;
    arrayOfByte[110] = 39;
    arrayOfByte[111] = 40;
    arrayOfByte[112] = 41;
    arrayOfByte[113] = 42;
    arrayOfByte[114] = 43;
    arrayOfByte[115] = 44;
    arrayOfByte[116] = 45;
    arrayOfByte[117] = 46;
    arrayOfByte[118] = 47;
    arrayOfByte[119] = 48;
    arrayOfByte[120] = 49;
    arrayOfByte[121] = 50;
    arrayOfByte[122] = 51;
  }

  public Base64()
  {
    this(0);
  }

  public Base64(int paramInt)
  {
    this(paramInt, CHUNK_SEPARATOR);
  }

  public Base64(int paramInt, byte[] paramArrayOfByte)
  {
    this(paramInt, paramArrayOfByte, false);
  }

  public Base64(int paramInt, byte[] paramArrayOfByte, boolean paramBoolean)
  {
  }

  public Base64(boolean paramBoolean)
  {
    this(76, CHUNK_SEPARATOR, paramBoolean);
  }

  public static byte[] decodeBase64(String paramString)
  {
    return new Base64().decode(paramString);
  }

  public static byte[] decodeBase64(byte[] paramArrayOfByte)
  {
    return new Base64().decode(paramArrayOfByte);
  }

  public static BigInteger decodeInteger(byte[] paramArrayOfByte)
  {
    return new BigInteger(1, decodeBase64(paramArrayOfByte));
  }

  public static byte[] encodeBase64(byte[] paramArrayOfByte)
  {
    return encodeBase64(paramArrayOfByte, false);
  }

  public static byte[] encodeBase64(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    return encodeBase64(paramArrayOfByte, paramBoolean, false);
  }

  public static byte[] encodeBase64(byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2)
  {
    return encodeBase64(paramArrayOfByte, paramBoolean1, paramBoolean2, 2147483647);
  }

  public static byte[] encodeBase64(byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return paramArrayOfByte;
    if (paramBoolean1);
    for (Base64 localBase64 = new Base64(paramBoolean2); ; localBase64 = new Base64(0, CHUNK_SEPARATOR, paramBoolean2))
    {
      long l = localBase64.getEncodedLength(paramArrayOfByte);
      if (l <= paramInt)
        break;
      throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + l + ") than the specified maximum size of " + paramInt);
    }
    return localBase64.encode(paramArrayOfByte);
  }

  public static byte[] encodeBase64Chunked(byte[] paramArrayOfByte)
  {
    return encodeBase64(paramArrayOfByte, true);
  }

  public static String encodeBase64String(byte[] paramArrayOfByte)
  {
    return StringUtils.newStringUtf8(encodeBase64(paramArrayOfByte, false));
  }

  public static byte[] encodeBase64URLSafe(byte[] paramArrayOfByte)
  {
    return encodeBase64(paramArrayOfByte, false, true);
  }

  public static String encodeBase64URLSafeString(byte[] paramArrayOfByte)
  {
    return StringUtils.newStringUtf8(encodeBase64(paramArrayOfByte, false, true));
  }

  public static byte[] encodeInteger(BigInteger paramBigInteger)
  {
    if (paramBigInteger == null)
      throw new NullPointerException("encodeInteger called with null parameter");
    return encodeBase64(toIntegerBytes(paramBigInteger), false);
  }

  @Deprecated
  public static boolean isArrayByteBase64(byte[] paramArrayOfByte)
  {
    return isBase64(paramArrayOfByte);
  }

  public static boolean isBase64(byte paramByte)
  {
    return (paramByte == 61) || ((paramByte >= 0) && (paramByte < DECODE_TABLE.length) && (DECODE_TABLE[paramByte] != -1));
  }

  public static boolean isBase64(String paramString)
  {
    return isBase64(StringUtils.getBytesUtf8(paramString));
  }

  public static boolean isBase64(byte[] paramArrayOfByte)
  {
    for (int i = 0; ; i++)
    {
      boolean bool2;
      if (i >= paramArrayOfByte.length)
        bool2 = true;
      boolean bool1;
      do
      {
        return bool2;
        if (isBase64(paramArrayOfByte[i]))
          break;
        bool1 = isWhiteSpace(paramArrayOfByte[i]);
        bool2 = false;
      }
      while (!bool1);
    }
  }

  static byte[] toIntegerBytes(BigInteger paramBigInteger)
  {
    int i = 7 + paramBigInteger.bitLength() >> 3 << 3;
    byte[] arrayOfByte1 = paramBigInteger.toByteArray();
    if ((paramBigInteger.bitLength() % 8 != 0) && (1 + paramBigInteger.bitLength() / 8 == i / 8))
      return arrayOfByte1;
    int j = arrayOfByte1.length;
    int k = paramBigInteger.bitLength() % 8;
    int m = 0;
    if (k == 0)
    {
      m = 1;
      j--;
    }
    int n = i / 8 - j;
    byte[] arrayOfByte2 = new byte[i / 8];
    System.arraycopy(arrayOfByte1, m, arrayOfByte2, n, j);
    return arrayOfByte2;
  }

  void decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BaseNCodec.Context paramContext)
  {
    if (paramContext.eof)
      return;
    if (paramInt2 < 0)
      paramContext.eof = true;
    int i = 0;
    byte[] arrayOfByte2;
    while (true)
    {
      if (i >= paramInt2);
      byte[] arrayOfByte1;
      int j;
      int k;
      while ((paramContext.eof) && (paramContext.modulus != 0))
      {
        arrayOfByte2 = ensureBufferSize(this.decodeSize, paramContext);
        switch (paramContext.modulus)
        {
        case 1:
        default:
          throw new IllegalStateException("Impossible modulus " + paramContext.modulus);
          arrayOfByte1 = ensureBufferSize(this.decodeSize, paramContext);
          j = paramInt1 + 1;
          k = paramArrayOfByte[paramInt1];
          if (k != 61)
            break label155;
          paramContext.eof = true;
        case 2:
        case 3:
        }
      }
      label155: if ((k >= 0) && (k < DECODE_TABLE.length))
      {
        int m = DECODE_TABLE[k];
        if (m >= 0)
        {
          paramContext.modulus = ((1 + paramContext.modulus) % 4);
          paramContext.ibitWorkArea = (m + (paramContext.ibitWorkArea << 6));
          if (paramContext.modulus == 0)
          {
            int n = paramContext.pos;
            paramContext.pos = (n + 1);
            arrayOfByte1[n] = ((byte)(paramContext.ibitWorkArea >> 16));
            int i1 = paramContext.pos;
            paramContext.pos = (i1 + 1);
            arrayOfByte1[i1] = ((byte)(paramContext.ibitWorkArea >> 8));
            int i2 = paramContext.pos;
            paramContext.pos = (i2 + 1);
            arrayOfByte1[i2] = ((byte)paramContext.ibitWorkArea);
          }
        }
      }
      i++;
      paramInt1 = j;
    }
    paramContext.ibitWorkArea >>= 4;
    int i5 = paramContext.pos;
    paramContext.pos = (i5 + 1);
    arrayOfByte2[i5] = ((byte)paramContext.ibitWorkArea);
    return;
    paramContext.ibitWorkArea >>= 2;
    int i3 = paramContext.pos;
    paramContext.pos = (i3 + 1);
    arrayOfByte2[i3] = ((byte)(paramContext.ibitWorkArea >> 8));
    int i4 = paramContext.pos;
    paramContext.pos = (i4 + 1);
    arrayOfByte2[i4] = ((byte)paramContext.ibitWorkArea);
  }

  void encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BaseNCodec.Context paramContext)
  {
    if (paramContext.eof);
    while (true)
    {
      return;
      if (paramInt2 < 0)
      {
        paramContext.eof = true;
        if ((paramContext.modulus != 0) || (this.lineLength != 0))
        {
          byte[] arrayOfByte2 = ensureBufferSize(this.encodeSize, paramContext);
          int i3 = paramContext.pos;
          switch (paramContext.modulus)
          {
          default:
            throw new IllegalStateException("Impossible modulus " + paramContext.modulus);
          case 1:
            int i8 = paramContext.pos;
            paramContext.pos = (i8 + 1);
            arrayOfByte2[i8] = this.encodeTable[(0x3F & paramContext.ibitWorkArea >> 2)];
            int i9 = paramContext.pos;
            paramContext.pos = (i9 + 1);
            arrayOfByte2[i9] = this.encodeTable[(0x3F & paramContext.ibitWorkArea << 4)];
            if (this.encodeTable == STANDARD_ENCODE_TABLE)
            {
              int i10 = paramContext.pos;
              paramContext.pos = (i10 + 1);
              arrayOfByte2[i10] = 61;
              int i11 = paramContext.pos;
              paramContext.pos = (i11 + 1);
              arrayOfByte2[i11] = 61;
            }
            break;
          case 0:
          case 2:
          }
          while (true)
          {
            paramContext.currentLinePos += paramContext.pos - i3;
            if ((this.lineLength <= 0) || (paramContext.currentLinePos <= 0))
              break;
            System.arraycopy(this.lineSeparator, 0, arrayOfByte2, paramContext.pos, this.lineSeparator.length);
            paramContext.pos += this.lineSeparator.length;
            return;
            int i4 = paramContext.pos;
            paramContext.pos = (i4 + 1);
            arrayOfByte2[i4] = this.encodeTable[(0x3F & paramContext.ibitWorkArea >> 10)];
            int i5 = paramContext.pos;
            paramContext.pos = (i5 + 1);
            arrayOfByte2[i5] = this.encodeTable[(0x3F & paramContext.ibitWorkArea >> 4)];
            int i6 = paramContext.pos;
            paramContext.pos = (i6 + 1);
            arrayOfByte2[i6] = this.encodeTable[(0x3F & paramContext.ibitWorkArea << 2)];
            if (this.encodeTable == STANDARD_ENCODE_TABLE)
            {
              int i7 = paramContext.pos;
              paramContext.pos = (i7 + 1);
              arrayOfByte2[i7] = 61;
            }
          }
        }
      }
      else
      {
        int i = 0;
        while (i < paramInt2)
        {
          byte[] arrayOfByte1 = ensureBufferSize(this.encodeSize, paramContext);
          paramContext.modulus = ((1 + paramContext.modulus) % 3);
          int j = paramInt1 + 1;
          int k = paramArrayOfByte[paramInt1];
          if (k < 0)
            k += 256;
          paramContext.ibitWorkArea = (k + (paramContext.ibitWorkArea << 8));
          if (paramContext.modulus == 0)
          {
            int m = paramContext.pos;
            paramContext.pos = (m + 1);
            arrayOfByte1[m] = this.encodeTable[(0x3F & paramContext.ibitWorkArea >> 18)];
            int n = paramContext.pos;
            paramContext.pos = (n + 1);
            arrayOfByte1[n] = this.encodeTable[(0x3F & paramContext.ibitWorkArea >> 12)];
            int i1 = paramContext.pos;
            paramContext.pos = (i1 + 1);
            arrayOfByte1[i1] = this.encodeTable[(0x3F & paramContext.ibitWorkArea >> 6)];
            int i2 = paramContext.pos;
            paramContext.pos = (i2 + 1);
            arrayOfByte1[i2] = this.encodeTable[(0x3F & paramContext.ibitWorkArea)];
            paramContext.currentLinePos = (4 + paramContext.currentLinePos);
            if ((this.lineLength > 0) && (this.lineLength <= paramContext.currentLinePos))
            {
              System.arraycopy(this.lineSeparator, 0, arrayOfByte1, paramContext.pos, this.lineSeparator.length);
              paramContext.pos += this.lineSeparator.length;
              paramContext.currentLinePos = 0;
            }
          }
          i++;
          paramInt1 = j;
        }
      }
    }
  }

  protected boolean isInAlphabet(byte paramByte)
  {
    return (paramByte >= 0) && (paramByte < this.decodeTable.length) && (this.decodeTable[paramByte] != -1);
  }

  public boolean isUrlSafe()
  {
    return this.encodeTable == URL_SAFE_ENCODE_TABLE;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary.Base64
 * JD-Core Version:    0.6.2
 */