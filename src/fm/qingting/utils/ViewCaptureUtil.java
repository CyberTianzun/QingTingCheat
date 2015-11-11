package fm.qingting.utils;

import fm.qingting.framework.view.IView;

public class ViewCaptureUtil
{
  private static final int STANDAR_WIDTH = 320;
  private static IView screenView;
  private static String viewPath;

  // ERROR //
  public static void captureViewPath()
  {
    // Byte code:
    //   0: getstatic 24	fm/qingting/utils/ViewCaptureUtil:screenView	Lfm/qingting/framework/view/IView;
    //   3: ifnonnull +4 -> 7
    //   6: return
    //   7: aconst_null
    //   8: putstatic 26	fm/qingting/utils/ViewCaptureUtil:viewPath	Ljava/lang/String;
    //   11: getstatic 24	fm/qingting/utils/ViewCaptureUtil:screenView	Lfm/qingting/framework/view/IView;
    //   14: invokeinterface 32 1 0
    //   19: invokevirtual 38	android/view/View:getMeasuredWidth	()I
    //   22: istore_1
    //   23: getstatic 24	fm/qingting/utils/ViewCaptureUtil:screenView	Lfm/qingting/framework/view/IView;
    //   26: invokeinterface 32 1 0
    //   31: invokevirtual 41	android/view/View:getMeasuredHeight	()I
    //   34: istore_2
    //   35: iload_1
    //   36: ifle -30 -> 6
    //   39: iload_2
    //   40: ifle -34 -> 6
    //   43: ldc 42
    //   45: iload_1
    //   46: i2f
    //   47: fdiv
    //   48: fstore_3
    //   49: fload_3
    //   50: iload_1
    //   51: i2f
    //   52: fmul
    //   53: f2i
    //   54: iconst_0
    //   55: fload_3
    //   56: iload_2
    //   57: i2f
    //   58: fmul
    //   59: f2i
    //   60: iadd
    //   61: getstatic 48	android/graphics/Bitmap$Config:RGB_565	Landroid/graphics/Bitmap$Config;
    //   64: invokestatic 54	android/graphics/Bitmap:createBitmap	(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
    //   67: astore 4
    //   69: new 56	android/graphics/Canvas
    //   72: dup
    //   73: aload 4
    //   75: invokespecial 59	android/graphics/Canvas:<init>	(Landroid/graphics/Bitmap;)V
    //   78: astore 5
    //   80: aload 5
    //   82: invokevirtual 62	android/graphics/Canvas:save	()I
    //   85: istore 6
    //   87: aload 5
    //   89: fconst_0
    //   90: iconst_0
    //   91: i2f
    //   92: invokevirtual 66	android/graphics/Canvas:translate	(FF)V
    //   95: aload 5
    //   97: fload_3
    //   98: fload_3
    //   99: invokevirtual 69	android/graphics/Canvas:scale	(FF)V
    //   102: getstatic 24	fm/qingting/utils/ViewCaptureUtil:screenView	Lfm/qingting/framework/view/IView;
    //   105: invokeinterface 32 1 0
    //   110: aload 5
    //   112: invokevirtual 73	android/view/View:draw	(Landroid/graphics/Canvas;)V
    //   115: aload 5
    //   117: iload 6
    //   119: invokevirtual 77	android/graphics/Canvas:restoreToCount	(I)V
    //   122: aload 5
    //   124: aload 5
    //   126: invokevirtual 62	android/graphics/Canvas:save	()I
    //   129: invokevirtual 77	android/graphics/Canvas:restoreToCount	(I)V
    //   132: invokestatic 83	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   135: invokevirtual 87	fm/qingting/qtradio/model/InfoManager:getContext	()Landroid/content/Context;
    //   138: invokevirtual 92	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   141: invokevirtual 96	android/content/Context:getFilesDir	()Ljava/io/File;
    //   144: invokevirtual 102	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   147: astore 7
    //   149: new 98	java/io/File
    //   152: dup
    //   153: new 104	java/lang/StringBuilder
    //   156: dup
    //   157: invokespecial 105	java/lang/StringBuilder:<init>	()V
    //   160: aload 7
    //   162: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: ldc 111
    //   167: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   173: invokespecial 117	java/io/File:<init>	(Ljava/lang/String;)V
    //   176: astore 8
    //   178: aload 8
    //   180: invokevirtual 121	java/io/File:createNewFile	()Z
    //   183: pop
    //   184: new 123	java/io/FileOutputStream
    //   187: dup
    //   188: aload 8
    //   190: invokespecial 126	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   193: astore 11
    //   195: aload 4
    //   197: getstatic 132	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   200: bipush 100
    //   202: aload 11
    //   204: invokevirtual 136	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   207: pop
    //   208: aload 11
    //   210: invokevirtual 139	java/io/FileOutputStream:flush	()V
    //   213: aload 11
    //   215: invokevirtual 142	java/io/FileOutputStream:close	()V
    //   218: new 104	java/lang/StringBuilder
    //   221: dup
    //   222: invokespecial 105	java/lang/StringBuilder:<init>	()V
    //   225: aload 7
    //   227: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   230: ldc 111
    //   232: invokevirtual 109	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   235: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   238: putstatic 26	fm/qingting/utils/ViewCaptureUtil:viewPath	Ljava/lang/String;
    //   241: return
    //   242: astore 9
    //   244: aload 9
    //   246: invokevirtual 145	java/io/IOException:printStackTrace	()V
    //   249: return
    //   250: astore 15
    //   252: aload 15
    //   254: invokevirtual 146	java/io/FileNotFoundException:printStackTrace	()V
    //   257: return
    //   258: astore 13
    //   260: aload 13
    //   262: invokevirtual 145	java/io/IOException:printStackTrace	()V
    //   265: return
    //   266: astore 14
    //   268: aload 14
    //   270: invokevirtual 145	java/io/IOException:printStackTrace	()V
    //   273: return
    //   274: astore_0
    //   275: return
    //
    // Exception table:
    //   from	to	target	type
    //   178	184	242	java/io/IOException
    //   184	195	250	java/io/FileNotFoundException
    //   208	213	258	java/io/IOException
    //   213	218	266	java/io/IOException
    //   7	35	274	java/lang/Exception
    //   43	178	274	java/lang/Exception
    //   178	184	274	java/lang/Exception
    //   184	195	274	java/lang/Exception
    //   195	208	274	java/lang/Exception
    //   208	213	274	java/lang/Exception
    //   213	218	274	java/lang/Exception
    //   218	241	274	java/lang/Exception
    //   244	249	274	java/lang/Exception
    //   252	257	274	java/lang/Exception
    //   260	265	274	java/lang/Exception
    //   268	273	274	java/lang/Exception
  }

  public static String getViewPath()
  {
    return viewPath;
  }

  public static void setScreenView(IView paramIView)
  {
    screenView = paramIView;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.ViewCaptureUtil
 * JD-Core Version:    0.6.2
 */