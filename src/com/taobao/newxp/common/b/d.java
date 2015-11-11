package com.taobao.newxp.common.b;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.taobao.munion.base.a;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.controller.XpListenersCenter.STATUS;
import com.taobao.newxp.view.common.gif.g;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

public class d
{
  public static boolean a = false;
  private static final byte b = 0;
  private static final byte c = 1;
  private static final String d = d.class.getName();
  private static final long e = 104857600L;
  private static final long f = 10485760L;
  private static final Map<ImageView, String> g = Collections.synchronizedMap(new WeakHashMap());
  private static Thread h;

  public static Bitmap a(Bitmap paramBitmap)
  {
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      Paint localPaint = new Paint();
      Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
      RectF localRectF = new RectF(localRect);
      localPaint.setAntiAlias(true);
      localCanvas.drawARGB(0, 0, 0, 0);
      localPaint.setColor(-12434878);
      localCanvas.drawRoundRect(localRectF, paramBitmap.getWidth() / 6, paramBitmap.getHeight() / 6, localPaint);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
      paramBitmap.recycle();
      return localBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      com.taobao.newxp.common.Log.e(d, "Cant`t create round corner bitmap. [OutOfMemoryError] ");
      return null;
    }
    catch (NullPointerException localNullPointerException)
    {
      return null;
    }
    catch (RuntimeException localRuntimeException)
    {
    }
    return null;
  }

  public static String a(Context paramContext, String paramString)
  {
    if (b.c(paramString))
      return null;
    try
    {
      String str1 = b(paramString) + ".tmp";
      String str2;
      long l;
      File localFile2;
      if (AlimmContext.getAliContext().getAppUtils().d())
      {
        str2 = Environment.getExternalStorageDirectory().getCanonicalPath();
        l = 104857600L;
        localFile2 = new File(str2 + "/download/.um");
        if (!localFile2.exists())
          break label273;
        if (c(localFile2.getCanonicalFile()) > l)
          a(localFile2);
      }
      while (true)
      {
        localFile1 = new File(localFile2, str1);
        try
        {
          localFile1.createNewFile();
          localFileOutputStream = new FileOutputStream(localFile1);
          localInputStream = (InputStream)new URL(paramString).openConnection().getContent();
          byte[] arrayOfByte = new byte[4096];
          while (true)
          {
            int i = localInputStream.read(arrayOfByte);
            if (i == -1)
              break;
            localFileOutputStream.write(arrayOfByte, 0, i);
          }
        }
        catch (Exception localException1)
        {
        }
        com.taobao.newxp.common.Log.a(d, localException1.getStackTrace().toString() + "\t url:\t" + b.a + paramString);
        if ((localFile1 != null) && (localFile1.exists()))
          localFile1.deleteOnExit();
        return null;
        str2 = paramContext.getCacheDir().getCanonicalPath();
        l = 10485760L;
        break;
        label273: if (!localFile2.mkdirs())
          com.taobao.newxp.common.Log.b(d, "Failed to create directory" + localFile2.getAbsolutePath() + ". Check permission. Make sure WRITE_EXTERNAL_STORAGE is added in your Manifest.xml");
      }
    }
    catch (Exception localException2)
    {
      File localFile1;
      FileOutputStream localFileOutputStream;
      InputStream localInputStream;
      while (true)
        localFile1 = null;
      localFileOutputStream.flush();
      localInputStream.close();
      localFileOutputStream.close();
      File localFile3 = new File(localFile1.getParent(), localFile1.getName().replace(".tmp", ""));
      localFile1.renameTo(localFile3);
      com.taobao.newxp.common.Log.a(d, "download img[" + paramString + "]  to " + localFile3.getCanonicalPath());
      String str3 = localFile3.getCanonicalPath();
      return str3;
    }
  }

  public static void a(Context paramContext, ImageView paramImageView, String paramString, boolean paramBoolean)
  {
    a(paramContext, paramImageView, paramString, paramBoolean, null, null, false);
  }

  public static void a(Context paramContext, ImageView paramImageView, String paramString, boolean paramBoolean, a parama)
  {
    a(paramContext, paramImageView, paramString, paramBoolean, parama, null, false);
  }

  public static void a(Context paramContext, ImageView paramImageView, String paramString, boolean paramBoolean, a parama, Animation paramAnimation)
  {
    a(paramContext, paramImageView, paramString, paramBoolean, parama, paramAnimation, false);
  }

  public static void a(final Context paramContext, final ImageView paramImageView, final String paramString, final boolean paramBoolean1, final a parama, final Animation paramAnimation, final boolean paramBoolean2)
  {
    if (paramImageView == null);
    do
    {
      return;
      g.put(paramImageView, paramString);
      try
      {
        new d(paramContext, paramString, b(paramContext, paramString))
        {
          public void a(Drawable paramAnonymousDrawable)
          {
            if ((paramAnonymousDrawable instanceof AnimationDrawable))
            {
              d.a(paramContext, paramImageView, paramAnonymousDrawable, true, parama, paramAnimation, false, paramString);
              return;
            }
            d.a(paramContext, paramImageView, paramAnonymousDrawable, paramBoolean1, parama, paramAnimation, paramBoolean2, paramString);
          }

          protected void onPreExecute()
          {
            super.onPreExecute();
            if (parama != null)
            {
              if (1 == this.h)
                parama.a(d.b.a);
            }
            else
              return;
            parama.a(d.b.b);
          }
        }
        .execute(new Object[0]);
        return;
      }
      catch (Exception localException)
      {
        com.taobao.newxp.common.Log.b(d, localException.toString());
      }
    }
    while (parama == null);
    parama.a(XpListenersCenter.STATUS.FAIL);
  }

  public static void a(Context paramContext, String paramString, final c paramc)
  {
    try
    {
      new d(paramContext, paramString, b(paramContext, paramString))
      {
        public void a(Drawable paramAnonymousDrawable)
        {
          paramc.a(paramAnonymousDrawable);
        }

        protected void onPreExecute()
        {
          super.onPreExecute();
          if (paramc != null)
          {
            if (1 == this.h)
              paramc.a(d.b.a);
          }
          else
            return;
          paramc.a(d.b.b);
        }
      }
      .execute(new Object[0]);
      return;
    }
    catch (Exception localException)
    {
      do
        com.taobao.newxp.common.Log.b(d, "", localException);
      while (paramc == null);
      paramc.a(null);
    }
  }

  protected static void a(File paramFile)
  {
    try
    {
      if (h == null)
      {
        h = new Thread(new Runnable()
        {
          public void run()
          {
            d.b(this.a);
            d.a(null);
          }
        });
        h.start();
      }
      return;
    }
    catch (Exception localException)
    {
      android.util.Log.w(d, "", localException);
    }
  }

  private static boolean a(ImageView paramImageView, String paramString)
  {
    String str = (String)g.get(paramImageView);
    return (str != null) && (!str.equals(paramString));
  }

  public static File b(Context paramContext, String paramString)
    throws IOException
  {
    String str1 = b(paramString);
    if (AlimmContext.getAliContext().getAppUtils().d());
    for (String str2 = Environment.getExternalStorageDirectory().getCanonicalPath(); ; str2 = paramContext.getCacheDir().getCanonicalPath())
    {
      File localFile = new File(new File(str2 + "/download/.um"), str1);
      if (!localFile.exists())
        break;
      return localFile;
    }
    return null;
  }

  private static String b(String paramString)
  {
    int i = paramString.lastIndexOf(".");
    String str = "";
    if (i >= 0)
      str = paramString.substring(i);
    return b.a(paramString) + str;
  }

  private static void b(Context paramContext, ImageView paramImageView, Drawable paramDrawable, boolean paramBoolean1, a parama, Animation paramAnimation, boolean paramBoolean2, String paramString)
  {
    if ((paramBoolean2) && (paramDrawable != null));
    while (true)
    {
      try
      {
        paramDrawable = new BitmapDrawable(a(((BitmapDrawable)paramDrawable).getBitmap()));
        break label207;
        if (parama != null)
          parama.a(XpListenersCenter.STATUS.FAIL);
        com.taobao.newxp.common.Log.e(d, "bind drawable failed. drawable [" + paramDrawable + "]  imageView[+" + paramImageView + "+]");
        return;
        if (a(paramImageView, paramString))
        {
          if (parama == null)
            continue;
          parama.a(XpListenersCenter.STATUS.FAIL);
          continue;
        }
      }
      catch (Exception localException)
      {
        com.taobao.newxp.common.Log.b(d, "bind failed", localException);
        if (parama == null)
          continue;
        parama.a(XpListenersCenter.STATUS.FAIL);
        continue;
      }
      finally
      {
      }
      if (paramBoolean1 == true)
        paramImageView.setBackgroundDrawable(paramDrawable);
      while (true)
      {
        if (paramAnimation != null)
          paramImageView.startAnimation(paramAnimation);
        if (parama == null)
          break;
        parama.a(XpListenersCenter.STATUS.SUCCESS);
        break;
        paramImageView.setImageDrawable(paramDrawable);
      }
      label207: if (paramDrawable != null)
        if (paramImageView != null);
    }
  }

  private static long c(File paramFile)
  {
    long l1;
    if ((paramFile == null) || (!paramFile.exists()) || (!paramFile.isDirectory()))
      l1 = 0L;
    while (true)
    {
      return l1;
      Stack localStack = new Stack();
      localStack.clear();
      localStack.push(paramFile);
      long l2;
      for (l1 = 0L; !localStack.isEmpty(); l1 = l2)
      {
        File[] arrayOfFile = ((File)localStack.pop()).listFiles();
        l2 = l1;
        int i = 0;
        if (i < arrayOfFile.length)
        {
          if (arrayOfFile[i].isDirectory())
            localStack.push(arrayOfFile[i]);
          for (long l3 = l2; ; l3 = l2 + arrayOfFile[i].length())
          {
            i++;
            l2 = l3;
            break;
          }
        }
      }
    }
  }

  private static Drawable c(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0));
    switch (d(paramString))
    {
    default:
      return null;
    case 0:
      try
      {
        Drawable localDrawable = Drawable.createFromPath(paramString);
        return localDrawable;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        com.taobao.newxp.common.Log.e(d, "Resutil fetchImage OutOfMemoryError:" + localOutOfMemoryError.toString());
        return null;
      }
    case 1:
    }
    File localFile = new File(paramString);
    try
    {
      g localg = new g(new FileInputStream(localFile), null);
      return localg;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      com.taobao.newxp.common.Log.e(d, "Resutil fetchGifImage FileNotFoundException:" + localFileNotFoundException.toString());
    }
    return null;
  }

  private static byte d(String paramString)
  {
    if ((paramString != null) && (paramString.toLowerCase().endsWith("gif")))
      return 1;
    return 0;
  }

  private static boolean d(File paramFile)
  {
    boolean bool1 = false;
    if (paramFile != null);
    while (true)
    {
      int i;
      try
      {
        boolean bool2 = paramFile.exists();
        bool1 = false;
        if (!bool2)
          break label122;
        boolean bool3 = paramFile.canWrite();
        bool1 = false;
        if (!bool3)
          break label122;
        if (!paramFile.isDirectory())
          return false;
        File[] arrayOfFile = paramFile.listFiles();
        i = 0;
        if (i < arrayOfFile.length)
          if (arrayOfFile[i].isDirectory())
            d(arrayOfFile[i]);
          else if (new Date().getTime() - arrayOfFile[i].lastModified() > 1800000L)
            arrayOfFile[i].delete();
      }
      catch (Exception localException)
      {
        return false;
      }
      bool1 = true;
      label122: return bool1;
      i++;
    }
  }

  public static abstract interface a
  {
    public abstract void a(d.b paramb);

    public abstract void a(XpListenersCenter.STATUS paramSTATUS);
  }

  public static enum b
  {
    static
    {
      b[] arrayOfb = new b[2];
      arrayOfb[0] = a;
      arrayOfb[1] = b;
    }
  }

  public static abstract interface c
  {
    public abstract void a(Drawable paramDrawable);

    public abstract void a(d.b paramb);
  }

  static abstract class d extends AsyncTask<Object, Integer, Drawable>
  {
    private Context a;
    private String b;
    private File c;
    int h = 0;

    public d(Context paramContext, String paramString, File paramFile)
    {
      this.c = paramFile;
      this.a = paramContext;
      this.b = paramString;
    }

    protected Drawable a(Object[] paramArrayOfObject)
    {
      if (d.a);
      try
      {
        Thread.sleep(3000L);
        if (1 == this.h)
        {
          Drawable localDrawable2 = d.a(this.c.getAbsolutePath());
          if (localDrawable2 == null)
            this.c.delete();
          com.taobao.newxp.common.Log.c(d.a(), "get drawable from cacheFile.");
          return localDrawable2;
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
          localInterruptedException.printStackTrace();
      }
      while (true)
      {
        try
        {
          d.a(this.a, this.b);
          File localFile = d.b(this.a, this.b);
          if ((localFile != null) && (localFile.exists()))
          {
            localDrawable1 = d.a(localFile.getAbsolutePath());
            com.taobao.newxp.common.Log.c(d.a(), "get drawable from net else file.");
            return localDrawable1;
          }
        }
        catch (Exception localException)
        {
          com.taobao.newxp.common.Log.e(d.a(), localException.toString(), localException);
          return null;
        }
        Drawable localDrawable1 = null;
      }
    }

    public abstract void a(Drawable paramDrawable);

    protected void b(Drawable paramDrawable)
    {
      a(paramDrawable);
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      if ((this.c != null) && (this.c.exists()))
        this.h = 1;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.b.d
 * JD-Core Version:    0.6.2
 */