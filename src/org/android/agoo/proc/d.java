package org.android.agoo.proc;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.umeng.message.proguard.aN;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

public class d
{
  public static String a;
  public static final String b = "android";
  public static final String c = "libcockroach.so";
  private static final boolean d = false;
  private static final String e = "SEProtect-";
  private static final String f = "test-";
  private static final String g = "1.3.3";
  private static String h = "SEProtect-1.3.3";
  private static final String i = "cockroach";
  private static final String[] j;
  private static final String[] k;
  private static final String[] l;
  private static HashMap<String, String[]> m;
  private Context n = null;
  private File o = null;

  static
  {
    a = null;
    j = new String[] { "cc7adaf0aaeb510ee4a3d62fd4b2b12c99a9474bb52d879024482bff6c8e2e52" };
    k = new String[] { "2a10e0cdfb83ed0358321c09d0da1ce9f1d524060d8a19671580a599bcb9bcba" };
    l = new String[] { "8e5677fa96dc5dde287126c92487f6b29160799d5d4acfe4d7337adbf80e9f7f" };
    m = new HashMap();
    a = "lib" + h + ".so";
    m.put(a, j);
    m.put("android", k);
    m.put("libcockroach.so", l);
  }

  private d(Context paramContext)
  {
    this.n = paramContext;
    try
    {
      this.o = this.n.getFilesDir();
      return;
    }
    catch (Throwable localThrowable)
    {
    }
  }

  private static String a(Build paramBuild, String paramString)
  {
    try
    {
      String str = Build.class.getField(paramString).get(paramBuild).toString();
      return str;
    }
    catch (Throwable localThrowable)
    {
    }
    return "Unknown";
  }

  private a a(String paramString)
  {
    a locala = new a();
    File localFile = c(paramString);
    if ((localFile != null) && (localFile.exists()));
    try
    {
      System.load(localFile.getAbsolutePath());
      Log.i("SOManager", "Call System.load() by SOManager");
      locala.b(true);
      locala.a(true);
      return locala;
    }
    catch (Throwable localThrowable)
    {
    }
    return locala;
  }

  public static d a(Context paramContext)
  {
    if (paramContext != null)
      return new d(paramContext.getApplicationContext());
    return null;
  }

