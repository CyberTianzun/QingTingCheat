package com.ixintui.pushsdk.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import dalvik.system.DexClassLoader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Locale;
import javax.crypto.Cipher;

public class a
{
  private static ClassLoader a;

  private static FileOutputStream a(String paramString)
  {
    try
    {
      File localFile = new File(Environment.getExternalStorageDirectory(), paramString);
      if (!localFile.getParentFile().exists())
        localFile.getParentFile().mkdirs();
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile, true);
      return localFileOutputStream;
    }
    catch (Exception localException)
    {
      localException.getMessage();
    }
    return null;
  }

  public static Object a(Context paramContext, String paramString)
  {
    try
    {
      Class localClass = c(paramContext, paramString);
      Object localObject1 = null;
      if (localClass != null)
      {
        Object localObject2 = localClass.newInstance();
        localObject1 = localObject2;
      }
      return localObject1;
    }
    catch (Exception localException)
    {
      a(localException);
    }
    return null;
  }

  public static Object a(Object paramObject, String paramString, Class[] paramArrayOfClass, Object[] paramArrayOfObject)
  {
    if (paramObject == null)
      return null;
    try
    {
      Object localObject = paramObject.getClass().getMethod(paramString, null).invoke(paramObject, null);
      return localObject;
    }
    catch (Exception localException)
    {
      a(localException);
    }
    return null;
  }

  public static String a(Context paramContext)
  {
    try
    {
      String str = d(paramContext).getString("version_file", null);
      return str;
    }
    catch (Exception localException)
    {
      a(localException);
    }
    return null;
  }

  private static String a(InputStream paramInputStream)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      DigestInputStream localDigestInputStream = new DigestInputStream(paramInputStream, localMessageDigest);
      byte[] arrayOfByte = new byte[8192];
      while (localDigestInputStream.read(arrayOfByte, 0, 8192) != -1);
      localDigestInputStream.close();
      String str = new BigInteger(1, localMessageDigest.digest()).toString(16);
      return str;
    }
    catch (Exception localException)
    {
      a(localException);
    }
    return null;
  }

  @SuppressLint({"GetInstance"})
  public static String a(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return "";
    try
    {
      Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      localCipher.init(2, b());
      String str = new String(localCipher.doFinal(paramArrayOfByte), "UTF-8");
      return str;
    }
    catch (Exception localException)
    {
      a(localException);
    }
    return null;
  }

  public static void a(Exception paramException)
  {
    if (!a());
    FileOutputStream localFileOutputStream;
    do
    {
      return;
      localFileOutputStream = a("ixintui/log.txt");
    }
    while (localFileOutputStream == null);
    PrintStream localPrintStream = new PrintStream(localFileOutputStream);
    paramException.printStackTrace(localPrintStream);
    try
    {
      localPrintStream.close();
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.getMessage();
    }
  }

  private static void a(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      boolean bool = "mounted".equals(Environment.getExternalStorageState());
      if (!bool);
      while (true)
      {
        return;
        FileOutputStream localFileOutputStream = a(paramString1);
        if (localFileOutputStream != null)
        {
          BufferedWriter localBufferedWriter = new BufferedWriter(new OutputStreamWriter(localFileOutputStream));
          Calendar localCalendar = Calendar.getInstance();
          Locale localLocale = Locale.getDefault();
          Object[] arrayOfObject = new Object[7];
          arrayOfObject[0] = Integer.valueOf(localCalendar.get(1));
          arrayOfObject[1] = Integer.valueOf(1 + localCalendar.get(2));
          arrayOfObject[2] = Integer.valueOf(localCalendar.get(5));
          arrayOfObject[3] = Integer.valueOf(localCalendar.get(11));
          arrayOfObject[4] = Integer.valueOf(localCalendar.get(12));
          arrayOfObject[5] = Integer.valueOf(localCalendar.get(13));
          arrayOfObject[6] = Integer.valueOf(localCalendar.get(14));
          String str = String.format(localLocale, "%d-%02d-%02d %02d:%02d:%02d.%03d", arrayOfObject);
          localBufferedWriter.write(str + " " + paramString2 + " \t" + paramString3 + "\r\n");
          localBufferedWriter.close();
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
    finally
    {
    }
  }

  public static boolean a()
  {
    try
    {
      boolean bool1 = "mounted".equals(Environment.getExternalStorageState());
      if (!bool1);
      boolean bool2;
      for (boolean bool3 = false; ; bool3 = bool2)
      {
        return bool3;
        bool2 = new File(Environment.getExternalStorageDirectory(), "ixintui/log_flag.txt").exists();
      }
    }
    finally
    {
    }
  }

  private static boolean a(Context paramContext, File paramFile)
  {
    try
    {
      String str1 = a(new FileInputStream(paramFile));
      String str2 = a(paramContext.getAssets().open("ixintui_plugin/ixintui_plugin.jar"));
      boolean bool1 = false;
      if (str2 != null)
      {
        boolean bool2 = str2.equalsIgnoreCase(str1);
        bool1 = false;
        if (bool2)
          bool1 = true;
      }
      return bool1;
    }
    catch (Exception localException)
    {
      a(localException);
    }
    return false;
  }

  private static boolean a(ClassLoader paramClassLoader)
  {
    try
    {
      if (paramClassLoader.loadClass("com.ixintui.pushsdk.PushSdkApiImpl") == null)
        return false;
      if ((paramClassLoader.loadClass("com.ixintui.push.PushServiceImpl") != null) && (paramClassLoader.loadClass("com.ixintui.push.PushReceiverImpl") != null) && (paramClassLoader.loadClass("com.ixintui.push.PushProviderImpl") != null) && (paramClassLoader.loadClass("com.ixintui.push.PushActivityImpl") != null))
      {
        Class localClass = paramClassLoader.loadClass("com.ixintui.push.MediateServiceImpl");
        if (localClass != null)
          return true;
      }
    }
    catch (Exception localException)
    {
      a(localException);
    }
    return false;
  }

  private static boolean a(String paramString1, String paramString2)
  {
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)));
    while (true)
    {
      return false;
      String str1 = a(Base64.decode(paramString2, 0));
      File localFile = new File(paramString1);
      try
      {
        String str2 = a(new FileInputStream(localFile));
        if (str1 != null)
        {
          boolean bool = str1.equalsIgnoreCase(str2);
          if (bool)
            return true;
        }
      }
      catch (Exception localException)
      {
      }
    }
    return false;
  }

  private static ClassLoader b(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    while (true)
    {
      return null;
      if (new File(paramString).exists())
        try
        {
          DexClassLoader localDexClassLoader = new DexClassLoader(paramString, paramContext.getCacheDir().getAbsolutePath(), null, paramContext.getClassLoader());
          boolean bool = a(localDexClassLoader);
          if (bool)
            return localDexClassLoader;
        }
        catch (Exception localException)
        {
          a(localException);
        }
    }
    return null;
  }

  private static String b(Context paramContext, File paramFile)
  {
    BufferedInputStream localBufferedInputStream;
    BufferedOutputStream localBufferedOutputStream;
    try
    {
      localBufferedInputStream = new BufferedInputStream(paramContext.getAssets().open("ixintui_plugin/ixintui_plugin.jar"));
      localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramFile));
      byte[] arrayOfByte = new byte[8192];
      while (true)
      {
        int i = localBufferedInputStream.read(arrayOfByte);
        if (i <= 0)
          break;
        localBufferedOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (Exception localException)
    {
      a(localException);
      return null;
    }
    localBufferedOutputStream.flush();
    localBufferedOutputStream.close();
    localBufferedInputStream.close();
    String str = paramFile.getAbsolutePath();
    return str;
  }

  private static PublicKey b()
  {
    X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDbMQJogEyynCkiQJ2EOH7v8mPL\nskclL0XonCy0rwdi+B5OuNTBO6vO4xaHMw2bmTET+lVvZ21BZB4DlCd7scVMRQQ4\n2CfE8BjqmSrBj9uV5q3jJbyAiLJdCcEmNtbfCbVgbXTl+nJvuZBP7Z6yPFKc7/RJ\nYEZ4/mpqJBduUHjkWQIDAQAB\n", 0));
    try
    {
      PublicKey localPublicKey = KeyFactory.getInstance("RSA").generatePublic(localX509EncodedKeySpec);
      return localPublicKey;
    }
    catch (Exception localException)
    {
      a(localException);
    }
    return null;
  }

  private static void b(String paramString1, String paramString2)
  {
    try
    {
      a("ixintui/log.txt", paramString1, paramString2);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static boolean b(Context paramContext)
  {
    try
    {
      SharedPreferences localSharedPreferences = d(paramContext);
      if (!localSharedPreferences.contains("version_code"))
        return false;
      int i = localSharedPreferences.getInt("version_code", 0);
      boolean bool;
      if (i > 14102)
        bool = true;
      while (true)
      {
        if (!bool);
        try
        {
          d(paramContext).edit().clear().commit();
          return bool;
          bool = false;
        }
        catch (Exception localException2)
        {
          while (true)
            a(localException2);
        }
      }
    }
    catch (Exception localException1)
    {
      a(localException1);
    }
    return false;
  }

  private static Class c(Context paramContext, String paramString)
  {
    String str2;
    if (a == null)
    {
      str2 = a(paramContext);
      if ((!b(paramContext)) || (!a(str2, c(paramContext))))
        break label221;
    }
    label193: label221: for (ClassLoader localClassLoader2 = b(paramContext, str2); ; localClassLoader2 = null)
    {
      File localFile;
      if (localClassLoader2 == null)
      {
        localFile = paramContext.getFileStreamPath("ixintui_plugin.jar");
        if ((!localFile.exists()) || (!a(paramContext, localFile)))
          break label193;
      }
      ClassLoader localClassLoader1;
      for (String str3 = localFile.getAbsolutePath(); ; str3 = b(paramContext, localFile))
      {
        localClassLoader2 = b(paramContext, str3);
        a = localClassLoader2;
        localClassLoader1 = a;
        if (localClassLoader1 != null)
          break;
        String str1 = "Process id: " + Process.myPid() + " Thread id: " + Thread.currentThread().getId() + " ";
        new StringBuilder().append(str1).append("failed to load plugin!");
        if (a())
          b("loadClass", str1 + "failed to load plugin!");
        return null;
      }
      try
      {
        Class localClass = localClassLoader1.loadClass(paramString);
        return localClass;
      }
      catch (Exception localException)
      {
        a(localException);
        return null;
      }
    }
  }

  public static String c(Context paramContext)
  {
    try
    {
      String str = d(paramContext).getString("sign", null);
      return str;
    }
    catch (Exception localException)
    {
      a(localException);
    }
    return null;
  }

  private static SharedPreferences d(Context paramContext)
  {
    if (paramContext == null)
      throw new InvalidParameterException();
    return paramContext.getSharedPreferences("com.ixintui.data.version", 0);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.ixintui.pushsdk.a.a
 * JD-Core Version:    0.6.2
 */