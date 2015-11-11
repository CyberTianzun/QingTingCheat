package weibo4android.http;

import java.io.IOException;
import java.io.InputStream;

public class ImageItem
{
  private String ImageType;
  private byte[] content;
  private String name;

  public ImageItem(String paramString, byte[] paramArrayOfByte)
    throws Exception
  {
    String str = getImageType(paramArrayOfByte);
    if ((str != null) && ((str.equalsIgnoreCase("image/gif")) || (str.equalsIgnoreCase("image/png")) || (str.equalsIgnoreCase("image/jpeg"))))
    {
      this.content = paramArrayOfByte;
      this.name = paramString;
      this.ImageType = str;
      return;
    }
    throw new IllegalStateException("Unsupported image type, Only Suport JPG ,GIF,PNG!");
  }

  public ImageItem(byte[] paramArrayOfByte)
    throws Exception
  {
    this("pic", paramArrayOfByte);
  }

  // ERROR //
  public static String getImageType(java.io.File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: ifnull +14 -> 17
    //   6: aload_0
    //   7: invokevirtual 59	java/io/File:exists	()Z
    //   10: istore_2
    //   11: aconst_null
    //   12: astore_1
    //   13: iload_2
    //   14: ifne +5 -> 19
    //   17: aload_1
    //   18: areturn
    //   19: new 61	java/io/FileInputStream
    //   22: dup
    //   23: aload_0
    //   24: invokespecial 64	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   27: astore_3
    //   28: aload_3
    //   29: invokestatic 67	weibo4android/http/ImageItem:getImageType	(Ljava/io/InputStream;)Ljava/lang/String;
    //   32: astore 8
    //   34: aload 8
    //   36: astore_1
    //   37: aload_3
    //   38: ifnull -21 -> 17
    //   41: aload_3
    //   42: invokevirtual 72	java/io/InputStream:close	()V
    //   45: aload_1
    //   46: areturn
    //   47: astore 9
    //   49: aload_1
    //   50: areturn
    //   51: astore 11
    //   53: aconst_null
    //   54: astore_3
    //   55: aconst_null
    //   56: astore_1
    //   57: aload_3
    //   58: ifnull -41 -> 17
    //   61: aload_3
    //   62: invokevirtual 72	java/io/InputStream:close	()V
    //   65: aconst_null
    //   66: areturn
    //   67: astore 5
    //   69: aconst_null
    //   70: areturn
    //   71: astore 10
    //   73: aconst_null
    //   74: astore_3
    //   75: aload 10
    //   77: astore 6
    //   79: aload_3
    //   80: ifnull +7 -> 87
    //   83: aload_3
    //   84: invokevirtual 72	java/io/InputStream:close	()V
    //   87: aload 6
    //   89: athrow
    //   90: astore 7
    //   92: goto -5 -> 87
    //   95: astore 6
    //   97: goto -18 -> 79
    //   100: astore 4
    //   102: goto -47 -> 55
    //
    // Exception table:
    //   from	to	target	type
    //   41	45	47	java/io/IOException
    //   19	28	51	java/io/IOException
    //   61	65	67	java/io/IOException
    //   19	28	71	finally
    //   83	87	90	java/io/IOException
    //   28	34	95	finally
    //   28	34	100	java/io/IOException
  }

  public static String getImageType(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    try
    {
      byte[] arrayOfByte = new byte[8];
      paramInputStream.read(arrayOfByte);
      String str = getImageType(arrayOfByte);
      return str;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  public static String getImageType(byte[] paramArrayOfByte)
  {
    if (isJPEG(paramArrayOfByte))
      return "image/jpeg";
    if (isGIF(paramArrayOfByte))
      return "image/gif";
    if (isPNG(paramArrayOfByte))
      return "image/png";
    if (isBMP(paramArrayOfByte))
      return "application/x-bmp";
    return null;
  }

  private static boolean isBMP(byte[] paramArrayOfByte)
  {
    boolean bool = true;
    if (paramArrayOfByte.length < 2)
      return false;
    if ((paramArrayOfByte[0] == 66) && (paramArrayOfByte[bool] == 77));
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  private static boolean isGIF(byte[] paramArrayOfByte)
  {
    boolean bool = true;
    if (paramArrayOfByte.length < 6)
      return false;
    if ((paramArrayOfByte[0] == 71) && (paramArrayOfByte[bool] == 73) && (paramArrayOfByte[2] == 70) && (paramArrayOfByte[3] == 56) && ((paramArrayOfByte[4] == 55) || (paramArrayOfByte[4] == 57)) && (paramArrayOfByte[5] == 97));
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  private static boolean isJPEG(byte[] paramArrayOfByte)
  {
    boolean bool = true;
    if (paramArrayOfByte.length < 2)
      return false;
    if ((paramArrayOfByte[0] == -1) && (paramArrayOfByte[bool] == -40));
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  private static boolean isPNG(byte[] paramArrayOfByte)
  {
    boolean bool = true;
    if (paramArrayOfByte.length < 8)
      return false;
    if ((paramArrayOfByte[0] == -119) && (paramArrayOfByte[bool] == 80) && (paramArrayOfByte[2] == 78) && (paramArrayOfByte[3] == 71) && (paramArrayOfByte[4] == 13) && (paramArrayOfByte[5] == 10) && (paramArrayOfByte[6] == 26) && (paramArrayOfByte[7] == 10));
    while (true)
    {
      return bool;
      bool = false;
    }
  }

  public byte[] getContent()
  {
    return this.content;
  }

  public String getImageType()
  {
    return this.ImageType;
  }

  public String getName()
  {
    return this.name;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.http.ImageItem
 * JD-Core Version:    0.6.2
 */