  // ERROR //
  private boolean a(String paramString, java.io.FileInputStream paramFileInputStream)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +9 -> 10
    //   4: iconst_0
    //   5: istore 13
    //   7: iload 13
    //   9: ireturn
    //   10: getstatic 68	org/android/agoo/proc/d:m	Ljava/util/HashMap;
    //   13: aload_1
    //   14: invokevirtual 164	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   17: checkcast 165	[Ljava/lang/String;
    //   20: astore 7
    //   22: aload_2
    //   23: ifnull +98 -> 121
    //   26: aload_2
    //   27: invokevirtual 171	java/io/FileInputStream:available	()I
    //   30: ifle +91 -> 121
    //   33: aload_2
    //   34: invokestatic 176	com/umeng/message/proguard/aN:c	(Ljava/io/InputStream;)Ljava/lang/String;
    //   37: astore 9
    //   39: aload 9
    //   41: ifnull +17 -> 58
    //   44: ldc 178
    //   46: aload 9
    //   48: invokevirtual 182	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   51: istore 10
    //   53: iload 10
    //   55: ifeq +13 -> 68
    //   58: aload_2
    //   59: ifnull +7 -> 66
    //   62: aload_2
    //   63: invokevirtual 185	java/io/FileInputStream:close	()V
    //   66: iconst_0
    //   67: ireturn
    //   68: iconst_0
    //   69: istore 11
    //   71: iload 11
    //   73: aload 7
    //   75: arraylength
    //   76: if_icmpge +45 -> 121
    //   79: aload 7
    //   81: iload 11
    //   83: aaload
    //   84: aload 9
    //   86: invokevirtual 182	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   89: istore 12
    //   91: iload 12
    //   93: ifeq +22 -> 115
    //   96: iconst_1
    //   97: istore 13
    //   99: aload_2
    //   100: ifnull -93 -> 7
    //   103: aload_2
    //   104: invokevirtual 185	java/io/FileInputStream:close	()V
    //   107: iload 13
    //   109: ireturn
    //   110: astore 14
    //   112: iload 13
    //   114: ireturn
    //   115: iinc 11 1
    //   118: goto -47 -> 71
    //   121: aload_2
    //   122: ifnull +7 -> 129
    //   125: aload_2
    //   126: invokevirtual 185	java/io/FileInputStream:close	()V
    //   129: iconst_0
    //   130: ireturn
    //   131: astore 5
    //   133: aload_2
    //   134: ifnull -5 -> 129
    //   137: aload_2
    //   138: invokevirtual 185	java/io/FileInputStream:close	()V
    //   141: goto -12 -> 129
    //   144: astore 6
    //   146: goto -17 -> 129
    //   149: astore_3
    //   150: aload_2
    //   151: ifnull +7 -> 158
    //   154: aload_2
    //   155: invokevirtual 185	java/io/FileInputStream:close	()V
    //   158: aload_3
    //   159: athrow
    //   160: astore 15
    //   162: goto -96 -> 66
    //   165: astore 8
    //   167: goto -38 -> 129
    //   170: astore 4
    //   172: goto -14 -> 158
    //
    // Exception table:
    //   from	to	target	type
    //   103	107	110	java/lang/Throwable
    //   10	22	131	java/lang/Throwable
    //   26	39	131	java/lang/Throwable
    //   44	53	131	java/lang/Throwable
    //   71	91	131	java/lang/Throwable
    //   137	141	144	java/lang/Throwable
    //   10	22	149	finally
    //   26	39	149	finally
    //   44	53	149	finally
    //   71	91	149	finally
    //   62	66	160	java/lang/Throwable
    //   125	129	165	java/lang/Throwable
    //   154	158	170	java/lang/Throwable
  }

  private boolean a(String paramString, byte[] paramArrayOfByte)
  {
    if (paramString == null);
    while (true)
    {
      return false;
      String[] arrayOfString = (String[])m.get(paramString);
      if ((paramArrayOfByte != null) && (arrayOfString != null))
      {
        String str = aN.a(paramArrayOfByte);
        if ((str != null) && (!"".equals(str)))
          for (int i1 = 0; i1 < arrayOfString.length; i1++)
            if (arrayOfString[i1].equals(str))
              return true;
      }
    }
  }

  private void b(String paramString)
  {
    c(paramString);
  }

  // ERROR //
  private File c(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 95	org/android/agoo/proc/d:o	Ljava/io/File;
    //   4: ifnonnull +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: aload_0
    //   10: getfield 95	org/android/agoo/proc/d:o	Ljava/io/File;
    //   13: invokevirtual 132	java/io/File:exists	()Z
    //   16: ifne +11 -> 27
    //   19: aload_0
    //   20: getfield 95	org/android/agoo/proc/d:o	Ljava/io/File;
    //   23: invokevirtual 194	java/io/File:mkdir	()Z
    //   26: pop
    //   27: aload_0
    //   28: getfield 95	org/android/agoo/proc/d:o	Ljava/io/File;
    //   31: invokevirtual 132	java/io/File:exists	()Z
    //   34: ifeq -27 -> 7
    //   37: new 128	java/io/File
    //   40: dup
    //   41: aload_0
    //   42: getfield 95	org/android/agoo/proc/d:o	Ljava/io/File;
    //   45: aload_1
    //   46: invokespecial 197	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   49: astore_2
    //   50: aload_2
    //   51: invokevirtual 132	java/io/File:exists	()Z
    //   54: ifeq +29 -> 83
    //   57: aload_0
    //   58: aload_1
    //   59: new 167	java/io/FileInputStream
    //   62: dup
    //   63: aload_2
    //   64: invokespecial 200	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   67: invokespecial 202	org/android/agoo/proc/d:a	(Ljava/lang/String;Ljava/io/FileInputStream;)Z
    //   70: ifeq +13 -> 83
    //   73: ldc 204
    //   75: ldc 206
    //   77: invokestatic 150	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   80: pop
    //   81: aload_2
    //   82: areturn
    //   83: ldc 204
    //   85: ldc 208
    //   87: invokestatic 150	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   90: pop
    //   91: aload_1
    //   92: new 104	android/os/Build
    //   95: dup
    //   96: invokespecial 209	android/os/Build:<init>	()V
    //   99: ldc 211
    //   101: invokestatic 213	org/android/agoo/proc/d:a	(Landroid/os/Build;Ljava/lang/String;)Ljava/lang/String;
    //   104: invokestatic 218	org/android/agoo/proc/c:a	(Ljava/lang/String;Ljava/lang/String;)[B
    //   107: astore 4
    //   109: aload 4
    //   111: ifnull +208 -> 319
    //   114: aload_0
    //   115: aload_1
    //   116: aload 4
    //   118: invokespecial 220	org/android/agoo/proc/d:a	(Ljava/lang/String;[B)Z
    //   121: ifne +13 -> 134
    //   124: ldc 204
    //   126: ldc 222
    //   128: invokestatic 150	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   131: pop
    //   132: aconst_null
    //   133: areturn
    //   134: new 128	java/io/File
    //   137: dup
    //   138: aload_0
    //   139: getfield 95	org/android/agoo/proc/d:o	Ljava/io/File;
    //   142: aload_1
    //   143: invokespecial 197	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   146: astore 6
    //   148: aload 6
    //   150: ifnull -143 -> 7
    //   153: aload 6
    //   155: invokevirtual 225	java/io/File:getParentFile	()Ljava/io/File;
    //   158: invokevirtual 228	java/io/File:canWrite	()Z
    //   161: ifeq -154 -> 7
    //   164: aload 6
    //   166: invokevirtual 132	java/io/File:exists	()Z
    //   169: ifeq +9 -> 178
    //   172: aload 6
    //   174: invokevirtual 231	java/io/File:delete	()Z
    //   177: pop
    //   178: new 233	java/io/FileOutputStream
    //   181: dup
    //   182: aload 6
    //   184: invokespecial 234	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   187: astore 7
    //   189: aload 7
    //   191: aload 4
    //   193: iconst_0
    //   194: aload 4
    //   196: arraylength
    //   197: invokevirtual 238	java/io/FileOutputStream:write	([BII)V
    //   200: aload_0
    //   201: aload_1
    //   202: new 167	java/io/FileInputStream
    //   205: dup
    //   206: aload 6
    //   208: invokespecial 200	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   211: invokespecial 202	org/android/agoo/proc/d:a	(Ljava/lang/String;Ljava/io/FileInputStream;)Z
    //   214: ifeq +24 -> 238
    //   217: ldc 204
    //   219: ldc 206
    //   221: invokestatic 150	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   224: pop
    //   225: aload 7
    //   227: ifnull +8 -> 235
    //   230: aload 7
    //   232: invokevirtual 239	java/io/FileOutputStream:close	()V
    //   235: aload 6
    //   237: areturn
    //   238: aload 6
    //   240: invokevirtual 132	java/io/File:exists	()Z
    //   243: ifeq +17 -> 260
    //   246: aload 6
    //   248: invokevirtual 231	java/io/File:delete	()Z
    //   251: pop
    //   252: ldc 204
    //   254: ldc 241
    //   256: invokestatic 150	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   259: pop
    //   260: aload 7
    //   262: ifnull -255 -> 7
    //   265: aload 7
    //   267: invokevirtual 239	java/io/FileOutputStream:close	()V
    //   270: aconst_null
    //   271: areturn
    //   272: astore 13
    //   274: aconst_null
    //   275: areturn
    //   276: astore 19
    //   278: aconst_null
    //   279: astore 9
    //   281: aload 9
    //   283: ifnull -276 -> 7
    //   286: aload 9
    //   288: invokevirtual 239	java/io/FileOutputStream:close	()V
    //   291: aconst_null
    //   292: areturn
    //   293: astore 10
    //   295: aconst_null
    //   296: areturn
    //   297: astore 18
    //   299: aconst_null
    //   300: astore 7
    //   302: aload 18
    //   304: astore 11
    //   306: aload 7
    //   308: ifnull +8 -> 316
    //   311: aload 7
    //   313: invokevirtual 239	java/io/FileOutputStream:close	()V
    //   316: aload 11
    //   318: athrow
    //   319: ldc 243
    //   321: ldc 245
    //   323: invokestatic 150	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   326: pop
    //   327: aconst_null
    //   328: areturn
    //   329: astore 17
    //   331: goto -96 -> 235
    //   334: astore 12
    //   336: goto -20 -> 316
    //   339: astore 11
    //   341: goto -35 -> 306
    //   344: astore 8
    //   346: aload 7
    //   348: astore 9
    //   350: goto -69 -> 281
    //   353: astore_3
    //   354: goto -263 -> 91
    //
    // Exception table:
    //   from	to	target	type
    //   265	270	272	java/lang/Throwable
    //   178	189	276	java/lang/Throwable
    //   286	291	293	java/lang/Throwable
    //   178	189	297	finally
    //   230	235	329	java/lang/Throwable
    //   311	316	334	java/lang/Throwable
    //   189	225	339	finally
    //   238	260	339	finally
    //   189	225	344	java/lang/Throwable
    //   238	260	344	java/lang/Throwable
    //   50	81	353	java/io/FileNotFoundException
    //   83	91	353	java/io/FileNotFoundException
  }

  public a a()
  {
    return a(a);
  }

  public a b()
  {
    return a("libcockroach.so");
  }

  public void c()
  {
    b("android");
  }

  public static class a
  {
    private boolean a = false;
    private boolean b = false;

    public void a(boolean paramBoolean)
    {
      this.a = paramBoolean;
    }

    public boolean a()
    {
      return this.a;
    }

    public void b(boolean paramBoolean)
    {
      this.b = paramBoolean;
    }

    public boolean b()
    {
      return this.b;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.proc.d
 * JD-Core Version:    0.6.2
 */