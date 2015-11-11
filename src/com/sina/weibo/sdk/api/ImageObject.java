package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sina.weibo.sdk.utils.LogUtil;
import java.io.File;

public class ImageObject extends BaseMediaObject
{
  public static final Parcelable.Creator<ImageObject> CREATOR = new Parcelable.Creator()
  {
    public ImageObject createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ImageObject(paramAnonymousParcel);
    }

    public ImageObject[] newArray(int paramAnonymousInt)
    {
      return new ImageObject[paramAnonymousInt];
    }
  };
  private static final int DATA_SIZE = 2097152;
  public byte[] imageData;
  public String imagePath;

  public ImageObject()
  {
  }

  public ImageObject(Parcel paramParcel)
  {
    this.imageData = paramParcel.createByteArray();
    this.imagePath = paramParcel.readString();
  }

  public boolean checkArgs()
  {
    if ((this.imageData == null) && (this.imagePath == null))
    {
      LogUtil.e("Weibo.ImageObject", "imageData and imagePath are null");
      return false;
    }
    if ((this.imageData != null) && (this.imageData.length > 2097152))
    {
      LogUtil.e("Weibo.ImageObject", "imageData is too large");
      return false;
    }
    if ((this.imagePath != null) && (this.imagePath.length() > 512))
    {
      LogUtil.e("Weibo.ImageObject", "imagePath is too length");
      return false;
    }
    if (this.imagePath != null)
    {
      File localFile = new File(this.imagePath);
      try
      {
        if ((!localFile.exists()) || (localFile.length() == 0L) || (localFile.length() > 10485760L))
        {
          LogUtil.e("Weibo.ImageObject", "checkArgs fail, image content is too large or not exists");
          return false;
        }
      }
      catch (SecurityException localSecurityException)
      {
        LogUtil.e("Weibo.ImageObject", "checkArgs fail, image content is too large or not exists");
        return false;
      }
    }
    return true;
  }

  public int describeContents()
  {
    return 0;
  }

  public int getObjType()
  {
    return 2;
  }

  // ERROR //
  public final void setImageObject(android.graphics.Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 88	java/io/ByteArrayOutputStream
    //   5: dup
    //   6: invokespecial 89	java/io/ByteArrayOutputStream:<init>	()V
    //   9: astore_3
    //   10: aload_1
    //   11: getstatic 95	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   14: bipush 85
    //   16: aload_3
    //   17: invokevirtual 101	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   20: pop
    //   21: aload_0
    //   22: aload_3
    //   23: invokevirtual 104	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   26: putfield 33	com/sina/weibo/sdk/api/ImageObject:imageData	[B
    //   29: aload_3
    //   30: ifnull +69 -> 99
    //   33: aload_3
    //   34: invokevirtual 107	java/io/ByteArrayOutputStream:close	()V
    //   37: return
    //   38: astore 4
    //   40: aload 4
    //   42: invokevirtual 110	java/lang/Exception:printStackTrace	()V
    //   45: ldc 45
    //   47: ldc 112
    //   49: invokestatic 53	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   52: aload_2
    //   53: ifnull -16 -> 37
    //   56: aload_2
    //   57: invokevirtual 107	java/io/ByteArrayOutputStream:close	()V
    //   60: return
    //   61: astore 7
    //   63: aload 7
    //   65: invokevirtual 113	java/io/IOException:printStackTrace	()V
    //   68: return
    //   69: astore 5
    //   71: aload_2
    //   72: ifnull +7 -> 79
    //   75: aload_2
    //   76: invokevirtual 107	java/io/ByteArrayOutputStream:close	()V
    //   79: aload 5
    //   81: athrow
    //   82: astore 6
    //   84: aload 6
    //   86: invokevirtual 113	java/io/IOException:printStackTrace	()V
    //   89: goto -10 -> 79
    //   92: astore 9
    //   94: aload 9
    //   96: invokevirtual 113	java/io/IOException:printStackTrace	()V
    //   99: return
    //   100: astore 5
    //   102: aload_3
    //   103: astore_2
    //   104: goto -33 -> 71
    //   107: astore 4
    //   109: aload_3
    //   110: astore_2
    //   111: goto -71 -> 40
    //
    // Exception table:
    //   from	to	target	type
    //   2	10	38	java/lang/Exception
    //   56	60	61	java/io/IOException
    //   2	10	69	finally
    //   40	52	69	finally
    //   75	79	82	java/io/IOException
    //   33	37	92	java/io/IOException
    //   10	29	100	finally
    //   10	29	107	java/lang/Exception
  }

  protected BaseMediaObject toExtraMediaObject(String paramString)
  {
    return this;
  }

  protected String toExtraMediaString()
  {
    return "";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeByteArray(this.imageData);
    paramParcel.writeString(this.imagePath);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.api.ImageObject
 * JD-Core Version:    0.6.2
 */