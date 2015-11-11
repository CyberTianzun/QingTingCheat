package fm.qingting.framework.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class AsyncImageLoader
{
  private static HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap();
  private static boolean mEnableCache;
  private AsyncTask<Void, Void, Void> mAsyncTask;
  private InputStream mIStream;
  private ImageCallback mImageCallback;
  private Thread mThread;

  public static void enableCache(boolean paramBoolean)
  {
    mEnableCache = paramBoolean;
  }

  public static Bitmap loadImageFromUrl(String paramString, AsyncImageLoader paramAsyncImageLoader)
  {
    try
    {
      paramAsyncImageLoader.mIStream = ((InputStream)new URL(paramString).getContent());
      InputStream localInputStream = paramAsyncImageLoader.mIStream;
      Object localObject = null;
      if (localInputStream != null)
      {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        Bitmap localBitmap = BitmapFactory.decodeStream(paramAsyncImageLoader.mIStream, null, localOptions);
        localObject = localBitmap;
      }
      return localObject;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  public void cancel()
  {
    this.mImageCallback = null;
    if (this.mAsyncTask != null)
      this.mAsyncTask.cancel(true);
    if ((this.mThread == null) || (this.mIStream != null));
    try
    {
      this.mIStream.close();
      this.mThread.interrupt();
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public Bitmap loadBitmapWithAsyncTask(final String paramString, ImageCallback paramImageCallback)
  {
    this.mImageCallback = paramImageCallback;
    if ((mEnableCache) && (imageCache.containsKey(paramString)))
    {
      Bitmap localBitmap = (Bitmap)((SoftReference)imageCache.get(paramString)).get();
      if (localBitmap != null)
        return localBitmap;
    }
    this.mAsyncTask = new AsyncTask()
    {
      protected Void doInBackground(Void[] paramAnonymousArrayOfVoid)
      {
        Bitmap localBitmap = AsyncImageLoader.loadImageFromUrl(paramString, AsyncImageLoader.this);
        if (localBitmap == null)
          localBitmap = AsyncImageLoader.loadImageFromUrl(paramString, AsyncImageLoader.this);
        if (AsyncImageLoader.mEnableCache)
          AsyncImageLoader.imageCache.put(paramString, new SoftReference(localBitmap));
        Message localMessage = this.val$handler.obtainMessage(0, localBitmap);
        this.val$handler.sendMessage(localMessage);
        return null;
      }

      protected void onCancelled()
      {
        if (AsyncImageLoader.this.mIStream != null);
        try
        {
          AsyncImageLoader.this.mIStream.close();
          return;
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
      }
    };
    this.mAsyncTask.execute(new Void[0]);
    return null;
  }

  public Bitmap loadBitmapWithThread(final String paramString, ImageCallback paramImageCallback)
  {
    this.mImageCallback = paramImageCallback;
    if ((mEnableCache) && (imageCache.containsKey(paramString)))
    {
      Bitmap localBitmap = (Bitmap)((SoftReference)imageCache.get(paramString)).get();
      if (localBitmap != null)
        return localBitmap;
    }
    this.mThread = new Thread()
    {
      public void run()
      {
        Bitmap localBitmap = AsyncImageLoader.loadImageFromUrl(paramString, AsyncImageLoader.this);
        if (AsyncImageLoader.mEnableCache)
          AsyncImageLoader.imageCache.put(paramString, new SoftReference(localBitmap));
        Message localMessage = this.val$handler.obtainMessage(0, localBitmap);
        this.val$handler.sendMessage(localMessage);
      }
    };
    this.mThread.start();
    return null;
  }

  public static abstract interface ImageCallback
  {
    public abstract void imageLoaded(Bitmap paramBitmap, String paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.AsyncImageLoader
 * JD-Core Version:    0.6.2
 */