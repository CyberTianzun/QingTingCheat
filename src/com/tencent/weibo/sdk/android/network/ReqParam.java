package com.tencent.weibo.sdk.android.network;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReqParam
{
  public Bitmap mBitmap = null;
  private Map<String, String> mParams = new HashMap();

  public void addParam(String paramString, Object paramObject)
  {
    this.mParams.put(paramString, paramObject.toString());
  }

  public void addParam(String paramString1, String paramString2)
  {
    this.mParams.put(paramString1, paramString2);
  }

  public void addParam(String paramString, byte[] paramArrayOfByte)
  {
    double d1 = paramArrayOfByte.length / 1024;
    if (d1 > 400.0D)
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      double d2 = d1 / 400.0D;
      zoomImage(this.mBitmap, this.mBitmap.getWidth() / Math.sqrt(d2), this.mBitmap.getHeight() / Math.sqrt(d2)).compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
      paramArrayOfByte = localByteArrayOutputStream.toByteArray();
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte.length)
      {
        Log.d("buffer=======", localStringBuffer.toString());
        this.mParams.put(paramString, localStringBuffer.toString());
        return;
      }
      localStringBuffer.append((char)paramArrayOfByte[i]);
    }
  }

  public Map<String, String> getmParams()
  {
    return this.mParams;
  }

  public void setBitmap(Bitmap paramBitmap)
  {
    this.mBitmap = paramBitmap;
  }

  public void setmParams(Map<String, String> paramMap)
  {
    this.mParams = paramMap;
  }

  public String toString()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = this.mParams.keySet().iterator();
    StringBuffer localStringBuffer;
    Iterator localIterator2;
    if (!localIterator1.hasNext())
    {
      Collections.sort(localArrayList, new Comparator()
      {
        public int compare(String paramAnonymousString1, String paramAnonymousString2)
        {
          if (paramAnonymousString1.compareTo(paramAnonymousString2) > 0)
            return 1;
          if (paramAnonymousString1.compareTo(paramAnonymousString2) < 0)
            return -1;
          return 0;
        }
      });
      localStringBuffer = new StringBuffer();
      localIterator2 = localArrayList.iterator();
    }
    while (true)
    {
      if (!localIterator2.hasNext())
      {
        Log.d("p-----", localStringBuffer.toString());
        return localStringBuffer.toString().replaceAll("\n", "").replaceAll("\r", "");
        localArrayList.add((String)localIterator1.next());
        break;
      }
      String str = (String)localIterator2.next();
      if (!str.equals("pic"))
      {
        localStringBuffer.append(str);
        localStringBuffer.append("=");
        localStringBuffer.append((String)this.mParams.get(str));
        localStringBuffer.append("&");
      }
    }
  }

  public Bitmap zoomImage(Bitmap paramBitmap, double paramDouble1, double paramDouble2)
  {
    float f1 = paramBitmap.getWidth();
    float f2 = paramBitmap.getHeight();
    Matrix localMatrix = new Matrix();
    localMatrix.postScale((float)paramDouble1 / f1, (float)paramDouble2 / f2);
    return Bitmap.createBitmap(paramBitmap, 0, 0, (int)f1, (int)f2, localMatrix, true);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.network.ReqParam
 * JD-Core Version:    0.6.2
 */