package fm.qingting.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CPUInfo
{
  public static final int FPU_NEON = 4;
  public static final int FPU_SOFT = 0;
  public static final int FPU_VFP = 1;
  public static final int FPU_VFPV3 = 2;
  private static String cpuArch;
  private static boolean cpuArchValid = false;
  private static int cpuFreq;
  private static boolean cpuFreqValid;
  private static int fpu;
  private static boolean fpuValid = false;

  static
  {
    cpuFreqValid = false;
  }

  // ERROR //
  public static String CPUArch()
  {
    // Byte code:
    //   0: getstatic 29	fm/qingting/utils/CPUInfo:cpuArchValid	Z
    //   3: ifne +64 -> 67
    //   6: ldc 38
    //   8: putstatic 40	fm/qingting/utils/CPUInfo:cpuArch	Ljava/lang/String;
    //   11: iconst_0
    //   12: istore_0
    //   13: new 42	java/util/Scanner
    //   16: dup
    //   17: new 44	java/io/BufferedReader
    //   20: dup
    //   21: new 46	java/io/FileReader
    //   24: dup
    //   25: ldc 48
    //   27: invokespecial 51	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   30: invokespecial 54	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   33: invokespecial 57	java/util/Scanner:<init>	(Ljava/lang/Readable;)V
    //   36: astore_1
    //   37: aload_1
    //   38: invokevirtual 61	java/util/Scanner:hasNext	()Z
    //   41: ifeq +14 -> 55
    //   44: iload_0
    //   45: ifeq +26 -> 71
    //   48: aload_1
    //   49: invokevirtual 64	java/util/Scanner:next	()Ljava/lang/String;
    //   52: putstatic 40	fm/qingting/utils/CPUInfo:cpuArch	Ljava/lang/String;
    //   55: aload_1
    //   56: ifnull +7 -> 63
    //   59: aload_1
    //   60: invokevirtual 67	java/util/Scanner:close	()V
    //   63: iconst_1
    //   64: putstatic 29	fm/qingting/utils/CPUInfo:cpuArchValid	Z
    //   67: getstatic 40	fm/qingting/utils/CPUInfo:cpuArch	Ljava/lang/String;
    //   70: areturn
    //   71: aload_1
    //   72: invokevirtual 64	java/util/Scanner:next	()Ljava/lang/String;
    //   75: ldc 69
    //   77: invokevirtual 75	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   80: istore 4
    //   82: iload 4
    //   84: ifeq -47 -> 37
    //   87: iconst_1
    //   88: istore_0
    //   89: goto -52 -> 37
    //   92: astore_2
    //   93: aconst_null
    //   94: astore_1
    //   95: aload_2
    //   96: invokevirtual 78	java/lang/Exception:printStackTrace	()V
    //   99: aload_1
    //   100: ifnull -37 -> 63
    //   103: aload_1
    //   104: invokevirtual 67	java/util/Scanner:close	()V
    //   107: goto -44 -> 63
    //   110: astore_3
    //   111: aconst_null
    //   112: astore_1
    //   113: aload_1
    //   114: ifnull +7 -> 121
    //   117: aload_1
    //   118: invokevirtual 67	java/util/Scanner:close	()V
    //   121: aload_3
    //   122: athrow
    //   123: astore_3
    //   124: goto -11 -> 113
    //   127: astore_2
    //   128: goto -33 -> 95
    //
    // Exception table:
    //   from	to	target	type
    //   13	37	92	java/lang/Exception
    //   13	37	110	finally
    //   37	44	123	finally
    //   48	55	123	finally
    //   71	82	123	finally
    //   95	99	123	finally
    //   37	44	127	java/lang/Exception
    //   48	55	127	java/lang/Exception
    //   71	82	127	java/lang/Exception
  }

  public static int CPUCost()
  {
    return CPUCost(android.os.Process.myPid());
  }

  public static int CPUCost(int paramInt)
  {
    while (true)
    {
      int j;
      try
      {
        java.lang.Process localProcess = Runtime.getRuntime().exec("top -d 1 -n 1");
        localProcess.waitFor();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
        Object localObject = localBufferedReader.readLine();
        if (localObject != null)
        {
          String[] arrayOfString = ((String)localObject).trim().split("\\s+");
          String str1 = arrayOfString[0];
          if (str1.equals(String.valueOf(paramInt)))
          {
            Log.d("CPU cost", str1 + '|' + (String)localObject);
            int i = arrayOfString.length;
            j = 0;
            if (j < i)
            {
              String str3 = arrayOfString[j];
              if (!str3.endsWith("%"))
                break label184;
              return Integer.parseInt(str3.substring(0, -1 + str3.length())) * CPUCurFreq() / 100;
            }
          }
          String str2 = localBufferedReader.readLine();
          localObject = str2;
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return 0;
      label184: j++;
    }
  }

  // ERROR //
  public static int CPUCurFreq()
  {
    // Byte code:
    //   0: iconst_0
    //   1: putstatic 178	fm/qingting/utils/CPUInfo:cpuFreq	I
    //   4: aconst_null
    //   5: astore_0
    //   6: new 42	java/util/Scanner
    //   9: dup
    //   10: new 44	java/io/BufferedReader
    //   13: dup
    //   14: new 46	java/io/FileReader
    //   17: dup
    //   18: ldc 180
    //   20: invokespecial 51	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   23: invokespecial 54	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   26: invokespecial 57	java/util/Scanner:<init>	(Ljava/lang/Readable;)V
    //   29: astore_1
    //   30: aload_1
    //   31: invokevirtual 183	java/util/Scanner:hasNextInt	()Z
    //   34: ifeq +10 -> 44
    //   37: aload_1
    //   38: invokevirtual 186	java/util/Scanner:nextInt	()I
    //   41: putstatic 178	fm/qingting/utils/CPUInfo:cpuFreq	I
    //   44: aload_1
    //   45: ifnull +7 -> 52
    //   48: aload_1
    //   49: invokevirtual 67	java/util/Scanner:close	()V
    //   52: getstatic 178	fm/qingting/utils/CPUInfo:cpuFreq	I
    //   55: ireturn
    //   56: astore_2
    //   57: aconst_null
    //   58: astore_1
    //   59: aload_2
    //   60: invokevirtual 78	java/lang/Exception:printStackTrace	()V
    //   63: aload_1
    //   64: ifnull -12 -> 52
    //   67: aload_1
    //   68: invokevirtual 67	java/util/Scanner:close	()V
    //   71: goto -19 -> 52
    //   74: astore_3
    //   75: aload_0
    //   76: ifnull +7 -> 83
    //   79: aload_0
    //   80: invokevirtual 67	java/util/Scanner:close	()V
    //   83: aload_3
    //   84: athrow
    //   85: astore_3
    //   86: aload_1
    //   87: astore_0
    //   88: goto -13 -> 75
    //   91: astore_2
    //   92: goto -33 -> 59
    //
    // Exception table:
    //   from	to	target	type
    //   6	30	56	java/lang/Exception
    //   6	30	74	finally
    //   30	44	85	finally
    //   59	63	85	finally
    //   30	44	91	java/lang/Exception
  }

  // ERROR //
  public static int CPUMaxFreq()
  {
    // Byte code:
    //   0: getstatic 27	fm/qingting/utils/CPUInfo:cpuFreqValid	Z
    //   3: ifne +60 -> 63
    //   6: ldc 188
    //   8: putstatic 178	fm/qingting/utils/CPUInfo:cpuFreq	I
    //   11: aconst_null
    //   12: astore_0
    //   13: new 42	java/util/Scanner
    //   16: dup
    //   17: new 44	java/io/BufferedReader
    //   20: dup
    //   21: new 46	java/io/FileReader
    //   24: dup
    //   25: ldc 190
    //   27: invokespecial 51	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   30: invokespecial 54	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   33: invokespecial 57	java/util/Scanner:<init>	(Ljava/lang/Readable;)V
    //   36: astore_1
    //   37: aload_1
    //   38: invokevirtual 183	java/util/Scanner:hasNextInt	()Z
    //   41: ifeq +10 -> 51
    //   44: aload_1
    //   45: invokevirtual 186	java/util/Scanner:nextInt	()I
    //   48: putstatic 178	fm/qingting/utils/CPUInfo:cpuFreq	I
    //   51: aload_1
    //   52: ifnull +7 -> 59
    //   55: aload_1
    //   56: invokevirtual 67	java/util/Scanner:close	()V
    //   59: iconst_1
    //   60: putstatic 27	fm/qingting/utils/CPUInfo:cpuFreqValid	Z
    //   63: getstatic 178	fm/qingting/utils/CPUInfo:cpuFreq	I
    //   66: ireturn
    //   67: astore_2
    //   68: aconst_null
    //   69: astore_1
    //   70: aload_2
    //   71: invokevirtual 78	java/lang/Exception:printStackTrace	()V
    //   74: aload_1
    //   75: ifnull -16 -> 59
    //   78: aload_1
    //   79: invokevirtual 67	java/util/Scanner:close	()V
    //   82: goto -23 -> 59
    //   85: astore_3
    //   86: aload_0
    //   87: ifnull +7 -> 94
    //   90: aload_0
    //   91: invokevirtual 67	java/util/Scanner:close	()V
    //   94: aload_3
    //   95: athrow
    //   96: astore_3
    //   97: aload_1
    //   98: astore_0
    //   99: goto -13 -> 86
    //   102: astore_2
    //   103: goto -33 -> 70
    //
    // Exception table:
    //   from	to	target	type
    //   13	37	67	java/lang/Exception
    //   13	37	85	finally
    //   37	51	96	finally
    //   70	74	96	finally
    //   37	51	102	java/lang/Exception
  }

  // ERROR //
  public static int FPU()
  {
    // Byte code:
    //   0: getstatic 25	fm/qingting/utils/CPUInfo:fpuValid	Z
    //   3: ifne +80 -> 83
    //   6: iconst_0
    //   7: putstatic 193	fm/qingting/utils/CPUInfo:fpu	I
    //   10: new 42	java/util/Scanner
    //   13: dup
    //   14: new 44	java/io/BufferedReader
    //   17: dup
    //   18: new 46	java/io/FileReader
    //   21: dup
    //   22: ldc 48
    //   24: invokespecial 51	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   27: invokespecial 54	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   30: invokespecial 57	java/util/Scanner:<init>	(Ljava/lang/Readable;)V
    //   33: astore_0
    //   34: aload_0
    //   35: invokevirtual 61	java/util/Scanner:hasNext	()Z
    //   38: ifeq +100 -> 138
    //   41: aload_0
    //   42: invokevirtual 64	java/util/Scanner:next	()Ljava/lang/String;
    //   45: astore_3
    //   46: aload_3
    //   47: ldc 195
    //   49: invokevirtual 75	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   52: ifeq +35 -> 87
    //   55: iconst_1
    //   56: getstatic 193	fm/qingting/utils/CPUInfo:fpu	I
    //   59: ior
    //   60: putstatic 193	fm/qingting/utils/CPUInfo:fpu	I
    //   63: goto -29 -> 34
    //   66: astore_2
    //   67: aload_2
    //   68: invokevirtual 78	java/lang/Exception:printStackTrace	()V
    //   71: aload_0
    //   72: ifnull +7 -> 79
    //   75: aload_0
    //   76: invokevirtual 67	java/util/Scanner:close	()V
    //   79: iconst_1
    //   80: putstatic 25	fm/qingting/utils/CPUInfo:fpuValid	Z
    //   83: getstatic 193	fm/qingting/utils/CPUInfo:fpu	I
    //   86: ireturn
    //   87: aload_3
    //   88: ldc 197
    //   90: invokevirtual 75	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   93: ifeq +25 -> 118
    //   96: iconst_2
    //   97: getstatic 193	fm/qingting/utils/CPUInfo:fpu	I
    //   100: ior
    //   101: putstatic 193	fm/qingting/utils/CPUInfo:fpu	I
    //   104: goto -70 -> 34
    //   107: astore_1
    //   108: aload_0
    //   109: ifnull +7 -> 116
    //   112: aload_0
    //   113: invokevirtual 67	java/util/Scanner:close	()V
    //   116: aload_1
    //   117: athrow
    //   118: aload_3
    //   119: ldc 199
    //   121: invokevirtual 75	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   124: ifeq -90 -> 34
    //   127: iconst_4
    //   128: getstatic 193	fm/qingting/utils/CPUInfo:fpu	I
    //   131: ior
    //   132: putstatic 193	fm/qingting/utils/CPUInfo:fpu	I
    //   135: goto -101 -> 34
    //   138: aload_0
    //   139: ifnull -60 -> 79
    //   142: aload_0
    //   143: invokevirtual 67	java/util/Scanner:close	()V
    //   146: goto -67 -> 79
    //   149: astore_1
    //   150: aconst_null
    //   151: astore_0
    //   152: goto -44 -> 108
    //   155: astore_2
    //   156: aconst_null
    //   157: astore_0
    //   158: goto -91 -> 67
    //
    // Exception table:
    //   from	to	target	type
    //   34	63	66	java/lang/Exception
    //   87	104	66	java/lang/Exception
    //   118	135	66	java/lang/Exception
    //   34	63	107	finally
    //   67	71	107	finally
    //   87	104	107	finally
    //   118	135	107	finally
    //   10	34	149	finally
    //   10	34	155	java/lang/Exception
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.CPUInfo
 * JD-Core Version:    0.6.2
 */