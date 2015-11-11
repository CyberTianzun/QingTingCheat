package u.aly;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class bv
{
  public static final String a = System.getProperty("line.separator");
  private static final String b = "helper";

  public static String a()
  {
    return a(new Date());
  }

  public static String a(Context paramContext, long paramLong)
  {
    if (paramLong < 1000L)
      return (int)paramLong + "B";
    if (paramLong < 1000000L)
      return Math.round((float)paramLong / 1000.0D) + "K";
    if (paramLong < 1000000000L)
      return new DecimalFormat("#0.0").format((float)paramLong / 1000000.0D) + "M";
    return new DecimalFormat("#0.00").format((float)paramLong / 1000000000.0D) + "G";
  }

  public static String a(File paramFile)
  {
    byte[] arrayOfByte = new byte[1024];
    try
    {
      boolean bool = paramFile.isFile();
      if (!bool)
        return "";
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      FileInputStream localFileInputStream = new FileInputStream(paramFile);
      while (true)
      {
        int i = localFileInputStream.read(arrayOfByte, 0, 1024);
        if (i == -1)
        {
          localFileInputStream.close();
          return String.format("%1$032x", new Object[] { new BigInteger(1, localMessageDigest.digest()) });
        }
        localMessageDigest.update(arrayOfByte, 0, i);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static String a(InputStream paramInputStream)
    throws IOException
  {
    InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream);
    char[] arrayOfChar = new char[1024];
    StringWriter localStringWriter = new StringWriter();
    while (true)
    {
      int i = localInputStreamReader.read(arrayOfChar);
      if (-1 == i)
        return localStringWriter.toString();
      localStringWriter.write(arrayOfChar, 0, i);
    }
  }

  public static String a(String paramString)
  {
    int i = 0;
    if (paramString == null)
      return null;
    try
    {
      byte[] arrayOfByte1 = paramString.getBytes();
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.reset();
      localMessageDigest.update(arrayOfByte1);
      byte[] arrayOfByte2 = localMessageDigest.digest();
      StringBuffer localStringBuffer = new StringBuffer();
      while (true)
      {
        if (i >= arrayOfByte2.length)
          return localStringBuffer.toString();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Byte.valueOf(arrayOfByte2[i]);
        localStringBuffer.append(String.format("%02X", arrayOfObject));
        i++;
      }
    }
    catch (Exception localException)
    {
    }
    return paramString.replaceAll("[^[a-z][A-Z][0-9][.][_]]", "");
  }

  public static String a(Date paramDate)
  {
    if (paramDate == null)
      return "";
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(paramDate);
  }

  public static void a(Context paramContext, String paramString)
  {
    paramContext.startActivity(paramContext.getPackageManager().getLaunchIntentForPackage(paramString));
  }

  public static void a(File paramFile, String paramString)
    throws IOException
  {
    a(paramFile, paramString.getBytes());
  }

  public static void a(File paramFile, byte[] paramArrayOfByte)
    throws IOException
  {
    FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
    try
    {
      localFileOutputStream.write(paramArrayOfByte);
      localFileOutputStream.flush();
      return;
    }
    finally
    {
      a(localFileOutputStream);
    }
  }

  public static void a(OutputStream paramOutputStream)
  {
    if (paramOutputStream != null);
    try
    {
      paramOutputStream.close();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static String b(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; ; i++)
      {
        if (i >= arrayOfByte.length)
          return localStringBuffer.toString();
        localStringBuffer.append(Integer.toHexString(0xFF & arrayOfByte[i]));
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      bj.a("helper", "getMD5 error", localNoSuchAlgorithmException);
    }
    return "";
  }

  public static boolean b(Context paramContext, String paramString)
  {
    try
    {
      paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static byte[] b(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (-1 == i)
        return localByteArrayOutputStream.toByteArray();
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
  }

  public static String c(String paramString)
  {
    long l;
    try
    {
      l = Long.valueOf(paramString).longValue();
      if (l < 1024L)
        return (int)l + "B";
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return paramString;
    }
    if (l < 1048576L)
      return new DecimalFormat("#0.00").format((float)l / 1024.0D) + "K";
    if (l < 1073741824L)
      return new DecimalFormat("#0.00").format((float)l / 1048576.0D) + "M";
    return new DecimalFormat("#0.00").format((float)l / 1073741824.0D) + "G";
  }

  public static void c(InputStream paramInputStream)
  {
    if (paramInputStream != null);
    try
    {
      paramInputStream.close();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static boolean d(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }

  public static boolean e(String paramString)
  {
    if (d(paramString));
    String str;
    do
    {
      return false;
      str = paramString.trim().toLowerCase(Locale.US);
    }
    while ((!str.startsWith("http://")) && (!str.startsWith("https://")));
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.bv
 * JD-Core Version:    0.6.2
 */