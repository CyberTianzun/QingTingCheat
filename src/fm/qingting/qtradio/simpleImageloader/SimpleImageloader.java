package fm.qingting.qtradio.simpleImageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.net.URL;

public class SimpleImageloader extends AsyncTask
{
  private static final int EndLoadingimage = 2;
  private static final int Init = 0;
  private static final int StartLoadimage = 1;
  private boolean isLoading = false;
  private String mAdUrl = null;
  private IRecvBitmapEventListener mBitmapListener;
  private Bitmap mImage;
  private String mImageUrl;

  private void log(String paramString)
  {
  }

  private boolean onHandleRecvBitmap(Bitmap paramBitmap)
  {
    if ((paramBitmap != null) && (this.mBitmapListener != null))
      this.mBitmapListener.onRecvBitmap(paramBitmap);
    return false;
  }

  // ERROR //
  private void restoreAdvertisementPic(Bitmap paramBitmap, String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +152 -> 153
    //   4: aload_2
    //   5: ifnull +148 -> 153
    //   8: aload_0
    //   9: getfield 28	fm/qingting/qtradio/simpleImageloader/SimpleImageloader:mAdUrl	Ljava/lang/String;
    //   12: ifnull +141 -> 153
    //   15: aload_0
    //   16: getfield 28	fm/qingting/qtradio/simpleImageloader/SimpleImageloader:mAdUrl	Ljava/lang/String;
    //   19: aload_2
    //   20: invokevirtual 53	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   23: ifeq +130 -> 153
    //   26: invokestatic 59	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   29: invokevirtual 63	fm/qingting/qtradio/model/InfoManager:getContext	()Landroid/content/Context;
    //   32: invokevirtual 68	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   35: invokevirtual 72	android/content/Context:getFilesDir	()Ljava/io/File;
    //   38: invokevirtual 78	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   41: astore_3
    //   42: new 80	java/lang/StringBuilder
    //   45: dup
    //   46: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   49: aload_3
    //   50: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: ldc 87
    //   55: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: invokevirtual 90	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   61: astore 4
    //   63: new 74	java/io/File
    //   66: dup
    //   67: aload 4
    //   69: invokespecial 92	java/io/File:<init>	(Ljava/lang/String;)V
    //   72: astore 5
    //   74: aload 5
    //   76: invokevirtual 96	java/io/File:createNewFile	()Z
    //   79: pop
    //   80: new 98	java/io/FileOutputStream
    //   83: dup
    //   84: aload 5
    //   86: invokespecial 101	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   89: astore 9
    //   91: aload_1
    //   92: getstatic 107	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   95: bipush 50
    //   97: aload 9
    //   99: invokevirtual 113	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   102: pop
    //   103: aload 9
    //   105: invokevirtual 116	java/io/FileOutputStream:flush	()V
    //   108: aload 9
    //   110: invokevirtual 119	java/io/FileOutputStream:close	()V
    //   113: invokestatic 124	fm/qingting/qtradio/model/SharedCfg:getInstance	()Lfm/qingting/qtradio/model/SharedCfg;
    //   116: aload 4
    //   118: invokevirtual 127	fm/qingting/qtradio/model/SharedCfg:setLocalAdvertisementPic	(Ljava/lang/String;)V
    //   121: return
    //   122: astore 7
    //   124: aload 7
    //   126: invokevirtual 130	java/io/IOException:printStackTrace	()V
    //   129: return
    //   130: astore 13
    //   132: aload 13
    //   134: invokevirtual 131	java/io/FileNotFoundException:printStackTrace	()V
    //   137: return
    //   138: astore 11
    //   140: aload 11
    //   142: invokevirtual 130	java/io/IOException:printStackTrace	()V
    //   145: return
    //   146: astore 12
    //   148: aload 12
    //   150: invokevirtual 130	java/io/IOException:printStackTrace	()V
    //   153: return
    //   154: astore 6
    //   156: return
    //
    // Exception table:
    //   from	to	target	type
    //   74	80	122	java/io/IOException
    //   80	91	130	java/io/FileNotFoundException
    //   103	108	138	java/io/IOException
    //   108	113	146	java/io/IOException
    //   63	74	154	java/lang/Exception
    //   74	80	154	java/lang/Exception
    //   80	91	154	java/lang/Exception
    //   91	103	154	java/lang/Exception
    //   103	108	154	java/lang/Exception
    //   108	113	154	java/lang/Exception
    //   113	121	154	java/lang/Exception
    //   124	129	154	java/lang/Exception
    //   132	137	154	java/lang/Exception
    //   140	145	154	java/lang/Exception
    //   148	153	154	java/lang/Exception
  }

  protected Object doInBackground(Object[] paramArrayOfObject)
  {
    String str = (String)paramArrayOfObject[0];
    if ((str == null) || (str.equalsIgnoreCase("")))
      return null;
    try
    {
      this.mImageUrl = str;
      this.mImage = BitmapFactory.decodeStream(new URL(str).openStream());
      restoreAdvertisementPic(this.mImage, str);
      return this.mImage;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        log("catch an exception");
      }
    }
  }

  public boolean isLastImageUrl(String paramString)
  {
    return (paramString != null) && (this.mImageUrl != null) && (this.mImageUrl.equalsIgnoreCase(paramString));
  }

  public boolean isLoading()
  {
    return this.isLoading;
  }

  protected void onPostExecute(Object paramObject)
  {
    if (paramObject != null)
    {
      log("onpostexecute,handle result");
      this.isLoading = false;
      onHandleRecvBitmap((Bitmap)paramObject);
    }
  }

  public void ready(boolean paramBoolean)
  {
    this.isLoading = paramBoolean;
  }

  public void setBitmapEventListener(IRecvBitmapEventListener paramIRecvBitmapEventListener)
  {
    this.mBitmapListener = paramIRecvBitmapEventListener;
  }

  public void setNetAdvertisementPic(String paramString)
  {
    this.mAdUrl = paramString;
  }

  public static abstract interface IRecvBitmapEventListener
  {
    public static final int RECV_BITMAP;

    public abstract boolean onRecvBitmap(Bitmap paramBitmap);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.simpleImageloader.SimpleImageloader
 * JD-Core Version:    0.6.2
 */