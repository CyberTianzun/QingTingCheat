package tv.cjump.jni;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.util.Log;
import java.lang.reflect.Field;

public class NativeBitmapFactory
{
  static Field nativeIntField = null;
  static boolean nativeLibLoaded = false;

  private static native Bitmap createBitmap(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);

  public static Bitmap createBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig)
  {
    return createBitmap(paramInt1, paramInt2, paramConfig, paramConfig.equals(Bitmap.Config.ARGB_8888));
  }

  public static Bitmap createBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean)
  {
    if ((!nativeLibLoaded) || (nativeIntField == null))
      return Bitmap.createBitmap(paramInt1, paramInt2, paramConfig);
    return createNativeBitmap(paramInt1, paramInt2, paramConfig, paramBoolean);
  }

  private static native Bitmap createBitmap19(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);

  private static Bitmap createNativeBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean)
  {
    int i = getNativeConfig(paramConfig);
    if (Build.VERSION.SDK_INT == 19)
      return createBitmap19(paramInt1, paramInt2, i, paramBoolean);
    return createBitmap(paramInt1, paramInt2, i, paramBoolean);
  }

  public static int getNativeConfig(Bitmap.Config paramConfig)
  {
    try
    {
      if (nativeIntField == null)
        return 0;
      int i = nativeIntField.getInt(paramConfig);
      return i;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return 0;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    return 0;
  }

  private static native boolean init();

  static void initField()
  {
    try
    {
      nativeIntField = Bitmap.Config.class.getDeclaredField("nativeInt");
      nativeIntField.setAccessible(true);
      return;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      nativeIntField = null;
      localNoSuchFieldException.printStackTrace();
    }
  }

  public static boolean isInNativeAlloc()
  {
    return (Build.VERSION.SDK_INT < 11) || ((nativeLibLoaded) && (nativeIntField != null));
  }

  public static void loadLibs()
  {
    if ((!DeviceUtils.isRealARMArch()) && (!DeviceUtils.isRealX86Arch()))
      nativeLibLoaded = false;
    while (nativeLibLoaded)
      return;
    try
    {
      if (Build.VERSION.SDK_INT >= 11)
        System.loadLibrary("ndkbitmap");
      for (nativeLibLoaded = true; ; nativeLibLoaded = false)
      {
        if (nativeLibLoaded)
        {
          if (init())
            break;
          release();
          nativeLibLoaded = false;
        }
        Log.e("NativeBitmapFactory", "loaded" + nativeLibLoaded);
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        nativeLibLoaded = false;
      }
    }
    catch (Error localError)
    {
      while (true)
      {
        localError.printStackTrace();
        nativeLibLoaded = false;
        continue;
        initField();
        if (!testLib())
        {
          release();
          nativeLibLoaded = false;
        }
      }
    }
  }

  public static void recycle(Bitmap paramBitmap)
  {
    paramBitmap.recycle();
  }

  private static native boolean release();

  public static void releaseLibs()
  {
    if (nativeLibLoaded)
      release();
    nativeIntField = null;
    nativeLibLoaded = false;
  }

  // ERROR //
  @android.annotation.SuppressLint({"NewApi"})
  private static boolean testLib()
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_0
    //   2: getstatic 12	tv/cjump/jni/NativeBitmapFactory:nativeIntField	Ljava/lang/reflect/Field;
    //   5: ifnonnull +9 -> 14
    //   8: iconst_0
    //   9: istore 11
    //   11: iload 11
    //   13: ireturn
    //   14: aconst_null
    //   15: astore_1
    //   16: iconst_2
    //   17: iconst_2
    //   18: getstatic 26	android/graphics/Bitmap$Config:ARGB_8888	Landroid/graphics/Bitmap$Config;
    //   21: iconst_1
    //   22: invokestatic 40	tv/cjump/jni/NativeBitmapFactory:createNativeBitmap	(IILandroid/graphics/Bitmap$Config;Z)Landroid/graphics/Bitmap;
    //   25: astore 7
    //   27: aload 7
    //   29: astore_3
    //   30: aload_3
    //   31: ifnull +135 -> 166
    //   34: aload_3
    //   35: invokevirtual 157	android/graphics/Bitmap:getWidth	()I
    //   38: iconst_2
    //   39: if_icmpne +127 -> 166
    //   42: aload_3
    //   43: invokevirtual 160	android/graphics/Bitmap:getHeight	()I
    //   46: iconst_2
    //   47: if_icmpne +119 -> 166
    //   50: iload_0
    //   51: ifeq +210 -> 261
    //   54: getstatic 51	android/os/Build$VERSION:SDK_INT	I
    //   57: bipush 17
    //   59: if_icmplt +15 -> 74
    //   62: aload_3
    //   63: invokevirtual 163	android/graphics/Bitmap:isPremultiplied	()Z
    //   66: ifne +8 -> 74
    //   69: aload_3
    //   70: iconst_1
    //   71: invokevirtual 166	android/graphics/Bitmap:setPremultiplied	(Z)V
    //   74: new 168	android/graphics/Canvas
    //   77: dup
    //   78: aload_3
    //   79: invokespecial 170	android/graphics/Canvas:<init>	(Landroid/graphics/Bitmap;)V
    //   82: astore 9
    //   84: new 172	android/graphics/Paint
    //   87: dup
    //   88: invokespecial 173	android/graphics/Paint:<init>	()V
    //   91: astore 10
    //   93: aload 10
    //   95: ldc 174
    //   97: invokevirtual 178	android/graphics/Paint:setColor	(I)V
    //   100: aload 10
    //   102: ldc 179
    //   104: invokevirtual 183	android/graphics/Paint:setTextSize	(F)V
    //   107: aload 9
    //   109: fconst_0
    //   110: fconst_0
    //   111: aload_3
    //   112: invokevirtual 157	android/graphics/Bitmap:getWidth	()I
    //   115: i2f
    //   116: aload_3
    //   117: invokevirtual 160	android/graphics/Bitmap:getHeight	()I
    //   120: i2f
    //   121: aload 10
    //   123: invokevirtual 187	android/graphics/Canvas:drawRect	(FFFFLandroid/graphics/Paint;)V
    //   126: aload 9
    //   128: ldc 189
    //   130: fconst_0
    //   131: fconst_0
    //   132: aload 10
    //   134: invokevirtual 193	android/graphics/Canvas:drawText	(Ljava/lang/String;FFLandroid/graphics/Paint;)V
    //   137: getstatic 51	android/os/Build$VERSION:SDK_INT	I
    //   140: bipush 17
    //   142: if_icmplt +119 -> 261
    //   145: aload_3
    //   146: invokevirtual 163	android/graphics/Bitmap:isPremultiplied	()Z
    //   149: istore 12
    //   151: iload 12
    //   153: istore 11
    //   155: aload_3
    //   156: ifnull -145 -> 11
    //   159: aload_3
    //   160: invokevirtual 149	android/graphics/Bitmap:recycle	()V
    //   163: iload 11
    //   165: ireturn
    //   166: iconst_0
    //   167: istore_0
    //   168: goto -118 -> 50
    //   171: astore 5
    //   173: ldc 116
    //   175: new 118	java/lang/StringBuilder
    //   178: dup
    //   179: invokespecial 119	java/lang/StringBuilder:<init>	()V
    //   182: ldc 195
    //   184: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: aload 5
    //   189: invokevirtual 196	java/lang/Exception:toString	()Ljava/lang/String;
    //   192: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   198: invokestatic 138	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   201: pop
    //   202: aload_1
    //   203: ifnull +7 -> 210
    //   206: aload_1
    //   207: invokevirtual 149	android/graphics/Bitmap:recycle	()V
    //   210: iconst_0
    //   211: ireturn
    //   212: astore 4
    //   214: aconst_null
    //   215: astore_3
    //   216: aload_3
    //   217: ifnull +7 -> 224
    //   220: aload_3
    //   221: invokevirtual 149	android/graphics/Bitmap:recycle	()V
    //   224: iconst_0
    //   225: ireturn
    //   226: astore_2
    //   227: aconst_null
    //   228: astore_3
    //   229: aload_3
    //   230: ifnull +7 -> 237
    //   233: aload_3
    //   234: invokevirtual 149	android/graphics/Bitmap:recycle	()V
    //   237: aload_2
    //   238: athrow
    //   239: astore_2
    //   240: goto -11 -> 229
    //   243: astore_2
    //   244: aload_1
    //   245: astore_3
    //   246: goto -17 -> 229
    //   249: astore 8
    //   251: goto -35 -> 216
    //   254: astore 5
    //   256: aload_3
    //   257: astore_1
    //   258: goto -85 -> 173
    //   261: iload_0
    //   262: istore 11
    //   264: goto -109 -> 155
    //
    // Exception table:
    //   from	to	target	type
    //   16	27	171	java/lang/Exception
    //   16	27	212	java/lang/Error
    //   16	27	226	finally
    //   34	50	239	finally
    //   54	74	239	finally
    //   74	151	239	finally
    //   173	202	243	finally
    //   34	50	249	java/lang/Error
    //   54	74	249	java/lang/Error
    //   74	151	249	java/lang/Error
    //   34	50	254	java/lang/Exception
    //   54	74	254	java/lang/Exception
    //   74	151	254	java/lang/Exception
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     tv.cjump.jni.NativeBitmapFactory
 * JD-Core Version:    0.6.2
 */