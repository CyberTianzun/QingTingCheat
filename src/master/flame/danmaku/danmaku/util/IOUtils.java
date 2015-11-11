package master.flame.danmaku.danmaku.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils
{
  public static void closeQuietly(InputStream paramInputStream)
  {
    if (paramInputStream != null);
    try
    {
      paramInputStream.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public static void closeQuietly(OutputStream paramOutputStream)
  {
    if (paramOutputStream != null);
    try
    {
      paramOutputStream.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public static byte[] getBytes(InputStream paramInputStream)
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte1 = new byte[8192];
      while (true)
      {
        int i = paramInputStream.read(arrayOfByte1);
        if (i == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte1, 0, i);
      }
      paramInputStream.close();
      byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
      return arrayOfByte2;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  public static String getString(InputStream paramInputStream)
  {
    byte[] arrayOfByte = getBytes(paramInputStream);
    if (arrayOfByte == null)
      return null;
    return new String(arrayOfByte);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.util.IOUtils
 * JD-Core Version:    0.6.2
 */