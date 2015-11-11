package fm.qingting.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class RemoteBitmapLoader extends AsyncTask
{
  private IBitmapRecvListener mListener;
  private String mUrl;

  public RemoteBitmapLoader(String paramString)
  {
    this.mUrl = paramString;
  }

  private void onHandleRecvBitmap(Bitmap paramBitmap)
  {
    if ((paramBitmap != null) && (this.mListener != null))
      this.mListener.onRecvBitmap(this.mUrl, paramBitmap);
  }

  // ERROR //
  protected Object doInBackground(Object[] paramArrayOfObject)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 15	fm/qingting/utils/RemoteBitmapLoader:mUrl	Ljava/lang/String;
    //   4: astore_2
    //   5: aconst_null
    //   6: astore_3
    //   7: aload_2
    //   8: ifnull +61 -> 69
    //   11: aload_0
    //   12: getfield 15	fm/qingting/utils/RemoteBitmapLoader:mUrl	Ljava/lang/String;
    //   15: ldc 35
    //   17: invokevirtual 41	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   20: istore 4
    //   22: aconst_null
    //   23: astore_3
    //   24: iload 4
    //   26: ifne +43 -> 69
    //   29: new 43	java/net/URL
    //   32: dup
    //   33: aload_0
    //   34: getfield 15	fm/qingting/utils/RemoteBitmapLoader:mUrl	Ljava/lang/String;
    //   37: invokespecial 45	java/net/URL:<init>	(Ljava/lang/String;)V
    //   40: invokevirtual 49	java/net/URL:openStream	()Ljava/io/InputStream;
    //   43: astore 13
    //   45: aload 13
    //   47: astore 6
    //   49: aload 6
    //   51: invokestatic 55	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   54: astore 15
    //   56: aload 15
    //   58: astore_3
    //   59: aload 6
    //   61: ifnull +8 -> 69
    //   64: aload 6
    //   66: invokevirtual 60	java/io/InputStream:close	()V
    //   69: aload_3
    //   70: areturn
    //   71: astore 16
    //   73: aload 16
    //   75: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   78: aload_3
    //   79: areturn
    //   80: astore 11
    //   82: aconst_null
    //   83: astore 6
    //   85: aload 11
    //   87: invokevirtual 64	java/lang/Exception:printStackTrace	()V
    //   90: aconst_null
    //   91: astore_3
    //   92: aload 6
    //   94: ifnull -25 -> 69
    //   97: aload 6
    //   99: invokevirtual 60	java/io/InputStream:close	()V
    //   102: aconst_null
    //   103: areturn
    //   104: astore 12
    //   106: aload 12
    //   108: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   111: aconst_null
    //   112: areturn
    //   113: astore 9
    //   115: aconst_null
    //   116: astore 6
    //   118: aconst_null
    //   119: astore_3
    //   120: aload 6
    //   122: ifnull -53 -> 69
    //   125: aload 6
    //   127: invokevirtual 60	java/io/InputStream:close	()V
    //   130: aconst_null
    //   131: areturn
    //   132: astore 10
    //   134: aload 10
    //   136: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   139: aconst_null
    //   140: areturn
    //   141: astore 5
    //   143: aconst_null
    //   144: astore 6
    //   146: aload 5
    //   148: astore 7
    //   150: aload 6
    //   152: ifnull +8 -> 160
    //   155: aload 6
    //   157: invokevirtual 60	java/io/InputStream:close	()V
    //   160: aload 7
    //   162: athrow
    //   163: astore 8
    //   165: aload 8
    //   167: invokevirtual 63	java/io/IOException:printStackTrace	()V
    //   170: goto -10 -> 160
    //   173: astore 7
    //   175: goto -25 -> 150
    //   178: astore 14
    //   180: goto -62 -> 118
    //   183: astore 11
    //   185: goto -100 -> 85
    //
    // Exception table:
    //   from	to	target	type
    //   64	69	71	java/io/IOException
    //   29	45	80	java/lang/Exception
    //   97	102	104	java/io/IOException
    //   29	45	113	java/lang/Error
    //   125	130	132	java/io/IOException
    //   29	45	141	finally
    //   155	160	163	java/io/IOException
    //   49	56	173	finally
    //   85	90	173	finally
    //   49	56	178	java/lang/Error
    //   49	56	183	java/lang/Exception
  }

  protected void onPostExecute(Object paramObject)
  {
    if (paramObject != null)
      onHandleRecvBitmap((Bitmap)paramObject);
  }

  public void setBitmapListener(IBitmapRecvListener paramIBitmapRecvListener)
  {
    this.mListener = paramIBitmapRecvListener;
  }

  public static abstract interface IBitmapRecvListener
  {
    public abstract void onRecvBitmap(String paramString, Bitmap paramBitmap);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.RemoteBitmapLoader
 * JD-Core Version:    0.6.2
 */