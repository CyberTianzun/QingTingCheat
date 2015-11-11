package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.io.File;
import u.aly.bi;
import u.aly.bj;
import u.aly.bv;

public final class f
{
  private static f a = new f();
  private static Context b;
  private static String c;
  private static long d = 0L;
  private static long e = 0L;
  private static final String f = "age";
  private static final String g = "sex";
  private static final String h = "id";
  private static final String i = "url";
  private static final String j = "mobclick_agent_user_";
  private static final String k = "mobclick_agent_online_setting_";
  private static final String l = "mobclick_agent_header_";
  private static final String m = "mobclick_agent_update_";
  private static final String n = "mobclick_agent_state_";
  private static final String o = "mobclick_agent_cached_";
  private static final String p = "mobclick_agent_sealed_";

  public static f a(Context paramContext)
  {
    if (b == null)
      b = paramContext.getApplicationContext();
    if (c == null)
      c = paramContext.getPackageName();
    return a;
  }

  private static boolean a(File paramFile)
  {
    long l1 = paramFile.length();
    return (paramFile.exists()) && (l1 > e);
  }

  private SharedPreferences k()
  {
    return b.getSharedPreferences("mobclick_agent_user_" + c, 0);
  }

  private String l()
  {
    return "mobclick_agent_header_" + c;
  }

  private String m()
  {
    return "mobclick_agent_cached_" + c + bi.c(b);
  }

  private String n()
  {
    return "mobclick_agent_sealed_" + c;
  }

  public void a(int paramInt1, int paramInt2)
  {
    SharedPreferences.Editor localEditor = a(b).g().edit();
    localEditor.putInt("umeng_net_report_policy", paramInt1);
    localEditor.putLong("umeng_net_report_interval", paramInt2);
    localEditor.commit();
  }

  public void a(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    SharedPreferences.Editor localEditor = k().edit();
    if (!TextUtils.isEmpty(paramString1))
      localEditor.putString("id", paramString1);
    if (!TextUtils.isEmpty(paramString2))
      localEditor.putString("url", paramString2);
    if (paramInt1 > 0)
      localEditor.putInt("age", paramInt1);
    localEditor.putInt("sex", paramInt2);
    localEditor.commit();
  }

  public void a(byte[] paramArrayOfByte)
  {
    String str = m();
    try
    {
      bv.a(new File(b.getFilesDir(), str), paramArrayOfByte);
      return;
    }
    catch (Exception localException)
    {
      bj.b("MobclickAgent", localException.getMessage());
    }
  }

  public int[] a()
  {
    SharedPreferences localSharedPreferences = g();
    int[] arrayOfInt = new int[2];
    if (localSharedPreferences.getInt("umeng_net_report_policy", -1) != -1)
    {
      arrayOfInt[0] = localSharedPreferences.getInt("umeng_net_report_policy", 1);
      arrayOfInt[1] = ((int)localSharedPreferences.getLong("umeng_net_report_interval", 0L));
      return arrayOfInt;
    }
    arrayOfInt[0] = localSharedPreferences.getInt("umeng_local_report_policy", 1);
    arrayOfInt[1] = ((int)localSharedPreferences.getLong("umeng_local_report_interval", 0L));
    return arrayOfInt;
  }

