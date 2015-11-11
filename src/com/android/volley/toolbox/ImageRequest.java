package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Priority;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;

public class ImageRequest extends Request<Bitmap>
{
  private static final float IMAGE_BACKOFF_MULT = 2.0F;
  private static final int IMAGE_MAX_RETRIES = 2;
  private static final int IMAGE_TIMEOUT_MS = 1000;
  private static final Object sDecodeLock = new Object();
  private final Bitmap.Config mDecodeConfig;
  private final Response.Listener<Bitmap> mListener;
  private final int mMaxHeight;
  private final int mMaxWidth;

  public ImageRequest(String paramString, Response.Listener<Bitmap> paramListener, int paramInt1, int paramInt2, Bitmap.Config paramConfig, Response.ErrorListener paramErrorListener)
  {
    super(0, paramString, paramErrorListener);
    setRetryPolicy(new DefaultRetryPolicy(1000, 2, 2.0F));
    this.mListener = paramListener;
    this.mDecodeConfig = paramConfig;
    this.mMaxWidth = paramInt1;
    this.mMaxHeight = paramInt2;
  }

  private Response<Bitmap> doParse(NetworkResponse paramNetworkResponse)
  {
    byte[] arrayOfByte = paramNetworkResponse.data;
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    Object localObject;
    if ((this.mMaxWidth == 0) && (this.mMaxHeight == 0))
    {
      localOptions.inPreferredConfig = this.mDecodeConfig;
      localObject = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
    }
    while (localObject == null)
    {
      return Response.error(new ParseError(paramNetworkResponse));
      localOptions.inJustDecodeBounds = true;
      BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
      int i = localOptions.outWidth;
      int j = localOptions.outHeight;
      int k = getResizedDimension(this.mMaxWidth, this.mMaxHeight, i, j);
      int m = getResizedDimension(this.mMaxHeight, this.mMaxWidth, j, i);
      localOptions.inJustDecodeBounds = false;
      localOptions.inSampleSize = findBestSampleSize(i, j, k, m);
      Bitmap localBitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
      if ((localBitmap != null) && ((localBitmap.getWidth() > k) || (localBitmap.getHeight() > m)))
      {
        localObject = Bitmap.createScaledBitmap(localBitmap, k, m, true);
        localBitmap.recycle();
      }
      else
      {
        localObject = localBitmap;
      }
    }
    return Response.success(localObject, HttpHeaderParser.parseCacheHeaders(paramNetworkResponse));
  }

  static int findBestSampleSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    double d = Math.min(paramInt1 / paramInt3, paramInt2 / paramInt4);
    for (float f = 1.0F; ; f *= 2.0F)
      if (f * 2.0F > d)
        return (int)f;
  }

  private static int getResizedDimension(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 == 0) && (paramInt2 == 0))
      return paramInt3;
    if (paramInt1 == 0)
      return (int)(paramInt2 / paramInt4 * paramInt3);
    if (paramInt2 == 0)
      return paramInt1;
    double d = paramInt4 / paramInt3;
    int i = paramInt1;
    if (d * i > paramInt2)
      i = (int)(paramInt2 / d);
    return i;
  }

  protected void deliverResponse(Bitmap paramBitmap)
  {
    this.mListener.onResponse(paramBitmap);
  }

  public Request.Priority getPriority()
  {
    return Request.Priority.LOW;
  }

  protected Response<Bitmap> parseNetworkResponse(NetworkResponse paramNetworkResponse)
  {
    synchronized (sDecodeLock)
    {
      try
      {
        Response localResponse2 = doParse(paramNetworkResponse);
        return localResponse2;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(paramNetworkResponse.data.length);
        arrayOfObject[1] = getUrl();
        VolleyLog.e("Caught OOM for %d byte image, url=%s", arrayOfObject);
        Response localResponse1 = Response.error(new ParseError(localOutOfMemoryError));
        return localResponse1;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.android.volley.toolbox.ImageRequest
 * JD-Core Version:    0.6.2
 */