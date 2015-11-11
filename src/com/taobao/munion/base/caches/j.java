package com.taobao.munion.base.caches;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class j
{
  public static final int a = 128;
  public static final long b = 300000L;
  public static final long c = 2592000000L;

  public static i a(int paramInt, i parami, FileChannel paramFileChannel)
  {
    switch (paramInt)
    {
    default:
      return parami;
    case 4:
      long l2 = System.currentTimeMillis();
      if (parami.a() == 0L)
        parami.a(l2 + 300000L);
      break;
    case 2:
    case 1:
    case 3:
    }
    try
    {
      parami.b(paramFileChannel.size());
      label66: a(parami, paramFileChannel);
      return parami;
      long l1 = System.currentTimeMillis();
      if (parami.a() == 0L)
        parami.a(l1 + 300000L);
      a(parami, paramFileChannel);
      return parami;
      a(parami, paramFileChannel);
      return parami;
      parami.a(false);
      a(parami, paramFileChannel);
      return parami;
    }
    catch (IOException localIOException)
    {
      break label66;
    }
  }

  // ERROR //
  private static i a(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 56	java/lang/String:length	()I
    //   4: sipush 128
    //   7: if_icmple +36 -> 43
    //   10: aload_0
    //   11: bipush 13
    //   13: invokevirtual 60	java/lang/String:charAt	(I)C
    //   16: bipush 126
    //   18: if_icmpne +25 -> 43
    //   21: aload_0
    //   22: bipush 27
    //   24: invokevirtual 60	java/lang/String:charAt	(I)C
    //   27: bipush 126
    //   29: if_icmpne +14 -> 43
    //   32: aload_0
    //   33: bipush 60
    //   35: invokevirtual 60	java/lang/String:charAt	(I)C
    //   38: bipush 126
    //   40: if_icmpeq +5 -> 45
    //   43: aconst_null
    //   44: areturn
    //   45: new 29	com/taobao/munion/base/caches/i
    //   48: dup
    //   49: invokespecial 61	com/taobao/munion/base/caches/i:<init>	()V
    //   52: astore_1
    //   53: aload_0
    //   54: ldc 63
    //   56: invokevirtual 67	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   59: astore_2
    //   60: bipush 7
    //   62: aload_2
    //   63: arraylength
    //   64: if_icmpne -21 -> 43
    //   67: aload_1
    //   68: aload_2
    //   69: iconst_0
    //   70: aaload
    //   71: invokestatic 73	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   74: invokevirtual 34	com/taobao/munion/base/caches/i:a	(J)V
    //   77: aload_1
    //   78: aload_2
    //   79: iconst_1
    //   80: aaload
    //   81: invokestatic 73	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   84: invokevirtual 75	com/taobao/munion/base/caches/i:c	(J)V
    //   87: aload_1
    //   88: aload_2
    //   89: iconst_2
    //   90: aaload
    //   91: invokevirtual 78	com/taobao/munion/base/caches/i:a	(Ljava/lang/String;)V
    //   94: aload_1
    //   95: aload_2
    //   96: iconst_3
    //   97: aaload
    //   98: invokevirtual 81	com/taobao/munion/base/caches/i:e	(Ljava/lang/String;)V
    //   101: aload_1
    //   102: aload_2
    //   103: iconst_4
    //   104: aaload
    //   105: invokevirtual 83	com/taobao/munion/base/caches/i:c	(Ljava/lang/String;)V
    //   108: aload_1
    //   109: aload_2
    //   110: iconst_5
    //   111: aaload
    //   112: invokevirtual 86	com/taobao/munion/base/caches/i:d	(Ljava/lang/String;)V
    //   115: aload_1
    //   116: aload_2
    //   117: bipush 6
    //   119: aaload
    //   120: invokevirtual 88	com/taobao/munion/base/caches/i:b	(Ljava/lang/String;)V
    //   123: aload_1
    //   124: areturn
    //   125: astore_3
    //   126: aload_3
    //   127: invokevirtual 91	java/lang/NumberFormatException:printStackTrace	()V
    //   130: aconst_null
    //   131: areturn
    //   132: astore 4
    //   134: aload 4
    //   136: invokevirtual 91	java/lang/NumberFormatException:printStackTrace	()V
    //   139: aconst_null
    //   140: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   67	77	125	java/lang/NumberFormatException
    //   77	87	132	java/lang/NumberFormatException
  }

  public static i a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      i locali = a(new String(paramArrayOfByte, paramInt1, paramInt2, "UTF-8"));
      return locali;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
    return null;
  }

  private static void a(i parami, FileChannel paramFileChannel)
  {
    System.currentTimeMillis();
    byte[] arrayOfByte = parami.f();
    if (arrayOfByte == null)
      return;
    ByteBuffer localByteBuffer = ByteBuffer.allocate(1 + arrayOfByte.length);
    localByteBuffer.put(arrayOfByte);
    localByteBuffer.put((byte)10);
    localByteBuffer.position(0);
    try
    {
      paramFileChannel.write(localByteBuffer, parami.e());
      return;
    }
    catch (IOException localIOException)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.caches.j
 * JD-Core Version:    0.6.2
 */