  public void b(byte[] paramArrayOfByte)
  {
    try
    {
      bv.a(new File(b.getFilesDir(), n()), paramArrayOfByte);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  // ERROR //
  public byte[] b()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 156	com/umeng/analytics/f:m	()Ljava/lang/String;
    //   4: astore_1
    //   5: new 82	java/io/File
    //   8: dup
    //   9: getstatic 67	com/umeng/analytics/f:b	Landroid/content/Context;
    //   12: invokevirtual 160	android/content/Context:getFilesDir	()Ljava/io/File;
    //   15: aload_1
    //   16: invokespecial 163	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   19: astore_2
    //   20: aload_2
    //   21: invokestatic 199	com/umeng/analytics/f:a	(Ljava/io/File;)Z
    //   24: ifeq +10 -> 34
    //   27: aload_2
    //   28: invokevirtual 202	java/io/File:delete	()Z
    //   31: pop
    //   32: aconst_null
    //   33: areturn
    //   34: aload_2
    //   35: invokevirtual 90	java/io/File:exists	()Z
    //   38: ifeq -6 -> 32
    //   41: getstatic 67	com/umeng/analytics/f:b	Landroid/content/Context;
    //   44: aload_1
    //   45: invokevirtual 206	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   48: astore 7
    //   50: aload 7
    //   52: astore 4
    //   54: aload 4
    //   56: invokestatic 209	u/aly/bv:b	(Ljava/io/InputStream;)[B
    //   59: astore 8
    //   61: aload 4
    //   63: invokestatic 212	u/aly/bv:c	(Ljava/io/InputStream;)V
    //   66: aload 8
    //   68: areturn
    //   69: astore 6
    //   71: aconst_null
    //   72: astore 4
    //   74: aload 6
    //   76: invokevirtual 196	java/lang/Exception:printStackTrace	()V
    //   79: aload 4
    //   81: invokestatic 212	u/aly/bv:c	(Ljava/io/InputStream;)V
    //   84: aconst_null
    //   85: areturn
    //   86: astore_3
    //   87: aconst_null
    //   88: astore 4
    //   90: aload_3
    //   91: astore 5
    //   93: aload 4
    //   95: invokestatic 212	u/aly/bv:c	(Ljava/io/InputStream;)V
    //   98: aload 5
    //   100: athrow
    //   101: astore 5
    //   103: goto -10 -> 93
    //   106: astore 6
    //   108: goto -34 -> 74
    //
    // Exception table:
    //   from	to	target	type
    //   41	50	69	java/lang/Exception
    //   41	50	86	finally
    //   54	61	101	finally
    //   74	79	101	finally
    //   54	61	106	java/lang/Exception
  }

  public Object[] b(Context paramContext)
  {
    SharedPreferences localSharedPreferences = k();
    Object[] arrayOfObject = new Object[4];
    if (localSharedPreferences.contains("id"))
      arrayOfObject[0] = localSharedPreferences.getString("id", null);
    if (localSharedPreferences.contains("url"))
      arrayOfObject[1] = localSharedPreferences.getString("url", null);
    if (localSharedPreferences.contains("age"))
      arrayOfObject[2] = Integer.valueOf(localSharedPreferences.getInt("age", -1));
    if (localSharedPreferences.contains("sex"))
      arrayOfObject[3] = Integer.valueOf(localSharedPreferences.getInt("sex", -1));
    return arrayOfObject;
  }

  public void c()
  {
    b.deleteFile(l());
    b.deleteFile(m());
  }

  // ERROR //
  public byte[] d()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 193	com/umeng/analytics/f:n	()Ljava/lang/String;
    //   4: astore_1
    //   5: new 82	java/io/File
    //   8: dup
    //   9: getstatic 67	com/umeng/analytics/f:b	Landroid/content/Context;
    //   12: invokevirtual 160	android/content/Context:getFilesDir	()Ljava/io/File;
    //   15: aload_1
    //   16: invokespecial 163	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   19: astore_2
    //   20: aload_2
    //   21: invokevirtual 90	java/io/File:exists	()Z
    //   24: ifeq +16 -> 40
    //   27: aload_2
    //   28: invokevirtual 86	java/io/File:length	()J
    //   31: lstore 5
    //   33: lload 5
    //   35: lconst_0
    //   36: lcmp
    //   37: ifgt +5 -> 42
    //   40: aconst_null
    //   41: areturn
    //   42: getstatic 67	com/umeng/analytics/f:b	Landroid/content/Context;
    //   45: aload_1
    //   46: invokevirtual 206	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   49: astore 10
    //   51: aload 10
    //   53: astore 8
    //   55: aload 8
    //   57: invokestatic 209	u/aly/bv:b	(Ljava/io/InputStream;)[B
    //   60: astore 11
    //   62: aload 8
    //   64: invokestatic 212	u/aly/bv:c	(Ljava/io/InputStream;)V
    //   67: aload 11
    //   69: areturn
    //   70: astore_3
    //   71: aload_2
    //   72: invokevirtual 202	java/io/File:delete	()Z
    //   75: pop
    //   76: aload_3
    //   77: invokevirtual 196	java/lang/Exception:printStackTrace	()V
    //   80: aconst_null
    //   81: areturn
    //   82: astore 9
    //   84: aconst_null
    //   85: astore 8
    //   87: aload 9
    //   89: invokevirtual 196	java/lang/Exception:printStackTrace	()V
    //   92: aload 8
    //   94: invokestatic 212	u/aly/bv:c	(Ljava/io/InputStream;)V
    //   97: goto -17 -> 80
    //   100: aload 8
    //   102: invokestatic 212	u/aly/bv:c	(Ljava/io/InputStream;)V
    //   105: aload 7
    //   107: athrow
    //   108: astore 7
    //   110: goto -10 -> 100
    //   113: astore 9
    //   115: goto -28 -> 87
    //   118: astore 7
    //   120: aconst_null
    //   121: astore 8
    //   123: goto -23 -> 100
    //
    // Exception table:
    //   from	to	target	type
    //   20	33	70	java/lang/Exception
    //   62	67	70	java/lang/Exception
    //   92	97	70	java/lang/Exception
    //   100	108	70	java/lang/Exception
    //   42	51	82	java/lang/Exception
    //   55	62	108	finally
    //   87	92	108	finally
    //   55	62	113	java/lang/Exception
    //   42	51	118	finally
  }

  public void e()
  {
    String str = n();
    boolean bool = b.deleteFile(str);
    bj.a("--->", "delete envelope:" + bool);
  }

  public boolean f()
  {
    String str = n();
    File localFile = new File(b.getFilesDir(), str);
    return (localFile.exists()) && (localFile.length() > 0L);
  }

  public SharedPreferences g()
  {
    return b.getSharedPreferences("mobclick_agent_online_setting_" + c, 0);
  }

  public SharedPreferences h()
  {
    return b.getSharedPreferences("mobclick_agent_header_" + c, 0);
  }

  public SharedPreferences i()
  {
    return b.getSharedPreferences("mobclick_agent_update_" + c, 0);
  }

  public SharedPreferences j()
  {
    return b.getSharedPreferences("mobclick_agent_state_" + c, 0);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.f
 * JD-Core Version:    0.6.2
 */