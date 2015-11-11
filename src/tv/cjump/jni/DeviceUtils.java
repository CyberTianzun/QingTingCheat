package tv.cjump.jni;

import android.os.Build;
import android.text.TextUtils;
import java.lang.reflect.Field;

public class DeviceUtils
{
  public static final String ABI_MIPS = "mips";
  public static final String ABI_X86 = "x86";
  private static final int EM_386 = 3;
  private static final int EM_AARCH64 = 183;
  private static final int EM_ARM = 40;
  private static final int EM_MIPS = 8;
  private static ARCH sArch = ARCH.Unknown;

  // ERROR //
  public static ARCH getMyCpuArch()
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: bipush 20
    //   5: newarray byte
    //   7: astore_1
    //   8: new 42	java/io/File
    //   11: dup
    //   12: invokestatic 48	android/os/Environment:getRootDirectory	()Ljava/io/File;
    //   15: ldc 50
    //   17: invokespecial 53	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   20: astore_2
    //   21: aload_2
    //   22: invokevirtual 57	java/io/File:canRead	()Z
    //   25: istore_3
    //   26: iload_3
    //   27: ifeq +120 -> 147
    //   30: new 59	java/io/RandomAccessFile
    //   33: dup
    //   34: aload_2
    //   35: ldc 61
    //   37: invokespecial 62	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   40: astore 5
    //   42: aload 5
    //   44: aload_1
    //   45: invokevirtual 66	java/io/RandomAccessFile:readFully	([B)V
    //   48: aload_1
    //   49: bipush 19
    //   51: baload
    //   52: bipush 8
    //   54: ishl
    //   55: aload_1
    //   56: bipush 18
    //   58: baload
    //   59: ior
    //   60: istore 12
    //   62: iload 12
    //   64: lookupswitch	default:+44->108, 3:+139->203, 8:+178->242, 40:+94->158, 183:+202->266
    //   109: fstore_1
    //   110: new 70	java/lang/StringBuilder
    //   113: dup
    //   114: invokespecial 71	java/lang/StringBuilder:<init>	()V
    //   117: ldc 73
    //   119: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: iload 12
    //   124: invokestatic 83	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   127: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   133: invokestatic 93	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   136: pop
    //   137: aload 5
    //   139: ifnull +8 -> 147
    //   142: aload 5
    //   144: invokevirtual 96	java/io/RandomAccessFile:close	()V
    //   147: getstatic 31	tv/cjump/jni/DeviceUtils:sArch	Ltv/cjump/jni/DeviceUtils$ARCH;
    //   150: astore 4
    //   152: ldc 2
    //   154: monitorexit
    //   155: aload 4
    //   157: areturn
    //   158: getstatic 99	tv/cjump/jni/DeviceUtils$ARCH:ARM	Ltv/cjump/jni/DeviceUtils$ARCH;
    //   161: putstatic 31	tv/cjump/jni/DeviceUtils:sArch	Ltv/cjump/jni/DeviceUtils$ARCH;
    //   164: goto -27 -> 137
    //   167: astore 10
    //   169: aload 10
    //   171: invokevirtual 102	java/io/FileNotFoundException:printStackTrace	()V
    //   174: aload 5
    //   176: ifnull -29 -> 147
    //   179: aload 5
    //   181: invokevirtual 96	java/io/RandomAccessFile:close	()V
    //   184: goto -37 -> 147
    //   187: astore 11
    //   189: aload 11
    //   191: invokevirtual 103	java/io/IOException:printStackTrace	()V
    //   194: goto -47 -> 147
    //   197: astore_0
    //   198: ldc 2
    //   200: monitorexit
    //   201: aload_0
    //   202: athrow
    //   203: getstatic 106	tv/cjump/jni/DeviceUtils$ARCH:X86	Ltv/cjump/jni/DeviceUtils$ARCH;
    //   206: putstatic 31	tv/cjump/jni/DeviceUtils:sArch	Ltv/cjump/jni/DeviceUtils$ARCH;
    //   209: goto -72 -> 137
    //   212: astore 8
    //   214: aload 8
    //   216: invokevirtual 103	java/io/IOException:printStackTrace	()V
    //   219: aload 5
    //   221: ifnull -74 -> 147
    //   224: aload 5
    //   226: invokevirtual 96	java/io/RandomAccessFile:close	()V
    //   229: goto -82 -> 147
    //   232: astore 9
    //   234: aload 9
    //   236: invokevirtual 103	java/io/IOException:printStackTrace	()V
    //   239: goto -92 -> 147
    //   242: getstatic 109	tv/cjump/jni/DeviceUtils$ARCH:MIPS	Ltv/cjump/jni/DeviceUtils$ARCH;
    //   245: putstatic 31	tv/cjump/jni/DeviceUtils:sArch	Ltv/cjump/jni/DeviceUtils$ARCH;
    //   248: goto -111 -> 137
    //   251: astore 6
    //   253: aload 5
    //   255: ifnull +8 -> 263
    //   258: aload 5
    //   260: invokevirtual 96	java/io/RandomAccessFile:close	()V
    //   263: aload 6
    //   265: athrow
    //   266: getstatic 112	tv/cjump/jni/DeviceUtils$ARCH:ARM64	Ltv/cjump/jni/DeviceUtils$ARCH;
    //   269: putstatic 31	tv/cjump/jni/DeviceUtils:sArch	Ltv/cjump/jni/DeviceUtils$ARCH;
    //   272: goto -135 -> 137
    //   275: astore 13
    //   277: aload 13
    //   279: invokevirtual 103	java/io/IOException:printStackTrace	()V
    //   282: goto -135 -> 147
    //   285: astore 7
    //   287: aload 7
    //   289: invokevirtual 103	java/io/IOException:printStackTrace	()V
    //   292: goto -29 -> 263
    //   295: astore 6
    //   297: aconst_null
    //   298: astore 5
    //   300: goto -47 -> 253
    //   303: astore 8
    //   305: aconst_null
    //   306: astore 5
    //   308: goto -94 -> 214
    //   311: astore 10
    //   313: aconst_null
    //   314: astore 5
    //   316: goto -147 -> 169
    //
    // Exception table:
    //   from	to	target	type
    //   42	62	167	java/io/FileNotFoundException
    //   108	137	167	java/io/FileNotFoundException
    //   158	164	167	java/io/FileNotFoundException
    //   203	209	167	java/io/FileNotFoundException
    //   242	248	167	java/io/FileNotFoundException
    //   266	272	167	java/io/FileNotFoundException
    //   179	184	187	java/io/IOException
    //   3	26	197	finally
    //   142	147	197	finally
    //   147	152	197	finally
    //   179	184	197	finally
    //   189	194	197	finally
    //   224	229	197	finally
    //   234	239	197	finally
    //   258	263	197	finally
    //   263	266	197	finally
    //   277	282	197	finally
    //   287	292	197	finally
    //   42	62	212	java/io/IOException
    //   108	137	212	java/io/IOException
    //   158	164	212	java/io/IOException
    //   203	209	212	java/io/IOException
    //   242	248	212	java/io/IOException
    //   266	272	212	java/io/IOException
    //   224	229	232	java/io/IOException
    //   42	62	251	finally
    //   108	137	251	finally
    //   158	164	251	finally
    //   169	174	251	finally
    //   203	209	251	finally
    //   214	219	251	finally
    //   242	248	251	finally
    //   266	272	251	finally
    //   142	147	275	java/io/IOException
    //   258	263	285	java/io/IOException
    //   30	42	295	finally
    //   30	42	303	java/io/IOException
    //   30	42	311	java/io/FileNotFoundException
  }

  public static String get_CPU_ABI()
  {
    return Build.CPU_ABI;
  }

  public static String get_CPU_ABI2()
  {
    try
    {
      Field localField = Build.class.getDeclaredField("CPU_ABI2");
      if (localField == null)
        return null;
      Object localObject = localField.get(null);
      if (!(localObject instanceof String))
        return null;
      String str = (String)localObject;
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static boolean isARMSimulatedByX86()
  {
    ARCH localARCH = getMyCpuArch();
    return (!supportX86()) && (ARCH.X86.equals(localARCH));
  }

  public static boolean isMagicBoxDevice()
  {
    String str1 = Build.MANUFACTURER;
    String str2 = Build.PRODUCT;
    return (str1.equalsIgnoreCase("MagicBox")) && (str2.equalsIgnoreCase("MagicBox"));
  }

  public static boolean isMiBox2Device()
  {
    String str1 = Build.MANUFACTURER;
    String str2 = Build.PRODUCT;
    return (str1.equalsIgnoreCase("Xiaomi")) && (str2.equalsIgnoreCase("dredd"));
  }

  public static boolean isProblemBoxDevice()
  {
    return (isMiBox2Device()) || (isMagicBoxDevice());
  }

  public static boolean isRealARMArch()
  {
    ARCH localARCH = getMyCpuArch();
    return ((supportABI("armeabi-v7a")) || (supportABI("armeabi"))) && (ARCH.ARM.equals(localARCH));
  }

  public static boolean isRealX86Arch()
  {
    ARCH localARCH = getMyCpuArch();
    return (supportABI("x86")) || (ARCH.X86.equals(localARCH));
  }

  public static boolean supportABI(String paramString)
  {
    String str = get_CPU_ABI();
    if ((!TextUtils.isEmpty(str)) && (str.equalsIgnoreCase(paramString)));
    while ((!TextUtils.isEmpty(get_CPU_ABI2())) && (str.equalsIgnoreCase(paramString)))
      return true;
    return false;
  }

  public static boolean supportMips()
  {
    return supportABI("mips");
  }

  public static boolean supportX86()
  {
    return supportABI("x86");
  }

  public static enum ARCH
  {
    static
    {
      ARM = new ARCH("ARM", 1);
      X86 = new ARCH("X86", 2);
      MIPS = new ARCH("MIPS", 3);
      ARM64 = new ARCH("ARM64", 4);
      ARCH[] arrayOfARCH = new ARCH[5];
      arrayOfARCH[0] = Unknown;
      arrayOfARCH[1] = ARM;
      arrayOfARCH[2] = X86;
      arrayOfARCH[3] = MIPS;
      arrayOfARCH[4] = ARM64;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     tv.cjump.jni.DeviceUtils
 * JD-Core Version:    0.6.2
 */