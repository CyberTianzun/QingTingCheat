package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils
{
  private static void delete(File paramFile)
  {
    if ((paramFile != null) && (paramFile.exists()) && (!paramFile.delete()))
      throw new RuntimeException(paramFile.getAbsolutePath() + " doesn't be deleted!");
  }

  private static boolean deleteDependon(String paramString)
  {
    boolean bool;
    if (TextUtils.isEmpty(paramString))
      bool = false;
    while (true)
    {
      return bool;
      File localFile = new File(paramString);
      int i = 1;
      int j = 0;
      if (1 < 0)
        j = 5;
      bool = false;
      if (localFile != null)
        while ((!bool) && (i <= j) && (localFile.isFile()) && (localFile.exists()))
        {
          bool = localFile.delete();
          if (!bool)
            i++;
        }
    }
  }

  private static boolean isFileExisted(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    File localFile;
    do
    {
      return false;
      localFile = new File(paramString);
    }
    while ((localFile == null) || (!localFile.exists()));
    return true;
  }

  private static boolean isParentExist(File paramFile)
  {
    if (paramFile == null);
    File localFile;
    do
    {
      return false;
      localFile = paramFile.getParentFile();
    }
    while ((localFile == null) || (localFile.exists()) || ((!paramFile.exists()) && (!paramFile.mkdirs())));
    return true;
  }

  public static boolean isWifi(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.getType() == 1);
  }

  private static void makesureFileExist(String paramString)
  {
    if (paramString == null);
    File localFile;
    do
    {
      return;
      localFile = new File(paramString);
    }
    while ((localFile == null) || (localFile.exists()) || (!isParentExist(localFile)));
    if (localFile.exists())
      delete(localFile);
    try
    {
      localFile.createNewFile();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  private static void revitionImageSize(String paramString, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt1 <= 0)
      throw new IllegalArgumentException("size must be greater than 0!");
    if (!isFileExisted(paramString))
    {
      if (paramString == null)
        paramString = "null";
      throw new FileNotFoundException(paramString);
    }
    if (!BitmapHelper.verifyBitmap(paramString))
      throw new IOException("");
    FileInputStream localFileInputStream = new FileInputStream(paramString);
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeStream(localFileInputStream, null, localOptions);
    Bitmap localBitmap;
    label177: FileOutputStream localFileOutputStream;
    try
    {
      localFileInputStream.close();
      i = 0;
      if ((localOptions.outWidth >> i <= paramInt1) && (localOptions.outHeight >> i <= paramInt1))
      {
        localOptions.inSampleSize = ((int)Math.pow(2.0D, i));
        localOptions.inJustDecodeBounds = false;
        localBitmap = safeDecodeBimtapFile(paramString, localOptions);
        if (localBitmap != null)
          break label177;
        throw new IOException("Bitmap decode error!");
      }
    }
    catch (Exception localException1)
    {
      while (true)
      {
        int i;
        localException1.printStackTrace();
        continue;
        i++;
      }
      deleteDependon(paramString);
      makesureFileExist(paramString);
      localFileOutputStream = new FileOutputStream(paramString);
      if (localOptions == null)
        break label245;
    }
    if ((localOptions.outMimeType != null) && (localOptions.outMimeType.contains("png")))
      localBitmap.compress(Bitmap.CompressFormat.PNG, paramInt2, localFileOutputStream);
    try
    {
      while (true)
      {
        localFileOutputStream.close();
        localBitmap.recycle();
        return;
        label245: localBitmap.compress(Bitmap.CompressFormat.JPEG, paramInt2, localFileOutputStream);
      }
    }
    catch (Exception localException2)
    {
      while (true)
        localException2.printStackTrace();
    }
  }

  // ERROR //
  private static void revitionImageSizeHD(String paramString, int paramInt1, int paramInt2)
    throws IOException
  {
    // Byte code:
    //   0: iload_1
    //   1: ifgt +13 -> 14
    //   4: new 108	java/lang/IllegalArgumentException
    //   7: dup
    //   8: ldc 110
    //   10: invokespecial 111	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   13: athrow
    //   14: aload_0
    //   15: invokestatic 113	com/sina/weibo/sdk/utils/ImageUtils:isFileExisted	(Ljava/lang/String;)Z
    //   18: ifne +23 -> 41
    //   21: aload_0
    //   22: ifnonnull +6 -> 28
    //   25: ldc 115
    //   27: astore_0
    //   28: new 117	java/io/FileNotFoundException
    //   31: dup
    //   32: aload_0
    //   33: invokespecial 118	java/io/FileNotFoundException:<init>	(Ljava/lang/String;)V
    //   36: astore 21
    //   38: aload 21
    //   40: athrow
    //   41: aload_0
    //   42: invokestatic 123	com/sina/weibo/sdk/utils/BitmapHelper:verifyBitmap	(Ljava/lang/String;)Z
    //   45: ifne +13 -> 58
    //   48: new 92	java/io/IOException
    //   51: dup
    //   52: ldc 125
    //   54: invokespecial 126	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   57: athrow
    //   58: iload_1
    //   59: iconst_2
    //   60: imul
    //   61: istore_3
    //   62: new 128	java/io/FileInputStream
    //   65: dup
    //   66: aload_0
    //   67: invokespecial 129	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   70: astore 4
    //   72: new 131	android/graphics/BitmapFactory$Options
    //   75: dup
    //   76: invokespecial 132	android/graphics/BitmapFactory$Options:<init>	()V
    //   79: astore 5
    //   81: aload 5
    //   83: iconst_1
    //   84: putfield 136	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   87: aload 4
    //   89: aconst_null
    //   90: aload 5
    //   92: invokestatic 142	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   95: pop
    //   96: aload 4
    //   98: invokevirtual 145	java/io/FileInputStream:close	()V
    //   101: iconst_0
    //   102: istore 8
    //   104: aload 5
    //   106: getfield 149	android/graphics/BitmapFactory$Options:outWidth	I
    //   109: iload 8
    //   111: ishr
    //   112: iload_3
    //   113: if_icmpgt +69 -> 182
    //   116: aload 5
    //   118: getfield 152	android/graphics/BitmapFactory$Options:outHeight	I
    //   121: iload 8
    //   123: ishr
    //   124: iload_3
    //   125: if_icmpgt +57 -> 182
    //   128: aload 5
    //   130: ldc2_w 153
    //   133: iload 8
    //   135: i2d
    //   136: invokestatic 160	java/lang/Math:pow	(DD)D
    //   139: d2i
    //   140: putfield 163	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   143: aload 5
    //   145: iconst_0
    //   146: putfield 136	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   149: aload_0
    //   150: aload 5
    //   152: invokestatic 167	com/sina/weibo/sdk/utils/ImageUtils:safeDecodeBimtapFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   155: astore 9
    //   157: aload 9
    //   159: ifnonnull +29 -> 188
    //   162: new 92	java/io/IOException
    //   165: dup
    //   166: ldc 169
    //   168: invokespecial 126	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   171: athrow
    //   172: astore 7
    //   174: aload 7
    //   176: invokevirtual 170	java/lang/Exception:printStackTrace	()V
    //   179: goto -78 -> 101
    //   182: iinc 8 1
    //   185: goto -81 -> 104
    //   188: aload_0
    //   189: invokestatic 172	com/sina/weibo/sdk/utils/ImageUtils:deleteDependon	(Ljava/lang/String;)Z
    //   192: pop
    //   193: aload_0
    //   194: invokestatic 174	com/sina/weibo/sdk/utils/ImageUtils:makesureFileExist	(Ljava/lang/String;)V
    //   197: aload 9
    //   199: invokevirtual 211	android/graphics/Bitmap:getWidth	()I
    //   202: aload 9
    //   204: invokevirtual 214	android/graphics/Bitmap:getHeight	()I
    //   207: if_icmple +176 -> 383
    //   210: aload 9
    //   212: invokevirtual 211	android/graphics/Bitmap:getWidth	()I
    //   215: istore 11
    //   217: iload_1
    //   218: i2f
    //   219: iload 11
    //   221: i2f
    //   222: fdiv
    //   223: fstore 12
    //   225: fload 12
    //   227: fconst_1
    //   228: fcmpg
    //   229: ifge +95 -> 324
    //   232: fload 12
    //   234: aload 9
    //   236: invokevirtual 211	android/graphics/Bitmap:getWidth	()I
    //   239: i2f
    //   240: fmul
    //   241: f2i
    //   242: fload 12
    //   244: aload 9
    //   246: invokevirtual 214	android/graphics/Bitmap:getHeight	()I
    //   249: i2f
    //   250: fmul
    //   251: f2i
    //   252: getstatic 220	android/graphics/Bitmap$Config:ARGB_8888	Landroid/graphics/Bitmap$Config;
    //   255: invokestatic 224	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
    //   258: astore 18
    //   260: aload 18
    //   262: ifnonnull +8 -> 270
    //   265: aload 9
    //   267: invokevirtual 202	android/graphics/Bitmap:recycle	()V
    //   270: new 226	android/graphics/Canvas
    //   273: dup
    //   274: aload 18
    //   276: invokespecial 229	android/graphics/Canvas:<init>	(Landroid/graphics/Bitmap;)V
    //   279: astore 19
    //   281: new 231	android/graphics/Matrix
    //   284: dup
    //   285: invokespecial 232	android/graphics/Matrix:<init>	()V
    //   288: astore 20
    //   290: aload 20
    //   292: fload 12
    //   294: fload 12
    //   296: invokevirtual 236	android/graphics/Matrix:setScale	(FF)V
    //   299: aload 19
    //   301: aload 9
    //   303: aload 20
    //   305: new 238	android/graphics/Paint
    //   308: dup
    //   309: invokespecial 239	android/graphics/Paint:<init>	()V
    //   312: invokevirtual 243	android/graphics/Canvas:drawBitmap	(Landroid/graphics/Bitmap;Landroid/graphics/Matrix;Landroid/graphics/Paint;)V
    //   315: aload 9
    //   317: invokevirtual 202	android/graphics/Bitmap:recycle	()V
    //   320: aload 18
    //   322: astore 9
    //   324: new 176	java/io/FileOutputStream
    //   327: dup
    //   328: aload_0
    //   329: invokespecial 177	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   332: astore 13
    //   334: aload 5
    //   336: ifnull +75 -> 411
    //   339: aload 5
    //   341: getfield 181	android/graphics/BitmapFactory$Options:outMimeType	Ljava/lang/String;
    //   344: ifnull +67 -> 411
    //   347: aload 5
    //   349: getfield 181	android/graphics/BitmapFactory$Options:outMimeType	Ljava/lang/String;
    //   352: ldc 183
    //   354: invokevirtual 186	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   357: ifeq +54 -> 411
    //   360: aload 9
    //   362: getstatic 192	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   365: iload_2
    //   366: aload 13
    //   368: invokevirtual 198	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   371: pop
    //   372: aload 13
    //   374: invokevirtual 199	java/io/FileOutputStream:close	()V
    //   377: aload 9
    //   379: invokevirtual 202	android/graphics/Bitmap:recycle	()V
    //   382: return
    //   383: aload 9
    //   385: invokevirtual 214	android/graphics/Bitmap:getHeight	()I
    //   388: istore 11
    //   390: goto -173 -> 217
    //   393: astore 17
    //   395: invokestatic 248	java/lang/System:gc	()V
    //   398: ldc2_w 249
    //   401: fload 12
    //   403: f2d
    //   404: dmul
    //   405: d2f
    //   406: fstore 12
    //   408: goto -176 -> 232
    //   411: aload 9
    //   413: getstatic 205	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   416: iload_2
    //   417: aload 13
    //   419: invokevirtual 198	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   422: pop
    //   423: goto -51 -> 372
    //   426: astore 15
    //   428: aload 15
    //   430: invokevirtual 170	java/lang/Exception:printStackTrace	()V
    //   433: goto -56 -> 377
    //
    // Exception table:
    //   from	to	target	type
    //   96	101	172	java/lang/Exception
    //   232	260	393	java/lang/OutOfMemoryError
    //   372	377	426	java/lang/Exception
  }

  public static boolean revitionPostImageSize(Context paramContext, String paramString)
  {
    try
    {
      if (NetworkHelper.isWifiValid(paramContext))
        revitionImageSizeHD(paramString, 1600, 75);
      else
        revitionImageSize(paramString, 1024, 75);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return false;
    }
    return true;
  }

  private static Bitmap safeDecodeBimtapFile(String paramString, BitmapFactory.Options paramOptions)
  {
    BitmapFactory.Options localOptions = paramOptions;
    if (localOptions == null)
    {
      localOptions = new BitmapFactory.Options();
      localOptions.inSampleSize = 1;
    }
    Object localObject1 = null;
    int i = 0;
    Object localObject2 = null;
    while (true)
    {
      if (i >= 5)
        return localObject1;
      try
      {
        localObject3 = new FileInputStream(paramString);
      }
      catch (FileNotFoundException localFileNotFoundException2)
      {
        try
        {
          Bitmap localBitmap = BitmapFactory.decodeStream((InputStream)localObject3, null, paramOptions);
          localObject1 = localBitmap;
          try
          {
            ((FileInputStream)localObject3).close();
            return localObject1;
          }
          catch (IOException localIOException2)
          {
            localIOException2.printStackTrace();
            return localObject1;
          }
        }
        catch (OutOfMemoryError localOutOfMemoryError1)
        {
          localOutOfMemoryError1.printStackTrace();
          localOptions.inSampleSize = (2 * localOptions.inSampleSize);
          try
          {
            ((FileInputStream)localObject3).close();
            i++;
            localObject2 = localObject3;
          }
          catch (IOException localIOException1)
          {
            while (true)
              localIOException1.printStackTrace();
          }
          localFileNotFoundException2 = localFileNotFoundException2;
          return localObject1;
        }
        catch (FileNotFoundException localFileNotFoundException1)
        {
          return localObject1;
        }
      }
      catch (OutOfMemoryError localOutOfMemoryError2)
      {
        while (true)
          Object localObject3 = localObject2;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.utils.ImageUtils
 * JD-Core Version:    0.6.2
 */