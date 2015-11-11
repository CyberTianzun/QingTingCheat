package fm.qingting.qtradio.manager;

import android.util.SparseIntArray;

public class IconManage
{
  private static SparseIntArray sResourcesCache = new SparseIntArray();

  // ERROR //
  public static int getNormalRes(int paramInt)
  {
    // Byte code:
    //   0: getstatic 15	fm/qingting/qtradio/manager/IconManage:sResourcesCache	Landroid/util/SparseIntArray;
    //   3: iload_0
    //   4: invokevirtual 29	android/util/SparseIntArray:get	(I)I
    //   7: istore_1
    //   8: iload_1
    //   9: ifeq +5 -> 14
    //   12: iload_1
    //   13: ireturn
    //   14: ldc 31
    //   16: new 33	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 34	java/lang/StringBuilder:<init>	()V
    //   23: ldc 36
    //   25: invokevirtual 40	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: iload_0
    //   29: invokevirtual 43	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   32: invokevirtual 47	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   35: invokevirtual 53	java/lang/Class:getDeclaredField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   38: aconst_null
    //   39: invokevirtual 59	java/lang/reflect/Field:getInt	(Ljava/lang/Object;)I
    //   42: istore 11
    //   44: iload 11
    //   46: istore_3
    //   47: getstatic 15	fm/qingting/qtradio/manager/IconManage:sResourcesCache	Landroid/util/SparseIntArray;
    //   50: iload_0
    //   51: iload_3
    //   52: invokevirtual 63	android/util/SparseIntArray:put	(II)V
    //   55: iload_3
    //   56: ireturn
    //   57: astore 4
    //   59: aload 4
    //   61: invokevirtual 66	java/lang/SecurityException:printStackTrace	()V
    //   64: iload_3
    //   65: ireturn
    //   66: astore 9
    //   68: ldc 67
    //   70: istore_3
    //   71: aload 9
    //   73: astore 10
    //   75: aload 10
    //   77: invokevirtual 68	java/lang/NoSuchFieldException:printStackTrace	()V
    //   80: iload_3
    //   81: ireturn
    //   82: astore 7
    //   84: ldc 67
    //   86: istore_3
    //   87: aload 7
    //   89: astore 8
    //   91: aload 8
    //   93: invokevirtual 69	java/lang/IllegalArgumentException:printStackTrace	()V
    //   96: iload_3
    //   97: ireturn
    //   98: astore 5
    //   100: ldc 67
    //   102: istore_3
    //   103: aload 5
    //   105: astore 6
    //   107: aload 6
    //   109: invokevirtual 70	java/lang/IllegalAccessException:printStackTrace	()V
    //   112: iload_3
    //   113: ireturn
    //   114: astore 6
    //   116: goto -9 -> 107
    //   119: astore 8
    //   121: goto -30 -> 91
    //   124: astore 10
    //   126: goto -51 -> 75
    //   129: astore_2
    //   130: ldc 67
    //   132: istore_3
    //   133: aload_2
    //   134: astore 4
    //   136: goto -77 -> 59
    //
    // Exception table:
    //   from	to	target	type
    //   47	55	57	java/lang/SecurityException
    //   14	44	66	java/lang/NoSuchFieldException
    //   14	44	82	java/lang/IllegalArgumentException
    //   14	44	98	java/lang/IllegalAccessException
    //   47	55	114	java/lang/IllegalAccessException
    //   47	55	119	java/lang/IllegalArgumentException
    //   47	55	124	java/lang/NoSuchFieldException
    //   14	44	129	java/lang/SecurityException
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.manager.IconManage
 * JD-Core Version:    0.6.2
 */