package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.sina.weibo.sdk.utils.LogUtil;

public abstract class BaseMediaObject
  implements Parcelable
{
  public static final int MEDIA_TYPE_CMD = 7;
  public static final int MEDIA_TYPE_IMAGE = 2;
  public static final int MEDIA_TYPE_MUSIC = 3;
  public static final int MEDIA_TYPE_TEXT = 1;
  public static final int MEDIA_TYPE_VIDEO = 4;
  public static final int MEDIA_TYPE_VOICE = 6;
  public static final int MEDIA_TYPE_WEBPAGE = 5;
  public String actionUrl;
  public String description;
  public String identify;
  public String schema;
  public byte[] thumbData;
  public String title;

  public BaseMediaObject()
  {
  }

  public BaseMediaObject(Parcel paramParcel)
  {
    this.actionUrl = paramParcel.readString();
    this.schema = paramParcel.readString();
    this.identify = paramParcel.readString();
    this.title = paramParcel.readString();
    this.description = paramParcel.readString();
    this.thumbData = paramParcel.createByteArray();
  }

  protected boolean checkArgs()
  {
    if ((this.actionUrl == null) || (this.actionUrl.length() > 512))
    {
      LogUtil.e("Weibo.BaseMediaObject", "checkArgs fail, actionUrl is invalid");
      return false;
    }
    if ((this.identify == null) || (this.identify.length() > 512))
    {
      LogUtil.e("Weibo.BaseMediaObject", "checkArgs fail, identify is invalid");
      return false;
    }
    if ((this.thumbData == null) || (this.thumbData.length > 32768))
    {
      StringBuilder localStringBuilder = new StringBuilder("checkArgs fail, thumbData is invalid,size is ");
      if (this.thumbData != null);
      for (int i = this.thumbData.length; ; i = -1)
      {
        LogUtil.e("Weibo.BaseMediaObject", i + "! more then 32768.");
        return false;
      }
    }
    if ((this.title == null) || (this.title.length() > 512))
    {
      LogUtil.e("Weibo.BaseMediaObject", "checkArgs fail, title is invalid");
      return false;
    }
    if ((this.description == null) || (this.description.length() > 1024))
    {
      LogUtil.e("Weibo.BaseMediaObject", "checkArgs fail, description is invalid");
      return false;
    }
    return true;
  }

  public int describeContents()
  {
    return 0;
  }

  public abstract int getObjType();

  // ERROR //
  public final void setThumbImage(android.graphics.Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 110	java/io/ByteArrayOutputStream
    //   5: dup
    //   6: invokespecial 111	java/io/ByteArrayOutputStream:<init>	()V
    //   9: astore_3
    //   10: aload_1
    //   11: getstatic 117	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   14: bipush 85
    //   16: aload_3
    //   17: invokevirtual 123	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   20: pop
    //   21: aload_0
    //   22: aload_3
    //   23: invokevirtual 126	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   26: putfield 56	com/sina/weibo/sdk/api/BaseMediaObject:thumbData	[B
    //   29: aload_3
    //   30: ifnull +69 -> 99
    //   33: aload_3
    //   34: invokevirtual 129	java/io/ByteArrayOutputStream:close	()V
    //   37: return
    //   38: astore 4
    //   40: aload 4
    //   42: invokevirtual 132	java/lang/Exception:printStackTrace	()V
    //   45: ldc 66
    //   47: ldc 134
    //   49: invokestatic 74	com/sina/weibo/sdk/utils/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   52: aload_2
    //   53: ifnull -16 -> 37
    //   56: aload_2
    //   57: invokevirtual 129	java/io/ByteArrayOutputStream:close	()V
    //   60: return
    //   61: astore 7
    //   63: aload 7
    //   65: invokevirtual 135	java/io/IOException:printStackTrace	()V
    //   68: return
    //   69: astore 5
    //   71: aload_2
    //   72: ifnull +7 -> 79
    //   75: aload_2
    //   76: invokevirtual 129	java/io/ByteArrayOutputStream:close	()V
    //   79: aload 5
    //   81: athrow
    //   82: astore 6
    //   84: aload 6
    //   86: invokevirtual 135	java/io/IOException:printStackTrace	()V
    //   89: goto -10 -> 79
    //   92: astore 9
    //   94: aload 9
    //   96: invokevirtual 135	java/io/IOException:printStackTrace	()V
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

  protected abstract BaseMediaObject toExtraMediaObject(String paramString);

  protected abstract String toExtraMediaString();

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.actionUrl);
    paramParcel.writeString(this.schema);
    paramParcel.writeString(this.identify);
    paramParcel.writeString(this.title);
    paramParcel.writeString(this.description);
    paramParcel.writeByteArray(this.thumbData);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.sina.weibo.sdk.api.BaseMediaObject
 * JD-Core Version:    0.6.2
 */